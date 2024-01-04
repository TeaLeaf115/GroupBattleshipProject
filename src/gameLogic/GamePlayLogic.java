package gameLogic;

import graphics.*;
import graphics.screens.GameOverScreen;

import java.awt.*;

public class GamePlayLogic {

	public Player player;
	public Bots bot;
	public static int turnOrder = 0;
	public static boolean compWon = false;
	public static boolean playerWon = false;

	public GamePlayLogic() {
		player = new Player();
		bot = new Bots(GamePanel.computerDifficulty);
		GamePanel.gameState = GameStates.GAMEPLAY;
	}

	public void ComputerTurn() {
		ShipLocations playerShipLocations = this.player.getShipLocations();
		if (GamePanel.gameState == GameStates.GAMEPLAY) {
			
			this.bot.shootOpponent(playerShipLocations);
			turnOrder++;
			if (this.gameOver()) {
				try {
					Robot rob = new Robot();
					GamePanel gp = GamePanel.getInstance();
					GameOverScreen.boardSS = rob.createScreenCapture(new Rectangle(gp.getX(), gp.getY()+30, gp.getWidth(), gp.getHeight()));
				}
				catch (AWTException e) {
					e.printStackTrace();
				}
				
				
				GamePanel.gameState = GameStates.GAMEOVER;
			}
		}
	}

	public boolean gameOver() {
		ShipLocations playerShipLocations = this.player.getShipLocations();
		if (playerShipLocations.getUnguessedSections().size() == 0) {
			compWon = true;
		}

		ShipLocations botShipLocations = this.bot.getShipLocations();
		if (botShipLocations.getUnguessedSections().size() == 0) {
			playerWon = true;
		}

		return compWon || playerWon;
	}
}
