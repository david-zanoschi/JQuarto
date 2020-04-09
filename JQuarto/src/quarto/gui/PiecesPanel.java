package quarto.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.swing.JLabel;
import javax.swing.JPanel;

import quarto.engine.StateManager;
import quarto.engine.board.Board;
import quarto.engine.pieces.Piece;

@SuppressWarnings("serial")
public class PiecesPanel extends JPanel
{
	public final static int PIECES_PER_ROW = 8;
	public final static int PIECES_PER_COLUMN = 2;
	public final static int PIECE_SIZE = GameWindow.WINDOW_WIDTH / PIECES_PER_ROW;	
	public final static int PIECES_PANEL_WIDTH = GameWindow.WINDOW_WIDTH;
	public final static int PIECES_PANEL_HEIGHT = PIECES_PER_ROW * PIECE_SIZE;
	
	private boolean areMouseListenersEnabled = true;
	private Board board;
	private Map<JLabel, Piece> pieceLabelPieceMap;
	private Map<JLabel, MouseListener> pieceLabelMouseListenerMap;
	
	public PiecesPanel(Board board) 
	{
		this.board = board;
		this.pieceLabelPieceMap = new HashMap<>();
		this.pieceLabelMouseListenerMap = new HashMap<>();
		
		this.configure();
	}
	
	private void configure()
	{	
		this.setLayout(new GridLayout(PIECES_PER_COLUMN, PIECES_PER_ROW));
		this.setSize(new Dimension(PIECES_PANEL_WIDTH, PIECES_PANEL_HEIGHT));
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

		for (Piece remainingPiece : this.board.getRemainingPieces())
		{
			JLabel pieceLabel = new JLabel();

			if (remainingPiece != null)
			{
				pieceLabel.setIcon(GuiHelper.PIECES_ICONS.get(remainingPiece.getPieceNumberAsString()));
				pieceLabel.setOpaque(true);

				pieceLabelPieceMap.put(pieceLabel, remainingPiece);
				pieceLabelMouseListenerMap.put(pieceLabel, null);
			}

			this.add(pieceLabel);
		}
		
		this.addMouseListeners();
	}
	
	public void updatePieces()
	{
		boolean matchFound = false;

		for (Entry<JLabel, Piece> entry : pieceLabelPieceMap.entrySet())
		{
			JLabel pieceLabel = entry.getKey();
			Piece piece = entry.getValue();

			for (Piece placedPiece: this.board.getPlacedPieces())
			{
				if (placedPiece.getPieceNumber() == piece.getPieceNumber() && pieceLabel.getIcon() != null)
				{
					pieceLabel.setIcon(null);
					pieceLabel.setOpaque(false);

					matchFound = true;
					break;
				}
			}

			if (matchFound)
			{
				break;
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
		for (Entry<JLabel, MouseListener> entry : this.pieceLabelMouseListenerMap.entrySet())
		{
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
					if (!StateManager.isPieceChosen && areMouseListenersEnabled)
					{
						label.setBackground(null);
					}
				}

				@Override
				public void mouseEntered(MouseEvent e)
				{
					if (!StateManager.isPieceChosen && areMouseListenersEnabled)
					{
						label.setBackground(new Color(133, 133, 133));
					}
				}

				@Override
				public void mouseClicked(MouseEvent e)
				{
					if (areMouseListenersEnabled)
					{
						label.setBackground(new Color(24, 179, 0));

						pieceLabelPieceMap.get(label).choose();
						for(Piece remainingPiece : board.getRemainingPieces())
						{
							if(remainingPiece == null)
							{
								continue;
							}


							if(remainingPiece.getPieceNumber() == pieceLabelPieceMap.get(label).getPieceNumber())
							{
								remainingPiece.choose();
								break;
							}
						}
						board.setChosenPiece();
						StateManager.pieceChosen();

						pieceLabelMouseListenerMap.remove(label);
						((JLabel) e.getSource()).removeMouseListener(this);
					}
				}
			};

			label.addMouseListener(mouseListener);
			this.pieceLabelMouseListenerMap.put(label, mouseListener);
		}
	}

	public void aiChosePiece(Piece pieceParam)
	{
		for (Entry<JLabel, Piece> labelPieceKeyValue : this.pieceLabelPieceMap.entrySet())
		{
			JLabel pLabel = labelPieceKeyValue.getKey();
			Piece piece = labelPieceKeyValue.getValue();

			if (piece.getPieceNumber() == pieceParam.getPieceNumber())
			{
				pLabel.setBackground(new Color(252, 3, 3));

				for (Entry<JLabel, MouseListener> labelMouseListenerKeyValue : this.pieceLabelMouseListenerMap.entrySet())
				{
					JLabel mLabel = labelMouseListenerKeyValue.getKey();
					MouseListener mouseListener = labelMouseListenerKeyValue.getValue();

					if (mLabel == pLabel)
					{
						pieceLabelMouseListenerMap.remove(mLabel);
						mLabel.removeMouseListener(mouseListener);
						break;
					}
				}
			}
		}

		for (Piece remainingPiece : this.board.getRemainingPieces())
		{
			if (remainingPiece == null)
			{
				continue;
			}

			if (remainingPiece.getPieceNumber() == pieceParam.getPieceNumber())
			{
				remainingPiece.choose();
				board.setChosenPiece();
				StateManager.pieceChosen();
				break;
			}
		}
	}
}





















