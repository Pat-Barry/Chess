package chess;

import java.io.Serializable;

public abstract class Piece implements Serializable {
	
	Board ParentBoard;
	
	int side;
	Position pos;
	
	
	public Piece(int i, int x, int y, Board ParentBoard) {
		this.side = i;
		this.pos = new Position(x, y);
		this.ParentBoard = ParentBoard;
	}
	
	
	public Piece setBoard(Board ParentBoard) {
		this.ParentBoard = ParentBoard;
		return this;
	}
	
	public void updatePos(int x, int y) {
		this.pos.x = x; this.pos.y = y;
	}

	public abstract String getString();

	public abstract void moveTo(Position newpos, Piece promotion) throws Exception;
	
	public abstract boolean canMoveTo(Position newpos);
	
	
	public boolean enemyAt(Position p) {
		if(this.ParentBoard.getPiece(p) != null) {
			if(this.ParentBoard.getPiece(p).side != this.side) {
				return true;
			}
		}
		return false;
	}
	public boolean collisionAt(Position p) {
		if(this.ParentBoard.getPiece(p) != null) {
			return true;
		}
		return false;
	}
}
