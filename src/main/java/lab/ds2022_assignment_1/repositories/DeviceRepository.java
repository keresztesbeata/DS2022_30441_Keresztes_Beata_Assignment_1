package lab.ds2022_assignment_1.repositories;

import lab.ds2022_assignment_1.model.entities.Account;
import lab.ds2022_assignment_1.model.entities.Device;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface DeviceRepository extends JpaRepository<Device, UUID>, JpaSpecificationExecutor<Device> {
    List<Device> findByAccountAndAddress(Account account, String address);

    List<Device> findByAccount(Account account);

    List<Device> findByAccountNull();
}
