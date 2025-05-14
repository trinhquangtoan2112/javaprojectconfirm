package vn.toan.testfullstep.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import vn.toan.testfullstep.common.Gender;
import vn.toan.testfullstep.common.UserStatus;
import vn.toan.testfullstep.common.UserType;

import java.io.Serializable;
import java.util.*;

@Slf4j(topic = "UserEntity")
@Getter
@Setter
@Entity(name = "User")
@Table(name = "tbl_user")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserEntity extends AbstractEntity<Long> implements Serializable {

    @Column(name = "first_name", length = 255)
    String firstName;

    @Column(name = "last_name", length = 255)
    String lastName;

    @Enumerated(EnumType.STRING)
    @JdbcTypeCode(SqlTypes.NAMED_ENUM)
    @Column(name = "gender")
    Gender gender;

    @Column(name = "date_of_birth")
    @Temporal(TemporalType.DATE)
    Date birthday;

    @Column(name = "email", length = 255)
    String email;

    @Column(name = "phone", length = 15)
    String phone;

    @Column(name = "username", length = 15)
    String username;

    @Column(name = "password", length = 255)
    String password;

    @Enumerated(EnumType.STRING)
    @JdbcTypeCode(SqlTypes.NAMED_ENUM)
    UserType type;

    @Enumerated(EnumType.STRING)
    @JdbcTypeCode(SqlTypes.NAMED_ENUM)
    UserStatus status;

    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JsonIgnore
    transient Set<UserHasRole> roles = new HashSet<>();

    @OneToMany(mappedBy = "user")

    transient Set<GroupHasUser> groups = new HashSet<>();

}
