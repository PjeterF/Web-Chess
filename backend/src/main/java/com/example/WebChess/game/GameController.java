package com.example.WebChess.game;

import com.example.WebChess.game.requests.MoveRequest;
import com.example.WebChess.game.responses.MoveResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
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
    public List<GameDTO_accountIDs> getAll(){
        return gameService.getAll();
    }

    @PostMapping("/create")
    public ResponseEntity<GameDTO_accountIDs> create(@RequestBody Map<String, String> body){
        String username1=body.get("username1");
        String username2=body.get("username2");

        if(username1==null || username2==null){
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }

        Optional<GameDTO_accountIDs> game=gameService.create(username1, username2);
        if(game.isEmpty()){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>(game.get(), HttpStatus.OK);
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping("/move")
    public ResponseEntity<String> move(@RequestBody MoveRequest request){
        if(request.getGameID()==null || request.getStart()==null || request.getTarget()==null || request.getStart().size()!=2 || request.getTarget().size()!=2){
            return new ResponseEntity<>("Invalid parameters", HttpStatus.BAD_REQUEST);
        }

        boolean result = gameService.makeAMove(request.getGameID(), request.getStart().get(0), request.getStart().get(1), request.getTarget().get(0), request.getTarget().get(1));

        if(!result){
            return new ResponseEntity<>("Invalid move", HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>("Valid move", HttpStatus.OK);
    }

}
