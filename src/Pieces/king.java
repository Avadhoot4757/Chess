package Pieces;

import Board.Board;
import Board.Tile;

public class king extends Piece { 
 

	public king(boolean white) 
	{ 
		super(white); 
	} 

	@Override
	public boolean isValidMove(Board board, Tile start, Tile end) 
	{ 
		// we can't move the piece to a Spot that 
		// has a piece of the same color 
		if (end.getPiece().isWhite() == this.isWhite()) { 
			return false; 
		} 

		int x = Math.abs(start.getX() - end.getX()); 
		int y = Math.abs(start.getY() - end.getY()); 
		if (x + y == 1 || x + y == 2 ) { 
			// check if this move will not result in the king 
			// being attacked if so return true 
			return true; 
		} 
        return false;
		 
	} 

	
} 

