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
	
	public Piece(int i) {
		this.side = i;
	}
	
	public void setBoard(int x, int y, Board ParentBoard) {
		this.ParentBoard = ParentBoard;
		ParentBoard.layout[y][x] = this;
		this.pos = new Position(x,y);
	}

	public abstract String getString();

	public abstract void moveTo(Position newpos, Piece promotion) throws Exception;
	
	
	
	
	
	
	
	
	
	public boolean enemyAt(Position p) { //Returns true if enemy at position, false if null or same-team piece
		if(this.ParentBoard.getPiece(p) != null) {
			if(this.ParentBoard.getPiece(p).side != this.side) {
				return true;
			}
		}
		return false;
	}
	public boolean friendAt(Position p) { //Returns true if friend at position, false if null or enemy piece
		if(this.ParentBoard.getPiece(p) != null) {
			if(this.ParentBoard.getPiece(p).side == this.side) {
				return true;
			}
			return false;
		}
		return false;
	}
	public boolean collisionAt(Position p) { //Returns true if piece is null, false if friend or enemy piece
		if(this.ParentBoard.getPiece(p) != null) {
			return true;
		}
		return false;
	}
}
