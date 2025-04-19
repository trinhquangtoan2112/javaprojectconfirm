package vn.toan.testfullstep.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import org.springframework.stereotype.Repository;
import vn.toan.testfullstep.model.UserEntity;


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
    UserEntity findByEmail(String email);
}
