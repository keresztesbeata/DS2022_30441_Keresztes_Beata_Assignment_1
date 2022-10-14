package lab.ds2022_assignment_1.services.api;

import lab.ds2022_assignment_1.controllers.handlers.requests.AccountData;
import lab.ds2022_assignment_1.dtos.AccountDTO;
import lab.ds2022_assignment_1.model.exceptions.DuplicateDataException;
import lab.ds2022_assignment_1.model.exceptions.EntityNotFoundException;

public interface AccountService {

    /**
     * Create a new account.
     *
     * @param request a {@link AccountData}
     * @return a {@link AccountDTO}
     * @throws DuplicateDataException if the username is already taken
     */
    AccountDTO createAccount(final AccountData request) throws DuplicateDataException;

    /**
     * Find account by unique username.
     *
     * @param username the username by which the account is searched
     * @return an {@link AccountDTO}
     * @throws EntityNotFoundException if no account exists with the given username
     */
    AccountDTO findAccountByUsername(final String username) throws EntityNotFoundException;

    /**
     * Find account by unique id.
     *
     * @param id the id by which the account is searched
     * @return an {@link AccountDTO}
     * @throws EntityNotFoundException if no account with the given id exists
     */
    AccountDTO findAccountById(final String id) throws EntityNotFoundException;

    /**
     * Delete an existing account.
     *
     * @param id the id of the account
     * @throws EntityNotFoundException if no such account exists
     */
    void deleteAccount(final String id) throws EntityNotFoundException;

    /**
     * Update the details of the account.
     *
     * @param id      the id of the account
     * @param request an {@link AccountData}
     * @return an {@link AccountDTO}
     * @throws DuplicateDataException  if the username is already taken
     * @throws EntityNotFoundException if no account was found with the given
     */
    AccountDTO updateAccount(final String id, final AccountData request) throws DuplicateDataException, EntityNotFoundException;

    /**
     * Get the account of the currently logged-in user.
     *
     * @return {@link AccountDTO}
     * @throws EntityNotFoundException if no user is logged in
     */
    AccountDTO getCurrentUserAccount() throws EntityNotFoundException;
}
