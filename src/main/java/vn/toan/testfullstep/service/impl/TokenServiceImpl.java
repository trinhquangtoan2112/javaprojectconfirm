package vn.toan.testfullstep.service.impl;

import java.util.Optional;

import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import vn.toan.testfullstep.excepton.ResourceNotFoundExcepton;
import vn.toan.testfullstep.model.Token;
import vn.toan.testfullstep.repository.TokenRepository;
import vn.toan.testfullstep.service.TokenService;

@Service
public record TokenServiceImpl(TokenRepository tokenRepository) implements TokenService {

    @Override
    public Integer saveToken(Token token) {

        Optional<Token> tokenData = getByUsername(token.getEmail());
        Token currentToken;
        if (tokenData.isEmpty()) {
            currentToken = tokenRepository.save(token);

        } else {
            currentToken = tokenData.get();
            currentToken.setAccessToken(token.getAccessToken());
            currentToken.setRefreshToken(token.getRefreshToken());
            tokenRepository.save(currentToken);
        }
        return currentToken.getId();
    }

    @Override
    public String delete(String email) {
        Optional<Token> tokenData = tokenRepository.findByEmail(email);
        Token currentToken;
        if (tokenData.isEmpty()) {
            throw new ResourceNotFoundExcepton(email + " khoong ton tai");

        } else {
            currentToken = tokenData.get();
            currentToken.setAccessToken("");
            currentToken.setRefreshToken("");
            tokenRepository.delete(currentToken);

        }

        return "delete";
    }

    public Optional<Token> getByUsername(String email) {
        return tokenRepository.findByEmail(email);
    }

}
