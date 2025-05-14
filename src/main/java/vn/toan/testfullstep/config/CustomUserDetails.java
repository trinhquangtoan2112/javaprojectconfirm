package vn.toan.testfullstep.config;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import vn.toan.testfullstep.common.UserStatus;
import vn.toan.testfullstep.model.Role;
import vn.toan.testfullstep.model.UserHasRole;

@Getter
@Setter
@Builder
@Slf4j
public class CustomUserDetails implements UserDetails, Serializable {

    private Long id;
    private String email;
    private String username;
    private String password;
    private UserStatus status;
    private transient Set<UserHasRole> roles = new HashSet<>();

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<Role> roleList = roles.stream().map(UserHasRole::getRole).toList();

        // Get role name
        List<String> roleNames = roleList.stream().map(Role::getName).toList();

        log.info("User roles: {}", roleNames);
        log.info("Test1421424 {}", roleNames);
        log.info("Authoried {}", roleNames.stream().map(s -> new SimpleGrantedAuthority("ROLE_" + s.toUpperCase())).toList());
        // NOSONAR return roleNames.stream().map(s-> new SimpleGrantedAuthority("ROLE_"+s.toUpperCase())).toList();

        return roleNames.stream().map(SimpleGrantedAuthority::new).toList();
    }

    @Override
    public String getPassword() {
        return password;
    } // Không cần mật khẩu

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return UserStatus.ACTIVE.equals(status);
    }
}
