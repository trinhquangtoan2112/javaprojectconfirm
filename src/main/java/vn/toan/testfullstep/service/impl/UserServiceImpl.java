package vn.toan.testfullstep.Service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import vn.toan.testfullstep.common.UserStatus;
import vn.toan.testfullstep.controller.request.UserCreationRequest;
import vn.toan.testfullstep.controller.request.UserPasswordRequest;
import vn.toan.testfullstep.controller.request.UserUpdateRequest;
import vn.toan.testfullstep.controller.response.UserPageResponse;
import vn.toan.testfullstep.controller.response.UserResponse;
import vn.toan.testfullstep.excepton.ResourceNotFoundExcepton;
import vn.toan.testfullstep.model.AddressEntity;
import vn.toan.testfullstep.model.UserEntity;
import vn.toan.testfullstep.repository.AddressRepository;
import vn.toan.testfullstep.repository.UserRepository;
import vn.toan.testfullstep.Service.UserService;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
@Slf4j(topic = "USER-SERVICE")
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final AddressRepository addressRepository;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;

    @Override
    public UserPageResponse findAll(String keyword, String sort, int pageNumber, int pageSize) {

        // sort
        Sort.Order order = new Sort.Order(Sort.Direction.ASC, "id");
        if (StringUtils.hasLength(sort)) {
            // goi vao day
            Pattern pattern = Pattern.compile("(\\w+?)(:)(.*)");// tencot:asc|desc
            Matcher matcher = pattern.matcher(sort);
            if (matcher.find()) {
                String column = matcher.group(1);
                if (matcher.group(3).equalsIgnoreCase("asc")) {

                    order = new Sort.Order(Sort.Direction.ASC, column);
                } else {
                    order = new Sort.Order(Sort.Direction.DESC, column);
                }
            }
        }
        // pageNumber
        int pageNo = 0;
        if (pageNumber > 0) {
            pageNo = pageNumber - 1;
        }
        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(order));
        if (StringUtils.hasLength(keyword)) {
            // goi vao day
            keyword = "%" + keyword.toLowerCase() + "%";
            Page<UserEntity> usersearchList = userRepository.searchByKeyWord(keyword, pageable);

            return getUserPageRespone(pageNumber, pageSize, pageable, usersearchList);
        }
        Page<UserEntity> userList = userRepository.findAll(pageable);
        return getUserPageRespone(pageNumber, pageSize, pageable, userList);
    }

    private UserPageResponse getUserPageRespone(int pageNumber, int pageSize, Pageable pageable,
            Page<UserEntity> userList) {

        List<UserResponse> userResponses = userList.stream().map(user -> {
            return modelMapper.map(user, UserResponse.class);
        }).collect(Collectors.toList());
        UserPageResponse userPageResponse = new UserPageResponse();
        userPageResponse.setPageNumber(pageNumber);
        userPageResponse.setPageSize(pageSize);
        userPageResponse.setTotalPages(userList.getTotalPages());
        userPageResponse.setTotalElements(userList.getTotalElements());
        userPageResponse.setUsers(userResponses);
        return userPageResponse;
    }

    @Override
    public UserResponse findById(Long id) {
        UserEntity user = userRepository.findById(id).orElseThrow(() -> {
            return new ResourceNotFoundExcepton("Không tìm thấy người dùngss");
        });

        // UserResponse userResponse = new UserResponse();
        // userResponse = modalMapperConfig.map(user);
        return modelMapper.map(user, UserResponse.class);
    }

    @Override
    public UserResponse findByUsername(String username) {
        return null;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public long saveUser(UserCreationRequest req) {
        UserEntity user = new UserEntity();
        user.setFirstName(req.getFirstName());
        user.setLastName(req.getLastName());
        user.setGender(req.getGender());
        user.setBirthday(req.getBirthday());
        user.setEmail(req.getEmail());
        user.setPhone(req.getPhone());
        user.setUsername(req.getUsername());
        user.setType(req.getType());
        user.setStatus(UserStatus.NONE);
        userRepository.save(user);
        log.info("Saved user: {}", user);
        if (user.getId() != null) {
            List<AddressEntity> addressess = new ArrayList<>();
            req.getAddresses().forEach(address -> {
                AddressEntity addressEntity = new AddressEntity();
                addressEntity.setApartmentNumber(address.getApartmentNumber());
                addressEntity.setFloor(address.getFloor());
                addressEntity.setBuilding(address.getBuilding());
                addressEntity.setStreetNumber(address.getStreetNumber());
                addressEntity.setStreet(address.getStreet());
                addressEntity.setCity(address.getCity());
                addressEntity.setCountry(address.getCountry());
                addressEntity.setAddressType(address.getAddressType());
                addressEntity.setUserId(user.getId());
                addressess.add(addressEntity);
            });
            addressRepository.saveAll(addressess);
            log.info("Save address");
        }
        return user.getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(UserUpdateRequest req) {
        UserEntity user = getUser(req.getId());
        user.setFirstName(req.getFirstName());
        user.setLastName(req.getLastName());
        user.setGender(req.getGender());
        user.setBirthday(req.getBirthday());
        user.setEmail(req.getEmail());
        user.setPhone(req.getPhone());
        user.setUsername(req.getUsername());

        userRepository.save(user);
        log.info("Updated user: {}", user);

        // save address
        List<AddressEntity> addresses = new ArrayList<>();

        req.getAddresses().forEach(address -> {
            AddressEntity addressEntity = addressRepository.findByUserIdAndAddressType(user.getId(),
                    address.getAddressType());
            if (addressEntity == null) {
                addressEntity = new AddressEntity();
            }
            addressEntity.setApartmentNumber(address.getApartmentNumber());
            addressEntity.setFloor(address.getFloor());
            addressEntity.setBuilding(address.getBuilding());
            addressEntity.setStreetNumber(address.getStreetNumber());
            addressEntity.setStreet(address.getStreet());
            addressEntity.setCity(address.getCity());
            addressEntity.setCountry(address.getCountry());
            addressEntity.setAddressType(address.getAddressType());
            addressEntity.setUserId(user.getId());

            addresses.add(addressEntity);
        });

        // save addresses
        addressRepository.saveAll(addresses);
        log.info("Updated addresses: {}", addresses);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void changePassword(UserPasswordRequest req) {
        UserEntity user = getUser(req.getId());
        if (req.getPassword().equals(req.getConfirmPassword())) {
            user.setPassword(passwordEncoder.encode(req.getPassword()));
        }

    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteUser(Long id) {
        UserEntity user = getUser(id);
        user.setStatus(UserStatus.INACTIVE);
        userRepository.save(user);
    }

    public UserEntity getUser(long id) {
        return userRepository.findById(id).orElseThrow(() -> {
            return new ResourceNotFoundExcepton("Khong tim thay user");
        });
    }
}
