package com.example.WebChess.game;

import com.example.WebChess.account.Account;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table
public class Game {
    @Id
    @SequenceGenerator(
            name="game_sequence",
            sequenceName = "game_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "game_sequence"
    )
    private Long id;
    private String boardState;

    public LocalDateTime getCreated() {
        return created;
    }

    public void setCreated(LocalDateTime created) {
        this.created = created;
    }

    public LocalDateTime getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(LocalDateTime lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    @ManyToMany
    private List<Account> accounts;
    boolean whitesTurn=true;
    LocalDateTime created;
    LocalDateTime lastUpdate;

    public Game(String boardState, Account whiteAccount, Account blackAccount) {
        this.boardState = boardState;
        accounts=new ArrayList<>();
        accounts.add(whiteAccount);
        accounts.add(blackAccount);
        created=LocalDateTime.now();
        lastUpdate=LocalDateTime.now();
    }

    public Game() {
        boardState="";
        accounts=new ArrayList<>();
        created=LocalDateTime.now();
        lastUpdate=LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBoardState() {
        return boardState;
    }

    public void setBoardState(String boardState) {
        this.boardState = boardState;
    }

    public Account getWhiteAccount() {
        return accounts.get(0);
    }

    public void setWhiteAccount(Account whiteAccount) {
        this.accounts.set(0, whiteAccount);
    }

    public Account getBlackAccount() {
        return accounts.get(1);
    }

    public void setBlackAccount(Account blackAccount) {
        this.accounts.set(1, blackAccount);
    }

    public boolean isWhitesTurn() {
        return whitesTurn;
    }

    public void setWhitesTurn(boolean whitesTurn) {
        this.whitesTurn = whitesTurn;
    }
}
