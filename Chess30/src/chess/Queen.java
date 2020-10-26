package chess;

public class Queen extends Piece{

	public Queen(int i, int x, int y, Board ParentBoard) {
		super(i, x, y, ParentBoard);
		// TODO Auto-generated constructor stub
	}
	
	public Queen(int i) {
		super(i);
		this.side = i;
	}

	@Override
	public String getString() {
		if(side == 1) {
			return "bQ";
		} else {
			return "wQ";
		}
	}

	@Override
	public void moveTo(Position newpos, Piece promotion) throws IllegalMoveException {
		Vector v = new Vector(newpos, this.pos);
		if(v.variationOf(-1, 1) || v.variationOf(0, 1)) {
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
				throw new IllegalMoveException("Collision Detected in Queen movement");
			}
		}
		else {
			throw new IllegalMoveException("Vector is not in step shape");
		}
		
	}

}
