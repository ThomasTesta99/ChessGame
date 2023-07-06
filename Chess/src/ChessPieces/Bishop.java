package ChessPieces;

import Movements.*;
import java.util.ArrayList;

/**
 * Bishop Chess Piece
 */
public class Bishop extends ChessPiece
{
	/**
	 * Piece Name
	 */
	public static String name = "Bishop";
	
	/**
	 * How much the piece is worth
	 */
	public static int materialWorth = 3;

	/**
	 * Class Constructor.
	 * @param posRow - int, row position.
	 * @param posCol - int, column position.
	 * @param color - String, piece color.
	 * @param image - char, piece symbol.
	 */
	public Bishop(int posRow, int posCol, String color, char image){
		super(posRow, posCol, color, image);
		getMovements().add(new DiagonalMovement());

		setUpgrades();
	}

	/**
	 * Copy Constructor.
	 * @param source - ChessPiece object to be copied.
	 */
	public Bishop(ChessPiece source)
	{
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
		ArrayList<Movement> bishopUpgrades = getAvailableUpgrades();

		bishopUpgrades.add(new SquareMovement());
		bishopUpgrades.add(new OrthogonalMovement());
		bishopUpgrades.add(new LMovement());
		bishopUpgrades.add(new HopCaptureMovement());
		bishopUpgrades.add(new RangeCaptureMovement());
		bishopUpgrades.add(new TeleportationMovement());
		bishopUpgrades.add(new RestrictedTeleportationMovement());

	}
}
