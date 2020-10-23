package chess;

public class Pawn extends Piece {

	int promotion_bar;
	int delta;
	boolean hasMoved = false;
	
	public Pawn(int s, int x, int y) {
		super(s, x, y);
		if(s == 0) {
			this.promotion_bar = 7;
			this.delta = 1;
		} else {
			this.promotion_bar = 0;
			this.delta = -1;
		}
		// TODO Auto-generated constructor stub
	}

	@Override
	public String getString() {
		// TODO Auto-generated method stub
		if(this.side == 0) {
			return "wp";
		} else {
			return "bp";
		}
	}

	@Override
	public void moveTo(int end_i, int end_j, Piece promotion) throws Exception { // i = y, j = x
		// TODO Auto-generated method stub
		this.hasMoved = true;
		
		if(end_i == this.pos.y + 1) {
			if(end_j == this.pos.x) {
				if(Board.layout[end_j][end_i] != null) {
					throw new Exception();
				}
			}
		}
		
		
		
	}

}
//ALL IN MOVETO
//Check if legal move for specific piece step shape
//Check for collisions except knight
//If piece is same team at end position, throw illegal move
//Check if it would cause checkmate for your own team, use check for checkmate func
//If piece is other team, capture
//If null moveTo
