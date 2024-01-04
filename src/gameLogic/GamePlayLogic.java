package gameLogic;

import graphics.GamePanel;

import java.awt.*;

public class GamePlayLogic {
	
	public Player player;
	public Bots bot;
	
	public GamePlayLogic() {
		player = new Player();
		bot = new Bots(GamePanel.computerDifficulty);

		/*
		Ship playerDestroyer = new Ship(Ship.ShipType.DESTROYER, Ship.Rotation.DOWN);
		playerDestroyer.setCoords(0, 0);
		player.shipLocations.addUnguessedShip(playerDestroyer);

		// submarine
		Ship playerSubmarine = new Ship(Ship.ShipType.SUBMARINE, Ship.Rotation.LEFT);
		playerSubmarine.setCoords(2, 3);
		player.shipLocations.addUnguessedShip(playerSubmarine);

		// cruiser
		Ship playerCruiser = new Ship(Ship.ShipType.CRUISER, Ship.Rotation.UP);
		playerCruiser.setCoords(4, 4);
		player.shipLocations.addUnguessedShip(playerCruiser);

		// battleship
		Ship playerBattleship = new Ship(Ship.ShipType.BATTLESHIP, Ship.Rotation.RIGHT);
		playerBattleship.setCoords(5, 5);
		player.shipLocations.addUnguessedShip(playerBattleship);
		
		bot.shipLocations.shootLocation(new Point(0, 0));
		bot.shipLocations.shootLocation(new Point(1,1));
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 10; j++) {
				bot.shipLocations.shootLocation(new Point(i, j));
			}
		}
    */
		
		bot.shipLocations.shootLocation(new Point(0, 0));
		bot.shipLocations.shootLocation(new Point(1,1));
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 10; j++) {
				bot.shipLocations.shootLocation(new Point(i, j));
			}
		}
	}
	
	
}
