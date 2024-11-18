package com.example.WebChess.game;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class GameDTO_accountIDs {
    private Long id;
    private String boardState;
    private List<Long> accountIDs;
    boolean whitesTurn=true;
    LocalDateTime created;
    LocalDateTime lastUpdate;

    public GameDTO_accountIDs(Game game) {
        this.id=game.getId();
        this.boardState=game.getBoardState();
        this.whitesTurn=game.isWhitesTurn();
        this.accountIDs=new ArrayList<>();
        this.accountIDs.add(game.getWhiteAccount().getId());
        this.accountIDs.add(game.getBlackAccount().getId());
        this.created=game.getCreated();
        this.lastUpdate=game.getLastUpdate();
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

    public List<Long> getAccountIDs() {
        return accountIDs;
    }

    public void setAccountIDs(List<Long> accountIDs) {
        this.accountIDs = accountIDs;
    }

    public boolean isWhitesTurn() {
        return whitesTurn;
    }

    public void setWhitesTurn(boolean whitesTurn) {
        this.whitesTurn = whitesTurn;
    }
}
