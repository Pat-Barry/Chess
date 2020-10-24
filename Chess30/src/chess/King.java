package chess;

public class King extends Piece {

	boolean hasMoved;
	int castleBar;
	public King(int s, int x, int y, Board ParentBoard) {
		super(s, x, y, ParentBoard);
		// TODO Auto-generated constructor stub
		this.hasMoved = false;
		if(s == 0) {
			this.castleBar = 0;
		} else {
			this.castleBar = 7;
		}
	}

	@Override
	public String getString() {
		if(side == 1) {
			return "bK";
		} else {
			return "wK";
		}
	}

	@Override
	public void moveTo(Position newpos, Piece promotion) throws Exception {
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
						if(ParentBoard.KingIsChecked(this.side) || ParentBoard.EnemyCanAttack(this.side, step1) || ParentBoard.EnemyCanAttack(this.side, step2)) {
							
							this.hasMoved = true;
							rk.hasMoved = true;
							
							ParentBoard.setPiece(newpos, this);
							ParentBoard.setPiece(newpos, rk);
						
						} else {
							throw new Exception("For castling, king cannot be, move through, or end up, under attack");
						}
					} else {
						throw new Exception("Cannot be any pieces between King/Rook to do castle");
					}
				} else {
					throw new Exception("Rook must (1) not have moved and (2) be on same team");
				}
			} else {
				throw new Exception("Cannot castle with non-rook piece");
			}
			
		} else if(v.variationOfWithLimit(0, 1, 1) || v.variationOfWithLimit(1, 1, 1)) {
			//System.out.println("Vector bk "+v.x+ " and "+v.y);
			//System.out.println("BK being asked to move to "+newpos.x +" and "+newpos.y);
			//System.out.println("Variation of passed, "+this.side);
			if(this.friendAt(newpos)) {
				throw new Exception("Cannot move King to same-team piece");
			}
			this.hasMoved = true;
			ParentBoard.setPiece(newpos, this);
			ParentBoard.setPiece(pos, null);
		} else {
			throw new Exception("Vector is not in step shape set");
		}
	}

}
