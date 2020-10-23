package chess;
public abstract class Piece {
	
	int side;
	Position pos;
	
	
	public Piece(int i, int x, int y) {
		this.side = i;
		this.pos = new Position(x, y);
	}
	public void updatePos(int x, int y) {
		this.pos.x = x; this.pos.y = y;
	}

	public abstract String getString();

	public abstract void moveTo(Position newpos, Piece promotion) throws Exception;
	
	
	
	
	public boolean enemyAt(Position p) {
		if(Board.getPiece(p) != null) {
			if(Board.getPiece(p).side != this.side) {
				return true;
			}
		}
		return false;
	}
	public boolean collisionAt(Position p) {
		if(Board.getPiece(p) != null) {
			return true;
		}
		return false;
	}
}
