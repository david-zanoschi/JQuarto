package quarto.gui;

import java.awt.*;
import javax.swing.*;

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
		
		this.configure();
	}
	
	private void configure()
	{
		this.gameWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.gameWindow.setResizable(false);
	}
	
	public void draw()
	{
		this.gameWindow.setLocationRelativeTo(null);
		this.gameWindow.pack();
		this.gameWindow.setVisible(true);
	}	
}




























