package chess;

public class Rook extends Piece {

	boolean hasMoved = false;
	
	public Rook(int s) {
		super(s);
	}

	@Override
	public String getString() {
		if(side == 1) {
			return "bR";
		} else {
			return "wR";
		}
	}

	@Override
	public void moveTo(Position newpos, Piece promotion) throws IllegalMoveException {
		Vector v = new Vector(newpos, this.pos);
		if(v.variationOf(0, 1)) {
			if (this.ParentBoard.noCollisions(pos, newpos)) {
				if (friendAt(newpos)) {
					throw new IllegalMoveException("Can't Friendly Fire");
				}
				
				else {
					ParentBoard.setPiece(newpos, this);
					ParentBoard.setPiece(pos, null);
					this.hasMoved = true;
				}
			}
			else {
				throw new IllegalMoveException("Collision Detected in Rook movement");
			}
		}
		else {
			throw new IllegalMoveException("Vector is not in step shape");
		}
	}
	
}
