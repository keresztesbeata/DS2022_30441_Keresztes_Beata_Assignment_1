package lab.ds2022_assignment_1.dtos;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AccountDTO {
    private String id;
    private String name;
    private String username;
    private String password;
    private String role;
}
