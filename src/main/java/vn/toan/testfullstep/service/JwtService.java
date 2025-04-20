package vn.toan.testfullstep.service;

import java.util.List;


import org.springframework.security.core.userdetails.UserDetails;
import vn.toan.testfullstep.common.TokenType;

public interface JwtService {

    String generateAccessToken(long userId, String username, List<String> authorities);

    String generateRefreshToken(long userId, String username, List<String> authorities);

    String extractUsername(String token, TokenType tokenType);
    Boolean isValid(String token, UserDetails user,TokenType tokenType);
}
