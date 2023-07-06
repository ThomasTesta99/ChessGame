package ChessPieces;

import ChessGameClasses.Board;
import Movements.Movement;
import javafx.scene.image.Image;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * ChessPiece class handles calculating the potentialMovements and possibleMovements of 
 * the Chess Piece. It also handles moving, capturing, and special movements that the chess piece
 * may have.
 */
abstract public class ChessPiece implements Serializable
{	
	/**
	 * list of directional movements the piece can make
	 */
	private ArrayList<Movement> movements;			
	
	/**
	 * list of movements the piece can upgrade to
	 */
	private ArrayList<Movement> availableUpgrades;		
	
	/**
	 * row position
	 */
	private int posRow;	
	
	/**
	 * column position
	 */
	private int posCol;				

	/**
	 * piece color
	 */
	private String color;
	
	/**
	 * if the piece is captured
	 */
	private boolean isCaptured;						
	
	/**
	 * keeps track of if the piece moved
	 */
	private boolean hasMoved;							
	
	/**
	 * piece symbol
	 */
	private char image;							


	/**
	 * Class constructor.
	 * @param posRow - int, row position.
	 * @param posCol - int, column position.
	 * @param color - String, piece color.
	 * @param image - char, piece symbol.
	 */
	public ChessPiece(int posRow, int posCol, String color, char image)
	{
		this.setPosRow(posRow);
		this.setPosCol(posCol);
		this.setColor(color);
		this.setImage(image);
		
		this.setMovements(new ArrayList<Movement>());
		this.setAvailableUpgrades(new ArrayList<Movement>());
		this.setIsCaptured(false);
		this.setHasMoved(false);
	}

	/**
	 * Copy constructor.
	 * @param source - chessPiece object to be copied.
	 */
	public ChessPiece(ChessPiece source)
	{
		this.setPosRow(source.posRow);
		this.setPosCol(source.posCol);
		this.setColor(source.color);
		this.setIsCaptured(source.isCaptured);
		this.setHasMoved(source.hasMoved);
		this.setImage(source.image);
		
		//Copy the movements by reference since they will not be changed.
		this.setMovements(new ArrayList<Movement>());
		for (Movement movement : source.getMovements())
			getMovements().add(movement);
		
		//Copy the available upgrade movements by reference since they will not be changed.
		this.setAvailableUpgrades(new ArrayList<Movement>());
			for (Movement movement : source.getAvailableUpgrades())
				getAvailableUpgrades().add(movement);
	}
	/**
	 * Method to get the piece color.
	 * @return color - String.
	 */
	public String getColor() { return color; }

	/**
	 * Method to get whether the piece has moved.
	 * @return hasMoved - boolean.
	 */
	public boolean getHasMoved() { return hasMoved; }

	/**
	 * Method to get the piece symbol.
	 * @return image - char.
	 */
	public char getImage() { return image; }

	/**
	 * Method to get whether the piece was captured.
	 * @return isCaptured - boolean.
	 */
	public boolean getIsCaptured() { return isCaptured; }

	/**
	 * Method to get the piece directional movements.
	 * @return movements - ArrayList<Movement>.
	 */
	public ArrayList<Movement> getMovements() { return movements; }

	/**
	 * Method to get the column position.
	 * @return posCol - int.
	 */
	public int getPosCol() { return posCol; }

	/**
	 * Method to get the row position
	 * @return posRow - int.
	 */
	public int getPosRow() { return posRow; }

	/**
	 * Method to return the available upgrades of the piece.
	 * @return availableUpgrades - ArrayList<Movement>.
	 */
	public ArrayList<Movement> getAvailableUpgrades() { return availableUpgrades; }

	/**
	 * Method to set the piece color.
	 * @param color - String, piece color
	 */
	public void setColor(String color) { this.color = color; }

	/**
	 * Method to set whether the piece has moved.
	 * @param hasMoved - boolean, if the piece has moved or not.
	 */
	public void setHasMoved(boolean hasMoved) { this.hasMoved = hasMoved; }

	/**
	 * Method to set the piece symbol.
	 * @param image - char, piece symbol.
	 */
	public void setImage(char image) { this.image = image; }

	/**
	 * Method to set if the piece has been captured.
	 * @param isCaptured - boolean, captured or not.
	 */
	public void setIsCaptured(boolean isCaptured) { this.isCaptured = isCaptured; }

	/**
	 * Method to set the directional movements of the piece.
	 * @param movements - ArrayList<Movement>, list of directional movements.
	 */
	public void setMovements(ArrayList<Movement> movements) { this.movements = movements; }

	/**
	 * Method to set the column position.
	 * @param posCol - int, column position.
	 */
	public void setPosCol(int posCol) { this.posCol = posCol; }

	/**
	 * Method to set the row position.
	 * @param posRow - int, row position.
	 */
	public void setPosRow(int posRow) { this.posRow = posRow; }

	/**
	 * Method to set the upgrades available for the piece.
	 * @param availableUpgrades - ArrayList<Movement>, available piece upgrades.
	 */
	public void setAvailableUpgrades(ArrayList<Movement> availableUpgrades) { this.availableUpgrades = availableUpgrades; }
	

	/**
	 * Return the String format of the ChessPiece.
	 * 'name' At ('row', 'column').  
	 */
	public String toString()
	{
		return getName() + " At (" + getPosRow() + ", " + getPosCol() + ")";
	}

	/**
	 * Abstract method to get the piece worth.
	 * @return materialWorth - int.
	 */
	abstract public int getMaterialWorth();

	/**
	 * Abstract method to get the piece name.
	 * @return name - String.
	 */
	abstract public String getName();

	/**
	 * Method to get all the potential movements the piece can make by using its directional movements.
	 * @param board - Board, the chess board.
	 * @return result - ArrayList<ArrayList<Character>>, a 2D board of potential spots the piece can move to.
	 */
	public ArrayList<ArrayList<Character>> calculatePotentialMovements(Board board)
	{
		//Create an empty ArrayList<ArrayList<Character>> to store the potential movement.
		ArrayList<ArrayList<Character>> allPotentialMovements = new ArrayList<ArrayList<Character>>();
		for (int row = 0; row < board.getPositionBoard().size(); row++)
		{
			ArrayList<Character> currentRow = new ArrayList<Character>();
			for (int column = 0; column < board.getPositionBoard().get(row).size(); column++)
				currentRow.add(Movement.invalidMoveSymbol);
				
			allPotentialMovements.add(currentRow);
		}
		
		//Loop through all of the movements and add them to the potential movement.
		for (Movement movement : getMovements())
		{
			ArrayList<ArrayList<Character>> currentPotentialMovements = movement.calculateMovement(getPosRow(), getPosCol(), getColor(), getHasMoved(), board);
			for (int row = 0; row < currentPotentialMovements.size(); row++)
			{
				for (int column = 0; column < currentPotentialMovements.get(row).size(); column++)
				{
					Character charInAll = allPotentialMovements.get(row).get(column);
					Character charInCurrent = currentPotentialMovements.get(row).get(column);
					
					//Precedence Order from greatest to lowest
					//Special Movements -> Normal Movements -> Invalid Movement
					
					//Special Movement Types
					if (charInAll.equals(Movement.castleSymbol) || charInCurrent.equals(Movement.castleSymbol))
						allPotentialMovements.get(row).set(column, Movement.castleSymbol);
					else if (charInAll.equals(Movement.twoUnitsUpSymbol) || charInCurrent.equals(Movement.twoUnitsUpSymbol))
						allPotentialMovements.get(row).set(column, Movement.twoUnitsUpSymbol);
					else if (charInAll.equals(Movement.enPassantSymbol) || charInCurrent.equals(Movement.enPassantSymbol))
						allPotentialMovements.get(row).set(column, Movement.enPassantSymbol);
					
					//Normal Movement Types
					else if (charInAll.equals(Movement.moveAndCaptureSymbol) || charInCurrent.equals(Movement.moveAndCaptureSymbol))
						allPotentialMovements.get(row).set(column, Movement.moveAndCaptureSymbol); 
					else if (charInAll.equals(Movement.captureSymbol) || charInCurrent.equals(Movement.captureSymbol))
						allPotentialMovements.get(row).set(column, Movement.captureSymbol); 
					else if (charInAll.equals(Movement.moveSymbol) || charInCurrent.equals(Movement.moveSymbol))
						allPotentialMovements.get(row).set(column, Movement.moveSymbol);
					
					//Invalid Movement
					else
						allPotentialMovements.get(row).set(column, Movement.invalidMoveSymbol); 
				}
			}
		}
		
		//Mark the piece itself
		allPotentialMovements.get(getPosRow()).set(getPosCol(), Movement.currentPositionSymbol); 
		return allPotentialMovements;
	}

	/**
	 * Method to update the position of the piece.
	 * @param newRow - int, new row position.
	 * @param newCol - int, new column position.
	 */
	public void updateMove(int newRow, int newCol)
	{
		this.posRow = newRow;
		this.posCol = newCol;
		hasMoved = true;
	}

	/**
	 * Method to simulate a potential move of a piece and determine whether that move results in your King being in check.
	 * @param moveRow - int, potential row position.
	 * @param moveCol - int, potential column position.
	 * @param currentMovementType - Character, a special symbol identify the spot on the board to see if the piece can move there.
	 * @param board - Board, chess board.
	 * @return boolean - whether the move results in check.
	 */
	public boolean thisMoveResultsInCheck(int moveRow, int moveCol, Character currentMovementType, Board board)
	{
		//Create a copy of the current board and store the inputs into an ArrayList
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
		
		
		//Find the current ChessPiece in the copyBoard
		ChessPiece currentChessPiece;
		int pos = -1;
		for (int i = 0; i < allyChessPieces.size(); i++)
			if (allyChessPieces.get(i).getPosRow() == posRow && allyChessPieces.get(i).getPosCol() == posCol)
				pos = i;
		currentChessPiece = allyChessPieces.get(pos);
		
		//Record the type of movement this is. 
		ArrayList<ArrayList<Character>> potentialMovements = currentChessPiece.calculatePotentialMovements(board);
		Character movementType = potentialMovements.get(moveRow).get(moveCol);
		
		actuallyPerformTheMovement(moveRow, moveCol, movementType, currentChessPiece, allyChessPieces, opponentChessPieces);
		
		copyBoard.updatePositionBoard();
		copyBoard.updateControlBoards();
		
		return copyBoard.isInCheck(color);
	}

	/**
	 * Method to edit the potential movements a piece can make if those movments result in the king being Checked. Accounts
	 * for pieces that are pinned to their spot to protect the king.
	 * @param potentialMovements - ArrayList<ArrayList<Character>>, a 2D grid of potential places the piece can move.
	 * @param board - Board, chess board.
	 * @return possible movements - ArrayList<ArrayList<Character>>.
	 */
	public ArrayList<ArrayList<Character>> calculatePossibleMovements(ArrayList<ArrayList<Character>> potentialMovements, Board board)
	{
		for (int row = 0; row < potentialMovements.size(); row++)
		{
			for (int col = 0; col < potentialMovements.get(row).size(); col++)
			{
				//If the current potential move results in a check, it is an invalid move.
				Character movementType = potentialMovements.get(row).get(col);
				if (!movementType.equals(Movement.invalidMoveSymbol) && 
						!movementType.equals(Movement.currentPositionSymbol))
				{
					if (thisMoveResultsInCheck(row, col, movementType, board))
						potentialMovements.get(row).set(col, Movement.invalidMoveSymbol);
				}
			}
		}
		return potentialMovements; //Return edited potentialMovements.
	}

	/**
	 * Method to prepare the movement of the chess piece.  First all the potential movements are calculated, then those movements
	 * are edited based on whether the piece's king has been checked.
	 * @param board - Board, chess board.
	 * @param userInput - ArrayList<Integer>, a list containing the coordinates chosen by the user.
	 * @return boolean - if the move was made.
	 */
	public boolean makeMovement(Board board, ArrayList<Integer> userInput)
	{
		//1) Get all of the potential moves that can be made.
		ArrayList<ArrayList<Character>> potentialMovements = calculatePotentialMovements(board);
		
		//2) Determine which of the potential moves are actually legal moves.
		ArrayList<ArrayList<Character>> possibleMovements = calculatePossibleMovements(potentialMovements, board);
					
		//3) Format userInput - (Index 1: row) (Index 2: column).
		int inputRow = userInput.get(0);
		int inputColumn = userInput.get(1);
		
		
		//4) Run the move
		
		//4a) Record the movement type
		Character movementType = possibleMovements.get(inputRow).get(inputColumn);
		
		//4b) Determine the allyChessPieces and the opponentChessPieces based on color.
		ArrayList<ChessPiece> opponentChessPieces;
		ArrayList<ChessPiece> allyChessPieces;
		if (color.equals("White"))
		{
			opponentChessPieces = board.getBlackChessPieces();
			allyChessPieces = board.getWhiteChessPieces();
		}
		else //color == "Black"
		{
			opponentChessPieces = board.getWhiteChessPieces();
			allyChessPieces = board.getBlackChessPieces();
		}
		
		//Actually perform the move and return true if the movement was made.
		return actuallyPerformTheMovement(inputRow, inputColumn, movementType, this, allyChessPieces, opponentChessPieces);
	}

	/**
	 * Method to move the chess piece.  Checks if the piece has captured an enemy piece, and if it has, it marks that piece as
	 * captured. Also handles special movements, such as EnPassant, Castling and Two Units Up.
	 * @param moveRow - int, new row position.
	 * @param moveCol - int, new column position.
	 * @param movementType - Character - movement type.
	 * @param currentChessPiece - ChessPiece, piece currently moving.
	 * @param allyChessPieces - ArrayList<ChessPiece>, current player chess pieces.
	 * @param opponentChessPieces - ArrayList<ChessPiece>, opponent chess pieces.
	 * @return boolean - whether the piece has moved.
	 */
	public boolean actuallyPerformTheMovement(int moveRow, int moveCol, Character movementType,
			ChessPiece currentChessPiece, ArrayList<ChessPiece> allyChessPieces, ArrayList<ChessPiece> opponentChessPieces)
	{
		//Move the ChessPiece if it was a Move or Move&Capture movement type.
		if (movementType.equals(Movement.moveSymbol) || movementType.equals(Movement.moveAndCaptureSymbol))
			currentChessPiece.updateMove(moveRow, moveCol);
		
		//Capture enemy piece if it was a Capture or Move&Capture.
		if (movementType.equals(Movement.captureSymbol) || movementType.equals(Movement.moveAndCaptureSymbol))
			for (int i = 0; i < opponentChessPieces.size(); i++)
				if (opponentChessPieces.get(i).getPosRow() == moveRow && opponentChessPieces.get(i).getPosCol() == moveCol)
					opponentChessPieces.get(i).chessPieceGetsCaptured();
		
		//Castle Movement
		if (movementType.equals(Movement.castleSymbol))
		{
			int kingShift = 3;
			int rookShift = 2;
			
			ChessPiece king = currentChessPiece;
			
			//Find the Rook
			ChessPiece rook;
			int rookIndex = -1;
			for (int i = 0; i < allyChessPieces.size(); i++)
				if (allyChessPieces.get(i).getPosRow() == moveRow && allyChessPieces.get(i).getPosCol() == moveCol)
					rookIndex = i;
			rook = allyChessPieces.get(rookIndex);
				
			//Left castle
			if (moveCol < king.getPosCol())
			{
				king.updateMove(king.getPosRow(), king.getPosCol() - kingShift);
				rook.updateMove(rook.getPosRow(), rook.getPosCol() + rookShift);
			}
			//Right castle
			else if (moveCol > king.getPosCol())
			{
				king.updateMove(king.getPosRow(), king.getPosCol() + kingShift);
				rook.updateMove(rook.getPosRow(), rook.getPosCol() - rookShift);
			}
		}
		
		//Two Units Up Movement
		if (movementType.equals(Movement.twoUnitsUpSymbol))
		{
			currentChessPiece.updateMove(moveRow, moveCol);
			
			//Since currentChessPiece has to have been originally
			//created as a Pawn object, downcasting it to Pawn only
			//changes the reference variable type and no copies will be created.
			Pawn pawn = (Pawn) currentChessPiece;
			pawn.setMovedTwoUnitsUp(true);
		}
		
		//En Passant Movement
		if (movementType.equals(Movement.enPassantSymbol))
		{
			//Move the chess piece.
			currentChessPiece.updateMove(moveRow, moveCol);
		
			int captureRow = currentChessPiece.getPosRow();
			if (currentChessPiece.getColor().equals("White")) captureRow++;
			else captureRow--;
			
			int captureCol = currentChessPiece.getPosCol();
			
			//Capture the enemy chess piece directly behind the movement destination.
			for (int i = 0; i < opponentChessPieces.size(); i++)
				if (opponentChessPieces.get(i).getPosRow() == captureRow && opponentChessPieces.get(i).getPosCol() == captureCol)
					opponentChessPieces.get(i).chessPieceGetsCaptured();
		}
		return (!movementType.equals(Movement.invalidMoveSymbol) && 
				!movementType.equals(Movement.currentPositionSymbol));
	}

	/**
	 * Method to handle when the piece gets captured.  The position is set to (-1, -1) and isCaptured is set to true.
	 */
	public void chessPieceGetsCaptured()
	{
		this.posRow = -1;
		this.posCol = -1;
		this.isCaptured = true;
	}

	/**
	 * Method to display the upgrades the piece can purchase.  If the piece has no upgrades, then the piece can't be upgraded.
	 */
	public void displayUpgrades()
	{
		int upgradeSize = availableUpgrades.size();
		if(upgradeSize == 0)
		{
			System.out.println("You can not upgrade this piece");
		}
		else
		{
			System.out.println(getName() + " Upgrade List: ");
			for(int i = 0; i < upgradeSize; i++)
			{
				System.out.print(i + ") ");
				System.out.println(availableUpgrades.get(i).getMovementName() + " - $" + availableUpgrades.get(i).getMovementCost() * getMaterialWorth());
			}
		}
	}

	/**
	 * Method to upgrade the piece. The movement chosen by the user is added to the piece movement arraylist, then
	 * that movement is removed from availableUpgrades so the upgrade cannot be purchased again.
	 * @param choice - int, index of available upgrade arraylist.
	 */
	public void upgrade(int choice)
	{
		getMovements().add(availableUpgrades.get(choice));
		availableUpgrades.remove(choice);
	}
}
