package com.example.WebChess;

import com.example.WebChess.account.AccountService;
import com.example.WebChess.chess.ChessEvaluator;
import com.example.WebChess.game.Game;
import com.example.WebChess.game.GameDTO_accountIDs;
import com.example.WebChess.game.GameService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Optional;

@SpringBootApplication
public class WebChessApplication {

	public static void main(String[] args) {
		SpringApplication.run(WebChessApplication.class, args);
	}

	@Bean
	CommandLineRunner commandLineRunner(AccountService accountService, GameService gameService){
		return args->{
			accountService.createAccount("User1");
			accountService.createAccount("User2");

			Optional<GameDTO_accountIDs> game=gameService.create("User1", "User2");

			if(game.isPresent()){
				gameService.makeAMove(game.get().getId(), 0, 0, 1, 1);
			}

			ChessEvaluator evaluator=new ChessEvaluator("rnbqkbnrpppppppp................................PPPPPPPPRNBQKBNR");
//			try{
//				System.out.println("Moves:");
//				for(String move : evaluator.getValidMoves(1, 0)){
//					System.out.println(move);
//				}
//			}catch (Throwable error){
//				System.out.println(error.toString());
//			}

			String[] bestMove=new String[2];
			boolean isWhite=false;
			for(int i=0;i<50;i++){
				evaluator.minimax(4, isWhite, bestMove, Integer.MIN_VALUE, Integer.MAX_VALUE);
				evaluator.makeAMove(bestMove[0], bestMove[1]);
				isWhite=!isWhite;
				System.in.read();
				System.out.flush();
				evaluator.printBoard();
			}
			System.out.flush();
			evaluator.printBoard();
        };
	}
}
