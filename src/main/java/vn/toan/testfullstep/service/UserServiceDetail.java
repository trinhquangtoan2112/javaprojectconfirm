package vn.toan.testfullstep.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import java.util.Collections;
import org.springframework.stereotype.Service;

import vn.toan.testfullstep.config.CustomUserDetails;
import vn.toan.testfullstep.model.UserEntity;
import vn.toan.testfullstep.repository.UserRepository;

@Service
public record UserServiceDetail(UserRepository userRepository) {

    public CustomUserDetails userServiceDetail(String email) {
    

    public UserDetailsService userServiceDetail(String email) {
        UserEntity user = userRepository.findByEmail(email);

        return new CustomUserDetails(user.getId(), user.getEmail(), user.getUsername(), user.getAuthorities() != null ? user.getAuthorities() : Collections.emptyList()
        );
    }
}
