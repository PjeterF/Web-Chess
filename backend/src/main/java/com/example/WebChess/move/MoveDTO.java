package com.example.WebChess.move;

import com.example.WebChess.game.Game;
import jakarta.persistence.ManyToOne;

public class MoveDTO {
    private Long id;
    private String notation;
    private Integer moveNumber;
    private Long gameID;

    public MoveDTO(Move move){
        this.id=move.getId();
        this.notation=move.getNotation();
        this.moveNumber=move.getMoveNumber();
        this.gameID=move.getGame().getId();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNotation() {
        return notation;
    }

    public void setNotation(String notation) {
        this.notation = notation;
    }

    public Integer getMoveNumber() {
        return moveNumber;
    }

    public void setMoveNumber(Integer moveNumber) {
        this.moveNumber = moveNumber;
    }

    public Long getGameID() {
        return gameID;
    }

    public void setGameID(Long gameID) {
        this.gameID = gameID;
    }
}
