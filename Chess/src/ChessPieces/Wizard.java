package ChessPieces;

import Movements.*;
import java.util.ArrayList;

/**
 * Wizard Chess Piece
 */
public class Wizard extends ChessPiece
{
	/**
	 * Piece Name
	 */
	public static String name = "Wizard";
	
	/**
	 * How much the piece is worth
	 */
	public static int materialWorth = 9;

	/**
	 * Class Constructor.
	 * @param posRow - int, row position.
	 * @param posCol - int, column position.
	 * @param color - String, piece color.
	 * @param image - char, piece symbol.
	 */
	public Wizard(int posRow, int posCol, String color, char image){
		super(posRow, posCol, color, image);
		getMovements().add(new SquareMovement());
		getMovements().add(new TeleportationMovement());

		setUpgrades();
	}

	/**
	 * Copy Constructor.
	 * @param source - ChessPiece object to be copied.
	 */
	public Wizard(ChessPiece source){
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
		ArrayList<Movement> wizardUpgrades = getAvailableUpgrades();

		wizardUpgrades.add(new HopCaptureMovement());
		wizardUpgrades.add(new RangeCaptureMovement());
	}
}
