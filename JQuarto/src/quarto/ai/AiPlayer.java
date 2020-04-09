package quarto.ai;

import quarto.engine.StateManager;
import quarto.engine.board.Board;
import quarto.engine.pieces.Piece;
import quarto.gui.PiecesPanel;

import java.util.*;

public class AiPlayer
{
    // place piece on winning position; if no winning position available, place the piece so the opponent won't invariably win with next move
    public static void placePiece(Board board)
    {
        Board.Builder builder = AiHelper.createBuilder(board.getPlacedPieces());
        List<Integer> emptyTilesCoordinates = board.computeEmptyTilesCoordinates();
        Piece chosenPiece = board.getChosenPiece();
        List<Integer> safeCoordinate = new ArrayList<>();

        for(Integer coordinate : emptyTilesCoordinates)
        {
            // coordinate, builder
            chosenPiece.place(coordinate);
            Board newBoard = builder.setPiece(chosenPiece).build();

            // win whenever possible
            if (newBoard.isGameOver())
            {
                StateManager.piecePlaced(newBoard);
                return;
            }
            // if it's not a winning move, get the non-winning pieces for current placement
            // if there are non-winning pieces for the current coordinate, store the coordinate
            if (AiHelper.getNonWinningPieces(newBoard, chosenPiece).size() > 0)
            {
                safeCoordinate.add(coordinate);
            }
            chosenPiece.resetPosition();
            builder = builder.removePiece(coordinate);
        }

        Random random = new Random();
        int chosenCoordinate;
        // if the match is lost no matter where the piece is placed, place it randomly
        if (safeCoordinate.size() == 0)
        {
            chosenCoordinate = emptyTilesCoordinates.get(random.nextInt(emptyTilesCoordinates.size()));
        }
        // else pick a coordinate that has no winning pieces
        else
        {
            chosenCoordinate = safeCoordinate.get(random.nextInt(safeCoordinate.size()));
        }
        chosenPiece.place(chosenCoordinate);
        Board newBoard = builder.setPiece(chosenPiece).build();
        StateManager.piecePlaced(newBoard);
    }

    // choose a piece so the opponent does not win
    public static void choosePiece(PiecesPanel piecesPanel)
    {
        Board board = piecesPanel.getBoard();
        List<Piece> remainingPieces = board.getRemainingPieces();
        List<Piece> nonWinningPieces = AiHelper.getNonWinningPieces(board);

        Random random = new Random();
        // hand a random non-winning piece
        if (nonWinningPieces.size() > 0)
        {
            // the reason for this piece of code is that remaining pieces and noWinning pieces are different objects
            // so choosing a piece from nonWinningPieces itself will not actually choose a piece from the game board
            Piece chosenPiece;
            boolean isNonWinningPiece = false;
            do {
                chosenPiece = remainingPieces.get(random.nextInt(remainingPieces.size()));

                if (chosenPiece != null)
                {
                    for(Piece nonWinningPiece : nonWinningPieces)
                    {
                        if (nonWinningPiece.getPieceNumber() == chosenPiece.getPieceNumber())
                        {
                            isNonWinningPiece = true;
                            break;
                        }
                    }
                }
            } while (!isNonWinningPiece);

            piecesPanel.aiChosePiece(chosenPiece);
            return;
        }
        // hand a random winning piece
        Piece winningPiece;
        do {
           winningPiece = remainingPieces.get(random.nextInt(remainingPieces.size()));
        } while (winningPiece == null);
        piecesPanel.aiChosePiece(winningPiece);
    }
}
