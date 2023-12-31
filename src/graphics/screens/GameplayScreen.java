package graphics.screens;

import gameLogic.GamePlayLogic;
import graphics.Board;
import graphics.GamePanel;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

import static graphics.GamePanel.getSpriteScaleMultiplier;

public class GameplayScreen extends JPanel {
	PlayerBoard playerPanel = new PlayerBoard();
	ComputerBoard computerPanel = new ComputerBoard();
	
	public static GamePlayLogic gl = new GamePlayLogic();
	public GameplayScreen() {
		setLayout(new GridLayout(1, 2));
		setBounds(0, 0, 800, 600);
		
		// Add player and computer panels to the game panel
		add(playerPanel);
		add(computerPanel);
	}
	
	public void draw() {
		// Don't touch this method.
		computerPanel.repaint();
		playerPanel.repaint();
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		Graphics2D g2 = (Graphics2D) g;
		BufferedImage logo = GamePanel.sm.getPlacementOverlay();
		// Code for drawing goes here
		g2.drawImage(logo,
				((int) GamePanel.windowSize.getWidth() / 2) - (int) (logo.getWidth() * 2.5 / 2),
				(int) GamePanel.windowSize.getHeight() / 2,
				(int)(logo.getWidth() * 2.5),
				(int)(logo.getHeight() * 2.5),
				null);
	}
	
	public void update() {
		// This is used for anything that will be updated on the gameloop clock, like animations or logic that needs to be constantly updated.
		// If you have any animation stuff you can call it here
		playerPanel.board.update();
		computerPanel.board.update();
	}
}

class PlayerBoard extends JPanel {
	public Board board;
	BufferedImage computerBoardOverlay = GamePanel.sm.getGuessingOverlay();
	int scaledComputerBoardOverlayWidth = (int)Math.ceil(computerBoardOverlay.getWidth() * getSpriteScaleMultiplier());
	int scaledComputerBoardOverlayHeight = (int)Math.ceil(computerBoardOverlay.getHeight() * getSpriteScaleMultiplier());
	
	public PlayerBoard() {
		setLayout(null); // Set layout to null for absolute positioning
//		setBounds(position.x, position.y, GamePanel.boardWidth, GamePanel.boardHeight);
//		setBorder(BorderFactory.createTitledBorder("Player"));
		setBackground(Color.BLUE);
		// Create the board panel for the player
		board = new Board(GameplayScreen.gl.bot.shipLocations);
		board.shipsVisible(false);
		board.setBounds(6*GamePanel.scaledTileSize,
				(int) GamePanel.getScreenSize().getHeight()/2 + 6*GamePanel.scaledTileSize,
				GamePanel.boardWidth,
				GamePanel.boardHeight);
		add(board);
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
//		System.out.println(getWidth());
		// Code for drawing goes here
		g2.drawImage(computerBoardOverlay,
				0,
				(getHeight()/2) - (scaledComputerBoardOverlayHeight /2),
				scaledComputerBoardOverlayWidth,
				scaledComputerBoardOverlayHeight,
				null);
//		g2.drawLine(0, getHeight()/2, getWidth(), getHeight()/2);
	}
}

class ComputerBoard extends JPanel {
	public Board board;
	BufferedImage playerBoardOverlay = GamePanel.sm.getPlacementOverlay();
	int scaledPlayerBoardOverlayWidth = (int)Math.ceil(playerBoardOverlay.getWidth() * getSpriteScaleMultiplier());
	int scaledPlayerBoardOverlayHeight = (int)Math.ceil(playerBoardOverlay.getHeight() * getSpriteScaleMultiplier());
	
	public ComputerBoard() {
		setLayout(null); // Set layout to null for absolute positioning
//		setBounds(position.x, position.y, GamePanel.boardWidth, GamePanel.boardHeight);
//		setBorder(BorderFactory.createTitledBorder("Computer"));
		setBackground(Color.GREEN);
		
		// Create the board panel for the computer
		board = new Board(GameplayScreen.gl.player.shipLocations);

		board.setBounds(6*GamePanel.scaledTileSize,
				(int) GamePanel.getScreenSize().getHeight()/2 + 6*GamePanel.scaledTileSize +8,
				GamePanel.boardWidth,
				GamePanel.boardHeight);
		
		add(board);
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
//		System.out.println(getWidth());
		// Code for drawing goes here
		g2.drawImage(playerBoardOverlay,
				0,
				(getHeight()/2) - (scaledPlayerBoardOverlayHeight/2),
				scaledPlayerBoardOverlayWidth,
				scaledPlayerBoardOverlayHeight,
				null);
//		g2.drawLine(0, getHeight()/2, getWidth(), getHeight()/2);
	}
}