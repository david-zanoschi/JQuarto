package quarto.gui;

import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

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
	

	private Map<JLabel, Tile> tileLabelTileMap;
	private Map<JLabel, MouseListener> tileLabelMouseListenerMap;
	
	private Board board;
	private PiecesPanel piecesPanel;
	
	private boolean areMouseListenersEnabled = true;
	
	public TilesPanel(Board board, PiecesPanel piecesPanel) 
	{
		this.board = board;
		this.piecesPanel = piecesPanel;
		
		this.tileLabelTileMap = new HashMap<>();
		this.tileLabelMouseListenerMap = new HashMap<>();
		
		this.configure();
	}
	
	public Board getBoard()
	{
		return this.board;
	}
	
	private void configure()
	{
		this.setLayout(new GridLayout(BoardHelper.NUM_TILES_PER_LINE, BoardHelper.NUM_TILES_PER_LINE));
		this.setSize(GuiHelper.TILES_PANEL_SIZE, GuiHelper.TILES_PANEL_SIZE);
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
	}
	
	public void updateTiles()
	{
		Iterator<Entry<JLabel, Tile>> mapIterator = tileLabelTileMap.entrySet().iterator();
		
		while(mapIterator.hasNext())
		{
			Entry<JLabel, Tile> mapElement = mapIterator.next();
			
			Tile tile = mapElement.getValue();
			
			if(tile.isOccupied())
			{
				Piece pieceOnTile = tile.getPieceOnTile();
				JLabel tileLabel = mapElement.getKey();
				tileLabel.setIcon(GuiHelper.PIECE_SLOTS_ICONS.get(pieceOnTile.getPieceNumberToString() + "Slot"));
			}	
		}
	}
	
	public void addMouseListeners() 
	{
		Iterator<Entry<JLabel, MouseListener>> mapIterator = this.tileLabelMouseListenerMap. entrySet().iterator();
		
		while(mapIterator.hasNext())
		{
			JLabel label = mapIterator.next().getKey();
			
			MouseListener mouseListener = new MouseListener() {
				boolean isMouseListenerEnabled = true;
				
				@Override
				public void mouseReleased(MouseEvent e) {}
				
				@Override
				public void mousePressed(MouseEvent e) {}
				
				@Override
				public void mouseExited(MouseEvent e) 
				{
					if(	!StateManager.isPiecePlaced && 
						isMouseListenerEnabled && areMouseListenersEnabled)
					{
						label.setIcon(GuiHelper.TILE_ICON);
					}
				}
				
				@Override
				public void mouseEntered(MouseEvent e) 
				{
					if(	!StateManager.isPiecePlaced &&
						isMouseListenerEnabled && areMouseListenersEnabled)
					{
						label.setIcon(GuiHelper.ALTERNATE_TILE_ICON);
					}
				}
				
				@Override
				public void mouseClicked(MouseEvent e) 
				{
					if(	!tileLabelTileMap.get(label).isOccupied() &&
						isMouseListenerEnabled && areMouseListenersEnabled)
					{
						Piece chosenPiece = board.computeChosenPiece();
						chosenPiece.place(tileLabelTileMap.get(label).getCoordinate());
						board = board.update();
						piecesPanel.setBoard(board);
						label.setIcon(GuiHelper.PIECE_SLOTS_ICONS.get(chosenPiece.getPieceNumberToString() + "Slot"));
						
						isMouseListenerEnabled = false;
						
						StateManager.piecePlaced();
					}
				}
			};
			
			label.addMouseListener(mouseListener);
			tileLabelMouseListenerMap.put(label, mouseListener);
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
	
	
}















