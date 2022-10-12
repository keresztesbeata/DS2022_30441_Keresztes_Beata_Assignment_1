package lab.ds2022_assignment_1.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EnergyConsumptionDTO {
    private String deviceId;
    private Float energy;
    private LocalDateTime timestamp;
}
