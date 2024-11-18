package com.example.WebChess.game.requests;

public class ComputerMoveRequest {
    private Long gameID;
    private Integer depth;
    private Boolean white;

    public Long getGameID() {
        return gameID;
    }

    public void setGameID(Long gameID) {
        this.gameID = gameID;
    }

    public Integer getDepth() {
        return depth;
    }

    public void setDepth(Integer depth) {
        this.depth = depth;
    }

    public Boolean getWhite() {
        return white;
    }

    public void setWhite(Boolean white) {
        this.white = white;
    }
}
