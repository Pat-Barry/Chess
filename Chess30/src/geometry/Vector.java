package geometry;

/**
 * Vector Object
 *  
 *  A <code>Vector</code> object contains the parameters and functionality
 *  for the Vector object
 * @author PatrickBarry
 *
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
	 * Second constructor for vector. Creates a vector between two positions.
	 * @param np - New position of piece
	 * @param p - Current position of piece
	 * @throws IllegalArgumentException - Vector cannot be a 0 0 vector
	 */
	public Vector(Position np, Position p) { //Create new vector with t= new pos and i= this.pos
		//System.out.println(np.x +" and "+p.x);
		//System.out.println(np.y +" and "+p.y);
		this.x = np.x - p.x;
		this.y = np.y - p.y;
		if(this.x == 0 && this.y == 0) {
			System.out.println("Found zero vector");
			throw new IllegalArgumentException("Vector cannot be a 0 0 vector");
		}	
	}
	
	/**
	 * First equals Method
	 * @param v - Vector to be compared with current vector.
	 * @return true - Vector is equal. false - Vector is not equal.
	 */
	public boolean equals(Vector v) {
		return this.x == v.x && this.y == v.y;
	}
	/**
	 * Second equals Method
	 * @param x - X component of vector which is compared to current vector.
	 * @param y - Y component of vector which is compared to current vector.
	 * @return true - Vector is equal. false - Vector is not equal.
	 */
	public boolean equals(int x, int y) {
		return this.x == x && this.y == y;
	}
	
	/**
	 * variationOf Method
	 * @param x - X component of vector which is compared to current vector.
	 * @param y - Y component of vector which is compared to current vector.
	 * @return true - If vector is a variation of current vector. false - If vector is not a variation of current vector
	 * @throws IllegalArguementException - If provided a zero vector.
	 */
	public boolean variationOf(int x, int y) { //checks if the vector matches the <x, y> pattern given, L = <1, 2>, R = <0, 1>, B = <1, 1>
		if( (x == 0 && y == 0)) {
			throw new IllegalArgumentException();
		} 
		
		
		// Say v = <5,0> and we do v.variationOf(<0,1>)
		// The below if statements confirm <5,0> is a rook move by making sure <5,0> and <0,1> both have 1 zero. 
		
		if( x == 0 || y == 0 ) { 
		//	System.out.println("A");
			//<0,1> has a 0 in it
		//	System.out.println("This x "+this.x+" and "+this.y);
			if( this.x == 0 || this.y == 0 ) { 
		//		System.out.println("B");
				return true; //<0,1> and <5,0> have a 0 in it
			}
		//	System.out.println("C");
			return false; // <0,1> has a 0 in it but <5,0> does not have a 0
			
		} else if( this.x == 0 || this.y == 0 ) {
		//	System.out.println("D");
			return false; // <0,1> does not have a 0 in it, but <5,0> does have a 0. 
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
	 * @param x - X component of vector which is compared to current vector.
	 * @param y - Y component of vector which is compared to current vector.
	 * @param limit - Maximum value x and y can be.
	 * @return true - If vector is a variation of current vector and passes size limit test. false - If vector is not a variation of current vector or does not pass size limit test.
	 */
	public boolean variationOfWithLimit(int x, int y, double limit) {
		if(!this.variationOf(x, y)) {
			return false;
		} 
		if(Math.abs(this.x) > limit || Math.abs(this.y) > limit) {
		//	System.out.println("This x "+this.x+" and "+this.y);
			return false;
		}
		return true;
	}
	
	
	/**
	 * UnitStepVector Method
	 * @return Vector - Scaled down version of the current vector into unit steps.
	 */
	public Vector UnitStepVector() { // Converts <4, -4> to <1, -1>
		if(this.x == 0) {
			return new Vector(0, (int) Math.abs(this.y)/this.y);
		}
		if(this.y == 0) {
			return new Vector((int) Math.abs(this.x)/this.x, 0);
		}
		return new Vector((int) Math.abs(this.x)/this.x, (int) Math.abs(this.y)/this.y);
	}
}



