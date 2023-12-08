package graphicsManager;

import java.awt.image.BufferedImage;

import java.util.Arrays;

import java.io.IOException;

// This class will get the 16x16 tiles from a spritesheet,
// and make different tile sets for the different boats.
public class SpriteManager {
	// The object that reads the spritesheet.
    private final SpriteSheetReader ssr;

	// An array of every tile from the tileset.
    private BufferedImage[] tileset;
	// An array of each sprite for the animated water background.
    public final BufferedImage[] waterTileset;

	// Enum for getting a section of the ship.
	public enum Section {
		FRONT, MID_1, MID_2, MID_3, BACK
	}

	public enum Indicator {
		HIT, COMP_HIT, MISS
	}
	
	// Arrays for each ship.
    private final BufferedImage[] destroyerTileset;
	private final BufferedImage[] cruiserTileset;
	private final BufferedImage[] submarineTileset;
	private final BufferedImage[] battleshipTileset;
	private final BufferedImage[] carrierTileset;

	// Array for the indicators 
    private final BufferedImage[] indicatorTileset;

    public final BufferedImage[] fullShipSprites;

    public SpriteManager() {
        ssr = new SpriteSheetReader("res/images/BattleshipSpritesheet.png", 16, 16);
        try {
            tileset = ssr.spriteSheetToArray();
        }
        catch (IOException e) {e.printStackTrace();}

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
	// Will return an array of every indicator.
	public BufferedImage[] getIndicatorTileset() {
		return indicatorTileset;
	}

	public BufferedImage getIndicator(Indicator indicator) {
		if (indicator == Indicator.MISS)
			return indicatorTileset[0];
		if (indicator == Indicator.HIT)
			return indicatorTileset[2];
		if (indicator == Indicator.COMP_HIT)
			return indicatorTileset[1];
		else
			throw new ShipSectionOutOfBounds("The Indicator you were trying to access does not exist.");
	}

	// Will return an array of the full ship sprites.
    public BufferedImage[] getFullShipSprites() {
        fullShipSprites[0] = ssr.getSpriteFromSheet(2, 0, 32, 16);
        fullShipSprites[1] = ssr.getSpriteFromSheet(1, 0, 48, 16);
        fullShipSprites[2] = ssr.getSpriteFromSheet(1, 3, 48, 16);
        fullShipSprites[3] = ssr.getSpriteFromSheet(2, 2, 64, 16);
        fullShipSprites[4] = ssr.getSpriteFromSheet(3, 0, 80, 16);

        return fullShipSprites;
    }

	// Will return an array of sections for the destoryer.
	public BufferedImage[] getDestroyerTileset() {
		return destroyerTileset;
	}

	// Will return a section from the destroyer.
	public BufferedImage getDestroyerSection(Section section) {
		if (section == Section.FRONT)
			return destroyerTileset[0];
		else if (section == Section.BACK)
			return destroyerTileset[1];
		else
			throw new ShipSectionOutOfBounds();
	}

	// Will return an array of sections for the cruiser.
	public BufferedImage[] getCruiserTileset() {
		return cruiserTileset;
	}

	// Will return a section from the cruiser.
	public BufferedImage getCruiserSection(Section section) {
		if (section == Section.FRONT)
			return cruiserTileset[0];
		else if (section == Section.MID_1)
			return cruiserTileset[1];
		else if (section == Section.BACK)
			return cruiserTileset[2];
		else
			throw new ShipSectionOutOfBounds();
	}

	// Will return an array of sections for the submarine.
	public BufferedImage[] getSubmarineTileset() {
		return submarineTileset;
	}

	// Will return a section from the submarine.
	public BufferedImage getSubmarineSection(Section section) {
		if (section == Section.FRONT)
			return submarineTileset[0];
		else if (section == Section.MID_1)
			return submarineTileset[1];
		else if (section == Section.BACK)
			return submarineTileset[2];
		else
			throw new ShipSectionOutOfBounds();
	}

	// Will return an array of sections for the battleship.
	public BufferedImage[] getBattleshipTileset() {
		return battleshipTileset;
	}

	// Will return a section from the battleship.
	public BufferedImage getBattleshipSection(Section section) {
		if (section == Section.FRONT)
			return battleshipTileset[0];
		else if (section == Section.MID_1)
			return battleshipTileset[1];
		else if (section == Section.MID_2)
			return battleshipTileset[2];
		else if (section == Section.BACK)
			return battleshipTileset[3];
		else
			throw new ShipSectionOutOfBounds();
	}

	// Will return an array of sections for the aircraft carrier.
	public BufferedImage[] getCarrierTileset() {
		return carrierTileset;
	}

	// Will return a section from the aircraft carrier.
	public BufferedImage getCarrierSection(Section section) {
		if (section == Section.FRONT)
			return carrierTileset[0];
		else if (section == Section.MID_1)
			return carrierTileset[1];
		else if (section == Section.MID_2)
			return carrierTileset[2];
		else if (section == Section.MID_3)
			return carrierTileset[3];
		else if (section == Section.BACK)
			return carrierTileset[4];
		else
			throw new ShipSectionOutOfBounds();
	}

	

	// Custom exception class to deal with a person trying to access a sesctio of a ship that does not exist.
	private class ShipSectionOutOfBounds extends Exception {
		ShipSectionOutOfBounds() {super("The Ship Section you were trying to access does not exist.");}
		ShipSectionOutOfBounds(String str) {super(str);}
	}
}
