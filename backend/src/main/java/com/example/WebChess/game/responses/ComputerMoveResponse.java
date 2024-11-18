package com.example.WebChess.game.responses;

public class ComputerMoveResponse {
    private String newBoardState;

    public ComputerMoveResponse(String newBoardState) {
        this.newBoardState = newBoardState;
    }

    public String getNewBoardState() {
        return newBoardState;
    }

    public void setNewBoardState(String newBoardState) {
        this.newBoardState = newBoardState;
    }
}
