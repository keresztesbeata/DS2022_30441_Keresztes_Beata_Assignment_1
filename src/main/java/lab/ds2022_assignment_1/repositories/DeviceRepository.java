package lab.ds2022_assignment_1.repositories;

import lab.ds2022_assignment_1.model.entities.Account;
import lab.ds2022_assignment_1.model.entities.Device;
import lab.ds2022_assignment_1.model.entities.HourlyEnergyConsumption;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.*;

@Repository
public interface DeviceRepository extends JpaRepository<Device, UUID> {
    List<Device> findByAccountAndAddress(Account account, String address);

    List<Device> findByAccount(Account account);

    @Query(value = "SELECT * FROM device d WHERE d.account_id = ?1 and d.id = ?2", nativeQuery = true)
    Optional<Device> findByAccountIdAndId(String accountId, String id);

    @Transactional
    @Query(value = "SELECT * FROM device d INNER JOIN energy_consumption e on d.id = e.device_id WHERE d.id = ?1 and date(e.hour) = ?2", nativeQuery = true)
    Optional<Device> findEnergyConsumptionsByDeviceIdAndDate(String id, Date date);

    @Transactional
    @Query(value = "SELECT * FROM device d INNER JOIN energy_consumption e on d.id = e.device_id WHERE d.account_id = ?1 and date(e.hour) = ?2", nativeQuery = true)
    List<Device> findEnergyConsumptionsByAccountIdAndDate(String accountId, Date date);
}
