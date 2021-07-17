package quarto.engine.board;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import quarto.engine.pieces.Piece;

public abstract class Tile {
	private static final Map<Integer, EmptyTile> ALL_EMPTY_TILES = createAllEmptyTiles();
	protected final int tileCoordinate;

	private Tile(final int tileCoordinate) {
		this.tileCoordinate = tileCoordinate;
	}

	public int getCoordinate() {
		return this.tileCoordinate;
	}

	public static Tile create(final int tileCoordinate, final Piece piece) {
		return piece != null ? new OccupiedTile(tileCoordinate, piece) : ALL_EMPTY_TILES.get(tileCoordinate);
	}

	private static Map<Integer, EmptyTile> createAllEmptyTiles() {
		final Map<Integer, EmptyTile> allEmptyTiles = new HashMap<>();

		for (int i = 0; i < 16; i++) {
			allEmptyTiles.put(i, new EmptyTile(i));
		}

		return Collections.unmodifiableMap(allEmptyTiles);
	}

	public abstract boolean isOccupied();

	public abstract Piece getPieceOnTile();

	public static final class EmptyTile extends Tile {
		private EmptyTile(final int tileCoordinate) {
			super(tileCoordinate);
		}

		@Override
		public boolean isOccupied() {
			return false;
		}

		@Override
		public Piece getPieceOnTile() {
			return null;
		}
	}

	public static final class OccupiedTile extends Tile {
		private final Piece pieceOnTile;

		private OccupiedTile(final int tileCoordinate, final Piece pieceOnTile) {
			super(tileCoordinate);
			this.pieceOnTile = pieceOnTile;
		}

		@Override
		public boolean isOccupied() {
			return true;
		}

		@Override
		public Piece getPieceOnTile() {
			return this.pieceOnTile;
		}
	}
}
