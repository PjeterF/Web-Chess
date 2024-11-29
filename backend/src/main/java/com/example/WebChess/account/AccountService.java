package com.example.WebChess.account;

import com.example.WebChess.account.exceptions.AccountRetrievalException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AccountService {
    private final AccountRepository accountRepository;
    private final BCryptPasswordEncoder encoder;

    @Autowired
    public AccountService(AccountRepository accountRepository, BCryptPasswordEncoder encoder) {
        this.accountRepository = accountRepository;
        this.encoder = encoder;
    }

    private String encryptPassword(String password){
        return encoder.encode(password);
    }

    boolean doPasswordsMatch(String password, String encoded){
        return encoder.matches(password, encoded);
    }

    public List<AccountDTO_gameIDs> getAll(){
        return accountRepository.findAll().stream().map(AccountDTO_gameIDs::new).toList();
    }

    public AccountDTO_gameIDs getByUsername(String username){
        if(username==null){
            throw new IllegalArgumentException("Missing parameters");
        }

        Optional<Account> account=accountRepository.findByUsername(username);
        if(account.isEmpty()){
            throw new AccountRetrievalException("Account with this username does not exist");
        }

        return new AccountDTO_gameIDs(account.get());
    }

    public AccountDTO_gameIDs createAccount(String username, String password, String role){
        if(username==null || password==null || role==null) {
            throw new IllegalArgumentException("Missing parameters");
        }

        Optional<Account> searchedAccount=accountRepository.findByUsername(username);
        if(searchedAccount.isPresent()){
            throw new AccountRetrievalException("Account with '"+username+"' already exists");
        }

        Account newAccount=new Account(username, encoder.encode(password), role);
        accountRepository.save(newAccount);
        return new AccountDTO_gameIDs(newAccount);
    }
}
