package quarto.ai;

import quarto.engine.StateManager;
import quarto.engine.board.Board;
import quarto.engine.pieces.Piece;
import quarto.gui.PiecesPanel;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class AiPlayer
{
    // place winning piece or place randomly
    public static void PlaceWinningOrRandomPiece(Board board)
    {
        Board.Builder builder = new Board.Builder();

        // current board builder
        for (Piece placedPiece : board.getPlacedPieces())
        {
            builder.setPiece(placedPiece);
        }

        Piece chosenPiece = board.getChosenPiece();
        List<Integer> emptyTilesCoordinates = board.computeEmptyTilesCoordinates();
        List<Integer> noNoCoordinates = new ArrayList<>();

        for(Integer coordinate : emptyTilesCoordinates)
        {
            chosenPiece.place(coordinate);
            Board newBoard = builder.setPiece(chosenPiece).build();

            // winning move
            if (newBoard.isGameOver())
            {
                StateManager.piecePlaced(newBoard);
                return;
            }
            // if after making the placement, whichever piece is handed is a winning piece, don't place the piece there
            else if (isWinningPosition(newBoard))
            {
                noNoCoordinates.add(coordinate);
            }

            chosenPiece.resetPosition();
            builder = builder.removePiece(coordinate);
        }

        Random random = new Random();
        int randomCoordinate = emptyTilesCoordinates.get(random.nextInt(emptyTilesCoordinates.size()));

        // if no matter the placement the match is lost, place the piece randomly
        if (noNoCoordinates.size() == emptyTilesCoordinates.size())
        {
            chosenPiece.place(randomCoordinate);
            Board newBoard = builder.setPiece(chosenPiece).build();
            StateManager.piecePlaced(newBoard);
        }
        // else place the piece randomly, but on a position that will not make the opponent win
        else
        {
            while (isNoNoCoordinate(noNoCoordinates, randomCoordinate))
            {
                randomCoordinate = emptyTilesCoordinates.get(random.nextInt(emptyTilesCoordinates.size()));
            }

            chosenPiece.place(randomCoordinate);
            Board newBoard = builder.setPiece(chosenPiece).build();
            StateManager.piecePlaced(newBoard);
        }
    }

    // choose a piece so the opponent does not win
    public static void chooseNotWinningPiece(PiecesPanel piecesPanel)
    {
        Board board = piecesPanel.getBoard();
        Board.Builder builder = new Board.Builder();

        // current board builder
        for (Piece placedPiece : board.getPlacedPieces())
        {
            builder.setPiece(placedPiece);
        }

        List<Piece> remainingPieces = board.getRemainingPieces();
        List<Integer> emptyTilesCoordinates = board.computeEmptyTilesCoordinates();

        Random random = new Random();
        List<Piece> noWinningPieces = new ArrayList<>();
        // iterate through all remaining pieces, pick the non-winning ones and hand a random one
        // else hand a winning piece
        for (Piece remainingPiece : remainingPieces)
        {
            if (remainingPiece == null)
            {
                continue;
            }

            boolean winningPlacement = false;
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
                noWinningPieces.add(remainingPiece);
            }
        }

        // hand a non winning piece
        if (noWinningPieces.size() > 0)
        {
            piecesPanel.aiChosePiece(noWinningPieces.get(random.nextInt(noWinningPieces.size())));
            return;
        }

        // hand a winning piece
        Piece winningPiece;
        do {
           winningPiece = remainingPieces.get(random.nextInt(remainingPieces.size()));
        } while (winningPiece == null);
        piecesPanel.aiChosePiece(winningPiece);
    }

    private static boolean isNoNoCoordinate(List<Integer> noNoCoordinates, int randomCoordinate)
    {
        for(Integer noNoCoordinate : noNoCoordinates)
        {
            if (randomCoordinate == noNoCoordinate)
            {
                return true;
            }
        }

        return false;
    }

    private static boolean isWinningPosition(Board board)
    {
        List<Piece> placedPieces = board.getPlacedPieces();
        Board.Builder builder = new Board.Builder();
        // current builder
        for (Piece placedPiece : placedPieces)
        {
            builder.setPiece(placedPiece);
        }

        boolean isWinningPosition = true;
        List<Piece> remainingPieces = board.getRemainingPieces();
        List<Integer> emptyTileCoordinates = board.computeEmptyTilesCoordinates();
        for (Piece remainingPiece : remainingPieces)
        {
            if (remainingPiece == null)
            {
                continue;
            }

            for (Integer coordinate : emptyTileCoordinates)
            {
                remainingPiece.place(coordinate);
                Board newBoard = builder.setPiece(remainingPiece).build();

                remainingPiece.resetPosition();
                builder = builder.removePiece(coordinate);

                if (!newBoard.isGameOver())
                {
                    isWinningPosition = false;
                    break;
                }
            }

            if (!isWinningPosition)
            {
                break;
            }
        }

        return isWinningPosition;
    }
}
