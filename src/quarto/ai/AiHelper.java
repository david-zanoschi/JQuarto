package quarto.ai;

import quarto.engine.board.Board;
import quarto.engine.board.BoardHelper;
import quarto.engine.board.Tile;

public class AiHelper 
{
	private static String EMPTY_TILE_SYMBOL = "0";
	
	public static String getCurrentJQS(Board board) 
	{
		StringBuilder stringBuilder = new StringBuilder();
		
		int i = 0;
		for(final Tile tile : board.getGameBoard())
		{
			stringBuilder.append(	tile.isOccupied() ? 
									computeInput(tile.getPieceOnTile().getType().toString(), i) :
									computeInput(EMPTY_TILE_SYMBOL, i));
			++i;
		}
		stringBuilder.append("\n");
		
		return stringBuilder.toString();
	}
	
	private static String computeInput(String input, int i)
	{
		return i < BoardHelper.NUM_TILES - 1 ? input + "," : input;
	}
}
