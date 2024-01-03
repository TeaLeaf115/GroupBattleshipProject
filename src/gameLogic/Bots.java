package gameLogic;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
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

	private final BotLevel botLevel;
	private ArrayList<Point> possibleGuesses;
	public ShipLocations shipLocations;
	private ArrayList<Ship> ships;

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

		this.shipLocations = new ShipLocations();
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

	public BotLevel getLevel() {
		return botLevel;
	}

	public void checkLevel(ShipLocations opponentLocations) {
		switch (this.botLevel) {
			case EASY -> easyBot(opponentLocations);

			case NORMAL -> normalBot(opponentLocations);

			case HARD -> hardBot(opponentLocations);

			default -> impossibleBot(opponentLocations);
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

	public void easyBot(ShipLocations opponentLocations) {
		// picks a random unguessed location
		Point guessLocation = this.possibleGuesses.remove(
				this.random.nextInt(this.possibleGuesses.size()));

		opponentLocations.shootLocation(guessLocation);
	}

	public void normalBot(ShipLocations opponentLocations) {
		Integer[][] heatMap = new Integer[GamePanel.maxBoardCol][GamePanel.maxBoardRow];

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

	public static Point readHeatMap(Integer[][] heatMap) throws IndexOutOfBoundsException {
		if (heatMap.length == 0 || heatMap[0].length == 0) {
			throw new IndexOutOfBoundsException();
		}

		// determines which point has the highest weight
		int shootX = 0;
		int shootY = 0;
		int maxWeight = heatMap[0][0];

		for (int x = 0; x < heatMap.length; x++) {
			int weight = Collections.max(Arrays.asList(heatMap[x]));

			if (weight > maxWeight) {
				shootX = x;
				shootY = Arrays.asList(heatMap[x]).indexOf(weight);
				maxWeight = weight;
			}
		}

		return new Point(shootX, shootY);
	}
}
