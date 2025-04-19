package vn.toan.testfullstep.Service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import vn.toan.testfullstep.Service.AuthenticatedService;
import vn.toan.testfullstep.Service.JwtService;
import vn.toan.testfullstep.controller.request.SignInRequest;
import vn.toan.testfullstep.controller.response.TokenResponse;
import vn.toan.testfullstep.repository.UserRepository;
@Service
@Slf4j
@RequiredArgsConstructor
public class AuthenticatedServiceImpl implements AuthenticatedService {
     private final UserRepository  userRepository;
     private final AuthenticationManager authenticationManager;
     private final JwtService jwtService;
    @Override
    public TokenResponse getAccessToken(SignInRequest signInRequest) {
        log.info("Get access token");
try {
    Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(signInRequest.getEmail(),signInRequest.getPassword()));
    SecurityContextHolder.getContext().setAuthentication(authentication);
}catch(Exception ex){
    System.out.println("24242421");
   log.error("test authentication",ex.getMessage());
   throw new AccessDeniedException(ex.getMessage());
}
        var user =userRepository.findByEmail(signInRequest.getEmail());
        String accessToken = jwtService.generateAccessToken(user.getId(),signInRequest.getEmail(),user.getAuthorities());
        String refreshToken = jwtService.generateAccessToken(user.getId(),signInRequest.getEmail(),user.getAuthorities());
        return TokenResponse.builder().accessToken(accessToken).refreshToken(refreshToken).build();

    }

    @Override
    public TokenResponse getRefreshToken(String request) {

        throw new UnsupportedOperationException("Unimplemented method 'getRefreshToken'");
    }

}
