package chess;
public class Bishop extends Piece{

	public Bishop(int i, int x, int y, Board ParentBoard) {
		super(i, x, y, ParentBoard);
		// TODO Auto-generated constructor stub
	}

	@Override
	public String getString() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void moveTo(Position newpos, Piece promotion) {
		// TODO Auto-generated method stub
		boolean legal = false;
	
	}


	//ALL IN MOVETO
	//Check if legal move for specific piece step shape
	//Check for collisions except knight
	//If piece is same team at end position, throw illegal move
	//Check if it would cause checkmate for your own team, use check for checkmate func
	//If piece is other team, capture
	//If null moveTo

}
