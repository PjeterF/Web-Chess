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
    public List<AccountDTO_gameIDs> getUsers(){
        return accountService.getAll();
    }

    @GetMapping("/{username}")
    public ResponseEntity<AccountDTO_gameIDs> getByUsername(@PathVariable String username){
        if(username==null){
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }

        Optional<AccountDTO_gameIDs> account=accountService.getByUsername(username);
        if(account.isEmpty()){
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(account.get(), HttpStatus.OK);
    }

    @PostMapping("/")
    public ResponseEntity<AccountDTO_gameIDs> createAccount(@RequestBody Map<String, String> body){
        if(body.get("username")==null){
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }

        Optional<AccountDTO_gameIDs> accountDTO= accountService.createAccount(body.get("username"));
        if(accountDTO.isEmpty()){
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(accountDTO.get(), HttpStatus.OK);
    }
}
