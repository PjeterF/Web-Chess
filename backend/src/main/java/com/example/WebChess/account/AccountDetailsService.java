package com.example.WebChess.account;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

public class AccountDetailsService implements UserDetailsService {
    @Autowired
    private AccountRepository accountRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Account> account=accountRepository.findByUsername(username);
        if(account.isEmpty()){
            throw new UsernameNotFoundException(username);
        }

        return User.builder()
                .username(account.get().getUsername())
                .password(account.get().getPassword())
                .roles(account.get().getRole())
                .build();
    }
}
