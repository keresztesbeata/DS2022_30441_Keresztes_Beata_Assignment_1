package lab.ds2022_assignment_1.dtos.mappers;

import lab.ds2022_assignment_1.controllers.handlers.requests.AccountDetailsRequest;
import lab.ds2022_assignment_1.dtos.AccountDTO;
import lab.ds2022_assignment_1.model.entities.Account;
import lab.ds2022_assignment_1.model.entities.UserRole;

public class AccountMapper {
    public Account mapRequestToEntity(AccountDetailsRequest request) {
        return Account.builder()
                .username(request.getUsername())
                .password(request.getPassword())
                .name(request.getName())
                .role(UserRole.valueOf(request.getRole()))
                .build();
    }

    public AccountDTO mapEntityToDto(Account account) {
        return AccountDTO.builder()
                .id(account.getId().toString())
                .name(account.getName())
                .role(account.getRole().toString())
                .build();
    }
}
