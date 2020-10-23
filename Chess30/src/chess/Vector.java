package chess;

public class Vector {
	int x;
	int y;
	public Vector(int x, int y){
		this.x = x;
		this.y = y;
	}
	public Vector(Position t, Position i) {
		this.x = t.x - i.x;
		this.y = t.y - i.y;
		if(this.x == 0 && this.y == 0) {
			throw new IllegalArgumentException();
		}	
	}
	
	public boolean equals(Vector p) {
		return this.x == p.x && this.y == p.y;
	}
	public boolean equals(int x, int y) {
		return this.equals(new Vector(x,y));
	}
	
	public boolean variationOf(int x, int y) {
		if( x == 0 && y == 0 ) {
			throw new IllegalArgumentException();
		}
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
		double px2 = (double) Math.abs(x);
		double px = (double) Math.abs(this.x);
		double py2 = (double) Math.abs(y);
		double py = (double) Math.abs(this.y);
		if( Math.abs(py2/px2 - py/px)  <  0.0001) {
			return true;
		}
		if( Math.abs(py2/px2 - px/py)  <  0.0001) {
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
