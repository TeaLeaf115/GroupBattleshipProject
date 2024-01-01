package gameLogic;

import java.awt.*;

public class GamePlayLogic {
	
	public Player player;
	public Bots bot;
	
	public GamePlayLogic() {
		player = new Player();
		bot = new Bots(Bots.BotLevel.EASY);

		// destroyer
		Ship playerDestroyer = new Ship(Ship.ShipType.DESTROYER, Ship.Rotation.LEFT);
		playerDestroyer.setCoords(0, 0);
		player.shipLocations.addUnguessedShip(playerDestroyer);

		// submarine
		Ship playerSubmarine = new Ship(Ship.ShipType.SUBMARINE, Ship.Rotation.DOWN);
		playerSubmarine.setCoords(2, 3);
		player.shipLocations.addUnguessedShip(playerSubmarine);

		// cruiser
		Ship playerCruiser = new Ship(Ship.ShipType.CRUISER, Ship.Rotation.RIGHT);
		playerCruiser.setCoords(4, 4);
		player.shipLocations.addUnguessedShip(playerCruiser);

		// battleship
		Ship playerBattleship = new Ship(Ship.ShipType.BATTLESHIP, Ship.Rotation.UP);
		playerBattleship.setCoords(5, 5);
		player.shipLocations.addUnguessedShip(playerBattleship);
		
//		player.shipLocations.shootLocation(new Point(0, 0));
//		player.shipLocations.shootLocation(new Point(1,1));
//		for (int i = 0; i < 10; i++) {
//			for (int j = 0; j < 10; j++) {
//				player.shipLocations.shootLocation(new Point(i, j));
//			}
//		}
	}
	
	
}
