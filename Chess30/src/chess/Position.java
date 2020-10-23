package chess;

public class Position {
	int x;
	int y;
	public Position(int x, int y){
		this.x = x;
		this.y = y;
	}
	public boolean isDeltaFrom(Position p, int x, int y) {
		return this.equals(new Position(p.x + x, p.y + y));
	}
	public boolean equals(Position p) {
		return this.x == p.x && this.y == p.y;
	}
}
