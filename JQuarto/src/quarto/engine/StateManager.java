package quarto.engine;

import java.util.Set;

import quarto.ai.AiHelper;
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
	
	public StateManager() 
	{		
		initializeBoard();
		configure();
		run();
	}
	
	private static void configure()
	{
		infoPanel = new InfoPanel();
		piecesPanel = new PiecesPanel(board);
		tilesPanel = new TilesPanel(board);
		
		gameWindow = new GameWindow(board, infoPanel, piecesPanel, tilesPanel);
	}
	
	public static void initializeBoard()
	{
		Builder builder = new Builder();
		builder.reset();
		board = builder.build();
	}
	
	public static void run()
	{
		infoPanel.setInfo(GuiHelper.CHOOSE_PIECE);
		
		piecesPanel.drawPieces();
		piecesPanel.enableMouseListeners();
		
		tilesPanel.drawTiles();
		tilesPanel.disableMouseListeners();
		
		gameWindow.addKeyListener();
		gameWindow.validate();
		gameWindow.draw();
		
		// ai
		System.out.print(AiHelper.getCurrentJQS(tilesPanel.getBoard()));
	}
	
	public static void pieceChosen()
	{
		isPieceChosen = true;
		isPiecePlaced = false;
		
		infoPanel.setInfo(GuiHelper.PLACE_PIECE);
		
		piecesPanel.disableMouseListeners();
		
		tilesPanel.updateTiles();
		tilesPanel.enableMouseListeners();
	}
	
	public static void piecePlaced()
	{
		isPieceChosen = false;
		isPiecePlaced = true;
		
		infoPanel.setInfo(GuiHelper.CHOOSE_PIECE);
		
		tilesPanel.updateTiles();
		tilesPanel.disableMouseListeners();

		piecesPanel.setBoard(tilesPanel.getBoard());
		piecesPanel.updatePieces();
		piecesPanel.enableMouseListeners();
		
		if(tilesPanel.getBoard().isGameOver())
		{
			tilesPanel.disableMouseListeners();
			piecesPanel.disableMouseListeners();
			
			infoPanel.setInfo(GuiHelper.GAME_OVER);
		}
		
		// ai
		System.out.print(AiHelper.getCurrentJQS(tilesPanel.getBoard()));
	}
	
	private static void reconfigure()
	{			
		infoPanel = new InfoPanel();
		tilesPanel.setBoard(board);
		piecesPanel.setBoard(board);
		
		gameWindow.configure(board, infoPanel, piecesPanel, tilesPanel);
	}
	
	public static void restart()
	{
		isPieceChosen = false;
		isPiecePlaced = false;
		initializeBoard();
		reconfigure();
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















