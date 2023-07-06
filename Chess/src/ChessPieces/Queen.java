package ChessPieces;

import Movements.*;
import java.util.ArrayList;

/**
 * Queen Chess Piece
 */
public class Queen extends ChessPiece
{
	/**
	 * Piece Name
	 */
	public static String name = "Queen";
	
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
	public Queen(int posRow, int posCol, String color, char image){
		super(posRow, posCol, color, image);
		getMovements().add(new DiagonalMovement());
		getMovements().add(new OrthogonalMovement());

		setUpgrades();
	}

	/**
	 * Copy Constructor.
	 * @param source - ChessPiece object to be copied.
	 */
	public Queen(ChessPiece source){
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
		ArrayList<Movement> queenUpgrade = getAvailableUpgrades();

		queenUpgrade.add(new HopCaptureMovement());
		queenUpgrade.add(new LMovement());
		queenUpgrade.add(new RangeCaptureMovement());
		queenUpgrade.add(new RestrictedTeleportationMovement());
		queenUpgrade.add(new TeleportationMovement());
	}
}
