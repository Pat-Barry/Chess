package pieces;

import chess.IllegalMoveException;
import chess.Piece;
import geometry.Position;
import geometry.Vector;

/**
 * 
 * A Queen is the representation of a queen chess piece, implementing the abstract methods declared in Piece.
 *  
 * @author Patrick Barry
 * @author Philip Murray
 *
 */

public class Queen extends Piece{
	
	
	/**
	 * Constructor for the Queen class
	 * @param s  side (0: white, 1: black) of the Queen
	 */
	public Queen(int s) {
		super(s);
	}

	/**
	 * Returns ASCII representation of the Queen
	 * Used in Board.render method
	 * @return bQ - If black Queen. wQ - If white Queen.
	 */
	@Override
	public String getString() {
		if(side == 1) {
			return "bQ";
		} else {
			return "wQ";
		}
	}
	
	/**
	 * This is the Queen's implementation of the moveTo method. 
	 * Performs various legality checks on requested movement. 
	 * If a check fails, an IllegalMoveException is thrown corresponding to the failed check.
	 * Move is applied on this Piece's ParentBoard.
	 * 
	 *  
	 * @param newpos  Position the Piece is moving to
	 * @param promotion  Not used in Queen's implementation
	 * @throws IllegalMoveException  Cannot capture same-side Piece
	 * @throws IllegalMoveException  Cannot move through a Piece
	 * @throws IllegalMoveException  Move Vector does not match any of the allowed movement vectors for this Piece
	 */
	@Override
	public void moveTo(Position newpos, Piece promotion) throws IllegalMoveException {
		Vector v = new Vector(newpos, this.pos);
		if(v.variationOf(-1, 1) || v.variationOf(0, 1)) {
			if (this.ParentBoard.noCollisions(pos, newpos)) {
				if (friendAt(newpos)) {
					throw new IllegalMoveException("Cannot capture same-side Piece");
				}
				
				else {
					ParentBoard.setPiece(newpos, this);
					ParentBoard.setPiece(pos, null);
				}
			}
			else {
				throw new IllegalMoveException("Cannot move through a Piece");
			}
		}
		else {
			throw new IllegalMoveException("Move Vector does not match any of the allowed movement vectors for this Piece");
		}
		
	}

}
