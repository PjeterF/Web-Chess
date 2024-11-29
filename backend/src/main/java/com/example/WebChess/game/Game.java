package com.example.WebChess.game;

import com.example.WebChess.account.Account;
import com.example.WebChess.move.Move;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
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
    @ManyToMany
    private List<Account> accounts;
    @OneToMany
    private List<Move> moves;
    boolean whitesTurn=true;
    private LocalDateTime created;
    private LocalDateTime lastUpdate;
    private Boolean whiteIsAutomated =false;
    private Boolean blackIsAutomated =false;
    private Integer difficulty=4;
    private String result="Ongoing";

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public void toggleTurn(){
        whitesTurn=!whitesTurn;
    }

    public Integer getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(Integer difficulty) {
        this.difficulty = difficulty;
    }

    public Boolean getWhiteIsAutomated() {
        return whiteIsAutomated;
    }

    public void setWhiteIsAutomated(Boolean whiteIsAutomated) {
        this.whiteIsAutomated = whiteIsAutomated;
    }

    public Boolean getBlackIsAutomated() {
        return blackIsAutomated;
    }

    public void setBlackIsAutomated(Boolean blackIsAutomated) {
        this.blackIsAutomated = blackIsAutomated;
    }

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

    public Game(String boardState, Account whiteAccount, Account blackAccount, boolean whiteIsAutomated, boolean blackIsAutomated, int difficulty) {
        this.boardState = boardState;
        accounts=new ArrayList<>();
        accounts.add(whiteAccount);
        accounts.add(blackAccount);
        created=LocalDateTime.now();
        lastUpdate=LocalDateTime.now();
        this.whiteIsAutomated = whiteIsAutomated;
        this.blackIsAutomated = blackIsAutomated;
        this.difficulty=difficulty;
        moves=new ArrayList<>();
    }

    public Game() {
        boardState="";
        accounts=new ArrayList<>();
        created=LocalDateTime.now();
        lastUpdate=LocalDateTime.now();
        moves=new ArrayList<>();
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

    public List<Move> getMoves() {
        return moves;
    }

    public void setMoves(List<Move> moves) {
        this.moves = moves;
    }
}
