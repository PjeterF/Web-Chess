package com.example.WebChess.account;

import com.example.WebChess.game.Game;
import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@Table
public class Account implements UserDetails {
    @Id
    @SequenceGenerator(
            name="account_sequence",
            sequenceName = "account_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "account_sequence"
    )
    private Long id;
    @ManyToMany(mappedBy = "accounts")
    private List<Game> games;
    @Column(name = "username", unique = true)
    private String username;
    private String password;
    @Enumerated(EnumType.ORDINAL)
    private AccountRole role;

    public Account() {
        this.id = null;
        this.games = new ArrayList<>();
    }

    public Account(String username) {
        this.id=null;
        this.games = new ArrayList<>();
    }

    public Account(String username, String password, AccountRole role){
        this.username=username;
        this.password=password;
        this.role=role;
        this.id=null;
        this.games = new ArrayList<>();
    }



    public String getUsername() {
        return username;
    }

    public AccountRole getRole() {
        return role;
    }

    public void setRole(AccountRole role) {
        this.role = role;
    }

    @Override
    public boolean isAccountNonExpired() {
        return UserDetails.super.isAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return UserDetails.super.isAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return UserDetails.super.isCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled() {
        return UserDetails.super.isEnabled();
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Game> getGames() {
        return games;
    }

    public void setGames(List<Game> games) {
        this.games = games;
    }

    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", games=" + games +
                '}';
    }
}
