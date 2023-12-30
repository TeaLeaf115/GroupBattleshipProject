package graphics;

import graphicsManager.AnimationHandler;
import graphicsManager.SpriteManager.Indicator;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashMap;

public class Board extends JPanel {
	private HashMap<Point, Cell> board;
	public static AnimationHandler waterAnimation = new AnimationHandler(GamePanel.sm.getWaterTileset(), 42);
	
	public Board() {
		board = new HashMap<>();
		
		// Sets the Panel's layout
		setLayout(new GridBagLayout());
		
		// ¯\_(ツ)_/¯
		GridBagConstraints gbc = new GridBagConstraints();
		
		// Makes the 10x10 board out of Cell objects.
		for (int row = 0; row < 10; row++) {
			for (int col = 0; col < 10; col++) {
				gbc.gridx = col;
				gbc.gridy = row;
				
				Cell cellPane = new Cell();
				cellPane.setBackground(Color.CYAN);
				// Adds the cell to a HashMap of every board position.
				board.put(new Point(row, col), cellPane);
				// Adds a cell to the board.
				add(cellPane, gbc);
			}
		}
	}
	public static class Cell extends JPanel {
		Indicator spaceStatus = Indicator.EMPTY;
		
		public Cell() {
			// Mouse functionality per cell.
			addMouseListener(new MouseAdapter() {
				@Override
				public void mouseEntered(MouseEvent e) {
					setBackground(Color.BLUE);
				}
				@Override
				public void mouseExited(MouseEvent e) {
					setBackground(Color.CYAN);
				}
				
				@Override
				public void mouseClicked(MouseEvent e) {
					// Makes sure the user is clicking with a left click.
					if (e.getButton() == MouseEvent.BUTTON1) {
						System.out.println("Clicked");
					}
				}
			});
		}
		
		// Paints the graphical stuff to the cell.
		@Override
		protected void paintComponent(Graphics g) {
			super.paintComponent(g);
			// Nice water background.
			waterAnimation.draw((Graphics2D)g, new Point(0, 0));
			// TODO add stuff so it displays the ship bits and bobs as well.
		}
		
		// Will be changed to become more dynamic with the window size, as well as being imported from outside class.
		@Override
		public Dimension getPreferredSize() {
			return new Dimension(GamePanel.scaledTileSize, GamePanel.scaledTileSize);
		}
	}
}