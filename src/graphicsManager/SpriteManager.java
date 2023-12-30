package graphicsManager;

import graphics.GamePanel;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;

/**
 * The SpriteManager class handles the management and retrieval of sprites from a spritesheet.
 * It organizes different tile sets for water, ship indicators, and various ship types.
 *
 * @see Section
 * @see Indicator
 * @see ImageSectionOutOfBounds
 */
public class SpriteManager {
	
	// The object responsible for reading the spritesheet.
	private SpriteSheetReader ssr;
	
	// An array containing every tile from the spritesheet.
	private BufferedImage[] tileset;
	
	// Enum for specifying a section of a ship.
	public enum Section {
		FRONT, MID_1, MID_2, MID_3, BACK
	}
	
	// Enum for ship indicators.
	public enum Indicator {
		EMPTY, HIT, COMP_HIT, MISS
	}
	
	// Arrays for animated water background sprites.
	private final BufferedImage[] waterTileset;
	
	// Arrays for ship indicators.
	private final BufferedImage[] indicatorTileset;
	
	// Arrays for each ship type.
	private final BufferedImage[] destroyerTileset;
	private final BufferedImage[] cruiserTileset;
	private final BufferedImage[] submarineTileset;
	private final BufferedImage[] battleshipTileset;
	private final BufferedImage[] carrierTileset;
	
	// Array for storing full ship sprites.
	public final BufferedImage[] fullShipSprites;
	
	private BufferedImage[] startButtonSprites;
	
	private BufferedImage guessingOverlay;
	private BufferedImage placementOverlay;
	private BufferedImage logo;
	private BufferedImage titleScreen;
	
	
	/**
	 * Constructs a SpriteManager instance and initializes various tile sets based on a sprite sheet.
	 */
	public SpriteManager() {
		try {
			ssr = new SpriteSheetReader("res/images/StartButtonSpriteSheet.png", 32, 16);
			startButtonSprites = ssr.spriteSheetToArray();
			ssr = new SpriteSheetReader("res/images/BattleshipSpritesheet.png", 16, 16);
			tileset = ssr.spriteSheetToArray();
			
			guessingOverlay = ImageIO.read(new File("res/images/GuessingOverlay.png"));
			placementOverlay = ImageIO.read(new File("res/images/PlacementOverlay.png"));
			logo = ImageIO.read(new File("res/images/Battleship_Logo.png"));
			titleScreen = ImageIO.read(new File("res/images/TitleScreen.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		waterTileset = Arrays.copyOfRange(tileset, 0, 4);
		
		destroyerTileset = Arrays.copyOfRange(tileset, 12, 14);
		cruiserTileset = Arrays.copyOfRange(tileset, 6, 9);
		submarineTileset = Arrays.copyOfRange(tileset, 9, 12);
		battleshipTileset = Arrays.copyOfRange(tileset, 14, 18);
		carrierTileset = Arrays.copyOfRange(tileset, 18, 24);
		
		indicatorTileset = new BufferedImage[3];
		indicatorTileset[0] = tileset[5];
		indicatorTileset[1] = tileset[4];
		indicatorTileset[2] = tileset[23];
		
		fullShipSprites = new BufferedImage[5];
	}
	
	/**
	 * Retrieves an array of water sprite frames for the animated water background.
	 *
	 * @return An array of water sprite frames.
	 */
	public BufferedImage[] getWaterTileset() {
		return waterTileset;
	}
	
	/**
	 * Retrieves an array of ship indicators.
	 *
	 * @return An array of ship indicators.
	 */
	public BufferedImage[] getIndicatorTileset() {
		return indicatorTileset;
	}
	
	/**
	 * Retrieves a specific ship indicator sprite based on the provided indicator type.
	 *
	 * @param indicator The type of ship indicator.
	 * @return The BufferedImage representing the specified ship indicator.
	 * @throws ImageSectionOutOfBounds If the specified indicator type is invalid.
	 */
	public BufferedImage getIndicator(Indicator indicator) throws ImageSectionOutOfBounds {
		return switch (indicator) {
			case MISS ->  indicatorTileset[0];
			case HIT ->  indicatorTileset[2];
			case COMP_HIT ->  indicatorTileset[1];
			default -> throw new ImageSectionOutOfBounds("The Indicator you were trying to access does not exist.");
		};
	}

	/**
	 * Retrieves an array of full ship sprites for different ship types.
	 *
	 * @return An array of full ship sprites.
	 */
	public BufferedImage[] getFullShipSprites() {
		fullShipSprites[0] = ssr.getSpriteFromSheet(2, 0, 32, 16);
		fullShipSprites[1] = ssr.getSpriteFromSheet(1, 0, 48, 16);
		fullShipSprites[2] = ssr.getSpriteFromSheet(1, 3, 48, 16);
		fullShipSprites[3] = ssr.getSpriteFromSheet(2, 2, 64, 16);
		fullShipSprites[4] = ssr.getSpriteFromSheet(3, 0, 80, 16);

		return fullShipSprites;
	}

	/**
	 * Retrieves an array of sections for the destroyer ship.
	 *
	 * @return An array of sections for the destroyer.
	 */
	public BufferedImage[] getDestroyerTileset() {
		return destroyerTileset;
	}

	/**
	 * Retrieves a specific section sprite from the destroyer based on the provided section type.
	 *
	 * @param section The type of ship section.
	 * @return The BufferedImage representing the specified section of the destroyer.
	 * @throws ImageSectionOutOfBounds If the specified section type is invalid.
	 */
	public BufferedImage getDestroyerSection(Section section) throws ImageSectionOutOfBounds {
		return switch (section) {
			case FRONT -> destroyerTileset[0];
			case BACK -> destroyerTileset[1];
			default -> throw new ImageSectionOutOfBounds();
		};
	}

	/**
	 * Retrieves an array of sections for the cruiser ship.
	 *
	 * @return An array of sections for the cruiser.
	 */
	public BufferedImage[] getCruiserTileset() {
		return cruiserTileset;
	}

	/**
	 * Retrieves a specific section sprite from the cruiser based on the provided section type.
	 *
	 * @param section The type of ship section.
	 * @return The BufferedImage representing the specified section of the cruiser.
	 * @throws ImageSectionOutOfBounds If the specified section type is invalid.
	 */
	public BufferedImage getCruiserSection(Section section) throws ImageSectionOutOfBounds {
		return switch (section) {
			case FRONT -> cruiserTileset[0];
			case MID_1 -> cruiserTileset[1];
			case BACK -> cruiserTileset[2];
			default -> throw new ImageSectionOutOfBounds();
		};
	}

	/**
	 * Retrieves an array of sections for the submarine ship.
	 *
	 * @return An array of sections for the submarine.
	 */
	public BufferedImage[] getSubmarineTileset() {
		return submarineTileset;
	}

	/**
	 * Retrieves a specific section sprite from the submarine based on the provided section type.
	 *
	 * @param section The type of ship section.
	 * @return The BufferedImage representing the specified section of the submarine.
	 * @throws ImageSectionOutOfBounds If the specified section type is invalid.
	 */
	public BufferedImage getSubmarineSection(Section section) throws ImageSectionOutOfBounds {
		return switch (section) {
			case FRONT -> submarineTileset[0];
			case MID_1 -> submarineTileset[1];
			case BACK -> submarineTileset[2];
			default -> throw new ImageSectionOutOfBounds();
		};
	}

	/**
	 * Retrieves a specific section sprite from the battleship based on the provided section type.
	 *
	 * @param section The type of ship section.
	 * @return The BufferedImage representing the specified section of the battleship.
	 * @throws ImageSectionOutOfBounds If the specified section type is invalid.
	 */
	public BufferedImage getBattleshipSection(Section section) throws ImageSectionOutOfBounds {
		return switch (section) {
			case FRONT -> battleshipTileset[0];
			case MID_1 -> battleshipTileset[1];
			case MID_2 -> battleshipTileset[2];
			case BACK -> battleshipTileset[3];
			default -> throw new ImageSectionOutOfBounds();
		};
	}

	/**
	 * Retrieves an array of sections for the aircraft carrier ship.
	 *
	 * @return An array of sections for the aircraft carrier.
	 */
	public BufferedImage[] getCarrierTileset() {
		return carrierTileset;
	}

	/**
	 * Retrieves a specific section sprite from the aircraft carrier based on the provided section type.
	 *
	 * @param section The type of ship section.
	 * @return The BufferedImage representing the specified section of the aircraft carrier.
	 * @throws ImageSectionOutOfBounds If the specified section type is invalid.
	 */
	public BufferedImage getCarrierSection(Section section) throws ImageSectionOutOfBounds {
		return switch (section) {
			case FRONT -> carrierTileset[0];
			case MID_1 -> carrierTileset[1];
			case MID_2 -> carrierTileset[2];
			case MID_3 -> carrierTileset[3];
			case BACK -> carrierTileset[4];
			default -> throw new ImageSectionOutOfBounds();
		};
	}
	
	public BufferedImage[] getStartButtonSprites() {
		return startButtonSprites;
	}
	
	public BufferedImage getStartButtonSprite(int spriteIndex) throws ImageSectionOutOfBounds{
		if (spriteIndex > startButtonSprites.length)
			throw new ImageSectionOutOfBounds();
		return startButtonSprites[spriteIndex];
	}
	public BufferedImage getGuessingOverlay() {
		return guessingOverlay;
	}
	
	public BufferedImage getPlacementOverlay() {
		return placementOverlay;
	}
	
	public BufferedImage getLogo() {
		return logo;
	}
	
	public BufferedImage getTitleScreen() {
		return titleScreen;
	}

	/**
	 * The {@code ShipSectionOutOfBounds} class is a custom exception that should be thrown
	 * when attempting to access a section of a ship that does not exist. It extends the standard
	 * {@code Exception} class and provides constructors for creating instances of this exception
	 * with a default error message or a custom error message.
	 *
	 * <p>
	 * Example Usage:
	 * <pre>
	 * {@code
	 * try {
	 *     // Some code that may throw ShipSectionOutOfBounds
	 * } catch (ShipSectionOutOfBounds ex) {
	 *     System.err.println("Error: " + ex.getMessage());
	 *     // Additional error handling logic if needed
	 * }
	 * }
	 * </pre>
	 * </p>
	 *
	 * @see Exception
	 */
	private static class ImageSectionOutOfBounds extends Exception {

		/**
		 * Constructs a new {@code ShipSectionOutOfBounds} instance with a default error message.
		 * The default error message indicates that the ship section being accessed does not exist.
		 */
		ImageSectionOutOfBounds() {
			super("The Ship Section you were trying to access does not exist.");
		}

		/**
		 * Constructs a new {@code ShipSectionOutOfBounds} instance with a custom error message.
		 * This constructor allows specifying a more detailed error message based on the specific
		 * context in which the exception is thrown.
		 *
		 * @param str The custom error message describing the reason for the exception.
		 */
		ImageSectionOutOfBounds(String str) {
			super(str);
		}
	}
}
