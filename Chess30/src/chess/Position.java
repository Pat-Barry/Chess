package chess;

import java.io.Serializable;

public class Position implements Serializable {
	int x;
	int y;
	public Position(int x, int y){
		this.x = x;
		this.y = y;
	}

	public boolean equals(Position p) {
		return this.x == p.x && this.y == p.y;
	}
	public boolean equals(int x, int y) {
		return this.x == x && this.y == y;
	}
	
	public Position addVector(int x, int y) { //Useful to see i.e. if a Piece's position + <0,1> has an enemy. OR USE this.enemyAt(pos)
		return new Position(this.x + x, this.y + y);
	}
}
