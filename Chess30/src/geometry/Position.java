package geometry;

import java.io.Serializable;

/**
 * 
 * The Position class's role to store the location of a Piece's reference inside Board.layout.
 * 
 * @author Patrick Barry
 * @author Philip Murray
 *
 */
public class Position implements Serializable {
	public int x;
	public int y;
	
	/**
	 * Constructor for the Position class.
	 * @param x  Coordinate x for a piece.
	 * @param y  Coordinate y for a piece.
	 */
	public Position(int x, int y){
		this.x = x;
		this.y = y;
	}

	/**
	 * Compares if Position p have the same x and y coordinates as this Position. 
	 * 
	 * @param p  Position of Piece.
	 * @return true  If positions are equal. false - If positions are not equal.
	 */
	public boolean equals(Position p) {
		return this.x == p.x && this.y == p.y;
	}
	
	/**
	 * Compares if a Piece's Position constructed from the provided x and y coordinates equals this Position.
	 * 
	 * @param x  Coordinate x of Piece.
	 * @param y  Coordinate y of Piece.
	 * @return true  If positions are equal. false - If positions not equal.
	 */
	public boolean equals(int x, int y) {
		return this.x == x && this.y == y;
	}
	
	/**
	 * Returns a position are translated by a given x and y from this Position.
	 * Useful for Passant-Pawn finding by adding +- (0, 1)
	 * 
	 * @param x  x component of vector
	 * @param y  y component of vector
	 * @return new Position obtained from adding vector from given x and y components to this Position.
	 */
	public Position addVector(int x, int y) { 
		return new Position(this.x + x, this.y + y);
	}
}
