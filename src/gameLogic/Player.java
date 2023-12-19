package gameLogic;

import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;

public class Player extends ShipLocations {

	public Player() {
		super();
	}

	public ShotStatus fireAt(Point coords) {
		return super.shootLocation(coords); // Leverage inherited method
	}

	// Convenient method to check if all ships are sunken
	public boolean hasWon() {
		return super.getUnguessedSections().isEmpty(); // Leverage inherited data
	}

	// Convenient method to get number of remaining ship sections
	public int getRemainingSections() {
		return super.getUnguessedSections().size(); // Leverage inherited data
	}

	public ArrayList<Point> getMisses() {
		return super.getMisses();
	}

	// Convenient method to get hit map for visualizing progress
	public HashMap<Point, ShipSection> getHitMap() {
		return super.getHitSections(); // Leverage inherited data
	}

	public void viewUserGrid() {
		// Output a users own grid
	}

	public void viewShots() {
		// output a user's hit and misses of the bot's ships
	}
}