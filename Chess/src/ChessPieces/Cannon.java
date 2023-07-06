package ChessPieces;

import Movements.*;
import java.util.ArrayList;

/**
 * Cannon Chess Piece
 */
public class Cannon extends ChessPiece
{
	/**
	 * Piece Name
	 */
	public static String name = "Cannon";
	
	/**
	 * How much the piece is worth
	 */
	public static int materialWorth = 5;

	/**
	 * Class Constructor.
	 * @param posRow - int, row position.
	 * @param posCol - int, column position.
	 * @param color - String, piece color.
	 * @param image - char, piece symbol.
	 */
	public Cannon(int posRow, int posCol, String color, char image){
		super(posRow, posCol, color, image);
		getMovements().add(new HopCaptureMovement());
		getMovements().add(new OrthogonalNonCaptureMovement());

		setUpgrades();
	}

	/**
	 * Copy Constructor.
	 * @param source - ChessPiece object to be copied.
	 */
	public Cannon(ChessPiece source){
		super(source);
	}

	/**
	 * Method to get the materialWorth of the piece.
	 * @return materialWorth - int
	 */
	public int getMaterialWorth() {
		return materialWorth;
	}

	/**
	 * Method to get the piece name.
	 * @return name - String
	 */
	public String getName() {
		return name;
	}

	/**
	 * Method to set the available upgrades of a Bishop when the piece is created.
	 */
	private void setUpgrades(){
		ArrayList<Movement> cannonUpgrades = getAvailableUpgrades();

		cannonUpgrades.add(new SquareMovement());
		cannonUpgrades.add(new DiagonalMovement());
		cannonUpgrades.add(new OrthogonalMovement());
		cannonUpgrades.add(new LMovement());
		cannonUpgrades.add(new RangeCaptureMovement());
		cannonUpgrades.add(new TeleportationMovement());
		cannonUpgrades.add(new RestrictedTeleportationMovement());
		cannonUpgrades.add(new LeftRightCaptureMovement());
	}
}
