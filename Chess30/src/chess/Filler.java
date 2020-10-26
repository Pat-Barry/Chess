package chess;

public class Filler extends Piece {

	public Filler(int i) {
		super(i);
		// TODO Auto-generated constructor stub
	}

	@Override
	public String getString() {
		// TODO Auto-generated method stub
		if(side == 1) {
			return "!!";
		} else {
			return "OK";
		}
	}

	@Override
	public void moveTo(Position newpos, Piece promotion) throws IllegalMoveException {
		// TODO Auto-generated method stub
		throw new IllegalMoveException();
		
	}

}
