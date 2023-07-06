package Players;

import ChessGameClasses.Board;
import ChessPieces.*;
import Items.Item;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Human Player extends Player and it takes input instead of having them calculated.
 */
public class HumanPlayer extends Player
{
	/**
	 * Three parameter constructor of HumanPlayer.
	 * It takes the three parameter and call the Player three-parameter constructor.
	 * 
	 * @param chessPieces ArrayList of Chess Pieces that the Player owns.
	 * @param color The team color of the Player.
	 * @param money The initial amount of money that the Player gets.
	 */
	public HumanPlayer(ArrayList<ChessPiece> chessPieces, String color, int money)
	{
		super(chessPieces, color, money);
	}
	
	@Override
	/**
	 * Take text-based input for decision. 
	 */
	public ArrayList<Integer> getInputTextBasedVersion(ChessPiece selectedChessPiece, Board board, ArrayList<Item> items)
	{
		ArrayList<Integer> input = new ArrayList<Integer>();
		int inputRow;
		int inputColumn = -1;
		
		inputRow = takeIntegerInput("Input The Row: ", -2, Board.rowNum);
		
		if (inputRow == -1) //BUY ITEMS
		{
			if (!board.isInCheck(getColor()))
				inputColumn = takeIntegerInput("Item Index: ", 0, items.size());
		}
		else if (inputRow == -2) //UPGRADES
		{
			if (!board.isInCheck(getColor()) && selectedChessPiece != null && selectedChessPiece.getAvailableUpgrades().size() != 0)
				inputColumn = takeIntegerInput("Upgrade Index: ", 0, selectedChessPiece.getAvailableUpgrades().size());
		}
		else //BOARD MOVEMENT
		{
			//Input
			inputColumn = takeIntegerInput("Input The Column: ", 0, Board.colNum);
		}
		
		input.add(inputRow);
		input.add(inputColumn);
		return input;
	}
	
	@Override
	/**
	 * Take text-based input for promotion.
	 */
	public ArrayList<Integer> getPromotionInputTextBasedVersion()
	{
		ArrayList<Integer> input = new ArrayList<Integer>();
		int inputRow = takeIntegerInput("Input The Row: ", -3, -2);
		int inputColumn = -1;
		
		if (inputRow == -3) //BUY ITEMS
			inputColumn = takeIntegerInput("Promotion Index: ", 0, 7);
		
		input.add(inputRow);
		input.add(inputColumn);
		return input;
	}
	
	/**
	 * Take input from the console. It handles exceptions of out of bound inputs and inputs of wrong formats.
	 * 
	 * Lower bound inclusive, upperbound exclusive
	 * 
	 * @param prompt
	 * @param lowerbound
	 * @param upperbound
	 * @return
	 */
	public Integer takeIntegerInput(String prompt, int lowerbound, int upperbound)
	{
		Scanner scanner = new Scanner(System.in);
			
		//Take an input. Ignore out of bound and non integer inputs.
		int input;
		do 
		{
			System.out.print(prompt);
			try
			{
				input = scanner.nextInt();	
				scanner.nextLine(); //Remove the "\n".
			} 
			catch(InputMismatchException e) //Catch exception for non integer inputs.
			{
				input = lowerbound - 1; //Out of bound to restart loop. 
				scanner.nextLine(); //Remove non integer inputs.
			}
		} while (input < lowerbound || input >= upperbound); //Try again if input was out of bound.
		return input;
	}
}