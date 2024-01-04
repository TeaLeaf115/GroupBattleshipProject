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
		repaint();
	}
	
	public void update() {
		// This is used for anything that will be updated on the gameloop clock, like animations or logic that needs to be constantly updated.
		// If you have any animation stuff you can call it here
		playerPanel.board.update();
		computerPanel.compBoard.update();
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		g2.drawImage(TitleScreen.logo,
				((int) GamePanel.windowSize.getWidth() / 2) - (int) (TitleScreen.logo.getWidth() * 2.5 / 2),
				(int) GamePanel.windowSize.getHeight() / 16,
				(int)(TitleScreen.logo.getWidth() * 2.5),
				(int)(TitleScreen.logo.getHeight() * 2.5),
				null);
	}
}

class PlayerBoard extends JPanel {
	public Board board;
	BufferedImage computerBoardOverlay = GamePanel.sm.getGuessingOverlay();
	BufferedImage titleHalf = TitleScreen.logo;
	int scaledComputerBoardOverlayWidth = (int)Math.ceil(computerBoardOverlay.getWidth() * getSpriteScaleMultiplier());
	int scaledComputerBoardOverlayHeight = (int)Math.ceil(computerBoardOverlay.getHeight() * getSpriteScaleMultiplier());
	
	public PlayerBoard() {
		setLayout(null); // Set layout to null for absolute positioning
		setBackground(new Color(0x848482));
		board = new Board(GameplayScreen.gl.bot.shipLocations);
		board.shipsVisible(true);
		board.setBounds((int)Math.ceil(6.5*GamePanel.scaledTileSize)-1,
				((int) GamePanel.getScreenSize().getHeight()/2 + 7*GamePanel.scaledTileSize)+4,
				GamePanel.boardWidth,
				GamePanel.boardHeight);
		add(board);
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		g2.drawImage(computerBoardOverlay,
				16,
				(getHeight()/2) - (scaledComputerBoardOverlayHeight /2),
				scaledComputerBoardOverlayWidth,
				scaledComputerBoardOverlayHeight,
				null);
		g2.drawImage(titleHalf,
				(getWidth() - (titleHalf.getWidth() * 2) /2),
				(int) GamePanel.windowSize.getHeight() / 32,
				titleHalf.getWidth() * 2,
				titleHalf.getHeight() * 2,
				null);
	}
}

class ComputerBoard extends JPanel {
	public Board compBoard;
	BufferedImage titleHalf = TitleScreen.logo;
	BufferedImage playerBoardOverlay = GamePanel.sm.getPlacementOverlay();
	int scaledPlayerBoardOverlayWidth = (int)Math.ceil(playerBoardOverlay.getWidth() * getSpriteScaleMultiplier());
	int scaledPlayerBoardOverlayHeight = (int)Math.ceil(playerBoardOverlay.getHeight() * getSpriteScaleMultiplier());
	
	public ComputerBoard() {
		setLayout(null);
		setBackground(new Color(0x848482));
		
		// Create the board panel for the computer
		compBoard = new Board(GameplayScreen.gl.player.shipLocations);

		compBoard.setBounds((6*GamePanel.scaledTileSize)+6,
				((int) GamePanel.getScreenSize().getHeight()/2 + 7*GamePanel.scaledTileSize) +12,
				GamePanel.boardWidth,
				GamePanel.boardHeight);
		add(compBoard);
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		// Code for drawing goes here
		g2.drawImage(playerBoardOverlay,
				8,
				(getHeight()/2) - (scaledPlayerBoardOverlayHeight/2),
				scaledPlayerBoardOverlayWidth,
				scaledPlayerBoardOverlayHeight,
				null);
		g2.drawImage(titleHalf,
				(-((titleHalf.getWidth() * 2)) / 2),
				(int) GamePanel.windowSize.getHeight() / 32,
				titleHalf.getWidth() * 2,
				titleHalf.getHeight() * 2,
				null);
	}
}