package quarto.gui;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import quarto.engine.StateManager;


public class GameWindow 
{
	public static final int WINDOW_WIDTH = 560;

	private JMenuBar menuBar;
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
		this.menuBar = configureMenuBar();
		this.gameWindow.setJMenuBar(menuBar);
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

	public JMenuBar configureMenuBar()
	{
		// Menu bar
		JMenuBar jMenuBar = new JMenuBar();
		// Tab
		JMenu opponentMenu = new JMenu("Choose opponent");
		jMenuBar.add(opponentMenu);
		// radio buttons group
		ButtonGroup buttonGroup = new ButtonGroup();
		// human opponent
		JRadioButtonMenuItem humanPlayerRadioButton = new JRadioButtonMenuItem("Human Player");
		humanPlayerRadioButton.addActionListener(e -> StateManager.isAiOpponentSelected = isAiOpponentSelected());
		buttonGroup.add(humanPlayerRadioButton);
		opponentMenu.add(humanPlayerRadioButton);
		// ai opponent
		JRadioButtonMenuItem aiPlayerRadioButton = new JRadioButtonMenuItem("AI Player");
		aiPlayerRadioButton.addActionListener(e -> StateManager.isAiOpponentSelected = isAiOpponentSelected());
		buttonGroup.add(aiPlayerRadioButton);
		opponentMenu.add(aiPlayerRadioButton);
		// persist opponent over game instances
		if (!StateManager.isAiOpponentSelected)
		{
			humanPlayerRadioButton.setSelected(true);
		}
		else
		{
			aiPlayerRadioButton.setSelected(true);
		}

		return jMenuBar;
	}

	public boolean isAiOpponentSelected()
	{
		return !this.menuBar.getMenu(0).getItem(0).isSelected();
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




























