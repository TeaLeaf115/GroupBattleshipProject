package graphics.screens;

import gameLogic.GamePlayLogic;
import graphics.Board;
import graphics.GamePanel;
import graphics.GameStates;

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
		if (GamePlayLogic.compWon || GamePlayLogic.playerWon) {
			draw();
			try {
				Robot rob = new Robot();
				GamePanel gp = GamePanel.getInstance();
				GameOverScreen.boardSS = rob.createScreenCapture(new Rectangle(gp.getX()+8, gp.getY()+30, gp.getWidth(), gp.getHeight()));
			}
			catch (AWTException e) {
				e.printStackTrace();
			}
			
			GamePanel.gameState = GameStates.GAMEOVER;
			GamePanel.screenChange = true;
		}
		playerPanel.board.update();
		computerPanel.compBoard.update();
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		g2.drawImage(TitleScreen.logo,
				((int) GamePanel.currentScreenSize.getWidth() / 2) - (int) (TitleScreen.logo.getWidth() * 2.5 / 2),
				(int) GamePanel.currentScreenSize.getHeight() / 16,
				(int) (TitleScreen.logo.getWidth() * 2.5),
				(int) (TitleScreen.logo.getHeight() * 2.5),
				null);
	}
}

class PlayerBoard extends JPanel {
	public Board board;
	BufferedImage computerBoardOverlay = GamePanel.sm.getGuessingOverlay();
	BufferedImage titleHalf = TitleScreen.logo;
	int scaledComputerBoardOverlayWidth = (int) Math.ceil(computerBoardOverlay.getWidth() * getSpriteScaleMultiplier());
	int scaledComputerBoardOverlayHeight = (int) Math
			.ceil(computerBoardOverlay.getHeight() * getSpriteScaleMultiplier());
	int scaledComputerBoardOverlayX = 16;
	int scaledComputerBoardOverlayY = (GamePanel.getScreenSize().height / 2) - (scaledComputerBoardOverlayHeight / 2);

	public PlayerBoard() {
		setLayout(null); // Set layout to null for absolute positioning
		setBackground(new Color(0x848482));

		board = new Board(GameplayScreen.gl.bot.getShipLocations());
		board.shipsVisible(false);

		board.setBounds(
				(int) Math.ceil(scaledComputerBoardOverlayX+(6 * GamePanel.scaledTileSize))-2,
				(int) Math.ceil(scaledComputerBoardOverlayY + (2 * GamePanel.scaledTileSize))-1,
				GamePanel.boardWidth,
				GamePanel.boardHeight);

		add(board);
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		g2.drawImage(computerBoardOverlay,
				scaledComputerBoardOverlayX,
				scaledComputerBoardOverlayY,
				scaledComputerBoardOverlayWidth,
				scaledComputerBoardOverlayHeight,
				null);

		g2.drawImage(titleHalf,
				(getWidth() - (titleHalf.getWidth() * 2) / 2),
				(int) GamePanel.currentScreenSize.getHeight() / 32,
				titleHalf.getWidth() * 2,
				titleHalf.getHeight() * 2,
				null);
	}
}

class ComputerBoard extends JPanel {
	public Board compBoard;
	BufferedImage titleHalf = TitleScreen.logo;
	BufferedImage playerBoardOverlay = GamePanel.sm.getPlacementOverlay();
	int scaledPlayerBoardOverlayWidth = (int) Math.ceil(playerBoardOverlay.getWidth() * getSpriteScaleMultiplier());
	int scaledPlayerBoardOverlayHeight = (int) Math.ceil(playerBoardOverlay.getHeight() * getSpriteScaleMultiplier());
	int scaledPlayerBoardOverlayX = 8;
	int scaledPlayerBoardOverlayY = (GamePanel.getScreenSize().height / 2) - (scaledPlayerBoardOverlayHeight / 2);
	
	public ComputerBoard() {
		setLayout(null);
		setBackground(new Color(0x848482));

		// Create the board panel for the computer
		compBoard = new Board(GameplayScreen.gl.player.shipLocations);
		compBoard.shipsVisible(true);
		compBoard.setBounds(
				(int) Math.ceil(scaledPlayerBoardOverlayX+(6 * GamePanel.scaledTileSize))-2,
				(int) Math.ceil(scaledPlayerBoardOverlayY + (3 * GamePanel.scaledTileSize))-1,
				GamePanel.boardWidth,
				GamePanel.boardHeight);

		add(compBoard);
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		// Code for drawing goes here
		g2.drawImage(playerBoardOverlay,
				scaledPlayerBoardOverlayX,
				scaledPlayerBoardOverlayY,
				scaledPlayerBoardOverlayWidth,
				scaledPlayerBoardOverlayHeight,
				null);

		g2.drawImage(titleHalf,
				(-((titleHalf.getWidth() * 2)) / 2),
				(int) GamePanel.currentScreenSize.getHeight() / 32,
				titleHalf.getWidth() * 2,
				titleHalf.getHeight() * 2,
				null);
	}
}