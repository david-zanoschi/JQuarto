package quarto.engine.board;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import quarto.engine.pieces.Piece;

public class Board 
{
	private List<Tile> gameBoard;
	private List<Integer> legalPositions;
	
	private List<Piece> placedPieces;
	private	List<Piece> remainingPieces;
	
	Board(Builder builder)
	{
		this.remainingPieces = computeRemainingPieces();
		this.placedPieces = computePlacedPieces();
		this.gameBoard = computeGameBoard(builder);
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
		
		//builder.setPiece(this.lastPlayedPiece);
		//lastPlayedPiece = null;
		
		return builder.build();
	}
	
	public List<Tile> getGameBoard()
	{
		return this.gameBoard;
	}
	
	public Collection<Integer> getLegalPositions()
	{
		return this.legalPositions;
	}
	
	public List<Piece> getPlacedPieces()
	{
		return this.placedPieces;
	}
	
	public List<Piece> getRemainingPieces()
	{
		return this.remainingPieces;
	}
	
	public Tile getTile(int tileCoordinate)
	{
		return this.gameBoard.get(tileCoordinate);
	}
	
	private List<Tile> computeGameBoard(final Builder builder)
	{
		final Tile[] tiles = new Tile[BoardHelper.NUM_TILES];
		
		for(int i = 0; i < BoardHelper.NUM_TILES; i++)
		{
			tiles[i] = Tile.createTile(i, builder.boardConfiguration.get(i));
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
	
	public Piece computeChosenPiece()
	{
		for(Piece piece : this.remainingPieces)
		{
			if(piece != null && piece.isChosen() && !piece.isPlaced())
			{
				return piece;
			}
		}
		
		return null;
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
	
	public static class Builder
	{
		Map<Integer, Piece> boardConfiguration;
		
		public Builder()
		{
			this.boardConfiguration = new HashMap<>();
		}
		
		public Builder setPiece(final Piece piece)
		{
			this.boardConfiguration.put(piece.getPiecePosition(), piece);
			return this;
		}
		
		public Board build()
		{
			return new Board(this);
		}
	}
}


























