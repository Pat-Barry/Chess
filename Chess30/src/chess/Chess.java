package chess;
import java.util.Scanner;

public class Chess {
	
	static int turn = 0;
	static boolean drawRequest = false;
	static int i;
	
	public static void main(String[] args) {		
		Board.render();
		
		for (i = 0; true; i++) {
			try {
				askForInput();
			}
			
			catch(Exception e) {
				System.out.println("Illegal move, try again");
				continue;
			}
			if (Board.state == -2) {
				System.out.println("draw");
				break;
			}
			try {
				Board.layout[Input.start_i][Input.start_i].moveTo(Input.end_i, Input.end_j, Input.promotion);
			}
			catch(Exception e) {
				System.out.println("Illegal move, try again");
				continue;
			}
			Board.updatePos();
			//Check for checkmate
			Board.render();
			if (Board.state == 0) {
				System.out.println("White wins");
				break;
			}
			
			if (Board.state == 1) {
				System.out.println("Black wins");
				break;
			}
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
				Board.state = -2;
				return;
			}
			
			throw new Exception();
		}
		if (s.length() == 6) { // Resign
			if (turn == 0) {
				Board.state = 1;
			}
			else {
				Board.state = 0;
			}
			return;
		}
		Input.start_j = charToInt(s.charAt(0));
		Input.start_i = -((int) s.charAt(1)) + 8;
		
		if (Board.layout[Input.start_i][Input.start_j] == null) {
			throw new Exception();
		}
		
		if (Board.layout[Input.start_i][Input.start_j].side == turn) {
			throw new Exception();
		}
		
		Input.end_j = charToInt(s.charAt(3));
		Input.end_i = -((int) s.charAt(4)) + 8;
		
		if (s.length() < 6) {// Regular Move
			return;
		}
		
		
		if (s.length() == 7) {// Regular Move + Promotion
			Input.promotion = charToPiece(s.charAt(6));
			return;
		}//Regular Move + Draw;
		drawRequest = true;
		
		
		
	}
	
	public static Piece charToPiece(char s) {
		if (s == 'N') {
			
		}
		
		return null;
	}
	
	public static void clearInput() {
		Input.start_i = -1;
		Input.start_j = -1;
		Input.end_i = -1;
		Input.end_j = -1;
		Input.promotion = null;
	}
	
	static class Input{
		static int start_i;
		static int start_j;
		static int end_i;
		static int end_j;
		static Piece promotion;
	}

}
