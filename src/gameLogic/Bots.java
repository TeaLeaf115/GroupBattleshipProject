package gameLogic;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import gameLogic.Ship.Rotation;
import gameLogic.Ship.ShipType;
import graphics.GamePanel;

public class Bots {
	public enum BotLevel {
		EASY,
		NORMAL,
		HARD,
		IMPOSSIBLE
	}

	private ArrayList<Point> possibleGuesses;
	private ShipLocations shipLocations;

	private ArrayList<Ship> ships;

	private final Random random = new Random();
	private final double impossibleProb = 0.85;

	public Bots() {
		possibleGuesses = new ArrayList<>();

		// fills possibleGuesses with all potential guesses
		for (int x = 0; x < GamePanel.maxBoardCol; x++) {
			for (int y = 0; y < GamePanel.maxBoardRow; y++) {
				possibleGuesses.add(new Point(x, y));
			}
		}

		this.shipLocations = new ShipLocations();
		this.setShips();
		
	}

	public ShipLocations getShipLocations() {
		return this.shipLocations;
	}

	public void setShips() {
		this.ships = new ArrayList<>();

		List<Rotation> rotations = Arrays.asList(Rotation.values());
		for (ShipType shipType : ShipType.values()) {
			// gets random rotation and ship length
			Rotation rotation = rotations.get(this.random.nextInt(Rotation.values().length));
			Ship ship = new Ship(shipType, rotation);

			// creates bounds for randomization
			int maxX = GamePanel.maxBoardCol;
			int maxY = GamePanel.maxBoardRow;

			if (rotation == Rotation.LEFT || rotation == Rotation.RIGHT) {
				maxX -= ship.getShipLength();

			} else {
				maxY -= ship.getShipLength();
			}

			// keeps generating placement points until valid
			boolean validPosition = false;
			while (!validPosition) {
				int shipX = this.random.nextInt(maxX);
				int shipY = this.random.nextInt(maxY);
				ship.setCoords(new Point(shipX, shipY));

				validPosition = true;
				for (Ship other : this.ships) {
					if (ship.intersect(other)) {
						validPosition = false;
						break;
					}
				}
			}

			// adds ship
			this.shipLocations.addUnguessedShip(ship);
			this.ships.add(ship);
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

	public void shootOpponent(ShipLocations opponentLocations) {
		switch (GamePanel.computerDifficulty) {
			case EASY -> this.easyBot(opponentLocations);
			case NORMAL -> this.normalBot(opponentLocations);
			case HARD -> this.hardBot(opponentLocations);
			default -> this.impossibleBot(opponentLocations);
		}
	}

	public void easyBot(ShipLocations opponentLocations) {
		// picks a random unguessed location
		Point guessLocation = this.possibleGuesses.remove(
				this.random.nextInt(this.possibleGuesses.size()));

		opponentLocations.shootLocation(guessLocation);
	}

	public void normalBot(ShipLocations opponentLocations) {
		int[][] heatMap = new int[GamePanel.maxBoardCol][GamePanel.maxBoardRow];

		// sets each missed location to lowest priority
		for (Point missedLocations : opponentLocations.getMisses()) {
			int x = (int) missedLocations.getX();
			int y = (int) missedLocations.getY();
			heatMap[x][y] = Integer.MIN_VALUE;
		}

		for (Point hitLocations : opponentLocations.getHitSections().keySet()) {
			// sets each hit location to lowest priority
			int x = (int) hitLocations.getX();
			int y = (int) hitLocations.getY();

			// Set hit location to lowest priority
			heatMap[x][y] = Integer.MIN_VALUE;

			// marks all positions adjacent to hit location
			if (x - 1 >= 0)
				heatMap[x - 1][y]++;

			if (x + 1 < heatMap.length)
				heatMap[x + 1][y]++;

			if (y - 1 >= 0)
				heatMap[x][y - 1]++;

			if (y + 1 < heatMap[0].length)
				heatMap[x][y + 1]++;
		}

		Point guessLocation = readHeatMap(heatMap);
		this.possibleGuesses.remove(guessLocation);
		opponentLocations.shootLocation(guessLocation);
	}

	public void hardBot(ShipLocations opponentLocations) {
		int[][] heatMap = new int[GamePanel.maxBoardCol][GamePanel.maxBoardRow];

		// Set each missed location to lowest priority
		for (Point missedLocation : opponentLocations.getMisses()) {
			int x = (int) missedLocation.getX();
			int y = (int) missedLocation.getY();
			heatMap[x][y] = Integer.MIN_VALUE;
		}

		// Update heat map based on hit locations
		for (Point hitLocation : opponentLocations.getHitSections().keySet()) {
			int x = (int) hitLocation.getX();
			int y = (int) hitLocation.getY();

			// Set hit location to lowest priority
			heatMap[x][y] = Integer.MIN_VALUE;

			// Increment probabilities for adjacent positions
			updateHeatMapAdjacent(heatMap, x, y);
		}

		Point guessLocation = readHeatMap(heatMap);
		this.possibleGuesses.remove(guessLocation);
		opponentLocations.shootLocation(guessLocation);
	}

	public void impossibleBot(ShipLocations opponentLocations) {
		// only shoot opponent locations when impossibleProb is true
		if (random.nextDouble() > this.impossibleProb) {
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

	private void updateHeatMapAdjacent(int[][] heatMap, int x, int y) {
		// Mark all positions adjacent to hit location
		if (x - 1 >= 0)
			heatMap[x - 1][y]++;

		if (x + 1 < heatMap.length)
			heatMap[x + 1][y]++;

		if (y - 1 >= 0)
			heatMap[x][y - 1]++;

		if (y + 1 < heatMap[0].length)
			heatMap[x][y + 1]++;
	}

	public Point readHeatMap(int[][] heatMap) throws IndexOutOfBoundsException {
		if (heatMap.length == 0 || heatMap[0].length == 0) {
			throw new IndexOutOfBoundsException();
		}

		// Determines which point has the highest weight
		int shootX = 0;
		int shootY = 0;

		int maxWeight = heatMap[0][0];
		ArrayList<Point> maxWeights = new ArrayList<>();

		for (int x = 0; x < heatMap.length; x++) {
			for (int y = 0; y < heatMap[x].length; y++) {
				int weight = heatMap[x][y];

				if (weight > maxWeight) {
					maxWeight = weight;

					maxWeights.clear();
					maxWeights.add(new Point(x, y));
				}

				else if (weight == maxWeight) {
					maxWeights.add(new Point(x, y));
				}
			}
		}

		return maxWeights.get(random.nextInt(maxWeights.size()));
	}
}
