package Movements;

import ChessGameClasses.Board;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * A movement that allows restricted teleportation. 
 */
public class RestrictedTeleportationMovement implements Movement,Serializable
{	/**
	 * A constant String representing the name of the movement.
	 */
	public static final String movementName = "Restricted Teleportation";
	
	/**
	 * A constant int representing the cost of the movement.
	 */
	public static final int movementCost = 1200;
	
	/**
	 * Method that returns the movement name.
	 * @return - String, Movement name.
	 */
	@Override
	public String getMovementName() { return movementName; }
	
	/**
	 *  Method that returns the movement cost.
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
		
		
		//Logic
		
		//Up
		restrictedTeleportInDirection(posRow, posCol, color, board, result, -1, 0);
		
		//Down
		restrictedTeleportInDirection(posRow, posCol, color, board, result, 1, 0);
		
		//Left
		restrictedTeleportInDirection(posRow, posCol, color, board, result, 0, -1);
		
		//Right
		restrictedTeleportInDirection(posRow, posCol, color, board, result, 0, 1);
		
		//To mark itself
		result.get(posRow).set(posCol, currentPositionSymbol); 
		return result;
	}
	
	/**
	 * 
	 * @param posRow - int, The row index of the position of the current chess piece on the board.
	 * @param posCol - int, The column index of the position of the current chess piece on the board.
	 * @param color - String, The color of the chess piece.
	 * @param board - Board, The whole chess board.
	 * @param result - ArrayList<ArrayList<Character>>, The empty ArrayList that will store the result.
	 * @param directionRow - int, A value that will be added to posRow.
	 * @param directionColumn - int, A value that will be added to posCol.
	 */
	public void restrictedTeleportInDirection(int posRow, int posCol, String color, Board board, ArrayList<ArrayList<Character>> result, int directionRow, int directionColumn)
	{
		//Determine the ally and enemy symbols depending on the color
		ArrayList<Character> pieceSymbols = getSymbols(color);
		Character allyKingSymbol = pieceSymbols.get(0);
		Character allyPieceSymbol = pieceSymbols.get(1);
		Character enemyKingSymbol = pieceSymbols.get(2);
		Character enemyPieceSymbol = pieceSymbols.get(3);
		
		final int restriction = 3;
		
		//Move in specified direction
		int currentRow = posRow + directionRow;
		int currentColumn = posCol + directionColumn;
		
		for (int i = 0; i < restriction; i++)
		{
			//If position is within board range
			if (board.rowColWithinBound(currentRow, currentColumn))
			{
				Character currentCharacter = board.getPositionBoard().get(currentRow).get(currentColumn);
				
				//If position is occupied by ally, can't move there
				if (currentCharacter.equals(allyKingSymbol) || currentCharacter.equals(allyPieceSymbol))
					result.get(currentRow).set(currentColumn, invalidMoveSymbol);
				
				//If position is occupied by enemy, can move&capture
				else if (currentCharacter.equals(enemyKingSymbol) || currentCharacter.equals(enemyPieceSymbol))
					result.get(currentRow).set(currentColumn, moveAndCaptureSymbol); 
				
				//If position is empty, can move there
				else
					result.get(currentRow).set(currentColumn, moveSymbol); 
			}
			
			//Move in specified direction
			currentRow += directionRow;
			currentColumn += directionColumn;
		}
	}
}