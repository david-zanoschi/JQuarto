package quarto.engine.board;

import java.util.ArrayList;
import java.util.List;

public class BoardHelper 
{
	public static final int NUM_TILES_PER_LINE = 4;
	public static final int NUM_TILES = NUM_TILES_PER_LINE * NUM_TILES_PER_LINE;
	public static final int NUM_PIECES = 16;
	public static final List<Integer[]> BOARD_LINES = computeBoardLines();
	
	private static Integer[] computeRow(final int rowNumber)
	{
		Integer[] row = new Integer[NUM_TILES_PER_LINE];
		
		for(int i = 0; i < NUM_TILES_PER_LINE; i++)
		{
			row[i] = rowNumber * NUM_TILES_PER_LINE + i;
		}
		
		return row;
	}
	
	private static Integer[] computeColumn(final int columnNumber)
	{
		Integer[] column = new Integer[NUM_TILES_PER_LINE];
		
		for(int i = 0; i < NUM_TILES_PER_LINE; i++)
		{
			column[i] = i * NUM_TILES_PER_LINE + columnNumber;
		}
		
		return column;
	}
	
	private static Integer[] computeDiagonal(final int diagonalNumber)
	{
		Integer[] diagonal = new Integer[NUM_TILES_PER_LINE];
		
		int startValue = diagonalNumber == 0 ? 3 : 0;
		int increment = diagonalNumber == 0 ? 3 : 5;
		
		for(int i = 0; i < NUM_TILES_PER_LINE; i++)
		{
			diagonal[i] = startValue;
			startValue += increment;
		}
		
		return diagonal;
	}
	
	private static List<Integer[]> computeBoardLines()
	{
		List<Integer[]> lines = new ArrayList<Integer[]>();
		
		for(int i = 0; i < NUM_TILES_PER_LINE; i++)
		{
			lines.add(computeRow(i));
			lines.add(computeColumn(i));
		}
		
		lines.add(computeDiagonal(0));
		lines.add(computeDiagonal(1));
		
		return lines;
	}
}


















