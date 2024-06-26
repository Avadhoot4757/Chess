package Pieces;
import java.awt.Image;
import javax.swing.ImageIcon;

import Board.Board;
import Board.Tile;

public class King extends Piece { 
 

	public King(boolean white) 
	{ 
		super(white); 
		int y = white?0:this.ImageWidth;
		this.image = new ImageIcon(ImageSheet.getSubimage(0*ImageWidth, y, ImageWidth, ImageWidth).getScaledInstance(80, 80, Image.SCALE_SMOOTH));
	} 

	@Override
	public boolean isValidMove(Board board, Tile start, Tile end){
		if (end.getPiece() != null && end.getPiece().isWhite() == this.isWhite()) { 
			return false; 
		} 

		int x = Math.abs(start.getX() - end.getX());
		int y = Math.abs(start.getY() - end.getY());
		if (x + y == 1 || (x == 1 && y == 1)) { 
			boolean opponentColor = !isWhite();
			if(Functions.isTileSafe(board, opponentColor, end))
				return true; 
		} 
        return false;
		 
	} 

	
} 

