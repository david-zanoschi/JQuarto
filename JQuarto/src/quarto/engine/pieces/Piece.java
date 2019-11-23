package quarto.engine.pieces;

import java.util.ArrayList;
import java.util.List;

public class Piece 
{
	public final static List<Piece> ALL_PIECES = createAllPieces();
	
	protected int piecePosition;
	protected final PieceType pieceType;
	protected final Integer pieceNumber;
	protected boolean isChosen = false;
	protected boolean isPlaced = false;
	
	Piece(final PieceType pieceType, final Integer pieceNumber)
	{
		this.pieceType = pieceType;
		this.pieceNumber = pieceNumber;
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
	
	public int getPiecePosition()
	{
		return this.piecePosition;
	}
	
	public PieceType getType()
	{
		return this.pieceType;
	}
	
	public String getPieceNumberToString()
	{
		return this.pieceNumber.toString();
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
		CHST(PieceColor.LIGHT, PieceHeight.TALL, PieceShape.ROUND, PieceTopper.FLAT),
		ChST(PieceColor.LIGHT, PieceHeight.SHORT, PieceShape.ROUND, PieceTopper.FLAT),
		CHsT(PieceColor.LIGHT, PieceHeight.TALL, PieceShape.SQUARE, PieceTopper.FLAT),
		CHSt(PieceColor.LIGHT, PieceHeight.TALL, PieceShape.ROUND, PieceTopper.HOLE),
		ChsT(PieceColor.LIGHT, PieceHeight.SHORT, PieceShape.SQUARE, PieceTopper.FLAT),
		CHst(PieceColor.LIGHT, PieceHeight.TALL, PieceShape.SQUARE, PieceTopper.HOLE),
		ChSt(PieceColor.LIGHT, PieceHeight.SHORT, PieceShape.ROUND, PieceTopper.HOLE),
		Chst(PieceColor.LIGHT, PieceHeight.SHORT, PieceShape.SQUARE, PieceTopper.HOLE),
		cHST(PieceColor.DARK, PieceHeight.TALL, PieceShape.ROUND, PieceTopper.FLAT),
		chST(PieceColor.DARK, PieceHeight.SHORT, PieceShape.ROUND, PieceTopper.FLAT),
		cHsT(PieceColor.DARK, PieceHeight.TALL, PieceShape.SQUARE, PieceTopper.FLAT),
		cHSt(PieceColor.DARK, PieceHeight.TALL, PieceShape.ROUND, PieceTopper.HOLE),
		chsT(PieceColor.DARK, PieceHeight.SHORT, PieceShape.SQUARE, PieceTopper.FLAT),
		cHst(PieceColor.DARK, PieceHeight.TALL, PieceShape.SQUARE, PieceTopper.HOLE),
		chSt(PieceColor.DARK, PieceHeight.SHORT, PieceShape.ROUND, PieceTopper.HOLE),
		chst(PieceColor.DARK, PieceHeight.SHORT, PieceShape.SQUARE, PieceTopper.HOLE);
		
		private PieceColor pieceColor;
		private PieceHeight pieceHeight;
		private PieceShape pieceShape;
		private PieceTopper pieceTopper;
		
		private PieceType(	final PieceColor pieceColor,
							final PieceHeight pieceHeight,
							final PieceShape pieceShape,
							final PieceTopper pieceTopper) 
		{
			this.pieceColor = pieceColor;
			this.pieceHeight = pieceHeight;
			this.pieceShape = pieceShape;
			this.pieceTopper = pieceTopper;
		}
		
		public PieceColor getColor()
		{
			return this.pieceColor;
		}
		
		public PieceHeight getHeight()
		{
			return this.pieceHeight;
		}
		
		public PieceShape getShape()
		{
			return this.pieceShape;
		}
		
		public PieceTopper getTopper()
		{
			return this.pieceTopper;
		}
	}
	
	public enum PieceColor
	{
		LIGHT("C"),
		DARK("c");
		
		private String color;
		
		private PieceColor(final String color)
		{
			this.color = color;
		}
		
		@Override
		public String toString()
		{
			return this.color;
		}
		
		public boolean equals(PieceColor pieceColor)
		{
			if(this.color == pieceColor.toString())
			{
				return true;
			}
			return false;
		}
	}
	
	public enum PieceHeight
	{
		TALL("H"),
		SHORT("h");
		
		private String height;
		
		private PieceHeight(final String height)
		{
			this.height = height;
		}
		
		@Override
		public String toString() 
		{
			return this.height;
		}
		
		public boolean equals(PieceHeight pieceHeight)
		{
			if(this.height == pieceHeight.toString())
			{
				return true;
			}
			return false;
		}
	}
	
	public enum PieceShape
	{
		ROUND("S"),
		SQUARE("s");
		
		private String shape;
		
		private PieceShape(final String shape)
		{
			this.shape = shape;
		}
		
		@Override
		public String toString() 
		{
			return this.shape;
		}
		
		public boolean equals(PieceShape pieceShape)
		{
			if(this.shape == pieceShape.toString())
			{
				return true;
			}
			return false;
		}
	}
	
	public enum PieceTopper
	{
		FLAT("T"),
		HOLE("t");
		
		private String topper;
		
		private PieceTopper(final String topper)
		{
			this.topper = topper;
		}
		
		@Override
		public String toString() 
		{
			return this.topper;
		}
		
		public boolean equals(PieceTopper pieceTopper)
		{
			if(this.topper == pieceTopper.toString())
			{
				return true;
			}
			return false;
		}
	}
}






















