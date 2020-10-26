package chess;
/**
 *  Knight Object
 *  
 *  A <code>Knight</code> object contains the parameters and functionality
 *  for the knight piece.
 *  @author PatrickBarry
 */
public class Knight extends Piece{
	/**
	 * First constructor for the knight class
	 * @param i - Player turn
	 * @param x - Coordinate x on board
	 * @param y - Coordinate y on board
	 * @param ParentBoard
	 */
	public Knight(int i, int x, int y, Board ParentBoard) {
		super(i, x, y, ParentBoard);
		
	}
	/**
	 * Second Constructor for the Knight Class
	 * @param i - Player turn
	 */
	public Knight(int i) {
		super(i);
		this.side = i;
	}
	/**
	 * getString Method
	 * @return bN - If black knight
	 * @return wN - If white knight
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
	 * @param promotion - Promoted Piece
	 * @throws Exception - If new position has a friendly piece
	 * @throws Exception - If new position is not a legal move for Knight
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
			throw new IllegalMoveException("Vector not in step shape");
		}
		
	}
	


}
