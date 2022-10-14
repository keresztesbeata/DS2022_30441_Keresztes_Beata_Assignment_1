package lab.ds2022_assignment_1.model.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EnergyConsumption {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "timestamp")
    private LocalDateTime timestamp;

    @Column(name = "energy")
    private Float energy;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "device_id", nullable = false)
    private Device device;
}
