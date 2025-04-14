package vn.toan.testfullstep.controller.request;

import java.util.Date;
import java.util.List;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;

@Getter
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserCreationRequest {
    String firstName;
    String lastName;
    String gender;
    Date birthday;
    String username;
    String email;
    String phone;
    String type;
    List<AddressRequest> addresses; // home,office
}
