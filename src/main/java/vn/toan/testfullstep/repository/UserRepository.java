package vn.toan.testfullstep.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import vn.toan.testfullstep.controller.response.UserResponse;
import vn.toan.testfullstep.model.UserEntity;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity,Long> {

}
