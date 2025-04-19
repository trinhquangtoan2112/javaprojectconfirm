package vn.toan.testfullstep.Service;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import vn.toan.testfullstep.model.UserEntity;
import vn.toan.testfullstep.repository.UserRepository;

@Service

public record UserServiceDetail(UserRepository userRepository) {

    public UserDetailsService userServiceDetail() {


        return userRepository::findByEmail;
    }
}
