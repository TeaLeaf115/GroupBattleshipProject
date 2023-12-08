package gameLogic;

import gameLogic.Ship.*;
import graphicsManager.SpriteManager.Section;

import java.awt.Point;

public class ShipSection {
    private Point coords;

    private boolean isHit;

    private ShipType shipType;
    private Rotation rotation;
    private Section section;

    public ShipSection(ShipType shipType, Rotation rotation, Section section) {
        this.coords = new Point();

        this.isHit = false;

        this.shipType = shipType;
        this.rotation = rotation;
        this.section = section;
    }

    public Point getCoords() {
        return this.coords;
    }

    /**
     * Sets the topleft location of the ship to specified coordinate
     * 
     * @param xPos    the x location of the ship is set to
     * @param yPosthe the y location of the ship is set to
     */
    public void setCoords(int xPos, int yPos) {
        this.coords.move(xPos, yPos);
    }
}
