package lab.ds2022_assignment_1.dtos.mappers;

import lab.ds2022_assignment_1.controllers.handlers.requests.AccountData;
import lab.ds2022_assignment_1.dtos.AccountDTO;
import lab.ds2022_assignment_1.model.entities.Account;
import lab.ds2022_assignment_1.model.entities.UserRole;

public class AccountMapper implements Mapper<Account, AccountData, AccountDTO> {
    public Account mapToEntity(AccountData data) {
        return Account.builder()
                .username(data.getUsername())
                .password(data.getPassword())
                .name(data.getName())
                .role(UserRole.valueOf(data.getRole()))
                .build();
    }

    public AccountDTO mapToDto(Account entity) {
        return AccountDTO.builder()
                .id(entity.getId().toString())
                .name(entity.getName())
                .role(entity.getRole().toString())
                .build();
    }
}
