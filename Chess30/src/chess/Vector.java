package chess;

/**
 * Vector Object
 *  
 *  A <code>Vector</code> object contains the parameters and functionality
 *  for the Vector object
 * @author PatrickBarry
 *
 */
public class Vector {
	int x;
	int y;
	
	/**
	 * 
	 * @param x
	 * @param y
	 */
	public Vector(int x, int y){
		this.x = x;
		this.y = y;
	}
	public Vector(Position np, Position p) { //Create new vector with t= new pos and i= this.pos
		//System.out.println(np.x +" and "+p.x);
		//System.out.println(np.y +" and "+p.y);
		this.x = np.x - p.x;
		this.y = np.y - p.y;
		if(this.x == 0 && this.y == 0) {
			throw new IllegalArgumentException("Vector cannot be a 0 0 vector");
		}	
	}
	
	public boolean equals(Vector p) {
		return this.x == p.x && this.y == p.y;
	}
	public boolean equals(int x, int y) {
		return this.x == x && this.y == y;
	}
	public String toString() {
		return "<"+this.x+","+this.y+">";
	}
	
		
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
	
	public boolean variationOf(int x, int y) { //checks if the vector matches the <x, y> pattern given, L = <1, 2>, R = <0, 1>, B = <1, 1>
		if( (x == 0 && y == 0) || (this.x == 0 && this.y == 0) ) {
			throw new IllegalArgumentException();
		} //From this point we know that each vector must have a non-zero component
		
		
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


/*
 if( x == 0 || this.x == 0 ) {
			if( x == 0 && this.x == 0 ) {
				return true;
			}
			return false;
		}
		if( y == 0 || this.y == 0 ) {
			if( y == 0 && this.y == 0 ) {
				return true;
			}
			return false;
		}
		*/
