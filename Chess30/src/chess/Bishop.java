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
	
	public Bishop(int i, int x, int y, Board ParentBoard) {
		super(i, x, y, ParentBoard);
		// TODO Auto-generated constructor stub
	}
	
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
	 * @throws Exception - If new position has a friendly piece
	 * @throws Exception - If collision detected during Bishop movement
	 * @throws Exception - If new position is not a legal move for Bishop
	 */
	@Override
	public void moveTo(Position newpos, Piece promotion) throws Exception {
		// TODO Auto-generated method stub
		Vector v = new Vector(newpos, this.pos);
		if(v.variationOf(-1, 1)) {
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
				throw new Exception("Collision Detected in Bishop movement");
			}
		}
		else {
			throw new Exception("Vector is not in step shape");
		}
	
	}


}
