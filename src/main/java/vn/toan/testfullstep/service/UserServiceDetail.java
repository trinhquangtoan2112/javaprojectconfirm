package vn.toan.testfullstep.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import org.springframework.stereotype.Service;

import vn.toan.testfullstep.config.CustomUserDetails;
import vn.toan.testfullstep.model.UserEntity;
import vn.toan.testfullstep.repository.UserRepository;

@Service
public record UserServiceDetail(UserRepository userRepository) implements UserDetailsService {

    public UserDetailsService userServiceDetail() {

        return UserRepository::findByEmail;

    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity user = userRepository.findByEmail(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found with email: " + username);
        }
        return new CustomUserDetails.builder()
                .id(user.getId())
                .email(user.getEmail())
                .username(user.getUsername())
                .password(user.getPassword())
                .status(user.getStatus())
                .roles(user.getRoles()) // Chưa có quyền
                .build();

    }
}
