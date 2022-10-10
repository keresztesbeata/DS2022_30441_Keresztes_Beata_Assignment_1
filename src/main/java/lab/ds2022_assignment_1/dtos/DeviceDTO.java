package lab.ds2022_assignment_1.dtos;

import lombok.*;

import java.sql.Timestamp;
import java.util.Map;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class DeviceDTO {
    private String id;
    private String description;
    private String address;
    private AccountDTO account;
    private Float maxHourlyEnergyConsumption;
    private Map<Timestamp, Float> hourlyEnergyConsumption;
}
