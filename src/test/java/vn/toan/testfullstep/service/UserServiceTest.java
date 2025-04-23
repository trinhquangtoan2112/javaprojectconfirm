package vn.toan.testfullstep.service;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;

import lombok.RequiredArgsConstructor;
import vn.toan.testfullstep.common.Gender;
import vn.toan.testfullstep.common.UserStatus;
import vn.toan.testfullstep.common.UserType;
import vn.toan.testfullstep.controller.request.AddressRequest;
import vn.toan.testfullstep.controller.request.UserCreationRequest;
import vn.toan.testfullstep.controller.request.UserUpdateRequest;
import vn.toan.testfullstep.controller.response.UserPageResponse;
import vn.toan.testfullstep.controller.response.UserResponse;
import vn.toan.testfullstep.excepton.ResourceNotFoundExcepton;
import vn.toan.testfullstep.model.UserEntity;
import vn.toan.testfullstep.repository.AddressRepository;
import vn.toan.testfullstep.repository.UserRepository;
import vn.toan.testfullstep.service.impl.UserServiceImpl;
import vn.toan.testfullstep.service.UserService;

@ExtendWith(MockitoExtension.class)
@RequiredArgsConstructor
class UserServiceTest {

    private UserService userService;
    private @Mock
    UserRepository userRepository;
    private @Mock
    AddressRepository addressRepository;
    private @Mock
    PasswordEncoder passwordEncoder;
    private @Mock
    ModelMapper modelMapper;
    private static UserEntity user1;
    private static UserEntity user2;

    @BeforeAll
    static void beforeAll() {
        user1 = new UserEntity();
        user1.setId(1L);
        user1.setFirstName("Tay");
        user1.setLastName("Java");
        user1.setGender(Gender.MALE);
        user1.setBirthday(new Date());
        user1.setEmail("quoctay87@gmail.com");
        user1.setPhone("0975118228");
        user1.setUsername("tayjava");
        user1.setPassword("password");
        user1.setType(UserType.USER);
        user1.setStatus(UserStatus.ACTIVE);

        user2 = new UserEntity();
        user2.setId(2L);
        user2.setFirstName("John");
        user2.setLastName("Doe");
        user2.setGender(Gender.FEMALE);
        user2.setBirthday(new Date());
        user2.setEmail("johndoe@gmail.com");
        user2.setPhone("0123456789");
        user2.setUsername("johndoe");
        user2.setPassword("password");
        user2.setType(UserType.USER);
        user2.setStatus(UserStatus.INACTIVE);
    }

    @BeforeEach
    void setUp() {
        userService = new UserServiceImpl(userRepository, addressRepository, passwordEncoder, modelMapper);
    }

    @AfterEach
    void tearDown() {

    }

    @Test
    void testGetListUser_Success() {
        Page<UserEntity> userPage = new PageImpl<>(Arrays.asList(user1, user2));
        when(userRepository.findAll(any(Pageable.class))).thenReturn(userPage);

        UserPageResponse userPageResponse = userService.findAll(null, null, 0, 20);
        assertNotNull(userPageResponse);

        assertEquals(2, userPageResponse.getTotalElements());
    }

    void testSearch_Success() {
        Page<UserEntity> userPage = new PageImpl<>(Arrays.asList(user1, user2));
        when(userRepository.searchByKeyWord(any(), any(Pageable.class))).thenReturn(userPage);

        UserPageResponse userPageResponse = userService.findAll("tayjava", null, 0, 20);
        assertNotNull(userPageResponse);

        assertEquals(1, userPageResponse.getTotalElements());
    }

    @Test
    void testGetListUser_Fail() {
        Page<UserEntity> userPage = new PageImpl<>(Arrays.asList(user1, user2));
        when(userRepository.findAll(any(Pageable.class))).thenReturn(userPage);

        UserPageResponse userPageResponse = userService.findAll(null, null, 0, 20);
        assertNotNull(userPageResponse);

        assertNotEquals(1, userPageResponse.getTotalElements());
    }

    @Test
    void testGetListUser_Null() {
        Page<UserEntity> userPage = new PageImpl<>(List.of());
        when(userRepository.findAll(any(Pageable.class))).thenReturn(userPage);

        UserPageResponse userPageResponse = userService.findAll(null, null, 0, 20);
        assertNotNull(userPageResponse);

        assertEquals(0, userPageResponse.getTotalElements());
    }

    @Test
    void testGetUserById() {
        when(userRepository.findById(1l)).thenReturn(Optional.of(user1));
        UserEntity user = userService.findById(1l);

        assertNotNull(user);
        assertEquals(1l, user.getId());
    }

    @Test
    void testGetUserById_Fail() {
        ResourceNotFoundExcepton resourceNotFoundExcepton = assertThrows(ResourceNotFoundExcepton.class, () -> userService.findById(2l));
        assertEquals("User Not Found", resourceNotFoundExcepton.getMessage());

    }

    @Test
    void findByUsername() {
    }

    @Test
    void testUserSuccess() {
        when(userRepository.save(any(UserEntity.class))).thenAnswer(invocation -> {
            UserEntity savedUser = invocation.getArgument(0); // Access the `user` object passed to `save()`
            savedUser.setId(1L); // Simulate assigning an ID
            return savedUser; // Return the updated `user` object
        });

        UserCreationRequest userCreationRequest = new UserCreationRequest();
        userCreationRequest.setFirstName("Tay");
        userCreationRequest.setLastName("Java");
        userCreationRequest.setGender(Gender.MALE);
        userCreationRequest.setBirthday(new Date());
        userCreationRequest.setEmail("quoctay87@gmail.com");
        userCreationRequest.setPhone("0975118228");
        userCreationRequest.setUsername("tayjava");
        userCreationRequest.setType(UserType.USER);
        AddressRequest addressRequest = new AddressRequest();
        addressRequest.setApartmentNumber("ApartmentNumber");
        addressRequest.setFloor("Floor");
        addressRequest.setBuilding("Building");
        addressRequest.setStreetNumber("StreetNumber");
        addressRequest.setStreet("Street");
        addressRequest.setCity("City");
        addressRequest.setCountry("Country");
        addressRequest.setAddressType(1);
        userCreationRequest.setAddresses(List.of(addressRequest));

        long result = userService.saveUser(userCreationRequest);

        assertNotNull(result);
        assertEquals(1L, result);
    }

    @Test
    void testUser_Fail() {
        when(userRepository.save(any(UserEntity.class))).thenAnswer(invocation -> {
            UserEntity savedUser = invocation.getArgument(0); // Access the `user` object passed to `save()`
            savedUser.setId(2l); // Simulate assigning an ID
            return savedUser; // Return the updated `user` object
        });

        UserCreationRequest userCreationRequest = new UserCreationRequest();
        userCreationRequest.setFirstName("Tay");
        userCreationRequest.setLastName("Java");
        userCreationRequest.setGender(Gender.MALE);
        userCreationRequest.setBirthday(new Date());
        userCreationRequest.setEmail("quoctay87@gmail.com");
        userCreationRequest.setPhone("0975118228");
        userCreationRequest.setUsername("tayjava");
        userCreationRequest.setType(UserType.USER);
        AddressRequest addressRequest = new AddressRequest();
        addressRequest.setApartmentNumber("ApartmentNumber");
        addressRequest.setFloor("Floor");
        addressRequest.setBuilding("Building");
        addressRequest.setStreetNumber("StreetNumber");
        addressRequest.setStreet("Street");
        addressRequest.setCity("City");
        addressRequest.setCountry("Country");
        addressRequest.setAddressType(1);
        userCreationRequest.setAddresses(List.of(addressRequest));

        long result = userService.saveUser(userCreationRequest);

        assertNotNull(result);
        assertNotEquals(1L, result);
    }

    @Test
    void testUpdate_Success() {
        Long userId = 2L;

        UserEntity updatedUser = new UserEntity();
        updatedUser.setId(userId);
        updatedUser.setFirstName("Jane");
        updatedUser.setLastName("Smith");
        updatedUser.setGender(Gender.FEMALE);
        updatedUser.setBirthday(new Date());
        updatedUser.setEmail("janesmith@gmail.com");
        updatedUser.setPhone("0123456789");
        updatedUser.setUsername("janesmith");
        updatedUser.setType(UserType.USER);
        updatedUser.setStatus(UserStatus.ACTIVE);

        // Giả lập hành vi của UserRepository
        when(userRepository.findById(userId)).thenReturn(Optional.of(user2));
        when(userRepository.save(any(UserEntity.class))).thenAnswer(invocation -> {
            UserEntity savedUser = invocation.getArgument(0); // Access the `user` object passed to `save()`
            savedUser.setId(2l); // Simulate assigning an ID
            return savedUser; // Return the updated `user` object
        });

        UserUpdateRequest updateRequest = new UserUpdateRequest();
        updateRequest.setId(userId);
        updateRequest.setFirstName("Jane");
        updateRequest.setLastName("Smith");
        updateRequest.setGender(Gender.MALE);
        updateRequest.setBirthday(new Date());
        updateRequest.setEmail("janesmith@gmail.com");
        updateRequest.setPhone("0123456789");
        updateRequest.setUsername("janesmith");

        AddressRequest addressRequest = new AddressRequest();
        addressRequest.setApartmentNumber("ApartmentNumber");
        addressRequest.setFloor("Floor");
        addressRequest.setBuilding("Building");
        addressRequest.setStreetNumber("StreetNumber");
        addressRequest.setStreet("Street");
        addressRequest.setCity("City");
        addressRequest.setCountry("Country");
        addressRequest.setAddressType(1);
        updateRequest.setAddresses(List.of(addressRequest));

        // Gọi phương thức cần kiểm tra
        userService.update(updateRequest);

        UserEntity result = userService.findById(userId);

        assertEquals("janesmith", result.getUsername());
        assertEquals("janesmith@gmail.com", result.getEmail());
    }

    @Test
    void changePassword() {
    }

    @Test
    void deleteUser() {
    }
}
