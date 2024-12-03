package com.example.WebChess.account;

import com.example.WebChess.account.exceptions.AccountRetrievalException;
import com.example.WebChess.account.exceptions.InvalidCredentialsException;
import com.example.WebChess.configuration.JWTService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AccountService {
    private final AccountRepository accountRepository;
    private final BCryptPasswordEncoder encoder;
    private final JWTService jwtService;

    @Autowired
    public AccountService(AccountRepository accountRepository, BCryptPasswordEncoder encoder, JWTService jwtService) {
        this.accountRepository = accountRepository;
        this.encoder = encoder;
        this.jwtService=jwtService;
    }

    private String encryptPassword(String password){
        return encoder.encode(password);
    }

    boolean doPasswordsMatch(String password, String encoded){
        return encoder.matches(password, encoded);
    }

    public List<AccountDTO> getAll(){
        return accountRepository.findAll().stream().map(AccountDTO::new).toList();
    }

    public AccountDTO getByUsername(String username){
        if(username==null){
            throw new IllegalArgumentException("Missing parameters");
        }

        Optional<Account> account=accountRepository.findByUsername(username);
        if(account.isEmpty()){
            throw new AccountRetrievalException("Account with this username does not exist");
        }

        return new AccountDTO(account.get());
    }

    public AccountDTO createAccount(String username, String password, AccountRole role){
        if(username==null || password==null || role==null) {
            throw new IllegalArgumentException("Missing parameters");
        }

        Optional<Account> searchedAccount=accountRepository.findByUsername(username);
        if(searchedAccount.isPresent()){
            throw new AccountRetrievalException("Account with '"+username+"' already exists");
        }

        Account newAccount=new Account(username, encoder.encode(password), role);
        accountRepository.save(newAccount);
        return new AccountDTO(newAccount);
    }

    public String login(String username, String password){
        Optional<Account> accountOptional=accountRepository.findByUsername(username);
        if(accountOptional.isEmpty()){
            throw new AccountRetrievalException("Account does not exist");
        }

        if(!doPasswordsMatch(password, accountOptional.get().getPassword())){
            throw new InvalidCredentialsException("Passwords do not match");
        }

        return jwtService.generateToken(accountOptional.get().getUsername());
    }
}
