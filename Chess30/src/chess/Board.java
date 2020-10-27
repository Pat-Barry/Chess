

package chess;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import geometry.Position;
import geometry.Vector;
import pieces.Bishop;
import pieces.King;
import pieces.Knight;
import pieces.Pawn;
import pieces.Queen;
import pieces.Rook;

/**  
 * The Board class represents a 2D chess Board, storing it's state via an 8 by 8 grid of Piece instances, and an 8 by 8 string visual display.
 * Provides movePiece and render for the Chess class to orchestrate with. 
 * Provides helper methods for the Pieces to conduct move-legality verification with. 
 *  
 * @author Patrick Barry
 * @author Philip Murray
 *
 */
public class Board implements Serializable {
	
	/** Stores the representation of the grid of a chess Board as an 8 by 8 Piece double-array.
	 * The first index corresponds to the File (or the ith or y-position)
	 * The second index corresponds to the Rank (or the jth or x-position)
	 */
	public Piece[][] layout = new Piece[8][8];
	
	/** Stores a visual representation of the grid of a chess Board as an 8 by 8 ASCII double-array 
	 *  Rendered from the Board.render method from Board.layout
	 */
	public String[][] stringBoard = new String[8][8];
	
	/** State of the chess game:   In Game: -1, White Wins: 0, Black Wins: 1, Draw: 2 */
	public int state = -1;
	
	/** Number of turns that have been played. Diverges from the Chess.gameItteration by 1 when testing possible moves in Check detection methods */
	public int gameItteration = 0;
	
	
	
	/**
	 * Constructor of the Board class. Returns a new chess Board with a new set of chess pieces in their initial positions. 
	 */
	public Board() {
		
		new Pawn(0).setBoard(0, 1, this);
		new Pawn(0).setBoard(1, 1, this);
		new Pawn(0).setBoard(2, 1, this);
		new Pawn(0).setBoard(3, 1, this);
		new Pawn(0).setBoard(4, 1, this);
		new Pawn(0).setBoard(5, 1, this);
		new Pawn(0).setBoard(6, 1, this);
		new Pawn(0).setBoard(7, 1, this);
		new Rook(0).setBoard(0, 0, this);
		
		new Knight(0).setBoard(1, 0, this);
		new Bishop(0).setBoard(2, 0, this);
		new Queen(0).setBoard(3, 0, this);
		new King(0).setBoard(4, 0, this);
		new Bishop(0).setBoard(5, 0, this);
		new Knight(0).setBoard(6, 0, this);
		new Rook(0).setBoard(7, 0, this);
		
		
		new Pawn(1).setBoard(0, 6, this);
		new Pawn(1).setBoard(1, 6, this);
		new Pawn(1).setBoard(2, 6, this);
		new Pawn(1).setBoard(3, 6, this);
		new Pawn(1).setBoard(4, 6, this);
		new Pawn(1).setBoard(5, 6, this);
		new Pawn(1).setBoard(6, 6, this);
		new Pawn(1).setBoard(7, 6, this);
		
		new Rook(1).setBoard(0, 7, this);
		new Knight(1).setBoard(1, 7, this);
		new Bishop(1).setBoard(2, 7, this);
		new Queen(1).setBoard(3, 7, this);
		new King(1).setBoard(4, 7, this);
		new Bishop(1).setBoard(5, 7, this);
		new Knight(1).setBoard(6, 7, this);
		new Rook(1).setBoard(7, 7, this);
		
	}
	
	/**
	 * Creates a copy of this board via serialization. Used when testing if moving a piece results in or maintains a checked state.
	 * @return Returns a copy of the current board
	 */
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
			throw new RuntimeException("Serialization Clone Failed");
		}
	}
	
	
	/**
	 * This method determines if the enemy side can capture at a specific position on the Board.
	 * Used to inspect the two spaces which the King travels when castling. 
	 * 
	 * For each piece of the side s's opponent, it is tested if it can be called to move to position p.
	 * If such piece exists, this method returns true.
	 * 
	 * @param s  The side (0: white, 1: black) in question
	 * @param p  The position that the opponent of side s may capture
	 * @return true  if the opponent of side s can capture at position p. false - If the opponent cannot capture at position p.
	 */
	public boolean EnemyCanAttack(int s, Position p) {
		Board bc = this.getCopy();
		Position TroubledKingPos = p;
		for(int y=0; y < 8; y++) {
			for(int x=0; x < 8; x++) {
				if(bc.layout[y][x] != null) {
					if(bc.layout[y][x].side != s) {
						try { 
							bc.layout[y][x].moveTo(TroubledKingPos, null);
							return true;
						} catch(Exception e) {
							
						}
					}
				}
			}
		}
		return false;
	}
	
	/**
	 * This method determines if the King of side s is checked.
	 * For each piece of the side s's opponent, it is tested if it can be called to move to the King's piece.
	 * If such piece exists, this method returns true. 
	 * 
	 * @param s  The side (0: white, 1: black) of the King
	 * @return true  if the opponent of side s can capture at the s's King. false - If the opponent cannot capture the King.
	 */
	public boolean KingIsChecked(int s) {
		                         
		Board bc = this.getCopy();
		Position TroubledKingPos = new Position(-1,-1);
		for(int y=0; y < 8; y++) {
			for(int x=0; x < 8; x++) {
				if(bc.layout[y][x] instanceof King) {
					if(bc.layout[y][x].side == s) {
						TroubledKingPos = bc.layout[y][x].pos;
						break;
					}
				}
			}
		}
		for(int y=0; y < 8; y++) {
			for(int x=0; x < 8; x++) {
				if(bc.layout[y][x] != null) {
					if(bc.layout[y][x].side != s) {
						try { 
							bc.layout[y][x].moveTo(TroubledKingPos, null);
							return true;
						} catch(Exception e) {
							
						}
					}
				}
			}
		}
		return false;
	}
	
	/**
	 * This method determines if side s's King can recover from a Checked state.
	 * For every piece the King has, for every spot on the board, the movePiece method is called to move the Piece to that spot.
	 * If there exists a valid move (no IllegalMoveException is thrown by the movePiece method), then this method returns true.
	 * 
	 * @param s  The side (0: white, 1: black) of the King
	 * @return true  if side s is in Checkmate state. false - if side s is in Checkmate state.
	 */
	public boolean KingCanRecover(int s) {
		
		Board rb = this.getCopy();
		for(int y=0; y < 8; y++) {
			for(int x=0; x < 8; x++) {
				if(rb.layout[y][x] != null) {
					if(rb.layout[y][x].side == s) {
						for(int b=0; b < 8; b++) {
							for(int a=0; a < 8; a++) {
								try {
									rb.movePiece(rb.layout[y][x].pos, new Position(a, b), new Queen(s), s); 
									return true;
								} catch(Exception e) {
									// That didn't fix the problem, so let's check the next move
								}
							}
						}
					}
				}
			}
		}
		return false;
	}
	
	
	
	
	/**
	 * This method is responsible enacting a player's move by calling for the piece at position p to move to position np. 
	 * It conducts some initial legality checks, then it clones this instance of chess Board, requests for the piece to move from p to np.
	 * The piece may throw an IllegalMoveException. After the piece is moved, the piece's King is checked to not be in a Checked state.
	 * An IllegalMoveException is thrown if the King is found to be in a Checked state.
	 * The piece's moves are then applied to this instance of the Board. 
	 * 
	 * KingIsChecked is called to determine if the opponent's King is now Checked.
	 * If opponent is Checked, KingCanRecover is called to determine if the opponent is in a Checkmate state.
	 * If opponent in Checkmate, this instance of the Board's state is set to the winning side's team ID (0 or 1). 
	 * 
	 * 
	 * @param p  Position of a player's piece
	 * @param np  Position the player is attempting to move their piece to
	 * @param prom  Piece that is the optional promotion parameter for Pawn cases
	 * @param s  The side (0: white, 1: black) of the player
	 * @throws IllegalMoveException  No piece located at p
	 * @throws IllegalMoveException  Piece at p is not from current team
	 * @throws IllegalMoveException  Move cannot be done, King is (or still is) checked
	 * @return 0  If the player's opponent is not Checked. 1 - the opponent's player is Checked. 2 - the opponent's player is in a Checkmate state.
	 */
	public int movePiece(Position p, Position np, Piece prom, int s) throws IllegalMoveException { 

		if(this.getPiece(p) == null) {
			throw new IllegalMoveException("No piece located at p");
		}
		if(this.getPiece(p).side != s) {
			throw new IllegalMoveException("Piece at p is not from team "+s);
		}
		try {
			Board boardclone = this.getCopy();
			boardclone.getPiece(p).moveTo(np, prom);
			boardclone.updatePos();
			boardclone.gameItteration++;
			
			if(boardclone.KingIsChecked(s)) {
				throw new IllegalMoveException("Move cannot be done, King is (or still is) checked");
			}
	
			this.getPiece(p).moveTo(np, prom); 
			this.updatePos();
			this.gameItteration++;
			
			int ns = 1;
			if(s == 1) ns = 0;
			if(this.KingIsChecked(ns)) {
				if(!this.KingCanRecover(ns)) {
					this.state = s;
					return 2;
				}
				return 1;
			}
			return 0;
			
		} catch(IllegalMoveException e) {
			throw e;
		}
	}
	
	/**
	 * This method is responsible for updating the position of each Piece on this Board after the moveTo method is called. 
	 * This ensures that the internal Position of the Piece moved always matches the indexical position of this Board's layout which references it. 
	 */
	void updatePos() {
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) { 
				if(layout[i][j] != null) {
					layout[i][j].updatePos(j, i);
				}
			}
		}
	}
	
	/**
	 * Responsible for updating the position member of each Piece after the moveTo method is called, to match that of this Board's Piece layout. 
	 * This ensures that the internal Position of the Piece moved is always updated. 
	 * @param p  Positional index of this Board's layout.
	 * @return Updated Piece
	 */
	public Piece getPiece(Position p) {
		return this.layout[p.y][p.x];
	}
	
	/**
	 * Responsible for setting the setting the positional index p of this Board's layout to be a reference to Piece pic.
	 * 
	 * @param p    Positional index of this Board's layout. 
	 * @param pic  Piece of this method updates this Board's layout at p to reference.  
	 */
	public void setPiece(Position p, Piece pic) {
		layout[p.y][p.x] = pic; 
	}
	
	
	/**
	 * Determines if there are no pieces located between two linearly separated positions on the board.
	 * 
	 * @param p1  Start position on this Board. 
	 * @param p2  End position on this Board.
	 * @return true - If no collisions exist. false - If collisions exist.
	 */
	public boolean noCollisions(Position p1, Position p2) {
		Vector sv = (new Vector(p2, p1)).UnitStepVector();
		for(Position i = new Position(p2.x - sv.x, p2.y - sv.y); p1.equals(i) == false; i = new Position(i.x - sv.x, i.y - sv.y)) {
			if(getPiece(i) != null) {
				return false;
			}
		}
		return true;
	}

	
	
	
	/**
	 * Prints this board as an ASCII representation in the console
	 */
	void render() {
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
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
			System.out.print(""+(i+1));
			System.out.println();
		}
		char[] charArray = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h'};
		System.out.print(" ");
		for (int i=0; i < 7; i++) {
			System.out.print(charArray[i]+"  ");
		}
		System.out.println(charArray[7]);
		System.out.println();
	}	
	
}

