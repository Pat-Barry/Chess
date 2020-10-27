package pieces;

import chess.IllegalMoveException;
import chess.Piece;
import geometry.Position;
import geometry.Vector;

/**
 *  Knight Object
 *  
 *  A <code>Knight</code> object contains the parameters and functionality
 *  for the knight piece.
 *  @author PatrickBarry
 */
public class Knight extends Piece{
	
	/**
	 * Constructor for the Knight Class
	 * @param i - Player turn
	 */
	public Knight(int i) {
		super(i);
		this.side = i;
	}
	/**
	 * getString Method
	 * @return bN - If black knight. wN - If white knight.
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
	 * @param newpos - New position
	 * @param promotion - Promoted piece
	 * @throws IllegalMoveException - Fiendly Fire for Knight
	 * @throws IllegalMoveException - Move Vector not allowed
	 */
	@Override
	public void moveTo(Position newpos, Piece promotion) throws IllegalMoveException {
		// TODO Auto-generated method stub
		Vector v = new Vector(newpos, pos);
		if (v.variationOfWithLimit(1, 2, 2)) {
			if (friendAt(newpos)) {
				throw new IllegalMoveException("Fiendly Fire for Knight");
			}
			
			else {
				ParentBoard.setPiece(newpos, this);
				ParentBoard.setPiece(pos, null);
			}
		}
		else {
			throw new IllegalMoveException("Move Vector not allowed");
		}
		
	}
	


}
