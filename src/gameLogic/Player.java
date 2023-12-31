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
	
	public ShipLocations getShipLocations() {
		return shipLocations;
	}
}