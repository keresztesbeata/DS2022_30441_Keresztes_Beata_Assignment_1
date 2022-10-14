package lab.ds2022_assignment_1.controllers;

import lab.ds2022_assignment_1.config.JwtTokenProvider;
import lab.ds2022_assignment_1.controllers.handlers.requests.AccountData;
import lab.ds2022_assignment_1.controllers.handlers.requests.AuthenticationRequest;
import lab.ds2022_assignment_1.dtos.AccountDTO;
import lab.ds2022_assignment_1.model.exceptions.DuplicateDataException;
import lab.ds2022_assignment_1.model.exceptions.EntityNotFoundException;
import lab.ds2022_assignment_1.services.api.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static lab.ds2022_assignment_1.controllers.Constants.*;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.http.ResponseEntity.ok;

@RestController
public class AccountRestController {

    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    JwtTokenProvider tokenProvider;
    @Autowired
    private AccountService service;

    @PostMapping(LOGIN_PATH)
    public ResponseEntity<JwtAuthenticationResponse> authenticateUser(@RequestBody AuthenticationRequest request) throws AuthenticationException {
        final Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        final String jwt = tokenProvider.generateToken(authentication);

        return ok(new JwtAuthenticationResponse(jwt));
    }

    @PostMapping(value = REGISTER_PATH, consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<AccountDTO> registerUser(@RequestBody AccountData data) throws DuplicateDataException {
        return ok(service.createAccount(data));
    }

    @GetMapping(CURRENTLY_LOGGED_IN_USER_PATH)
    public ResponseEntity<AccountDTO> getLoggedInUser() throws EntityNotFoundException {
        return ok(service.getCurrentUserAccount());
    }

    @PostMapping(value = ACCOUNTS_PATH, consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<AccountDTO> createAccount(@Valid @RequestBody AccountData data) throws DuplicateDataException {
        return ok(service.createAccount(data));
    }

    @PutMapping(value = ACCOUNT_ID_PATH, consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<AccountDTO> updateAccount(@PathVariable(ACCOUNT_ID) String id,
                                                    @Valid @RequestBody AccountData data) throws DuplicateDataException, EntityNotFoundException {
        return ok(service.updateAccount(id, data));
    }

    @GetMapping(ACCOUNT_ID_PATH)
    public ResponseEntity<AccountDTO> getAccountById(@PathVariable(ACCOUNT_ID) String id) throws EntityNotFoundException {
        return ok(service.findAccountById(id));
    }

    @GetMapping(ACCOUNTS_PATH)
    public ResponseEntity<AccountDTO> getAccountByUsername(@RequestParam String username) throws EntityNotFoundException {
        return ok(service.findAccountByUsername(username));
    }

    @DeleteMapping(ACCOUNT_ID_PATH)
    public ResponseEntity deleteAccount(@PathVariable(ACCOUNT_ID) String id) throws EntityNotFoundException {
        service.deleteAccount(id);
        return ResponseEntity.ok().build();
    }
}
