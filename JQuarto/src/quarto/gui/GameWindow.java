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

		// Opponent tab
		JMenu opponentMenu = new JMenu("Choose opponent");
		jMenuBar.add(opponentMenu);
		// Mover tab
		JMenu moverMenu = new JMenu("Who moves first");
		jMenuBar.add(moverMenu);

		ButtonGroup opponentButtonGroup = new ButtonGroup();
		// human opponent
		JRadioButtonMenuItem humanOpponentRadioButton = new JRadioButtonMenuItem("Human Player");
		humanOpponentRadioButton.addActionListener(e -> {
			if (StateManager.isAiOpponentSelected)
			{
				jMenuBar.getMenu(1).setEnabled(false);
				StateManager.isAiOpponentSelected = isAiOpponentSelected();

				if (StateManager.moveCounter > 1)
				{
					StateManager.restart();
				}
			}
		});
		opponentButtonGroup.add(humanOpponentRadioButton);
		opponentMenu.add(humanOpponentRadioButton);
		// ai opponent
		JRadioButtonMenuItem aiOpponentRadioButton = new JRadioButtonMenuItem("AI Player");
		aiOpponentRadioButton.addActionListener(e -> {
			if (!StateManager.isAiOpponentSelected)
			{
				jMenuBar.getMenu(1).setEnabled(true);
				StateManager.isAiOpponentSelected = isAiOpponentSelected();

				if (StateManager.moveCounter > 1)
				{
					StateManager.restart();
				}
			}
		});
		opponentButtonGroup.add(aiOpponentRadioButton);
		opponentMenu.add(aiOpponentRadioButton);
		// persist opponent over game instances
		if (!StateManager.isAiOpponentSelected)
		{
			// human player selected
			jMenuBar.getMenu(0).getItem(0).setSelected(true);
			// disable who moves first menu
			jMenuBar.getMenu(1).setEnabled(false);
		}
		else
		{
			// ai player selected
			jMenuBar.getMenu(0).getItem(1).setSelected(true);
			// enable who moves first menu
			jMenuBar.getMenu(1).setEnabled(true);
		}

		ButtonGroup moverButtonGroup = new ButtonGroup();
		// human mover
		JRadioButtonMenuItem humanMoverRadioButton = new JRadioButtonMenuItem("Human Player");
		humanMoverRadioButton.addActionListener(e -> {
			if (StateManager.isAiMovingFirst)
			{
				StateManager.isAiMovingFirst = isAiMovingFirst();
				StateManager.restart();
			}
		});
		moverButtonGroup.add(humanMoverRadioButton);
		moverMenu.add(humanMoverRadioButton);
		// ai mover
		JRadioButtonMenuItem aiMoverRadioButton = new JRadioButtonMenuItem("Ai Player");
		aiMoverRadioButton.addActionListener(e -> {
			if(!StateManager.isAiMovingFirst)
			{
				StateManager.isAiMovingFirst = isAiMovingFirst();
				if (StateManager.moveCounter == 1)
				{
					StateManager.decideWhatAiDoes();
				}
				else
				{
					StateManager.restart();
				}
			}
		});
		moverButtonGroup.add(aiMoverRadioButton);
		moverMenu.add(aiMoverRadioButton);
		// persis mover over game instances
		if (!StateManager.isAiMovingFirst)
		{
			// human player moves
			jMenuBar.getMenu(1).getItem(0).setSelected(true);
		}
		else
		{
			// ai player moves
			jMenuBar.getMenu(1).getItem(1).setSelected(true);
		}

		return jMenuBar;
	}

	public boolean isAiOpponentSelected()
	{
		return !this.menuBar.getMenu(0).getItem(0).isSelected();
	}

	public boolean isAiMovingFirst()
	{
		return !this.menuBar.getMenu(1).getItem(0).isSelected();
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




























