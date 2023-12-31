package gameLogic;

import java.awt.*;

public class GamePlayLogic {
	
	public Player player;
	public Bots bot;
	
	public GamePlayLogic() {
		player = new Player();
		bot = new Bots(Bots.BotLevel.EASY);
		Ship playerDestroyer = new Ship(Ship.ShipType.DESTROYER, Ship.Rotation.LEFT);
		Ship playerSubmarine = new Ship(Ship.ShipType.SUBMARINE, Ship.Rotation.DOWN);
		Ship playerCruiser = new Ship(Ship.ShipType.CRUISER, Ship.Rotation.RIGHT);
		Ship playerBattleship = new Ship(Ship.ShipType.BATTLESHIP, Ship.Rotation.UP);
		playerDestroyer.setCoords(0, 0);
		playerSubmarine.setCoords(2, 3);
		playerCruiser.setCoords(4, 4);
		playerBattleship.setCoords(5, 5);
		player.shipLocations.addUnguessedShip(playerDestroyer);
		player.shipLocations.addUnguessedShip(playerCruiser);
		player.shipLocations.addUnguessedShip(playerBattleship);
		player.shipLocations.addUnguessedShip(playerSubmarine);
//		player.shipLocations.shootLocation(new Point(0, 0));
//		player.shipLocations.shootLocation(new Point(1,1));
//		for (int i = 0; i < 10; i++) {
//			for (int j = 0; j < 10; j++) {
//				player.shipLocations.shootLocation(new Point(i, j));
//			}
//		}
	}
	
	
}
