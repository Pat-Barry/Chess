package chess;
/**
 * IllegalMoveException Class
 * @author PatrickBarry
 *
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
