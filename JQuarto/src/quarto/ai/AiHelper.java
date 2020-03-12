package quarto.ai;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import quarto.engine.board.Board;
import quarto.engine.board.Tile;
import quarto.engine.pieces.Piece;

public class AiHelper 
{	
	public static String getLastMove(Board board) 
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
		if (board.isGameOver())
		{
			// game over
			stringBuilder.append(board.isFirstPlayer() ? "1" : "2");
		}
		else if (board.isDraw())
		{
			// draw
			stringBuilder.append("0");
		}
		
		return stringBuilder.toString();
	}
	
	public static void getGameCode(Board board)
	{
		StringBuilder stringBuilder = new StringBuilder();
		
		for(Tile tile : board.getGameBoard()) 
		{
			if(tile.isOccupied())
			{
				stringBuilder.append(tile.getPieceOnTile().getType().toString());
				stringBuilder.append(", ");
			}
			else
			{
				stringBuilder.append("-");
				stringBuilder.append(", ");
			}
		}
		
		stringBuilder.append("\n");
		
		BufferedWriter bufferedWriter;
		try 
		{
			bufferedWriter = new BufferedWriter(new FileWriter("tree.txt", true));
			bufferedWriter.append(stringBuilder.toString());
			bufferedWriter.close();
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
	}
	
	public static void createTranspositionTree(Board rootBoard)
	{
		Node<Board> rootNode = new Node<>(rootBoard);
		createBoardTransposition(rootNode, 1);
		
		displayTranspositionTree(rootNode);
	}
	
	private static void displayTranspositionTree(Node<Board> node)
	{
		getGameCode(node.data);
		
		for(Node<Board> childrenNode : node.children)
		{
			displayTranspositionTree(childrenNode);
		}
	}
	
	// iterator represents the number of moves ahead to consider
	public static void createBoardTransposition(Node<Board> node, int iterator)
	{
		Board board = node.data;
		Piece pieceToPlace = board.getChosenPiece();
		
		for(Tile tile : board.getGameBoard())
		{
			if(!tile.isOccupied())
			{
				pieceToPlace.place(tile.getCoordinate());
				Board childrenBoard = board.update();
				
				if(iterator > 0)
				{
					Node<Board> childrenNode = new Node<>(childrenBoard);
					node.children.add(childrenNode);
					childrenNode.parent = node;
					
					List<Piece> childrenPieces = childrenNode.data.getRemainingPieces();
					
					for(Piece remainingPiece : childrenPieces)
					{
						if(remainingPiece != null)
						{
							remainingPiece.choose();
							childrenNode.data.setChosenPiece();
							
							createBoardTransposition(childrenNode, iterator-1);
						}
					}
				}
				else
				{
					List<Piece> parentPlacedPieces = node.data.getPlacedPieces();
					
					for(Piece placedPiece : parentPlacedPieces)
					{
						if(placedPiece != pieceToPlace)
						{
							placedPiece.reset();
						}
					}
				}
			}
		}
	}
	
	// modifier indicates whether points should be added or subtracted
	public static int EvaluatePosition(Board board, int modifier) 
	{
		int positionScore = 0;
		
		if(board.isGameOver() || board.isDraw())
		{
			positionScore += 500;
		}
		
		// criteria for position evaluation
		
		return positionScore;
	}
}
