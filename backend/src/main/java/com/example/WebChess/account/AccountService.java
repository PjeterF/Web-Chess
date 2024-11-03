package com.example.WebChess.account;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AccountService {
    private AccountRepository accountRepository;

    @Autowired
    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public List<Account> getAll(){
        return accountRepository.findAll();
    }

    public Optional<Account> getByUsername(String username){
        return accountRepository.findByUsername(username);
    }

    public Optional<Account> createAccount(String username){
        Optional<Account> searchedAccount=accountRepository.findByUsername(username);
        if(searchedAccount.isPresent()){
            return Optional.empty();
        }

        Account newAccount=new Account(username);
        accountRepository.save(newAccount);
        return Optional.of(newAccount);
    }
}
