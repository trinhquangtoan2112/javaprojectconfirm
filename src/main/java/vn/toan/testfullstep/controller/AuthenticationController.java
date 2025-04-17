package vn.toan.testfullstep.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ch.qos.logback.core.subst.Token;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import vn.toan.testfullstep.controller.request.SignInRequest;
import vn.toan.testfullstep.controller.response.TokenResponse;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/auth")
@Slf4j(topic = "AUTHENTICATION-Controler")
@Tag(name = "Auth controller")
public class AuthenticationController {

    @PostMapping("/access-token")
    @Operation(summary = "Access token", description = "Get access token and refresh token by username and password")
    public TokenResponse getAccessToken(@RequestBody SignInRequest signIn) {
        log.info("Access token request");
        return TokenResponse.builder().accessToken("22421412412412").refreshToken("422212").build();
    }

    @PostMapping("/refresh-access-token")
    @Operation(summary = "Refresh Access token", description = "Get access token by using refresh token")
    public TokenResponse getRefreshAccessToken(@RequestBody String refreshToken) {
        log.info("Request token request");
        return TokenResponse.builder().accessToken("22421412412412").refreshToken("422212").build();
    }
}
