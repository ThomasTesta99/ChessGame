package Items;

import ChessGameClasses.Board;
import ChessPieces.*;

import java.util.ArrayList;

/**
 * An Item That Randomly Swap Two Chess Pieces Each Own By One Player.
 */
public class Swapper extends RandomItem{

    /**
     * Class parametrized constructor.
     * @param name - String - item name.
     * @param description - String, item description.
     * @param cost - int, item cost.
     */
    public Swapper(String name, String description, int cost){
        super(name, description, cost);
    }

    /**
     * Class default constructor.
     */
    public Swapper(){
        super("Swapper", "Randomly Swap Two Chess Pieces Each Own By One Player.", 1000);
    }

    /**
     * Method to use the item.  The method stores all the valid indexes(not a king at first), into an arraylist, then it chooses
     * a random index for both colors and swaps the position of pieces. If there are only king indexes left, then the
     * kings are swapped.
     * @param color - String, piece color of the current player.
     * @param allyChessPieces - ArrayList<ChessPiece>, list of ally chess pieces.
     * @param opponentChessPieces - ArrayList<ChessPiece>, list of opponent chess pieces.
     * @param board - Board, game board.
     * @return - message to say which pieces are being swapped and where their new position is.
     */
    @Override
    public String use(String color, ArrayList<ChessPiece> allyChessPieces, ArrayList<ChessPiece> opponentChessPieces, Board board) {
        
        //Valid indexes of Chess Pieces
        ArrayList<Integer> allyValidIndex = new ArrayList<Integer>();
        ArrayList<Integer> opponentValidIndex = new ArrayList<Integer>();
        
        int allyKingIndex = -1;
        int opponentKingIndex = -1;
        
        //Valid if they are not captured and are not Kings
        for (int i = 0; i < allyChessPieces.size(); i++)
        {
        	if (!allyChessPieces.get(i).getIsCaptured())
        	{
        		if (!(allyChessPieces.get(i) instanceof King))
        			allyValidIndex.add(i);
        		else
        			allyKingIndex = i;
        	}
        }

        //Valid if they are not captured and are not Kings
        for (int i = 0; i < opponentChessPieces.size(); i++)
        {
        	if (!opponentChessPieces.get(i).getIsCaptured())
        	{
        		if (!(opponentChessPieces.get(i) instanceof King))
        			opponentValidIndex.add(i);
        		else
        			opponentKingIndex = i;
        	}
        }
        
        //If one player only has a King, then add the kings in
        if (allyValidIndex.size() == 0 || opponentValidIndex.size() == 0)
        {
        	allyValidIndex.add(allyKingIndex);
        	opponentValidIndex.add(opponentKingIndex);
        }
        
        
        int num1 = allyValidIndex.get(getRandomNum(allyValidIndex.size()));
        int num2 = opponentValidIndex.get(getRandomNum(opponentValidIndex.size()));
        
        ChessPiece randomAllyPiece = allyChessPieces.get(num1);
        ChessPiece randomOpponentPiece = opponentChessPieces.get(num2);
        

        int allyRow = randomAllyPiece.getPosRow();
        int allyCol = randomAllyPiece.getPosCol();

        int oppRow = randomOpponentPiece.getPosRow();
        int oppCol = randomOpponentPiece.getPosCol();

        String message;
        message = "Swapping: " + randomAllyPiece.getName() + " at (" + allyRow + ", " + allyCol + ") with ";
        message += randomOpponentPiece.getName() + " at (" + oppRow + ", " + oppCol + ")\n";


        randomAllyPiece.updateMove(oppRow, oppCol);
        randomOpponentPiece.updateMove(allyRow, allyCol);

        message +=  randomAllyPiece.getName() + " now at (" + randomAllyPiece.getPosRow() + ", " + randomAllyPiece.getPosCol() + ") with ";
        message += randomOpponentPiece.getName() + " now at (" + randomOpponentPiece.getPosRow() + ", " + randomOpponentPiece.getPosCol() + ")";
        return message;
    }
}