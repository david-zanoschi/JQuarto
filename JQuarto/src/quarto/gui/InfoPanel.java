package quarto.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JLabel;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class InfoPanel extends JPanel {
	public static final int INFO_PANEL_WIDTH = GameWindow.WINDOW_WIDTH;
	public static final int INFO_PANEL_HEIGHT = 150;

	private JLabel infoLabel;

	public InfoPanel() {
		this.infoLabel = new JLabel();

		this.configure();
	}

	private void configure() {
		this.infoLabel.setSize(new Dimension(INFO_PANEL_WIDTH, INFO_PANEL_HEIGHT));
		this.add(this.infoLabel, BorderLayout.CENTER);
	}

	public void setText(String info) {
		this.infoLabel.setText(info);
	}
}
