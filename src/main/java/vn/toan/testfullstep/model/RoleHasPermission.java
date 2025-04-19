package vn.toan.testfullstep.model;


import jakarta.persistence.*;
import lombok.*;
@Setter
@Getter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "tbl_role_has_permission")
public class RoleHasPermission extends AbstractEntity<Integer>{
    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;

    @ManyToOne
    @JoinColumn(name = "permission_id")
    private Permission permission;
}
