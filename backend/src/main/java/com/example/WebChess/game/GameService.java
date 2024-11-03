package com.example.WebChess.game;

import com.example.WebChess.account.Account;
import com.example.WebChess.account.AccountRepository;
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
    public Optional<Game> create(String username1, String username2){
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

        return Optional.of(saved);
    }

    public List<Game> getAll() {
        return gameRepository.findAll();
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

        //char[][] array=FENtoArray(game.get().getBoardState());
        //System.out.println(array);

        return true;
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
           case 'n'->{

           }
       }

        return false;
    }

    private boolean validatePawn(int startX, int startY, int targetX, int targetY, String board){
        if(Character.isLowerCase(board.charAt(coordToIndex(startX, startY)))){
            if(board.charAt(coordToIndex(targetX, targetY))=='.' && targetX==startX && targetY==startY-1){
                return true;
            }else if( board.charAt(coordToIndex(targetX, targetY))=='.' && startY==6 && targetY==4  && startX==targetX){
                return true;
            }else if(Character.isUpperCase(board.charAt(coordToIndex(targetX, targetY))) && targetY==startY-1 && Math.abs(targetX-startX)==1){
                return true;
            }
        }else{
            if(board.charAt(coordToIndex(targetX, targetY))=='.' && targetX==startX && targetY==startY+1){
                return true;
            }else if(board.charAt(coordToIndex(targetX, targetY))=='.' && startY==1 && targetY==3 && startX==targetX){
                return true;
            }else if(Character.isUpperCase(board.charAt(coordToIndex(targetX, targetY))) && targetY==startY+1 && Math.abs(targetX-startX)==1){
                return true;
            }
        }
        return false;
    }

    private boolean validateRook(int startX, int startY, int targetX, int targetY, String board){
        if(!(startX == targetX || startY == targetY)){
            return false;
        }

        if(startY==targetY){
            int offset=targetX<startX?-1:1;
            for(int i=startX+offset;i<targetX;i+=offset){
                if(board.charAt(coordToIndex(i, startY))!='.'){
                    return false;
                }
            }
        }else if(startX==targetX){
            int offset=targetY<startY?-1:1;
            for(int i=startY+offset;i<targetY;i+=offset){
                if(board.charAt(coordToIndex(startX, i))!='.'){
                    return false;
                }
            }
        }

        char start=board.charAt(coordToIndex(startX, startY));
        char target=board.charAt(coordToIndex(targetX, targetY));

        return Character.isLowerCase(start) != Character.isLowerCase(target) || target == '.';
    }
}
