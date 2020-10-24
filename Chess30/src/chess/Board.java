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
		
		new Pawn(0).setBoard(0, 1, this);
		new Pawn(0).setBoard(1, 1, this);
		new Pawn(0).setBoard(2, 1, this);
		new Pawn(0).setBoard(3, 1, this);
		new Pawn(0).setBoard(4, 1, this);
		new Pawn(0).setBoard(5, 1, this);
		new Pawn(0).setBoard(6, 1, this);
		new Pawn(0).setBoard(7, 1, this);
		new Rook(0).setBoard(0, 0, this);
		new Knight(0).setBoard(1, 0, this);
		new Bishop(0).setBoard(2, 0, this);
		new Queen(0).setBoard(3, 0, this);
		new King(0).setBoard(4, 0, this);
		new Bishop(0).setBoard(5, 0, this);
		new Knight(0).setBoard(6, 0, this);
		new Rook(0).setBoard(7, 0, this);
		
		new Pawn(1).setBoard(0, 6, this);
		new Pawn(1).setBoard(1, 6, this);
		new Pawn(1).setBoard(2, 6, this);
		new Pawn(1).setBoard(3, 6, this);
		new Pawn(1).setBoard(4, 6, this);
		new Pawn(1).setBoard(5, 6, this);
		new Pawn(1).setBoard(6, 6, this);
		new Pawn(1).setBoard(7, 6, this);
		
		new Rook(1).setBoard(0, 7, this);
		new Knight(1).setBoard(1, 7, this);
		new Bishop(1).setBoard(2, 7, this);
		new Queen(1).setBoard(3, 7, this);
		new King(1).setBoard(4, 7, this);
		new Bishop(1).setBoard(5, 7, this);
		new Knight(1).setBoard(6, 7, this);
		new Rook(1).setBoard(7, 7, this);
		
	}
	
	public boolean EnemyCanAttack(int s, Position p) {
		Board bc = this.getCopy();
		Position TroubledKingPos = p;
		for(int y=0; y < 8; y++) {
			for(int x=0; x < 8; x++) {
				if(bc.layout[y][x] != null) {
					if(bc.layout[y][x].side != s) {
						try { 
							bc.layout[y][x].moveTo(TroubledKingPos, null);
							return true;
						} catch(Exception e) {
							
						}
					}
				}
			}
		}
		return false;
	}
	
	public boolean KingIsChecked(int s) { // this.KingIsChecked() checks if team S is checked on "this" board.
		                                  // First, it makes a clone of "this" board to test.
										  // Second, it checks if there exists a move from !S -> S_King. If there exists 1, it returns true. 
		//Chess.wait(1000);
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
		//System.out.println("after kf");
		if(!foundKing) {
			throw new RuntimeException("Could not find King");
		}
		for(int y=0; y < 8; y++) {
			for(int x=0; x < 8; x++) {
				if(bc.layout[y][x] != null) {
					if(bc.layout[y][x].side != s) {
						try { 
							//System.out.println("Trying move");
							bc.layout[y][x].moveTo(TroubledKingPos, null);
							return true;
						} catch(Exception e) {
							
						}
					}
				}
			}
		}
		return false;
	}
	
	public void movePiece(Position p, Position np, Piece prom, int s) throws Exception { 
		
		
		//System.out.println("MP Called");
		/* 
		 * This is the public-API to move a piece on a board. It makes a clone of the "this" board.
		 * Then it tests to see of the piece_at_position_p.moveTo(np) call is valid on the clone.
		 * If it is valid, it checks if team S on the cloned board is in a checked state.
		 * If it is still valid, it calls the moveTo() on the this board.
		 * 
		 */
		
		//Chess.wait(1000);
		//System.out.println(p.x + " and "+p.y);
		//if(this.layout[0][3] == null) {
			//System.out.println("0 3 is null");
		//}
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
			
			//System.out.println("Before King is Checked for Checked");
			
			if(boardclone.KingIsChecked(s)) { // This call throws exception if the previous call was found to check the King
				throw new Exception("Move cannot be done, King is checked");
			}
			
			//System.out.println("After king is checked for check");

			this.getPiece(p).moveTo(np, prom); // Run the move, if it (1) is okay with moveTo and (2) doesn't cause KingIsChecked
			this.updatePos();
			this.gameItteration++;
			
			int ns = 1;
			if(s == 1) ns = 0;
			if(this.KingIsChecked(ns)) {
				System.out.println("ns is checked");
				if(!this.KingCanRecover(ns)) {
					this.state = s;
				}
			}
			
		} catch(Exception e) {
			throw new Exception(e);
		}
	}
	
	public boolean KingCanRecover(int s) {
		
		System.out.println("KCR");
		
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
				if(rb.layout[y][x] != null) {
					if(rb.layout[y][x].side == s) {
						for(int b=0; b < 8; b++) {
							for(int a=0; a < 8; a++) {
								try {
									rb.movePiece(rb.layout[y][x].pos, new Position(a, b), new Queen(s), s); // Found a move, where after the move, King is not checked
									return true;
								} catch(Exception e) {
									// That didn't fix the problem, so let's check the next move
								}
							}
						}
					}
				}
			}
		}
		return false;
	}

	void verifyPosIntegrity() {
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				if(layout[i][j] != null) {
					if(layout[i][j].pos.x != j && layout[i][j].pos.y != i) {
						System.out.println("WARNING! Layout["+i+"]["+j+"] does not match ("+i+","+j+")");
					}
				}
			}
		}
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
	
	public Piece getPiece(Position p) {
		return this.layout[p.y][p.x];
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
			System.out.print(" "+(i+1));
			System.out.println();
		}
		char[] charArray = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h'};
		System.out.print(" ");
		for (int i=0; i < 7; i++) {
			System.out.print(charArray[i]+"  ");
		}
		System.out.println(charArray[7]);
		System.out.println();
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
