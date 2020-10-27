package geometry;

/**
 * Vector Class
 *  
 *  The <code>Vector</code> class's role is to represent the "shape" of a Piece's movement from it's position to a new position.
 *  Has methods to compute whether or not a Piece's movement is of an allowed "shape", i.e. diagonal for Bishop, "L" shaped for Knight. 
 *  
 * @author Patrick Barry
 * @author Philip Murray
 */
public class Vector {
	public int x;
	public int y;
	
	/**
	 * First constructor for vector. Creates a vector with the given x and y components.
	 * @param x - x direction component of vector
	 * @param y - y direction component of vector
	 */
	public Vector(int x, int y){
		this.x = x;
		this.y = y;
	}
	
	/**
	 * Second constructor for vector. Creates a vector from subtracting the x and y components of the first position from the second position.
	 * @param np - Prospective new position of piece
	 * @param p - Current position of piece
	 * @throws IllegalArgumentException - Vector cannot be a 0 0 vector. Such occurs when moving xn to xn. 
	 */
	public Vector(Position np, Position p) { 
		this.x = np.x - p.x;
		this.y = np.y - p.y;
		if(this.x == 0 && this.y == 0) {
			System.out.println("Found zero vector");
			throw new IllegalArgumentException("Vector cannot be a 0 0 vector");
		}	
	}
	
	/**
	 * First equals Method
	 * Compares if vector has the same x and y components as this vector.
	 * @param v - Vector to be compared with current vector.
	 * @return true - Vector is equal. false - Vector is not equal.
	 */
	public boolean equals(Vector v) {
		return this.x == v.x && this.y == v.y;
	}
	/**
	 * Second equals Method
	 * Compares if vector has the same x and y components as this vector.
	 * @param x - X component of vector which is compared to current vector.
	 * @param y - Y component of vector which is compared to current vector.
	 * @return true - Vector is equal. false - Vector is not equal.
	 */
	public boolean equals(int x, int y) {
		return this.x == x && this.y == y;
	}
	
	/**
	 * variationOf Method
	 * This method is used to validate that a Piece's movement vector is a permissible movement pattern for that Piece.
	 * Checks if the Piece's movement vector can match the pattern vector by applying a rotation, reflection or scalar transformation.
	 * 
	 * Use N = <1, 2>, R = <0, 1>, B = <1, 1>, Q = <0,1> || <1,1>
	 * 
	 * @param x - X component of pattern vector which is compared to current vector.
	 * @param y - Y component of pattern vector which is compared to current vector.
	 * @return true - If this vector is a variation of the given pattern vector. false - If this vector is not a variation of the pattern vector.
	 * @throws IllegalArguementException - If provided a zero vector. Converted into IllegalMoveException upon being catched. 
	 */
	public boolean variationOf(int x, int y) {
		
		if( (x == 0 && y == 0)) {
			throw new IllegalArgumentException();
		} 
		if( x == 0 || y == 0 ) { 
			if( this.x == 0 || this.y == 0 ) { 
				return true;
			}
			return false; 
		} else if( this.x == 0 || this.y == 0 ) {
			return false;
		}
	
		double px2 = (double) Math.abs(x);
		double px = (double) Math.abs(this.x);
		double py2 = (double) Math.abs(y);
		double py = (double) Math.abs(this.y);
		if( Math.abs(py2/px2 - py/px)  <  0.0001) { //Handles if 1/2 == 1/2, OR 1/2 == -1/2, OR 1/-2 == -1/2, etc (same ratio)
			return true;
		}
		if( Math.abs(py2/px2 - px/py)  <  0.0001) { //Handles if 1/2 == 2/1, OR 2/1 == -1/2, OR -2/1 == 1/-2 etc (inverse ratio)
			return true;
		}
		return false;
	}
	
	/**
	 * variationOfWithLimit Method
	 * This method applies a constraint to the variationOf method that the magnitude x and y components both be less than the provided limit. 
	 * 
	 * Use limit = 2 for Knight and limit = 1 for 
	 * 
	 * @param x - X component of vector which is compared to current vector.
	 * @param y - Y component of vector which is compared to current vector.
	 * @param limit - Maximum value this Vector's x and y components can be.
	 * @return true - If vector is a variation of current vector and passes size limit test. false - If vector is not a variation of current vector or does not pass size limit test.
	 */
	public boolean variationOfWithLimit(int x, int y, double limit) {
		if(!this.variationOf(x, y)) {
			return false;
		} 
		if(Math.abs(this.x) > limit || Math.abs(this.y) > limit) {
			return false;
		}
		return true;
	}
	
	
	/**
	 * UnitStepVector Method
	 * Returns a vector that can take single step increments to traverse the direction of the this-vector.
	 * Scales non-zero x and y Vector components to have a magnitude of 1. 
	 * Only suitable for linear Piece movement vectors.
	 * 
	 * @return Vector - Scaled down version of the current vector into unit steps.
	 */
	public Vector UnitStepVector() { 
		if(this.x == 0) {
			return new Vector(0, (int) Math.abs(this.y)/this.y);
		}
		if(this.y == 0) {
			return new Vector((int) Math.abs(this.x)/this.x, 0);
		}
		return new Vector((int) Math.abs(this.x)/this.x, (int) Math.abs(this.y)/this.y);
	}
}



