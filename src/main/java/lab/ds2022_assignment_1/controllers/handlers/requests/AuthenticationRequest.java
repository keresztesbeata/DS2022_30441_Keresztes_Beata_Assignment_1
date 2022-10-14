package lab.ds2022_assignment_1.controllers.handlers.requests;

import lombok.*;

import javax.validation.constraints.NotBlank;

@Data
public class AuthenticationRequest {
    @NotBlank
    private String username;
    @NotBlank
    private String password;
}
