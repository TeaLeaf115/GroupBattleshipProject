package graphicsManager;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class SpriteSheetReader {
	// Buffered image for to hold the spritesheet.
    private BufferedImage spriteSheet;
	// The width and height of each sprite tile.
    private final int tileWidth;
    private final int tileHeight;
	// The number of rows and columns that are in the spritesheet.
    private final int numRows;
    private final int numCols;

    /**
	 * Class constructor that reads the inputted file, and the width/height of each sprite.
	 *
     * @param fileName the image file path/name
     * @param tileWidth the width of each sprite tile
     * @param tileHeight the height of each sprite tile
     */
    public SpriteSheetReader(String fileName, int tileWidth, int tileHeight) {
        // Creates a BufferedImage of the original image from 'fileName'.
        try {
            this.spriteSheet = ImageIO.read(new File(fileName));
        }
        catch (IOException e) {
			e.printStackTrace();
        }

        this.tileWidth = tileWidth;
        this.tileHeight = tileHeight;

        this.numRows = spriteSheet.getHeight() / tileHeight;
        this.numCols = spriteSheet.getWidth() / tileWidth;
    }

    /**
     * Turns a sprite sheet into an array of each sprite.
	 *
     * @return BufferedImage[] array of each sprite from the sprite sheet going from left to right.
     * @throws IOException Signals that an I/O exception has occurred.
	 * @see BufferedImage
     */
    public BufferedImage[] spriteSheetToArray() throws IOException {

        // Creates an array for the sprites from the sheet.
        BufferedImage[] spriteArray = new BufferedImage[numRows * numCols];

		// For loop that goes through every column and row in the spritesheet and adds a subimage square to an array.
        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numCols; j++) {
                spriteArray[(i * numCols) + j] = spriteSheet.getSubimage(j*tileHeight, i*tileWidth, tileWidth, tileHeight);
            }
        }

		// Returns the array of sprites.
        return spriteArray;
    }

	/**
	 * Returns a single sprite from the sprite sheet.
	 *
	 * @param row the row that the spritesheet will start grabbing the sprite from
	 * @param col the column that the spritesheet will start grabbing the sprite from
	 * @param spriteWidth the width of the sprite that will be returned
	 * @param spriteHeight the width of the sprite that will be returned
	 *
	 * @return BufferedImage of the sprite from the spritesheet
	 */
    public BufferedImage getSpriteFromSheet(int row, int col, int spriteWidth, int spriteHeight) {
		// If statement that checks if the sprite that will try to be accessed is within the boundries of the spritesheet.
        if (row * tileWidth >= spriteSheet.getWidth() || col * tileHeight >= spriteSheet.getHeight() || row < 0 || col < 0)
            throw new IndexOutOfBoundsException("There was an issue with grabbing the sprite you wanted.");

		// Returns a sub-image from the spritesheet.
        return spriteSheet.getSubimage(col*tileHeight, row*tileWidth, spriteWidth, spriteHeight);
    }
}