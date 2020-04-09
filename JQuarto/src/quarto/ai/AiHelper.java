package quarto.ai;

import java.util.ArrayList;
import java.util.List;

import quarto.engine.StateManager;
import quarto.engine.board.Board;
import quarto.engine.pieces.Piece;
import quarto.gui.GameWindow;

public class AiHelper 
{
	public static boolean isAiMove(GameWindow gameWindow)
	{
		// returns true if opponent is ai and ai moves first and it's odd move or ai moves second and it's even move
		return 	gameWindow.isAiOpponentSelected()
				&& ((StateManager.isAiMovingFirst && StateManager.moveCounter % 2 == 1) || (!StateManager.isAiMovingFirst && StateManager.moveCounter % 2 == 0));
	}

	public static Board.Builder createBuilder(List<Piece> placedPieces)
	{
		Board.Builder builder = new Board.Builder();

		for (Piece placedPiece : placedPieces)
		{
			builder.setPiece(placedPiece);
		}

		return builder;
	}

	// winning position - no matter the piece handed, the opponent can win
	// iterate through all remaining pieces, pick the non-winning ones and store them
	public static List<Piece> getNonWinningPieces(Board boardParam, Piece chosenPiece)
	{
		Board.Builder builder = AiHelper.createBuilder(boardParam.getPlacedPieces());
		Board board = builder.build();

		List<Piece> remainingPieces = board.getRemainingPieces();
		List<Integer> emptyTilesCoordinates = board.computeEmptyTilesCoordinates();
		List<Piece> nonWinningPieces = new ArrayList<>();

		for (Piece remainingPiece : remainingPieces)
		{
			if (remainingPiece == null)
			{
				continue;
			}
			else if (chosenPiece != null)
			{
				// the chosen piece is still a remaining piece, so it must be filtered out when considering what piece to choose for opponent
				if (remainingPiece.getPieceNumber() == chosenPiece.getPieceNumber())
				{
					continue;
				}
			}

			boolean winningPlacement = false;
			// place the piece on all empty tiles; if no placement wins the game, store the piece
			for (Integer coordinate : emptyTilesCoordinates)
			{
				remainingPiece.place(coordinate);
				Board newBoard = builder.setPiece(remainingPiece).build();

				remainingPiece.resetPosition();
				builder = builder.removePiece(coordinate);

				if (newBoard.isGameOver())
				{
					winningPlacement = true;
					break;
				}
			}

			if (!winningPlacement)
			{
				nonWinningPieces.add(remainingPiece);
			}
		}

		return nonWinningPieces;
	}

	public static List<Piece> getNonWinningPieces(Board board)
	{
		return getNonWinningPieces(board, null);
	}

//	public static void getGameCode(Board board)
//	{
//		StringBuilder stringBuilder = new StringBuilder();
//
//		for(Tile tile : board.getGameBoard())
//		{
//			if(tile.isOccupied())
//			{
//				stringBuilder.append(tile.getPieceOnTile().getType().toString());
//				stringBuilder.append(", ");
//			}
//			else
//			{
//				stringBuilder.append("-");
//				stringBuilder.append(", ");
//			}
//		}
//
//		stringBuilder.append("\n");
//
//		BufferedWriter bufferedWriter;
//		try
//		{
//			bufferedWriter = new BufferedWriter(new FileWriter("tree.txt", true));
//			bufferedWriter.append(stringBuilder.toString());
//			bufferedWriter.close();
//		}
//		catch (IOException e)
//		{
//			e.printStackTrace();
//		}
//	}

//	public static String getLastMove(Board board)
//	{
//		StringBuilder stringBuilder = new StringBuilder();
//
//		// player
//		stringBuilder.append(board.getCurrentPlayerString());
//		// piece
//		Piece chosenPiece = board.getChosenPiece();
//		// piece type
//		stringBuilder.append(chosenPiece.getType().toString());
//		// piece position
//		int piecePosition = chosenPiece.getCoordinate();
//		stringBuilder.append(piecePosition < 10 ? "0" + piecePosition : piecePosition);
//		// separator
//		stringBuilder.append(",");
//		if (board.isGameOver())
//		{
//			// game over
//			stringBuilder.append(board.isFirstPlayer() ? "1" : "2");
//		}
//		else if (board.isDraw())
//		{
//			// draw
//			stringBuilder.append("0");
//		}
//
//		return stringBuilder.toString();
//	}

//	public static void createTranspositionTree(Board rootBoard)
//	{
//		Node<Board> rootNode = new Node<>(rootBoard);
//		createBoardTransposition(rootNode, 1);
//
//		displayTranspositionTree(rootNode);
//	}
	
//	private static void displayTranspositionTree(Node<Board> node)
//	{
//		getGameCode(node.data);
//
//		for(Node<Board> childrenNode : node.children)
//		{
//			displayTranspositionTree(childrenNode);
//		}
//	}
	
	// iterator represents the number of moves ahead to consider
//	public static void createBoardTransposition(Node<Board> node, int iterator)
//	{
//		Board board = node.data;
//		Piece pieceToPlace = board.getChosenPiece();
//
//		for(Tile tile : board.getGameBoard())
//		{
//			if(!tile.isOccupied())
//			{
//				pieceToPlace.place(tile.getCoordinate());
//				Board childrenBoard = board.update();
//
//				if(iterator > 0)
//				{
//					Node<Board> childrenNode = new Node<>(childrenBoard);
//					node.children.add(childrenNode);
//					childrenNode.parent = node;
//
//					List<Piece> childrenPieces = childrenNode.data.getRemainingPieces();
//
//					for(Piece remainingPiece : childrenPieces)
//					{
//						if(remainingPiece != null)
//						{
//							remainingPiece.choose();
//							childrenNode.data.setChosenPiece();
//
//							createBoardTransposition(childrenNode, iterator-1);
//						}
//					}
//				}
//				else
//				{
//					List<Piece> parentPlacedPieces = node.data.getPlacedPieces();
//
//					for(Piece placedPiece : parentPlacedPieces)
//					{
//						if(placedPiece != pieceToPlace)
//						{
//							placedPiece.reset();
//						}
//					}
//				}
//			}
//		}
//	}
	
	// modifier indicates whether points should be added or subtracted
//	public static int EvaluatePosition(Board board, int modifier)
//	{
//		int positionScore = 0;
//
//		if(board.isGameOver() || board.isDraw())
//		{
//			positionScore += 500;
//		}
//
//		// criteria for position evaluation
//		return positionScore;
//	}
}
