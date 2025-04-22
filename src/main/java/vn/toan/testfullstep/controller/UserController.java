package vn.toan.testfullstep.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import vn.toan.testfullstep.controller.request.UserCreationRequest;
import vn.toan.testfullstep.controller.request.UserPasswordRequest;
import vn.toan.testfullstep.controller.request.UserUpdateRequest;
import vn.toan.testfullstep.controller.response.UserPageResponse;
import vn.toan.testfullstep.controller.response.UserResponse;
import vn.toan.testfullstep.service.UserService;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("/user")
@Tag(name = "User controller")
@AllArgsConstructor
@Slf4j
public class UserController {

    private final UserService userService;

    @Operation(summary = "TEST API", description = "Mo ta chi tiet")
    @GetMapping("/list")
//    @PreAuthorize("hasAnyAuthority('admin')")
    @PreAuthorize("hasAnyRole('SYSADMIN')")
    public Map<String, Object> getListUser(@RequestParam(required = false) String keyword,
            @RequestParam(required = false) String sort,
            @RequestParam(defaultValue = "0") int pageNum,
            @RequestParam(defaultValue = "10") int pageSize) {
        log.info("user");
        UserPageResponse listuser = userService.findAll(keyword, sort, pageNum, pageSize);

        Map<String, Object> result = new LinkedHashMap<>();
        result.put("status", HttpStatus.OK.value());
        result.put("message", "Lấy dữ liệu thành công");
        result.put("data", listuser);
        return result;

    }

    @Operation(summary = "Get user detail", description = "API retrieve user detail by ID from database")
    @GetMapping("/{userId}")
    @PreAuthorize("hasAnyAuthority('manager','admin')")
    // @PreAuthorize("hasAnyRole('MANAGER')")
    public Map<String, Object> getUserDetail(@PathVariable @Min(1) Long userId) {

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
        result.put("message", "Get user success");
        result.put("data", userService.findById(userId));

        return result;
    }

    @Operation(summary = "Create User", description = "API add new user to database")
    @PostMapping("/add")
    public ResponseEntity<Object> createUser(@RequestBody UserCreationRequest request) {

        Map<String, Object> result = new LinkedHashMap<>();
        result.put("status", HttpStatus.CREATED.value());
        result.put("message", "User created successfully");
        result.put("data", userService.saveUser(request));

        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    @Operation(summary = "Update User", description = "API update user to database")
    @PutMapping("/upd")
    public Map<String, Object> updateUser(@RequestBody UserUpdateRequest request) {
        userService.update(request);
        Map<String, Object> result = new LinkedHashMap<>();
        result.put("status", HttpStatus.ACCEPTED.value());
        result.put("message", "User updated successfully");
        result.put("data", request);

        return result;
    }

    @Operation(summary = "Change Password", description = "API change password for user to database")
    @PatchMapping("/change-pwd")
    public Map<String, Object> changePassword(@RequestBody @Valid UserPasswordRequest request) {
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
