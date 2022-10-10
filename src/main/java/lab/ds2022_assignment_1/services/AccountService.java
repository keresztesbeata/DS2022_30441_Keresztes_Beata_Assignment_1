package lab.ds2022_assignment_1.services;

import lab.ds2022_assignment_1.config.UserDetailsImpl;
import lab.ds2022_assignment_1.controllers.handlers.requests.AccountDetailsRequest;
import lab.ds2022_assignment_1.dtos.AccountDTO;
import lab.ds2022_assignment_1.dtos.mappers.AccountMapper;
import lab.ds2022_assignment_1.model.entities.Account;
import lab.ds2022_assignment_1.model.exceptions.DuplicateUsernameException;
import lab.ds2022_assignment_1.model.exceptions.EntityNotFoundException;
import lab.ds2022_assignment_1.repositories.AccountRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Slf4j
public class AccountService {
    @Autowired
    private AccountRepository repository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    private final AccountMapper mapper = new AccountMapper();
    private static final String DUPLICATE_USERNAME_ERR_MSG = "Duplicate username! The name %s is already taken.";
    private static final String NOT_EXISTENT_ACCOUNT_ERR_MSG = "This account doesn't exist!";
    private static final String NO_LOGGED_IN_USER_ERROR_MESSAGE = "No logged in user!";

    /**
     * Create a new account.
     *
     * @param request a {@link AccountDetailsRequest}
     * @return a {@link AccountDTO}
     * @throws DuplicateUsernameException if the username is already taken
     */
    public AccountDTO createAccount(final AccountDetailsRequest request) throws DuplicateUsernameException {
        Account account = mapper.mapRequestToEntity(request);
        if (repository.findByUsername(account.getUsername()).isPresent()) {
            throw new DuplicateUsernameException(String.format(DUPLICATE_USERNAME_ERR_MSG, account.getUsername()));
        }
        final String encodedPassword = passwordEncoder.encode(account.getPassword());
        account.setPassword(encodedPassword);
        final Account savedAccount = repository.save(account);
        log.info("Account for user {} was created successfully!", account.getUsername());

        return mapper.mapEntityToDto(savedAccount);
    }

    /**
     * Find account by unique username.
     *
     * @param username the username by which the account is searched
     * @return an {@link AccountDTO}
     * @throws EntityNotFoundException if no account exists with the given username
     */
    public AccountDTO findAccountByUsername(final String username) throws EntityNotFoundException {
        return mapper.mapEntityToDto(
                repository.findByUsername(username)
                        .orElseThrow(() -> new EntityNotFoundException(NOT_EXISTENT_ACCOUNT_ERR_MSG))
        );
    }

    /**
     * Find account by unique id.
     *
     * @param id the id by which the account is searched
     * @return an {@link AccountDTO}
     * @throws EntityNotFoundException if no account with the given id exists
     */
    public AccountDTO findAccountById(final String id) throws EntityNotFoundException {
        return mapper.mapEntityToDto(
                repository.findById(UUID.fromString(id))
                        .orElseThrow(() -> new EntityNotFoundException(NOT_EXISTENT_ACCOUNT_ERR_MSG))
        );
    }

    /**
     * Delete an existing account.
     *
     * @param id the id of the account
     * @throws EntityNotFoundException if no such account exists
     */
    public void deleteAccount(final String id) throws EntityNotFoundException {
        final Account account = repository.findById(UUID.fromString(id))
                .orElseThrow(() -> new EntityNotFoundException(NOT_EXISTENT_ACCOUNT_ERR_MSG));
        repository.delete(account);
    }

    /**
     * Update the details of the account.
     *
     * @param id      the id of the account
     * @param request an {@link AccountDetailsRequest}
     * @return an {@link AccountDTO}
     * @throws DuplicateUsernameException if the username is already taken
     * @throws EntityNotFoundException    if no account was found with the given
     */
    public AccountDTO updateAccount(String id, final AccountDetailsRequest request) throws DuplicateUsernameException, EntityNotFoundException {
        Account newAccount = mapper.mapRequestToEntity(request);
        final Account oldAccount = repository.findById(UUID.fromString(id))
                .orElseThrow(() -> new EntityNotFoundException(NOT_EXISTENT_ACCOUNT_ERR_MSG));
        newAccount.setId(oldAccount.getId());

        if (!newAccount.getUsername().equals(oldAccount.getUsername()) &&
                repository.findByUsername(newAccount.getUsername()).isPresent()) {
            throw new DuplicateUsernameException(String.format(DUPLICATE_USERNAME_ERR_MSG, newAccount.getUsername()));
        }

        final String encodedPassword = passwordEncoder.encode(newAccount.getPassword());
        newAccount.setPassword(encodedPassword);
        final Account savedAccount = repository.save(newAccount);
        log.info("Account for user {} was created successfully!", newAccount.getUsername());

        return mapper.mapEntityToDto(savedAccount);
    }

    /**
     * Get the account of the currently logged-in user.
     *
     * @return {@link AccountDTO}
     * @throws EntityNotFoundException if no user is logged in
     */
    public AccountDTO getCurrentUserAccount() throws EntityNotFoundException {
        final Object currentUserAccount = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (currentUserAccount instanceof UserDetailsImpl) {
            return mapper.mapEntityToDto(((UserDetailsImpl) currentUserAccount).getAccount());
        } else {
            throw new EntityNotFoundException(NO_LOGGED_IN_USER_ERROR_MESSAGE);
        }
    }
}
