package vn.toan.testfullstep.controller.response;

import java.io.Serializable;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TokenResponse implements Serializable {

    String accessToken;
    String refreshToken;
    Long userId;

}
