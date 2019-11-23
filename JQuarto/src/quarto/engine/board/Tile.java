package quarto.engine.board;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import quarto.engine.pieces.Piece;

public abstract class Tile 
{
	protected final int tileCoordinate;
	private static final Map<Integer, EmptyTile> ALL_EMPTY_TILES = createAllEmptyTiles();
	
	private Tile(final int tileCoordinate)
	{
		this.tileCoordinate = tileCoordinate;
	}
	
	public int getTileCoordinate()
	{
		return this.tileCoordinate;
	}
	
	private static Map<Integer, EmptyTile> createAllEmptyTiles()
	{
		final Map<Integer, EmptyTile> allEmptyTiles = new HashMap<>();
		
		for(int i = 0; i < 16; i++)
		{
			allEmptyTiles.put(i, new EmptyTile(i));
		}
		
		return Collections.unmodifiableMap(allEmptyTiles);
	}
	
	public static Tile createTile(final int tileCoordinate, final Piece piece)
	{
		return piece != null ? new OccupiedTile(tileCoordinate, piece) : ALL_EMPTY_TILES.get(tileCoordinate);
	}
	
	public abstract boolean isTileOccupied();
	public abstract Piece getPieceOnTile();
	
	public static final class EmptyTile extends Tile
	{
		private EmptyTile(final int tileCoordinate)
		{
			super(tileCoordinate);
		}

		@Override
		public boolean isTileOccupied() 
		{
			return false;
		}

		@Override
		public Piece getPieceOnTile() 
		{
			return null;
		}
	}
	
	public static final class OccupiedTile extends Tile
	{
		private final Piece pieceOnTile;
		
		OccupiedTile(final int tileCoordinate, final Piece pieceOnTile) 
		{
			super(tileCoordinate);
			this.pieceOnTile = pieceOnTile;
		}

		@Override
		public boolean isTileOccupied() 
		{
			return true;
		}

		@Override
		public Piece getPieceOnTile() 
		{
			return this.pieceOnTile;
		}
	}
}



























