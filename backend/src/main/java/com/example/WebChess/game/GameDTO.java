package com.example.WebChess.game;

import com.example.WebChess.move.MoveDTO;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class GameDTO {
    private Long id;
    private String boardState;
    private List<String> accountIDs;
    boolean whitesTurn=true;
    LocalDateTime created;
    LocalDateTime lastUpdate;
    Boolean whiteIsAutomated;
    Boolean blackIsAutomated;
    private List<MoveDTO> moves;
    String result;

    public GameDTO(Game game) {
        this.id=game.getId();
        this.boardState=game.getBoardState();
        this.whitesTurn=game.isWhitesTurn();
        this.accountIDs=new ArrayList<>();
        this.accountIDs.add(game.getWhiteAccount().getUsername());
        this.accountIDs.add(game.getBlackAccount().getUsername());
        this.created=game.getCreated();
        this.lastUpdate=game.getLastUpdate();
        this.whiteIsAutomated =game.getWhiteIsAutomated();
        this.blackIsAutomated =game.getBlackIsAutomated();
        this.result=game.getResult();

        moves=new ArrayList<>();
        for(var move : game.getMoves()){
            moves.add(new MoveDTO(move));
        }
    }

    public List<MoveDTO> getMoves() {
        return moves;
    }

    public void setMoves(List<MoveDTO> moves) {
        this.moves = moves;
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

    public List<String> getAccountIDs() {
        return accountIDs;
    }

    public void setAccountIDs(List<String> accountIDs) {
        this.accountIDs = accountIDs;
    }

    public boolean isWhitesTurn() {
        return whitesTurn;
    }

    public void setWhitesTurn(boolean whitesTurn) {
        this.whitesTurn = whitesTurn;
    }
}
