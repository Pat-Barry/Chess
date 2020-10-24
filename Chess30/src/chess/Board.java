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
	
	public boolean KingIsChecked(int s) {
		Position TroubledKingPos = new Position(-1,-1);
		boolean foundKing = false;
		for(int y=0; y < 8; y++) {
			for(int x=0; x < 8; x++) {
				if(layout[y][x] instanceof King) {
					if(layout[y][x].side == s) {
						TroubledKingPos = layout[y][x].pos;
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
				if(layout[y][x].side != s) {
					if(layout[y][x].canMoveTo(TroubledKingPos)) {
						return true;
					}
				}
			}
		}
		return false;
	}
	public boolean KingCanRecover(int s) {
		if(!KingIsChecked(s)) {
			throw new RuntimeException("This method should not be called");
		}
		for(int y=0; y < 8; y++) {
			for(int x=0; x < 8; x++) {
				if(layout[y][x].side == s) {
					for(int a=0; a < 8; a++) {
						for(int b=0; b < 8; b++) {
							if(layout[y][x].canMoveTo(new Position(a, b))) {
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
	Piece getPieceFromDelta(Position p, int x, int y) {
		return layout[p.y + y][p.x + x];
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
	
	static boolean thisTeamIsInTrouble(int side) {
		return false;
	}
	void checkIfcheckmate(int side) {
		
	}
			
}
