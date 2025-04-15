package vn.toan.testfullstep.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import vn.toan.testfullstep.controller.request.UserCreationRequest;
import vn.toan.testfullstep.controller.request.UserPasswordRequest;
import vn.toan.testfullstep.controller.request.UserUpdateRequest;
import vn.toan.testfullstep.controller.response.UserResponse;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import vn.toan.testfullstep.service.UserService;

@RestController
@RequestMapping("/user")
@Tag(name = "User controller")
@AllArgsConstructor
public class UserController {
    private final UserService userService;
    @Operation(summary = "TEST API", description = "Mo ta chi tiet")
    @GetMapping("/list")
    public Map<String, Object> getListUser(@RequestParam(required = false) String name,
            @RequestParam(defaultValue = "0") int pageNum,
            @RequestParam(defaultValue = "20") int pageSize) {
        UserResponse userResponse1 = new UserResponse();

        userResponse1.setId(1L);
        userResponse1.setFirstName("Tay");
        userResponse1.setLastName("Java");
        userResponse1.setGender(
                "Male");
        userResponse1.setBirthday(new Date());
        userResponse1.setUsername("admin");
        userResponse1.setEmail("admin@gmail.com");
        userResponse1.setPhone("0975118228");

        UserResponse userResponse2 = new UserResponse();
        userResponse2.setId(2L);
        userResponse2.setFirstName("Leo");
        userResponse2.setLastName("Messi");
        userResponse2.setGender("Male");
        userResponse2.setBirthday(new Date());
        userResponse2.setUsername("user");
        userResponse2.setEmail("user@gmail.com");
        userResponse2.setPhone("0971234567");

        List<UserResponse> userList = List.of(userResponse1, userResponse2);

        Map<String, Object> result = new LinkedHashMap<>();
        result.put("status", HttpStatus.OK.value());
        result.put("message", "Lấy dữ liệu thành công");
        result.put("data", userList);
        return result;

    }

    @Operation(summary = "Get user detail", description = "API retrieve user detail by ID from database")
    @GetMapping("/{userId}")
    public Map<String, Object> getUserDetail(@PathVariable Long userId) {

        UserResponse userResponse = new UserResponse();
        userResponse.setId(userId);
        userResponse.setFirstName("Tay");
        userResponse.setLastName("Java");
        userResponse.setGender("Male");
        userResponse.setBirthday(new Date());
        userResponse.setUsername("admin");
        userResponse.setEmail("admin@gmail.com");
        userResponse.setPhone("0975118228");

        Map<String, Object> result = new LinkedHashMap<>();
        result.put("status", HttpStatus.OK.value());
        result.put("message", "user");
        result.put("data", userResponse);

        return result;
    }

    @Operation(summary = "Create User", description = "API add new user to database")
    @PostMapping("/add")
    public ResponseEntity<Object> createUser(@RequestBody UserCreationRequest request) {

        Map<String, Object> result = new LinkedHashMap<>();
        result.put("status", HttpStatus.CREATED.value());
        result.put("message", "User created successfully");
        result.put("data", userService.saveUser(request));



        return new ResponseEntity<>(result,HttpStatus.CREATED);
    }

    @Operation(summary = "Update User", description = "API update user to database")
    @PutMapping("/upd")
    public Map<String, Object> updateUser(@RequestBody UserUpdateRequest request) {
        userService.update(request);
        Map<String, Object> result = new LinkedHashMap<>();
        result.put("status", HttpStatus.ACCEPTED.value());
        result.put("message", "User updated successfully");
        result.put("data",request);

        return result;
    }

    @Operation(summary = "Change Password", description = "API change password for user to database")
    @PatchMapping("/change-pwd")
    public Map<String, Object> changePassword(UserPasswordRequest request) {
        userService.changePassword(request);
        Map<String, Object> result = new LinkedHashMap<>();
        result.put("status", HttpStatus.NO_CONTENT.value());
        result.put("message", "Password updated successfully");


        return result;
    }

    @Operation(summary = "Delete user", description = "API activate user from database")
    @DeleteMapping("/del/{userId}")
    public Map<String, Object> deleteUser(@PathVariable Long userId) {
userService.deleteUser(userId);
        Map<String, Object> result = new LinkedHashMap<>();
        result.put("status", HttpStatus.RESET_CONTENT.value());
        result.put("message", "User deleted successfully");
        result.put("data", "");

        return result;
    }


}
