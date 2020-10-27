package geometry;

import java.io.Serializable;

/**
 * Position object
 * 
 * A <code>Position</code> object contains the parameters and functionality
 *  for the Position object
 * @author PatrickBarry
 *
 */
public class Position implements Serializable {
	public int x;
	public int y;
	/**
	 * Constructor for Position class.
	 * @param x - Coordinate x for piece.
	 * @param y - Coordinate y for piece.
	 */
	public Position(int x, int y){
		this.x = x;
		this.y = y;
	}

	/**
	 * First equals Method.
	 * @param p - Position of Piece.
	 * @return true - If positions are same. false - If positions are different.
	 */
	public boolean equals(Position p) {
		return this.x == p.x && this.y == p.y;
	}
	
	/**
	 * Second equals Method.
	 * @param x - Coordinate x of Piece.
	 * @param y - Coordinate y of Piece.
	 * @return true - If positions are same. false - If positions are different.
	 */
	public boolean equals(int x, int y) {
		return this.x == x && this.y == y;
	}
	
	/**
	 * addVector Method
	 * @param x - Current x coordinate of piece
	 * @param y - Current y coordinate of piece
	 * @return new Position by adding vector to x and y coordinates
	 */
	public Position addVector(int x, int y) { //Useful to see i.e. if a Piece's position + <0,1> has an enemy. OR USE this.enemyAt(pos)
		return new Position(this.x + x, this.y + y);
	}
}
