package ChessPieces;

import Movements.*;
import java.util.ArrayList;

/**
 * Ninja Chess Piece
 */
public class Ninja extends ChessPiece
{
	/**
	 * Piece Name
	 */
	public static String name = "Ninja";
	
	/**
	 * How much the piece is worth
	 */
	public static int materialWorth = 7;

	/**
	 * Class Constructor.
	 * @param posRow - int, row position.
	 * @param posCol - int, column position.
	 * @param color - String, piece color.
	 * @param image - char, piece symbol.
	 */
	public Ninja(int posRow, int posCol, String color, char image){
		super(posRow, posCol, color, image);
		getMovements().add(new RestrictedTeleportationMovement());

		setUpgrades();
	}

	/**
	 * Copy Constructor.
	 * @param source - ChessPiece object to be copied.
	 */
	public Ninja(ChessPiece source){
		super(source);
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
	 * Method to set the available upgrades of a Bishop when the piece is created.
	 */
	private void setUpgrades(){
		ArrayList<Movement> ninjaUpgrades = getAvailableUpgrades();

		ninjaUpgrades.add(new SquareMovement());
		ninjaUpgrades.add(new DiagonalMovement());
		ninjaUpgrades.add(new OrthogonalMovement());
		ninjaUpgrades.add(new LMovement());
		ninjaUpgrades.add(new HopCaptureMovement());
		ninjaUpgrades.add(new RangeCaptureMovement());
		ninjaUpgrades.add(new TeleportationMovement());
		ninjaUpgrades.add(new LeftRightCaptureMovement());

	}
}
