package pieces;

import chess.IllegalMoveException;
import chess.Piece;
import geometry.Position;
import geometry.Vector;

/**
 * 
 * A Pawn is the representation of a pawn chess piece, implementing the abstract methods declared in Piece.
 *  
 * @author Patrick Barry
 * @author Philip Murray
 *
 */

public class Pawn extends Piece {
	
	/** Stores whether a Pawn has moved */
	boolean hasMoved = false;

	/** File a Pawn must reach to be promoted. 0 or 7 (corresponding to File 1 or File 8), which is the yth index of Board.layout a Pawn must reach to be promoted. */
	int promotion_bar;
	
	/** The y-direction a Pawn moves in, 1 for white, -1 for black */
	int moveDirectionDelta;
	
	/** Stores the ParentBoard.gameItteration (the turn number) that a Pawn took a 2-step move on */
	int passantItteration = -1;
	
	
	/**
	 * Constructor for the Rook class
	 * @param s  side (0: white, 1: black) of the Rook
	 */
	public Pawn(int s) {
		super(s);
		if(s == 0) {
			promotion_bar = 7;
			moveDirectionDelta = 1;
		} else {
			promotion_bar = 0;
			moveDirectionDelta = -1;
		}
	}
	
	
	/**
	 * Returns ASCII representation of the Queen
	 * Used in Board.render method
	 * @return bp - If black Pawn. wp - If white Pawn.
	 */
	@Override
	public String getString() {
		if(this.side == 0) {
			return "wp";
		} else {
			return "bp";
		}
	}

	
	/**
	 * This is the Pawn's implementation of the moveTo method. 
	 * Performs various legality checks on requested movement. 
	 * If a check fails, an IllegalMoveException is thrown corresponding to the failed check.
	 * Move is applied on this Piece's ParentBoard.
	 * 
	 * @param newpos  New position
	 * @param promotion  Optional Piece that is supplied when Pawn reaches the other end of the chess Board. Promotion replaces Pawn at newpos. 
	 * @throws IllegalMoveException  For Pawn: Cannot move a step forward when Piece is located there
	 * @throws IllegalMoveException  For Pawn: Diagonal movement requires capture
	 * @throws IllegalMoveException  For Pawn: Can only move two steps forward on first move
	 * @throws IllegalMoveException  For Pawn: Cannot move two steps forward another Piece is located there
	 * @throws IllegalMoveException  Move vector does not match a legal move
	 */
	@Override
	public void moveTo(Position newpos, Piece promotion) throws IllegalMoveException {
		
		
		if(promotion == null) {
			promotion = new Queen(this.side);
		}
		
		Position capture = newpos;
		Vector v = new Vector(newpos, this.pos);
		
		if(v.equals(0, moveDirectionDelta)) {
			
			if(this.collisionAt(newpos) ) {
				throw new IllegalMoveException("For Pawn: Cannot move a step forward when Piece is located there");
			}
			
		} else if(v.equals(-1, moveDirectionDelta) || v.equals(1, moveDirectionDelta)) 
		{
			if(!this.enemyAt(newpos) ) {
				if(this.isPassantPawnAt( newpos.addVector(0, -moveDirectionDelta) )){
					
					capture = newpos.addVector(0, -moveDirectionDelta);
				} else {
					Pawn pwn = (Pawn) ParentBoard.getPiece(newpos.addVector(0, -moveDirectionDelta));
					throw new IllegalMoveException("For Pawn: Diagonal movement requires capture");
				}
			} 
			
		} else if(v.equals(0, 2*moveDirectionDelta)) {
			if(hasMoved) {
				throw new IllegalMoveException("For Pawn: Can only move two steps forward on first move");
			}
			if(this.collisionAt(newpos)) {
				throw new IllegalMoveException("For Pawn: Cannot move two steps forward another Piece is located there");
			}
			this.passantItteration = ParentBoard.gameItteration;
			
		} else {
			throw new IllegalMoveException("Move Vector does not match any of the allowed movement vectors for this Piece");
		}
		
		//Move is legal
		this.hasMoved = true;
		ParentBoard.setPiece(capture, null);
		ParentBoard.setPiece(this.pos, null);
		if(newpos.y == this.promotion_bar) {
			promotion.setBoard(newpos.x, newpos.y, ParentBoard);
		} else {
			ParentBoard.setPiece(newpos, this);
		}	
	}
	
	/**
	 * Method is used by Pawn's moveTo implementation to see if there exists an enemy Pawn which has moved two steps in the previous turn. 
	 * 
	 * @param p  Position to check. 
	 * @return true  if there exists a Pawn at p which has moved two steps in the previous turn. false - if no such pawn exists. 
	 */
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
