package com.example.WebChess;

import com.example.WebChess.account.AccountRole;
import com.example.WebChess.account.AccountService;
import com.example.WebChess.chess.ChessEvaluator;
import com.example.WebChess.configuration.JWTService;
import com.example.WebChess.game.Game;
import com.example.WebChess.game.GameDTO;
import com.example.WebChess.game.GameService;
import com.example.WebChess.game.requests.MoveRequest;
import com.example.WebChess.game.requests.UndoMoveRequest;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;

@SpringBootApplication
public class WebChessApplication {

	public static void main(String[] args) {
		SpringApplication.run(WebChessApplication.class, args);
	}

	@Bean
	CommandLineRunner commandLineRunner(AccountService accountService, GameService gameService){
		return args->{
			accountService.createAccount("User1", "123", AccountRole.USER);
			accountService.createAccount("User2", "qwe", AccountRole.USER);
			accountService.createAccount("Computer", "COMPUTER", AccountRole.COMPUTER);

			String tok=accountService.login("User2", "qwe");
			System.out.println(tok);
        };
	}
}
