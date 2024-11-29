package com.example.WebChess.game;

import com.example.WebChess.account.Account;
import com.example.WebChess.account.AccountRepository;
import com.example.WebChess.account.exceptions.AccountRetrievalException;
import com.example.WebChess.chess.ChessEvaluator;
import com.example.WebChess.game.exceptions.GameRetrievalException;
import com.example.WebChess.game.exceptions.IllegalTileSelectinException;
import com.example.WebChess.game.exceptions.InvalidBoardOperationException;
import com.example.WebChess.game.requests.ComputerMoveRequest;
import com.example.WebChess.game.requests.MoveRequest;
import com.example.WebChess.game.requests.UndoMoveRequest;
import com.example.WebChess.move.Move;
import com.example.WebChess.move.MoveRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class GameService {
    private GameRepository gameRepository;
    private AccountRepository accountRepository;
    private MoveRepository moveRepository;

    @Autowired
    public GameService(GameRepository gameRepository, AccountRepository accountRepository, MoveRepository moveRepository) {
        this.gameRepository = gameRepository;
        this.accountRepository=accountRepository;
        this.moveRepository=moveRepository;
    }

    @Transactional
    public GameDTO create(String username1, String username2, boolean whiteAutomated, boolean blackAutomated, int difficulty){
        if(username1==null || username2==null){
            throw new IllegalArgumentException("At least one username was null");
        }

        Optional<Account> account1=accountRepository.findByUsername(username1);
        Optional<Account> account2=accountRepository.findByUsername(username2);

        if(account1.isEmpty() || account2.isEmpty()){
            throw new AccountRetrievalException("Could not find at least one account");
        }

        Game newGame=new Game("rnbqkbnrpppppppp................................PPPPPPPPRNBQKBNR", account1.get(), account2.get(), whiteAutomated, blackAutomated, difficulty);
        Game saved=gameRepository.save(newGame);

        account1.get().getGames().add(saved);
        account2.get().getGames().add(saved);

        return new GameDTO(newGame);
    }

    public List<GameDTO> getAll() {
        return gameRepository.findAll().stream().map(GameDTO::new).toList();
    }

    public GameDTO getGameById(Long id){
        if(id==null){
            throw new IllegalArgumentException("Missing parameters");
        }

        Optional<Game> game=gameRepository.findById(id);
        if(game.isEmpty()){
            throw new AccountRetrievalException("Could not find game");
        }
        return new GameDTO(game.get());
    }

    public List<GameDTO> getGamesOfUser(String username){
        if(username==null){
            throw new IllegalArgumentException("Missing parameters");
        }

        Optional<Account> accountOptional=accountRepository.findByUsername(username);
        if(accountOptional.isEmpty()){
            throw new AccountRetrievalException("Account does not exist");
        }

        List<GameDTO> result=new ArrayList<>();
        for(Game game : accountOptional.get().getGames()){
            result.add(new GameDTO(game));
        }

        return result;
    }

    @Transactional
    public Game makeAComputerMove(ComputerMoveRequest computerMoveRequest){
        if(computerMoveRequest.getGameID()==null || computerMoveRequest.getWhite()==null || computerMoveRequest.getDepth()==null){
            throw new IllegalArgumentException("Missing parameters");
        }

        if(computerMoveRequest.getDepth()<1){
            throw new IllegalArgumentException("Invalid depth value; must be greater than 0");
        }

        Optional<Game> game=gameRepository.findById(computerMoveRequest.getGameID());
        if(game.isEmpty()){
            throw new GameRetrievalException("Could not find game");
        }

        ChessEvaluator evaluator=new ChessEvaluator(game.get().getBoardState());
        String[] move=new String[2];
        evaluator.minimax(computerMoveRequest.getDepth(), computerMoveRequest.getWhite(), move, Integer.MIN_VALUE, Integer.MAX_VALUE);

        evaluator.makeAMove(move[0], move[1]);
        game.get().setBoardState(evaluator.getBoardString());
        game.get().setWhitesTurn(!computerMoveRequest.getWhite());

        List<Move> gameMoves=game.get().getMoves();
        Move newMove=new Move(move[0]+move[1], game.get());
        moveRepository.save(newMove);

        gameMoves.add(newMove);
        game.get().setMoves(gameMoves);
        gameRepository.save(game.get());

        return game.get();
    }

    @Transactional
    public Game makeAMove(MoveRequest moveRequest){
        if(moveRequest.getStart()==null){
            throw new IllegalArgumentException("Starting coordinate was null");
        }
        if(moveRequest.getTarget()==null){
            throw new IllegalArgumentException("Target coordinate was null");
        }
        if(moveRequest.getStart().get(0)<0 || moveRequest.getStart().get(0)>=8 || moveRequest.getStart().get(1)<0 || moveRequest.getStart().get(1)>=8){
            throw new IllegalArgumentException("Starting coordinate is out of bounds");
        }
        if(moveRequest.getTarget().get(0)<0 || moveRequest.getTarget().get(0)>=8 || moveRequest.getTarget().get(1)<0 || moveRequest.getTarget().get(1)>=8){
            throw new IllegalArgumentException("Target coordinate is out of bounds");
        }
        if(moveRequest.getGameID()==null){
            throw new IllegalArgumentException("Game ID was null");
        }

        Optional<Game> game=gameRepository.findById(moveRequest.getGameID());
        if(game.isEmpty()){
            throw new GameRetrievalException("Could not find game");
        }

        char piece=game.get().getBoardState().charAt(coordToIndex(moveRequest.getStart().get(0), moveRequest.getStart().get(1)));
        if(piece=='.'){
            throw new IllegalTileSelectinException("Starting tile was empty");
        }
        if(Character.isLowerCase(piece) && game.get().isWhitesTurn()){
            throw new IllegalTileSelectinException("Starting tile had a other player's piece");
        }
        if(Character.isUpperCase(piece) && !game.get().isWhitesTurn()){
            throw new IllegalTileSelectinException("Starting tile had a other player's piece");
        }

        ChessEvaluator evaluator=new ChessEvaluator(game.get().getBoardState());

        boolean canMove=evaluator.validateMove(
                moveRequest.getStart().get(0),
                moveRequest.getStart().get(1),
                moveRequest.getTarget().get(0),
                moveRequest.getTarget().get(1)
        );

        if(canMove) {
            StringBuilder builder=new StringBuilder(game.get().getBoardState());
            int startIndex=coordToIndex(moveRequest.getStart().get(0), moveRequest.getStart().get(1));
            int targetIndex=coordToIndex(moveRequest.getTarget().get(0), moveRequest.getTarget().get(1));
            builder.setCharAt(targetIndex, builder.charAt(startIndex));
            builder.setCharAt(startIndex, '.');

            game.get().setBoardState(builder.toString());
            game.get().toggleTurn();

            List<Move> gameMoves=game.get().getMoves();
            String start=evaluator.encodePosition(moveRequest.getStart().get(0), moveRequest.getStart().get(1));
            String target=evaluator.encodePosition(moveRequest.getTarget().get(0), moveRequest.getTarget().get(1));

            Move newMove=new Move(start+target, game.get());
            moveRepository.save(newMove);

            gameMoves.add(newMove);
            game.get().setMoves(gameMoves);
            gameRepository.save(game.get());

            return game.get();
        }
        else{
            throw new RuntimeException("Error processing move");
        }
    }

    @Transactional
    public Game undoMove(@RequestBody UndoMoveRequest request){
        if(request.getGameID()==null){
            throw new IllegalArgumentException("Missing parameters");
        }
        Optional<Game> gameOptional=gameRepository.findById(request.getGameID());
        if(gameOptional.isEmpty()){
            throw new GameRetrievalException("Game with given ID does not exist");
        }

        Game game=gameOptional.get();
        if(game.getMoves().isEmpty()){
            throw new InvalidBoardOperationException("There are not moves to undo");
        }

        ChessEvaluator evaluator=new ChessEvaluator("rnbqkbnrpppppppp................................PPPPPPPPRNBQKBNR");

        game.getMoves().remove(game.getMoves().size()-1);
        for(var move : game.getMoves()){
            String start=move.getNotation().substring(0, 2);
            String target=move.getNotation().substring(2, 4);
            evaluator.makeAMove(start, target);
        }

        game.setBoardState(evaluator.getBoardString());
        game.toggleTurn();
        gameRepository.save(game);

        return game;
    }

    private char[][] FENtoArray(String FEN){
        char[][] result=new char[8][8];

        int row=0, col=0;
        for(int i=0;i<FEN.length();i++){
            char currentChar=FEN.charAt(i);
            if(currentChar=='/'){
                row++;
                col=0;
                continue;
            }
            if(currentChar>='0' &&  currentChar<='8'){
                for(int j=0;j<currentChar-'0';j++){
                    result[row][col]='.';
                    col++;
                }
            }else{
                result[row][col]=currentChar;
                col++;
            }
        }

        return result;
    }

    int coordToIndex(int x, int y){
        return x+8*y;
    }
}
