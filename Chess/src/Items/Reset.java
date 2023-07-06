package Items;

import ChessGameClasses.Board;
import ChessGameClasses.ChessGameRunner;
import ChessPieces.*;

import java.util.ArrayList;

/**
 * A Item That Resets The Board.
 */
public class Reset extends DefinedItem
{

	/**
	 * Class parametrized constructor.
	 * @param name - String - item name.
	 * @param description - String, item description.
	 * @param cost - int, item cost.
	 */
    public Reset(String name, String description, int cost)
    {
        super(name, description, cost);
    }

	/**
	 * Class default constructor.
	 */
    public Reset()
    {
        super("Reset", "Reset The Board", 0);
    }

	/**
	 * Method to use the item.  The method determines the original spots of the pieces to place the current pieces in
	 * those spots.  If the player has extra pieces those pieces are deleted.
	 * @param color - String, piece color of the current player.
	 * @param allyChessPieces - ArrayList<ChessPiece>, list of ally chess pieces.
	 * @param opponentChessPieces - ArrayList<ChessPiece>, list of opponent chess pieces.
	 * @param board - Board, game board.
	 * @return - message stating the board has been reset.
	 */
    @Override
    public String use(String color, ArrayList<ChessPiece> allyChessPieces, ArrayList<ChessPiece> opponentChessPieces, Board board)
    {
    	//Store the original chess piece set of both player.
    	ArrayList<ChessPiece> initialAllyChessPieces;
    	ArrayList<ChessPiece> initialOpponentChessPieces;
    
    	//Determine both player's original chess piece set depending on their color.
    	if (color.equals("White"))
    	{
    		initialAllyChessPieces = ChessGameRunner.createWhiteChessSet();
    		initialOpponentChessPieces = ChessGameRunner.createBlackChessSet();
    	}
    	else
    	{
    		initialAllyChessPieces = ChessGameRunner.createBlackChessSet();
    		initialOpponentChessPieces = ChessGameRunner.createWhiteChessSet();
    	}
    	
    	//Loop through the passed-in chess piece sets, reset all chess pieces that haven't been captured.
        for (int i = 0; i < initialAllyChessPieces.size(); i++)
        	if (!allyChessPieces.get(i).getIsCaptured())
        	{
        		allyChessPieces.get(i).setPosRow(initialAllyChessPieces.get(i).getPosRow());
        		allyChessPieces.get(i).setPosCol(initialAllyChessPieces.get(i).getPosCol());
        		allyChessPieces.get(i).setHasMoved(false);
        	}
        
        //Delete the extra pieces
        for (int i = initialAllyChessPieces.size(); i < allyChessPieces.size(); i++)
        {
        	allyChessPieces.remove(i);
        	i--;
        }
        
        for (int i = 0; i < initialOpponentChessPieces.size(); i++)
        	if (!opponentChessPieces.get(i).getIsCaptured())
        	{
        		opponentChessPieces.get(i).setPosRow(initialOpponentChessPieces.get(i).getPosRow());
        		opponentChessPieces.get(i).setPosCol(initialOpponentChessPieces.get(i).getPosCol());
        		opponentChessPieces.get(i).setHasMoved(false);
        	}
        
        //Delete the extra pieces
        for (int i = initialOpponentChessPieces.size(); i < opponentChessPieces.size(); i++)
        {
        	opponentChessPieces.remove(i);
        	i--;
        }
        
        return "The Board Has Been Reset.";
    }
}