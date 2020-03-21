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

	// called on board initialization
	public void draw()
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
		
		this.addMouseListeners();
	}

	public void update()
	{
		for (Piece placedPiece : this.board.getPlacedPieces())
		{
			for (Entry<JLabel, Tile> entry : this.tileLabelTileMap.entrySet())
			{
				if (placedPiece.getPosition() == entry.getValue().getCoordinate())
				{
					entry.getKey().setIcon(GuiHelper.PIECE_SLOTS_ICONS.get(placedPiece.getPieceNumberAsString() + "Slot"));

					for (Entry<JLabel, MouseListener> labelMouse : this.tileLabelMouseListenerMap.entrySet())
					{
						if(labelMouse.getKey() == entry.getKey())
						{
							labelMouse.getKey().removeMouseListener(labelMouse.getValue());
						}
					}
				}
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

			MouseListener mouseListener = new MouseListener()
			{
				@Override
				public void mouseReleased(MouseEvent e) {
				}

				@Override
				public void mousePressed(MouseEvent e) {
				}

				@Override
				public void mouseExited(MouseEvent e)
				{
					if (!StateManager.isPiecePlaced && areMouseListenersEnabled)
					{
						label.setIcon(GuiHelper.TILE_ICON);
					}
				}

				@Override
				public void mouseEntered(MouseEvent e)
				{
					if (!StateManager.isPiecePlaced && areMouseListenersEnabled)
					{
						label.setIcon(GuiHelper.ALTERNATE_TILE_ICON);
					}
				}

				@Override
				public void mouseClicked(MouseEvent e)
				{
					if (!tileLabelTileMap.get(label).isOccupied() && areMouseListenersEnabled)
					{
						Piece chosenPiece = board.getChosenPiece();
						chosenPiece.place(tileLabelTileMap.get(label).getCoordinate());

						board = board.update();
						setBoard(board);

						label.setIcon(GuiHelper.PIECE_SLOTS_ICONS.get(chosenPiece.getPieceNumberAsString() + "Slot"));

						StateManager.piecePlaced();

						tileLabelMouseListenerMap.remove(label);
						((JLabel) e.getSource()).removeMouseListener(this);
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
					break;
				}
				catch (AWTException e) 
				{
					e.printStackTrace();
				}
			}
			
		}
		
	}
}















