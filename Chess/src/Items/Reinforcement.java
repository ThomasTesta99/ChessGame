package Items;

import ChessGameClasses.Board;
import ChessPieces.ChessPiece;
import ChessPieces.Pawn;

import java.util.ArrayList;

/**
 * A Item That Summons A New Line Of Pawns.
 */
public class Reinforcement extends DefinedItem{

    /**
     * Class parametrized constructor.
     * @param name - String - item name.
     * @param description - String, item description.
     * @param cost - int, item cost.
     */
    public Reinforcement(String name, String description, int cost){
        super(name, description, cost);
    }

    /**
     * Class default constructor.
     */
    public Reinforcement(){
        super("Reinforcement", "Summon A New Line Of Pawns.", 5000);
    }

    /**
     * Method to use the item. Depending on the player, the method loops through a row and if the spot is empty, it
     * places a new pawn there and adds it to the player arraylist.
     * @param color - String, piece color of the current player.
     * @param allyChessPieces - ArrayList<ChessPiece>, list of ally chess pieces.
     * @param opponentChessPieces - ArrayList<ChessPiece>, list of opponent chess pieces.
     * @param board - Board, game board.
     * @return - message stating the color of the player that now has a new line of pawns.
     */
    @Override
    public String use(String color, ArrayList<ChessPiece> allyChessPieces, ArrayList<ChessPiece> opponentChessPieces, Board board) {
        int colNums = board.getPositionBoard().get(0).size();
        int row = 2;
        if(color.equals("White")){
            row = board.getPositionBoard().size() - 3;
            for(int col = 0; col < colNums; col++){
                if(board.getPositionBoard().get(row).get(col).equals(' ')){
                    allyChessPieces.add(new Pawn(row,col, "White", 'P'));
                }
            }
        }
        else{
            for(int col = 0; col < colNums; col++){
                if(board.getPositionBoard().get(row).get(col).equals(' ')){
                    allyChessPieces.add(new Pawn(row,col, "Black", 'p'));
                }
            }
        }
        
        return "A New Line Of Pawns Was Summoned For " + color + ".";
    }
}