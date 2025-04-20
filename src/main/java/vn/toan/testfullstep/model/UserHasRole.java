package vn.toan.testfullstep.model;


import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;
import lombok.*;
@Setter
@Getter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "tbl_user_has_role")
public class UserHasRole   extends AbstractEntity<Integer>{
    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private UserEntity user;

    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;
}
