package application;
	
import ChessPieces.*;

import java.util.ArrayList;
import java.util.List;

import ChessGameClasses.Board;
import ChessGameClasses.ChessGameRunner;
import ChessPieces.ChessPiece;
import ChessPieces.King;
import Movements.Movement;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;

/**
 * Handles the GUI display and setting up the ChessGameRunner to be ran.
 */
public class Main extends Application 
{	
	//Game Variables
	//---------------------------------------------------------------------------
	/**
	 * Initial amount of money Players get.
	 */
	int initialMoneyAmount = 1000;
	
	/**
	 * The passive income amount of the Players.
	 */
	int initialPassiveIncomeAmount = 300;
	
	/**
	 * The passive income bonus for every material the player is down. 
	 */
	int passiveIncomeBonus = 50;
	//---------------------------------------------------------------------------
	
	/**
	 * The chess game 
	 */
	ChessGameRunner chessGameRunner;
	
	/**
	 * The ImageView of every single Chess Piece
	 */
	ArrayList<Image> chessPieceImages;

	/**
	 * Main Layout of the GUI
	 */
	BorderPane layout;
	
	//Menu Variables
	//---------------------------------------------------------------------------
	/**
	 * Menu Bar
	 */
	MenuBar menuBar;
	
	/**
	 * File Menu
	 */
	Menu fileMenu;
	
	/**
	 * Save Game
	 */
	MenuItem saveGame;
	
	/**
	 * Open Game
	 */
	MenuItem open;
	//---------------------------------------------------------------------------
	
	
	/**
	 * The left side of the BorderPane layout
	 */
	VBox layoutLeft;
	
	
	//The center of the BorderPane layout
	//---------------------------------------------------------------------------
	/**
	 * The center of the BorderPane layout
	 */
	BorderPane layoutCenter;
	
	/**
	 * Black player information
	 */
	HBox blackPlayerInformation;
	
	/**
	 * White player information
	 */
	HBox whitePlayerInformation;
	
	/**
	 * The chess board
	 */
	GridPane chessBoard;
	//---------------------------------------------------------------------------
	
	/**
	 * The right side of the BorderPane layout
	 */
	VBox layoutRight;
	
	/**
	 * The main launches the GUI
	 * 
	 * @param args arguments
	 */
	public static void main(String[] args) 
	{
		launch(args);
	}
	
	@Override
	/**
	 * Initialize the chessGameRunner and the GUI display. 
	 */
	public void start(Stage primaryStage) 
	{
		chessGameRunner = new ChessGameRunner(initialMoneyAmount, initialPassiveIncomeAmount, passiveIncomeBonus);
		chessGameRunner.displayAllTextBaseVersion(chessGameRunner.getWhitePlayer());
		
		try 
		{	
			//Dimensions
			int rowNum = Board.rowNum;
			int colNum = Board.colNum;
			
			//Format
			int tileSize = 60;
			int padding = 10;
			int gap = 1;
			
			chessPieceImages = generateChessPieceImageViews();
			
			layout = new BorderPane();
			
			//Has black player info on top, chessBoard in the center, white player info on the botton.
			layoutCenter = new BorderPane();
			
			//The board with rectangle as buttons
			chessBoard = createChessBoard(rowNum, colNum, tileSize, chessGameRunner);
			chessBoard.setAlignment(Pos.CENTER); 
			//chessBoard.setPadding(new Insets(padding, padding, padding, padding));
			//chessBoard.setHgap(gap);
			//chessBoard.setVgap(gap);
			chessBoard.setStyle("-fx-background-color: black;");
			layoutCenter.setCenter(chessBoard);
			layout.setCenter(layoutCenter);
			
			//The Menu At The Top
			createMenu(tileSize);
			layout.setTop(menuBar);
			layout.getTop().setStyle("-fx-background-color: rgb(64,64,64)");
			
			//Initial Display
			updateGui(tileSize, chessBoard, chessGameRunner);
			
			Scene scene = new Scene(layout, 1300, 700, Color.BLACK);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			
			//Icon
			Image icon = new Image("file:Images/chessIcon.jpg");
			primaryStage.getIcons().add(icon);
			
			primaryStage.setResizable(true);
			primaryStage.setTitle("Chess Board");
			primaryStage.setScene(scene);
			primaryStage.show();
			
		} 
		catch(Exception e) 
		{
			e.printStackTrace();
		}
		
	}
	
	/**
	 * Generate the images for each type of chess piece for each color
	 * 
	 * @return An ArrayList of Images for each type of chess piece for each color
	 */
	ArrayList<Image> generateChessPieceImageViews()
	{
		Image bArcher=new Image("file:Images/ChessPieceImages/chess-archer-black.png");
		Image wArcher=new Image("file:Images/ChessPieceImages/chess-archer-white.png");
		Image bBishop = new Image("file:Images/ChessPieceImages/chess-bishop-black.png");
		Image wBishop = new Image("file:Images/ChessPieceImages/chess-bishop-white.png");
		Image bCannon=new Image("file:Images/ChessPieceImages/chess-cannon-black.png");
		Image wCannon=new Image("file:Images/ChessPieceImages/chess-cannon-white.png");
		Image bKing = new Image("file:Images/ChessPieceImages/chess-king-black.png");
		Image wKing = new Image("file:Images/ChessPieceImages/chess-king-white.png");
		Image bKnight = new Image("file:Images/ChessPieceImages/chess-knight-black.png");
		Image wKnight = new Image("file:Images/ChessPieceImages/chess-knight-white.png");
		Image bNinja=new Image("file:Images/ChessPieceImages/chess-ninja-black.png");
		Image wNinja=new Image("file:Images/ChessPieceImages/chess-ninja-white.png");
		Image bPawn=new Image("file:Images/ChessPieceImages/chess-pawn-black.png");
		Image wPawn=new Image("file:Images/ChessPieceImages/chess-pawn-white.png");
		Image bQueen = new Image("file:Images/ChessPieceImages/chess-queen-black.png");
		Image wQueen = new Image("file:Images/ChessPieceImages/chess-queen-white.png");
		Image bRook = new Image("file:Images/ChessPieceImages/chess-rook-black.png");
		Image wRook = new Image("file:Images/ChessPieceImages/chess-rook-white.png");
		Image bWizard=new Image("file:Images/ChessPieceImages/chess-wizard-black.png");
		Image wWizard=new Image("file:Images/ChessPieceImages/chess-wizard-white.png");
		ArrayList<Image> chessPieceImage = new ArrayList<>(List.of(bArcher, wArcher, 
				bBishop, wBishop, bCannon, wCannon, bKing, wKing, bKnight, wKnight, bNinja, wNinja,
				bPawn, wPawn, bQueen, wQueen, bRook, wRook, bWizard, wWizard));
		
		return chessPieceImage;
	}
	
	/**
	 * Get the corresponding Image of the passed-in chess piece
	 * 
	 * @param chessPiece the chess piece to get the image for.
	 * @return the corresponding image of the passed-in chess piece.
	 */
	public Image getImage(ChessPiece chessPiece)
	{
		if (chessPiece instanceof Archer)
			if (chessPiece.getColor().equals("Black")) return chessPieceImages.get(0);
			else return chessPieceImages.get(1);
		else if (chessPiece instanceof Bishop)
			if (chessPiece.getColor().equals("Black")) return chessPieceImages.get(2);
			else return chessPieceImages.get(3);
		else if (chessPiece instanceof Cannon)
			if (chessPiece.getColor().equals("Black")) return chessPieceImages.get(4);
			else return chessPieceImages.get(5);
		else if (chessPiece instanceof King)
			if (chessPiece.getColor().equals("Black")) return chessPieceImages.get(6);
			else return chessPieceImages.get(7);
		else if (chessPiece instanceof Knight)
			if (chessPiece.getColor().equals("Black")) return chessPieceImages.get(8);
			else return chessPieceImages.get(9);
		else if (chessPiece instanceof Ninja)
			if (chessPiece.getColor().equals("Black")) return chessPieceImages.get(10);
			else return chessPieceImages.get(11);
		else if (chessPiece instanceof Pawn)
			if (chessPiece.getColor().equals("Black")) return chessPieceImages.get(12);
			else return chessPieceImages.get(13);
		else if (chessPiece instanceof Queen)
			if (chessPiece.getColor().equals("Black")) return chessPieceImages.get(14);
			else return chessPieceImages.get(15);
		else if (chessPiece instanceof Rook)
			if (chessPiece.getColor().equals("Black")) return chessPieceImages.get(16);
			else return chessPieceImages.get(17);
		else //if (chessPiece instanceof Wizard)
			if (chessPiece.getColor().equals("Black")) return chessPieceImages.get(18);
			else return chessPieceImages.get(19);
	}
	
	/**
	 * Create a set of rectangles for each tile of the board. The rectangles are to be used as buttons
	 * and to return the input of the corresponding row and column.
	 * 
	 * @param numRows Number of rows of the board
	 * @param numCols Number of columns of the board
	 * @param tileSize The tile size
	 * @param chessGameRunner The chess game
	 * @return a GridPane of rectangles as the buttons for each tile of the board.
	 */
	public GridPane createChessBoard(int numRows, int numCols, int tileSize, ChessGameRunner chessGameRunner)
	{
		GridPane chessBoard = new GridPane();
		
		int arc = 5;
		
		int strokeWidth = 2;
		Color strokeColor = Color.BLACK;
		
		Color selectedStrokeColor = Color.GOLD;
		
		for (int row = 0; row < numRows; row++)
		{
			for (int column = 0; column < numCols; column++)
			{
				Rectangle rectangle = new Rectangle(row, column, tileSize, tileSize);
				rectangle.setArcWidth(arc);
				rectangle.setArcHeight(arc);
				
				rectangle.setStrokeWidth(strokeWidth);
				rectangle.setStroke(strokeColor);
				
				rectangle.setOnMouseEntered(e -> 
				{
					rectangle.setStrokeWidth(strokeWidth);
					rectangle.setStroke(selectedStrokeColor);
				});
				
				rectangle.setOnMouseExited(e -> 
				{
					rectangle.setStrokeWidth(strokeWidth);
					rectangle.setStroke(strokeColor);
				});
				
				rectangle.setOnMouseClicked(e -> 
				{		
					ArrayList<Integer> userInput = new ArrayList<Integer>();
					userInput.add((int)rectangle.getX());
					userInput.add((int)rectangle.getY());
					chessGameRunner.runGame(userInput);
					updateGui(tileSize, chessBoard, chessGameRunner);
				});
				
				chessBoard.add(rectangle, column, row);
			}
		}
		
		return chessBoard;
	}
	
	
	/**
	 * Draw the default chess board color
	 * 
	 * @param chessBoard The GUI chess board to edit
	 * @param numRows Number of rows of the board
	 * @param numCols Number of columns of the board
	 * @param firstBoardColor the first color
	 * @param secondBoardColor the second color
	 */
	public void setChessBoardColor(GridPane chessBoard, int numRows, int numCols, Color firstBoardColor, Color secondBoardColor)
	{
		Color color;
		int index = 0;
		for (int row = 0; row < numRows; row++)
		{
			if (row % 2 == 0) color = firstBoardColor;
			else color = secondBoardColor;
			
			for (int column = 0; column < numCols; column++)
			{
				if (chessBoard.getChildren().get(index) instanceof Rectangle)
				{
					Rectangle rectangle = (Rectangle) chessBoard.getChildren().get(index);
					rectangle.setFill(color);
				}
				if (color.equals(firstBoardColor)) color = secondBoardColor;
				else color = firstBoardColor;
				index++;
			}
		}
	}
	
	/**
	 * If the King is in check, color the tile of the King in red.
	 * 
	 * @param chessBoard The GUI chess board to edit
	 * @param numRows the number of rows of the board
	 * @param numCols the number of columns of the board
	 * @param chessGameRunner the chess game 
	 */
	public void setKingInCheckColor(GridPane chessBoard, int numRows, int numCols, ChessGameRunner chessGameRunner)
	{
		if (chessGameRunner.getBoard().isInCheck(chessGameRunner.getCurrentPlayer().getColor()))
		{
			Color kingInCheckColor = Color.DARKRED;
			//For marking the King tile as red if king is in check
			
			//Find the king
			int kingRow = -1;
			int kingCol = -1;
	
			for (ChessPiece potentialKing : chessGameRunner.getCurrentPlayer().getChessPieces())
			{
				if (!potentialKing.getIsCaptured())
					if (potentialKing instanceof King)
					{
						kingRow = potentialKing.getPosRow();
						kingCol = potentialKing.getPosCol();
					}
			}
			
			//Mark the rectangle that the king is on with the DarkRed color
			int index = 0;
			for (int row = 0; row < numRows; row++)
			{
				for (int column = 0; column < numCols; column++)
				{
					if (kingRow == row && kingCol == column)
					{
						Rectangle rectangle = (Rectangle) chessBoard.getChildren().get(index);
						Paint fill = rectangle.getFill();
						Color recColor = (Color) fill;
						double mixRatio = 0.85;
						rectangle.setFill(recColor.interpolate(kingInCheckColor, mixRatio));
					}
					index++;
				}
			}
		}
	}
	
	/**
	 * Color in the possible movements that the selected chess piece can make with the corresponding color
	 * to the movement type.
	 * 
	 * @param chessBoard The GUI chess board to edit
	 * @param numRows the number of rows of the board
	 * @param numCols the number of columns of the board
	 * @param chessGameRunner the chess game
	 */
	public void setChessBoardPossibleMovementsColor(GridPane chessBoard, int numRows, int numCols, ChessGameRunner chessGameRunner)
	{
		ChessPiece selectedChessPiece = chessGameRunner.getSelectedChessPiece();
		Board board = chessGameRunner.getBoard();
		
		if (selectedChessPiece != null)
		{
			Color moveAndCaptureColor = Color.DARKRED; //'X'
			Color captureColor = Color.RED; //'x'
			Color moveColor = Color.BLUE; //'O', 'T'
			Color specialMovementColor = Color.PURPLE; // 'C', 'E'
			
			Color currentPositionColor = Color.DARKGOLDENROD;
			
			Color enemyMovementColor = Color.BLACK;
			
			//Calculate the potential movements and then calculate the possible movements.
			ArrayList<ArrayList<Character>> potentialMovements = selectedChessPiece.calculatePotentialMovements(board);
			ArrayList<ArrayList<Character>> possibleMovements = selectedChessPiece.calculatePossibleMovements(potentialMovements, board);
		
			//Loop through every rectangle button
			int index = 0;
			for (int row = 0; row < numRows; row++)
			{
				for (int column = 0; column < numCols; column++)
				{
					if (chessBoard.getChildren().get(index) instanceof Rectangle)
					{
						//Prepare by getting the color of the rectangle and the movement type.
						Rectangle rectangle = (Rectangle) chessBoard.getChildren().get(index);
						Paint fill = rectangle.getFill();
						Color recColor = (Color) fill;
						double mixRatio = 0.85;
						Character movementType = possibleMovements.get(row).get(column);
						
						//Color in the rectangle with the corresponding color for the possible movement type
						if (movementType.equals(Movement.moveAndCaptureSymbol)) 
							rectangle.setFill(recColor.interpolate(moveAndCaptureColor, mixRatio));
						if (movementType.equals(Movement.captureSymbol)) 
							rectangle.setFill(recColor.interpolate(captureColor, mixRatio));
						if (movementType.equals(Movement.moveSymbol) || movementType.equals(Movement.twoUnitsUpSymbol)) 
							rectangle.setFill(recColor.interpolate(moveColor, mixRatio));
						if (movementType.equals(Movement.castleSymbol) || movementType.equals(Movement.enPassantSymbol)) 
							rectangle.setFill(recColor.interpolate(specialMovementColor, mixRatio));
						if (movementType.equals(Movement.currentPositionSymbol))
							rectangle.setFill(recColor.interpolate(currentPositionColor, mixRatio));
						
						//Mark possible movement tiles of opponent chess pieces with a different color
						if (chessGameRunner.getCurrentOpponent().getColor().equals(selectedChessPiece.getColor()))
						{
							if (!(movementType.equals(Movement.currentPositionSymbol) || 
									movementType.equals(Movement.invalidMoveSymbol)))
										rectangle.setFill(recColor.interpolate(enemyMovementColor, mixRatio));
						}
					}
					index++;
				}
			}
		}
	}
	
	/**
	 * Remove all of the chess pieces so that their GUI display positions can be updated
	 * 
	 * @param chessBoard The GUI chess board to edit
	 * @param numRows the number of rows of the board
	 * @param numCols the number of columns of the board
	 */
	public void removeChessPieces(GridPane chessBoard, int numRows, int numCols)
	{
		//Remove from the back until only rectangles are left
		for (int i = 0; i < chessBoard.getChildren().size(); i++)
		{
			if (chessBoard.getChildren().get(i) instanceof ImageView)
			{
				chessBoard.getChildren().remove(i);
				i--;
			}
		}
	}
	
	/**
	 * All all of the chess pieces by looping through the white chess pieces and black chess pieces
	 * and adding the chess pieces onto the GUI display board at the correct position
	 * 
	 * @param chessBoard The GUI chess board to edit
	 * @param tileSize the tile size of the GUI chess board
	 * @param chessGameRunner the chess game runner
	 */
	public void addChessPieces(GridPane chessBoard, int tileSize, ChessGameRunner chessGameRunner)
	{
		Board board = chessGameRunner.getBoard();
		
		//Loop through all of the black chess pieces and add them to the chess board GridPane
		 for(int i=0;i<board.getBlackChessPieces().size();i++)
		 {
			 if(board.getBlackChessPieces().get(i).getIsCaptured() == false)
			 {
				 ChessPiece piece = board.getBlackChessPieces().get(i);
				 
				 ImageView view = new ImageView(getImage(piece));
				 view.setScaleY(1);
					
				 view.setFitHeight(tileSize);
				 view.setFitWidth(tileSize); 
				 view.setPreserveRatio(true);
				 view.setMouseTransparent(true);
	
				 chessBoard.add(view, piece.getPosCol() , piece.getPosRow());
			 }
		 }
		 
		//Loop through all of the white chess pieces and add them to the chess board GridPane
		 for(int i=0;i<board.getWhiteChessPieces().size();i++)
		 {
			 if(board.getWhiteChessPieces().get(i).getIsCaptured() == false)
			 {
				 ChessPiece piece = board.getWhiteChessPieces().get(i);
				 
				 ImageView view = new ImageView(getImage(piece));
				 view.setScaleY(1);
					
				 view.setFitHeight(tileSize);
				 view.setFitWidth(tileSize); 
				 view.setPreserveRatio(true);
				 view.setMouseTransparent(true);
				 
				 chessBoard.add(view, piece.getPosCol() , piece.getPosRow());
			 }
		 }
	}

	/**
	 * Update the GUI. The whole thing is a BorderPane embedded with VBoxes on both sides, a borderPane in the center, 
	 * HBoxes on the top and bottom. The left side displays the game information and information about the selected 
	 * chess piece. The right side displays information about upgrades or promotions, and items. The center displays the 
	 * chess board, with player informations on the top and bottom. The top of the layout is the save/load game menu.
	 * 
	 * @param tileSize The tile size of the GUI chess board
	 * @param chessBoard The chess board to edit
	 * @param chessGameRunner The chess game runner
	 */
	public void updateGui(int tileSize, GridPane chessBoard, ChessGameRunner chessGameRunner)
	{
		//Dimensions
		int rowNum = Board.rowNum;
		int colNum = Board.colNum;
		
		//The left side of the BorderPane layout
		//--------------------------------------------------------------------------------------------------------
		layoutLeft = new VBox(30);
		layoutLeft.setPrefWidth(350);
		layoutLeft.setAlignment(Pos.TOP_CENTER);
		layoutLeft.setStyle("-fx-background-color: grey;");
		
		//Game Information (BorderPane layout's Left)
		//A new VBox gameInformation is generated every turn.
		if (!chessGameRunner.getGameOver())
		{
			//Game Information
			//------------------------------------
			VBox gameInformation = chessGameRunner.generateGUIGameInformation();
			//------------------------------------
			layoutLeft.getChildren().add(gameInformation);
		}
		else
		{
			//Restart
			//------------------------------------
			VBox reset = chessGameRunner.generateGUIGameOverMessage();
			reset.setAlignment(Pos.CENTER);
			
			Button resetButton = new Button("Play Again");
			resetButton.setAlignment(Pos.CENTER);
			
			resetButton.setOnAction(e -> {
				
				chessGameRunner.SetUp(initialMoneyAmount, initialPassiveIncomeAmount, passiveIncomeBonus);
				chessGameRunner.displayAllTextBaseVersion(chessGameRunner.getWhitePlayer());
				
				updateGui(tileSize, chessBoard, chessGameRunner);
			});
			reset.getChildren().add(resetButton);
			//------------------------------------
			layoutLeft.getChildren().add(reset);
		}
		
		//Selected Chess Piece Information
		//------------------------------------
		VBox selectedChessPieceInformation = chessGameRunner.generateGUISelectedChessPieceInformation();
		layoutLeft.getChildren().add(selectedChessPieceInformation);
		//------------------------------------
		
		layout.setLeft(layoutLeft);
		//--------------------------------------------------------------------------------------------------------
		
		
		//The center of the BorderPane layout
		//--------------------------------------------------------------------------------------------------------
		//Player information (BorderPane layout's Center's Top and Bottom)
		//A new Hbox is generated for both player information
		blackPlayerInformation = chessGameRunner.generateGUIBlackPlayerInformation();
		layoutCenter.setTop(blackPlayerInformation);
		
		whitePlayerInformation = chessGameRunner.generateGUIWhitePlayerInformation();
		layoutCenter.setBottom(whitePlayerInformation);
		
		//ChessBoard, the board color, possible movements, and chess piece images
		//(BorderPane layout's Center's Center)
		//The chessBoard GridPane is edited.
		setChessBoardColor(chessBoard, rowNum, colNum, Color.LIGHTGREY, Color.GREEN);
		setKingInCheckColor(chessBoard, rowNum, colNum, chessGameRunner);
		setChessBoardPossibleMovementsColor(chessBoard, rowNum, colNum, chessGameRunner);
		removeChessPieces(chessBoard, rowNum, colNum);
		addChessPieces(chessBoard, tileSize, chessGameRunner);
		//--------------------------------------------------------------------------------------------------------
		
		
		//The right side of the BorderPane layout
		//--------------------------------------------------------------------------------------------------------
		layoutRight = new VBox(30);
		layoutRight.setPrefWidth(350);
		layoutRight.setAlignment(Pos.TOP_CENTER);
		layoutRight.setStyle("-fx-background-color: grey;");
		
		//If there is promotion at the end of the turn, replace upgrade with promotion list.
		if (chessGameRunner.getCurrentPlayer().findPawnReadyToPromote() != -1 && chessGameRunner.isMoveWasMade())
		{
			//Promotion
			//------------------------------------
			VBox promotionGUI = chessGameRunner.generateGUIPromotion();
			
			ListView<String> promotionListView = (ListView<String>) promotionGUI.getChildren().get(1);
			
			Button promotionButton = new Button("Confirm Promotion");
			promotionButton.setOnAction(e -> {
				ArrayList<Integer> userInput = new ArrayList<Integer>();
				userInput.add(-3);
				userInput.add(promotionListView.getSelectionModel().getSelectedIndex());
				
				if (userInput.get(1) != -1)
				{
					chessGameRunner.runGame(userInput);
					updateGui(tileSize, chessBoard, chessGameRunner);
				}
			});
			promotionGUI.getChildren().add(promotionButton);
			//------------------------------------
			layoutRight.getChildren().addAll(promotionGUI);
		}
		else
		{
			//Upgrade
			//------------------------------------
			VBox upgradesGUI = chessGameRunner.generateGUIUpgrades();
			
			ListView<String> upgradesListView = (ListView<String>) upgradesGUI.getChildren().get(1);
			
			Button upgradeButton = new Button("Confirm Upgrade");
			upgradeButton.setOnAction(e -> {
				ArrayList<Integer> userInput = new ArrayList<Integer>();
				userInput.add(-2);
				userInput.add(upgradesListView.getSelectionModel().getSelectedIndex());
				
				if (userInput.get(1) != -1)
				{
					chessGameRunner.runGame(userInput);
					updateGui(tileSize, chessBoard, chessGameRunner);
				}
			});
			upgradesGUI.getChildren().add(upgradeButton);
			//------------------------------------
			layoutRight.getChildren().addAll(upgradesGUI);
		}
		
		//Items
		//------------------------------------
		VBox itemsGUI = chessGameRunner.generateGUIItems();
		ListView<String> itemsListView = (ListView<String>) itemsGUI.getChildren().get(1);
		Button itemButton = new Button("Purchase Item");
		itemButton.setOnAction(e -> {
			ArrayList<Integer> userInput = new ArrayList<Integer>();
			userInput.add(-1);
			userInput.add(itemsListView.getSelectionModel().getSelectedIndex());
			
			if (userInput.get(1) != -1)
			{
				chessGameRunner.runGame(userInput);
				updateGui(tileSize, chessBoard, chessGameRunner);
			}
		});
		
		itemsGUI.getChildren().add(itemButton);
		//------------------------------------
		
		layoutRight.getChildren().addAll(itemsGUI);
		layout.setRight(layoutRight);
		//--------------------------------------------------------------------------------------------------------
	}
	
	/**
	 * Create the menu display
	 * 
	 * @param tileSize The tileSize of the GUI chess board
	 */
	public void createMenu(int tileSize)
	{
		menuBar = new MenuBar();
		fileMenu = new Menu("File");
		open = new MenuItem("Open");
		saveGame = new MenuItem("Save");

		menuBar.setStyle("-fx-text-fill: black");
		fileMenu.setStyle("-fx-text-fill: black");
		open.setStyle("-fx-text-fill: black");
		saveGame.setStyle("-fx-text-fill: black");

		saveGame.setOnAction(event->{
			chessGameRunner.saveGame();
		});

		open.setOnAction(event->{
			chessGameRunner.loadGame();
			updateGui(tileSize, chessBoard, chessGameRunner);
		});

		fileMenu.getItems().add(open);
		fileMenu.getItems().add(saveGame);

		menuBar.getMenus().add(fileMenu);
	}
}
