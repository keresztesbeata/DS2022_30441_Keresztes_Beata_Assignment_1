package lab.ds2022_assignment_1.repositories;

import lab.ds2022_assignment_1.model.entities.Account;
import lab.ds2022_assignment_1.model.entities.Device;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface DeviceRepository extends JpaRepository<Device, UUID> {
    List<Device> findByAccountAndAddress(Account account, String address);

    List<Device> findByAccount(Account account);

    @Query(value = "SELECT * FROM device d WHERE d.account_id = ?1 and d.id = ?2", nativeQuery = true)
    Optional<Device> findByAccountIdAndId(String accountId, String id);
}
