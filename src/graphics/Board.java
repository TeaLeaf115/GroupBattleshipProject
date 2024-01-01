package graphics;

import gameLogic.Ship;
import gameLogic.ShipLocations;
import gameLogic.ShipSection;
import graphics.screens.GameplayScreen;
import graphicsManager.AnimationHandler;
import graphicsManager.SpriteManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.HashMap;

public class Board extends JPanel {
	private HashMap<Point, Cell> board;
	private boolean shipsVisible;
	ShipLocations sl;
	public AnimationHandler waterAnimation = new AnimationHandler(GamePanel.sm.getWaterTileset(), 42);
	
	public Board(ShipLocations spaceStatuses) {
		board = new HashMap<>();
		shipsVisible = true;
		// Sets the Panel's layout
		setLayout(new GridBagLayout());
		sl = spaceStatuses;
		GridBagConstraints gbc = new GridBagConstraints();
		
		// Makes the 10x10 board out of Cell objects.
		for (int row = 0; row < 10; row++) {
			for (int col = 0; col < 10; col++) {
				gbc.gridx = col;
				gbc.gridy = row;
				
				Cell cellPane = new Cell(new Point(row, col));
				cellPane.setBackground(Color.CYAN);
				// Adds the cell to a HashMap of every board position.
//				board.put(, cellPane);
				// Adds a cell to the board.
				add(cellPane, gbc);
			}
		}
	}
	
	public void shipsVisible(boolean visible) {
		this.shipsVisible = visible;
	}
	
	public void update() {
		waterAnimation.update();
	}
	
	public class Cell extends JPanel {
		Point coord;
		public Cell(Point coord) {
			this.coord = coord;
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
					if (e.getButton() == MouseEvent.BUTTON1 && !shipsVisible) {
						GameplayScreen.gl.bot.shipLocations.shootLocation(coord);
					}
				}
			});
		}
		
		// Paints the graphical stuff to the cell.
		@Override
		protected void paintComponent(Graphics g) {
			super.paintComponent(g);
			Graphics2D g2 = (Graphics2D) g;
			// Nice water background.
			waterAnimation.draw(g2, new Point(0, 0));
			try {
				if (sl.getMisses().contains(coord)) {
					g2.drawImage(GamePanel.sm.getIndicator(SpriteManager.Indicator.MISS), 0, 0, GamePanel.scaledTileSize, GamePanel.scaledTileSize, null);
					return;
				}
				
				if (shipsVisible) {
					if (sl.getUnguessedSections().containsKey(coord)) {
						ShipSection section = sl.getUnguessedSections().get(coord);
						g2.drawImage(rotate(GamePanel.sm.getShipSectionFromShip(section.getShipType(), section.getSection()), section.getRotation()), 0, 0, GamePanel.scaledTileSize, GamePanel.scaledTileSize, null);
					}
					if (sl.getHitSections().containsKey(coord)) {
						ShipSection section = sl.getHitSections().get(coord);
						g2.drawImage(rotate(GamePanel.sm.getShipSectionFromShip(section.getShipType(), section.getSection()), section.getRotation()), 0, 0, GamePanel.scaledTileSize, GamePanel.scaledTileSize, null);
						g2.drawImage(GamePanel.sm.getIndicator(SpriteManager.Indicator.COMP_HIT), 0, 0, GamePanel.scaledTileSize, GamePanel.scaledTileSize, null);
					}
				}
				else {
					if (sl.getHitSections().containsKey(coord)) {
						g2.drawImage(GamePanel.sm.getIndicator(SpriteManager.Indicator.HIT), 0, 0, GamePanel.scaledTileSize, GamePanel.scaledTileSize, null);
					}
				}
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		// Will be changed to become more dynamic with the window size, as well as being imported from outside class.
		@Override
		public Dimension getPreferredSize() {
			return new Dimension(GamePanel.scaledTileSize, GamePanel.scaledTileSize);
		}
	}
	
	private BufferedImage rotate(BufferedImage img, Ship.Rotation rotation) {
		// Getting Dimensions of image
		int width = img.getWidth();
		int height = img.getHeight();
		
		// Creating a new buffered image
		BufferedImage newImage = new BufferedImage(img.getWidth(), img.getHeight(), img.getType());
		
		// creating Graphics in buffered image
		Graphics2D g2 = newImage.createGraphics();
		
		// Rotating image by degrees using toRadians()
		// method
		// and setting new dimension t it
		g2.rotate(rotation.rad, width / 2, height / 2);
		g2.drawImage(img, null, 0, 0);
		
		// Return rotated buffer image
		return newImage;
	}
}