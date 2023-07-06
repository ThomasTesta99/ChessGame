package Movements;

import ChessGameClasses.Board;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * A movement that captures by hopping over a piece.
 */
public class HopCaptureMovement implements Movement,Serializable
{	/**
	 * A constant String representing the name of the movement.
	 */
	public static final String movementName = "Hop Capture";
	
	/**
	 * A constant int representing the cost of the movement.
	 */
	public static final int movementCost = 800;
	
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
		
		
		//Logic
		
		//Hop Up
		hopTowardsDirection(posRow, posCol, color, board, result, -1, 0);
		
		//Hop Down
		hopTowardsDirection(posRow, posCol, color, board, result, 1, 0);
		
		//Hop Left
		hopTowardsDirection(posRow, posCol, color, board, result, 0, -1);
				
		//Hop Right
		hopTowardsDirection(posRow, posCol, color, board, result, 0, 1);
		
		//To mark itself
		result.get(posRow).set(posCol, currentPositionSymbol); 
		return result;
	}
	
	/**
	 * 
	 * @param posRow - int, The current row that the chess piece is in.
	 * @param posCol - int, The current column that the chess piece is in.d.
	 * @param color - String, The color of the chess piece.
	 * @param board - Board,the whole chess board.
	 * @param result - ArrayList<ArrayList<Character>>, the empty ArrayList that will store the result.
	 * @param directionRow - int, a value that will be added to posRow.
	 * @param directionColumn - int, a value that will be added to posCol.
	 */
	public void hopTowardsDirection(int posRow, int posCol, String color, Board board, ArrayList<ArrayList<Character>> result, int directionRow, int directionColumn)
	{
		//Determine the ally and enemy symbols depending on the color
		ArrayList<Character> pieceSymbols = getSymbols(color);
		Character allyKingSymbol = pieceSymbols.get(0);
		Character allyPieceSymbol = pieceSymbols.get(1);
		Character enemyKingSymbol = pieceSymbols.get(2);
		Character enemyPieceSymbol = pieceSymbols.get(3);
		
		int currentRow = posRow + directionRow;
		int currentColumn = posCol + directionColumn;
		
		//If the position is not out of range
		while (board.rowColWithinBound(currentRow, currentColumn))
		{
			Character currentCharacter = board.getPositionBoard().get(currentRow).get(currentColumn);
			
			//The first piece that is found is the piece to jump over.
			if (currentCharacter.equals(allyKingSymbol) || currentCharacter.equals(allyPieceSymbol) ||
					currentCharacter.equals(enemyKingSymbol) || currentCharacter.equals(enemyPieceSymbol))
			{
				currentRow += directionRow;
				currentColumn += directionColumn;
				break;
			}
			currentRow += directionRow;
			currentColumn += directionColumn;
		}
		
		//Find possible capture piece
		while (board.rowColWithinBound(currentRow, currentColumn))
		{
			Character currentCharacter = board.getPositionBoard().get(currentRow).get(currentColumn);
			
			//The next piece is the piece that can be captured if it is an opponent piece.
			if (currentCharacter.equals(allyKingSymbol) || currentCharacter.equals(allyPieceSymbol) ||
					currentCharacter.equals(enemyKingSymbol) || currentCharacter.equals(enemyPieceSymbol))
			{
				//If it is an opponent piece, it can be captured.
				if (currentCharacter.equals(enemyKingSymbol) || currentCharacter.equals(enemyPieceSymbol))
					result.get(currentRow).set(currentColumn, moveAndCaptureSymbol);
				
				//If it is an ally piece, it is an invalid move.
				else if (currentCharacter.equals(allyKingSymbol) || currentCharacter.equals(allyPieceSymbol))
					result.get(currentRow).set(currentColumn, invalidMoveSymbol);
				break;
			}
			currentRow += directionRow;
			currentColumn += directionColumn;
		}
	}
}
