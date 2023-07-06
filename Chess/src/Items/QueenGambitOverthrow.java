package Items;

import ChessGameClasses.Board;
import ChessPieces.ChessPiece;
import ChessPieces.Queen;

import java.util.ArrayList;

/**
 * An Item That Eliminates All Of Your Opponent’s Queen.
 */
public class QueenGambitOverthrow extends DefinedItem
{

    /**
     * Class parametrized constructor.
     * @param name - String - item name.
     * @param description - String, item description.
     * @param cost - int, item cost.
     */
    public QueenGambitOverthrow(String name, String description, int cost)
    {
        super(name, description, cost);
    }

    /**
     * Class default constructor.
     */
    public QueenGambitOverthrow() 
    {
        super("Queen Gambit Overthrow", "Eliminate All Of Your Opponent’s Queen." ,5000);
    }

    /**
     * Method to use the item. Loops through the opponent's chess pieces and if it is a queen, it marks the piece
     * as captured.
     * @param color - String, piece color of the current player.
     * @param allyChessPieces - ArrayList<ChessPiece>, list of ally chess pieces.
     * @param opponentChessPieces - ArrayList<ChessPiece>, list of opponent chess pieces.
     * @param board - Board, game board.
     * @return - message stating that all the player's Queens have been eliminated.
     */
    @Override
    public String use(String color, ArrayList<ChessPiece> allyChessPieces, ArrayList<ChessPiece> opponentChessPieces, Board board)
    {
        for(ChessPiece potentialQueen : opponentChessPieces)
        {
        	if (!potentialQueen.getIsCaptured())
        	{
	            if(potentialQueen instanceof Queen)
	            {
	                potentialQueen.chessPieceGetsCaptured();
	            }
        	}
        }
        
        String opponentColor;
        if (color.equals("White")) opponentColor = "Black";
        else opponentColor = "White";
        return "All Of " + opponentColor + "'s Queens Were Eliminated.";
    }
}