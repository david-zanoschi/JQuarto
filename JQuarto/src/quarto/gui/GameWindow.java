package quarto.gui;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;

import javax.swing.*;

import quarto.engine.StateManager;
import quarto.engine.board.Board;
 

public class GameWindow 
{
	public static final int WINDOW_WIDTH = 560;
	
	private JFrame gameWindow;
	
	public GameWindow(	InfoPanel infoPanel,
						PiecesPanel piecesPanel,
						TilesPanel tilesPanel) 
	{
		this.gameWindow = new JFrame("JQuarto");
		this.configure(infoPanel, piecesPanel, tilesPanel);
	}
	
	public void configure(	InfoPanel infoPanel,
							PiecesPanel piecesPanel, 
							TilesPanel tilesPanel)
	{
		this.gameWindow.add(tilesPanel, BorderLayout.NORTH);
		this.gameWindow.add(infoPanel, BorderLayout.CENTER);
		this.gameWindow.add(piecesPanel, BorderLayout.SOUTH);
		
		this.gameWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.addKeyListener();
	}

	public void draw()
	{
		this.gameWindow.pack(); // sets the size for the JFrame so that all the child components are of preferred size or above
		this.gameWindow.setLocationRelativeTo(null); // for null, it centers the JFrame
		this.gameWindow.setResizable(false);
		this.gameWindow.setVisible(true);
	}
	
	public void close() 
	{
		this.gameWindow.dispose();
	}
	
	public void addKeyListener()
	{
		this.gameWindow.addKeyListener(new KeyListener() 
		{
			
			@Override
			public void keyTyped(KeyEvent e) 
			{
			}
			
			@Override
			public void keyReleased(KeyEvent e) 
			{
			}
			
			@Override
			public void keyPressed(KeyEvent e) 
			{
				if(e.getExtendedKeyCode() == KeyEvent.getExtendedKeyCodeForChar('r')) 
				{
					StateManager.restart();
				}
				
				if(e.getKeyChar() == KeyEvent.VK_ESCAPE)
				{
					gameWindow.dispatchEvent(new WindowEvent(gameWindow, WindowEvent.WINDOW_CLOSING));
				}
			}
		});
	}
}




























