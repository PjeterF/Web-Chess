package com.example.WebChess.chess;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

public class ChessEvaluator {
    private char[][] board;
    private StringBuilder stringBuilder;
    private static int[][] weights;
    static{
        weights= new int[][]{
            {1, 1, 1, 1, 1, 1, 1, 1},
            {1, 1, 1, 1, 1, 1, 1, 1},
            {1, 1, 1, 1, 1, 1, 1, 1},
            {1, 1, 1, 1, 1, 1, 1, 1},
            {1, 1, 1, 1, 1, 1, 1, 1},
            {1, 1, 1, 1, 1, 1, 1, 1},
            {1, 1, 1, 1, 1, 1, 1, 1},
            {1, 1, 1, 1, 1, 1, 1, 1},
        };

        for(int y=0;y<8;y++){
            for(int x=0;x<8;x++){
                weights[y][x]=5-Math.abs(4-y);
            }
        }
    }

    public ChessEvaluator(String boardString){
        stringBuilder=new StringBuilder();
        board=new char[8][8];
        for(int y=0;y<8;y++){
            for(int x=0;x<8;x++){
                board[y][x]=boardString.charAt(x+y*8);
            }
        }
    }

    public void printBoard(){
        for(int y=0;y<8;y++){
            for(int x=0;x<8;x++){
                System.out.print(board[y][x]+" ");
            }
            System.out.print('\n');
        }
    }

    public boolean isCoordinateInRange(int x, int y){
        if(x<0 || x>7 || y<0 || y>7){
            return false;
        }
        return true;
    }

    public String getBoardString(){
        StringBuilder builder=new StringBuilder();

        for(int y=0;y<8;y++){
            for(int x=0;x<8;x++){
                builder.append(board[y][x]);
            }
        }

        return builder.toString();
    }

    public void makeAMove(String start, String target){
        int[] s=decodePosition(start);
        int[] t=decodePosition(target);

        if(isCoordinateInRange(s[0], s[1]) && isCoordinateInRange(t[0], t[1])){
            board[t[1]][t[0]]=board[s[1]][s[0]];
            board[s[1]][s[0]]='.';
        }
    }

    public boolean validateMove(int startX, int startY, int targetX, int targetY){
        var validMoves=getValidMoves(startX, startY);
        for(var move : validMoves){
            if(move.equals(encodePosition(targetX, targetY))){
                return true;
            }
        }
        return false;
    }

    public boolean validateMove(String start, String target){
        var validMoves=getValidMoves(start);
        for(var move : validMoves){
            if(move.equals(target)){
                return true;
            }
        }
        return false;
    }

    public Collection<String> getValidMoves(int x, int y){
        if(x<0 || x>7 || y<0 || y>7){
            throw new IllegalArgumentException("Position out of bounds");
        }

        char piece=board[y][x];
        if(piece=='.'){
            throw new RuntimeException("No pieces at given position");
        }

        switch (Character.toLowerCase(piece)){
            case 'p'->{
                return getPawnMoves(x, y);
            }
            case 'r'->{
                return getRookMoves(x, y);
            }
            case 'k'->{
                return getKingMoves(x, y);
            }
            case 'n'->{
                return getKnightMoves(x, y);
            }
            case 'b'->{
                return getBishopMoves(x, y);
            }
            case 'q'->{
                return getQueenMoves(x, y);
            }
        }

        throw new RuntimeException("Corrupted board state");
    }

    public Collection<String> getValidMoves(String encodedPosition){
        int[] decodedPosition=decodePosition(encodedPosition);
        return getValidMoves(decodedPosition[0], decodedPosition[1]);
    }

    public String encodePosition(int x, int y){
        stringBuilder.append((char)('a'+x));
        stringBuilder.append(8-y);
        String result=stringBuilder.toString();
        stringBuilder.setLength(0);
        return result;
    }
    public int[] decodePosition(String encoded){
        int[] result=new int[2];

        result[0]=encoded.charAt(0)-'a';
        result[1]=8-Character.getNumericValue(encoded.charAt(1));

        return result;
    }

    private boolean addMove(Collection<String> moves, int x, int y, boolean isWhite){
        if(board[y][x]=='.'){
            moves.add(encodePosition(x, y));
            return true;
        }
        if(Character.isUpperCase(board[y][x]) != isWhite){
            moves.add(encodePosition(x, y));
            return false;
        }
        return false;
    }

    private HashSet<String> getRookMoves(int xS, int yS){
        HashSet<String> result=new HashSet<>();
        boolean isWhite=Character.isUpperCase(board[yS][xS]);

        for(int i=xS+1;i<8;i++){
            if(!addMove(result, i, yS, isWhite)){
                break;
            }
        }
        for(int i=xS-1;i>=0;i--){
            if(!addMove(result, i, yS, isWhite)){
                break;
            }
        }
        for(int i=yS+1;i<8;i++){
            if(!addMove(result, xS, i, isWhite)){
                break;
            }
        }
        for(int i=yS-1;i>=0;i--){
            if(!addMove(result, xS, i, isWhite)){
                break;
            }
        }

        return result;
    }

    private HashSet<String> getBishopMoves(int xS, int yS){
        HashSet<String> result=new HashSet<>();
        boolean isWhite=Character.isUpperCase(board[yS][xS]);

        {
            int x=xS+1, y=yS+1;
            while(x>=0 && x<8 && y>=0 && y<8){
                if(!addMove(result, x, y, isWhite)){
                    break;
                }
                x++;
                y++;
            }
        }
        {
            int x=xS+1, y=yS-1;
            while(x>=0 && x<8 && y>=0 && y<8){
                if(!addMove(result, x, y, isWhite)){
                    break;
                }
                x++;
                y--;
            }
        }
        {
            int x=xS-1, y=yS+1;
            while(x>=0 && x<8 && y>=0 && y<8){
                if(!addMove(result, x, y, isWhite)){
                    break;
                }
                x--;
                y++;
            }
        }
        {
            int x=xS-1, y=yS-1;
            while(x>=0 && x<8 && y>=0 && y<8){
                if(!addMove(result, x, y, isWhite)){
                    break;
                }
                x--;
                y--;
            }
        }

        return result;
    }

    private HashSet<String> getKnightMoves(int xS, int yS){
        ArrayList<int[]> possiblePositions = new ArrayList<>();
        possiblePositions.add(new int[]{xS+1, yS+2});
        possiblePositions.add(new int[]{xS-1, yS+2});
        possiblePositions.add(new int[]{xS+1, yS-2});
        possiblePositions.add(new int[]{xS-1, yS-2});
        possiblePositions.add(new int[]{xS+2, yS+1});
        possiblePositions.add(new int[]{xS+2, yS-1});
        possiblePositions.add(new int[]{xS-2, yS+1});
        possiblePositions.add(new int[]{xS-2, yS-1});

        HashSet<String> result=new HashSet<>();
        boolean isWhite=Character.isUpperCase(board[yS][xS]);

        for(int[] pos : possiblePositions){
            int x=pos[0], y=pos[1];
            if(x<0 || x>7 || y<0 || y>7){
                continue;
            }
            if(Character.isUpperCase(board[y][x])!=isWhite || board[y][x]=='.'){
                result.add(encodePosition(x, y));
            }
        }

        return result;
    }

    private HashSet<String> getQueenMoves(int xS, int yS){
        HashSet<String> result=new HashSet<>();
        result.addAll(getBishopMoves(xS, yS));
        result.addAll(getRookMoves(xS, yS));
        return result;
    }

    private HashSet<String> getKingMoves(int xS, int yS){
        HashSet<String> result=new HashSet<>();
        boolean isWhite=Character.isUpperCase(board[yS][xS]);

        for(int y=yS-1;y<=yS+1;y++){
            for(int x=xS-1;x<=xS+1;x++){
                if(x==xS && y==yS){
                    continue;
                }
                if(x<0 || x>7 || y<0 || y>7){
                    continue;
                }
                addMove(result, x, y, isWhite);
            }
        }

        return result;
    }

    private HashSet<String> getPawnMoves(int xS, int yS){
        HashSet<String> result=new HashSet<>();
        boolean isWhite=Character.isUpperCase(board[yS][xS]);

        int direction=isWhite?-1:1;
        int startingRow=isWhite?6:1;

        boolean couldMove=false;
        if(board[yS+direction][xS]=='.'){
            couldMove=true;
            result.add(encodePosition(xS, yS+direction));
        }
        if(yS==startingRow && couldMove){
            if(board[yS+direction*2][xS]=='.'){
                result.add(encodePosition(xS, yS+direction*2));
            }
        }

        int[] dx={-1, 1};
        for (int deltaX : dx) {
            int newX = xS + deltaX;
            int newY = yS + direction;
            if (newX >= 0 && newX < 8 && newY >= 0 && newY < 8) {
                char target = board[newY][newX];
                if (target != '.' && Character.isUpperCase(target) != isWhite) {
                    result.add(encodePosition(newX, newY));
                }
            }
        }

        return result;
    }

    private int evaluateScore(){
        int score=0;

        for(int y=0;y<8;y++){
            for(int x=0;x<8;x++){
                if(board[y][x]=='.'){
                    continue;
                }
                int sign=Character.isUpperCase(board[y][x])?1:-1;
                switch (Character.toLowerCase(board[y][x])){
                    case 'p'->{score+=sign*100;}
                    case 'r'->{score+=sign*300;}
                    case 'k'->{score+=sign*10000;}
                    case 'n'->{score+=sign*500;}
                    case 'b'->{score+=sign*300;}
                    case 'q'->{score+=sign*1000;}
                }
                score+=weights[y][x];
            }
        }

        return score;
    }

    public int minimax(int depth, boolean isWhite, String[] bestMoveResult, int alpha, int beta){
        if(depth==0){
            return evaluateScore();
        }

        String[] currentBestMove=new String[2];
        int evaluationScore=isWhite?Integer.MIN_VALUE:Integer.MAX_VALUE;

        for(int y=0;y<8;y++){
            for(int x=0;x<8;x++) {
                if(board[y][x]!='.' && Character.isUpperCase(board[y][x])==isWhite){
                    for(String move : getValidMoves(x, y)){
                        int [] decodedMove=decodePosition(move);
                        char movingPiece=board[y][x];
                        char targetPiece=board[decodedMove[1]][decodedMove[0]];

                        board[decodedMove[1]][decodedMove[0]]=movingPiece;
                        board[y][x]='.';

                        int evaluation=minimax(depth-1, !isWhite, bestMoveResult, alpha, beta);

                        board[decodedMove[1]][decodedMove[0]]=targetPiece;
                        board[y][x]=movingPiece;

                        if(isWhite){
                            if(evaluation>evaluationScore){
                                evaluationScore=evaluation;
                                currentBestMove[0]=encodePosition(x, y);
                                currentBestMove[1]=move;
                            }
                            alpha=Math.max(evaluationScore, alpha);
                        }else{
                            if(evaluation<evaluationScore){
                                evaluationScore=evaluation;
                                currentBestMove[0]=encodePosition(x, y);
                                currentBestMove[1]=move;
                            }
                            beta=Math.min(evaluationScore, beta);
                        }
                        if(beta<=alpha){
                            return evaluationScore;
                        }
                    }
                }
            }
        }

        if (bestMoveResult!=null && currentBestMove[0]!=null && currentBestMove[1]!=null) {
            bestMoveResult[0] = currentBestMove[0];
            bestMoveResult[1] = currentBestMove[1];
        }

        return evaluationScore;
    }
}
