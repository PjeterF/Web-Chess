package com.example.WebChess.account;

import com.example.WebChess.game.Game;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@Table
public class Account {
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
    private String role;

    public Account() {
        this.id = null;
        this.games = new ArrayList<>();
    }

    public Account(String username) {
        this.id=null;
        this.games = new ArrayList<>();
    }

    public Account(String username, String password, String role){
        this.username=username;
        this.password=password;
        this.role=role;
        this.id=null;
        this.games = new ArrayList<>();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
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
