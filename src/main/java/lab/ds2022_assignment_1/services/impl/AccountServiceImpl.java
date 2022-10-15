package lab.ds2022_assignment_1.services.impl;

import lab.ds2022_assignment_1.config.UserDetailsImpl;
import lab.ds2022_assignment_1.controllers.handlers.requests.AccountData;
import lab.ds2022_assignment_1.dtos.AccountDTO;
import lab.ds2022_assignment_1.dtos.mappers.AccountMapper;
import lab.ds2022_assignment_1.model.entities.Account;
import lab.ds2022_assignment_1.model.exceptions.DuplicateDataException;
import lab.ds2022_assignment_1.model.exceptions.EntityNotFoundException;
import lab.ds2022_assignment_1.model.exceptions.NoLoggedInUserException;
import lab.ds2022_assignment_1.repositories.AccountRepository;
import lab.ds2022_assignment_1.services.api.AccountService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Slf4j
public class AccountServiceImpl implements AccountService {
    @Autowired
    private AccountRepository repository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    private final AccountMapper mapper = new AccountMapper();
    private static final String DUPLICATE_USERNAME_ERR_MSG = "Duplicate username! The name %s is already taken.";
    private static final String NOT_EXISTENT_ACCOUNT_ERR_MSG = "This account doesn't exist!";
    private static final String NO_LOGGED_IN_USER_ERROR_MESSAGE = "No logged in user!";

    /**
     * {@inheritDoc}
     */
    @Override
    public AccountDTO createAccount(final AccountData request) throws DuplicateDataException {
        Account account = mapper.mapToEntity(request);
        if (repository.findByUsername(account.getUsername()).isPresent()) {
            throw new DuplicateDataException(String.format(DUPLICATE_USERNAME_ERR_MSG, account.getUsername()));
        }
        final String encodedPassword = passwordEncoder.encode(account.getPassword());
        account.setPassword(encodedPassword);
        final Account savedAccount = repository.save(account);
        log.debug("Account with id {} for user with username {} was created successfully!", account.getId(), account.getUsername());

        return mapper.mapToDto(savedAccount);
    }

    /**
     * /**
     * {@inheritDoc}
     */
    @Override
    public AccountDTO findAccountByUsername(final String username) throws EntityNotFoundException {
        return mapper.mapToDto(
                repository.findByUsername(username)
                        .orElseThrow(() -> new EntityNotFoundException(NOT_EXISTENT_ACCOUNT_ERR_MSG))
        );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public AccountDTO findAccountById(final String id) throws EntityNotFoundException {
        return mapper.mapToDto(
                repository.findById(UUID.fromString(id))
                        .orElseThrow(() -> new EntityNotFoundException(NOT_EXISTENT_ACCOUNT_ERR_MSG))
        );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void deleteAccount(final String id) throws EntityNotFoundException {
        final Account account = repository.findById(UUID.fromString(id))
                .orElseThrow(() -> new EntityNotFoundException(NOT_EXISTENT_ACCOUNT_ERR_MSG));

        repository.delete(account);
        log.debug("Account with id {} was successfully deleted!", id);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public AccountDTO updateAccount(final String id, final AccountData request) throws DuplicateDataException, EntityNotFoundException {
        Account newAccount = mapper.mapToEntity(request);
        final Account oldAccount = repository.findById(UUID.fromString(id))
                .orElseThrow(() -> new EntityNotFoundException(NOT_EXISTENT_ACCOUNT_ERR_MSG));
        newAccount.setId(oldAccount.getId());

        if (!newAccount.getUsername().equals(oldAccount.getUsername()) &&
                repository.findByUsername(newAccount.getUsername()).isPresent()) {
            throw new DuplicateDataException(String.format(DUPLICATE_USERNAME_ERR_MSG, newAccount.getUsername()));
        }

        final String encodedPassword = passwordEncoder.encode(newAccount.getPassword());
        newAccount.setPassword(encodedPassword);
        final Account savedAccount = repository.save(newAccount);
        log.info("Account with id {} was successfully updated!", id);

        return mapper.mapToDto(savedAccount);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public AccountDTO getCurrentUserAccount() throws NoLoggedInUserException {
        final Object currentUserAccount = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (currentUserAccount instanceof UserDetailsImpl) {
            return mapper.mapToDto(((UserDetailsImpl) currentUserAccount).getAccount());
        } else {
            throw new NoLoggedInUserException(NO_LOGGED_IN_USER_ERROR_MESSAGE);
        }
    }
}
