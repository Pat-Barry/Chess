package chess;

public class Rook extends Piece {

	boolean hasMoved;
	public Rook(int s, int x, int y, Board ParentBoard) {
		super(s, x, y, ParentBoard);
		this.hasMoved = false;
	}
	
	public Rook(int i) {
		super(i);
		this.side = i;
		this.hasMoved = false;
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
