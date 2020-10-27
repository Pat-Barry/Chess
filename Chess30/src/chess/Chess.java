package chess;
import java.util.Scanner;

import geometry.Position;
import pieces.Bishop;
import pieces.Knight;
import pieces.Queen;
import pieces.Rook;

/**
 * @author PatrickBarry
 * Chess Class which contains driver for chess match and input methods
 *
 */
public class Chess {
	
	static int turn = 0;
	static boolean drawRequest = false;
	public static int drawItt = -1;
	public static int gameItteration;
	
	public static Board CurrentBoard;
	public static Board DeepCopy;
	
	/**
	 * Main Method for generating board and runnning chess match.
	 * @param args
	 */
	public static void main(String[] args) {
		CurrentBoard = new Board();
		CurrentBoard.render();
		

		for (gameItteration = 0; true; gameItteration++) {
			try {
				askForInput();
			}
			
			catch(Exception e) {
				System.out.println("Illegal move, try again");
				continue;
			}
			if (CurrentBoard.state == -2) {
				System.out.println("draw");
				break;
			}
			if (CurrentBoard.state == 0) {
				System.out.println("White wins");
				break;
			}
			
			if (CurrentBoard.state == 1) {
				System.out.println("Black wins");
				break;
			}
			
			try {
				int cm = CurrentBoard.movePiece(new Position(Input.start_j,  Input.start_i), new Position(Input.end_j, Input.end_i), Input.promotion, turn);
				if(cm == 1) {
					System.out.println("Check");
				} else if(cm == 2) {
					System.out.println("Checkmate");
				}
			}
			catch(Exception e) {
				System.out.println("Illegal move, try again");
				continue;
			}

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
		}

	}
	
	/**
	 * charToInt Method which takes in a char and converts char to a position x on the board
	 * @param c - Character input
	 * @return i - X position on board for legal move. -1 for Illegal move.
	 */
	public static int charToInt(char c) {
		char[] charArray = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h'};
		for (int i = 0; i < charArray.length; i++) {
			if (c == charArray[i]) {
				return i;
			}
		}
		return -1;
	}
	
	/**
	 * askForInput Method which takes in user input for moving pieces with or without promotion, requesting a draw, declaring a draw, and resigning
	 * @throws IllegalMoveException - Requesting draw when is none
	 * @throws IllegalMoveException - Start spot is null
	 * @throws IllegalMoveException - Start side is not equal to turn
	 * @throws IllegalMoveException - Cannot request promotion if black isn't moving to x1
	 * @throws IllegalMoveException - Cannot request promotion if white isn't moving to x8
	 */
	public static void askForInput() throws IllegalMoveException {

		clearInput();
		if (turn == 0) {
			System.out.print("White's Input: ");
		}
		
		if (turn == 1) {
			System.out.print("Black's Input: ");
		}
		Scanner in = new Scanner(System.in);         
		String s = in.nextLine();
		
		
		if (s.equals("draw")) {
			if (drawRequest && (drawItt + 1 == CurrentBoard.gameItteration)) {
				CurrentBoard.state = -2;
				return;
			}
			
			throw new IllegalMoveException("Requesting draw when is none");
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
		Input.start_i = Integer.parseInt( Character.toString(s.charAt(1)) ) - 1;
		
		if (CurrentBoard.layout[Input.start_i][Input.start_j] == null) {
			throw new IllegalMoveException("Start spot is null");
		}
		
		if (CurrentBoard.layout[Input.start_i][Input.start_j].side != turn) {
			throw new IllegalMoveException("Start side is not equal to turn");
		}
		
		Input.end_j = charToInt(s.charAt(3));
		Input.end_i =  Integer.parseInt( Character.toString(s.charAt(4)) ) - 1;
		
		if (s.length() < 6) {// Regular Move
			return;
		}
		
		
		if (s.length() == 7) {// Regular Move + Promotion
			if(turn == 1) {
				if(Input.end_i != 0) {
					throw new IllegalMoveException("Cannot request promotion if black isn't moving to x1");
				} 
			} else if(turn == 0) {
				if(Input.end_i != 7) {
					throw new IllegalMoveException("Cannot request promotion if white isn't moving to x8");
				}
			}
			Input.promotion = charToPiece(s.charAt(6));
			return;
		}//Regular Move + Draw;
		drawRequest = true;
		drawItt = CurrentBoard.gameItteration;
		
	}
	
	/**
	 * charToPiece Method which takes in a char parameter to create a piece for promotion.
	 * @param s - char input for new promoted piece.
	 * @return new Piece depending on char input.
	 */
	public static Piece charToPiece(char s) {
		if (s == 'N') {
			return new Knight(turn);
		}
		if (s == 'R') {
			return new Rook(turn);
		}
		if (s == 'B') {
			return new Bishop(turn);
		}
		return new Queen(turn);
	}
	
	/**
	 *  clearInput Method which sets starting position, ending positions, and promotion to 0 and null respectively.
	 */
	public static void clearInput() {
		Input.start_i = -1;
		Input.start_j = -1;
		Input.end_i = -1;
		Input.end_j = -1;
		Input.promotion = null;
	}
	
	/**
	 * Input class for chess. Declares a start i,j position, an end i,j position, and a promotion piece.
	 * 
	 */
	static class Input {
		static int start_i;
		static int start_j;
		static int end_i;
		static int end_j;
		static Piece promotion;
	}

}
