package quarto.engine;

import java.util.Set;

import quarto.engine.board.Board;
import quarto.engine.board.Board.Builder;
import quarto.gui.GameWindow;
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
		infoPanel.setInfo("Choose a piece for your opponent");
		
		piecesPanel.drawPieces();
		piecesPanel.enableMouseListeners();
		
		tilesPanel.drawTiles();
		tilesPanel.disableMouseListeners();
		
		gameWindow.addKeyListener();
		gameWindow.validate();
		gameWindow.draw();
		
//		Set<Thread> threadSet = Thread.getAllStackTraces().keySet();
//		int i = 0;
//		for(final Thread thread : threadSet) 
//		{
//			System.out.print(i + ") " + thread.getName() + ", " + thread.isDaemon() + "\n");
//			++i;
//		}
//		System.out.print("----------------------\n");
	}
	
	public static void pieceChosen()
	{
		isPieceChosen = true;
		isPiecePlaced = false;
		
		infoPanel.setInfo("Place chosen piece on table");
		
		piecesPanel.disableMouseListeners();
		
		tilesPanel.updateTiles();
		tilesPanel.enableMouseListeners();
	}
	
	public static void piecePlaced()
	{
		isPieceChosen = false;
		isPiecePlaced = true;
		
		infoPanel.setInfo("Choose a piece for your opponent");
		
		tilesPanel.updateTiles();
		tilesPanel.disableMouseListeners();

		piecesPanel.setBoard(tilesPanel.getBoard());
		piecesPanel.updatePieces();
		piecesPanel.enableMouseListeners();
		
		if(tilesPanel.getBoard().isGameOver())
		{
			tilesPanel.disableMouseListeners();
			piecesPanel.disableMouseListeners();
			
			infoPanel.setInfo("Game over.");
		}
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
}















