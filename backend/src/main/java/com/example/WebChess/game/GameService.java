package com.example.WebChess.game;

import com.example.WebChess.account.Account;
import com.example.WebChess.account.AccountRepository;
import com.example.WebChess.chess.ChessEvaluator;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class GameService {
    private GameRepository gameRepository;
    private AccountRepository accountRepository;

    @Autowired
    public GameService(GameRepository gameRepository, AccountRepository accountRepository) {
        this.gameRepository = gameRepository;
        this.accountRepository=accountRepository;
    }

    @Transactional
    public Optional<GameDTO_accountIDs> create(String username1, String username2){
        if(username1==null || username2==null){
            return Optional.empty();
        }

        Optional<Account> account1=accountRepository.findByUsername(username1);
        Optional<Account> account2=accountRepository.findByUsername(username2);

        if(account1.isEmpty() || account2.isEmpty()){
            return Optional.empty();
        }

        Game newGame=new Game("rnbqkbnrpppppppp................................PPPPPPPPRNBQKBNR", account1.get(), account2.get());
        Game saved=gameRepository.save(newGame);

        account1.get().getGames().add(saved);
        account2.get().getGames().add(saved);

        return Optional.of(new GameDTO_accountIDs(newGame));
    }

    public List<GameDTO_accountIDs> getAll() {
        return gameRepository.findAll().stream().map(GameDTO_accountIDs::new).toList();
    }

    public Optional<GameDTO_accountIDs> getGameById(Long id){
        Optional<Game> game=gameRepository.findById(id);
        if(game.isPresent()){
            return Optional.of(new GameDTO_accountIDs(game.get()));
        }else{
            return Optional.empty();
        }
    }

    @Transactional
    public String makeAComputerMove(Long gameId, boolean white, int depth){
        Optional<Game> game=gameRepository.findById(gameId);
        if(game.isEmpty()){
            throw new RuntimeException("Could not find game");
        }

        ChessEvaluator evaluator=new ChessEvaluator(game.get().getBoardState());
        String[] move=new String[2];
        evaluator.minimax(depth, white, move, Integer.MIN_VALUE, Integer.MAX_VALUE);

        evaluator.makeAMove(move[0], move[1]);
        game.get().setBoardState(evaluator.getBoardString());
        game.get().setWhitesTurn(!white);
        gameRepository.save(game.get());

        return game.get().getBoardState();
    }

    @Transactional
    public boolean makeAMove(Long gameId, int startX, int startY, int targetX, int targetY){
        if(startX<0 || startY>=8 || startY<0 || startY>=8){
            return false;
        }

        Optional<Game> game=gameRepository.findById(gameId);
        if(game.isEmpty()){
            return false;
        }

        char piece=game.get().getBoardState().charAt(coordToIndex(startX, startY));
        if(piece=='.'){
            return false;
        }
        if(Character.isLowerCase(piece) && game.get().isWhitesTurn()){
            return false;
        }
        if(Character.isUpperCase(piece) && !game.get().isWhitesTurn()){
            return false;
        }

        boolean result=validateMove(startX, startY, targetX, targetY, game.get().getBoardState());
        if(result){
            StringBuilder builder=new StringBuilder(game.get().getBoardState());
            int startIndex=coordToIndex(startX, startY);
            int targetIndex=coordToIndex(targetX, targetY);
            builder.setCharAt(targetIndex, builder.charAt(startIndex));
            builder.setCharAt(startIndex, '.');

            game.get().setBoardState(builder.toString());
            if(game.get().isWhitesTurn()){
                game.get().setWhitesTurn(false);
            }else{
                game.get().setWhitesTurn(true);
            }
            gameRepository.save(game.get());
            return true;
        }

        return false;
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

    private boolean validateMove(int startX, int startY, int targetX, int targetY, String board){
        char piece=board.charAt(startX+8*startY);
        char target=board.charAt(targetX+8*targetY);

       if(!Character.isAlphabetic(piece)){
           return false;
       }

       ArrayList<ArrayList<Integer>> validOffsets;
       switch(Character.toLowerCase(piece)){
           case 'p'->{
               return validatePawn(startX, startY, targetX, targetY, board);
           }
           case 'r'->{
               return validateRook(startX, startY, targetX, targetY, board);
           }
           case 'k'->{
                return validateKing(startX, startY, targetX, targetY, board);
           }
           case 'n'->{
               return validateKnight(startX, startY, targetX, targetY, board);
           }
           case 'b'->{
               return validateBishop(startX, startY, targetX, targetY, board);
           }
           case 'q'->{
               return validateQueen(startX, startY, targetX, targetY, board);
           }
       }

        return false;
    }

    private boolean validatePawn(int startX, int startY, int targetX, int targetY, String board){
        if(Character.isLowerCase(board.charAt(coordToIndex(startX, startY)))){
            if(board.charAt(coordToIndex(targetX, targetY))=='.' && targetX==startX && targetY==startY+1){
                return true;
            }else if( board.charAt(coordToIndex(targetX, targetY))=='.' && startY==1 && targetY==3  && startX==targetX){
                return true;
            }else if(Character.isUpperCase(board.charAt(coordToIndex(targetX, targetY))) && startY==targetY-1 && Math.abs(targetX-startX)==1){
                return true;
            }
        }else{
            if(board.charAt(coordToIndex(targetX, targetY))=='.' && targetX==startX && targetY==startY-1){
                return true;
            }else if(board.charAt(coordToIndex(targetX, targetY))=='.' && startY==6 && targetY==4 && startX==targetX){
                return true;
            }else if(Character.isLowerCase(board.charAt(coordToIndex(targetX, targetY))) && startY==targetY+1 && Math.abs(targetX-startX)==1){
                return true;
            }
        }
        return false;
    }

    private boolean validateRook(int startX, int startY, int targetX, int targetY, String board){
        if(startY==targetY && startX!=targetX){
            int offset=targetX<startX?-1:1;
            for(int i=startX+offset;i!=targetX;i+=offset){
                if(board.charAt(coordToIndex(i, startY))!='.'){
                    return false;
                }
            }
        }else if(startX==targetX && startY!=targetY){
            int offset=targetY<startY?-1:1;
            for(int i=startY+offset;i!=targetY;i+=offset){
                if(board.charAt(coordToIndex(startX, i))!='.'){
                    return false;
                }
            }
        }else{
            return false;
        }

        char start=board.charAt(coordToIndex(startX, startY));
        char target=board.charAt(coordToIndex(targetX, targetY));

        return Character.isLowerCase(start) != Character.isLowerCase(target) || target == '.';
    }

    private boolean validateKing(int startX, int startY, int targetX, int targetY, String board){
        int xDiff=Math.abs((startX-targetX));
        int yDiff=Math.abs((startY-targetY));

        if(xDiff==0 && yDiff==0){
            return false;
        }

        if(xDiff>1 || yDiff>1){
            return false;
        }

        char start=board.charAt(coordToIndex(startX, startY));
        char target=board.charAt(coordToIndex(targetX, targetY));
        if(target=='.'){
            return true;
        }
        if(Character.isLowerCase(start)==Character.isLowerCase(target)){
            return false;
        }

        return true;
    }

    private boolean validateKnight(int startX, int startY, int targetX, int targetY, String board){
        class Coord{
            public int x;
            public int y;

            public Coord(int x, int y) {
                this.x = x;
                this.y = y;
            }
        }

        List<Coord> validPositions=new ArrayList<>();
        validPositions.add(new Coord(startX+1, startY+2));
        validPositions.add(new Coord(startX-1, startY+2));
        validPositions.add(new Coord(startX+1, startY-2));
        validPositions.add(new Coord(startX-1, startY-2));

        validPositions.add(new Coord(startX+2, startY+1));
        validPositions.add(new Coord(startX+2, startY-1));
        validPositions.add(new Coord(startX-2, startY+1));
        validPositions.add(new Coord(startX-2, startY-1));

        boolean valid=false;
        for(Coord coord : validPositions){
            if(coord.x==targetX && coord.y==targetY){
                valid=true;
                break;
            }
        }

        if(!valid){
            return false;
        }

        char start=board.charAt(coordToIndex(startX, startY));
        char target=board.charAt(coordToIndex(targetX, targetY));
        if(target=='.'){
            return true;
        }
        if(Character.isLowerCase(start)==Character.isLowerCase(target)){
            return false;
        }else{
            return true;
        }
    }

    private boolean validateBishop(int startX, int startY, int targetX, int targetY, String board){
        int xDiff=Math.abs(startX-targetX);
        int yDiff=Math.abs(startY-targetY);

        if(xDiff==0 && yDiff==0){
            return false;
        }

        if(xDiff!=yDiff){
            return false;
        }

        int xOffset=startX<targetX?1:-1;
        int yOffset=startY<targetY?1:-1;

        int x=startX+xOffset, y=startY+yOffset;
        while(x!=targetX && y!=targetY){
            if(board.charAt(coordToIndex(x, y))!='.'){
                return false;
            }
            x+=xOffset;
            y+=yOffset;
        }

        char start=board.charAt(coordToIndex(startX, startY));
        char target=board.charAt(coordToIndex(targetX, targetY));
        if(target=='.'){
            return true;
        }
        if(Character.isLowerCase(start)==Character.isLowerCase(target)){
            return false;
        }else{
            return true;
        }
    }

    private boolean validateQueen(int startX, int startY, int targetX, int targetY, String board){
        return validateBishop(startX, startY, targetX, targetY, board) || validateRook(startX, startY, targetX, targetY, board);
    }
}
