package vn.toan.testfullstep.Service.impl;

import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import vn.toan.testfullstep.Service.AuthenticatedService;
import vn.toan.testfullstep.controller.request.SignInRequest;
import vn.toan.testfullstep.controller.response.TokenResponse;

@Service
@Slf4j
public class AuthenticatedServiceImpl implements AuthenticatedService {

    @Override
    public TokenResponse getAccessToken(SignInRequest signInRequest) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getAccessToken'");
    }

    @Override
    public TokenResponse getRefreshToken(String request) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getRefreshToken'");
    }

}
