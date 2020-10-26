package chess;
/**
 * Queen Object
 *  
 *  A <code>Queen</code> object contains the parameters and functionality
 *  for the Queen piece
 *  @author PatrickBarry
 */

public class Queen extends Piece{
	
	public Queen(int i, int x, int y, Board ParentBoard) {
		super(i, x, y, ParentBoard);
		// TODO Auto-generated constructor stub
	}
	
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
	 * @throws Exception - If new position has a friendly piece
	 * @throws Exception - If collision detected during Queen movement
	 * @throws Exception - If new position is not a legal move for Queen
	 */
	@Override
	public void moveTo(Position newpos, Piece promotion) throws Exception {
		Vector v = new Vector(newpos, this.pos);
		if(v.variationOf(-1, 1) || v.variationOf(0, 1)) {
			if (this.ParentBoard.noCollisions(pos, newpos)) {
				if (friendAt(newpos)) {
					throw new Exception("Can't Friendly Fire");
				}
				
				else {
					ParentBoard.setPiece(newpos, this);
					ParentBoard.setPiece(pos, null);
				}
			}
			else {
				throw new Exception("Collision Detected in Queen movement");
			}
		}
		else {
			throw new Exception("Vector is not in step shape");
		}
		
	}

}
