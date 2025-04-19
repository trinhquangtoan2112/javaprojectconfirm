package vn.toan.testfullstep.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Setter
@Getter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "tbl_role")
public class Role extends AbstractEntity<Integer>{
    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

//    @OneToMany(mappedBy = "user")
//    private Set<User> users;

    @OneToMany(mappedBy = "role")
    private Set<RoleHasPermission> roles = new HashSet<>();
}
