package vn.toan.testfullstep.controller.request;

import java.io.Serializable;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AddressRequest implements Serializable {

    String apartmentNumber;
    String floor;
    String building;
    String streetNumber;
    String street;
    String city;
    String country;
    Integer addressType;

}
