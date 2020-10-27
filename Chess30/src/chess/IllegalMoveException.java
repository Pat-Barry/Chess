package chess;
/**
 * IllegalMoveException Class
 * This exception is thrown when a player's move is deemed illegal, either in pre-processing in askForInput method, 
 * the implementation of a Piece's moveTo method, or if the KingIsChecked method returns true. 
 *
 * @author Patrick Barry
 * @author Philip Murray
 */
public class IllegalMoveException extends Exception {
	/**
	 * IllegalMoveException one arg constructor
	 * @param e - Exception string
	 */
	public IllegalMoveException(String e) {
		super(e);
	}
	
	/**
	 * IllegalMoveException no arg constructor
	 */
	public IllegalMoveException() {
		super();
	}
}
