package com.example.WebChess.game;

import com.example.WebChess.account.exceptions.AccountRetrievalException;
import com.example.WebChess.game.exceptions.GameRetrievalException;
import com.example.WebChess.game.exceptions.IllegalTileSelectinException;
import com.example.WebChess.game.exceptions.InvalidBoardOperationException;
import com.example.WebChess.game.requests.ComputerMoveRequest;
import com.example.WebChess.game.requests.CreateGameRequest;
import com.example.WebChess.game.requests.MoveRequest;
import com.example.WebChess.game.requests.UndoMoveRequest;
import com.example.WebChess.game.responses.ComputerMoveResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/games")
public class GameController {
    private final GameService gameService;

    @Autowired
    public GameController(GameService gameService) {
        this.gameService = gameService;
    }

    @GetMapping("")
    public List<GameDTO> getAll(){
        return gameService.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long id){
        try{
            GameDTO game=gameService.getGameById(id);
            return new ResponseEntity<>(game, HttpStatus.OK);
        }catch (AccountRetrievalException exception){
            return new ResponseEntity<>(exception.getMessage(), HttpStatus.NOT_FOUND);
        }catch (IllegalArgumentException exception){
            return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
        }catch (Exception exception){
            return new ResponseEntity<>(exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/username/{username}")
    public ResponseEntity<?> getUsersGames(@PathVariable String username){
        try {
            List<GameDTO> games=gameService.getGamesOfUser(username);
            return new ResponseEntity<>(games, HttpStatus.OK);
        }catch (IllegalArgumentException exception) {
            return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
        }catch (AccountRetrievalException exception){
            return new ResponseEntity<>(exception.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("")
    public ResponseEntity<GameDTO> create(@RequestBody CreateGameRequest createGameRequest){
        try{
            GameDTO game=gameService.create(
                    createGameRequest.getUsername1(),
                    createGameRequest.getUsername2(),
                    createGameRequest.getWhiteIsAutomated(),
                    createGameRequest.getBlackIsAutomated(),
                    createGameRequest.getDifficulty()
            );
            return new ResponseEntity<>(game, HttpStatus.OK);
        }catch (Exception error){
            error.getCause();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/move")
    public ResponseEntity<?> move(@RequestBody MoveRequest request){
        if(request.getGameID()==null || request.getStart()==null || request.getTarget()==null || request.getStart().size()!=2 || request.getTarget().size()!=2){
            return new ResponseEntity<>("Invalid parameters", HttpStatus.BAD_REQUEST);
        }
        try{
            Game game = gameService.makeAMove(request);
            return new ResponseEntity<>(new GameDTO(game), HttpStatus.OK);
        }catch (GameRetrievalException exception){
            return new ResponseEntity<>(exception.getMessage(), HttpStatus.NOT_FOUND);
        }catch (IllegalTileSelectinException | IllegalArgumentException exception){
            return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
        }catch (RuntimeException exception){
            return new ResponseEntity<>(exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/computerMove")
    public ResponseEntity<?> computerMove(@RequestBody ComputerMoveRequest request) {
        try{
            Game game=gameService.makeAComputerMove(request);
            return new ResponseEntity<>(new GameDTO(game), HttpStatus.OK);
        }catch (IllegalArgumentException exception){
            return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
        }catch (GameRetrievalException exception){
            return new ResponseEntity<>(exception.getMessage(), HttpStatus.NOT_FOUND);
        }catch (Exception exception){
            return new ResponseEntity<>(exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/undo")
    public ResponseEntity<?> undoMove(@RequestBody UndoMoveRequest request){
        try{
            Game game=gameService.undoMove(request);
            return new ResponseEntity<>(new GameDTO(game), HttpStatus.OK);
        }catch (IllegalArgumentException | InvalidBoardOperationException exception){
            return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
        }catch (GameRetrievalException exception){
            return new ResponseEntity<>(exception.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception exception){
            return new ResponseEntity<>(exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
