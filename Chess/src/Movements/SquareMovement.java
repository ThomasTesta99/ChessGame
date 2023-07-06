package Movements;

import ChessGameClasses.Board;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * A movement that allows a chess piece to move in a square.
 */
public class SquareMovement implements Movement,Serializable
{	/**
	 * A constant String representing the name of the movement.
	 */
	public static final String movementName = "Square Movement";
	
	/**
	 * A constant int representing the cost of the movement.
	 */
	public static final int movementCost = 300;
	
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
	 * @param posRow - int,The current row that the chess piece is in.
	 * @param posCol - int,The current column that the chess piece is in.
	 * @param color - String,The current color of the chess piece.
	 * @param hasMoved - boolean,a boolean indicating whether the chess piece has moved.
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
		
		//Determine the ally and enemy symbols depending on the color
		ArrayList<Character> pieceSymbols = getSymbols(color);
		Character allyKingSymbol = pieceSymbols.get(0);
		Character allyPieceSymbol = pieceSymbols.get(1);
		Character enemyKingSymbol = pieceSymbols.get(2);
		Character enemyPieceSymbol = pieceSymbols.get(3);
		
		//Logic	
		int range = 3;
		int startingRow = posRow - 1;
		int startingColumn = posCol - 1;

		int currentRow = startingRow;
		int currentColumn = startingColumn;
		for (int i = 0; i < range; i++)
		{
			currentRow = startingRow;
			for (int j = 0; j < range; j++)
			{
				 //If the position is not out of range
				if (board.rowColWithinBound(currentRow, currentColumn))
				{
					Character currentCharacter = board.getPositionBoard().get(currentRow).get(currentColumn);
					
					//If position is occupied by ally, can't move there.
					if (currentCharacter.equals(allyKingSymbol) || currentCharacter.equals(allyPieceSymbol))
						result.get(currentRow).set(currentColumn, invalidMoveSymbol);
					
					//If position is occupied by enemy, can move&capture.
					else if (currentCharacter.equals(enemyKingSymbol) || currentCharacter.equals(enemyPieceSymbol))
						result.get(currentRow).set(currentColumn, moveAndCaptureSymbol); 
					
					//If position is empty, can move there.
					else
						result.get(currentRow).set(currentColumn, moveSymbol); 
				}
				currentRow++;
			}
			currentColumn++;
		}
		
		//The piece can't move onto itself
		result.get(posRow).set(posCol, currentPositionSymbol); 
		
		return result;
	}
}

