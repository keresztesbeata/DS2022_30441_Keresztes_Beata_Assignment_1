package lab.ds2022_assignment_1.services;

import lab.ds2022_assignment_1.controllers.handlers.requests.CRUDAccountRequest;
import lab.ds2022_assignment_1.dtos.AccountDTO;
import lab.ds2022_assignment_1.dtos.mappers.AccountMapper;
import lab.ds2022_assignment_1.model.entities.Account;
import lab.ds2022_assignment_1.model.exceptions.DuplicateUsernameException;
import lab.ds2022_assignment_1.model.exceptions.EntityNotFoundException;
import lab.ds2022_assignment_1.repositories.AccountRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Slf4j
public class AccountService {
    @Autowired
    private AccountRepository repository;

    private final AccountMapper mapper = new AccountMapper();

    private static final String DUPLICATE_USERNAME_ERR_MSG = "Duplicate username! The name %s is already taken.";
    private static final String NOT_EXISTENT_ACCOUNT_ERR_MSG = "This account doesn't exist!";

    /**
     * Create a new account.
     *
     * @param request a {@link CRUDAccountRequest}
     * @return a {@link AccountDTO}
     * @throws DuplicateUsernameException if the username is already taken
     */
    public AccountDTO createAccount(final CRUDAccountRequest request) throws DuplicateUsernameException {
        final Account account = mapper.mapRequestToEntity(request);
        if (repository.findByUsername(account.getUsername()).isPresent()) {
            throw new DuplicateUsernameException(String.format(DUPLICATE_USERNAME_ERR_MSG, account.getUsername()));
        }

        // todo: encode the password before saving it
        final Account savedAccount = repository.save(account);
        log.info("Account for user {} was created successfully!", account.getUsername());

        return mapper.mapEntityToDto(savedAccount);
    }

    /**
     * Check if the account exists by the user credentials.
     *
     * @param username the username of the user's account
     * @param password the password of the user's account
     * @return true if the account with the given credentials exists
     */
    public boolean existsAccount(final String username, final String password) {
        return repository.findByUsernameAndPassword(username, password).isPresent();
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
     * @param request an {@link CRUDAccountRequest}
     * @return an {@link AccountDTO}
     * @throws DuplicateUsernameException if the username is already taken
     * @throws EntityNotFoundException    if no account was found with the given
     */
    public AccountDTO updateAccount(String id, final CRUDAccountRequest request) throws DuplicateUsernameException, EntityNotFoundException {
        final Account newAccount = mapper.mapRequestToEntity(request);
        final Account oldAccount = repository.findById(UUID.fromString(id))
                .orElseThrow(() -> new EntityNotFoundException(NOT_EXISTENT_ACCOUNT_ERR_MSG));
        newAccount.setId(oldAccount.getId());

        if (!newAccount.getUsername().equals(oldAccount.getUsername()) &&
                repository.findByUsername(newAccount.getUsername()).isPresent()) {
            throw new DuplicateUsernameException(String.format(DUPLICATE_USERNAME_ERR_MSG, newAccount.getUsername()));
        }

        // todo: encode the password before saving it
        final Account savedAccount = repository.save(newAccount);
        log.info("Account for user {} was created successfully!", newAccount.getUsername());

        return mapper.mapEntityToDto(savedAccount);
    }
}
