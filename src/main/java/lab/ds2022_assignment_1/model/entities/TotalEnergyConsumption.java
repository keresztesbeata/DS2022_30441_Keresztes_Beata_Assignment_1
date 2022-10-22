package lab.ds2022_assignment_1.model.entities;

import java.time.LocalDateTime;

public interface TotalEnergyConsumption {
    Float getEnergy();

    LocalDateTime getTimestamp();

    String getAccountId();
}

