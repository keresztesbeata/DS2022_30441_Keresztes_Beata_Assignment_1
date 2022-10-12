package lab.ds2022_assignment_1.dtos;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DeviceDTO {
    private String id;
    private String description;
    private String address;
}
