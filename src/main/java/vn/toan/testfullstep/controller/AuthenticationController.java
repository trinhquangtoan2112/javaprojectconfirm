package vn.toan.testfullstep.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ch.qos.logback.core.subst.Token;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import vn.toan.testfullstep.controller.request.SignInRequest;
import vn.toan.testfullstep.controller.response.TokenResponse;
import vn.toan.testfullstep.service.AuthenticatedService;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/auth")
@Slf4j(topic = "AUTHENTICATION-Controler")
@Tag(name = "Auth controller")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticatedService authenticatedService;

    @PostMapping("/access-token")
    @Operation(summary = "Access token", description = "Get access token and refresh token by username and password")
    public TokenResponse getAccessToken(@RequestBody SignInRequest signIn) {
        log.info("Access token");
        return authenticatedService.getAccessToken(signIn);
    }

    @PostMapping("/refresh-access-token")
    @Operation(summary = "Refresh Access token", description = "Get access token by using refresh token")
    public TokenResponse getRefreshAccessToken(HttpServletRequest request) {
        log.info("Request token request");

        return authenticatedService.getRefreshToken(request);
    }

    @PostMapping("/login")
    @Operation(summary = "Login account", description = "Login account")
    public String login(@RequestBody SignInRequest signIn) {
        return "";
    }

    @PostMapping("/logout")
    @Operation(summary = "Log out", description = "Log out account")
    public String logout(HttpServletRequest request) {
        return authenticatedService.logout(request);
    }

    @PostMapping("/forgot-password")
    @Operation(summary = "Forgot password", description = "Forgot password")
    public ResponseEntity<String> forgotPassword(@RequestParam String email) {
        return new ResponseEntity<>(authenticatedService.forgotPassword(email), HttpStatus.OK);
    }

    @PostMapping("/reset-password")
    @Operation(summary = "Reset password", description = "Reset password")
    public ResponseEntity<String> resetPassword(@RequestBody String secretKey) {
        return new ResponseEntity<>(authenticatedService.forgotPassword(secretKey), HttpStatus.OK);
    }
}
