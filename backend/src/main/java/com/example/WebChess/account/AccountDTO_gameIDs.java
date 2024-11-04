package com.example.WebChess.account;

import com.example.WebChess.game.Game;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.ManyToMany;

import java.util.ArrayList;
import java.util.List;

public class AccountDTO {
    private Long id;
    private String username;
    private List<Long> gameIDs;

    public AccountDTO(Account account) {
        this.id = account.getId();
        this.username = account.getUsername();
        List<Long> ids=new ArrayList<>();
        for(Game game : account.getGames()){
            ids.add(game.getId());
        }
        this.gameIDs = ids;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<Long> getGameIDs() {
        return gameIDs;
    }

    public void setGameIDs(List<Long> gameIDs) {
        this.gameIDs = gameIDs;
    }
}
