package lab.ds2022_assignment_1.controllers.handlers.requests;

import lombok.*;

@Data
public class AuthenticationRequest {
    private String username;
    private String password;
}
