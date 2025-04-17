package vn.toan.testfullstep.Service;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import vn.toan.testfullstep.Repository.UserRepository;

@Service

public record UserServiceDetail(UserRepository userRepository) {

    public UserDetailsService userServiceDetail() {
        String username = "sdassd";
        return userRepository::findByUsername;
    }
}
