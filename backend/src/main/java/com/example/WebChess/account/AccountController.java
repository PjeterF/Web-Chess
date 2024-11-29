package com.example.WebChess.account;

import com.example.WebChess.account.exceptions.AccountRetrievalException;
import com.example.WebChess.account.requests.CreateAccountRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "/api/accounts")
public class AccountController {
    private final AccountService accountService;

    @Autowired
    public AccountController(AccountService accountService){
        this.accountService = accountService;
    }

    @GetMapping("")
    public List<AccountDTO_gameIDs> getUsers(){
        return accountService.getAll();
    }

    @GetMapping("/{username}")
    public ResponseEntity<?> getByUsername(@PathVariable String username){
        try{
            AccountDTO_gameIDs account=accountService.getByUsername(username);
            return new ResponseEntity<>(account, HttpStatus.OK);
        }catch (AccountRetrievalException exception){
            return new ResponseEntity<>(exception.getMessage(), HttpStatus.NOT_FOUND);
        }catch (IllegalArgumentException exception){
            return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
        }catch (Exception exception){
            return new ResponseEntity<>(exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("")
    public ResponseEntity<?> createAccount(@RequestBody CreateAccountRequest request){
        try{
            AccountDTO_gameIDs account=accountService.createAccount(request.getUsername(), request.getPassword(), request.getRole());
            return new ResponseEntity<>(account, HttpStatus.CREATED);
        }catch (AccountRetrievalException exception){
            return new ResponseEntity<>(exception.getMessage(), HttpStatus.CONFLICT);
        }catch (IllegalArgumentException exception){
            return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
        }catch (Exception exception){
            return new ResponseEntity<>(exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
