package Movements;

import ChessGameClasses.Board;
import ChessPieces.ChessPiece;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Protecting the king by moving it behind the rook. 
 */
public class CastleMovement implements Movement,Serializable
{
	/**
	 * A constant String representing the name of the movement.
	 */
	public static final String movementName = "Castle Movement";
	
	/**
	 * A constant int representing the name of the cost of the movement.
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
		
		int kingColumn = 4;
		
		//Can only castle if King is not in check
		if (!board.isInCheck(color))
		{
			//Castle to the left
			//Left castle path is from column 1 to column 3.
			//Left Rook is at column 0
			castleInDirection(posRow, posCol, color, hasMoved, board, result, 1, kingColumn - 1, 0);
			
			//Castle to the right
			//Right castle path is from column 5 to column 7.
			//Right Rook is at column 8
			castleInDirection(posRow, posCol, color, hasMoved, board, result, kingColumn + 1, Board.colNum - 2, Board.colNum - 1);
		}
		//To mark itself
		result.get(posRow).set(posCol, currentPositionSymbol); 
		return result;
	}
	
	/**
	 * 
     * @param posRow - int, The row index of the position of the current chess piece on the board.
	 * @param posCol - int, The column index of the position of the current chess piece on the board.
	 * @param color - color, The color of the current piece.
	 * @param hasMoved - boolean, A boolean indicating whether the chess piece has moved.
	 * @param board - Board, The whole chess board.
	 * @param result - ArrayList<ArrayList<Character>>, A empty ArrayList that will store the result
	 * @param fromCol - int, The starting column.
	 * @param toCol - int, The ending column.
	 * @param rookCol - int, Get the Column of the rook.
	 */
	public void castleInDirection(int posRow, int posCol, String color, boolean hasMoved, Board board, 
			ArrayList<ArrayList<Character>> result, int fromCol, int toCol, int rookCol)
	{
		//Determine the ally and enemy symbols depending on the color
		ArrayList<Character> pieceSymbols = getSymbols(color);
		Character allyKingSymbol = pieceSymbols.get(0);
		Character allyPieceSymbol = pieceSymbols.get(1);
		Character enemyKingSymbol = pieceSymbols.get(2);
		Character enemyPieceSymbol = pieceSymbols.get(3);
		
		//Determine row
		int currentRow;
		if (color.equals("White")) 
			currentRow = Board.rowNum - 1; //White always starts from the bottom
		else //color.equals("Black")
			currentRow = 0; //Black always starts from the top
		
		int kingColumn = 4;
		
		//By the end of test cases, if canCastle remains true, then the King can castle.
		boolean canCastle = true;
		
		//Checks if the castle path is open. 
		for (int currentColumn = fromCol; currentColumn <= toCol; currentColumn++)
		{
			//If the position is not out of range
			if (board.rowColWithinBound(currentRow, currentColumn))
			{
				Character currentCharacter = board.getPositionBoard().get(currentRow).get(currentColumn);
				
				//If position is occupied then king can't castle
				if (currentCharacter.equals(allyKingSymbol) || currentCharacter.equals(allyPieceSymbol) ||
						currentCharacter.equals(enemyKingSymbol) || currentCharacter.equals(enemyPieceSymbol))
					
				{
					canCastle = false;
					break;
				}
			}
		}
		
		//Check if rook and King are in position and haven't moved yet.
		if (canCastle)
		{
			//King can't castle if king is out of position or has moved
			if (posRow != currentRow || posCol != kingColumn || hasMoved)
				canCastle = false;
			
			//Find the rook
			ArrayList<ChessPiece> chessPieces;
			if (color.equals("White")) chessPieces = board.getWhiteChessPieces();
			else chessPieces = board.getBlackChessPieces();
			
			//If a chess piece of the corresponding color was found at the bottom left corner,
			//and it is a rook, has not been captured, and has not moved, then a valid castle rook was found. 
			boolean found = false;
			for (ChessPiece chessPiece : chessPieces)
				if (chessPiece.getPosRow() == currentRow && chessPiece.getPosCol() == rookCol)
					if (chessPiece.getName().equals("Rook"))
						if (!chessPiece.getIsCaptured())
							if (!chessPiece.getHasMoved())
							{
								found = true;
							}
			
			//If the a valid castle rook was found, castle is valid. 
			if (!found) canCastle = false;
		}
		
		//Mark the Rook position as the castle movement
		if (canCastle)
		{
			result.get(currentRow).set(rookCol, Movement.castleSymbol);
		}
	}
}

