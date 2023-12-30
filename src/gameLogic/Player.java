package gameLogic;

import java.util.ArrayList;

import gameLogic.Ship.ShipType;

public class Player {
	public ShipLocations shipLocations;
	public ArrayList<Ship> ships;
	

	public Player() {
		this.shipLocations = new ShipLocations();
		this.ships = new ArrayList<>();
		for (ShipType shipType : ShipType.values()) {
			this.ships.add(new Ship(shipType));
		}

		
	}

	public ArrayList<Ship> getShips() {
		return this.ships;
	}

	public ArrayList<ShipSection> getShipSections() {
		ArrayList<ShipSection> shipSections = new ArrayList<>();
		shipSections.addAll(this.shipLocations.getUnguessedSections().values());
		shipSections.addAll(this.shipLocations.getHitSections().values());

		return shipSections;
	}

	// public ShotStatus fireAt(Point coords) {
	// 	return super.shootLocation(coords); // Leverage inherited method
	// }

	// // Convenient method to check if all ships are sunken
	// public boolean hasWon() {
	// 	return super.getUnguessedSections().isEmpty(); // Leverage inherited data
	// }

	// // Convenient method to get number of remaining ship sections
	// public int getRemainingSections() {
	// 	return super.getUnguessedSections().size(); // Leverage inherited data
	// }

	// public ArrayList<Point> getMisses() {
	// 	return super.getMisses();
	// }

	// // Convenient method to get hit map for visualizing progress
	// public HashMap<Point, ShipSection> getHitMap() {
	// 	return super.getHitSections(); // Leverage inherited data
	// }

	// public void viewUserGrid() {
	// 	// Output a users own grid
	// }

	// public void viewShots() {
	// 	// output a user's hit and misses of the bot's ships
	// }
}