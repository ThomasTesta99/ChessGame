package ChessPieces;

import Movements.*;
import java.util.ArrayList;

/**
 * Archer Chess Piece
 */
public class Archer extends ChessPiece
{
	/**
	 * Piece Name
	 */
	public static String name = "Archer";
	
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
	public Archer(int posRow, int posCol, String color, char image){
		super(posRow, posCol, color, image);
		getMovements().add(new AdvanceMovement());
		getMovements().add(new RangeCaptureMovement());

		setUpgrades();
	}

	/**
	 * Copy Constructor.
	 * @param source - ChessPiece object to be copied.
	 */
	public Archer(ChessPiece source){
		super(source);
	}

	/**
	 * Method to get the materialWorth of the piece.
	 * @return materialWorth - int
	 */
	public int getMaterialWorth(){
		return materialWorth;
	}

	/**
	 * Method to get the piece name.
	 * @return name - String
	 */
	public String getName(){
		return name;
	}

	/**
	 * Method to set the available upgrades of an Archer when the piece is created.
	 */
	private void setUpgrades(){
		ArrayList<Movement> archerUpgrade = getAvailableUpgrades();

		archerUpgrade.add(new SquareMovement());
		archerUpgrade.add(new DiagonalMovement());
		archerUpgrade.add(new OrthogonalMovement());
		archerUpgrade.add(new LMovement());
		archerUpgrade.add(new HopCaptureMovement());
		archerUpgrade.add(new TeleportationMovement());
		archerUpgrade.add(new RestrictedTeleportationMovement());
		archerUpgrade.add(new LeftRightCaptureMovement());

	}
}
