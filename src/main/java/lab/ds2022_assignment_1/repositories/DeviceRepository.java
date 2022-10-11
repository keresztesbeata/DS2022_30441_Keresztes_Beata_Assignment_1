package lab.ds2022_assignment_1.repositories;

import lab.ds2022_assignment_1.model.entities.Account;
import lab.ds2022_assignment_1.model.entities.Device;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.util.*;

@Repository
public interface DeviceRepository extends JpaRepository<Device, UUID> {
    List<Device> findByAccountAndAddress(Account account, String address);

    List<Device> findByAccount(Account account);

    @Query(value = "SELECT * FROM device d WHERE d.account_id = ?1 and d.id = ?2", nativeQuery = true)
    Optional<Device> findByAccountIdAndId(String accountId, String id);

    @Transactional
    @Query(value = "SELECT e.hour, e.energy FROM energy_consumption e LEFT JOIN device d on d.id = e.device_id WHERE d.id = ?1 and date(e.hour) = ?2", nativeQuery = true)
    List<HourlyEnergyConsumption> findHourlyEnergyConsumptionByAccountIdAndByDeviceIdAndDate(String deviceId, Date date);

    @Transactional
    @Query(value = "SELECT e.hour, e.energy FROM energy_consumption e LEFT JOIN device d on d.id = e.device_id WHERE d.account_id = ?1 and date(e.hour) = ?2", nativeQuery = true)
    List<List<HourlyEnergyConsumption>> findHourlyEnergyConsumptionByAccountIdAndDate(String accountId, Date date);
}
