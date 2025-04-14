package vn.toan.testfullstep.controller.response;

import java.io.Serializable;
import java.util.Date;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserResponse implements Serializable {
    Long id;
    String firstName;
    String lastName;
    String gender;
    Date birthday;
    String username;
    String email;
    String phone;

}
