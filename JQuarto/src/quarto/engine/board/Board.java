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
					if(	isColorSameOnLine(piecesOnLine) ||
						isHeightSameOnLine(piecesOnLine) ||
						isShapeSameOnLine(piecesOnLine) ||
						isTopperSameOnLine(piecesOnLine))
					{
						return true;
					}
					return false;
				}
			}
		}
		
		return false;
	}
	
	private static boolean isColorSameOnLine(List<Piece> piecesOnLine)
	{
		if(	piecesOnLine.get(0).getType().getColor()
			.equals(piecesOnLine.get(1).getType().getColor()) &&
			piecesOnLine.get(0).getType().getColor()
			.equals(piecesOnLine.get(2).getType().getColor()) &&
			piecesOnLine.get(0).getType().getColor()
			.equals(piecesOnLine.get(3).getType().getColor()))
		{
			return true;
		}
		return false;
	}
	
	private static boolean isHeightSameOnLine(List<Piece> piecesOnLine)
	{
		if(	piecesOnLine.get(0).getType().getHeight()
				.equals(piecesOnLine.get(1).getType().getHeight()) &&
				piecesOnLine.get(0).getType().getHeight()
				.equals(piecesOnLine.get(2).getType().getHeight()) &&
				piecesOnLine.get(0).getType().getHeight()
				.equals(piecesOnLine.get(3).getType().getHeight()))
			{
				return true;
			}
			return false;
	}
	
	private static boolean isShapeSameOnLine(List<Piece> piecesOnLine)
	{
		if(	piecesOnLine.get(0).getType().getShape()
				.equals(piecesOnLine.get(1).getType().getShape()) &&
				piecesOnLine.get(0).getType().getShape()
				.equals(piecesOnLine.get(2).getType().getShape()) &&
				piecesOnLine.get(0).getType().getShape()
				.equals(piecesOnLine.get(3).getType().getShape()))
			{
				return true;
			}
			return false;
	}
	
	private static boolean isTopperSameOnLine(List<Piece> piecesOnLine)
	{
		if(	piecesOnLine.get(0).getType().getTopper()
				.equals(piecesOnLine.get(1).getType().getTopper()) &&
				piecesOnLine.get(0).getType().getTopper()
				.equals(piecesOnLine.get(2).getType().getTopper()) &&
				piecesOnLine.get(0).getType().getTopper()
				.equals(piecesOnLine.get(3).getType().getTopper()))
			{
				return true;
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


























