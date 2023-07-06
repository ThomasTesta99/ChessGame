package ChessPieces;

import Movements.*;
import java.util.ArrayList;

/**
 * Rook Chess Piece
 */
public class Rook extends ChessPiece
{
	/**
	 * Piece Name
	 */
	public static String name = "Rook";
	
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
	public Rook(int posRow, int posCol, String color, char image){
		super(posRow, posCol, color, image);
		getMovements().add(new OrthogonalMovement());

		setUpgrades();
	}

	/**
	 * Copy Constructor.
	 * @param source - ChessPiece object to be copied.
	 */
	public Rook(ChessPiece source){
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
		ArrayList<Movement> rookUpgrade = getAvailableUpgrades();

		rookUpgrade.add(new SquareMovement());
		rookUpgrade.add(new DiagonalMovement());
		rookUpgrade.add(new LMovement());
		rookUpgrade.add(new HopCaptureMovement());
		rookUpgrade.add(new RangeCaptureMovement());
		rookUpgrade.add(new TeleportationMovement());
		rookUpgrade.add(new RestrictedTeleportationMovement());
		rookUpgrade.add(new LeftRightCaptureMovement());
	}
}
