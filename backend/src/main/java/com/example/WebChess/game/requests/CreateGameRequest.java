package com.example.WebChess.game.requests;

public class CreateGameRequest {
    private String username1;
    private String username2;
    private Boolean whiteIsAutomated;
    private Boolean blackIsAutomated;
    private Integer difficulty;

    public CreateGameRequest(String username1, String username2, Boolean whiteIsAutomated, Boolean blackIsAutomated, Integer difficulty) {
        this.username1 = username1;
        this.username2 = username2;
        this.whiteIsAutomated = whiteIsAutomated;
        this.blackIsAutomated = blackIsAutomated;
        this.difficulty=difficulty;
    }

    public Integer getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(Integer difficulty) {
        this.difficulty = difficulty;
    }

    public String getUsername1() {
        return username1;
    }

    public void setUsername1(String username1) {
        this.username1 = username1;
    }

    public String getUsername2() {
        return username2;
    }

    public void setUsername2(String username2) {
        this.username2 = username2;
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
}
