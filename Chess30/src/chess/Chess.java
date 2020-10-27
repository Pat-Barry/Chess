package chess;
import java.util.Scanner;

import geometry.Position;
import pieces.Bishop;
import pieces.Knight;
import pieces.Queen;
import pieces.Rook;


/**
 * 
 * Initiates and orchestrates the game of Chess in the main method.
 * Houses functionality to request, pre-process and store user input. 
 *
 * @author Patrick Barry
 * @author Philip Murray
 */


public class Chess {
	
	/**
	 * Represents whose turn it is to make a movement. 
	 * (0: white, 1: black) of the Queen
	 */
	static int turn = 0;
	
	/** Stores the amount of turns which have occurred */
	public static int gameItteration;
	
	/** Stores whether a draw was previously requested.  */
	static boolean drawRequest = false;
	
	/** Stores the gameItteration of the last draw request.  */
	public static int drawItt = -1;
	
	/** Stores the game's chess Board  */
	public static Board CurrentBoard;

	
	/**
	 * Main Method for generating chess Board and running chess match.
	 * Iterates over the range of turns which will occur, making requests for input, requesting pieces to be moved, calling the Board to be rendered, and declaring game states.
	 * @param args  Not used
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
			int cm = 0;
			try {
				cm = CurrentBoard.movePiece(new Position(Input.start_j,  Input.start_i), new Position(Input.end_j, Input.end_i), Input.promotion, turn);
			}
			catch(Exception e) {
				System.out.println("Illegal move, try again");
				continue;
			}
			System.out.println();
			CurrentBoard.render();
			if(cm == 1) {
				System.out.println();
				System.out.println("Check");
			} else if(cm == 2) {
				System.out.println();
				System.out.println("Checkmate");
			}
			
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
	 * Takes in a user-input char and converts char to a Rank (jth or x-position) on the board
	 * @param c  Character input of Rank
	 * @return i  jth or x-position on board for legal move. -1 for Illegal move.
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
	 * Takes in user input for moving pieces with or without promotion, requesting a draw, declaring a draw, and resigning
	 * @throws IllegalMoveException  Stating draw none was requested by opposing team
	 * @throws IllegalMoveException  Piece at initial provided position is null
	 * @throws IllegalMoveException  Piece at initial provided position is not on side of player
	 * @throws IllegalMoveException  Cannot request promotion if black isn't moving to x1
	 * @throws IllegalMoveException  Cannot request promotion if white isn't moving to x8
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
			
			throw new IllegalMoveException("Stating draw none was requested by opposing team");
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
			throw new IllegalMoveException("Piece at initial provided position is null");
		}
		
		if (CurrentBoard.layout[Input.start_i][Input.start_j].side != turn) {
			throw new IllegalMoveException("Piece at initial provided position is not on side of player");
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
		}
		//Regular Move + Draw;
		drawRequest = true;
		drawItt = CurrentBoard.gameItteration;
		
	}
	
	/**
	 * Maps an user-inputted char parameter to create a piece for promotion.
	 * @param s  char input for new promoted piece.
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
	 *  Sets starting position, ending positions, and promotion to 0 and null respectively.
	 *  Called before asking for input to clear data from previous input request.
	 */
	public static void clearInput() {
		Input.start_i = -1;
		Input.start_j = -1;
		Input.end_i = -1;
		Input.end_j = -1;
		Input.promotion = null;
	}
	
	/**
	 * Class that stores chess input. Declares a start i,j position (x and y position mapped from Rank and File respectively), an end i,j position, and a promotion piece.
	 * 
	 */
	static class Input {
		/** Initial File of a user input */
		static int start_i;
		
		/** Initial Rank of a user input */
		static int start_j;
		
		/** Ending File of a user input */
		static int end_i;
		
		/** Initial Rank of a user input */
		static int end_j;
		
		/** Optional Piece that is supplied when Pawn reaches the other end of the chess Board. */
		static Piece promotion;
	}

}
