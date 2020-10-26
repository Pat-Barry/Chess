package chess;

public class IllegalMoveException extends Exception {
	public IllegalMoveException(String e) {
		super(e);
	}
	public IllegalMoveException() {
		super();
	}
}
