package com.example.WebChess.account;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AccountService {
    private AccountRepository accountRepository;

    @Autowired
    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public List<AccountDTO_gameIDs> getAll(){
        return accountRepository.findAll().stream().map(AccountDTO_gameIDs::new).toList();
    }

    public Optional<AccountDTO_gameIDs> getByUsername(String username){
        Optional<Account> account=accountRepository.findByUsername(username);
        if(account.isEmpty()){
            return Optional.empty();
        }
        return Optional.of(new AccountDTO_gameIDs(account.get()));
    }

    public Optional<AccountDTO_gameIDs> createAccount(String username){
        Optional<Account> searchedAccount=accountRepository.findByUsername(username);
        if(searchedAccount.isPresent()){
            return Optional.empty();
        }

        Account newAccount=new Account(username);
        accountRepository.save(newAccount);
        return Optional.of(new AccountDTO_gameIDs(newAccount));
    }
}
