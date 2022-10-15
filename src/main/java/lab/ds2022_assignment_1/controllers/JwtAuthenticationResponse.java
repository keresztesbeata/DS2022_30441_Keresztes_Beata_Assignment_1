package lab.ds2022_assignment_1.controllers;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

import java.util.List;

@Data
public class JwtAuthenticationResponse {
    private String accessToken;
    private String tokenType = "Bearer";
    private List<String> authorities;

    public JwtAuthenticationResponse(String accessToken, List<String> authorities) {
        this.accessToken = accessToken;
        this.authorities = authorities;
    }
}
