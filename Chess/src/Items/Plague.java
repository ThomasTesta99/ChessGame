package Items;

import ChessGameClasses.Board;
import ChessPieces.*;

import java.util.ArrayList;

/**
 * An Item That Randomly Pick A Chess Piece On The Board And Eliminates All Chess Pieces Of The Same Type.
 */
public class Plague extends RandomItem
{
    /**
     * Class parametrized constructor.
     * @param name - String - item name.
     * @param description - String, item description.
     * @param cost - int, item cost.
     */
    public Plague(String name, String description, int cost)
    {
        super(name, description, cost);
    }

    /**
     * Class default constructor.
     */
    public Plague()
    {
        super("Plague", "Randomly Pick A Chess Piece On The Board And Eliminates All Chess Pieces Of The Same Type.", 0);
    }

    /**
     * Method to use the item.  The method loops through the ally player's chess pieces and stores the piece name in an
     * arraylist that marks it as a possible target for the plague. If it's a king, or the piece is captured, it's name
     * is not placed in the arraylist. Then the method gets a random index, and eliminates all the pieces with the
     * name chosen at the random index.
     * @param color - String, piece color of the current player.
     * @param allyChessPieces - ArrayList<ChessPiece>, list of ally chess pieces.
     * @param opponentChessPieces - ArrayList<ChessPiece>, list of opponent chess pieces.
     * @param board - Board, game board.
     * @return - message of the piece that Plague has fallen on or if there are not enough pieces for the item to work.
     */
    @Override
    public String use(String color, ArrayList<ChessPiece> allyChessPieces, ArrayList<ChessPiece> opponentChessPieces, Board board) 
    {
        //All the Possible Targets
        ArrayList<String> possibleTargets = new ArrayList<String>();
        
        //Record all valid targets into possibleTargets
        //1) They are valid if the chess piece hasn't been captured, 
        //2) It is not the king.
        //3) It doesn't already exist in possibleTargets.
        for (int i = 0; i < allyChessPieces.size(); i++)
        	if (!allyChessPieces.get(i).getIsCaptured())
        		if (!allyChessPieces.get(i).getName().equals(King.name)) 
        			if (!targetAlreadyExists(allyChessPieces.get(i).getName(), possibleTargets))
        				possibleTargets.add(allyChessPieces.get(i).getName());
        
        if (possibleTargets.size() > 0)
        {
	        int randomIndex;
	        String target;
	        
		    randomIndex = getRandomNum(possibleTargets.size());
		    target = possibleTargets.get(randomIndex);
	        
	        
	        for (ChessPiece chessPiece : allyChessPieces)
	        	if (!chessPiece.getIsCaptured())
		        	if (chessPiece.getName().equals(target))
		        		chessPiece.chessPieceGetsCaptured();
	        
	        for (ChessPiece chessPiece : opponentChessPieces)
	        	if (!chessPiece.getIsCaptured())
		        	if (chessPiece.getName().equals(target))
		        		chessPiece.chessPieceGetsCaptured();
	        
	        return "A Plague Has Fallen On All Of The " + target + " Pieces.";
        }
        else
        {
        	return "Not Enough Victims For The Plague To Spread.";
        }
    }

    /**
     * Method to determine if the target piece doesn't already exist in the possible targets.
     * @param target - String, target name.
     * @param possibleTargets - ArrayList<String>, possible targets for the plague.
     * @return - boolean, true if it exists.
     */
    private boolean targetAlreadyExists(String target, ArrayList<String> possibleTargets)
    {
    	for (String name : possibleTargets)
    		if (name.equals(target))
    				return true;
    	return false;
    }
}