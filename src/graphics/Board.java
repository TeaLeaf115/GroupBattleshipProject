package graphics;

import graphicsManager.SpriteManager.Indicator;

import javax.swing.*;
import javax.swing.border.MatteBorder;
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
				// Cyan cus why the hell not.
				cellPane.setBackground(Color.CYAN);
				// This line right here caused me the most pain in my life ever!!!!! Fuck you!
//				cellPane.setBorder(new MatteBorder(0, 0, 0, 0, Color.BLACK));
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
				// This function is somehow needed even though it does jackshit.
				@Override
				public void mouseEntered(MouseEvent e) {
					setBackground(Color.BLUE);
				}
				
				// Same with this fucker here, DOES ABSOLUTLY NOTHING USEFUL!!!
				@Override
				public void mouseExited(MouseEvent e) {
					setBackground(Color.CYAN);
				}
				
				// The favorite child of mouse functionality for this class that detects what the player is setting the cell to.
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
			// TODO add shit so it displays the ship bits and bobs as well.
		}
		
		// Will be changed to become more dynamic with the window size, as well as being imported from outside class.
		@Override
		public Dimension getPreferredSize() {
			return new Dimension(GamePanel.scaledTileSize, GamePanel.scaledTileSize);
		}
	}
}