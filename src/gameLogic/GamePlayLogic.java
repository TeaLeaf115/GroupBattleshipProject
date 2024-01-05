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
		bot = new Bots();
		GamePanel.gameState = GameStates.GAMEPLAY;
	}

	public void computerTurn() {
		ShipLocations playerShipLocations = this.player.getShipLocations();

		if (GamePanel.gameState == GameStates.GAMEPLAY) {
			System.out.println("Shoot");
			if (!this.gameOver()) {
				this.bot.shootOpponent(playerShipLocations);
			}
		}
		
		turnOrder++;
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
