package vn.toan.testfullstep.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import vn.toan.testfullstep.repository.UserRepository;

@Service
public record UserServiceDetail(UserRepository userRepository) {

    public UserDetailsService userServiceDetail() {

        return userRepository::findByEmail;
    }
}
