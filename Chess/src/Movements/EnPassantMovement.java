package Movements;

import ChessGameClasses.Board;
import ChessPieces.ChessPiece;
import ChessPieces.Pawn;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * A movement that allows Pawns to capture other Pawns that have moved two units up.
 */
public class EnPassantMovement implements Movement,Serializable
{	/**
	 * A constant String representing the name of the movement.
	 */
	public static final String movementName = "En Passant";
	
	/**
	 * A constant int representing the cost of the movement.
	 */
	public static final int movementCost = 0;
	
	/**
	 * Method that returns the movement name.
	 * @return - String, Movement name.
	 */
	@Override
	public String getMovementName() { return movementName; }
	
	/**
	 * Method that returns the movement cost.
	 * @return - int, Movement cost.
	 */
	@Override
	public int getMovementCost() { return movementCost; }
	
	/**
	 * Method that return the possible movements of the chess piece.
	 * 
	 * @param posRow - int, The current row that the chess piece is in.
	 * @param posCol - int, The current column that the chess piece is in.
	 * @param color - String, The current color of the chess piece.
	 * @param hasMoved - boolean, A boolean indicating whether the chess piece has moved.
	 * @param board - Board, The whole chess board.
	 * 
	 * @return - ArrayList<ArrayList<Character>>, result.
	 */
	//posRow and posCol are the row and column that the chess piece is currently in.
	@Override
	public ArrayList<ArrayList<Character>> calculateMovement(int posRow, int posCol, String color, boolean hasMoved, Board board)
	{
		//Create an empty ArrayList<ArrayList<Character>> to store the possible moves
		ArrayList<ArrayList<Character>> result = Board.createAnEmpty2DCharacterArrayList();
		
		//Check left side
		checkPosition(posRow, posCol, color, board, result, posRow, posCol - 1);
		
		//Check right side
		checkPosition(posRow, posCol, color, board, result, posRow, posCol + 1);
		
		//Mark the piece itself
		result.get(posRow).set(posCol, currentPositionSymbol); 
		
		return result;
	}
	
	/**
	 * 
	 * @param posRow - int, The current row that the chess piece is in.
	 * @param posCol - int, The current column that the chess piece is in.
	 * @param color - String, The color of the chess piece.
	 * @param board - Board, The whole chess board.
	 * @param result - ArrayList<ArrayList<Character>>, The empty ArrayList that will store the result.
	 * @param rowToCheck - int, A integer that will be added to the current posRow to get it to the row.
	 * @param colToCheck - int, A integer that will be added to the current posCol to get it to the column.
	 */
	public void checkPosition(int posRow, int posCol, String color, Board board, ArrayList<ArrayList<Character>> result, int rowToCheck, int colToCheck)
	{
		//Determine the enemy piece symbols depending on the color
		ArrayList<Character> pieceSymbols = getSymbols(color);
		Character enemyPieceSymbol = pieceSymbols.get(3);
				
		//Determine the opponent chess piece set and direction
		ArrayList<ChessPiece> opponentChessPieces;
		int direction;
		if (color.equals("White")) 
		{
			opponentChessPieces = board.getBlackChessPieces();
			direction = -1;
		}
		else 
		{
			opponentChessPieces = board.getWhiteChessPieces();
			direction = 1;
		}
			
		//Found a valid pawn to perform En Passant Capture on
		boolean found = false;
				
		//If row and column is within bound
		if (board.rowColWithinBound(rowToCheck, colToCheck))
			//If the position is occupied by an enemyPiece
			if (board.getPositionBoard().get(rowToCheck).get(colToCheck).equals(enemyPieceSymbol))
				//Find the chessPiece at that position
				for (ChessPiece chessPiece : opponentChessPieces)
					if (chessPiece.getPosRow() == rowToCheck && 
							chessPiece.getPosCol() == colToCheck)
					{
						if (!chessPiece.getIsCaptured())
						{
							//En Passant is valid if it is a pawn and moved two units up.
							if (chessPiece instanceof Pawn)
							{
								Pawn pawn = (Pawn) chessPiece;
								if (pawn.getMovedTwoUnitsUp())
									found = true;
								break;
							}
						}
					}
				
		//If a valid pawn was found, mark the position behind that Pawn as en passant symbol.
		if (found) result.get(rowToCheck + direction).set(colToCheck, Movement.enPassantSymbol);
	}
}

