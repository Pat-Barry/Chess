package chess;

public class Rook extends Piece {

	boolean hasMoved;
	public Rook(int s, int x, int y, Board ParentBoard) {
		super(s, x, y, ParentBoard);
		this.hasMoved = false;
	}

	@Override
	public String getString() {
		if(side == 1) {
			return "bR";
		} else {
			return "wK";
		}
	}

	@Override
	public void moveTo(Position newpos, Piece promotion) throws Exception {
		throw new Exception();
		//this.hasMoved = true;
	}
	
}
