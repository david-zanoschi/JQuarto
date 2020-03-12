package quarto.engine.board;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import quarto.engine.pieces.Piece;
import quarto.engine.pieces.Piece.PieceType;

public class Board 
{
	public static final  List<String> PIECES_NUMBERS_STRINGS = getAllPiecesNumbersAsStrings();

	public List<Piece> allPieces;
	private List<Piece> placedPieces;
	private	List<Piece> remainingPieces;
	private List<Tile> gameBoard;
	
	private static boolean isFirstPlayer = true;
	private Piece chosenPiece = null;
	
	Board(Builder builder)
	{
		this.gameBoard = computeGameBoard(builder);
		this.allPieces = computeAllPieces();
		this.placedPieces = computePlacedPieces();
		this.remainingPieces = computeRemainingPieces();
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
		return this.chosenPiece;
	}
	
	public void setChosenPiece()
	{
		for(Piece piece : this.remainingPieces)
		{
			if(piece != null && piece.isChosen() && !piece.isPlaced())
			{
				this.chosenPiece = piece;
				break;
			}
		}
	}
	
	private static List<Piece> createAllPieces()
	{
		List<Piece> allPieces = new ArrayList<>();
		
		// whites
		allPieces.add(new Piece(PieceType.CHST, 0));
		allPieces.add(new Piece(PieceType.CHSt, 1));
		allPieces.add(new Piece(PieceType.ChST, 2));
		allPieces.add(new Piece(PieceType.ChSt, 3));
		
		allPieces.add(new Piece(PieceType.CHsT, 4));
		allPieces.add(new Piece(PieceType.CHst, 5));
		allPieces.add(new Piece(PieceType.ChsT, 6));
		allPieces.add(new Piece(PieceType.Chst, 7));
		
		// blacks
		allPieces.add(new Piece(PieceType.cHST, 8));
		allPieces.add(new Piece(PieceType.cHSt, 9));
		allPieces.add(new Piece(PieceType.chST, 10));
		allPieces.add(new Piece(PieceType.chSt, 11));

		allPieces.add(new Piece(PieceType.cHsT, 12));
		allPieces.add(new Piece(PieceType.cHst, 13));
		allPieces.add(new Piece(PieceType.chsT, 14));
		allPieces.add(new Piece(PieceType.chst, 15));
		
		return allPieces;
	}
	
	private static List<String> getAllPiecesNumbersAsStrings()
	{
		List<String> result = new ArrayList<>();
		
		for(Piece piece : createAllPieces())
		{
			result.add(piece.getPieceNumberAsString());
		}
		
		return result;
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

		return placedPieces.size() == this.allPieces.size() && !this.isGameOver();
	}
	
	private List<Tile> computeGameBoard(final Builder builder)
	{
		final Tile[] tiles = new Tile[BoardHelper.NUM_TILES];
		
		for(int i = 0; i < BoardHelper.NUM_TILES; i++)
		{
			tiles[i] = Tile.create(i, builder.boardConfiguration.get(i));
		}
		
		return List.of(tiles);
	}

	private List<Piece> computeAllPieces()
	{
		List<Piece> result = createAllPieces();

		for(int i = 0; i < BoardHelper.NUM_TILES; i++)
		{
			Tile ithTile = this.gameBoard.get(i);

			if(ithTile.isOccupied())
			{
				result.set(ithTile.getPieceOnTile().getPieceNumber(), this.gameBoard.get(i).getPieceOnTile());
			}
		}

		return result;
	}
	
	private List<Piece> computePlacedPieces()
	{
		List<Piece> placedPieces = new ArrayList<>();
		
		for(final Piece piece : this.allPieces)
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
		List<Piece> remainingPieces = new ArrayList<>();

		for(final Piece piece : this.allPieces)
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

		public void reset()
		{
			this.boardConfiguration = new HashMap<>();
		}
		
		public Board build()
		{
			return new Board(this);
		}
	}
}


























