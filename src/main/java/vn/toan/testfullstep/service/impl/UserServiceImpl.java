package vn.toan.testfullstep.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.toan.testfullstep.common.UserStatus;
import vn.toan.testfullstep.common.UserType;
import vn.toan.testfullstep.controller.request.UserCreationRequest;
import vn.toan.testfullstep.controller.request.UserPasswordRequest;
import vn.toan.testfullstep.controller.request.UserUpdateRequest;
import vn.toan.testfullstep.controller.response.UserResponse;
import vn.toan.testfullstep.excepton.ResourceNotFoundExcepton;
import vn.toan.testfullstep.model.AddressEntity;
import vn.toan.testfullstep.model.UserEntity;
import vn.toan.testfullstep.repository.AddressRepository;
import vn.toan.testfullstep.repository.UserRepository;
import vn.toan.testfullstep.service.UserService;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j(topic ="USER-SERVICE")
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final AddressRepository addressRepository;
    private final PasswordEncoder passwordEncoder;
    @Override
    public List<UserResponse> findAll() {
        return List.of();
    }

    @Override
    public UserResponse findById(Long id) {
        return null;
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
        if(user.getId()!=null){
            List<AddressEntity> addressess = new ArrayList<>();
            req.getAddresses().forEach(address ->{
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
     UserEntity user =getUser(req.getId());
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
            AddressEntity addressEntity = addressRepository.findByUserIdAndAddressType(user.getId(), address.getAddressType());
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
        UserEntity  user= getUser(req.getId());
        if(req.getPassword().equals(req.getConfirmPassword())){
           user.setPassword( passwordEncoder.encode(req.getPassword()));
        }

    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteUser(Long id) {
        UserEntity  user= getUser(id);
        user.setStatus(UserStatus.INACTIVE);
        userRepository.save(user);
    }


    public  UserEntity getUser (long id){
      return  userRepository.findById(id).orElseThrow(() ->{
         return new ResourceNotFoundExcepton("Khong tim thay user");
      });
    }
}
