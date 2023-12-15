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
	private ShipLocations shipLocations;

	private final Random random = new Random();

	private final double impossibleProb = 0.85;

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

	public void checkLevel(ShipLocations opponentLocations) {
		switch (this.botLevel) {
			case EASY -> easyBot(shipLocations);

			case NORMAL -> normalBot(opponentLocations);

			case HARD -> hardBot(opponentLocations);

			default -> impossibleBot(opponentLocations);
		}
	}

	public void easyBot(ShipLocations opponentLocations) {
		// picks a random unguessed location
		Point guessLocation = this.possibleGuesses.remove(
				this.random.nextInt(this.possibleGuesses.size()));

		opponentLocations.shootLocation(guessLocation);
	}

	public boolean normalBot(ShipLocations opponentLocations) {
		return false;
	}

	public void hardBot(ShipLocations opponentLocations) {
		int[][] heatMap = new int[GamePanel.maxBoardCol][GamePanel.maxBoardRow];

		// sets each missed location to lowest priority
		for (Point missedLocations : opponentLocations.getMisses()) {
			heatMap[(int) missedLocations.getX()][(int) missedLocations.getY()] = Integer.MIN_VALUE;
		}

		// checks rows for adjacent points
		for (int row = 0; row < GamePanel.maxBoardCol; row++) {

		}

		// checks columns for adjacent points
		for (int col = 0; col < GamePanel.maxBoardRow; col++) {
			
		}
	}

	public void impossibleBot(ShipLocations opponentLocations) {
		// only shoot opponent locations when impossibleProb is true
		if (random.nextDouble() > this.impossibleProb)  {
			return;
		}

		// selects one of the opponent's ships and shoots its location
		Point[] opponentPoints = opponentLocations.getUnguessedSections()
				.keySet()
				.toArray(Point[]::new);

		Point guessLocation = opponentPoints[random.nextInt(opponentPoints.length)];
		this.possibleGuesses.remove(guessLocation);
		opponentLocations.shootLocation(guessLocation); // guaranteed hit
	}

	// public void impossibleBot(Location l) {
	// int numberOfFate = Randomizer.nextInt(0, 99);
	// if (numberOfFate <= 84) {
	// int randIdx = Randomizer.nextInt(0, l.unguessedSections.size());
	// int count = 0;
	// for (Map.Entry<Point, shipSection> entry : l.unguessedSections.entrySet()) {
	// if (count == randIdx) {
	// // Found a match! Declare hit and potentially update the ship section state.
	// shipSection = entry.getValue();
	// shipSection.setHit(true);
	// // Update ship section if needed
	// break; // No need to keep searching after a hit
	// count++;
	// }
	// }

	// } else {
	// Point p = easyBot();
	// checkHit(p, l);
	// }
	// }

	// public boolean checkHit(Point p, Location l) {
	// for (Map.Entry<Point, shipSection> entry : l.unguessedSections.entrySet()) {
	// if (entry.getKey().equals(p)) {
	// // Found a match! Declare hit and potentially update the ship section state.
	// hit = true;
	// shipSection = entry.getValue(); // Update ship section if needed
	// break; // No need to keep searching after a hit
	// shipSection.setHit(true);
	// }
	// }
	// }
}
