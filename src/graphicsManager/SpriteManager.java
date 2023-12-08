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
    public final BufferedImage[] waterTileSet;

	// Enum for getting a section of the ship.
	public enum Section {
		FRONT, MID_1, MID_2, MID_3, BACK
	}

	public enum Indicator {
		HIT, COMP_HIT, MISS
	}
	
	// Arrays for each ship.
    private final BufferedImage[] destroyerTileSet;
	private final BufferedImage[] cruiserTileSet;
	private final BufferedImage[] submarineTileSet;
	private final BufferedImage[] battleshipTileSet;
	private final BufferedImage[] carrierTileSet;

	// Array for the indicators 
    private final BufferedImage[] indicatorTileSet;

    public final BufferedImage[] fullShipSprites;

    public SpriteManager() {
        ssr = new SpriteSheetReader("res/images/BattleshipSpritesheet.png", 16, 16);
        try {
            tileset = ssr.spriteSheetToArray();
        }
        catch (IOException e) {e.printStackTrace();}

        waterTileSet = Arrays.copyOfRange(tileset, 0, 4);

        destroyerTileSet = Arrays.copyOfRange(tileset, 12, 14);
        cruiserTileSet = Arrays.copyOfRange(tileset, 6, 9);
        submarineTileSet = Arrays.copyOfRange(tileset, 9, 12);
        battleshipTileSet = Arrays.copyOfRange(tileset, 14, 18);
        carrierTileSet = Arrays.copyOfRange(tileset, 18, 24);

        indicatorTileSet = new BufferedImage[3];
        indicatorTileSet[0] = tileset[5];
        indicatorTileSet[1] = tileset[4];
        indicatorTileSet[2] = tileset[23];

        fullShipSprites = new BufferedImage[5];
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
	public BufferedImage[] getDestroyerTileSet() {
		return destroyerTileSet;
	}

	// Will return a section from the destroyer.
	public BufferedImage getDestroyerSection(Section section) {
		if (section == Section.FRONT)
			return destroyerTileSet[0];
		else if (section == Section.BACK)
			return destroyerTileSet[1];
		else
			throw new ShipSectionOutOfBounds();
	}

	// Will return an array of sections for the cruiser.
	public BufferedImage[] getCruiserTileSet() {
		return cruiserTileSet;
	}

	// Will return a section from the cruiser.
	public BufferedImage getCruiserSection(Section section) {
		if (section == Section.FRONT)
			return cruiserTileSet[0];
		else if (section == Section.MID_1)
			return cruiserTileSet[1];
		else if (section == Section.BACK)
			return cruiserTileSet[2];
		else
			throw new ShipSectionOutOfBounds();
	}

	// Will return an array of sections for the submarine.
	public BufferedImage[] getSubmarineTileSet() {
		return submarineTileSet;
	}

	// Will return a section from the submarine.
	public BufferedImage getSubmarineSection(Section section) {
		if (section == Section.FRONT)
			return submarineTileSet[0];

		else if (section == Section.MID_1)
			return submarineTileSet[1];

		else if (section == Section.BACK)
			return submarineTileSet[2];
		else
			throw new ShipSectionOutOfBounds();

		
	}

	// Will return an array of sections for the battleship.
	public BufferedImage[] getBattleshipTileSet() {
		return battleshipTileSet;
	}

	// Will return a section from the battleship.
	public BufferedImage getBattleshipSection(Section section) {
		if (section == Section.FRONT)
			return battleshipTileSet[0];
		else if (section == Section.MID_1)
			return battleshipTileSet[1];
		else if (section == Section.MID_2)
			return battleshipTileSet[2];
		else if (section == Section.BACK)
			return battleshipTileSet[3];
		else
			throw new ShipSectionOutOfBounds();
	}

	// Will return an array of sections for the aircraft carrier.
	public BufferedImage[] getCarrierTileSet() {
		return carrierTileSet;
	}

	// Will return a section from the aircraft carrier.
	public BufferedImage getCarrierSection(Section section) {
		if (section == Section.FRONT)
			return carrierTileSet[0];
		else if (section == Section.MID_1)
			return carrierTileSet[1];
		else if (section == Section.MID_2)
			return carrierTileSet[2];
		else if (section == Section.MID_3)
			return carrierTileSet[3];
		else if (section == Section.BACK)
			return carrierTileSet[4];
		else
			throw new ShipSectionOutOfBounds();
	}

	

	// Custom exception class to deal with a person trying to access a sesctio of a ship that does not exist.
	private class ShipSectionOutOfBounds extends Exception {
		ShipSectionOutOfBounds() {super("The Ship Section you were trying to access does not exist.");}
		ShipSectionOutOfBounds(String str) {super(str);}
	}
}