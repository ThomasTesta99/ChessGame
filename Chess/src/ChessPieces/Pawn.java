package ChessPieces;

import Movements.*;
import java.util.ArrayList;

/**
 * Pawn Chess Piece
 */
public class Pawn extends ChessPiece
{
	/**
	 * Piece Name
	 */
	public static String name = "Pawn";
	
	/**
	 * How much the piece is worth
	 */
	public static int materialWorth = 1;
	
	/**
	 * Keep track of whether the Pawn moved Two Units Up last turn.
	 */
	private boolean movedTwoUnitsUp;

	/**
	 * Class Constructor.
	 * @param posRow - int, row position.
	 * @param posCol - int, column position.
	 * @param color - String, piece color.
	 * @param image - char, piece symbol.
	 */
	public Pawn(int posRow, int posCol, String color, char image){
		super(posRow, posCol, color, image);
		getMovements().add(new AdvanceMovement());
		getMovements().add(new LeftRightCaptureMovement());
		getMovements().add(new TwoUnitsUpMovement());
		getMovements().add(new EnPassantMovement());

		setMovedTwoUnitsUp(false);
		setUpgrades();
	}

	/**
	 * Copy Constructor.
	 * @param source - ChessPiece object to be copied.
	 */
	public Pawn(ChessPiece source){
		super(source);
		setMovedTwoUnitsUp(false);
	}

	/**
	 * Method to get the materialWorth of the piece.
	 * @return materialWorth - int
	 */
	public int getMaterialWorth() { return materialWorth; }

	/**
	 * Method to get the piece name.
	 * @return name - String
	 */
	public String getName() { return name; }

	/**
	 * Method to get whether the Pawn has moved two units up.
	 * @return movedTwoUnitsUp - boolean
	 */
	public boolean getMovedTwoUnitsUp() { return movedTwoUnitsUp; }

	/**
	 * Set if the Pawn has moved two units up
	 * @param movedTwoUnitsUp - boolean, whether the Pawn has moved two units up
	 */
	public void setMovedTwoUnitsUp(boolean movedTwoUnitsUp) { this.movedTwoUnitsUp = movedTwoUnitsUp; }

	/**
	 * Method to set the available upgrades of a Bishop when the piece is created.
	 */
	private void setUpgrades(){
		ArrayList<Movement> pawnUpgrades = getAvailableUpgrades();

		pawnUpgrades.add(new SquareMovement());
		pawnUpgrades.add(new DiagonalMovement());
		pawnUpgrades.add(new OrthogonalMovement());
		pawnUpgrades.add(new LMovement());
		pawnUpgrades.add(new HopCaptureMovement());
		pawnUpgrades.add(new RangeCaptureMovement());
		pawnUpgrades.add(new TeleportationMovement());
		pawnUpgrades.add(new RestrictedTeleportationMovement());
	}
}
