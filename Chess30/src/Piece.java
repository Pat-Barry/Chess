
public abstract class Piece {
	
	int side;
	
	public Piece (int i) {
		this.side = i;
	}

	public abstract String getString();

	public abstract void moveTo(int end_i, int end_j, Piece promotion) throws Exception;
	
}
