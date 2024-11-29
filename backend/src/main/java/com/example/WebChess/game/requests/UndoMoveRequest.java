package com.example.WebChess.game.requests;

public class UndoMoveRequest {
    private Long gameID;

    public UndoMoveRequest(Long gameID) {
        this.gameID = gameID;
    }

    public UndoMoveRequest() {
    }

    public Long getGameID() {
        return gameID;
    }

    public void setGameID(Long gameID) {
        this.gameID = gameID;
    }
}
