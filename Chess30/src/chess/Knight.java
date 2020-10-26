package chess;

public class Knight extends Piece{

	public Knight(int i, int x, int y, Board ParentBoard) {
		super(i, x, y, ParentBoard);
		
	}
	
	public Knight(int i) {
		super(i);
		this.side = i;
	}

	@Override
	public String getString() {
		if(side == 1) {
			return "bN";
		} else {
			return "wN";
		}
	}

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
