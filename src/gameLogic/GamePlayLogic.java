package gameLogic;

import java.awt.*;

public class GamePlayLogic {
	
	public Player player;
	public Bots bot;
	
	public GamePlayLogic() {
		player = new Player();
		bot = new Bots(Bots.BotLevel.EASY);
		Ship playerDestroyer = new Ship(Ship.ShipType.DESTROYER);
		playerDestroyer.setCoords(0, 0);
		player.shipLocations.addUnguessedShip(playerDestroyer);
//		player.shipLocations.shootLocation(new Point(0, 0));
//		player.shipLocations.shootLocation(new Point(1,1));
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 10; j++) {
				player.shipLocations.shootLocation(new Point(i, j));
			}
		}
	}
	
	
}
