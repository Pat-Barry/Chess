package chess;
/**
 * Bishop Object
 * 
 * A <code>Bishop</code> object contains the parameters and functionality
 *  for the Bishop piece
 * @author PatrickBarry
 *
 */
public class Bishop extends Piece{
	
	
	/**
	 * Constructor for the Bishop Class
	 * @param i - Player turn
	 */
	public Bishop(int i) {
		super(i);
		this.side = i;
	}

	/**
	 * getString Method
	 * @return bB - If black Bishop. wB - If white Bishop.
	 */
	@Override
	public String getString() {
		// TODO Auto-generated method stub
		if(side == 1) {
			return "bB";
		} else {
			return "wB";
		}
	}
	
	/**
	 * moveTo Method
	 * @param newpos - New position
	 * @param promotion - Promoted piece
	 * @throws IllegalMoveException - If new position has a friendly piece
	 * @throws IllegalMoveException - If collision detected during Bishop movement
	 * @throws IllegalMoveException - If new position is not a legal move for Bishop
	 */
	@Override
	public void moveTo(Position newpos, Piece promotion) throws IllegalMoveException {
		// TODO Auto-generated method stub
		Vector v = new Vector(newpos, this.pos);
		if(v.variationOf(-1, 1)) {
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
				throw new IllegalMoveException("Collision Detected in Bishop movement");
			}
		}
		else {
			throw new IllegalMoveException("Vector is not in step shape");
		}
	
	}


}
