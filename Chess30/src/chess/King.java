package chess;
/**
 * King Object
 *  
 *  A <code>King</code> object contains the parameters and functionality
 *  for the King piece
 *
 */

public class King extends Piece {

	boolean hasMoved = false;
	int castleBar;
	
	/**
	 * Constructor for the King class
	 * @param s - Player turn
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
	 * getString Method
	 * @return bK - If black King. wK - If white King.
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
	 * moveTo Method
	 * @param newpos - New position
	 * @param promotion - Promoted Piece
	 * @throws IllegalMoveException - For castling, king cannot be, move through, or end up, under attack
	 * @throws IllegalMoveException - Cannot be any pieces between King/Rook to do castle
	 * @throws IllegalMoveException - Rook must (1) not have moved and (2) be on same team
	 * @throws IllegalMoveException - Cannot castle with non-rook piece
	 * @throws IllegalMoveException - Cannot move King to same-team piece
	 * @throws IllegalMoveException - Move Vector not allowed
	 */
	@Override
	public void moveTo(Position newpos, Piece promotion) throws IllegalMoveException {
		
	//	System.out.println("This posx "+this.pos.x+" this posy "+this.pos.y);
		Vector v = new Vector(newpos, this.pos);
	//	System.out.println("King vector"+v);
		if(!hasMoved && newpos.y == this.castleBar && (newpos.x == 0 || newpos.x == 7)) {
			if(ParentBoard.getPiece(newpos) instanceof Rook) {
				Rook rk = (Rook) ParentBoard.getPiece(newpos);
				
				Position step1 = pos.addVector(0, (newpos.x == 0 ? -1 : 1));
				Position step2 = pos.addVector(0, (newpos.x == 0 ? -2 : 2));
				if(rk.side == this.side && rk.hasMoved == false) {
					if(ParentBoard.noCollisions(pos, newpos)) {
						Chess.castle = true;
						if(ParentBoard.KingIsChecked(this.side) || ParentBoard.EnemyCanAttack(this.side, step1) || ParentBoard.EnemyCanAttack(this.side, step2)) {
							
							Chess.castle = false;
							throw new IllegalMoveException("For castling, king cannot be, move through, or end up, under attack");
				
						} else {
							Chess.castle = false;
							this.hasMoved = true;
							rk.hasMoved = true;
							
							ParentBoard.setPiece(newpos, this);
							ParentBoard.setPiece(pos, rk);
						}
					} else {
						throw new IllegalMoveException("Cannot be any pieces between King/Rook to do castle");
					}
				} else {
					throw new IllegalMoveException("Rook must (1) not have moved and (2) be on same team");
				}
			} else {
				throw new IllegalMoveException("Cannot castle with non-rook piece");
			}
			
		} else if(v.variationOfWithLimit(0, 1, 1) || v.variationOfWithLimit(1, 1, 1)) {
			//System.out.println("Vector bk "+v.x+ " and "+v.y);
			//System.out.println("BK being asked to move to "+newpos.x +" and "+newpos.y);
			//System.out.println("Variation of passed, "+this.side);
			if(this.friendAt(newpos)) {
				throw new IllegalMoveException("Cannot move King to same-team piece");
			}
			this.hasMoved = true;
			ParentBoard.setPiece(newpos, this);
			ParentBoard.setPiece(pos, null);
		} else {
			throw new IllegalMoveException("Move Vector not allowed");
		}
	}

}
