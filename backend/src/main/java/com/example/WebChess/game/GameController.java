package com.example.WebChess.game;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/game")
public class GameController {
    private final GameService gameService;

    @Autowired
    public GameController(GameService gameService) {
        this.gameService = gameService;
    }

    @GetMapping("/all")
    public List<Game> getAll(){
        return gameService.getAll();
    }

    public ResponseEntity<Game> create(@RequestParam String username1, @RequestParam String username2){
        if(username1==null || username2==null){
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }

        Optional<Game> game=gameService.create(username1, username2);
        if(game.isEmpty()){
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(game.get(), HttpStatus.OK);
    }
}
