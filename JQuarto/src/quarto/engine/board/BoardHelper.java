package quarto.engine.board;

import java.util.ArrayList;
import java.util.List;

public class BoardHelper 
{
	public static final int NUM_TILES_PER_LINE = 4;
	public static final int NUM_TILES = NUM_TILES_PER_LINE * NUM_TILES_PER_LINE;
	
	public static final int NUM_PIECES = 16;
	
	private static final Integer[] FIRST_ROW = computeRow(0);
	private static final Integer[] SECOND_ROW = computeRow(1);
	private static final Integer[] THIRD_ROW = computeRow(2);
	private static final Integer[] FOURTH_ROW = computeRow(3);
	
	private static final Integer[] FIRST_COLUMN = computeColumn(0);
	private static final Integer[] SECOND_COLUMN = computeColumn(1);
	private static final Integer[] THIRD_COLUMN = computeColumn(2);
	private static final Integer[] FOURTH_COLUMN = computeColumn(3);
	
	private static final Integer[] FIRST_DIAGONAL = computeDiagonal(0);
	private static final Integer[] SECOND_DIAGONAL = computeDiagonal(1);
	
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
		
		lines.add(FIRST_ROW);
		lines.add(SECOND_ROW);
		lines.add(THIRD_ROW);
		lines.add(FOURTH_ROW);
		
		lines.add(FIRST_COLUMN);
		lines.add(SECOND_COLUMN);
		lines.add(THIRD_COLUMN);
		lines.add(FOURTH_COLUMN);
		
		lines.add(FIRST_DIAGONAL);
		lines.add(SECOND_DIAGONAL);
		
		return lines;
	}
}


















