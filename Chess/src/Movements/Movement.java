package Movements;

import ChessGameClasses.Board;
import java.util.ArrayList;

/**
 * Movements that chess pieces can have
 */
public interface Movement
{
	//Movement Type Symbols.
	/**
	 *  A constant Character where moveAndCaptureSymbol is equal to X.
	 */
	public static final Character moveAndCaptureSymbol = 'X';
	
	/**
	 *  A constant Character where captureSymbol is equal to x.
	 */
	public static final Character captureSymbol = 'x';
	
	/**
	 * A constant Character where moveSymbol is equal to O.
	 */
	public static final Character moveSymbol = 'O';
	
	/**
	 * A constant Character where invalidMoveSymbol is equal to ' '.
	 */
	public static final Character invalidMoveSymbol = ' ';
	
	//Special Movement Type 
	/**
	 * A constant Character where castleSymbol is equal to C.
	 */
	public static final Character castleSymbol = 'C';
	
	/**
	 * A constant Character where twoUnitsUpSymbol is equal to T.
	 */
	public static final Character twoUnitsUpSymbol = 'T';
	
	/**
	 * A constant Character where enPassanySymbol is equal to E.
	 */
	public static final Character enPassantSymbol = 'E';
	
	//Symbol to mark the current piece's position.
	/**
	 * A constant Character where currentPositionSymbol is equal to @.
	 */
	public static final Character currentPositionSymbol = '@';
	
	/**
	 * 
	 * @return MovementName.
	 */
	public String getMovementName();  
	
	/**
	 * 
	 * @return MovementCost
	 */
	public int getMovementCost();
	
	/**
	 * 
	 * @param posRow - int, The current row that the chess piece is in.
	 * @param posCol - int, The current column that the chesspiece is in.
	 * @param color - String, The color of the chess piece.
	 * @param hasMoved - boolean, A boolean indicating whether the chess piece has moved.
	 * @param board - Board, The whole chess board.
	 * @return - ArrayList<ArrayList<Character>, Possible movements.
	 */
	//posRow and posCol are the row and column that the chess piece is currently in.
	public ArrayList<ArrayList<Character>> calculateMovement(int posRow, int posCol, String color, boolean hasMoved, Board board);
	
	//Chess Piece symbols for the position board will be stored in an ArrayList of four elements. 
	//Index 0 holds the ally king symbol
	//Index 1 holds the ally piece symbol
	//Index 2 holds the enemy king symbol
	//Index 0 holds the enemy piece symbol
	
	/**
	 * Method to return the chess piece symbol.
	 * @param color - String, The current color of the chess piece.
	 * @return - ArrayList<ArrayList<Character>, Chess piece symbols.
	 */
	public default ArrayList<Character> getSymbols(String color)
	{
		ArrayList<Character> chessPieceSymbols = new ArrayList<Character>();
		//If color is white, white pieces are allies and black pieces are enemies.
		if (color.equals("White"))
		{
			chessPieceSymbols.add(Board.whiteKingPieceSymbol);
			chessPieceSymbols.add(Board.whitePieceSymbol);
			chessPieceSymbols.add(Board.blackKingPieceSymbol);
			chessPieceSymbols.add(Board.blackPieceSymbol);
		}
		//Else color is black, black pieces are allies and white pieces are enemies.
		else 
		{
			chessPieceSymbols.add(Board.blackKingPieceSymbol);
			chessPieceSymbols.add(Board.blackPieceSymbol);
			chessPieceSymbols.add(Board.whiteKingPieceSymbol);
			chessPieceSymbols.add(Board.whitePieceSymbol);
		}
		return chessPieceSymbols;
	}
}