package chess;
public class Bishop extends Piece{

	public Bishop(int i, int x, int y, Board ParentBoard) {
		super(i, x, y, ParentBoard);
	}
	
	public Bishop(int i) {
		super(i);
		this.side = i;
	}

	@Override
	public String getString() {
		// TODO Auto-generated method stub
		if(side == 1) {
			return "bB";
		} else {
			return "wB";
		}
	}

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
