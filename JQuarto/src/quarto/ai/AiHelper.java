package quarto.ai;

import quarto.engine.board.Board;
import quarto.engine.pieces.Piece;

public class AiHelper 
{	
	public static String getCurrentJQS(Board board) 
	{
		StringBuilder stringBuilder = new StringBuilder();
		
		// player
		stringBuilder.append(board.getCurrentPlayerString());
		
		// piece
		Piece chosenPiece = board.getChosenPiece();
		// piece type
		stringBuilder.append(chosenPiece.getType().toString());
		// piece position
		int piecePosition = chosenPiece.getPosition();
		stringBuilder.append(piecePosition < 10 ? "0" + piecePosition : piecePosition);
		
		// separator
		stringBuilder.append(",");
		
		// game over
		if (board.isGameOver())
		{
			stringBuilder.append(board.isFirstPlayer() ? "1\n" : "2\n");
		}
		else if (board.isDraw())
		{
			stringBuilder.append("0\n");
		}
		
		return stringBuilder.toString();
	}
}
