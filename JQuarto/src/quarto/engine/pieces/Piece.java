package quarto.engine.pieces;

public class Piece 
{	
	protected int piecePosition;
	protected final PieceType pieceType;
	protected final Integer pieceNumber;
	protected boolean isChosen = false;
	protected boolean isPlaced = false;
	
	public Piece(final PieceType pieceType, final Integer pieceNumber)
	{
		this.pieceType = pieceType;
		this.pieceNumber = pieceNumber;
	}
	
	public int getPosition()
	{
		return this.piecePosition;
	}
	
	public PieceType getType()
	{
		return this.pieceType;
	}
	
	public String getPieceNumberAsString()
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
	
	public void choose()
	{
		this.isChosen = true;
	}
	
	public void place(int positionCoordinate)
	{
		this.piecePosition = positionCoordinate;
		this.isPlaced = true;
		this.isChosen = false;
	}
	
	public void reset() 
	{
		this.piecePosition = 0;
		this.isChosen = false;
		this.isPlaced = false;
	}
	
	public void resetPosition() 
	{
		this.piecePosition = 0;
		this.isPlaced = false;
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
		
		PieceType(	final PieceColor pieceColor,
							final PieceHeight pieceHeight,
							final PieceShape pieceShape,
							final PieceTopper pieceTopper) 
		{
			this.pieceColor = pieceColor;
			this.pieceHeight = pieceHeight;
			this.pieceShape = pieceShape;
			this.pieceTopper = pieceTopper;
		}
		
		@Override
		public String toString()
		{
			return  this.pieceColor.toString() +
					this.pieceHeight.toString() +
					this.pieceShape.toString() +
					this.pieceTopper.toString();
		}
	}
	
	public enum PieceColor
	{
		LIGHT("C"),
		DARK("c");
		
		private String color;
		
		PieceColor(final String color)
		{
			this.color = color;
		}
		
		@Override
		public String toString()
		{
			return this.color;
		}
	}
	
	public enum PieceHeight
	{
		TALL("H"),
		SHORT("h");
		
		private String height;
		
		PieceHeight(final String height)
		{
			this.height = height;
		}
		
		@Override
		public String toString() 
		{
			return this.height;
		}
	}
	
	public enum PieceShape
	{
		ROUND("S"),
		SQUARE("s");
		
		private String shape;
		
		PieceShape(final String shape)
		{
			this.shape = shape;
		}
		
		@Override
		public String toString() 
		{
			return this.shape;
		}
	}
	
	public enum PieceTopper
	{
		FLAT("T"),
		HOLE("t");
		
		private String topper;
		
		PieceTopper(final String topper)
		{
			this.topper = topper;
		}
		
		@Override
		public String toString() 
		{
			return this.topper;
		}
	}
}






















