package Movements;

import ChessGameClasses.Board;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * A movement that advances the current chess piece up by one unit.
 */
public class AdvanceMovement implements Movement, Serializable
{	/**
	 * A constant String representing the name of the movement.
	 */
	public static final String movementName = "Advance Movement";
	
	/**
	 * A constant int representing the cost of the movement.
	 */
	public static final int movementCost = 100;
	
	/**
	 * Method that returns the movement name.
	 * @return - String, Movement name.
	 */
	@Override
	public String getMovementName() { return movementName; }
	
	/**
	 * Method that returns he movement cost.
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
	 * @return - ArrayList<ArrayList<Character>, result.
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
		int currentRow = posRow;
		if (color.equals("White")) currentRow--; //White always starts from the bottom
		else currentRow++;
		
		int currentColumn = posCol;
		
		//If the position is not out of range
		if (board.rowColWithinBound(currentRow, currentColumn))
		{
			Character currentCharacter = board.getPositionBoard().get(currentRow).get(currentColumn);
			
			//If position is occupied by ally or enemy, can't move there.
			if (currentCharacter.equals(allyKingSymbol) || currentCharacter.equals(allyPieceSymbol) ||
					currentCharacter.equals(enemyKingSymbol) || currentCharacter.equals(enemyPieceSymbol))
				result.get(currentRow).set(currentColumn, invalidMoveSymbol);
			
			//If position is empty, can move there
			else
				result.get(currentRow).set(currentColumn, moveSymbol); 
		}
		
		//To mark itself
		result.get(posRow).set(posCol, currentPositionSymbol); 
		return result;
	}
}

