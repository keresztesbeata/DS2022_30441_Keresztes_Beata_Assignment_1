package lab.ds2022_assignment_1.repositories;

import lombok.*;

import java.sql.Timestamp;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class HourlyEnergyConsumption {
    private Timestamp hour;
    private Float energy;
}
