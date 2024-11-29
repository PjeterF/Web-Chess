package com.example.WebChess.game.requests;

import java.util.List;

public class MoveRequest {
    private Long gameID;
    private List<Integer> start;
    private List<Integer> target;

    public MoveRequest(Long gameID, List<Integer> start, List<Integer> target) {
        this.gameID = gameID;
        this.start = start;
        this.target = target;
    }

    public MoveRequest() {
    }

    public Long getGameID() {
        return gameID;
    }

    public void setGameID(Long gameID) {
        this.gameID = gameID;
    }

    public List<Integer> getStart() {
        return start;
    }

    public void setStart(List<Integer> start) {
        this.start = start;
    }

    public List<Integer> getTarget() {
        return target;
    }

    public void setTarget(List<Integer> target) {
        this.target = target;
    }
}
