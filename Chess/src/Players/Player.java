package Players;
import java.util.ArrayList;

import ChessPieces.Bishop;
import ChessPieces.Cannon;
import ChessPieces.ChessPiece;
import ChessPieces.Knight;
import ChessPieces.Ninja;
import ChessPieces.Pawn;
import ChessPieces.Queen;
import ChessPieces.Rook;
import ChessPieces.Wizard;
import Items.Item;
import ChessGameClasses.Board;
import Movements.Movement;
import javafx.scene.media.AudioClip;

/**
 * The Player class represents a Player who has an ArrayList of Chess Pieces,
 * a color, and money.
 */
abstract public class Player
{
	/**
	 * Bubble audio for selecting and de-selecting Chess Pieces.
	 */
	public static final AudioClip bubbleSound = new AudioClip("file:Audio/bubbleSound.wav");
	
	/**
	 * Thud audio for a Chess Piece making a move. 
	 */
	public static final AudioClip thudSound = new AudioClip("file:Audio/thudSound.wav");
	
	
	/**
	 * ArrayList of Chess Pieces that the Player owns.
	 */
	private ArrayList<ChessPiece> chessPieces;
	
	/**
	 * The team color of the Player.
	 */
	private String color;
	
	/**
	 * The amount of money that the Player has.
	 */
	private int money;
	
	/**
	 * Three parameter Constructor of the Player Class.
	 * 
	 * @param chessPieces ArrayList of Chess Pieces that the Player owns.
	 * @param color The team color of the Player.
	 * @param money The initial amount of money that the Player gets.
	 */
	public Player(ArrayList<ChessPiece> chessPieces, String color, int money)
	{
		this.setChessPieces(chessPieces);
		this.setColor(color);
		this.setMoney(money);
	}
	
	/**
	 * Get the ArrayList of Chess Pieces.
	 * 
	 * @return ArrayList of Chess Pieces that the Player owns.
	 */
	public ArrayList<ChessPiece> getChessPieces() { return chessPieces; }
	
	/**
	 * Get the team Color of the Player.
	 * 
	 * @return The team color of the Player.
	 */
	public String getColor() { return color; }
	
	/**
	 * Get the amount of money that the Player has.
	 * 
	 * @return the amount of money that the Player has.
	 */
	public int getMoney() { return money; }
	
	/**
	 * Set the ArrayList of Chess Pieces.
	 * 
	 * @param chessPieces New ArrayList of Chess Pieces.
	 */
	public void setChessPieces(ArrayList<ChessPiece> chessPieces) { this.chessPieces = chessPieces; }
	
	/**
	 * Set the color of the Player.
	 * 
	 * @param color The color of the Player.
	 */
	public void setColor(String color) { this.color = color; }
	
	/**
	 * Set the amount of money that the Player has.
	 * 
	 * @param money The amount of money that the Player has.
	 */
	public void setMoney(int money) { this.money = money; }
	
	/**
	 * Calculate the total material worth that the Player has.
	 * 
	 * @return return the current total material worth.
	 */
	public int getTotalMaterialWorth()
	{
		//Loop through all of the player's chess pieces and add up the material worth.
		int totalMaterialWorth = 0;
		for (ChessPiece chessPiece : chessPieces)
			if (!chessPiece.getIsCaptured())
				totalMaterialWorth += chessPiece.getMaterialWorth();
		return totalMaterialWorth;
	}
	
	/**
	 * Clear the movedTwoUnitsUp history of all the Pawns that the Player owns.
	 */
	public void clearMovedTwoUnitsUp()
	{
		//Set All Pawns' movedTwoUnitsUp variable to false.
		for (ChessPiece chessPiece : chessPieces)
			if (chessPiece.getName().equals("Pawn"))
			{
				Pawn pawn = (Pawn) chessPiece;
				pawn.setMovedTwoUnitsUp(false);
			}
	}
	
	/**
	 * Get text based input for Player decision from the input stream.
	 * 
	 * @param selectedChessPiece The current selected chess piece.
	 * @param board The game board. 
	 * @param items The list of items that the Player can buy.
	 * @return The user input with input row followed by input column.
	 */
	abstract public ArrayList<Integer> getInputTextBasedVersion(ChessPiece selectedChessPiece, Board board, ArrayList<Item> items);
	
	/**
	 * Find the first Pawn that is at the promotion line and return its index
	 * in the ChessPieces ArrayList
	 * 
	 * @return the index of the first Pawn that is ready to be promoted.
	 */
	public int findPawnReadyToPromote()
	{
		int promotionLine;
		if (color.equals("White")) promotionLine = 0;
		else promotionLine = Board.rowNum - 1;
		
		//If a Pawn is at the promotionLine, return true.
		for (int i = 0; i < chessPieces.size(); i++)
		{
			if (!chessPieces.get(i).getIsCaptured())
				if (chessPieces.get(i) instanceof Pawn)
					if (chessPieces.get(i).getPosRow() == promotionLine)
						return i;
		}
		return -1; //There are no Pawns that are ready to be promoted.
	}
	
	/**
	 * Get text based input for Promotion.
	 * 
	 * @return the user input with input row followed by input column.
	 */
	abstract public ArrayList<Integer> getPromotionInputTextBasedVersion();
	
	/**
	 * Promote the passed in chess piece by creating a new chess piece
	 * based on input and then copy the position of the original chess piece
	 * to the new chess piece.
	 * 
	 * @param chessPiece Holds the location of the chess piece to be promoted.
	 * @param input The selection input from 0 to 6 inclusive that determines which chess piece to promote to.
	 * @return The newly promoted chess piece with the same position as the original passed in chess piece.
	 */
	public ChessPiece promote(ChessPiece chessPiece, int input)
	{
		ChessPiece newChessPiece = null;
		
		int row = chessPiece.getPosRow();
		int col = chessPiece.getPosCol();
		String color = chessPiece.getColor();
		
		switch(input)
		{
			case 0: 
				if (getColor().equals("White")) 
					newChessPiece = new Queen(row, col, color, 'Q');
				else 
					newChessPiece = new Queen(row, col, color, 'q');
				break;
			case 1:
				if (getColor().equals("White")) 
					newChessPiece = new Bishop(row, col, color, 'B');
				else 
					newChessPiece = new Bishop(row, col, color, 'b');
				break;
			case 2: 
				if (getColor().equals("White")) 
					newChessPiece = new Knight(row, col, color, 'H');
				else 
					newChessPiece = new Knight(row, col, color, 'h');
				break;
			case 3: 
				if (getColor().equals("White")) 
					newChessPiece = new Rook(row, col, color, 'R');
				else 
					newChessPiece = new Rook(row, col, color, 'r');
				break;
			case 4: 
				if (getColor().equals("White")) 
					newChessPiece = new Cannon(row, col, color, 'C');
				else 
					newChessPiece = new Cannon(row, col, color, 'c');
				break;
			case 5: 
				if (getColor().equals("White")) 
					newChessPiece = new Ninja(row, col, color, 'N');
				else 
					newChessPiece = new Ninja(row, col, color, 'n');
				break;
			case 6: 
				if (getColor().equals("White")) 
					newChessPiece = new Wizard(row, col, color, 'W');
				else 
					newChessPiece = new Wizard(row, col, color, 'w');
				break;
		}
		return newChessPiece;
	}
	
	/**
	 * Promote the Pawn at the passed-in index using the passed-in input to determine
	 * which chess piece to promote the Pawn to. 
	 * 
	 * @param input The input that decides which chess piece to promote to.
	 * @param pawnIndex The index of the Pawn in the ArrayList chessPieces.
	 */
	public void promotePawnAtIndex(ArrayList<Integer> input, int pawnIndex)
	{
		int inputRow = input.get(0);
		int inputColumn = input.get(1);
		
		if (inputRow == -3) //Promotion
		{
			getChessPieces().set(pawnIndex, promote(getChessPieces().get(pawnIndex), inputColumn));
		}
	}
	
	/**
	 * Takes in input then run the game depending on the input and the current status of the game.
	 * It accounts for buying items, upgrading, selecting chess piece, and moving chess piece. It accounts for
	 * the failure to perform these actions. The method returns four variables that allows the ChessGameRunner to 
	 * display the status of the game.
	 * 
	 * @param input The input that decides what kind of the decision that the player is trying to make.
	 * @param moveWasMade Whether or not a move was successfully made.
	 * @param selectedChessPiece The chess piece that is selected.
	 * @param message A message that details what king of movement was made, or why wasn't the move made.
	 * @param lastPlayedItem The last item that was played.
	 * @param opponent The opponent of the Player calling this method.
	 * @param board The game board.
	 * @param items All of the items that the current player can buy.
	 * @return An ArrayList of Objects of moveWasMade, selectedChessPiece, message, and lastPlayedItem. 
	 * 		   With the purpose of giving ChessGameRunner enough information to display about the status of the game. 
	 */
	public ArrayList<Object> run(ArrayList<Integer> input, boolean moveWasMade, ChessPiece selectedChessPiece, String message, Item lastPlayedItem, Player opponent, Board board, ArrayList<Item> items)
	{
		//Index 0: moveWasMade
		//Index 1: selectedChessPiece
		//Index 2: message
		//Index 3: lastPlayedItem
		ArrayList<Object> result = new ArrayList<Object>();
			
		//FORMAT INPUT
		int inputRow = input.get(0);
		int inputColumn = input.get(1);
		
		//RUN LOGIC
		//-------------------------------------------------------------------------
		if (inputRow == -1) //BUY ITEMS
		{
			if (!board.isInCheck(color))
			{
				if(getMoney() >= items.get(inputColumn).getItemCost())
				{
					message = items.get(inputColumn).run(getColor(), board);
					lastPlayedItem = items.get(inputColumn);
					money -= items.get(inputColumn).getItemCost();
					moveWasMade = true;
				}
				else
				{
					message = "Not Enough Money.";
					moveWasMade = false;
				}
			}
			else
			{
				message = "Can't Buy Item While In Check.";
				moveWasMade = false;
			}
		}
		else if (inputRow == -2) //UPGRADES
		{
			if (!board.isInCheck(color) && selectedChessPiece != null && 
					selectedChessPiece.getColor().equals(color) &&
						selectedChessPiece.getAvailableUpgrades().size() != 0)
			{
				int price = selectedChessPiece.getAvailableUpgrades().get(inputColumn).getMovementCost() * selectedChessPiece.getMaterialWorth();
				if(getMoney() >= price)
				{
					selectedChessPiece.upgrade(inputColumn);
					message = color + " Upgraded " + selectedChessPiece + " To Have " + selectedChessPiece.getAvailableUpgrades().get(inputColumn).getMovementName() + ".";
					money -= price;
					moveWasMade = true;
				}
				else
				{
					message = "Not Enough Money.";
					moveWasMade = false;
				}
			}
			else
			{
				//Upgrade failure messages
				if (board.isInCheck(color))
					message = "Can't Upgrade While In Check.";
				else if (selectedChessPiece == null)
					message = "Select A Piece To Upgrade.";
				else if (!selectedChessPiece.getColor().equals(color))
					message = "Can't Upgrade Opponent Chess Piece";
				else if (selectedChessPiece.getAvailableUpgrades().size() == 0)
					message = "There Are No Upgrades.";
				moveWasMade = false;
			}
		}
		else //BOARD INPUT
		{		
			//Clicked On An Valid Movement
			if (selectedChessPiece != null)
			{
				if (selectedChessPiece.getColor().equals(color))
				{
					//Put input into the proper format.
					ArrayList<Integer> movementPosition = new ArrayList<Integer>();
					movementPosition.add(inputRow);
					movementPosition.add(inputColumn);
					
					//Get the movementType for displaying the correct message.
					ArrayList<ArrayList<Character>> potentialMovements = selectedChessPiece.calculatePotentialMovements(board);
					ArrayList<ArrayList<Character>> possibleMovements = selectedChessPiece.calculatePossibleMovements(potentialMovements, board);
					Character movementType = possibleMovements.get(inputRow).get(inputColumn);
					
					//Get money for capturing
					if (movementType.equals(Movement.moveAndCaptureSymbol) || movementType.equals(Movement.captureSymbol))
					{
						for (ChessPiece chessPiece : opponent.getChessPieces())
						{
							if (!chessPiece.getIsCaptured())
							{
								if (chessPiece.getPosRow() == inputRow && chessPiece.getPosCol() == inputColumn)
								{
									int captureRewardPerMaterialWorth = 100;
									money += chessPiece.getMaterialWorth() * captureRewardPerMaterialWorth;
								}
							}
						}
					}
					
					//Run the movement.
					moveWasMade = selectedChessPiece.makeMovement(board, movementPosition);
					
					//Set the correct message.
					if (movementType.equals(Movement.moveAndCaptureSymbol)) message = selectedChessPiece + " Captured.";
					else if (movementType.equals(Movement.captureSymbol)) message = selectedChessPiece + " Range Captured.";
					else if (movementType.equals(Movement.moveSymbol)) message = selectedChessPiece + " Moved.";
					else if (movementType.equals(Movement.invalidMoveSymbol)) message = "Invalid Movement.";
					else if (movementType.equals(Movement.castleSymbol)) message = "Castled.";
					else if (movementType.equals(Movement.twoUnitsUpSymbol)) message = selectedChessPiece + " Moved Two Units Up.";
					else if (movementType.equals(Movement.enPassantSymbol)) message = selectedChessPiece + " Captured With En Passant.";
				
					//Play audio
					if (!(movementType.equals(Movement.invalidMoveSymbol) || movementType.equals(Movement.currentPositionSymbol)))
					{
						if (thudSound.isPlaying())
							thudSound.stop();
						thudSound.play();
					}
				}
				else
				{
					message = "Can't Move Opponent Chess Piece.";
					moveWasMade = false;
				}
			}
				
			//Clicked On An Ally Piece
			if (!moveWasMade)
			{
				for (int i = 0; i < getChessPieces().size(); i++)
				{
					//Piece is found.
					if (getChessPieces().get(i).getPosRow() == inputRow && getChessPieces().get(i).getPosCol() == inputColumn)
					{
						//if the piece that was clicked on is the selected piece. De-select
						if (getChessPieces().get(i) == selectedChessPiece)
						{
							selectedChessPiece = null;
							message = "Deselected " + getChessPieces().get(i) + " (Ally).";
							moveWasMade = false;
						}
						else
						{
							selectedChessPiece = getChessPieces().get(i);
							message = "Selected " + selectedChessPiece + " (Ally).";
							moveWasMade = false;
						}
						
						//Play bubble sound
						if (bubbleSound.isPlaying())
							bubbleSound.stop();
						bubbleSound.play();
						break;
					}
				}
			}
				
			//Clicked On An Opponent Piece
			if (!moveWasMade)
			{
				for (int i = 0; i < opponent.getChessPieces().size(); i++)
				{
					//Piece is found.
					if (opponent.getChessPieces().get(i).getPosRow() == inputRow && opponent.getChessPieces().get(i).getPosCol() == inputColumn)
					{
						//if the piece that was clicked on is the selected piece. De-select
						if (opponent.getChessPieces().get(i) == selectedChessPiece)
						{
							selectedChessPiece = null;
							message = "Deselected " + opponent.getChessPieces().get(i) + " (Opponent).";
							moveWasMade = false;
						}
						else
						{
							selectedChessPiece = opponent.getChessPieces().get(i);
							message = "Selected " + selectedChessPiece + " (Opponent).";
							moveWasMade = false;
						}
						
						//Play bubble sound
						if (bubbleSound.isPlaying())
							bubbleSound.stop();
						bubbleSound.play();
						break;
					}
				}
			}
		}
		
		result.add(moveWasMade);
		result.add(selectedChessPiece);
		result.add(message);
		result.add(lastPlayedItem);
		return result;
	}
}
