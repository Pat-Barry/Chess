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

	public abstract void moveTo(int end_i, int end_j, Piece promotion) throws Exception;
	
}
