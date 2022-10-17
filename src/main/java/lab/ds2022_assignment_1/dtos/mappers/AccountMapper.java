package lab.ds2022_assignment_1.dtos.mappers;

import lab.ds2022_assignment_1.controllers.handlers.requests.AccountData;
import lab.ds2022_assignment_1.dtos.AccountDTO;
import lab.ds2022_assignment_1.model.entities.Account;
import lab.ds2022_assignment_1.model.entities.UserRole;

public class AccountMapper implements Mapper<Account, AccountData, AccountDTO> {
    @Override
    public Account mapToEntity(AccountData data) {
        return Account.builder()
                .username(data.getUsername())
                .password(data.getPassword())
                .name(data.getName())
                .role(UserRole.valueOf(data.getRole()))
                .build();
    }

    @Override
    public Account mapDtoToEntity(AccountDTO data) {
        return Account.builder()
                .username(data.getUsername())
                .name(data.getName())
                .role(UserRole.valueOf(data.getRole()))
                .build();
    }

    @Override
    public AccountDTO mapToDto(Account entity) {
        return AccountDTO.builder()
                .id(entity.getId().toString())
                .name(entity.getName())
                .username(entity.getUsername())
                .role(entity.getRole().toString())
                .build();
    }
}
