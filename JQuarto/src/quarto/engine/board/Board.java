package quarto.engine.board;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import quarto.engine.pieces.Piece;

public class Board 
{
	private List<Piece> placedPieces;
	private	List<Piece> remainingPieces;
	private List<Tile> gameBoard;
	
	private static boolean isFirstPlayer = true;
	private static Piece chosenPiece = null;
	
	Board(Builder builder)
	{
		this.placedPieces = computePlacedPieces();
		this.remainingPieces = computeRemainingPieces();
		this.gameBoard = computeGameBoard(builder);
	}
	
	public boolean isFirstPlayer()
	{
		return isFirstPlayer;
	}
	
	public String getCurrentPlayerString() 
	{
		return isFirstPlayer ? "1" : "2";
	}
	
	public void nextPlayer() 
	{
		isFirstPlayer = !isFirstPlayer;
	}
	
	public void setFirstPlayer() 
	{
		isFirstPlayer = true;
	}
	
	public List<Piece> getPlacedPieces()
	{
		return this.placedPieces;
	}
	
	public List<Piece> getRemainingPieces()
	{
		return this.remainingPieces;
	}
	
	public List<Tile> getGameBoard()
	{
		return this.gameBoard;
	}
	
	public Tile getTile(int tileCoordinate)
	{
		return this.gameBoard.get(tileCoordinate);
	}
	
	public Piece getChosenPiece() 
	{
		return chosenPiece;
	}
	
	public void setChosenPiece()
	{
		for(Piece piece : this.remainingPieces)
		{
			if(piece != null && piece.isChosen() && !piece.isPlaced())
			{
				chosenPiece = piece;
			}
		}
	}
	
	public Board update()
	{
		Builder builder = new Builder();
		
		this.placedPieces = computePlacedPieces();
		this.remainingPieces = computeRemainingPieces();
		
		for(Piece piece : this.placedPieces)
		{
			builder.setPiece(piece);
		}
		
		return builder.build();
	}
	
	public boolean isGameOver()
	{
		for(final Integer[] line : BoardHelper.BOARD_LINES)
		{
			List<Piece> piecesOnLine = new ArrayList<>();

			for(final Integer coordinate : line)
			{
				Piece pieceOnTile = this.getTile(coordinate).getPieceOnTile();

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
						if(	piecesOnLine.get(0).getType().toString().charAt(i) ==
							piecesOnLine.get(1).getType().toString().charAt(i) &&
							piecesOnLine.get(0).getType().toString().charAt(i) ==
							piecesOnLine.get(2).getType().toString().charAt(i) &&
							piecesOnLine.get(0).getType().toString().charAt(i) ==
							piecesOnLine.get(3).getType().toString().charAt(i))
						{
							return true;
						}
					}
				}
			}
		}

		return false;
	}
	
	public boolean isDraw()
	{
		List<Piece> placedPieces = this.computePlacedPieces();
		
		if(placedPieces.size() == Piece.ALL_PIECES.size() && !this.isGameOver())
		{
			return true;
		}
		
		return false;
	}
	
	private List<Tile> computeGameBoard(final Builder builder)
	{
		final Tile[] tiles = new Tile[BoardHelper.NUM_TILES];
		
		for(int i = 0; i < BoardHelper.NUM_TILES; i++)
		{
			tiles[i] = Tile.create(i, builder.boardConfiguration.get(i));
		}
		
		return Collections.unmodifiableList(Arrays.asList(tiles));
	}
	
	private List<Piece> computePlacedPieces()
	{
		List<Piece> placedPieces = new ArrayList<Piece>();
		
		for(final Piece piece : Piece.ALL_PIECES)
		{
			if(piece.isPlaced())
			{
				placedPieces.add(piece);
			}
		}
		
		return placedPieces;
	}
	
	private List<Piece> computeRemainingPieces()
	{
		List<Piece> remainingPieces = new ArrayList<Piece>();
		
		for(final Piece piece : Piece.ALL_PIECES)
		{
			if(!piece.isPlaced())
			{
				remainingPieces.add(piece);
			}
			else 
			{
				remainingPieces.add(null);
			}
		}
		
		return remainingPieces;
	}
	
	public static class Builder
	{
		Map<Integer, Piece> boardConfiguration;
		
		public Builder()
		{
			this.boardConfiguration = new HashMap<>();
		}
		
		public Builder setPiece(final Piece piece)
		{
			this.boardConfiguration.put(piece.getPosition(), piece);
			return this;
		}
		
		public Board build()
		{
			return new Board(this);
		}
		
		public void reset() 
		{
			for(final Piece piece : Piece.ALL_PIECES) 
			{
				piece.reset();
			}
		}
	}
}


























