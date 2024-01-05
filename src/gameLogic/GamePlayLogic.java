package gameLogic;

import graphics.*;

public class GamePlayLogic {

	public Player player;
	public Bots bot;
	public static boolean compWon = false;
	public static boolean playerWon = false;

	public GamePlayLogic() {
		player = new Player();
		bot = new Bots();
		GamePanel.gameState = GameStates.GAMEPLAY;
	}

	public void gameLoop() {
		ShipLocations playerShipLocations = this.player.getShipLocations();
		if (!this.gameOver()) {
			this.bot.shootOpponent(playerShipLocations);
		} 
		
		if (this.gameOver()){
			GamePanel.gameState = GameStates.GAMEOVER;
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
