package Movements;

import ChessGameClasses.Board;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * A movement that allows range capture.
 */
public class RangeCaptureMovement implements Movement,Serializable
{	/**
	 * A constant String representing the name of the movement.
	 */
	public static final String movementName = "Range Capture";
	
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
		final int range = 2;
		int currentRow = posRow;
		int currentColumn = posCol;
		
		if (color.equals("White")) currentRow -= range; //Assuming White is on the bottom
		else currentRow += range;
		
		//Left side
		checkPosition(color, board, result, currentRow, currentColumn - 2);
		
		//Center side
		checkPosition(color, board, result, currentRow, currentColumn);
		
		//Right side
		checkPosition(color, board, result, currentRow, currentColumn + 2);
		
		//To mark itself
		result.get(posRow).set(posCol, currentPositionSymbol); 
		return result;
	}
	
	/**
	 * 
	 * @param color - String, the color of the current piece.
	 * @param board - Board, the whole chess board.
	 * @param result - ArrayList<ArrayList<Character>>, An empty ArrayList.
	 * @param row - int, The current row.
	 * @param column, The current column.
	 */
	public void checkPosition(String color, Board board, ArrayList<ArrayList<Character>> result, int row, int column)
	{
		//Determine the ally and enemy symbols depending on the color
		ArrayList<Character> pieceSymbols = getSymbols(color);
		Character enemyKingSymbol = pieceSymbols.get(2);
		Character enemyPieceSymbol = pieceSymbols.get(3);
				
		int currentRow = row;
		int currentColumn = column;
		
		//If the position is not out of range
		if (board.rowColWithinBound(currentRow, currentColumn))
		{
			Character currentCharacter = board.getPositionBoard().get(currentRow).get(currentColumn);

			//If position is occupied by enemy, it can capture. Otherwise it can't move nor capture there.
			if (currentCharacter.equals(enemyKingSymbol) || currentCharacter.equals(enemyPieceSymbol))
				result.get(currentRow).set(currentColumn, captureSymbol); 
		}
	}
}

