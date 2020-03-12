package quarto.engine;

import java.util.Set;

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
		
		gameWindow = new GameWindow(board, infoPanel, piecesPanel, tilesPanel);
	}
	
	public static void run()
	{
		infoPanel.setInfo(GuiHelper.CHOOSE_PIECE);
		
		piecesPanel.drawPieces();
		piecesPanel.enableMouseListeners();
		
		tilesPanel.drawTiles();
		tilesPanel.disableMouseListeners();
		
		gameWindow.addKeyListener();
		gameWindow.setFramePosition();
		gameWindow.draw();
	}
	
	public static void pieceChosen()
	{		
		isPieceChosen = true;
		isPiecePlaced = false;
		
		infoPanel.setInfo(GuiHelper.PLACE_PIECE);
		
		piecesPanel.disableMouseListeners();
		
		tilesPanel.updateTiles();
		tilesPanel.enableMouseListeners();
		
		// create transposition tree using a copy of the board
//		Builder builder = new Builder();
//		for(Tile tile : tilesPanel.getBoard().getGameBoard())
//		{
//			builder.setPiece(tile.getPieceOnTile());
//		}
//		AiHelper.createTranspositionTree(builder.build());
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
		
		// ai
		// System.out.print(AiHelper.getLastMove(tilesPanel.getBoard()));
		
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
		else
		{			
			tilesPanel.getBoard().nextPlayer();
		}
	}
	
	public static void restart()
	{
		System.out.print(
				tilesPanel.getBoard().isGameOver() || tilesPanel.getBoard().isDraw() ? "\n" : "X\n");
		
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















