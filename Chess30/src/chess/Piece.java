package chess;

import java.io.Serializable;
/**
 * Piece Object
 *  
 *  A <code>Piece</code> object contains the parameters and functionality
 *  for the Piece object
 * @author PatrickBarry
 *
 */

import geometry.Position;

public abstract class Piece implements Serializable {
	
	public Board ParentBoard;
	
	public int side;
	public Position pos;
	
	/**
	 * Constructor for the Piece class
	 * @param i - Player turn
	 */
	public Piece(int i) {
		this.side = i;
	}
	
	/**
	 * First setBoard Method
	 * @param ParentBoard - Board that the chess piece is stored in
	 * @return this - Returns ParentBoard
	 */
	public Piece setBoard(Board ParentBoard) {
		this.ParentBoard = ParentBoard;
		return this;
	}
	
	/**
	 * updatePos Method. Updates current position of piece.
	 * @param x - Updates x coordinate of piece.
	 * @param y - Updates y coordinate of piece.
	 */
	public void updatePos(int x, int y) {
		this.pos.x = x; this.pos.y = y;
	}
	
	/**
	 * Second setBoard Method
	 * @param x - X coordinate of piece.
	 * @param y - Y coordinate of piece.
	 * @param ParentBoard
	 */
	public void setBoard(int x, int y, Board ParentBoard) {
		this.ParentBoard = ParentBoard;
		ParentBoard.layout[y][x] = this;
		this.pos = new Position(x,y);
	}

	/**
	 * Abstract getString Method for specific pieces.
	 */
	public abstract String getString();

	/**
	 * Abstract moveTo Method
	 * @param newpos - New position
	 * @param promotion - Promoted Piece
	 * @throws IllegalMoveException
	 */
	public abstract void moveTo(Position newpos, Piece promotion) throws IllegalMoveException;
	
	
	
	
	
	
	
	
	
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
