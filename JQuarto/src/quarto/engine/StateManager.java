package quarto.engine;

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
	public static boolean isAiMovingFirst; // who moves first hands the first piece; second move = place piece + choose piece
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
		infoPanel.setText(GuiHelper.CHOOSE_PIECE);

		piecesPanel.draw();
		tilesPanel.draw();
		window.draw();

		piecesPanel.enableMouseListeners();
		tilesPanel.disableMouseListeners();

		if (AiHelper.isAiMove(window))
		{
			AiPlayer.choosePiece(piecesPanel);
		}
	}
	
	public static void pieceChosen()
	{
		moveCounter++;
		board.nextPlayer();

		isPieceChosen = true;
		isPiecePlaced = false;
		infoPanel.setText(GuiHelper.PLACE_PIECE);

		piecesPanel.disableMouseListeners();
		tilesPanel.enableMouseListeners();

		if(AiHelper.isAiMove(window))
		{
			AiPlayer.placePiece(piecesPanel.getBoard());
		}
	}

	// this method can be called without boardParam argument, so it must be checked for null
	public static void piecePlaced(Board boardParam)
	{
		isPieceChosen = false;
		isPiecePlaced = true;
		infoPanel.setText(GuiHelper.CHOOSE_PIECE);

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
			
			infoPanel.setText(GuiHelper.computeGameOverMessage(tilesPanel.getBoard().getCurrentPlayerString()));
		} 
		else if(tilesPanel.getBoard().isDraw())
		{
			tilesPanel.disableMouseListeners();
			piecesPanel.disableMouseListeners();
			
			infoPanel.setText(GuiHelper.DRAW);
		}
		else if (AiHelper.isAiMove(window))
		{
			AiPlayer.choosePiece(piecesPanel);
		}
	}

	// simulates optional board parameter
	public static void piecePlaced()
	{
		piecePlaced(null);
	}

	public static void restart()
	{
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
		if (AiHelper.isAiMove(window) && !isPieceChosen)
		{
			AiPlayer.choosePiece(piecesPanel);
		}
		else if (AiHelper.isAiMove(window) && isPieceChosen && !isPiecePlaced)
		{
			AiPlayer.placePiece(piecesPanel.getBoard());
		}
	}

	//Threads
//	public static Set<Thread> threadSet;
	
//	public static Set<Thread> getThreads()
//	{
//		return Thread.getAllStackTraces().keySet();
//	}
	
//	public static void logThreads(String location)
//	{
//		int i = 0;
//		System.out.print(location + ":\n");
//		for(final Thread thread : threadSet)
//		{
//			System.out.print(i + ") " + thread.toString() + "\n");
//
//			++i;
//		}
//		System.out.print("Runs on edt: " + javax.swing.SwingUtilities.isEventDispatchThread() + "\n");
//		System.out.print("----------------------\n");
//	}
}















