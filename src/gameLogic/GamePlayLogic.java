package gameLogic;

public class GamePlayLogic {
	
	public Player player;
	public Bots bot;
	
	public GamePlayLogic() {
		player = new Player();
		bot = new Bots(Bots.BotLevel.EASY);
		Ship playerDestroyer = new Ship(Ship.ShipType.DESTROYER);
		playerDestroyer.setCoords(0, 0);
		player.shipLocations.addUnguessedShip(playerDestroyer);
	}
	
	
}
