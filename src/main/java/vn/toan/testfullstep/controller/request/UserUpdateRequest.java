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
    private Long id;
    private String firstName;
    private String lastName;
    private Gender gender;
    private Date birthday;
    private String username;
    private String email;
    private String phone;
    private List<AddressRequest> addresses;
}
