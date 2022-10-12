package lab.ds2022_assignment_1.controllers.handlers.requests;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class EnergyConsumptionDetailsRequest {
    private String deviceId;
    private float energy;
    private LocalDateTime timestamp;
}
