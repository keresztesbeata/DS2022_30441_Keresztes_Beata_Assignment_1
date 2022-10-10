package lab.ds2022_assignment_1.controllers;

import lab.ds2022_assignment_1.config.JwtTokenProvider;
import lab.ds2022_assignment_1.controllers.handlers.requests.AccountDetailsRequest;
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
    public ResponseEntity<JwtAuthenticationResponse> authenticateUser(@Valid @RequestBody AuthenticationRequest request) throws AuthenticationException {
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

    @PostMapping(REGISTER_PATH)
    public ResponseEntity<AccountDTO> registerUser(@Valid @RequestBody AccountDetailsRequest request) throws DuplicateDataException {
        return ok(service.createAccount(request));
    }

    @GetMapping(CURRENTLY_LOGGED_IN_USER_PATH)
    public ResponseEntity<AccountDTO> getLoggedInUser() throws EntityNotFoundException {
        return ok(service.getCurrentUserAccount());
    }

    @PostMapping(ACCOUNTS_PATH)
    public ResponseEntity<AccountDTO> createAccount(@Valid @RequestBody AccountDetailsRequest request) throws DuplicateDataException {
        return ok(service.createAccount(request));
    }

    @PutMapping(ACCOUNT_ID_PATH)
    public ResponseEntity<AccountDTO> updateAccount(@PathVariable(ACCOUNT_ID) String id,
                                                    @Valid @RequestBody AccountDetailsRequest request) throws DuplicateDataException, EntityNotFoundException {
        return ok(service.updateAccount(id, request));
    }

    @GetMapping(ACCOUNT_ID_PATH)
    public ResponseEntity<AccountDTO> findAccountById(@PathVariable(ACCOUNT_ID) String id) throws EntityNotFoundException {
        return ok(service.findAccountById(id));
    }

    @GetMapping(ACCOUNTS_PATH)
    public ResponseEntity<AccountDTO> findAccountByUsername(@RequestParam String username) throws EntityNotFoundException {
        return ok(service.findAccountByUsername(username));
    }

    @PostMapping(DELETE_ACCOUNT_PATH)
    public ResponseEntity deleteAccount(@PathVariable(ACCOUNT_ID) String id) throws EntityNotFoundException {
        service.deleteAccount(id);
        return ResponseEntity.ok().build();
    }
}
