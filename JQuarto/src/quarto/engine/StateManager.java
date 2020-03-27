package quarto.engine;

import java.util.Set;

import quarto.ai.AiHelper;
import quarto.ai.AiPlayer;
import quarto.engine.board.Board;
import quarto.engine.board.Board.Builder;
import quarto.gui.GameWindow;
import quarto.gui.GuiHelper;
import quarto.gui.InfoPanel;
import quarto.gui.PiecesPanel;
import quarto.gui.TilesPanel;


public class StateManager 
{
	private static Board board;
	private static InfoPanel infoPanel;
	private static PiecesPanel piecesPanel;
	private static TilesPanel tilesPanel;
	private static GameWindow window;
	
	public static boolean isPieceChosen = false;
	public static boolean isPiecePlaced = false;
	public static boolean isAiOpponentSelected;
	// he who moves first hands the first piece; the second move is placing it a choosing another piece for the opponent
	public static boolean isAiMovingFirst;
	public static int moveCounter = 1;
	
	public StateManager() 
	{		
	}
	
	public void start()
	{
		initializeBoard();
		configure();
		run();
	}

	public static void initializeBoard()
	{
		Builder builder = new Builder();
		builder.reset();
		board = builder.build();
	}
	
	private static void configure()
	{
		if(window != null)
		{
			window.close();
		}
		
		board.setFirstPlayer();
		
		infoPanel = new InfoPanel();
		piecesPanel = new PiecesPanel(board);
		tilesPanel = new TilesPanel(board);
		
		window = new GameWindow(infoPanel, piecesPanel, tilesPanel);
		isAiOpponentSelected = window.isAiOpponentSelected();
		isAiMovingFirst = window.isAiMovingFirst();
	}
	
	public static void run()
	{
		infoPanel.setInfo(GuiHelper.CHOOSE_PIECE);
		
		piecesPanel.draw();
		piecesPanel.enableMouseListeners();
		
		tilesPanel.draw();
		tilesPanel.disableMouseListeners();

		window.draw();

		if (AiHelper.aiShouldMove(window))
		{
			AiPlayer.chooseNotWinningPiece(piecesPanel);
		}
	}
	
	public static void pieceChosen()
	{
		moveCounter++;

		board.nextPlayer();

		isPieceChosen = true;
		isPiecePlaced = false;
		
		infoPanel.setInfo(GuiHelper.PLACE_PIECE);
		piecesPanel.disableMouseListeners();
		tilesPanel.enableMouseListeners();

		if(AiHelper.aiShouldMove(window))
		{
			AiPlayer.PlaceWinningOrRandomPiece(piecesPanel.getBoard());
		}
	}
	
	public static void piecePlaced(Board boardParam)
	{
		isPieceChosen = false;
		isPiecePlaced = true;
		
		infoPanel.setInfo(GuiHelper.CHOOSE_PIECE);

		if(boardParam != null)
		{
			tilesPanel.setBoard(boardParam);
		}
		tilesPanel.update();
		tilesPanel.disableMouseListeners();
		
		piecesPanel.setBoard(tilesPanel.getBoard());
		piecesPanel.updatePieces();
		piecesPanel.enableMouseListeners();
		
		if(tilesPanel.getBoard().isGameOver())
		{
			tilesPanel.disableMouseListeners();
			piecesPanel.disableMouseListeners();
			
			infoPanel.setInfo(GuiHelper.computeGameOverMessage(tilesPanel.getBoard().getCurrentPlayerString()));
		} 
		else if(tilesPanel.getBoard().isDraw())
		{
			tilesPanel.disableMouseListeners();
			piecesPanel.disableMouseListeners();
			
			infoPanel.setInfo(GuiHelper.DRAW);
		}
		else if (AiHelper.aiShouldMove(window))
		{
			AiPlayer.chooseNotWinningPiece(piecesPanel);
		}
	}

	// To simulate optional board parameter
	public static void piecePlaced()
	{
		piecePlaced(null);
	}

	public static void restart()
	{
		System.out.print(tilesPanel.getBoard().isGameOver() || tilesPanel.getBoard().isDraw() ? "\n" : "X\n");
		
		isPieceChosen = false;
		isPiecePlaced = false;
		board = null;
		moveCounter = 1;
		
		initializeBoard();
		configure();
		run();
	}

	public static void decideWhatAiDoes()
	{
		if (AiHelper.aiShouldMove(window) && !isPieceChosen)
		{
			AiPlayer.chooseNotWinningPiece(piecesPanel);
		}
		else if (AiHelper.aiShouldMove(window) && isPieceChosen && !isPiecePlaced)
		{
			AiPlayer.PlaceWinningOrRandomPiece(piecesPanel.getBoard());
		}
	}

	// Threads
	public static Set<Thread> threadSet;
	
	public static Set<Thread> getThreads() 
	{
		return Thread.getAllStackTraces().keySet();
	}
	
	public static void logThreads(String location) 
	{
		int i = 0;
		System.out.print(location + ":\n");
		for(final Thread thread : threadSet) 
		{
			System.out.print(i + ") " + thread.toString() + "\n");
			
			++i;
		}
		System.out.print("Runs on edt: " + javax.swing.SwingUtilities.isEventDispatchThread() + "\n");
		System.out.print("----------------------\n");
	}
}















