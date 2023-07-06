package Items;

import ChessGameClasses.Board;
import ChessPieces.*;

import java.util.ArrayList;

/**
 * An Item That Eliminates All Unique Pieces(Archers, Cannons, Ninjas, And Wizards)."
 */
public class UniqueSlayer extends DefinedItem
{
    /**
     * Class parametrized constructor.
     * @param name - String - item name.
     * @param description - String, item description.
     * @param cost - int, item cost.
     */
    public UniqueSlayer(String name, String description, int cost)
    {
        super(name, description, cost);
    }

    /**
     * Class default constructor.
     */
    public UniqueSlayer()
    {
        super("Unique Slayer", "Eliminate All Unique Pieces(Archers, Cannons, Ninjas, And Wizards).", 2500);
    }

    /**
     * Method to use the item.  The opponent's and current player's chess pieces are looped through and if the pieces is an instanceof a unique
     * pieces, then that piece is marked as captured.
     * @param color - String, piece color of the current player.
     * @param allyChessPieces - ArrayList<ChessPiece>, list of ally chess pieces.
     * @param opponentChessPieces - ArrayList<ChessPiece>, list of opponent chess pieces.
     * @param board - Board, game board.
     * @return - message stating that the unique pieces have been eliminated.
     */
    @Override
    public String use(String color, ArrayList<ChessPiece> allyChessPieces, ArrayList<ChessPiece> opponentChessPieces, Board board)
    {
        for(ChessPiece piece : opponentChessPieces)
        {
        	if (!piece.getIsCaptured())
        	{
	            if(piece instanceof Wizard || piece instanceof Ninja || piece instanceof Cannon || piece instanceof Archer)
	            {
	                piece.chessPieceGetsCaptured();
	            }
        	}
        }
        for(ChessPiece piece : allyChessPieces)
        {
        	if (!piece.getIsCaptured())
        	{
	            if(piece instanceof Wizard || piece instanceof Ninja || piece instanceof Cannon || piece instanceof Archer)
	            {
	                piece.chessPieceGetsCaptured();
	            }
        	}
        }
        
        return "Archers, Cannons, Ninjas, And Wizards Have All Been Eliminated.";
    }
}