package lab.ds2022_assignment_1.model.entities;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.sql.Timestamp;

@Embeddable
@Data
@NoArgsConstructor
public class HourlyEnergyConsumption implements Serializable {
    @Column(name = "hour")
    private Timestamp hour;

    @Column(name = "energy")
    private Float energy;
}
