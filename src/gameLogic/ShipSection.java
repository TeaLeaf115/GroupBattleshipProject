package gameLogic;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import gameLogic.Ship.*;

public class ShipSection {
    private int xPos, yPos;

    // section of the ship from 0 for ship bow and ship length - 1 for stern
    private int shipSegment;
    private boolean isHit;

    private ShipType shipType;
    private Rotation rotation;

    private BufferedImage image;
    

    public ShipSection(ShipType shipType, Rotation rotation, int shipSegment) {
        this.xPos = 0;
        this.yPos = 0;

        this.shipSegment = shipSegment;
        this.isHit = false;

        this.shipType = shipType;
        this.rotation = rotation;
        
        

        // todo: determine image from ship type

    }

    public int getX() {
        return this.xPos;
    }

    public int getY() {
        return this.yPos;
    }

    /**
     * Sets the topleft location of the ship to specified coordinate
     * @param xPos the x location of the ship is set to
     * @param yPosthe the y location of the ship is set to
     */
    public void setCoords(int xPos, int yPos) {
        this.xPos = xPos;
        this.yPos = yPos;
    }

    /**
     * Draws the ship section image onto graphics
     */
    public void draw(int x, int y, Graphics2D g2) {
        g2.drawImage(
                this.image,
                x,
                y,
                TILE_SIZE,
                TILE_SIZE,
                null);
    }


}
