package pieces;

import chess.IllegalMoveException;
import chess.Piece;
import geometry.Position;
import geometry.Vector;

/**
 * Knight Class
 * 
 * A <code>Knight</code> is the representation of a knight chess piece, implementing the abstract methods declared in Piece.
 *  
 * @author Patrick Barry
 * @author Philip Murray
 *
 */
public class Knight extends Piece{
	
	/**
	 * Constructor for the Knight class
	 * @param s - side (0: white, 1: black) of the Knight
	 */
	public Knight(int s) {
		super(s);
	}
	
	/**
	 * getString Method
	 * Returns ASCII representation of this Knight. 
	 * Used in Board.render method
	 * @return bN - If black Knight. wN - If white Knight
	 */
	@Override
	public String getString() {
		if(side == 1) {
			return "bN";
		} else {
			return "wN";
		}
	}

	/**
	 * moveTo Method
	 * This is the Knight's implementation of the moveTo method. 
	 * Performs various legality checks on requested movement. 
	 * If a check fails, an IllegalMoveException is thrown corresponding to the failed check.
	 * Move is applied on this Piece's ParentBoard.
	 * 
	 *  
	 * @param newpos - Position the Piece is moving to
	 * @param promotion - Not used in Knight's implementation
	 * @throws IllegalMoveException - Cannot capture same-side Piece
	 * @throws IllegalMoveException - Move Vector does not match any of the allowed movement vectors for this Piece
	 */
	@Override
	public void moveTo(Position newpos, Piece promotion) throws IllegalMoveException {
		// TODO Auto-generated method stub
		Vector v = new Vector(newpos, pos);
		if (v.variationOfWithLimit(1, 2, 2)) {
			if (friendAt(newpos)) {
				throw new IllegalMoveException("Cannot capture same-side Piece");
			}
			
			else {
				ParentBoard.setPiece(newpos, this);
				ParentBoard.setPiece(pos, null);
			}
		}
		else {
			throw new IllegalMoveException("Move Vector does not match any of the allowed movement vectors for this Piece");
		}	
	}
	
}
