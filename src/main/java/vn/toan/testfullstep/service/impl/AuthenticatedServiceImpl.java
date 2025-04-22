package vn.toan.testfullstep.service.impl;

import lombok.RequiredArgsConstructor;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationManager;
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
import java.util.stream.Collectors;

import io.micrometer.common.util.StringUtils;
import jakarta.servlet.http.HttpServletRequest;
import vn.toan.testfullstep.common.TokenType;
import vn.toan.testfullstep.excepton.InvalidDataException;
import vn.toan.testfullstep.model.RedisToken;
import vn.toan.testfullstep.model.UserEntity;
import vn.toan.testfullstep.service.RedisTokenService;
import vn.toan.testfullstep.service.TokenService;

@Service
@Slf4j(topic = "Authentication IMPL")
@RequiredArgsConstructor
public class AuthenticatedServiceImpl implements AuthenticatedService {

    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final TokenService tokenService;
    private final RedisTokenService redisTokenService;

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
        String refreshToken = jwtService.generateRefreshToken(user.getId(), signInRequest.getEmail(), authorties);

        RedisToken token = new RedisToken();
        token.setAccessToken(accessToken);
        token.setRefreshToken(refreshToken);
        redisTokenService.saveToken(token);
        return TokenResponse.builder().accessToken(accessToken).refreshToken(refreshToken).build();

    }

    @Override
    public TokenResponse getRefreshToken(HttpServletRequest request) {
        log.info("Get refresh token");
        String token = request.getHeader("RefresheToken");
        if (StringUtils.isBlank(token)) {
            throw new InvalidDataException("Không chứa refreshToken");
        }
        final String email = jwtService.extractUsername(token, TokenType.REFRESH_TOKEN);
        UserEntity userEntity = userRepository.findByEmail(email);
        if (!jwtService.isValid(token, userEntity, TokenType.REFRESH_TOKEN)) {
            throw new InvalidDataException("JWT INVALID");
        }
        List<String> authorities = getAuthoriy(userEntity);

        String accessToken = jwtService.generateAccessToken(userEntity.getId(), userEntity.getEmail(), authorities);
        RedisToken saveToken = new RedisToken();
        saveToken.setAccessToken(accessToken);
        saveToken.setRefreshToken(token);

        redisTokenService.saveToken(saveToken);

        return TokenResponse.builder().accessToken(accessToken).build();
    }

    private List<String> getAuthoriy(UserEntity user) {
        return user.getRoles().stream()
                .map(role -> role.getRole().getName())
                .collect(Collectors.toList());
    }

    @Override
    public String logout(HttpServletRequest request) {
        String token = request.getHeader("token");
        if (StringUtils.isBlank(token)) {
            throw new InvalidDataException("Token must not be blank");
        }
        final String email = jwtService.extractUsername(token, TokenType.REFRESH_TOKEN);
        tokenService.delete(email);
        return "Delete";
    }

    @Override
    public String forgotPassword(String email) {
        //    check email
        //check user active or inactive
        //create token
        //send email
        return "Success";
    }

    @Override
    public String resetPassword(String secretKey) {

        if (StringUtils.isBlank(secretKey)) {
            throw new InvalidDataException("Không chứa refreshToken");
        }
        final String email = jwtService.extractUsername(secretKey, TokenType.RESET_TOKEN);
        UserEntity userEntity = userRepository.findByEmail(email);
        if (!jwtService.isValid(secretKey, userEntity, TokenType.REFRESH_TOKEN)) {
            throw new InvalidDataException("JWT INVALID");
        }
        throw new UnsupportedOperationException("Unimplemented method 'resetPassword'");
    }
}
