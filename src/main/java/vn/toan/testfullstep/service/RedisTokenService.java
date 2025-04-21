package vn.toan.testfullstep.service;

import org.springframework.stereotype.Service;

import vn.toan.testfullstep.model.RedisToken;
import vn.toan.testfullstep.repository.RedisTokenRepository;

@Service
public record RedisTokenService(RedisTokenRepository redisTokenRepository) {

    public String saveToken(RedisToken token) {

        RedisToken redisToken = redisTokenRepository.save(token);
        return redisToken.getId();
    }

    public String deleteToken(String id) {
        redisTokenRepository.deleteById(id);
        return "Delete";
    }
}
