package quarto.gui;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.plaf.nimbus.State;

import quarto.engine.StateManager;
import quarto.engine.board.Board;

public class GuiHelper 
{
	private final static String DEFAULT_PIECE_SLOT_ICON_PATH = "PieceSlot";
	private final static String ALTERNATE_PIECE_SLOT_ICON_PATH = "AlternatePieceSlot";
	
	public final static String CHOOSE_PIECE = "Choose a piece for your opponent";
	public final static String PLACE_PIECE = "Place the chosen piece on the table";
	public final static String DRAW = "Draw.";
	
	public final static ImageIcon TILE_ICON = getTileIcon(DEFAULT_PIECE_SLOT_ICON_PATH);
	public final static ImageIcon ALTERNATE_TILE_ICON = getTileIcon(ALTERNATE_PIECE_SLOT_ICON_PATH);
	public final static Map<String, ImageIcon> PIECES_ICONS = getPiecesIcons("");
	public final static Map<String, ImageIcon> PIECE_SLOTS_ICONS = getPiecesIcons("Slot");

	private static ImageIcon getTileIcon(String iconPath)
	{
		BufferedImage tileBufferedImage = null;
		
		try
		{
			tileBufferedImage = ImageIO.read(new File("JQuarto/res/"+ iconPath + ".png"));
		}
		catch (IOException e) 
		{
			e.printStackTrace();
		}

		assert tileBufferedImage != null;
		Image tileImage = tileBufferedImage.getScaledInstance(	TilesPanel.TILE_SIZE,
																TilesPanel.TILE_SIZE, 
																Image.SCALE_SMOOTH);

		return new ImageIcon(tileImage);
	}
	
	private static Map<String, ImageIcon> getPiecesIcons(String input)
	{
		Map<String, ImageIcon> result = new HashMap<>();
		
		for(String pieceNumber : Board.PIECES_NUMBERS_STRINGS)
		{
			BufferedImage pieceBufferedImage = null;
			String key = pieceNumber + input;
			
			try 
			{
				pieceBufferedImage = ImageIO.read(new File("JQuarto/res/" + key + ".png"));
			} 
			catch (IOException e) 
			{
				e.printStackTrace();
			}
			
			Image pieceImage;
			
			if(input.equals("Slot"))
			{
				assert pieceBufferedImage != null;
				pieceImage = pieceBufferedImage.getScaledInstance(	TilesPanel.TILE_SIZE,
																	TilesPanel.TILE_SIZE, 
																	Image.SCALE_SMOOTH);
			}
			else
			{
				assert pieceBufferedImage != null;
				pieceImage = pieceBufferedImage.getScaledInstance(PiecesPanel.PIECE_SIZE, PiecesPanel.PIECE_SIZE, Image.SCALE_SMOOTH);
			}

			ImageIcon pieceImageIcon = new ImageIcon(pieceImage);
			result.put(key, pieceImageIcon);
		}
		
		return result;
	}
	
	public static String computeGameOverMessage(String playerName)
	{
		return "Game over. Player " + playerName + " won";
	}

	public static JRadioButtonMenuItem createOpponentButton (GameWindow window, String label)
	{
		JRadioButtonMenuItem button = new JRadioButtonMenuItem(label);
		button.addActionListener(e -> {
			StateManager.isAiOpponentSelected = window.isAiOpponentSelected();
			window.getGameWindow().getJMenuBar().getMenu(1).setEnabled(StateManager.isAiOpponentSelected);
			if (StateManager.moveCounter > 1)
			{
				StateManager.restart();
			}
		});
		return button;
	}

	public static JRadioButtonMenuItem createMoverButton (GameWindow window, String label)
	{
		JRadioButtonMenuItem button = new JRadioButtonMenuItem(label);
		button.addActionListener(e -> {
			StateManager.isAiMovingFirst = window.isAiMovingFirst();
			if (!StateManager.isAiMovingFirst)
			{
				StateManager.restart();
			}
			else
			{
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
		return button;
	}

	public static JMenu configureMenu(String label, ButtonGroup buttonGroup, JRadioButtonMenuItem... buttons)
	{
		JMenu menu = new JMenu(label);
		for (JRadioButtonMenuItem radioButton : buttons)
		{
			buttonGroup.add(radioButton);
			menu.add(radioButton);
		}
		return menu;
	}
}




















