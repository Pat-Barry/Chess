package chess;
/**
 * Rook Object
 * 
 * A <code>Rook</code> object contains the parameters and functionality
 *  for the Rook piece
 * @author PatrickBarry
 *
 */

public class Rook extends Piece {

	boolean hasMoved;
	
	public Rook(int s, int x, int y, Board ParentBoard) {
		super(s, x, y, ParentBoard);
		this.hasMoved = false;
	}
	
	/**
	 * Constructor for the Rook class
	 * @param i - Player turn
	 */
	public Rook(int i) {
		super(i);
		this.side = i;
		this.hasMoved = false;
	}
	/**
	 * getString Method
	 * @return bR - If black Rook. wR - If white Rook.
	 */
	@Override
	public String getString() {
		if(side == 1) {
			return "bR";
		} else {
			return "wR";
		}
	}
	
	/**
	 * moveTo Method
	 * @param newpos - New position
	 * @param promotion - Promoted piece
	 * @throws Exception - If new position has a friendly piece
	 * @throws Exception - If collision detected during Rook movement
	 * @throws Exception - If new position is not a legal move for Rook
	 */
	@Override
	public void moveTo(Position newpos, Piece promotion) throws Exception {
		Vector v = new Vector(newpos, this.pos);
		if(v.variationOf(0, 1)) {
			if (this.ParentBoard.noCollisions(pos, newpos)) {
				if (friendAt(newpos)) {
					throw new Exception("Can't Friendly Fire");
				}
				
				else {
					ParentBoard.setPiece(newpos, this);
					ParentBoard.setPiece(pos, null);
					this.hasMoved = true;
				}
			}
			else {
				throw new Exception("Collision Detected in Rook movement");
			}
		}
		else {
			throw new Exception("Vector is not in step shape");
		}
	}
	
}
