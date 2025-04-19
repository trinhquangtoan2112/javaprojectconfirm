package vn.toan.testfullstep.model;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.type.SqlTypes;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import vn.toan.testfullstep.common.Gender;
import vn.toan.testfullstep.common.UserStatus;
import vn.toan.testfullstep.common.UserType;

import java.io.Serializable;
import java.util.*;

@Slf4j
@Getter
@Setter
@Entity(name = "User")
@Table(name = "tbl_user")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserEntity extends AbstractEntity<Long> implements UserDetails, Serializable {

    @Column(name = "first_name", length = 255)
    String firstName;

    @Column(name = "last_name", length = 255)
    private String lastName;

    @Enumerated(EnumType.STRING)
    @JdbcTypeCode(SqlTypes.NAMED_ENUM)
    @Column(name = "gender")
    private Gender gender;

    @Column(name = "date_of_birth")
    @Temporal(TemporalType.DATE)
    private Date birthday;

    @Column(name = "email", length = 255)
    private String email;

    @Column(name = "phone", length = 15)
    private String phone;

    @Column(name = "username", length = 15)
    private String username;

    @Column(name = "password", length = 255)
    private String password;

    @Enumerated(EnumType.STRING)
    @JdbcTypeCode(SqlTypes.NAMED_ENUM)
    private UserType type;

    @Enumerated(EnumType.STRING)
    @JdbcTypeCode(SqlTypes.NAMED_ENUM)
    private UserStatus status;


    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<UserHasRole> roles = new HashSet<>();

    @OneToMany(mappedBy = "user")
    private Set<GroupHasUser> groups = new HashSet<>();
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<Role> roleList = roles.stream().map(UserHasRole::getRole).toList();

        // Get role name
        List<String> roleNames = roleList.stream().map(Role::getName).toList();
        log.info("User roles: {}", roleNames);

//        return roleNames.stream().map(SimpleGrantedAuthority::new).toList();
        return roleNames.stream().map(s-> new SimpleGrantedAuthority("ROLE_"+s.toUpperCase())).toList();
    }

    @Override
    public boolean isAccountNonExpired() {
        return UserDetails.super.isAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return UserDetails.super.isAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return UserDetails.super.isCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled() {
        return UserStatus.ACTIVE.equals(status);
    }
}
