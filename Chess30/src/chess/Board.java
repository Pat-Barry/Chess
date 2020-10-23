package chess;
public class Board {
	static Piece[][] layout = new Piece[8][8];
	static String[][] stringBoard = new String[8][8];
	static int state = -1;

	static void updatePos() {
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) { 
				if(layout[i][j] != null) {
					layout[i][j].updatePos(j, i);
				}
			}
		}
	}
	
	static Piece getPiece(Position p) {
		return layout[p.y][p.x];
	}
	static Piece getPieceFromDelta(Position p, int x, int y) {
		return layout[p.y + y][p.x + x];
	}
	static void setPiece(Position p, Piece pic) {
		layout[p.y][p.x] = pic; 
	}
	
	static boolean sameTeam(Position a, Position b) {
		if(getPiece(a) != null) {
			if(getPiece(b) != null) {
				if(getPiece(a).side == getPiece(b).side) {
					return true;
				}
			}
		}
		return false;
	}
	static boolean noCollisions(Position p1, Position p2) {
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

	
	static void render() {
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
