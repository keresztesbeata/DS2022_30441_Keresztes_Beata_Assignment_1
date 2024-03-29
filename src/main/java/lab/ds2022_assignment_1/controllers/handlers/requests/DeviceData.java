package lab.ds2022_assignment_1.controllers.handlers.requests;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class DeviceData {
    @NotBlank
    private String address;
    private String description;
}
