package Movements;

import ChessGameClasses.Board;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * A movement that moves diagonally.
 */
public class DiagonalMovement implements Movement,Serializable
{	/**
	 * A constant String representing the name of the movement.
	 */
	public static final String movementName = "Diagonal Movement";
	
	/**
	 * A constant int representing the cost of the movement.
	 */
	public static final int movementCost = 500;
	
	/**
	 * Method that returns the movement name.
	 * @return - String, Movement name.
	 */
	@Override
	public String getMovementName() { return movementName; }
	
	/**
	 * Method that returns the movement cost.
	 * @return int, Movement cost.
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
		
		//Logic
		
		//Upper Left Direction
		moveInDirection(posRow, posCol, color, board, result, -1, -1);
		
		//Upper Right Direction
		moveInDirection(posRow, posCol, color, board, result, -1, 1);
		
		//Lower Left Direction
		moveInDirection(posRow, posCol, color, board, result, 1, -1);
		
		//Lower Right Direction
		moveInDirection(posRow, posCol, color, board, result, 1, 1);
		
		
		//The piece can't move onto itself
		result.get(posRow).set(posCol, currentPositionSymbol); 
		
		return result;
	}
	
	/**
	 * 
	 * @param posRow - int, The row of the chess piece.
	 * @param posCol - int, The column of the chess piece.
	 * @param color - String, The color of the chess piece.
	 * @param board - Board, The whole chess board.
	 * @param result - ArrayList<ArrayList<Character>>, The empty ArrayList that will store the result.
	 * @param directionRow - int, A value that will be added to posRow.
	 * @param directionColumn - int, A value that will be added to posCol. 
	 */
	public void moveInDirection(int posRow, int posCol, String color, Board board, ArrayList<ArrayList<Character>> result, int directionRow, int directionColumn)
	{
		//Determine the ally and enemy symbols depending on the color
		ArrayList<Character> pieceSymbols = getSymbols(color);
		Character allyKingSymbol = pieceSymbols.get(0);
		Character allyPieceSymbol = pieceSymbols.get(1);
		Character enemyKingSymbol = pieceSymbols.get(2);
		Character enemyPieceSymbol = pieceSymbols.get(3);
		
		//Move in specified direction
		int currentRow = posRow + directionRow;
		int currentColumn = posCol + directionColumn;
		
		//If the position is not out of range
		while (board.rowColWithinBound(currentRow, currentColumn))
		{
			Character currentCharacter = board.getPositionBoard().get(currentRow).get(currentColumn);
			//If position is occupied by ally, can't move there and is blocked from moving further
			if (currentCharacter.equals(allyKingSymbol) || currentCharacter.equals(allyPieceSymbol))
			{
				result.get(currentRow).set(currentColumn, invalidMoveSymbol);
				break;
			}
			
			//If position is occupied by enemy, can move&capture and is blocked from moving further
			else if (currentCharacter.equals(enemyKingSymbol) || currentCharacter.equals(enemyPieceSymbol))
			{
				result.get(currentRow).set(currentColumn, moveAndCaptureSymbol); 
				break;
			}
			
			//If position is empty, can move there
			else
				result.get(currentRow).set(currentColumn, moveSymbol); 
			
			//Move in specified direction
			currentRow += directionRow;
			currentColumn += directionColumn;
		}
	}
}

