package gameLogic;

import graphics.*;

import java.awt.*;

import gameLogic.ShipLocations.ShotStatus;

public class GamePlayLogic {

	public Player player;
	public Bots bot;
	public static int turnOrder;
	public static boolean compWon = false;
	public static boolean playerWon = false;

	public GamePlayLogic() {
		player = new Player();
		bot = new Bots(GamePanel.computerDifficulty);
		GamePanel.gameState = GameStates.GAMEPLAY;
	}

	public void gameLoop() {
		ShipLocations playerShipLocations = player.getShipLocations();
		while (GamePanel.gameState == GameStates.GAMEPLAY) {
			if (GamePlayLogic.turnOrder % 2 == 1) {
				switch (this.bot.getLevel()) {
					case EASY -> this.bot.easyBot(playerShipLocations);

					case NORMAL -> this.bot.normalBot(playerShipLocations);

					case HARD -> this.bot.hardBot(playerShipLocations);

					default -> this.bot.impossibleBot(playerShipLocations);
				}

				turnOrder++;
			}

			if (this.gameOver()) {
				GamePanel.gameState = GameStates.GAMEOVER;
			}
		}
	}

	public boolean gameOver() {
		if (player.shipLocations.getUnguessedSections().size() == 0) {
			compWon = true;
		}
		if (bot.shipLocations.getUnguessedSections().size() == 0) {
			playerWon = true;
		}

		return compWon || playerWon;
	}
}
