package vn.toan.testfullstep.service.impl;

import lombok.RequiredArgsConstructor;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import vn.toan.testfullstep.service.AuthenticatedService;
import vn.toan.testfullstep.service.JwtService;
import vn.toan.testfullstep.controller.request.SignInRequest;
import vn.toan.testfullstep.controller.response.TokenResponse;
import vn.toan.testfullstep.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;

import vn.toan.testfullstep.model.UserEntity;

@Service
@Slf4j(topic = "Authentication IMPL")
@RequiredArgsConstructor
public class AuthenticatedServiceImpl implements AuthenticatedService {

    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    @Override
    public TokenResponse getAccessToken(SignInRequest signInRequest) {
        log.info("Get access token");
        List<String> authorties = new ArrayList<>();

        try {

            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(signInRequest.getEmail(), signInRequest.getPassword()));
            log.info("isAuthenticated = {}", authentication.isAuthenticated());
            log.info("Authorities: {}", authentication.getAuthorities().toString());
            authorties.add(authentication.getAuthorities().toString());
            SecurityContextHolder.getContext().setAuthentication(authentication);
        } catch (Exception ex) {

            log.error("test authentication", ex.getMessage());
            throw new AccessDeniedException(ex.getMessage());
        }
        var user = userRepository.findByEmail(signInRequest.getEmail());
        String accessToken = jwtService.generateAccessToken(user.getId(), signInRequest.getEmail(), authorties);
        String refreshToken = jwtService.generateAccessToken(user.getId(), signInRequest.getEmail(), authorties);
        return TokenResponse.builder().accessToken(accessToken).refreshToken(refreshToken).build();

    }

    @Override
    public TokenResponse getRefreshToken(String request) {

        throw new UnsupportedOperationException("Unimplemented method 'getRefreshToken'");
    }

}
