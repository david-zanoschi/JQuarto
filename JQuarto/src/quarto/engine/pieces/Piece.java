package quarto.engine.pieces;

import java.util.ArrayList;
import java.util.List;

public class Piece 
{
	public final static List<Piece> ALL_PIECES = createAllPieces();
	
	protected int piecePosition;
	protected final PieceType pieceType;
	protected boolean isChosen = false;
	protected boolean isPlaced = false;
	
	Piece(final PieceType pieceType)
	{
		this.pieceType = pieceType;
	}
	
	private static List<Piece> createAllPieces()
	{
		List<Piece> allPieces = new ArrayList<>();
		
		// whites
		allPieces.add(new Piece(PieceType.CHST));
		allPieces.add(new Piece(PieceType.ChST));
		allPieces.add(new Piece(PieceType.CHSt));
		allPieces.add(new Piece(PieceType.ChSt));
		
		allPieces.add(new Piece(PieceType.CHsT));
		allPieces.add(new Piece(PieceType.ChsT));
		allPieces.add(new Piece(PieceType.CHst));
		allPieces.add(new Piece(PieceType.Chst));
		
		// blacks
		allPieces.add(new Piece(PieceType.cHST));
		allPieces.add(new Piece(PieceType.cHSt));
		allPieces.add(new Piece(PieceType.chST));
		allPieces.add(new Piece(PieceType.chSt));

		allPieces.add(new Piece(PieceType.cHsT));
		allPieces.add(new Piece(PieceType.cHst));
		allPieces.add(new Piece(PieceType.chsT));
		allPieces.add(new Piece(PieceType.chst));
		
		return allPieces;
	}
	
	public int getPiecePosition()
	{
		return this.piecePosition;
	}
	
	public PieceType getPieceType()
	{
		return this.pieceType;
	}
	
	public boolean isChosen()
	{
		return this.isChosen;
	}
	
	public boolean isPlaced()
	{
		return this.isPlaced;
	}
	
	public void choosePiece()
	{
		this.isChosen = true;
	}
	
	public void placePiece(int positionCoordinate)
	{
		this.piecePosition = positionCoordinate;
		this.isPlaced = true;
		this.isChosen = false;
	}
	
	public enum PieceType
	{
		CHST("CHST"),
		ChST("ChST"),
		CHsT("CHsT"),
		CHSt("CHSt"),
		ChsT("ChsT"),
		CHst("CHst"),
		ChSt("ChSt"),
		Chst("Chst"),
		cHST("cHST"),
		chST("chST"),
		cHsT("cHsT"),
		cHSt("cHSt"),
		chsT("chsT"),
		cHst("cHst"),
		chSt("chSt"),
		chst("chst");
		
		private String pieceName;
		
		private PieceType(final String pieceName) 
		{
			this.pieceName = pieceName;
		}
		
		@Override
		public String toString()
		{
			return this.pieceName;
		}
	}
}






















