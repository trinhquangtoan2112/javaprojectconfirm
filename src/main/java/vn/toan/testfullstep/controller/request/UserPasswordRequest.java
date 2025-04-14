package vn.toan.testfullstep.controller.request;

import java.io.Serializable;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserPasswordRequest implements Serializable {
    Long id;
    String password;
    String confirmPassword;
}
