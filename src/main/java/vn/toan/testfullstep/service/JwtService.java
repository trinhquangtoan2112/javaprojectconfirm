package vn.toan.testfullstep.Service;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;

import vn.toan.testfullstep.common.TokenType;

public interface JwtService {

    String generateAccessToken(long userId, String username, List<String> authorities);

    String generateRefreshToken(long userId, String username, List<String> authorities);

    String extractUsername(String token, TokenType tokenType);
}
