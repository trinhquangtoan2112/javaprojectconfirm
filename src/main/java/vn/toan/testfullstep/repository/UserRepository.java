package vn.toan.testfullstep.Repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import vn.toan.testfullstep.model.UserEntity;
import java.util.List;
import org.springframework.stereotype.Repository;
@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    @Query(value = "select u from User u where u.status='ACTIVE' " +
            "and (lower(u.firstName) like :keyword " +
            "or lower(u.lastName) like :keyword " +
            "or lower(u.username) like :keyword " +
            "or lower(u.phone) like :keyword " +
            "or lower(u.email) like :keyword)")
    Page<UserEntity> searchByKeyWord(String keyword, Pageable pageable);

    UserEntity findByUsername(String username);
}
