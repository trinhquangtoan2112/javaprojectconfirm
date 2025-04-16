package vn.toan.testfullstep.controller.request;

import java.util.Date;
import java.util.List;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;
import vn.toan.testfullstep.common.Gender;
import vn.toan.testfullstep.common.UserType;

@Getter
@ToString

@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserCreationRequest {
    String firstName;
    String lastName;
    Gender gender;
    Date birthday;
    String username;
    String email;
    String phone;
    UserType type;
    List<AddressRequest> addresses; // home,office
}
