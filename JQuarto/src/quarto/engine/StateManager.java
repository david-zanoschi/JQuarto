package quarto.engine;

import java.util.Set;

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
	private static GameWindow gameWindow;
	
	public static boolean isPieceChosen = false;
	public static boolean isPiecePlaced = false;
	public static boolean isAiOpponentSelected;
	
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
		if(gameWindow != null) 
		{
			gameWindow.close();
		}
		
		board.setFirstPlayer();
		
		infoPanel = new InfoPanel();
		piecesPanel = new PiecesPanel(board);
		tilesPanel = new TilesPanel(board);
		
		gameWindow = new GameWindow(infoPanel, piecesPanel, tilesPanel);
		isAiOpponentSelected = gameWindow.isAiOpponentSelected();
	}
	
	public static void run()
	{
		infoPanel.setInfo(GuiHelper.CHOOSE_PIECE);
		
		piecesPanel.draw();
		piecesPanel.enableMouseListeners();
		
		tilesPanel.draw();
		tilesPanel.disableMouseListeners();

		gameWindow.draw();
	}
	
	public static void pieceChosen()
	{
		board.nextPlayer();

		isPieceChosen = true;
		isPiecePlaced = false;
		
		infoPanel.setInfo(GuiHelper.PLACE_PIECE);
		piecesPanel.disableMouseListeners();
		tilesPanel.enableMouseListeners();

		if(!tilesPanel.getBoard().isFirstPlayer() && gameWindow.isAiOpponentSelected())
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
		else if (!tilesPanel.getBoard().isFirstPlayer() && gameWindow.isAiOpponentSelected())
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
		
		initializeBoard();
		configure();
		run();
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















