package pieces;

import chess.IllegalMoveException;
import chess.Piece;
import geometry.Position;
import geometry.Vector;

/**
 * Queen Object
 *  
 *  A <code>Queen</code> object contains the parameters and functionality
 *  for the Queen piece
 *  @author PatrickBarry
 */

public class Queen extends Piece{
	
	
	/**
	 * Constructor for the Queen class
	 * @param i - Player turn
	 */
	public Queen(int i) {
		super(i);
		this.side = i;
	}

	/**
	 * getString Method
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
	 * moveTo Method
	 * @param newpos - New position
	 * @param promotion - Promoted piece
	 * @throws IllegalMoveException - Can't Friendly Fire
	 * @throws IllegalMoveException - Collision Detected in Queen movement
	 * @throws IllegalMoveException - Move Vector not allowed
	 */
	@Override
	public void moveTo(Position newpos, Piece promotion) throws IllegalMoveException {
		Vector v = new Vector(newpos, this.pos);
		if(v.variationOf(-1, 1) || v.variationOf(0, 1)) {
			if (this.ParentBoard.noCollisions(pos, newpos)) {
				if (friendAt(newpos)) {
					throw new IllegalMoveException("Can't Friendly Fire");
				}
				
				else {
					ParentBoard.setPiece(newpos, this);
					ParentBoard.setPiece(pos, null);
				}
			}
			else {
				throw new IllegalMoveException("Collision Detected in Queen movement");
			}
		}
		else {
			throw new IllegalMoveException("Move Vector not allowed");
		}
		
	}

}
