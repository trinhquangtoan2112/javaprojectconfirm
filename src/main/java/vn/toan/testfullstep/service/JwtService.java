package vn.toan.testfullstep.Service;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;

import vn.toan.testfullstep.common.TokenType;

public interface JwtService {

    String generateToken(long userId, String username, Collection<? extends GrantedAuthority> authorities);

    String refreshToken(long userId, String username, Collection<? extends GrantedAuthority> authorities);

    String extractUsername(String token, TokenType tokenType);
}
