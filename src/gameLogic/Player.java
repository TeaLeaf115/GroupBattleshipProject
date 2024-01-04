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

	public ShipLocations getShipLocations() {
		return this.shipLocations;
	}

	public void setShipLocations() {
		this.shipLocations.addUnguessedShips(this.ships);
	}

	public ArrayList<Ship> getShips() {
		return this.ships;
	}

	/**
	 * Returns an arraylist of all ship sections of ships
	 * 
	 * @return an arraylist of all ship sections
	 */
	public ArrayList<ShipSection> getShipSections() {
		ArrayList<ShipSection> shipSections = new ArrayList<>();
		shipSections.addAll(this.shipLocations.getUnguessedSections().values());
		shipSections.addAll(this.shipLocations.getHitSections().values());

		return shipSections;
	}

	/**
	 * Returns whether all ships are placed
	 * 
	 * @return whether all ships are placed
	 */
	public boolean isShipsPlaced() {
		for (Ship ship : this.ships) {
			if (!ship.isPlaced()) {
				return false;
			}
		}
		return true;
	}
  
}