package lab.ds2022_assignment_1.services;

import lab.ds2022_assignment_1.controllers.handlers.requests.AccountDetailsRequest;
import lab.ds2022_assignment_1.model.entities.Account;
import lab.ds2022_assignment_1.model.entities.UserRole;
import lab.ds2022_assignment_1.model.exceptions.DuplicateUsernameException;
import lab.ds2022_assignment_1.model.exceptions.EntityNotFoundException;
import lab.ds2022_assignment_1.repositories.AccountRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;
import java.util.UUID;

import static lab.ds2022_assignment_1.services.Constants.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
@RunWith(MockitoJUnitRunner.class)
class AccountServiceTest {

    @InjectMocks
    private AccountService service;
    @Mock
    private AccountRepository repository;

    @Mock
    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    private Account account;
    private AccountDetailsRequest request;

    @BeforeEach
    void setUp() {
        account = Account.builder()
                .id(UUID.fromString(ID_1))
                .name(NAME_1)
                .username(USERNAME_1)
                .password(passwordEncoder.encode(PASSWORD_1))
                .role(UserRole.ADMIN)
                .build();

        request = new AccountDetailsRequest();
        request.setName(NAME_1);
        request.setUsername(USERNAME_1);
        request.setPassword(PASSWORD_1);
        request.setRole(UserRole.CLIENT.name());
    }

    @Test
    void createAccount() {
        Mockito.when(repository.save(any(Account.class)))
                .thenReturn(account);

        Assertions.assertDoesNotThrow(() -> service.createAccount(request));
        verify(repository).save(any(Account.class));

        Mockito.when(repository.findByUsername(USERNAME_1))
                .thenReturn(Optional.ofNullable(account));
        Assertions.assertThrows(DuplicateUsernameException.class, () -> service.createAccount(request));
    }

    @Test
    void findAccountByUsername() {
        Mockito.when(repository.findByUsername(USERNAME_1))
                .thenReturn(Optional.of(account));

        Assertions.assertDoesNotThrow(() -> service.findAccountByUsername(USERNAME_1));
        verify(repository).findByUsername(USERNAME_1);

        Assertions.assertThrows(EntityNotFoundException.class, () -> service.findAccountByUsername(USERNAME_2));
        verify(repository).findByUsername(USERNAME_2);
    }

    @Test
    void findAccountById() {
        Mockito.when(repository.findById(UUID.fromString(ID_1)))
                .thenReturn(Optional.of(account));

        Assertions.assertDoesNotThrow(() -> service.findAccountById(ID_1));
        verify(repository).findById(UUID.fromString(ID_1));

        Assertions.assertThrows(EntityNotFoundException.class, () -> service.findAccountById(ID_2));
        verify(repository).findById(UUID.fromString(ID_2));
    }

    @Test
    void deleteAccount() {
        Mockito.when(repository.findById(UUID.fromString(ID_1)))
                .thenReturn(Optional.of(account));

        Assertions.assertDoesNotThrow(() -> service.deleteAccount(ID_1));
        verify(repository).delete(any(Account.class));

        Assertions.assertThrows(EntityNotFoundException.class, () -> service.deleteAccount(ID_2));
    }

    @Test
    void updateAccount() {
        Assertions.assertThrows(EntityNotFoundException.class, () -> service.updateAccount(ID_1, request));

        Mockito.when(repository.findById(UUID.fromString(ID_1)))
                .thenReturn(Optional.ofNullable(account));
        Mockito.when(repository.save(any(Account.class)))
                .thenReturn(account);

        Assertions.assertDoesNotThrow(() -> service.updateAccount(ID_1, request));
        verify(repository).save(any(Account.class));

        Mockito.when(repository.findByUsername(USERNAME_2))
                .thenReturn(Optional.ofNullable(account));
        request.setUsername(USERNAME_2);

        Assertions.assertThrows(DuplicateUsernameException.class, () -> service.createAccount(request));
    }
}