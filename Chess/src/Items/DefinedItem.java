package Items;

import java.util.ArrayList;

import ChessGameClasses.Board;
import ChessPieces.ChessPiece;

/**
 * Items that have a defined outcome. 
 */
public abstract class DefinedItem implements Item
{
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
	 * @param name - String, item name.
	 * @param description - String, item description.
	 * @param cost - int, item cost.
	 */
    public DefinedItem(String name, String description, int cost)
    {
        setItemName(name);
        setItemDescription(description);
        setItemCost(cost);
    }
    
    public String getItemName() { return itemName; }
    public void setItemName(String itemName) { this.itemName = itemName; }
	public String getItemDescription() { return itemDescription; }
	public void setItemDescription(String itemDescription) { this.itemDescription = itemDescription; }
    public int getItemCost() { return itemCost; }
    public void setItemCost(int itemCost) { this.itemCost = itemCost; }

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
    	String message;
    	
    	//Create a copy of the current board.
    	Board copyBoard = new Board(board);
    			
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
    	
    	message = use(color, allyChessPieces, opponentChessPieces, copyBoard);
    	
    	copyBoard.updatePositionBoard();
		copyBoard.updateControlBoards();
		
		//Allow the item to influence the board if it does not result in a check for both Players.
		if (!copyBoard.isInCheck("White") && !copyBoard.isInCheck("Black"))
		{
			board.deepCopy(copyBoard);
			return message;
		}
		else
		{
			return "Item Has Been Burned Because The Item Results In A Check.";
		}
    }
}