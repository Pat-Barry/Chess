package chess;
import java.util.Scanner;

public class Chess {
	
	static int turn = 0;
	static boolean drawRequest = false;
	public static int gameItteration;
	
	public static Board CurrentBoard;
	public static Board DeepCopy;
	
	public static void wait(int ms){
		try {
			Thread.sleep(ms);
		} catch(Exception e) {
			
		}
	}
	
	public static void main(String[] args) {
		CurrentBoard = new Board();
		CurrentBoard.render();
		

		for (gameItteration = 0; true; gameItteration++) {
			//System.out.println("game itt "+gameItteration);
			//wait(1000);
			try {
				askForInput();
			}
			
			catch(Exception e) {
				System.out.println(e);
				System.out.println("Illegal move, try again");
			//	e.printStackTrace();
				continue;
			}
			if (CurrentBoard.state == -2) {
				System.out.println("draw");
				break;
			}
			try {
				//CurrentBoard.layout[Input.start_i][Input.start_i].moveTo(new Position(Input.end_j, Input.end_i), Input.promotion);
				System.out.println("Moving "+Input.start_j+" "+  Input.start_i+" to "+Input.end_j+" "+Input.end_i+" on turn "+turn);
				CurrentBoard.movePiece(new Position(Input.start_j,  Input.start_i), new Position(Input.end_j, Input.end_i), Input.promotion, turn);
			}
			catch(Exception e) {
				System.out.println("Illegal move, try again");
			//	e.printStackTrace();
				System.out.println(e);
				continue;
			}
			//CurrentBoard.updatePos();
			//Check for checkmate
			CurrentBoard.render();
			if (CurrentBoard.state == 0) {
				System.out.println("White wins");
				break;
			}
			
			if (CurrentBoard.state == 1) {
				System.out.println("Black wins");
				break;
			}
			if(turn == 1) { turn = 0; } else { turn = 1; }
			CurrentBoard.gameItteration++;
			
			CurrentBoard.verifyPosIntegrity();
		}

	}
	
	public static int charToInt(char c) {
		char[] charArray = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h'};
		for (int i = 0; i < charArray.length; i++) {
			if (c == charArray[i]) {
				return i;
			}
		}
		
		return -1;
		
	}
	
	public static void askForInput() throws Exception{
		//System.out.println("IN ask for input");
		//wait(1000);
		clearInput();
		
		if (turn == 0) {
			System.out.print("White's Input: ");
		}
		
		if (turn == 1) {
			System.out.print("Black's Input: ");
		}
		Scanner in = new Scanner(System.in);         
		String s = in.nextLine();
		System.out.println(s);
		if (s.equals("draw")) {
			if (drawRequest) {
				CurrentBoard.state = -2;
				return;
			}
			
			throw new Exception("requesting draw when is none");
		}
		if (s.length() == 6) { // Resign
			if (turn == 0) {
				CurrentBoard.state = 1;
			}
			else {
				CurrentBoard.state = 0;
			}
			return;
		}
		Input.start_j = charToInt(s.charAt(0));
		Input.start_i = Integer.parseInt( Character.toString(s.charAt(1)) ) - 1; //-Integer.parseInt( Character.toString(s.charAt(1)) ) + 8;
		
		//System.out.println(Input.start_i + " and startj "+Input.start_j);
		
		if (CurrentBoard.layout[Input.start_i][Input.start_j] == null) {
			throw new Exception("start spot is null");
		}
		
		if (CurrentBoard.layout[Input.start_i][Input.start_j].side != turn) {
			throw new Exception("start side is not equal to turn");
		}
		
		Input.end_j = charToInt(s.charAt(3));
		Input.end_i =  Integer.parseInt( Character.toString(s.charAt(4)) ) - 1; //-((int) s.charAt(4)) + 8;
		
		if (s.length() < 6) {// Regular Move
			return;
		}
		
		
		if (s.length() == 7) {// Regular Move + Promotion
			Input.promotion = charToPiece(s.charAt(6));
			return;
		}//Regular Move + Draw;
		drawRequest = true;
		
		
		
	}
	
	public static Piece charToPiece(char s) throws Exception {
		if (s == 'N') {
			
		}
		if (s == 'R') {
			
		}
		if (s == 'B') {
			
		}
		if (s == 'Q') {
			Input.promotion = new Queen(turn);
		}
		throw new Exception("Promotion input must be N/R/B/Q");
	}
	
	public static void clearInput() {
		Input.start_i = -1;
		Input.start_j = -1;
		Input.end_i = -1;
		Input.end_j = -1;
		Input.promotion = null;
	}
	
	static class Input {
		static int start_i;
		static int start_j;
		static int end_i;
		static int end_j;
		static Piece promotion;
	}

}
