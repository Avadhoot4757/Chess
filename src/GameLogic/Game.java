package GameLogic;

import java.awt.Color;
import java.awt.GridBagLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;

import Board.*;
import Pieces.*;
import Players.*;

public class Game {
    private Player[] players = new Player[2];
    private Board board;
    private Player currentTurn;
    private List<Move> movesPlayed;
    private enum GameStatus { 
        ACTIVE, 
        BLACK_WIN, 
        WHITE_WIN, 
        FORFEIT, 
        STALEMATE, 
        RESIGNATION 
    } 
    private GameStatus gameStatus;
    
    public void setStatus(GameStatus status) {
        this.gameStatus = status;
    }

    public Game(){
        movesPlayed = new ArrayList<Move>();
        players[0] = new HumanPlayer(true);
        players[1] = new HumanPlayer(false);
        gameStatus = GameStatus.ACTIVE;
        board = new Board(this);
        currentTurn = players[0];
        
        JFrame frame = new JFrame("Chess");
        frame.setSize(800, 800);
        frame.add(board);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new GridBagLayout());
        frame.getContentPane().setBackground(new Color(47, 79, 79));
        frame.setVisible(true);
    }

    public boolean playerMove(Tile start, Tile end){ 
        Move move = new Move(currentTurn, start, end);
        boolean result = this.makeMove(move, currentTurn);
        board.updateBoard();
        
        return result;  
    } 

    private boolean makeMove(Move move, Player player) {
        Piece sourcePiece = move.getStart().getPiece(); 
        if (sourcePiece == null || player != currentTurn || 
            sourcePiece.isWhite() != player.isWhiteSide() ||
            !sourcePiece.isValidMove(board, move.getStart(), move.getEnd())){
            return false;
        }
        
        Piece destPiece = move.getEnd().getPiece(); 
        if (destPiece != null) {
            destPiece.setAlive(false); 
            move.setPieceKilled(destPiece); 
        }
        
        move.getEnd().setPiece(sourcePiece);
        move.getStart().setPiece(null);

        for(int i = 0; i<8; i++){
            for(int j = 0; j<8; j++){
                Piece p = board.getTile(i, j).getPiece();
                if(p != null && p instanceof King && p.isWhite() == currentTurn.isWhiteSide()){
                    if(Functions.isTileSafe(board, !p.isWhite(), board.getTile(i, j))){
                        movesPlayed.add(move);
                    }
                    else{
                        move.getEnd().setPiece(destPiece);
                        move.getStart().setPiece(sourcePiece);
                        return false;
                    }
                }
            }
        }
        
        if (destPiece != null && destPiece instanceof King) {
            if (player.isWhiteSide()) {
                this.setStatus(GameStatus.WHITE_WIN);
            } else {
                this.setStatus(GameStatus.BLACK_WIN);
            }

            board.handleGameOver(player.isWhiteSide());
        }
        
        currentTurn = (currentTurn == players[0]) ? players[1] : players[0];
        
        return true; 
    }
}
