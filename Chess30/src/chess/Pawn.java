package chess;

import java.util.HashMap;
import java.util.function.Function;

public class Pawn extends Piece {

	int promotion_bar;
	int moveDirectionDelta;
	boolean hasMoved = false;
	int passantItteration = -1;
	
	public Pawn(int s, int x, int y) {
		super(s, x, y);
		if(s == 0) {
			this.promotion_bar = 7;
			this.moveDirectionDelta = 1;
		} else {
			this.promotion_bar = 0;
			this.moveDirectionDelta = -1;
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
	public void moveTo(Position newpos, Piece promotion) throws Exception { // i = y, j = x
		// TODO Auto-generated method stub
		
		Position capture = newpos;
		
		Vector v = new Vector(newpos, this.pos);
		
		if(v.equals(0, moveDirectionDelta)) {
			if(this.collisionAt(newpos) ) {
				throw new Exception("Cannot move pawn ahead, there is a collision");
			}
			
		} else if(v.equals(-1, moveDirectionDelta) || v.equals(1, moveDirectionDelta)) {
			if(!this.enemyAt(newpos) ) {
				if(this.isPassantPawnAt( newpos.addVector(0, -moveDirectionDelta) )){
					capture = newpos.addVector(0, -moveDirectionDelta);
				} else {
					throw new Exception("Trying to move pawn diagonal where there is no piece, or passant pass");
				}
			} // else we can take that piece
			
		} else if(v.equals(0, 2*moveDirectionDelta)) {
			if(hasMoved) {
				throw new Exception("Can only go 2 steps on first pawn move");
			}
			if(this.collisionAt(newpos)) {
				throw new Exception("Cannot pass 2, there is a piece 2 steps ahead");
			}
			
		} else {
			throw new Exception("Move vector does not match a legal move");
		}
		
		if(promotion != null && newpos.y != this.promotion_bar) {
			throw new Exception("Requestion promotion, when not moving to a end of the board");
		}
		if(newpos.y == this.promotion_bar && promotion == null) {
			throw new Exception("Must request promotion upon reaching promotion bar");
		}
		
		
		
		
		//Now a legal move
		try {
			
			Board.setPiece(capture, null);
			Board.setPiece(this.pos, null);
			if(newpos.y == this.promotion_bar) {
				Board.setPiece(newpos, promotion);
			} else {
				Board.setPiece(newpos, this);
			}
			if(Board.thisTeamIsInTrouble(this.side) ) {
				throw new Exception("This move causes pawn's king to be in danger");
			}
		} catch(Exception e) {
			throw new Exception(e);
		}
	}
	
	boolean isPassantPawnAt(Position p) {
		if(Board.getPiece(p) == null) {
			return false;
		}
		if(Board.getPiece(p) instanceof Pawn) {
			Pawn pwn = (Pawn) Board.getPiece(p);
			if(pwn.side != this.side) {
				if(pwn.passantItteration == Chess.gameItteration - 1) {
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
