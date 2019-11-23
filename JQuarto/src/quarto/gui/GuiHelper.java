package quarto.gui;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import quarto.engine.board.BoardHelper;
import quarto.engine.pieces.Piece;

public class GuiHelper 
{
	private final static String DEFAULT_PIECE_SLOT_ICON_PATH = "PieceSlot";
	private final static String ALTERNATE_PIECE_SLOT_ICON_PATH = "AlternatePieceSlot";

	public final static int PIECE_SIZE = GameWindow.WINDOW_WIDTH / PiecesPanel.PIECES_PER_ROW;
	public final static int PIECES_PANEL_WIDTH = GameWindow.WINDOW_WIDTH;
	public final static int PIECES_PANEL_HEIGHT = PiecesPanel.PIECES_PER_ROW * GuiHelper.PIECE_SIZE;
	
	public final static int TILE_SIZE = GameWindow.WINDOW_WIDTH / BoardHelper.NUM_TILES_PER_LINE;
	public final static int TILES_PANEL_SIZE = BoardHelper.NUM_TILES_PER_LINE * GuiHelper.TILE_SIZE;
	
	public final static ImageIcon TILE_ICON = getTileIcon(DEFAULT_PIECE_SLOT_ICON_PATH);
	public final static ImageIcon ALTERNATE_TILE_ICON = getTileIcon(ALTERNATE_PIECE_SLOT_ICON_PATH);
	public final static Map<String, ImageIcon> PIECES_ICONS = getPiecesIcons("");
	public final static Map<String, ImageIcon> PIECE_SLOTS_ICONS = getPiecesIcons("Slot");

	private static ImageIcon getTileIcon(String iconPath)
	{
		BufferedImage tileBufferedImage = null;
		
		try
		{
			tileBufferedImage = ImageIO.read(new File("res/"+ iconPath + ".png"));
		}
		catch (IOException e) 
		{
			e.printStackTrace();
		}
		
		Image tileImage = tileBufferedImage.getScaledInstance(	TILE_SIZE, 
																TILE_SIZE, 
																Image.SCALE_SMOOTH);
		ImageIcon tileImageIcon = new ImageIcon(tileImage);
		
		return tileImageIcon;
	}
	
	private static Map<String, ImageIcon> getPiecesIcons(String input)
	{
		Map<String, ImageIcon> result = new HashMap<String, ImageIcon>();
		
		for(Piece piece : Piece.ALL_PIECES)
		{
			BufferedImage pieceBufferedImage = null;
			String key = piece.getPieceNumberToString() + input;
			
			try 
			{
				pieceBufferedImage = ImageIO.read(new File("res/" + key + ".png"));
			} 
			catch (IOException e) 
			{
				e.printStackTrace();
			}
			
			Image pieceImage;
			
			if(input == "Slot")
			{
				pieceImage = pieceBufferedImage.getScaledInstance(TILE_SIZE, TILE_SIZE, Image.SCALE_SMOOTH);
			}
			else
			{
				pieceImage = pieceBufferedImage.getScaledInstance(PIECE_SIZE, PIECE_SIZE, Image.SCALE_SMOOTH);				
			}
			
			ImageIcon pieceImageIcon = new ImageIcon(pieceImage);
			
			result.put(key, pieceImageIcon);
		}
		
		return result;
	}
}




















