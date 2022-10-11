package lab.ds2022_assignment_1.controllers.handlers.requests;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@ToString
public class LinkDeviceRequest {
    @NotBlank
    private String deviceId;
}
