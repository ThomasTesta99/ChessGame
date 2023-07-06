package Items;

/**
 * Basically Vector2
 */
public class IntPair 
{
	/**
	 * The first number(row).
	 */
    private int first;
    
    /**
     * The second number(column).
     */
    private int second; 

    /**
     * Class constructor.
     * @param first - int, row.
     * @param second - int, column.
     */
    public IntPair(int first, int second) 
    {
        this.first = first;
        this.second = second;
    }

    /**
     * Method to return the row position, or the first number of the pair.
     * @return - int, row.
     */
    public int getFirst() {
        return first;
    }

    /**
     * Method to return the column position, or the second number of the pair.
     * @return - int column.
     */
    public int getSecond() {
        return second;
    }

    /**
     * Method to set the first number.
     * @param first - int, first.
     */
    public void setFirst(int first) {
        this.first = first;
    }

    /**
     * Method to set the second number.
     * @param second - int, second.
     */
    public void setSecond(int second) {
        this.second = second;
    }
}