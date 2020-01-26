package quarto.gui;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.*;

import quarto.engine.StateManager;
import quarto.engine.board.Board;
 

public class GameWindow 
{
	public static final int WINDOW_WIDTH = 560;
	
	private JFrame gameWindow;
	
	public GameWindow(	Board board,
						InfoPanel infoPanel,
						PiecesPanel piecesPanel,
						TilesPanel tilesPanel) 
	{
		this.gameWindow = new JFrame("JQuarto");	
		this.configure(board, infoPanel, piecesPanel, tilesPanel);
	}
	
	public void configure(	Board board, 
							InfoPanel infoPanel, 
							PiecesPanel piecesPanel, 
							TilesPanel tilesPanel)
	{
		this.gameWindow.add(tilesPanel, BorderLayout.NORTH);
		this.gameWindow.add(infoPanel, BorderLayout.CENTER);
		this.gameWindow.add(piecesPanel, BorderLayout.SOUTH);
		
		this.gameWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.gameWindow.setResizable(false);
	}
	
	@SuppressWarnings("exports")
	public void add(JPanel jPanel, String borderLayoutString)
	{
		this.gameWindow.add(jPanel, borderLayoutString);
	}
	
	public void draw()
	{
		this.gameWindow.setLocationRelativeTo(null);
		this.gameWindow.pack();
		this.gameWindow.setVisible(true);
	}
	
	public void clear()
	{
		this.gameWindow.removeAll();
	}
	
	public void validate()
	{
		this.gameWindow.validate();
		this.gameWindow.repaint();
	}
	
	public void addKeyListener()
	{
		this.gameWindow.addKeyListener(new KeyListener() 
		{
			
			@Override
			public void keyTyped(KeyEvent e) 
			{
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void keyReleased(KeyEvent e) 
			{
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void keyPressed(KeyEvent e) 
			{
				if(e.getExtendedKeyCode() == KeyEvent.getExtendedKeyCodeForChar('r')) 
				{
					StateManager.restart();
				}
			}
		});
	}
}




























