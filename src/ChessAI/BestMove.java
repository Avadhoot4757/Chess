package ChessAI;

import Board.Board;
import GameLogic.Game;
import GameLogic.Move;

public class BestMove {
    public static Move findBestMove(Board board){
        board.isAI = true;
        int bestValue = Integer.MAX_VALUE;
        Move bestMove = null;

        for(Move move : Functions.getAllPossibleMoves(board, false)){
            if(board.game.makeMove(move, false)){
                int boardValue = minimax(board, Game.depth - 1, Integer.MIN_VALUE, Integer.MAX_VALUE, true);
                board.game.undoMove();
                if (boardValue < bestValue) {
                    bestValue = boardValue;
                    bestMove = move;
                }
            }
        }

        board.isAI = false;
        return bestMove;
    }

    private static int minimax(Board board, int depth, int alpha, int beta, boolean isWhiteTurn) {
        if(depth == 0 || board.game.isGameOver()){
            return Functions.evaluateBoard(board);
        }

        if(isWhiteTurn){
            int maxEval = Integer.MIN_VALUE;
            for(Move move : Functions.getAllPossibleMoves(board, true)){
                if(board.game.makeMove(move, true)){
                    int eval = minimax(board, depth - 1, alpha, beta, false);
                    board.game.undoMove();
                    maxEval = Math.max(maxEval, eval);
                    alpha = Math.max(alpha, eval);
                    if (beta <= alpha) {
                        break;
                    }
                }
            }
            return maxEval;
        }
        else{
            int minEval = Integer.MAX_VALUE;
            for(Move move : Functions.getAllPossibleMoves(board, false)){
                if(board.game.makeMove(move, false)){
                    int eval = minimax(board, depth - 1, alpha, beta, true);
                    board.game.undoMove();
                    minEval = Math.min(minEval, eval);
                    beta = Math.min(beta, eval);
                    if (beta <= alpha) {
                        break;
                    }
                }
            }
            return minEval;
        }
    }
}
