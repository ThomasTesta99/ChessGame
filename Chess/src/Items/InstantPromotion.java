package Items;

import ChessGameClasses.Board;
import ChessPieces.*;

import java.util.ArrayList;

/**
 * An Item That Instantly Upgrades A Random Pawn Of The Item User To A Queen.
 */
public class InstantPromotion extends RandomItem{

    /**
     * Class parametrized constructor.
     * @param name - String - item name.
     * @param description - String, item description.
     * @param cost - int, item cost.
     */
    public InstantPromotion(String name, String description, int cost){
        super(name, description, cost);
    }

    /**
     * Class default constructor.
     */
    public InstantPromotion(){
        super("Instant Promotion", "Instantly Upgrade A Random Pawn Of The Item User To A Queen.", 2500);
    }

    /**
     * Method to use the item. The method loops through the ally chess pieces and stores any pawn in an ArrayList. Then a
     * random index is chosen, and that pawn is selected to be promoted to a queen.
     * @param color - String, piece color of the current player.
     * @param allyChessPieces - ArrayList<ChessPiece>, list of ally chess pieces.
     * @param opponentChessPieces - ArrayList<ChessPiece>, list of opponent chess pieces.
     * @param board - Board, game board.
     * @return - String - the message of whether the item was used or the item description.
     */
    @Override
    public String use(String color, ArrayList<ChessPiece> allyChessPieces, ArrayList<ChessPiece> opponentChessPieces, Board board) {
        ArrayList<Integer> pawns = new ArrayList<>();

        for(int i = 0; i < allyChessPieces.size(); i++){
            if(allyChessPieces.get(i) instanceof Pawn && !allyChessPieces.get(i).getIsCaptured()){
                pawns.add(i);
            }
        }

        if(pawns.size() == 0)
        {
            return "No Pawns Were Promoted Because There Are No Pawns.";
        }

        int randomIndex = getRandomNum(pawns.size());

        int oldPawnIndex = pawns.get(randomIndex);
        ChessPiece oldPiece = allyChessPieces.get(oldPawnIndex);
        int row = oldPiece.getPosRow();
        int col = oldPiece.getPosCol();

        ChessPiece newQueen = null;

        if(color.equals("White")){
            newQueen = new Queen(row, col, color, 'Q');
        }else{
            newQueen = new Queen(row, col, color, 'q');
        }

        allyChessPieces.remove(oldPiece);
        allyChessPieces.add(oldPawnIndex, newQueen);
        
        return "The Pawn At (" + row + ", " + col + ") Was Promoted To A Queen."; 
    }
}