package lab.ds2022_assignment_1.dtos;

import lombok.*;

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
}
