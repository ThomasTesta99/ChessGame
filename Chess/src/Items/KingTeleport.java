package Items;

import ChessGameClasses.Board;
import ChessPieces.*;

import java.util.ArrayList;

/**
 * An Item That Moves Both Kings To A Random Location On The Board.
 */
public class KingTeleport extends RandomItem{

    /**
     * Class parametrized constructor.
     * @param name - String - item name.
     * @param description - String, item description.
     * @param cost - int, item cost.
     */
    public KingTeleport(String name, String description, int cost){
        super(name, description, cost);
    }

    /**
     * Class default constructor.
     */
    public KingTeleport(){
        super("King Teleport", "Moves Both Kings To A Random Location On The Board.", 1000);
    }


    /**
     * Method to use the item. First it calls the private method findKing to find the enemy and ally king. Then it finds
     * an empty spot inside, and the king's position is moved there.  Whether the king is in check is handled in the
     * run function in RandomItem.
     * @param color - String, piece color of the current player.
     * @param allyChessPieces - ArrayList<ChessPiece>, list of ally chess pieces.
     * @param opponentChessPieces - ArrayList<ChessPiece>, list of opponent chess pieces.
     * @param board - Board, game board.
     * @return - String - message of stating the king was teleported.
     */
    @Override
    public String use(String color, ArrayList<ChessPiece> allyChessPieces, ArrayList<ChessPiece> opponentChessPieces, Board board) {
        int allyKingIndex = findKing(allyChessPieces);
        int oppKingIndex = findKing(opponentChessPieces);

        ArrayList<Integer> newAllyPos = findEmptySpot(board);
        allyChessPieces.get(allyKingIndex).updateMove(newAllyPos.get(0), newAllyPos.get(1));

        ArrayList<Integer> newOppPos = findEmptySpot(board);
        opponentChessPieces.get(oppKingIndex).updateMove(newOppPos.get(0), newOppPos.get(1));
        
        return "Both Kings Were Teleported.";
    }

    /**
     * Method to loop through the player pieces and find the king.
     * @param playerPieces - ArrayList<ChessPiece>, player pieces.
     * @return - int - index the king is at in the ArrayList.
     */
    private int findKing(ArrayList<ChessPiece> playerPieces){
        int kingIndex = -1;

        for(int i = 0; i < playerPieces.size(); i++){
            if(playerPieces.get(i) instanceof King){
                kingIndex = i;
            }
        }

        return kingIndex;
    }

    /**
     * Method to find an empty spot on the board to place the kings at.
     * @param board - Board, game board.
     * @return - empty spot - ArrayList<Integer>.
     */
    private ArrayList<Integer> findEmptySpot(Board board){
        boolean emptySpot = false;
        int randomRow = getRandomNum(board.getPositionBoard().size());
        int randomCol = getRandomNum(board.getPositionBoard().get(0).size());
        while(!emptySpot){
            if(board.getPositionBoard().get(randomRow).get(randomCol).equals(' ')){
                emptySpot = true;
            }else{
                randomRow = getRandomNum(board.getPositionBoard().size());
                randomCol = getRandomNum(board.getPositionBoard().get(0).size());
            }
        }
        ArrayList<Integer> emptyPosition = new ArrayList<>();
        emptyPosition.add(randomRow);
        emptyPosition.add(randomCol);
        return emptyPosition;
    }
}