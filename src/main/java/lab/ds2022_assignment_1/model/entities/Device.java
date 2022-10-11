
package lab.ds2022_assignment_1.model.entities;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class Device {
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(name = "id", updatable = false, nullable = false, columnDefinition = "VARCHAR(36)")
    @Type(type = "uuid-char")
    private UUID id;

    @Column(name = "description", length = 500)
    private String description;

    @Column(name = "address", length = 300)
    private String address;

    @ManyToOne
    @JoinColumn(name = "account_id")
    private Account account;

    @Column(name = "maxHourlyEnergyConsumption", columnDefinition = "float default 0", precision = 8, scale = 2)
    private float maxHourlyEnergyConsumption;

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "energy_consumption",
            joinColumns = {@JoinColumn(name = "device_id")})
    @Column(name = "energy")
    @MapKeyColumn(name = "hour")
    private Map<Timestamp, Float> hourlyEnergyConsumption = new HashMap<>();

    public void addEnergyConsumption(Timestamp hour, Float energy) {
        hourlyEnergyConsumption.put(hour, energy);

        if(energy > maxHourlyEnergyConsumption) {
            maxHourlyEnergyConsumption = energy;
        }
    }
}
