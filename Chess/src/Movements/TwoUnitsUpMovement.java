package Movements;

import ChessGameClasses.Board;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Moving two units up.
 */
public class TwoUnitsUpMovement implements Movement,Serializable
{	/**
	 * A constant String representing the name of the movement.
	 */
    public static final String movementName = "Two Units Up";
    
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
    public ArrayList<ArrayList<Character>> calculateMovement(int posRow, int posCol, String color, boolean moved, Board board) 
    {
    	//Create an empty ArrayList<ArrayList<Character>> to store the possible moves
        ArrayList<ArrayList<Character>> result = Board.createAnEmpty2DCharacterArrayList();

        if(moved)
        {
            return result;
        }

        int col = posCol, row;
        int direction = 0;

        if(color.compareTo("White") == 0)
            direction = -1;
        else
            direction = 1;
        
        //If the position is not out of range
		if (board.rowColWithinBound(posRow + direction, col) &&
				board.rowColWithinBound(posRow + direction + direction, col))
        {
	        	
	        row = posRow + direction;
	        Character pathCharacter = board.getPositionBoard().get(row).get(col);
	        
	        row += direction;
	        Character destinationCharacter = board.getPositionBoard().get(row).get(col);
	        
	        //If path and destination are not occupied, then this Movement can be made.
	        if (pathCharacter.equals(Movement.invalidMoveSymbol) &&
	        		destinationCharacter.equals(Movement.invalidMoveSymbol))
	        {
	            result.get(row).set(col, Movement.twoUnitsUpSymbol);
	        }
        }

        return result;
    }
}
