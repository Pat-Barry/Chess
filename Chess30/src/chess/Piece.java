package chess;

import java.io.Serializable;
import geometry.Position;

/**
 * 
 * Piece Object
 *  
 *  The <code>Piece</code> abstract class defines the functionality of a Piece of a chess game. 
 *  It is extended by Pawn, Knight, Bishop, Rook, Queen and King, which implement its two abstract methods getString and moveTo.
 *  Most notably, the implementation of the moveTo method houses logic responsible for determining if a move is valid for the Piece to make. 
 *
 *  Instances of the Piece class are housed in an instance of a chess Board, which has a double-array to represent the Piece's relative locations on the Board. 
 *  The Piece class also has helper methods to determine if one of it's friend/enemy/(or null reference) Pieces is located at a specific spot on the board. 
 *  
 *  
 * @author Patrick Barry
 * @author Philip Murray
 *
 */
public abstract class Piece implements Serializable {
	
	/** The chess Board that the Piece is stored in */
	public Board ParentBoard;
	
	/** The Piece's position for it's location on the board
	 *  Matches the Board's layout's yth xth indexical reference for the Piece. 
	 */
	public Position pos;
	
	/** The Piece's team/side (0: white, 1: black) */
	public int side;
	
	
	/**
	 * Constructor for the Piece class
	 * @param s - side (0: white, 1: black) of the Piece
	 */
	public Piece(int s) {
		this.side = s;
	}
	
	/**
	 * setBoard Method
	 * Sets this Piece's ParentBoard to the provided Board for reference
	 * Sets the ParentBoard's yth xth indexical position to reference this Piece
	 * Sets this Piece's internal position to that of the ParentBoard
	 * 
	 * @param x - X coordinate of piece.
	 * @param y - Y coordinate of piece.
	 * @param ParentBoard - Board that this Piece is stored in, and that this Piece stores.
	 */
	public void setBoard(int x, int y, Board ParentBoard) {
		this.ParentBoard = ParentBoard;
		ParentBoard.layout[y][x] = this;
		this.pos = new Position(x,y);
	}
	

	/**
	 * updatePos Method. 
	 * Updates this Piece's internal position with a position located at the provided x and y coordinates
	 * @param x - Updates x coordinate of piece.
	 * @param y - Updates y coordinate of piece.
	 */
	public void updatePos(int x, int y) {
		this.pos.x = x; this.pos.y = y;
	}
	

	/**
	 * Abstract getString Method
	 * 
	 * Returns ASCII representation of the Piece
	 * Used in Board.render method
	 * @return bP - If black Piece. wP - If white Piece. Where P can be p, N, B, R, Q, or K
	 */
	public abstract String getString();

	
	/**
	 * Abstract moveTo Method
	 * 
	 * For each Piece implementation:
	 * Performs various legality checks on requested movement. 
	 * If a check fails, an IllegalMoveException is thrown corresponding to the failed check.
	 * Move is applied on this Piece's ParentBoard.
	 * 
	 * @param newpos - Position the Piece is moving to
	 * @param promotion - Optional depending on implementation
	 * @throws IllegalMoveException  Types vary depending on implementation
	 */
	public abstract void moveTo(Position newpos, Piece promotion) throws IllegalMoveException;
	
	
	
	/**
	 * enemyAt Method
	 * 
	 * Checks an enemy of this Piece is located at the provided position. 
	 * 
	 * @param p - Position that is checked.
	 * @return true - if enemy is located at Position p. false - if none or same-side piece is located at Position p. 
	 */
	public boolean enemyAt(Position p) { 
		if(this.ParentBoard.getPiece(p) != null) {
			if(this.ParentBoard.getPiece(p).side != this.side) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * enemyAt Method
	 * 
	 * Checks an same-side piece (relative to this Piece) is located at the provided position. 
	 * 
	 * @param p - Position that is checked.
	 * @return true - if friend is located at Position p. false - if none or enemy piece is located at Position p. 
	 */
	public boolean friendAt(Position p) {
		if(this.ParentBoard.getPiece(p) != null) {
			if(this.ParentBoard.getPiece(p).side == this.side) {
				return true;
			}
			return false;
		}
		return false;
	}
	
	/**
	 * collisionAt Method
	 * 
	 * Checks a Piece is located at the provided position. 
	 * 
	 * @param p - Position that is checked.
	 * @return true - if a Piece is located at Position p. false - if no Piece is located at p. 
	 */
	public boolean collisionAt(Position p) {
		if(this.ParentBoard.getPiece(p) != null) {
			return true;
		}
		return false;
	}
}
