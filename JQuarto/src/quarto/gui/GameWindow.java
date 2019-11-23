package quarto.gui;

import java.awt.*; //awt - abstract window toolkit
import javax.swing.*; //built on top of awt and provides an aditional set of graphical interface components

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
		this.gameWindow.add(tilesPanel, BorderLayout.NORTH);
		this.gameWindow.add(infoPanel, BorderLayout.CENTER);
		this.gameWindow.add(piecesPanel, BorderLayout.SOUTH);
		
		configure();
	}
	
	private void configure()
	{
		this.gameWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public void drawGameWindow()
	{
		// display the window (in center of screen)
		this.gameWindow.setLocationRelativeTo(null);
				
		// set the window size with pack() - looks at what the frame contains and sizes it accordingly
		this.gameWindow.pack();
				
		// show the window
		this.gameWindow.setVisible(true);
	}	
}




























