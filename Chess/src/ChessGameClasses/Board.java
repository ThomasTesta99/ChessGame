package ChessGameClasses;

import Movements.Movement;
import ChessPieces.*;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * The Board class holds the chess game board. It contains the position board, and the control
 * boards of both player.
 */
public class Board implements Serializable
{
	/**
	 * Number of rows
	 */
	public static final int rowNum = 10;
	
	/**
	 * Number of columns
	 */
	public static final int colNum = 9;	

	/**
	 * Character to represent the white king on the character board
	 */
	public static final Character whiteKingPieceSymbol = 'W';		
	
	/**
	 * Character to represent a white piece on the character board
	 */
	public static final Character whitePieceSymbol = 'w';		
	
	/**
	 * Character to represent the black king on the character board
	 */
	public static final Character blackKingPieceSymbol = 'B';		
	
	/**
	 * Character to represent a black piece on the character board
	 */
	public static final Character blackPieceSymbol = 'b';			
	
	/**
	 * White chess pieces
	 */
	private ArrayList<ChessPiece> whiteChessPieces;	
	
	/**
	 * Black ChessPieces
	 */
	private ArrayList<ChessPiece> blackChessPieces;

	/**
	 * Position of all the pieces on the board
	 */
	private ArrayList<ArrayList<Character>> positionBoard;
	
	/**
	 * Combination of all possible moves for white chess pieces
	 */
	private ArrayList<ArrayList<Character>> whiteControlBoard;
	
	/**
	 * Combination of all possible moves for black chess pieces
	 */
	private ArrayList<ArrayList<Character>> blackControlBoard;

	/**
	 * Class constructor.
	 * @param whiteChessPieces - ArrayList<ChessPiece>, white chess pieces.
	 * @param blackChessPieces - ArrayList<ChessPiece>, black chess pieces.
	 */
	public Board(ArrayList<ChessPiece> whiteChessPieces, ArrayList<ChessPiece> blackChessPieces)
	{
		this.whiteChessPieces = whiteChessPieces;
		this.blackChessPieces = blackChessPieces;
		
		this.positionBoard = createAnEmpty2DCharacterArrayList();
		this.whiteControlBoard = createAnEmpty2DCharacterArrayList();
		this.blackControlBoard = createAnEmpty2DCharacterArrayList();
		
		updatePositionBoard();
		updateControlBoards();
	}

	/**
	 * Copy Constructor
	 * @param source - Board, board object to be copied.
	 */
	public Board(Board source)
	{
		this.whiteChessPieces = new ArrayList<ChessPiece>();
		this.blackChessPieces = new ArrayList<ChessPiece>();
		
		copyChessPieceArrayListByValue(this.whiteChessPieces, source.whiteChessPieces);
		copyChessPieceArrayListByValue(this.blackChessPieces, source.blackChessPieces);
		
		this.positionBoard = createAnEmpty2DCharacterArrayList();
		this.whiteControlBoard = createAnEmpty2DCharacterArrayList();
		this.blackControlBoard = createAnEmpty2DCharacterArrayList();
		
		updatePositionBoard();
		updateControlBoards();
	}

	/**
	 * Method to get the white chess pieces.
	 * @return - whiteChessPieces - ArrayList<ChessPiece>.
	 */
	public ArrayList<ChessPiece> getWhiteChessPieces() { return whiteChessPieces; }

	/**
	 * Method to get the black chess pieces.
	 * @return - blackChessPieces -  ArrayList<ChessPiece>.
	 */
	public ArrayList<ChessPiece> getBlackChessPieces() { return blackChessPieces; }

	/**
	 * Method to get the position board.
	 * @return - position board - ArrayList<ChessPiece>.
	 */
	public ArrayList<ArrayList<Character>> getPositionBoard() { return positionBoard; }

	/**
	 * Method to return a version of the board that marks all the possible movements of the white pieces.
	 * @return - whiteControlBoard - ArrayList<ChessPiece>.
	 */
	public ArrayList<ArrayList<Character>> getWhiteControlBoard() { return whiteControlBoard; }

	/**
	 * Method to return a version of the board that marks all the possible movements of the black pieces.
	 * @return - blackControlBoard - ArrayList<ChessPiece>.
	 */
	public ArrayList<ArrayList<Character>> getBlackControlBoard() { return blackControlBoard; }

	/**
	 * Method to create a deep copy for a board object.
	 * @param source - Board - object to be copied.
	 */
	public void deepCopy(Board source)
	{
		this.whiteChessPieces.clear();
		this.blackChessPieces.clear();

		copyChessPieceArrayListByValue(this.whiteChessPieces, source.whiteChessPieces);
		copyChessPieceArrayListByValue(this.blackChessPieces, source.blackChessPieces);

		this.positionBoard = source.getPositionBoard();
		this.whiteControlBoard = source.getWhiteControlBoard();
		this.blackControlBoard = source.getBlackControlBoard();
	}

	/**
	 * Method to update the position of each chess piece after each turn.
	 */
	public void updatePositionBoard()
	{
		//Create an empty ArrayList<ArrayList<Character>> to store the possible moves
		positionBoard.clear();
		positionBoard = new ArrayList<ArrayList<Character>>();
		for (int row = 0; row < rowNum; row++)
		{
			ArrayList<Character> currentRow = new ArrayList<Character>();
			for (int column = 0; column < colNum; column++)
				currentRow.add(Movement.invalidMoveSymbol);

			positionBoard.add(currentRow);
		}

		//Add all the white chess pieces to the Position Board if they haven't been captured yet.
		//And differentiate between Kings and non-King pieces.
		for (ChessPiece whitePiece : whiteChessPieces)
		{
			if (!whitePiece.getIsCaptured())
			{
				//If it is the White King
				if (whitePiece instanceof King)
					positionBoard.get(whitePiece.getPosRow()).set(whitePiece.getPosCol(), whiteKingPieceSymbol);
				else
					positionBoard.get(whitePiece.getPosRow()).set(whitePiece.getPosCol(), whitePieceSymbol);
			}
		}

		//Add all the black chess pieces to the Position Board if they haven't been captured yet.
		//And differentiate between Kings and non-King pieces.
		for (ChessPiece blackPiece : blackChessPieces)
		{
			if (!blackPiece.getIsCaptured())
			{
				//If it is the Black King
				if (blackPiece instanceof King)
					positionBoard.get(blackPiece.getPosRow()).set(blackPiece.getPosCol(), blackKingPieceSymbol);
				else
					positionBoard.get(blackPiece.getPosRow()).set(blackPiece.getPosCol(), blackPieceSymbol);
			}
		}
	}

	/**
	 * Method that loops through all the Player's chess pieces and combines their movement boards to create a board that
	 * knows every space a player controls.
	 * @param chessPieces - ArrayList<ChessPiece>, Player's chess pieces.
	 * @param controlBoard - ArrayList<ChessPiece>, current controlBoard to be updated.
	 */
	public void calculateControlBoard(ArrayList<ChessPiece> chessPieces, ArrayList<ArrayList<Character>> controlBoard)
	{
		//clear controlBoard so that the Player's board control can be updated.
		controlBoard.clear();
		for (int row = 0; row < Board.rowNum; row++)
		{
			ArrayList<Character> currentRow = new ArrayList<Character>();
			for (int column = 0; column < Board.colNum; column++)
				currentRow.add(Movement.invalidMoveSymbol);

			controlBoard.add(currentRow);
		}

		//Loop through all the Player's Chess Pieces.
		for (ChessPiece chessPiece : chessPieces)
		{
			//If the piece wasn't captured.
			if (!chessPiece.getIsCaptured())
			{
				ArrayList<ArrayList<Character>> currentPieceMovements = chessPiece.calculatePotentialMovements(this);
				for (int row = 0; row < currentPieceMovements.size(); row++)
				{
					for (int column = 0; column < currentPieceMovements.get(row).size(); column++)
					{
						Character charInAll = controlBoard.get(row).get(column);
						Character charInCurrent = currentPieceMovements.get(row).get(column);

						//Precedence Order from greatest to lowest
						//(capture&move, capture) to move to invalid move.
						if (charInAll.equals(Movement.moveAndCaptureSymbol) || charInCurrent.equals(Movement.moveAndCaptureSymbol))
							controlBoard.get(row).set(column, Movement.moveAndCaptureSymbol);
						else if (charInAll.equals(Movement.captureSymbol) || charInCurrent.equals(Movement.captureSymbol))
							controlBoard.get(row).set(column, Movement.captureSymbol);
						else if (charInAll.equals(Movement.moveSymbol) || charInCurrent.equals(Movement.moveSymbol))
							controlBoard.get(row).set(column, Movement.moveSymbol);
						else
							controlBoard.get(row).set(column, Movement.invalidMoveSymbol);
					}
				}
			}
		}
	}

	/**
	 * Method to update both of the player's control boards.
	 */
	public void updateControlBoards()
	{
		calculateControlBoard(this.whiteChessPieces, this.whiteControlBoard);
		calculateControlBoard(this.blackChessPieces, this.blackControlBoard);
	}

	/**
	 * Method to determine whether the king is in check.
	 * @param color - String, current player color.
	 * @return - boolean, whether the king is in check.
	 */
	public boolean isInCheck(String color)
	{
		ArrayList<ChessPiece> allyChessPieces;
		ArrayList<ArrayList<Character>> enemyControlBoard;
		if (color.equals("White"))
		{
			allyChessPieces = whiteChessPieces;
			enemyControlBoard = blackControlBoard;
		}
		else
		{
			allyChessPieces = blackChessPieces;
			enemyControlBoard = whiteControlBoard;
		}

		int kingRow = -1;
		int kingCol = -1;
		for (int i = 0; i < allyChessPieces.size(); i++)
		{
			if (allyChessPieces.get(i) instanceof King)
			{
				kingRow = allyChessPieces.get(i).getPosRow();
				kingCol = allyChessPieces.get(i).getPosCol();
			}

		}

		if (kingRow == -1 && kingCol == -1)
			return false;
		else
			return (enemyControlBoard.get(kingRow).get(kingCol).equals(Movement.captureSymbol)) ||
					(enemyControlBoard.get(kingRow).get(kingCol).equals(Movement.moveAndCaptureSymbol));
	}

	/**
	 * Method to determine the number of moves available for the current player. Used to determine stalemate and checkmate.
	 * @param color - String, current player color.
	 * @return - int - the number of moves allowed.
	 */
	public int numberOfLegalMoves(String color)
	{
		ArrayList<ChessPiece> allyChessPieces;
		if (color.equals("White")) allyChessPieces = whiteChessPieces;
		else allyChessPieces = blackChessPieces;

		//Calculate the possibleMovement board of each chess piece and
		//count up the number of possible movements of all chess pieces.
		int count = 0;
		for (ChessPiece chessPiece : allyChessPieces)
		{
			if (!chessPiece.getIsCaptured())
			{
				ArrayList<ArrayList<Character>> potentialMovements = chessPiece.calculatePotentialMovements(this);
				ArrayList<ArrayList<Character>> possibleMovements = chessPiece.calculatePossibleMovements(potentialMovements, this);
				for (ArrayList<Character> list : possibleMovements)
					for (Character movementType : list)
						if (!(movementType.equals(Movement.invalidMoveSymbol) || movementType.equals(Movement.currentPositionSymbol)))
							count++;
			}
		}
		return count;
	}

	//The board that labels every piece for which specific piece they are and what color they have.

	/**
	 * Method to get the board used for display.  Labels every piece for the piece and color they are.
	 * @return - ArrayList<ArrayList<Character>> - the board to display.
	 */
	public ArrayList<ArrayList<Character>> getDisplayBoard()
	{
		//Create an empty 2d array list to store the display board.
		ArrayList<ArrayList<Character>> displayBoard = new ArrayList<ArrayList<Character>>();
		for (int row = 0; row < rowNum; row++)
		{
			ArrayList<Character> currentRow = new ArrayList<Character>();
			for (int column = 0; column < colNum; column++)
				currentRow.add(Movement.invalidMoveSymbol);
			displayBoard.add(currentRow);
		}

		//Record the piece onto the displayBoard if it hasn't been captured yet.
		for (ChessPiece whitePiece : whiteChessPieces)
		{
			if (!whitePiece.getIsCaptured())
				displayBoard.get(whitePiece.getPosRow()).set(whitePiece.getPosCol(), whitePiece.getImage());
		}

		//Record the piece onto the displayBoard if it hasn't been captured yet.
		for (ChessPiece blackPiece : blackChessPieces)
		{
			if (!blackPiece.getIsCaptured())
				displayBoard.get(blackPiece.getPosRow()).set(blackPiece.getPosCol(), blackPiece.getImage());
		}

		return displayBoard;
	}

	/**
	 * Method to copy an ArrayList of chess pieces from one ArrayList to another.
	 * @param destination - ArrayList<ChessPiece>, new ArrayList to hold the copied chess pieces.
	 * @param source - ArrayList<ChessPiece>, list of chess pieces to be copied.
	 */
	public static void copyChessPieceArrayListByValue(ArrayList<ChessPiece> destination, ArrayList<ChessPiece> source)
	{
		for (ChessPiece chessPiece : source)
		{
			if (chessPiece instanceof Archer) destination.add(new Archer(chessPiece));
			else if (chessPiece instanceof Bishop) destination.add(new Bishop(chessPiece));
			else if (chessPiece instanceof Cannon) destination.add(new Cannon(chessPiece));
			else if (chessPiece instanceof King) destination.add(new King(chessPiece));
			else if (chessPiece instanceof Knight) destination.add(new Knight(chessPiece));
			else if (chessPiece instanceof Ninja) destination.add(new Ninja(chessPiece));
			else if (chessPiece instanceof Pawn) destination.add(new Pawn(chessPiece));
			else if (chessPiece instanceof Queen) destination.add(new Queen(chessPiece));
			else if (chessPiece instanceof Rook) destination.add(new Rook(chessPiece));
			else if (chessPiece instanceof Wizard) destination.add(new Wizard(chessPiece));
		}
	}

	/**
	 * Method to display the character board(text based version).
	 * @param array - ArrayList<ArrayList<Character>>, a 2D grid that will hold all the piece symbols.
	 */
	public static void displayBoard(ArrayList<ArrayList<Character>> array)
	{
		System.out.print("  ");
		for (int column = 0; column < array.get(0).size(); column++)
			System.out.print(column + " ");
		System.out.println();

		for (int row = 0; row < array.size(); row++)
		{
			System.out.print(row + " "); //Row label
			for (int column = 0; column < array.get(row).size(); column++)
			{
				if (array.get(row).get(column).equals(' '))
					System.out.print("-");
				else
					System.out.print(array.get(row).get(column));
				System.out.print(" ");
			}
			System.out.println();
		}
	}

	/**
	 * Method to create an empty 2D ArrayList of Characters.
	 * @return - ArrayList<ArrayList<Character>> - an empty 2D ArrayList.
	 */
	public static ArrayList<ArrayList<Character>> createAnEmpty2DCharacterArrayList()
	{
		ArrayList<ArrayList<Character>> result = new ArrayList<ArrayList<Character>>();
		for (int row = 0; row < Board.rowNum; row++)
		{
			ArrayList<Character> currentRow = new ArrayList<Character>();
			for (int column = 0; column < Board.colNum; column++)
				currentRow.add(Movement.invalidMoveSymbol);
			result.add(currentRow);
		}
		return result;
	}

	/**
	 * Method to check if the row and column are valid.
	 * @param row - int, board row.
	 * @param col - int, board column.
	 * @return - boolean - whether the row and column fall on the board.
	 */
	public boolean rowColWithinBound(int row, int col)
	{
		return ((row >= 0) && (row < rowNum) && (col >= 0) && (col < colNum));
	}
}
