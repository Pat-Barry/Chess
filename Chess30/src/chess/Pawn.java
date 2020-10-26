package chess;


public class Pawn extends Piece {

	int promotion_bar;
	int moveDirectionDelta;
	boolean hasMoved = false;
	int passantItteration = -1;
	
	public Pawn(int s, int x, int y, Board ParentBoard) {
		super(s, x, y, ParentBoard);
		if(s == 0) {
			promotion_bar = 7;
			moveDirectionDelta = 1;
		} else {
			promotion_bar = 0;
			moveDirectionDelta = -1;
		}
	}
	
	public Pawn(int i) {
		super(i);
		this.side = i;
		this.hasMoved = false;
		if(i == 0) {
			promotion_bar = 7;
			moveDirectionDelta = 1;
		} else {
			promotion_bar = 0;
			moveDirectionDelta = -1;
		}
	}

	@Override
	public String getString() {
		if(this.side == 0) {
			return "wp";
		} else {
			return "bp";
		}
	}

	
	@Override
	public void moveTo(Position newpos, Piece promotion) throws IllegalMoveException {
		
		
		if(promotion == null) {
			promotion = new Queen(this.side);
		}
	//	System.out.println("We are being asked to move");
		
		Position capture = newpos;
		Vector v = new Vector(newpos, this.pos);
		
		//System.out.println("Vector " + v.x +" "+ v.y);
		
		if(v.equals(0, moveDirectionDelta)) {
	//	System.out.println("Vector is a match for 01");
			if(this.collisionAt(newpos) ) {
				throw new IllegalMoveException("Cannot move pawn ahead, there is a collision");
			}
		//	System.out.println("Passed if else 01");
			
		} else if(v.equals(-1, moveDirectionDelta) || v.equals(1, moveDirectionDelta)) {
			if(!this.enemyAt(newpos) ) {
				System.out.println("No enemy at newpos ");
				if(this.isPassantPawnAt( newpos.addVector(0, -moveDirectionDelta) )){
					System.out.println("FOUND PASS PAWN");
					capture = newpos.addVector(0, -moveDirectionDelta);
				} else {
					Pawn pwn = (Pawn) ParentBoard.getPiece(newpos.addVector(0, -moveDirectionDelta));
					System.out.println(ParentBoard.gameItteration+" "+pwn.passantItteration);
					throw new IllegalMoveException("Trying to move pawn diagonal where there is no piece, or passant pass");
				}
			} 
			
		} else if(v.equals(0, 2*moveDirectionDelta)) {
			if(hasMoved) {
				throw new IllegalMoveException("Can only go 2 steps on first pawn move");
			}
			if(this.collisionAt(newpos)) {
				throw new IllegalMoveException("Cannot pass 2, there is a piece 2 steps ahead");
			}
			System.out.println("Setting passant itteration "+ParentBoard.gameItteration);
			this.passantItteration = ParentBoard.gameItteration;
			
		} else {
			throw new IllegalMoveException("Move vector does not match a legal move");
		}
		
		// here
		/*
		if(promotion != null && newpos.y != this.promotion_bar) {
			System.out.println("e1");
			throw new Exception("Requestion promotion, when not moving to a end of the board");
		}
		*/
		
		
		
		//Chess.wait(1000);
		
		//Now a legal move unless possibility of checkmate
		//Board deepcopy = new Board().setToState(currentBoard)
		
		this.hasMoved = true;
		if(!capture.equals(newpos)) {
			System.out.println("capture not newpos");
		}
		ParentBoard.setPiece(capture, null);
		ParentBoard.setPiece(this.pos, null);
		if(newpos.y == this.promotion_bar) {
			//System.out.println("new queen");
			//ParentBoard.setPiece(newpos, promotion);
			promotion.setBoard(newpos.x, newpos.y, ParentBoard);
		} else {
			//System.out.println("Setting");
			ParentBoard.setPiece(newpos, this);
		}	
	}
	
	
	boolean isPassantPawnAt(Position p) {
		if(ParentBoard.getPiece(p) == null) {
			return false;
		}
		if(ParentBoard.getPiece(p) instanceof Pawn) {
			Pawn pwn = (Pawn) ParentBoard.getPiece(p);
			if(pwn.side != this.side) {
				if(pwn.passantItteration == ParentBoard.gameItteration - 1) {
					return true;
				}
			}
		}
		return false;
	}
	

}






/*
if(this.pos.isDeltaFrom(newpos, 0, 1)) {
	if(Board.getPiece(newpos) != null) {
		throw new Exception();
	}
}
if(this.pos.isDeltaFrom(newpos, 0, 2)) {
	if(Board.getPiece(newpos) != null) {
		throw new Exception();
	}
}

*/


//ALL IN MOVETO
//Check if legal move for specific piece step shape
//Check for collisions except knight
//If piece is same team at end position, throw illegal move
//Check if it would cause checkmate for your own team, use check for checkmate func
//If piece is other team, capture
//If null moveTo

/*
 
		try {
			this.hasMoved = true;
			ParentBoard.setPiece(capture, null);
			ParentBoard.setPiece(this.pos, null);
			if(newpos.y == this.promotion_bar) {
				ParentBoard.setPiece(newpos, promotion);
			} else {
				ParentBoard.setPiece(newpos, this);
			}
			if(Board.thisTeamIsInTrouble(this.side) ) {
				throw new Exception("This move causes pawn's king to be in danger");
			}
		} catch(Exception e) {
			throw new Exception(e);
		}
		
		*/
