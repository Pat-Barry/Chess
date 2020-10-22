public class Board {
	static Piece[][] layout = new Piece[8][8];
	static String[][] stringBoard = new String[8][8];
	static int state = -1;
	
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
			
}
