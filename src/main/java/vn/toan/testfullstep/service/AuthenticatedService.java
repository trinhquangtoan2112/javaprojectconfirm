package vn.toan.testfullstep.Service;

import vn.toan.testfullstep.controller.request.SignInRequest;
import vn.toan.testfullstep.controller.response.TokenResponse;

public interface AuthenticatedService {
    TokenResponse getAccessToken(SignInRequest signInRequest);

    TokenResponse getRefreshToken(String request);

}
