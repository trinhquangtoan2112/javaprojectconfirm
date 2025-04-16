package vn.toan.testfullstep.controller.request;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;
import vn.toan.testfullstep.common.Gender;

@Getter
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserUpdateRequest implements Serializable {
    Long id;
    String firstName;
    String lastName;
    Gender gender;
    Date birthday;
    String username;
    String email;
    String phone;
    List<AddressRequest> addresses;
}
