package quarto.engine;

import quarto.engine.board.Board;
import quarto.gui.GameWindow;
import quarto.gui.InfoPanel;
import quarto.gui.PiecesPanel;
import quarto.gui.TilesPanel;

public class StateManager 
{
	private static InfoPanel infoPanel;
	private static PiecesPanel piecesPanel;
	private static TilesPanel tilesPanel;
	private static GameWindow gameWindow;
	
	public static boolean isPieceChosen = false;
	public static boolean isPiecePlaced = false;
	
	public StateManager(Board board) 
	{
		configure(board);
	}
	
	private void configure(Board board)
	{
		infoPanel = new InfoPanel();
		piecesPanel = new PiecesPanel(board);
		tilesPanel = new TilesPanel(board, piecesPanel);
		gameWindow = new GameWindow(board, infoPanel, piecesPanel, tilesPanel);
	}
	
	public void initiateGame()
	{
		infoPanel.setInfo("Choose a piece for your opponent");
		
		piecesPanel.drawPieces();
		piecesPanel.addMouseListeners();
		
		tilesPanel.drawTiles();
		tilesPanel.addMouseListeners();
		tilesPanel.disableMouseListeners();
		
		gameWindow.drawGameWindow();
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

		piecesPanel.updatePieces();
		piecesPanel.enableMouseListeners();
		
		if(tilesPanel.getBoard().isGameOver())
		{
			tilesPanel.disableMouseListeners();
			piecesPanel.disableMouseListeners();
			
			infoPanel.setInfo("Game over.");
		}
	}

}















