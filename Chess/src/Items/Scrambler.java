package Items;

import ChessGameClasses.Board;
import ChessPieces.*;

import java.util.ArrayList;
import java.util.Collections;

/**
 * An Item That Scrambles The Board.
 */
public class Scrambler extends RandomItem{

	/**
	 * Class parametrized constructor.
	 * @param name - String - item name.
	 * @param description - String, item description.
	 * @param cost - int, item cost.
	 */
	public Scrambler(String name, String description, int cost)
	{
        super(name, description, cost);
    }

	/**
	 * Class default constructor.
	 */
	public Scrambler() 
	{
		super("Scrambler", "Scramble The Entire Board", 0);
	}

	/**
	 * Method to use the item. First the method sets all the piece's hasMoved to true, since they will all be moving.
	 * Then it uses an IntPair arrayList to store all spots on the board, and shuffles them. Then it loops through all the black pieces and
	 * places them in the new spot. After all the black pieces have been placed, the IntPair arraylist is shuffled again and the process is
	 * done again on the white pieces.
	 * @param color - String, piece color of the current player.
	 * @param allyChessPieces - ArrayList<ChessPiece>, list of ally chess pieces.
	 * @param opponentChessPieces - ArrayList<ChessPiece>, list of opponent chess pieces.
	 * @param board - Board, game board.
	 * @return - message stating the board has been scrambled.
	 */
	@Override
	public String use(String color, ArrayList<ChessPiece> allyChessPieces, ArrayList<ChessPiece> opponentChessPieces, Board board) 
	{
	
		//Set all the chess pieces to has moved
		for (ChessPiece chessPiece : board.getBlackChessPieces())
			chessPiece.setHasMoved(true);
			
		for (ChessPiece chessPiece : board.getWhiteChessPieces())
			chessPiece.setHasMoved(true);
		
		//Add the whole board into the ArrayList. ArrayList will take row and col and add it into the arraylist together.
		ArrayList<IntPair> allPosition = new ArrayList<IntPair>();
		for(int row=0;row<10;row++)
		{
			for(int col=0;col<9;col++)
			{
				allPosition.add(new IntPair(row,col));
			}
		}
		//It will shuffle the elements inside allPosition
		Collections.shuffle(allPosition);
		//Set black chess pieces row and col to a random one on the board. To ensure two pieces doesn't go to the same spot, once one position one is added, it will be deleted.
		for(int i=0;i<board.getBlackChessPieces().size();i++)
		{
			if (!board.getBlackChessPieces().get(i).getIsCaptured())
			{
			  board.getBlackChessPieces().get(i).setPosRow(allPosition.get(i).getFirst());
			  board.getBlackChessPieces().get(i).setPosCol(allPosition.get(i).getSecond());
			  allPosition.remove(i);
			}
		}
		
		//shuffle the elements again
		Collections.shuffle(allPosition);
		//set the white pieces row and col to the leftover positions in allPosition
		for(int i=0;i<board.getWhiteChessPieces().size();i++)
		{
			 
			if (!board.getWhiteChessPieces().get(i).getIsCaptured())
			{
			   board.getWhiteChessPieces().get(i).setPosRow(allPosition.get(i).getFirst());
			   board.getWhiteChessPieces().get(i).setPosCol(allPosition.get(i).getSecond());
			}
			 
		}
		
		return "The Board Has Been Scrambled.";
	 }
}