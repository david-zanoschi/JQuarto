package quarto.gui;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import quarto.engine.StateManager;

public class GameWindow {
	public static final int WINDOW_WIDTH = 560;

	private JMenuBar menuBar;
	private JFrame gameWindow;

	public GameWindow(InfoPanel infoPanel, PiecesPanel piecesPanel, TilesPanel tilesPanel) {
		this.gameWindow = new JFrame("JQuarto");
		this.configure(infoPanel, piecesPanel, tilesPanel);
	}

	public JFrame getGameWindow() {
		return this.gameWindow;
	}

	public void configure(InfoPanel infoPanel, PiecesPanel piecesPanel, TilesPanel tilesPanel) {
		this.menuBar = configureMenuBar();
		this.gameWindow.setJMenuBar(menuBar);
		persistOpponent();
		persistMover();
		this.gameWindow.add(tilesPanel, BorderLayout.NORTH);
		this.gameWindow.add(infoPanel, BorderLayout.CENTER);
		this.gameWindow.add(piecesPanel, BorderLayout.SOUTH);
		this.gameWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.addKeyListener();
	}

	public void draw() {
		this.gameWindow.pack(); // sets the size for the JFrame so that all the child components are of
								// preferred size or above
		this.gameWindow.setLocationRelativeTo(null); // for null, it centers the JFrame
		this.gameWindow.setResizable(false);
		this.gameWindow.setVisible(true);
	}

	public void close() {
		this.gameWindow.dispose();
	}

	public JMenuBar configureMenuBar() {
		// Menu bar
		JMenuBar jMenuBar = new JMenuBar();

		JMenu opponentMenu = GuiHelper.configureMenu("Choose opponent", new ButtonGroup(),
				GuiHelper.createOpponentButton(this, "Human Player"),
				GuiHelper.createOpponentButton(this, "AI Player"));
		jMenuBar.add(opponentMenu);

		JMenu moverMenu = GuiHelper.configureMenu("Who moves first", new ButtonGroup(),
				GuiHelper.createMoverButton(this, "Human Player"), GuiHelper.createMoverButton(this, "AI Player"));
		jMenuBar.add(moverMenu);

		return jMenuBar;
	}

	public boolean isAiOpponentSelected() {
		return !this.menuBar.getMenu(0).getItem(0).isSelected();
	}

	public boolean isAiMovingFirst() {
		return !this.menuBar.getMenu(1).getItem(0).isSelected();
	}

	// persist opponent through game instances
	// if human opponent, 'who moves first' menu is disabled
	public void persistOpponent() {
		if (!StateManager.isAiOpponentSelected) {
			gameWindow.getJMenuBar().getMenu(0).getItem(0).setSelected(true);
			gameWindow.getJMenuBar().getMenu(1).setEnabled(false);
		} else {
			gameWindow.getJMenuBar().getMenu(0).getItem(1).setSelected(true);
			gameWindow.getJMenuBar().getMenu(1).setEnabled(true);
		}
	}

	// persist mover through game instances
	public void persistMover() {
		if (!StateManager.isAiMovingFirst) {
			gameWindow.getJMenuBar().getMenu(1).getItem(0).setSelected(true);
		} else {
			gameWindow.getJMenuBar().getMenu(1).getItem(1).setSelected(true);
		}
	}

	public void addKeyListener() {
		this.gameWindow.addKeyListener(new KeyListener() {

			@Override
			public void keyTyped(KeyEvent e) {
			}

			@Override
			public void keyReleased(KeyEvent e) {
			}

			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getExtendedKeyCode() == KeyEvent.getExtendedKeyCodeForChar('r')) {
					StateManager.restart();
				}

				if (e.getKeyChar() == KeyEvent.VK_ESCAPE) {
					gameWindow.dispatchEvent(new WindowEvent(gameWindow, WindowEvent.WINDOW_CLOSING));
				}
			}
		});
	}
}
