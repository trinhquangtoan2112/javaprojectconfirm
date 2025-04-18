package vn.toan.testfullstep.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import vn.toan.testfullstep.model.AddressEntity;
import org.springframework.stereotype.Repository;
@Repository
public interface AddressRepository extends JpaRepository<AddressEntity, Long> {
    AddressEntity findByUserIdAndAddressType(Long userId, Integer addressType);

  
}
