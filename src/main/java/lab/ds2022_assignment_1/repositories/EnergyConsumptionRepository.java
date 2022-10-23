package lab.ds2022_assignment_1.repositories;

import lab.ds2022_assignment_1.model.entities.EnergyConsumption;
import lab.ds2022_assignment_1.model.entities.TotalEnergyConsumption;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface EnergyConsumptionRepository extends JpaRepository<EnergyConsumption, Integer> {
    @Transactional
    @Query(value = "SELECT e.* FROM energy_consumption e INNER JOIN device d on d.id = e.device_id WHERE d.account_id = ?1 and d.id = ?2 and date(e.timestamp) = ?3", nativeQuery = true)
    List<EnergyConsumption> findByAccountIdAndDeviceIdAndTimestamp(String accountId, String deviceId, LocalDate timestamp);

    @Transactional
    @Query(value = "SELECT e.* FROM total_energy_consumption_view e WHERE e.accountId = ?1 and date(e.timestamp) = ?2 group by hour(e.timestamp)", nativeQuery = true)
    List<TotalEnergyConsumption> findTotalEnergyConsumptionByAccountIdAndTimestamp(String accountId, LocalDate date);
}
