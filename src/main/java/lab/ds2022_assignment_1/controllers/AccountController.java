package lab.ds2022_assignment_1.controllers;

import lab.ds2022_assignment_1.controllers.handlers.requests.CRUDAccountRequest;
import lab.ds2022_assignment_1.dtos.AccountDTO;
import lab.ds2022_assignment_1.model.exceptions.DuplicateUsernameException;
import lab.ds2022_assignment_1.model.exceptions.EntityNotFoundException;
import lab.ds2022_assignment_1.services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import static lab.ds2022_assignment_1.controllers.Constants.*;
import static org.springframework.http.ResponseEntity.ok;

@Controller
public class AccountController {

    @Autowired
    private AccountService service;

    @PostMapping(ACCOUNTS_PATH)
    public ResponseEntity<AccountDTO> createAccount(@RequestBody CRUDAccountRequest request) throws DuplicateUsernameException {
        return ok(service.createAccount(request));
    }

    @PutMapping(ACCOUNT_ID_PATH)
    public ResponseEntity<AccountDTO> updateAccount(@PathVariable(ACCOUNT_ID) String id,
                                                    @RequestBody CRUDAccountRequest request) throws DuplicateUsernameException, EntityNotFoundException {
        return ok(service.updateAccount(id, request));
    }

    @GetMapping(ACCOUNT_ID_PATH)
    public ResponseEntity<AccountDTO> findAccountById(@PathVariable(ACCOUNT_ID) String id) throws EntityNotFoundException {
        return ok(service.findAccountById(id));
    }

    @GetMapping(ACCOUNTS_PATH)
    public ResponseEntity<AccountDTO> findAccountByUsername(@RequestParam("username") String username) throws EntityNotFoundException {
        return ok(service.findAccountByUsername(username));
    }

    @PostMapping(DELETE_ACCOUNT_PATH)
    public ResponseEntity deleteAccount(@PathVariable(ACCOUNT_ID) String id) throws EntityNotFoundException {
        service.deleteAccount(id);

        return ResponseEntity.ok().build();
    }
}
