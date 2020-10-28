package pieces;

import chess.Chess;
import chess.IllegalMoveException;
import chess.Piece;
import geometry.Position;
import geometry.Vector;

/**
 * 
 * A King is the representation of a king chess piece, implementing the abstract methods declared in Piece.
 *  
 * @author Patrick Barry
 * @author Philip Murray
 *
 */

public class King extends Piece {

	/** Stores whether a King has moved */
	boolean hasMoved = false;
	
	/** File a King starts out on. 0 or 7 (corresponding to File 1 or File 8), which is the yth index the King starts out on. */
	int castleBar;
	
	/**
	 * Constructor for the King class
	 * @param s  side (0: white, 1: black) of the Queen
	 */
	public King(int s) {
		super(s);
		if(s == 0) {
			this.castleBar = 0;
		} else {
			this.castleBar = 7;
		}
	}
	
	/**
	 * Returns ASCII representation of the King. 
	 * Used in Board.render method
	 * @return bK - If black King. wK - If white King
	 */
	@Override
	public String getString() {
		if(side == 1) {
			return "bK";
		} else {
			return "wK";
		}
	}

	/**
	 * This is the King's implementation of the moveTo method. 
	 * Performs various legality checks on requested movement. 
	 * If a check fails, an IllegalMoveException is thrown corresponding to the failed check.
	 * Move is applied on this Piece's ParentBoard.
	 * 
	 *  
	 * @param newpos  Position the King is moving to
	 * @param promotion  Not used in King's implementation
	 * @throws IllegalMoveException  For castling: King cannot start at, move through, or end up at a position that can be attacked
	 * @throws IllegalMoveException  For castling: There cannot be a Piece between the King/Rook
	 * @throws IllegalMoveException  For castling: Rook must (1) not have moved and (2) be on same team
	 * @throws IllegalMoveException  For castling: Cannot castle with non-Rook Piece
	 * @throws IllegalMoveException  Cannot capture same-team Piece
	 * @throws IllegalMoveException  Move Vector does not match any of the allowed movement vectors for this Piece
	 */
	
	@Override
	public void moveTo(Position newpos, Piece promotion) throws IllegalMoveException {
		
		Vector v = new Vector(newpos, this.pos);
		
		if(!hasMoved && 	( (v.equals(-2,0) && newpos.equals(2, this.castleBar)) || (v.equals(2,0) && newpos.equals(6, this.castleBar)) )) {
			Position rookPos;
			Position step1;
			Position step2;
			Position newRookPos;
			if(v.equals(2,0)) {
				rookPos = new Position( 7, castleBar);
				step1 = pos.addVector(1, 0);
				step2 = pos.addVector(2, 0);
				newRookPos = this.pos.addVector(1, 0);
			} else {
				rookPos = new Position( 0, castleBar);
				step1 = pos.addVector(-1, 0);
				step2 = pos.addVector(-2, 0);
				newRookPos = this.pos.addVector(-1, 0);
			}
			if(ParentBoard.getPiece(rookPos) instanceof Rook) {
				
				Rook rk = (Rook) ParentBoard.getPiece(rookPos);

				if(rk.side == this.side && rk.hasMoved == false) {
					if(ParentBoard.noCollisions(pos, rookPos)) {
						if(ParentBoard.KingIsChecked(this.side) || ParentBoard.EnemyCanAttack(this.side, step1) || ParentBoard.EnemyCanAttack(this.side, step2)) {
							throw new IllegalMoveException("For castling: King cannot start at, move through, or end up at a position that can be attacked");
						} else {
							this.hasMoved = true;
							rk.hasMoved = true;
							ParentBoard.setPiece(newpos, this);
							ParentBoard.setPiece(newRookPos, rk);
							ParentBoard.setPiece(this.pos, null);
							ParentBoard.setPiece(rookPos, null);
						}
					} else {
						throw new IllegalMoveException("For castling: There cannot be a Piece between the King/Rook");
					}
				} else {
					throw new IllegalMoveException("For castling: Rook must (1) not have moved and (2) be on same team");
				}
			} else {
				throw new IllegalMoveException("For castling: Cannot castle with non-Rook Piece");
			}
			
		} else if(v.variationOfWithLimit(0, 1, 1) || v.variationOfWithLimit(1, 1, 1)) {
			if(this.friendAt(newpos)) {
				throw new IllegalMoveException("Cannot capture same-side Piece");
			}
			this.hasMoved = true;
			ParentBoard.setPiece(newpos, this);
			ParentBoard.setPiece(pos, null);
		} else {
			throw new IllegalMoveException("Move Vector does not match any of the allowed movement vectors for this Piece");
		}
	}

}
