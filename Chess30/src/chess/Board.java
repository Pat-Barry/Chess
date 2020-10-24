package chess;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class Board implements Serializable {
	Piece[][] layout;
	String[][] stringBoard;
	int state;
	int gameItteration;
	
	public Board() {
		layout = new Piece[8][8];
		stringBoard = new String[8][8];
		state = -1;
		gameItteration = 0;
	}
	
	public boolean KingIsChecked(int s) { // this.KingIsChecked() checks if team S is checked on "this" board.
		                                  // First, it makes a clone of "this" board to test.
										  // Second, it checks if there exists a move from !S -> S_King. If there exists 1, it returns true. 
		Board bc = this.getCopy();
		Position TroubledKingPos = new Position(-1,-1);
		boolean foundKing = false;
		for(int y=0; y < 8; y++) {
			for(int x=0; x < 8; x++) {
				if(bc.layout[y][x] instanceof King) {
					if(bc.layout[y][x].side == s) {
						TroubledKingPos = bc.layout[y][x].pos;
						foundKing = true;
						break;
					}
				}
			}
		}
		if(!foundKing) {
			throw new RuntimeException("Could not find King");
		}
		for(int y=0; y < 8; y++) {
			for(int x=0; x < 8; x++) {
				if(bc.layout[y][x].side != s) {
					try { 
						bc.layout[y][x].moveTo(TroubledKingPos, null);
						return true;
					} catch(Exception e) {
						
					}
				}
			}
		}
		return false;
	}
	
	public void movePiece(Position p, Position np, Piece prom, int s) throws Exception { 
		
		/* 
		 * This is the public-API to move a piece on a board. It makes a clone of the "this" board.
		 * Then it tests to see of the piece_at_position_p.moveTo(np) call is valid on the clone.
		 * If it is valid, it checks if team S on the cloned board is in a checked state.
		 * If it is still valid, it calls the moveTo() on the this board.
		 * 
		 */
		
		if(this.getPiece(p) == null) {
			throw new Exception("No piece located at p");
		}
		if(this.getPiece(p).side != s) {
			throw new Exception("Piece at p is not from team "+s);
		}
		try {
			Board boardclone = this.getCopy();
			boardclone.getPiece(p).moveTo(np, prom); // This call throws exception if illegal move EXCEPT for check checking
			boardclone.updatePos();
			boardclone.gameItteration++;
			
			if(boardclone.KingIsChecked(s)) { // This call throws exception if the previous call was found to check the King
				throw new Exception("Move cannot be done, King is checked");
			}

			this.getPiece(p).moveTo(np, prom); // Run the move, if it (1) is okay with moveTo and (2) doesn't cause KingIsChecked
			this.updatePos();
			this.gameItteration++;
			
			int ns = 1;
			if(s == 1) {
				ns = 0;
			}
			
			if(this.KingIsChecked(ns)) {
				if(!this.KingCanRecover(s)) {
					this.state = s;
				}
			}
			
		} catch(Exception e) {
			throw new Exception(e);
		}
	}
	
	public boolean KingCanRecover(int s) {
		
		/* Creates a clone of the 'this' board rc. 
		 * Uses the public-API movePiece to, for every S_Piece on the board, see if there exists a move S_Piece -> Spot such that there is no longer a check.
		 * If the move does not exist, the king cannot recover. 
		 */
		
		if(!KingIsChecked(s)) {
			throw new RuntimeException("This method should not be called");
		}
		Board rb = this.getCopy();
		for(int y=0; y < 8; y++) {
			for(int x=0; x < 8; x++) {
				if(rb.layout[y][x].side == s) {
					for(int b=0; b < 8; b++) {
						for(int a=0; a < 8; a++) {
							try {
								rb.movePiece(rb.layout[y][x].pos, new Position(a, b), null, s); // Found a move, where after the move, King is not checked
								return true;
							} catch(Exception e) {
								// That didn't fix the problem, so let's check the next move
							}
						}
					}
				}
			}
		}
		return false;
	}

	void updatePos() {
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) { 
				if(layout[i][j] != null) {
					layout[i][j].updatePos(j, i);
				}
			}
		}
	}
	
	Piece getPiece(Position p) {
		return layout[p.y][p.x];
	}
	void setPiece(Position p, Piece pic) {
		layout[p.y][p.x] = pic; 
	}
	
	boolean sameTeam(Position a, Position b) {
		if(getPiece(a) != null) {
			if(getPiece(b) != null) {
				if(getPiece(a).side == getPiece(b).side) {
					return true;
				}
			}
		}
		return false;
	}
	boolean noCollisions(Position p1, Position p2) {
		if(p1.equals(p2)) {
			throw new IllegalArgumentException("Collision method must take two non-identical positions");
		}
		Vector sv = (new Vector(p2, p1)).UnitStepVector(); //for(int x = p2.x - sv.x, y = p2.y - sv.y; p1.equals(x,y) == false; x = x - sv.x, y = y - sv.y) {
		for(Position i = new Position(p2.x - sv.x, p2.y - sv.y); p1.equals(i) == false; i = new Position(i.x - sv.x, i.y - sv.y)) {
			if(getPiece(i) != null) {
				return false;
			}
		}
		return true;
	}

	
	void render() {
		for (int i = 0; i < 8; i++) { // i = 1 - 8
			for (int j = 0; j < 8; j++) { // j = a -h
				if (layout[i][j] == null) {
					if (i % 2 != 0) {
						if (j % 2 != 0) {
							stringBoard[i][j] = "##";
						}
						else {
							stringBoard[i][j] = "  ";
						}
					}
					
					else {
						if (j % 2 == 0) {
							stringBoard[i][j] = "##";
						}
						else {
							stringBoard[i][j] = "  ";
						}
					}
				}
				
				else {
					stringBoard[i][j] = layout[i][j].getString();
				}
			}
		}
		
		for (int i = 7; i >= 0; i--) {
			for (int j = 0; j < 8; j++) {
				System.out.print(stringBoard[i][j] + " ");
				
			}
			System.out.println();
		}
	}	
	
	public Board getCopy() {
		try {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ObjectOutputStream oos = new ObjectOutputStream(baos);
			oos.writeObject(this);
			ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
			ObjectInputStream ois = new ObjectInputStream(bais);
			return (Board) ois.readObject();
		}
		catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("Clone failed");
		}
	}
}

/*
if(kbc.layout[y][x].canMoveTo(new Position(a, b))) {
	Board boardcopy = this.getCopy();
	try {
		boardcopy.layout[x][y].moveTo(new Position(a, b), null);
		boardcopy.gameItteration++;
		if(!boardcopy.KingIsChecked(s)) {
			return true;
		}
	} catch(Exception e) {
		System.out.println("Copy doesn't have parallel move ability");
		System.out.println(e);
	}
}
*/
