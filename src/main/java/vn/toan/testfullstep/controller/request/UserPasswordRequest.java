package vn.toan.testfullstep.controller.request;

import java.io.Serializable;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserPasswordRequest implements Serializable {
    @NotNull(message = "Id must not be null")
    @Min(value = 1, message = "Id must be greater than 0")
    Long id;
    @NotBlank(message = "password must not be null")
    String password;
    @NotBlank(message = "confirm password must not be null")
    String confirmPassword;
}
