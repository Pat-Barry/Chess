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
	public boolean equals(int x, int y) {
		return this.x == x && this.y == y;
	}
	
	public Position addVector(int x, int y) {
		return new Position(this.x + x, this.y + y);
	}
}
