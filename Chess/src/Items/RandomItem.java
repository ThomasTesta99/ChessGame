package Items;

import ChessGameClasses.Board;
import ChessPieces.ChessPiece;

import java.util.ArrayList;

/**
 * An Item With A Random Outcome.
 */
public abstract class RandomItem implements Item{
	/**
	 * Item name
	 */
    private String itemName;
    
    /**
     * Item description
     */
    private String itemDescription;
    
    /**
     * Item cost
     */
    private int itemCost;

    /**
     * Class constructor.
     * @param itemName - String, item name.
     * @param itemDescription - String, item description.
     * @param itemCost - int, item cost.
     */
    public RandomItem(String itemName, String itemDescription, int itemCost){
        setItemName(itemName);
        setItemDescription(itemDescription);
        setItemCost(itemCost);
    }

    /**
     * Method to generate a random number.
     * @param range - int, range for how big the random number can be.
     * @return - int, random number.
     */
    public int getRandomNum(int range){
        return (int)(Math.random() * range);
    }

    @Override
    public String getItemName() {
        return itemName;
    }
    @Override
    public void setItemName(String itemName) {
        this.itemName = itemName;
    }
    @Override
    public String getItemDescription() {
        return itemDescription;
    }
    @Override
    public void setItemDescription(String itemDescription) {
        this.itemDescription = itemDescription;
    }
    @Override
    public int getItemCost() {
        return itemCost;
    }
    @Override
    public void setItemCost(int itemCost) {
        this.itemCost = itemCost;
    }

    /**
     * Method to return the DefinedItem object as a String.
     * @return "Name " + name (new line) + "Description." + description.
     */
    public String toString()
    {
    	return "Name: " + getItemName() + "\n" +
    			"Description: " + getItemDescription() + "\n";
    }

    public String run(String color, Board board)
    {        
    	//The item's message.
    	String message = null;
    	
        //The board to store the simulation. 
        Board copyBoard = null;
        
        //Max number of times to re run the item if the item results in a check for both Kings. 
        int maxReRun = 100;
        
        //Simulate the item. If the item user's King is in check after the item ran, run the item again.
        //Keep running until the user's King is not in check or it has reached the maxReRun.
        for (int i = 0; i < maxReRun; i++)
        {
        	copyBoard = new Board(board);

            //Determine the allyChessPieces and the enemyChessPieces
            ArrayList<ChessPiece> opponentChessPieces;
            ArrayList<ChessPiece> allyChessPieces;
            if (color.equals("White"))
            {
                opponentChessPieces = copyBoard.getBlackChessPieces();
                allyChessPieces = copyBoard.getWhiteChessPieces();
            }
            else //color == "Black"
            {
                opponentChessPieces = copyBoard.getWhiteChessPieces();
                allyChessPieces = copyBoard.getBlackChessPieces();
            }
            
            //Run the item
            message = use(color, allyChessPieces, opponentChessPieces, copyBoard);

            //Prepare for isInCheck() calculation.
            copyBoard.updatePositionBoard();
            copyBoard.updateControlBoards();
            
            //If both players are not in check, we have found the valid result board. 
            if (!copyBoard.isInCheck("White") && !copyBoard.isInCheck("Black"))
                break;
        }
        
      //Allow item to influence the board if both players are not in check.
       if (!copyBoard.isInCheck("White") && !copyBoard.isInCheck("Black"))
       {
    	   board.deepCopy(copyBoard);
    	   return message;
       }
       else
       {
    	   return "Item Has Been Burned Because The Item Resulted In A Check " + maxReRun + " Times In A Row.";
       }
    }
}