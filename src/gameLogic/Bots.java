package gameLogic;

import graphics.GamePanel;
import java.util.*;
import java.awt.Point;


public class Bots {
	public enum BotLevel {
		EASY,
		NORMAL,
		HARD,
		IMPOSSIBLE
	}

	private BotLevel botLevel;
	private ArrayList<Point> possibleGuesses;
	private Locations 

	private final Random random = new Random();

	public Bots(BotLevel botLevel) {
		this.botLevel = botLevel;
		possibleGuesses = new ArrayList<>();

		// fills possibleGuesses with all potential guesses
		for (int x = 0; x < GamePanel.maxBoardCol; x++) {
			for (int y = 0; y < GamePanel.maxBoardRow; y++) {
				possibleGuesses.add(new Point(x, y));
			}
		}
	}

	public BotLevel getLevel() {
		return botLevel;
	}

	public Point checkLevel() {
		switch (this.botLevel) {
			case BotLevel.EASY -> return easyBot();

			case BotLevel.NORMAL -> return normalBot();

			case BotLevel.HARD -> return hardBot();

			default -> return impossibleBot();
		}
	}
	
	public void easyBot(Locations opponentLocations) {
		// picks a random unguessed location
		Point guessLocation = this.possibleGuesses.remove(
			this.random.nextInt(this.possibleGuesses.size()));

		opponentLocations.shootLocation(guessLocation);
	}
	public boolean normalBot() {
		return false;
	}

	public boolean hardBot() {
		return true;
	}

	public void impossibleBot(Location l) {
		int numberOfFate = Randomizer.nextInt(0, 99);
		if (numberOfFate <= 84) {
			int randIdx = Randomizer.nextInt(0, l.unguessedSections.size());
			int count = 0;
			for (Map.Entry<Point, shipSection> entry : l.unguessedSections.entrySet()) {
				if (count == randIdx) {
					// Found a match! Declare hit and potentially update the ship section state.
					shipSection = entry.getValue();
					shipSection.setHit(true);
					// Update ship section if needed
					break; // No need to keep searching after a hit
					count++;
				}
			}

		} else {
			Point p = easyBot();
			checkHit(p, l);
		}
	}

	public boolean checkHit(Point p, Location l) {
		for (Map.Entry<Point, shipSection> entry : l.unguessedSections.entrySet()) {
			if (entry.getKey().equals(p)) {
				// Found a match! Declare hit and potentially update the ship section state.
				hit = true;
				shipSection = entry.getValue(); // Update ship section if needed
				break; // No need to keep searching after a hit
				shipSection.setHit(true);
			}
		}
	}

	public void makeGuess(Locations opponentLocations) {

	}
}
