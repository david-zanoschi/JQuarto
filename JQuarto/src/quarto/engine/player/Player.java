package quarto.engine.player;

import java.util.ArrayList;
import java.util.List;

import quarto.engine.board.Board;
import quarto.engine.board.BoardHelper;
import quarto.engine.pieces.Piece;

public class Player 
{
	protected final Board board;
	private final boolean isWinner = false;
	
	Player(final Board board)
	{
		this.board = board;
	}
	
	public boolean isWinner()
	{
		return this.isWinner;
	}
	
	protected boolean hasWon()
	{
		for(final Integer[] line : BoardHelper.BOARD_LINES)
		{
			for(final Integer coordinate : line)
			{
				List<Piece> piecesOnLine = new ArrayList<>();
				Piece pieceOnTile = board.getTile(coordinate).getPieceOnTile();
				
				if(pieceOnTile != null)
				{ 
					piecesOnLine.add(pieceOnTile);
				}
				else
				{
					break;
				}
				
				if(piecesOnLine.size() == BoardHelper.NUM_TILES_PER_LINE)
				{
					for(int i = 0; i < 4; i++)
					{
						if(	piecesOnLine.get(0).getPieceType().toString().charAt(i) ==
							piecesOnLine.get(1).getPieceType().toString().charAt(i) &&
							piecesOnLine.get(0).getPieceType().toString().charAt(i) ==
							piecesOnLine.get(2).getPieceType().toString().charAt(i) &&
							piecesOnLine.get(0).getPieceType().toString().charAt(i) ==
							piecesOnLine.get(3).getPieceType().toString().charAt(i))
						{
							return true;
						}
					}
				}
			}
		}
		
		return false;
	}
}





















