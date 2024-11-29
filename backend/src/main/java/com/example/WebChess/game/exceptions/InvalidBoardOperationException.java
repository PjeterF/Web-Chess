package com.example.WebChess.game.exceptions;

public class InvalidBoardOperationException extends RuntimeException {
    public InvalidBoardOperationException(String message) {
        super(message);
    }
}
