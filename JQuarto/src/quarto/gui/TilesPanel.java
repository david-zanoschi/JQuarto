package quarto.gui;

import java.awt.AWTException;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.InputEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.awt.Robot;

import javax.swing.JLabel;
import javax.swing.JPanel;

import quarto.engine.StateManager;
import quarto.engine.board.Board;
import quarto.engine.board.BoardHelper;
import quarto.engine.board.Tile;
import quarto.engine.pieces.Piece;

@SuppressWarnings("serial")
public class TilesPanel extends JPanel
{
	public final static int TILE_SIZE = GameWindow.WINDOW_WIDTH / BoardHelper.NUM_TILES_PER_LINE;
	public final static int TILES_PANEL_SIZE = BoardHelper.NUM_TILES_PER_LINE * TILE_SIZE;

	private boolean areMouseListenersEnabled = true;
	private Board board;
	private Map<JLabel, Tile> tileLabelTileMap;
	private Map<JLabel, MouseListener> tileLabelMouseListenerMap;
	
	public TilesPanel(Board board) 
	{
		this.board = board;
		this.tileLabelTileMap = new HashMap<>();
		this.tileLabelMouseListenerMap = new HashMap<>();
		
		this.configure();
	}
	
	private void configure()
	{
		this.setLayout(new GridLayout(BoardHelper.NUM_TILES_PER_LINE, BoardHelper.NUM_TILES_PER_LINE));
		this.setSize(TILES_PANEL_SIZE, TILES_PANEL_SIZE);
	}
	
	public Board getBoard()
	{
		return this.board;
	}
	
	public void setBoard(Board board)
	{
		this.board = board;
	}
	
	public void drawTiles()
	{
		this.removeAll();
		
		for(int i = 0; i < this.board.getGameBoard().size(); i++)
		{
			JLabel tileLabel = new JLabel();
			
			tileLabel.setIcon(GuiHelper.TILE_ICON);
			
			tileLabelTileMap.put(tileLabel, this.board.getTile(i));
			tileLabelMouseListenerMap.put(tileLabel, null);
			
			this.add(tileLabel);
		}
		
		addMouseListeners();
	}
	
	public void updateTiles()
	{
		for (Entry<JLabel, Tile> entry : tileLabelTileMap.entrySet()) {
			Tile tile = entry.getValue();

			if (tile.isOccupied()) {
				Piece pieceOnTile = tile.getPieceOnTile();
				JLabel tileLabel = entry.getKey();
				tileLabel.setIcon(GuiHelper.PIECE_SLOTS_ICONS.get(pieceOnTile.getPieceNumberAsString() + "Slot"));
			}
		}
	}
	
	public void disableMouseListeners()
	{
		this.areMouseListenersEnabled = false;
	}
	
	public void enableMouseListeners()
	{
		this.areMouseListenersEnabled = true;
	}
	
	private void addMouseListeners() 
	{
		for (Entry<JLabel, MouseListener> entry : this.tileLabelMouseListenerMap.entrySet()) {
			JLabel label = entry.getKey();

			MouseListener mouseListener = new MouseListener() {
				boolean isMouseListenerEnabled = true;

				@Override
				public void mouseReleased(MouseEvent e) {
				}

				@Override
				public void mousePressed(MouseEvent e) {
				}

				@Override
				public void mouseExited(MouseEvent e) {
					if (!StateManager.isPiecePlaced &&
							isMouseListenerEnabled && areMouseListenersEnabled) {
						label.setIcon(GuiHelper.TILE_ICON);
					}
				}

				@Override
				public void mouseEntered(MouseEvent e) {
					if (!StateManager.isPiecePlaced &&
							isMouseListenerEnabled && areMouseListenersEnabled) {
						label.setIcon(GuiHelper.ALTERNATE_TILE_ICON);
					}
				}

				@Override
				public void mouseClicked(MouseEvent e) {
					if (!tileLabelTileMap.get(label).isOccupied() &&
							isMouseListenerEnabled && areMouseListenersEnabled) {
						Piece chosenPiece = board.getChosenPiece();
						chosenPiece.place(tileLabelTileMap.get(label).getCoordinate());

						board = board.update();

						label.setIcon(GuiHelper.PIECE_SLOTS_ICONS.get(chosenPiece.getPieceNumberAsString() + "Slot"));

						isMouseListenerEnabled = false;

						StateManager.piecePlaced();
					}
				}
			};

			label.addMouseListener(mouseListener);
			tileLabelMouseListenerMap.put(label, mouseListener);
		}
	}
	
	public void simulateClick(int tileCoordinate) 
	{
		Iterator<Entry<JLabel, Tile>> mapIterator = tileLabelTileMap.entrySet().iterator();
		
		while(mapIterator.hasNext())
		{
			Tile tile = mapIterator.next().getValue();
			
			if(tile.getCoordinate() == tileCoordinate)
			{
				JLabel label = mapIterator.next().getKey();
				Point originPoint = label.getLocationOnScreen();
				
				try 
				{
					Robot robot = new Robot();
					robot.mouseMove(originPoint.x + 1, originPoint.y + 1);
					robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
					robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
				}
				catch (AWTException e) 
				{
					e.printStackTrace();
				}
			}
			
		}
		
	}
}















