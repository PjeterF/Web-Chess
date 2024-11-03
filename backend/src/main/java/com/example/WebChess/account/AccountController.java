package com.example.WebChess.account;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping(path = "/api/account")
public class AccountController {
    private final AccountService accountService;

    @Autowired
    public AccountController(AccountService accountService){
        this.accountService = accountService;
    }

    @GetMapping("/all")
    public List<Account> getUsers(){
        return accountService.getAll();
    }

    @GetMapping("/")
    public ResponseEntity<Account> getByUsername(@RequestParam String username){
        if(username==null){
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }

        Optional<Account> account=accountService.getByUsername(username);
        if(account.isEmpty()){
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(account.get(), HttpStatus.OK);
    }

    @PostMapping("/")
    public ResponseEntity<Account> createAccount(@RequestBody Map<String, String> body){
        if(body.get("username")==null){
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }

        Optional<Account> account= accountService.createAccount(body.get("username"));
        if(account.isEmpty()){
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(account.get(), HttpStatus.OK);
    }
}
