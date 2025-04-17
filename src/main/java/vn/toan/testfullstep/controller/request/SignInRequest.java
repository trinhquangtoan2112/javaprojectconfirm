package vn.toan.testfullstep.controller.request;

import java.io.Serializable;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SignInRequest implements Serializable {
    String email;
    String password;
    String platform;
    String deviceToken;
    String versionApp;
}
