package Items;

import ChessGameClasses.Board;
import ChessPieces.ChessPiece;

import java.util.ArrayList;

/**
 * Items that can be used to influence the Chess Game Board.
 */
public interface Item 
{
    /**
     * Method is called when the user chooses an item.  The method uses a copy of the game board to simulate the use
     * of the item, and if that item results in the player's king being in check, then the item is not used. If the item
     * does not result in the king being in check, then the copy game board is copied to the old board, and the item can
     * be used.
     * @param color - String, piece color of the current player.
     * @param board - Board, game board.
     * @return - message - if the item was used, the item description, if not, then a message that the item was not used.
     */
    public String run( String color, Board board);

    /**
     * Method is defined by classes that implement Item.
     * @param color - String, piece color of the current player.
     * @param allyChessPieces - ArrayList<ChessPiece>, list of ally chess pieces.
     * @param opponentChessPieces - ArrayList<ChessPiece>, list of opponent chess pieces.
     * @param board - Board, game board.
     * @return - String - the message of whether the item was used or the item description.
     */
    public String use(String color, ArrayList<ChessPiece> allyChessPieces, ArrayList<ChessPiece> opponentChessPieces, Board board);

    /**
     * Method to get the Item name.
     * @return - String, itemName.
     */
    public String getItemName();

    /**
     * Method to set the Item name.
     * @param itemName - String, item name.
     */
    public void setItemName(String itemName);

    /**
     * Method to get the Item description.
     * @return - String, item description.
     */
    public String getItemDescription();

    /**
     * Method to set the Item description.
     * @param itemDescription - String, item description.
     */
    public void setItemDescription(String itemDescription);

    /**
     * Method to get the Item cost.
     * @return - int, item cost.
     */
    public int getItemCost();

    /**
     * Method to set the Item cost.
     * @param itemCost - int, item cost.
     */
    public void setItemCost(int itemCost);
}