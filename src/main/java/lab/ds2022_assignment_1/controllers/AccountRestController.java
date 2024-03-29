package lab.ds2022_assignment_1.controllers;

import lab.ds2022_assignment_1.config.JwtTokenProvider;
import lab.ds2022_assignment_1.controllers.handlers.requests.AccountData;
import lab.ds2022_assignment_1.controllers.handlers.requests.AuthenticationRequest;
import lab.ds2022_assignment_1.controllers.handlers.requests.SearchCriteria;
import lab.ds2022_assignment_1.controllers.handlers.requests.ValidUUID;
import lab.ds2022_assignment_1.dtos.AccountDTO;
import lab.ds2022_assignment_1.model.exceptions.DuplicateDataException;
import lab.ds2022_assignment_1.model.exceptions.EntityNotFoundException;
import lab.ds2022_assignment_1.model.exceptions.InvalidFilterException;
import lab.ds2022_assignment_1.model.exceptions.NoLoggedInUserException;
import lab.ds2022_assignment_1.services.api.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static lab.ds2022_assignment_1.controllers.Constants.*;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.http.ResponseEntity.ok;

@RestController
@Validated
public class AccountRestController {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtTokenProvider tokenProvider;
    @Autowired
    private AccountService service;

    @PostMapping(LOGIN_PATH)
    public ResponseEntity<JwtAuthenticationResponse> authenticateUser(@RequestBody @Valid AuthenticationRequest request) throws AuthenticationException {
        final Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        final String jwt = tokenProvider.generateToken(authentication);

        return ok(new JwtAuthenticationResponse(jwt,
                authentication.getAuthorities().stream()
                        .map(GrantedAuthority::getAuthority)
                        .collect(Collectors.toList())));
    }

    @PostMapping(value = REGISTER_PATH, consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<AccountDTO> registerUser(@RequestBody @Valid AccountData data) throws DuplicateDataException {
        return ok(service.createAccount(data));
    }

    @GetMapping(CURRENTLY_LOGGED_IN_USER_PATH)
    public ResponseEntity<AccountDTO> getLoggedInUser() throws NoLoggedInUserException {
        return ok(service.getCurrentUserAccount());
    }

    @PostMapping(value = ACCOUNTS_PATH, consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<AccountDTO> createAccount(@RequestBody @Valid AccountData data) throws DuplicateDataException {
        return new ResponseEntity<>(service.createAccount(data), HttpStatus.CREATED);
    }

    @PutMapping(value = ACCOUNT_ID_PATH, consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<AccountDTO> updateAccount(@PathVariable(ACCOUNT_ID) @ValidUUID String id,
                                                    @RequestBody @Valid AccountDTO dto) throws DuplicateDataException, EntityNotFoundException {
        return ok(service.updateAccount(id, dto));
    }

    @GetMapping(ACCOUNT_ID_PATH)
    public ResponseEntity<AccountDTO> getAccountById(@PathVariable(ACCOUNT_ID) @ValidUUID String id) throws EntityNotFoundException {
        return ok(service.findAccountById(id));
    }

    @GetMapping(ACCOUNTS_PATH)
    public ResponseEntity<List<AccountDTO>> getAccounts(@RequestParam @Nullable String userRole) {
        return ok(service.findAccounts(Optional.ofNullable(userRole)));
    }

    @GetMapping(ACCOUNTS_FILTER_PATH)
    public ResponseEntity<List<AccountDTO>> filterAccounts(@RequestParam String filterKey, @RequestParam String filterValue, @RequestParam @Nullable String userRole) throws InvalidFilterException {
        return ok(service.filterAccounts(new SearchCriteria(filterKey, filterValue), Optional.ofNullable(userRole)));
    }

    @DeleteMapping(ACCOUNT_ID_PATH)
    public ResponseEntity deleteAccount(@PathVariable(ACCOUNT_ID) @ValidUUID String id) throws EntityNotFoundException {
        service.deleteAccount(id);
        return ResponseEntity.ok().build();
    }
}
