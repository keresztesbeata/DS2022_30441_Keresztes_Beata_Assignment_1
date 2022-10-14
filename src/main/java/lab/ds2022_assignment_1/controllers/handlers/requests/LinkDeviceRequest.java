package lab.ds2022_assignment_1.controllers.handlers.requests;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class LinkDeviceRequest {
    @NotBlank
    private String deviceId;
    @NotBlank
    private String accountId;
}
