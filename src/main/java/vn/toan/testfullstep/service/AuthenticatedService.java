package vn.toan.testfullstep.service;

import jakarta.servlet.http.HttpServletRequest;
import vn.toan.testfullstep.controller.request.SignInRequest;
import vn.toan.testfullstep.controller.response.TokenResponse;

public interface AuthenticatedService {

    TokenResponse getAccessToken(SignInRequest signInRequest);

    TokenResponse getRefreshToken(HttpServletRequest request);

    String logout(HttpServletRequest request);

    String forgotPassword(String email);

    String resetPassword(String secretKey);
}
