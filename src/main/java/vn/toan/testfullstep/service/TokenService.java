package vn.toan.testfullstep.service;

import vn.toan.testfullstep.model.Token;

public interface TokenService {

    Integer saveToken(Token token);

    String delete(String email);
}
