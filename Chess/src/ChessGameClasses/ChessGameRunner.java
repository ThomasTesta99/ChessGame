package ChessGameClasses;

import Players.*;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import ChessPieces.*;
import Items.*;
import Movements.Movement;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * The ChessGameRunner handles all of the game variables, running each turn,
 * determining whether the game is over, determining the current status of the game, 
 * and displaying everything to the user through both text-based console and GUI.
 */
public class ChessGameRunner
{
	/**
	 * The White Player.
	 */
	private Player whitePlayer;
	
	/**
	 * The Black Player.
	 */
	private Player blackPlayer;
	
	/**
	 * The Game Board.
	 */
	private Board board;
	
	/**
	 * The turn by turn passive income of the Players.
	 */
	private int passiveIncome;
	
	/**
	 * The bonus passive income per material down. 
	 */
	private int passiveIncomeBonus;
	
	/**
	 * All of the crazyItems which are to be played randomly once in a while.
	 */
	private ArrayList<Item> crazyItems;
	
	/**
	 * All of the items that the Players can purchase.
	 */
	private ArrayList<Item> items;
	
	//Game Variables
	//-------------------------------------------------------------------
	/**
	 * Whether the game is over or not.
	 */
	private Boolean gameOver;
	
	/**
	 * The current turn.
	 */
	private int turn;
	
	/**
	 * The total material worth of the initial Chess Set.
	 */
	private int initialTotalMaterialWorth;
	
	/**
	 * Holds the results returned by Player's run method
	 */
	private ArrayList<Object> runResult;
	
	/**
	 * Was a move successfully made.
	 */
	private boolean moveWasMade;
	
	/**
	 * Current selected chess piece.
	 */
	private ChessPiece selectedChessPiece;
	
	/**
	 * Message result from the Player's run method.
	 */
	private String message;
	
	/**
	 * Last item that was played.
	 */
	private Item lastPlayedItem;
	
	/**
	 * True if there are pawns to be promoted.
	 */
	private boolean promotionComplete;
	
	/**
	 * The current chance of a crazy item being played.
	 */
	private int crazyItemCounter;
	
	/**
	 * The turn where a crazy item is guaranteed to be played.
	 */
	private int crazyItemGuaranteedTurn;
	
	/**
	 * The player that is to move this turn.
	 */
	Player currentPlayer;
	
	/**
	 * The opponent of the current player.
	 */
	Player currentOpponent;
	//-------------------------------------------------------------------
	
	/**
	 * Three-parameter constructor of ChessGameRunner Class. 
	 * It calls the SetUp() method to properly initialize the instance.
	 * 
	 * @param initialMoneyAmount Initial amount of money Players get.
	 * @param initialPassiveIncomeAmount The passive income amount of the Players.
	 * @param passiveIncomeBonus The passive income bonus for every material the player is down. 
	 */
	public ChessGameRunner(int initialMoneyAmount, int initialPassiveIncomeAmount, int passiveIncomeBonus)
	{
		SetUp(initialMoneyAmount, initialPassiveIncomeAmount, passiveIncomeBonus);
	}
	
	/**
	 * It properly initializes the instance of the class. This block of code is 
	 * written outside of the constructor because this block of code is necessary to be ran
	 * when playing the game again. 
	 * 
	 * @param initialMoneyAmount Initial amount of money Players get.
	 * @param initialPassiveIncomeAmount The passive income amount of the Players.
	 * @param passiveIncomeBonus The passive income bonus for every material the player is down. 
	 */
	public void SetUp(int initialMoneyAmount, int initialPassiveIncomeAmount, int passiveIncomeBonus)
	{
		this.whitePlayer = new HumanPlayer(createWhiteChessSet(), "White", initialMoneyAmount);
		this.blackPlayer = new HumanPlayer(createBlackChessSet(), "Black", initialMoneyAmount);
		
		this.board = new Board(whitePlayer.getChessPieces(), blackPlayer.getChessPieces());
		
		this.passiveIncome = initialPassiveIncomeAmount;
		this.passiveIncomeBonus = passiveIncomeBonus;
	
		createCrazyItems();
		createItems();
		
		//Game Variables
		gameOver = false;
		
		turn = 1;
		initialTotalMaterialWorth = whitePlayer.getTotalMaterialWorth();
		
		runResult = new ArrayList<Object>();
		moveWasMade = false;
		selectedChessPiece = null;
		message = "";
		lastPlayedItem = null;
		
		promotionComplete = false;
		
		//A crazy item is guaranteed to play at least once every 15 turns.
		crazyItemCounter = 0;
		crazyItemGuaranteedTurn = 15;
		
		currentPlayer = getWhitePlayer();
		currentOpponent = getBlackPlayer();
	}
	
	/**
	 * Takes input then run the turn depending on the input and the current status of the game.
	 * It accounts for who's turn it is, calling the current player's run method, accounting for promotions at
	 * the end of a player's turn, determining if the game is over, ending the current turn, preparing for the next turn, and
	 * finally, displaying the text-based version of the game to the console.
	 * 
	 * @param input The input that determines the move that the current Player is trying to make.
	 */
	public void runGame(ArrayList<Integer> input)
	{			
		//Run The Turn
		if (!isMoveWasMade() && !gameOver)
		{
			//Run Logic
			setRunResult(currentPlayer.run(input, isMoveWasMade(), getSelectedChessPiece(), getMessage(), getLastPlayedItem(), currentOpponent, getBoard(), getItems()));
		
			
			//Record the result
			setMoveWasMade((boolean) getRunResult().get(0));
			setSelectedChessPiece((ChessPiece) getRunResult().get(1));
			setMessage((String) getRunResult().get(2));
			setLastPlayedItem((Item) getRunResult().get(3));
		}
			
		//Promotion
		int pawnIndex = currentPlayer.findPawnReadyToPromote();
		setPromotionComplete((pawnIndex == -1));
		if (!isPromotionComplete() && isMoveWasMade() && !gameOver)
		{
			selectedChessPiece = currentPlayer.getChessPieces().get(pawnIndex);	
			
			//Run Logic
			currentPlayer.promotePawnAtIndex(input, pawnIndex);
			
			displayPromotionTextBasedVersion(currentPlayer, pawnIndex);
		}
		pawnIndex = currentPlayer.findPawnReadyToPromote();
		setPromotionComplete((pawnIndex == -1));
			
			
		//Run At The End Of Each Turn
		//----------------------------------------------------------------------------------------
		if (isMoveWasMade() && isPromotionComplete() && !gameOver)
		{	
//			//A crazy item is guaranteed to play at least once every 15 turns.
//			int randomNumber = (int)(Math.random() * getCrazyItemGuaranteedTurn()) + 1;
//			if (randomNumber <= getCrazyItemCounter())
//			{
//				int num = (int)(Math.random() * getCrazyItems().size());
//				setMessage("A CRAZY ITEM WAS RANDOMLY PLAYED! " + getCrazyItems().get(num).run(currentPlayer.getColor(), getBoard()));
//				setLastPlayedItem(getCrazyItems().get(num));
//			}
//			else
//				setCrazyItemCounter(getCrazyItemCounter() + 1);
//				
//			if (getCrazyItemCounter() > 15)
//				setCrazyItemCounter(0);
//				
//			//update the position board and control board
			getBoard().updatePositionBoard();
			getBoard().updateControlBoards();
		
			//Increment turn
			setTurn(getTurn() + 1);
		}
		
		//Run At The Start Of Each New Turn
		//----------------------------------------------------------------------------------------
		//White goes on Odd turn
		if (getTurn() % 2 == 1)
		{
			currentPlayer = getWhitePlayer();
			currentOpponent = getBlackPlayer();
		}
		//Black goes on Even turn
		else
		{
			currentPlayer = getBlackPlayer();
			currentOpponent = getWhitePlayer();
		}
		
		//Check whether or not it is game over
		if (board.numberOfLegalMoves(currentPlayer.getColor()) == 0)
		{
			if (board.isInCheck(currentPlayer.getColor()))
				System.out.println(currentPlayer.getColor() + " Has Been Checkmated by " + currentOpponent.getColor());
			else
				System.out.println("Stalemate");
			gameOver = true;
			
		}
		
		if (isMoveWasMade() && isPromotionComplete() && !gameOver)
		{
			//Passive income
			int bonus = (getInitialTotalMaterialWorth() - currentPlayer.getTotalMaterialWorth()) * getPassiveIncomeBonus();
			currentPlayer.setMoney(currentPlayer.getMoney() + getPassiveIncome() + bonus);
				
			currentPlayer.clearMovedTwoUnitsUp(); //Clear the En Passant history.
						
			setMoveWasMade(false);
			setSelectedChessPiece(null);
						
			setPromotionComplete(false);
		}
		//-----------------------------------------------------------------------------
		
		displayAllTextBaseVersion(currentPlayer);
	}
	
	/**
	 * Create the white chess piece set. The white chess pieces always start at the bottom of the board.
	 * 
	 * @return An ArrayList of chess pieces that holds all of the initial white chess pieces.
	 */
	public static ArrayList<ChessPiece> createWhiteChessSet()
	{
		ArrayList<ChessPiece> whiteChessPieces = new ArrayList<ChessPiece>();
		String chessPieceColor = "White";
		
		//First Row
		whiteChessPieces.add(new Pawn(7,0, chessPieceColor, 'P'));
		whiteChessPieces.add(new Pawn(7,1, chessPieceColor, 'P'));
		whiteChessPieces.add(new Pawn(7,2, chessPieceColor, 'P'));
		whiteChessPieces.add(new Pawn(7,3, chessPieceColor, 'P'));
		whiteChessPieces.add(new Pawn(7,4, chessPieceColor, 'P'));
		whiteChessPieces.add(new Pawn(7,5, chessPieceColor, 'P'));
		whiteChessPieces.add(new Pawn(7,6, chessPieceColor, 'P'));
		whiteChessPieces.add(new Pawn(7,7, chessPieceColor, 'P'));
		whiteChessPieces.add(new Pawn(7,8, chessPieceColor, 'P'));
		
		//Second Row
		whiteChessPieces.add(new Archer(8,0, chessPieceColor, 'A'));
		whiteChessPieces.add(new Cannon(8,1, chessPieceColor, 'C'));
		whiteChessPieces.add(new Bishop(8,2, chessPieceColor, 'B'));
		whiteChessPieces.add(new Ninja(8,3, chessPieceColor, 'N'));
		whiteChessPieces.add(new Archer(8,4, chessPieceColor, 'A'));
		whiteChessPieces.add(new Ninja(8,5, chessPieceColor, 'N'));
		whiteChessPieces.add(new Bishop(8,6, chessPieceColor, 'B'));
		whiteChessPieces.add(new Cannon(8,7, chessPieceColor, 'C'));
		whiteChessPieces.add(new Archer(8,8, chessPieceColor, 'A'));
		
		//Third Row
		whiteChessPieces.add(new Rook(9,0, chessPieceColor, 'R'));
		whiteChessPieces.add(new Knight(9,1, chessPieceColor, 'H'));
		whiteChessPieces.add(new Bishop(9,2, chessPieceColor, 'B'));
		whiteChessPieces.add(new Queen(9,3, chessPieceColor, 'Q'));
		whiteChessPieces.add(new King(9,4, chessPieceColor, 'K'));
		whiteChessPieces.add(new Wizard(9,5, chessPieceColor, 'W'));
		whiteChessPieces.add(new Bishop(9,6, chessPieceColor, 'B'));
		whiteChessPieces.add(new Knight(9,7, chessPieceColor, 'H'));
		whiteChessPieces.add(new Rook(9,8, chessPieceColor, 'R'));
		
		return whiteChessPieces;
	}
	
	/**
	 * Create the black chess piece set. The black chess pieces always start at the top of the board.
	 * 
	 * @return An ArrayList of chess pieces that holds all of the initial black chess pieces.
	 */
	public static ArrayList<ChessPiece> createBlackChessSet()
	{
		ArrayList<ChessPiece> blackChessPieces = new ArrayList<ChessPiece>();
		String chessPieceColor = "Black";
		
		//First Row
		blackChessPieces.add(new Pawn(2,0, chessPieceColor, 'p'));
		blackChessPieces.add(new Pawn(2,1, chessPieceColor, 'p'));
		blackChessPieces.add(new Pawn(2,2, chessPieceColor, 'p'));
		blackChessPieces.add(new Pawn(2,3, chessPieceColor, 'p'));
		blackChessPieces.add(new Pawn(2,4, chessPieceColor, 'p'));
		blackChessPieces.add(new Pawn(2,5, chessPieceColor, 'p'));
		blackChessPieces.add(new Pawn(2,6, chessPieceColor, 'p'));
		blackChessPieces.add(new Pawn(2,7, chessPieceColor, 'p'));
		blackChessPieces.add(new Pawn(2,8, chessPieceColor, 'p'));
				
		//Second Row
		blackChessPieces.add(new Archer(1,0, chessPieceColor, 'a'));
		blackChessPieces.add(new Cannon(1,1, chessPieceColor, 'c'));
		blackChessPieces.add(new Bishop(1,2, chessPieceColor, 'b'));
		blackChessPieces.add(new Ninja(1,3, chessPieceColor, 'n'));
		blackChessPieces.add(new Archer(1,4, chessPieceColor, 'a'));
		blackChessPieces.add(new Ninja(1,5, chessPieceColor, 'n'));
		blackChessPieces.add(new Bishop(1,6, chessPieceColor, 'b'));
		blackChessPieces.add(new Cannon(1,7, chessPieceColor, 'c'));
		blackChessPieces.add(new Archer(1,8, chessPieceColor, 'a'));
				
		//Third Row
		blackChessPieces.add(new Rook(0,0, chessPieceColor, 'r'));
		blackChessPieces.add(new Knight(0,1, chessPieceColor, 'h'));
		blackChessPieces.add(new Bishop(0,2, chessPieceColor, 'b'));
		blackChessPieces.add(new Queen(0,3, chessPieceColor, 'q'));
		blackChessPieces.add(new King(0,4, chessPieceColor, 'k'));
		blackChessPieces.add(new Wizard(0,5, chessPieceColor, 'w'));
		blackChessPieces.add(new Bishop(0,6, chessPieceColor, 'b'));
		blackChessPieces.add(new Knight(0,7, chessPieceColor, 'h'));
		blackChessPieces.add(new Rook(0,8, chessPieceColor, 'r'));
		
		return blackChessPieces;
	}
	
	/**
	 * Create an ArrayList that holds all of the crazy items, Scrambler, Plague, and Reset.
	 */
	public void createCrazyItems()
	{
		setCrazyItems(new ArrayList<>());
		
		getCrazyItems().add(new Scrambler());
		getCrazyItems().add(new Plague());
		getCrazyItems().add(new Reset());
	}
	
	/**
	 * Create an ArrayList that holds all of the items that the Players can buy.
	 */
	public void createItems()
	{
		setItems(new ArrayList<>());
		
		getItems().add(new Scrambler());
		getItems().add(new Plague());
		getItems().add(new Reset());
		
		getItems().add(new QueenGambitOverthrow());
		getItems().add(new Reinforcement());

		getItems().add(new InstantPromotion());
		getItems().add(new UniqueSlayer());

		getItems().add(new Swapper());
		getItems().add(new KingTeleport());	
	}
	
	/**
	 * Game information GUI. Displays turn, message, and last item played.
	 * 
	 * @return a VBox that holds labels that inform the user about game information
	 */
	public VBox generateGUIGameInformation()
	{
		VBox gameInformation = new VBox();
		gameInformation.setPrefWidth(350);
		gameInformation.setPrefHeight(300);
		gameInformation.setAlignment(Pos.TOP_LEFT);
		gameInformation.setStyle("-fx-background-color: grey;");
		
		Label turn = new Label("Turn " + getTurn() + "   (" + currentPlayer.getColor() + "'s Turn)");
		turn.setAlignment(Pos.CENTER);
		turn.setWrapText(true);
		turn.setPrefWidth(gameInformation.getPrefWidth());
		
		Label message = new Label("\nMessage:  " + getMessage());
		message.setWrapText(true);
		message.setPrefWidth(gameInformation.getPrefWidth());
		
		Label lastPlayedItem = new Label("\nLast Item That Was Played: \n" + getLastPlayedItem());
		lastPlayedItem.setWrapText(true);
		lastPlayedItem.setPrefWidth(gameInformation.getPrefWidth());
		
		gameInformation.getChildren().addAll(turn, message, lastPlayedItem);
		
		return gameInformation;
	}
	
	/**
	 * Game over message GUI. Displayed when the game is over and informs the 
	 * user about how the game has ended.
	 * 
	 * @return a VBox that holds labels that inform the user about the end of the game.
	 */
	public VBox generateGUIGameOverMessage()
	{
		VBox gameOverMessage = new VBox();
		gameOverMessage.setPrefWidth(350);
		gameOverMessage.setPrefHeight(300);
		gameOverMessage.setAlignment(Pos.TOP_LEFT);
		gameOverMessage.setStyle("-fx-background-color: grey;");
		
		Label gameOverLabel = new Label("Game Over");
		gameOverLabel.setAlignment(Pos.CENTER);
		gameOverLabel.setWrapText(true);
		gameOverLabel.setPrefWidth(gameOverMessage.getPrefWidth());
		
		Label result = new Label();
		result.setAlignment(Pos.CENTER);
		result.setWrapText(true);
		result.setPrefWidth(gameOverMessage.getPrefWidth());
		
		if (board.isInCheck(currentPlayer.getColor()))
			result.setText(currentPlayer.getColor() + " Has Been Checkmated by " + currentOpponent.getColor());
		else
			result.setText("\nStalemate: " + currentPlayer + " Has No Legal Moves But Is Not In Check");
		
		gameOverMessage.getChildren().addAll(gameOverLabel, result);
		return gameOverMessage;
	}
	
	/**
	 * GUI that displays information about the current selected chess piece, such as
	 * what kind of chess piece it is, its' material worth, and the movements that it has.
	 * 
	 * @return a VBox that holds Labels and a ListView that inform the user about the selected chess piece.
	 */
	public VBox generateGUISelectedChessPieceInformation()
	{
		VBox selectedChessPieceInformation = new VBox(10);
		selectedChessPieceInformation.setPrefWidth(350);
		selectedChessPieceInformation.setAlignment(Pos.TOP_LEFT);
		selectedChessPieceInformation.setStyle("-fx-background-color: lightgrey;");
		
		if (selectedChessPiece != null)
		{
			Label info = new Label("Selected Chess Piece: " + selectedChessPiece);
			info.setWrapText(true);
			info.setPrefWidth(selectedChessPieceInformation.getPrefWidth());
			
			Label materialWorth = new Label("Material Worth: " + selectedChessPiece.getMaterialWorth());
			materialWorth.setWrapText(true);
			materialWorth.setPrefWidth(selectedChessPieceInformation.getPrefWidth());
			
			Label movement = new Label("Movements");
			movement.setWrapText(true);
			movement.setPrefWidth(selectedChessPieceInformation.getPrefWidth());
			
			ListView<String> movementsListView = new ListView<String>();
			movementsListView.setPrefHeight(200);
			for (int i = 0; i < selectedChessPiece.getMovements().size(); i++)
			{
				movementsListView.getItems().addAll((i + 1) + ") " + selectedChessPiece.getMovements().get(i).getMovementName());
			}
			
			selectedChessPieceInformation.getChildren().addAll(info, materialWorth, movement, movementsListView);
		}
		return selectedChessPieceInformation;
	}
	
	/**
	 * GUI that displays information about the Black Player.
	 * 
	 * @return A HBox that holds the color of the player, total material worth, and the amount of money the Player has.
	 */
	public HBox generateGUIBlackPlayerInformation()
	{
		HBox playerInformation = new HBox();
		playerInformation.setAlignment(Pos.CENTER);
		playerInformation.setStyle("-fx-background-color: black;");
		
		Label playerInfo = new Label("Black Player:   Material Worth: " + getBlackPlayer().getTotalMaterialWorth() + "   Money: $" + getBlackPlayer().getMoney());
		playerInfo.setWrapText(true);
		playerInfo.setPrefWidth(playerInformation.getPrefWidth());
		
		playerInformation.getChildren().addAll(playerInfo);
		
		return playerInformation;
	}
	
	/**
	 * GUI that displays information about the White Player.
	 * 
	 * @return A HBox that holds the color of the player, total material worth, and the amount of money the Player has.
	 */
	public HBox generateGUIWhitePlayerInformation()
	{
		HBox playerInformation = new HBox();
		playerInformation.setAlignment(Pos.CENTER);
		playerInformation.setStyle("-fx-background-color: black;");
		
		Label playerInfo = new Label("White Player:   Material Worth: " + getWhitePlayer().getTotalMaterialWorth() + "   Money: $" + getWhitePlayer().getMoney());
		playerInfo.setWrapText(true);
		playerInfo.setPrefWidth(playerInformation.getPrefWidth());
		
		playerInformation.getChildren().addAll(playerInfo);
		
		return playerInformation;
	}
	
	/**
	 * GUI that displays the available upgrades that the current selected chess piece has.
	 * 
	 * @return
	 */
	public VBox generateGUIUpgrades()
	{
		Label listName = new Label();
		
		ListView<String> upgradesListView = new ListView<String>();
		upgradesListView.setPrefHeight(300);
		
		VBox upgradesGUI = new VBox(10);
		
		if (selectedChessPiece != null)
		{	
			ArrayList<Movement> availableUpgrades = getSelectedChessPiece().getAvailableUpgrades();
			int upgradeSize = availableUpgrades.size();
			if(upgradeSize == 0)
			{
				listName.setText(getSelectedChessPiece().getName() + " Upgrade List: (No Upgrades)");
			}
			else
			{
				listName.setText(getSelectedChessPiece().getName() + " Upgrade List: ");
				for(int i = 0; i < upgradeSize; i++)
				{
					String listViewElement = (i + 1) + ") ";
					
					int price = availableUpgrades.get(i).getMovementCost() * getSelectedChessPiece().getMaterialWorth();
					listViewElement += availableUpgrades.get(i).getMovementName() + " - $" + price;
					
					upgradesListView.getItems().addAll(listViewElement);
				}
			}
		}
		else
		{
			listName.setText("Upgrade List: ");
		}
		
		upgradesGUI.getChildren().addAll(listName, upgradesListView);
		return upgradesGUI;
	}
	
	/**
	 * GUI that displays all of the items that the Players can buy.
	 * 
	 * @return A VBox that holds labels and a ListView that displays all of the items that the Players can buy.
	 */
	public VBox generateGUIItems()
	{
		Label listName = new Label();
		
		ListView<String> itemsListView = new ListView<String>();
		itemsListView.setPrefHeight(300);
		
		VBox itemsGUI = new VBox(10);
		
		listName.setText("Items");
		
		for(int i = 0; i < getItems().size(); i++)
		{
			String listViewElement = (i + 1) + ") " + getItems().get(i).getItemName() + " - $" + getItems().get(i).getItemCost();
			itemsListView.getItems().addAll(listViewElement);
		}
		
		itemsGUI.getChildren().addAll(listName, itemsListView);
		return itemsGUI;
	}
	
	/**
	 * GUI that displays all of the potential promotions that the Players can promote their Pawns to.
	 * 
	 * @return VBox that states which Pawn the Player is promoting and what the Player can promote to.
	 */
	public VBox generateGUIPromotion()
	{
		int pawnIndex = currentPlayer.findPawnReadyToPromote();
		ChessPiece pawn = currentPlayer.getChessPieces().get(pawnIndex);
		
		Label listName = new Label();
		listName.setText("Promote " + pawn);
		
		ListView<String> promotionListView = new ListView<String>();
		promotionListView.setPrefHeight(300);
		
		VBox promotionGUI = new VBox(10);
		
		//Display Promotion
		promotionListView.getItems().addAll("1) Queen");
		promotionListView.getItems().addAll("2) Bishop");
		promotionListView.getItems().addAll("3) Knight");
		promotionListView.getItems().addAll("4) Rook");
		promotionListView.getItems().addAll("5) Cannon");
		promotionListView.getItems().addAll("6) Ninja");
		promotionListView.getItems().addAll("7) Wizard");
		
		promotionGUI.getChildren().addAll(listName, promotionListView);
		return promotionGUI;
	}
	
	/**
	 * Display the text-based version of the Game Information.
	 * 
	 * @param currentPlayer The current player who is to go this turn.
	 */
	private void displayGameInformationTextBasedVersion(Player currentPlayer)
	{
		System.out.print("\tTurn " + getTurn());
		System.out.println("\t(" + currentPlayer.getColor() + "'s Turn)");
		PrintBorder();
			
		//Print in check
		System.out.println(currentPlayer.getColor() + " Is In CHECK?: " + getBoard().isInCheck(currentPlayer.getColor()));
		PrintBorder();
			
		//Print Message
		System.out.println("Message: " + getMessage());
		PrintBorder();
			
		System.out.println("Last Item That Was Played: \n" + getLastPlayedItem());
	}
	
	/**
	 * Display the text-based version of the player information.
	 */
	private void displayPlayerInformationTextBasedVersion()
	{
		System.out.println("White Player:\tMaterial Worth: " + getWhitePlayer().getTotalMaterialWorth() + "\tMoney: $" + getWhitePlayer().getMoney());
		System.out.println("Black Player:\tMaterial Worth: " + getBlackPlayer().getTotalMaterialWorth() + "\tMoney: $" + getBlackPlayer().getMoney());
	}
	
	/**
	 * Display the text-basd version of the Board, movement board, upgrade list, and item list.
	 * 
	 * @param currentPlayer The current player who is to go this turn.
	 */
	private void displayBoardMovementUpgradesItemTextBasedVersion(Player currentPlayer)
	{
		//1) Display the display board
		Board.displayBoard(getBoard().getDisplayBoard());
		PrintBorder();
			
		if (getSelectedChessPiece() == null)
		{
			System.out.println("Selected Chess Piece: " + null);
			PrintBorder();
			
			//2) Display the movement board of the piece.
			System.out.println("***Possible Movements This Piece Can Make");
			System.out.println("Empty Board");
			PrintBorder();
				
			//3) Upgrades
			System.out.print("***(-2) ");
			System.out.println("Upgrade List");
			System.out.println("Empty List");
			PrintBorder();
		}
		else //A chess piece is selected
		{
			System.out.print("Selected Chess Piece: " + getSelectedChessPiece());
			if (getSelectedChessPiece().getColor().equals(currentPlayer.getColor())) System.out.println(" (Ally).");
			else System.out.println(" (Opponent).");
			PrintBorder();
				
			//2) Display the movement board of the piece.
			System.out.println("***Possible Movements This Piece Can Make.");
			ArrayList<ArrayList<Character>> potentialMovements = getSelectedChessPiece().calculatePotentialMovements(getBoard());
			ArrayList<ArrayList<Character>> possibleMovements = getSelectedChessPiece().calculatePossibleMovements(potentialMovements, getBoard());
			Board.displayBoard(possibleMovements);
			PrintBorder();
				
			//3) Upgrades
			System.out.println("***(-2) " + getSelectedChessPiece().getName() + " Upgrade List: ");
			ArrayList<Movement> availableUpgrades = getSelectedChessPiece().getAvailableUpgrades();
			int upgradeSize = availableUpgrades.size();
			if(upgradeSize == 0)
			{
				System.out.println("No Upgrades.");
			}
			else
			{
				for(int i = 0; i < upgradeSize; i++)
				{
					System.out.print(i + ") ");
					System.out.println(availableUpgrades.get(i).getMovementName() + " - $" + availableUpgrades.get(i).getMovementCost() * getSelectedChessPiece().getMaterialWorth());
				}
			}
			PrintBorder();
		}
			
		//4) Items
		System.out.println("***(-1) Items");
		for(int i = 0; i < getItems().size(); i++)
		{
			System.out.println(i + ") " + getItems().get(i).getItemName() + " - $" + getItems().get(i).getItemCost());
		}
	}
	
	/**
	 * Call all of the methods that display the text-based version of the game
	 * 
	 * @param currentPlayer The current player who is to go this turn.
	 */
	public void displayAllTextBaseVersion(Player currentPlayer)
	{
		ClearConsole();
		PrintBorder();
		
		//Game Info
		displayGameInformationTextBasedVersion(currentPlayer);
		PrintBorder();
		
		//Display player information
		displayPlayerInformationTextBasedVersion();
		PrintBorder();
		
		//Display Board, movement board, upgrade list, and item list.
		displayBoardMovementUpgradesItemTextBasedVersion(currentPlayer);
		PrintBorder();
	}
	
	/**
	 * Call all of the methods that display the text-based version of the game for promotion.
	 * 
	 * @param currentPlayer The current player who is to go this turn.
	 * @param pawnIndex The index of the Pawn that is to be promoted.
	 */
	private void displayPromotionTextBasedVersion(Player currentPlayer, int pawnIndex)
	{
		ClearConsole();
		PrintBorder();
		
		//Game Info
		displayGameInformationTextBasedVersion(currentPlayer);
		PrintBorder();
		
		//Display player information
		displayPlayerInformationTextBasedVersion();
		PrintBorder();
		
		//Display Board
		Board.displayBoard(getBoard().getDisplayBoard());
		PrintBorder();
		
		ChessPiece pawn = currentPlayer.getChessPieces().get(pawnIndex);
		
		//Display Promotion
		System.out.println("Choose a piece to promote the Pawn At (" + pawn.getPosRow() + ", " + pawn.getPosCol() + ")");
		System.out.println("0) Queen");
		System.out.println("1) Bishop");
		System.out.println("2) Knight");
		System.out.println("3) Rook");
		System.out.println("4) Cannon");
		System.out.println("5) Ninja");
		System.out.println("6) Wizard");
	}
	
	/**
	 * Print a border for text-based version display.
	 */
	public static void PrintBorder()
	{
		System.out.println("----------------------------------------------------");
	}
	
	/**
	 * Clear the console for text-based version display.
	 */
	public static void ClearConsole()
	{
		for (int i = 0; i < 50; i++)
			System.out.println();
	}

	/**
	 * Get the White Player
	 * 
	 * @return the White Player
	 */
	public Player getWhitePlayer() { return whitePlayer; }
	
	/**
	 * Set the White Player
	 * 
	 * @param whitePlayer the new White Player
	 */
	public void setWhitePlayer(Player whitePlayer) { this.whitePlayer = whitePlayer; }
	
	/**
	 * Get the Black Player
	 * 
	 * @return the Black Player
	 */
	public Player getBlackPlayer() { return blackPlayer; }
	
	/**
	 * Set the Black Player
	 * 
	 * @param blackPlayer the new Black Player
	 */
	public void setBlackPlayer(Player blackPlayer) { this.blackPlayer = blackPlayer; }
	
	/**
	 * Get the board
	 * 
	 * @return the board
	 */
	public Board getBoard() { return board; }
	
	/**
	 * Set the board
	 * 
	 * @param board The new board
	 */
	public void setBoard(Board board) { this.board = board; }
	
	/**
	 * Get the Passive Income
	 * 
	 * @return the Passive Income
	 */
	public int getPassiveIncome() { return passiveIncome; }
	
	/**
	 * Set the Passive Income
	 * 
	 * @param passiveIncome The new passive income
	 */
	public void setPassiveIncome(int passiveIncome) { this.passiveIncome = passiveIncome; }
	
	/**
	 * Get the Passive Income Bonus
	 *  
	 * @return the Passive Income Bonus
	 */
	public int getPassiveIncomeBonus() { return passiveIncomeBonus; }
	
	/**
	 * Set the passive income bonus 
	 * @param passiveIncomeBonus the new passive income bonus 
	 */
	public void setPassiveIncomeBonus(int passiveIncomeBonus) { this.passiveIncomeBonus = passiveIncomeBonus; }
	
	/**
	 * Get the Game Over variable
	 * 
	 * @return whether or not it is Game Over
	 */
	public Boolean getGameOver() { return gameOver; }
	
	/**
	 * Set GameOver
	 * 
	 * @param gameOver The new gameOver variable
	 */
	public void setGameOver(Boolean gameOver) { this.gameOver = gameOver; }
	
	/**
	 * Get the ArrayList of Crazy Items
	 * 
	 * @return The ArrayList of Crazy Items
	 */
	public ArrayList<Item> getCrazyItems() { return crazyItems; }
	
	/**
	 * Set the ArrayList of Crazy Items
	 * 
	 * @param crazyItems The new ArrayList of Crazy Items
	 */
	public void setCrazyItems(ArrayList<Item> crazyItems) { this.crazyItems = crazyItems; }
	
	/**
	 * Get the ArrayList of Items
	 * 
	 * @return The ArrayList of Items
	 */
	public ArrayList<Item> getItems() { return items; }
	
	/**
	 * Set the ArrayList of Items
	 * 
	 * @param items the new ArrayList of Items
	 */
	public void setItems(ArrayList<Item> items) { this.items = items; }
	
	/**
	 * Get the current Turn
	 * 
	 * @return the current Turn
	 */
	public int getTurn() { return turn; }
	
	/**
	 * Set the current turn
	 * 
	 * @param turn the new current turn
	 */
	public void setTurn(int turn) { this.turn = turn; }
	
	/**
	 * Get the initial total material worth
	 * 
	 * @return the initial total material worth
	 */
	public int getInitialTotalMaterialWorth() { return initialTotalMaterialWorth; }
	
	/**
	 * Set the initial total material worth
	 * 
	 * @param initialTotalMaterialWorth the new initial total material worth
	 */
	public void setInitialTotalMaterialWorth(int initialTotalMaterialWorth) { this.initialTotalMaterialWorth = initialTotalMaterialWorth; }
	
	/**
	 * Get the run result
	 * 
	 * @return the run result
	 */
	public ArrayList<Object> getRunResult() { return runResult; }
	
	/**
	 * Get the run result
	 * 
	 * @param runResult the new run result
	 */
	public void setRunResult(ArrayList<Object> runResult) { this.runResult = runResult; }
	
	/**
	 * Get wasMoveMade
	 * 
	 * @return Get wasMoveMade
	 */
	public boolean isMoveWasMade() { return moveWasMade; }
	
	/**
	 * Set wasMoveMade
	 * 
	 * @param moveWasMade the new wasMoveMade
	 */
	public void setMoveWasMade(boolean moveWasMade) { this.moveWasMade = moveWasMade; }
	
	/**
	 * Get the selected chess piece
	 * 
	 * @return get the selected chess piece
	 */
	public ChessPiece getSelectedChessPiece() { return selectedChessPiece; }
	
	/**
	 * Set the selectedChessPiece
	 * 
	 * @param selectedChessPiece the new selected chess piece
	 */
	public void setSelectedChessPiece(ChessPiece selectedChessPiece) { this.selectedChessPiece = selectedChessPiece; }
	
	/**
	 * Get the message result
	 * 
	 * @return the message result
	 */
	public String getMessage() { return message; }
	
	/**
	 * Set the message 
	 * @param message the new message
	 */
	public void setMessage(String message) { this.message = message; }
	
	/**
	 * Get the last item played
	 * 
	 * @return Get the last item played
	 */
	public Item getLastPlayedItem() { return lastPlayedItem; }
	
	/**
	 * Set the last played item
	 * @param lastPlayedItem the new last played item
	 */
	public void setLastPlayedItem(Item lastPlayedItem) { this.lastPlayedItem = lastPlayedItem; }
	
	/**
	 * Get is promotion complete
	 * 
	 * @return get whether or not promotion is complete
	 */
	public boolean isPromotionComplete() { return promotionComplete; }
	
	/**
	 * Set the if the promotion is complete
	 * 
	 * @param promotionComplete get promotion is complete
	 */
	public void setPromotionComplete(boolean promotionComplete) { this.promotionComplete = promotionComplete; }
	
	/**
	 * Get crazy item counter
	 * 
	 * @return crazy item counter
	 */
	public int getCrazyItemCounter() { return crazyItemCounter; }
	
	/**
	 * Set the crazy item counter
	 * 
	 * @param crazyItemCounter the new crazy item counter
	 */
	public void setCrazyItemCounter(int crazyItemCounter) { this.crazyItemCounter = crazyItemCounter; }
	
	/**
	 * Get crazy item guaranteed turn. 
	 * @return crazy item guaranteed turn. 
	 */
	public int getCrazyItemGuaranteedTurn() { return crazyItemGuaranteedTurn; }
	
	/**
	 * Set the crazy item guaranteed turn
	 * 
	 * @param crazyItemGuaranteedTurn the new crazy item guaranteed turn
	 */
	public void setCrazyItemGuaranteedTurn(int crazyItemGuaranteedTurn) { this.crazyItemGuaranteedTurn = crazyItemGuaranteedTurn; }
	
	/**
	 * Get current player
	 * 
	 * @return current player
	 */
	public Player getCurrentPlayer() { return currentPlayer; }
	
	/**
	 * Get current opponent
	 * 
	 * @return current opponent
	 */
	public Player getCurrentOpponent() { return currentOpponent; }
	
	/**
	 * Save the game by converting all of the ChessGameRunner member fields to bytes and storing them in
	 * chess.dat
	 */
	public void saveGame()
	{
		try 
		{
			ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream("chess.dat"));
			output.writeObject(board);
			output.writeInt(whitePlayer.getMoney());
			output.writeInt(blackPlayer.getMoney());
			output.writeInt(passiveIncome);
			output.writeInt(passiveIncomeBonus);
			output.writeBoolean(gameOver);
			output.writeInt(turn);
			
			output.writeInt(initialTotalMaterialWorth);
			output.writeBoolean(moveWasMade);
			output.writeObject(message);
			output.writeBoolean(promotionComplete);
			output.writeInt(crazyItemCounter);
			output.writeInt(crazyItemGuaranteedTurn);
			
			output.close();
		} 
		catch (Exception e) 
		{
			System.out.println("Error writing board to file.");
			e.printStackTrace();
		}
	}

	/**
	 * Load the saved game from chess.dat
	 */
	public void loadGame()
	{
		try
		{
			ObjectInputStream input = new ObjectInputStream(new FileInputStream("chess.dat"));
			board.deepCopy((Board) input.readObject());
			whitePlayer.setMoney(input.readInt());
			blackPlayer.setMoney(input.readInt());
			passiveIncome = input.readInt();
			passiveIncomeBonus = input.readInt();
			gameOver = input.readBoolean();
			turn = input.readInt();
			
			initialTotalMaterialWorth = input.readInt();
			moveWasMade = input.readBoolean();
			message = (String) input.readObject();
			promotionComplete = input.readBoolean();
			crazyItemCounter = input.readInt();
			crazyItemGuaranteedTurn = input.readInt(); 
			
			selectedChessPiece = null;
			lastPlayedItem = null;
			
			//White goes on Odd turn
			if (getTurn() % 2 == 1)
			{
				currentPlayer = getWhitePlayer();
				currentOpponent = getBlackPlayer();
			}
			//Black goes on Even turn
			else
			{
				currentPlayer = getBlackPlayer();
				currentOpponent = getWhitePlayer();
			}
		}
		catch(Exception e)
		{
			System.out.println("I broke");
			e.printStackTrace();
		}
	}
	
}
