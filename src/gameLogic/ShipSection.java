package gameLogic;

import gameLogic.Ship.*;
import graphicsManager.SpriteManager.Section;

import java.awt.Point;

public class ShipSection {
    private Point coords;

    private boolean isHit;

    // used to map graphics to ship section
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
     * Sets the topleft location of the section to specified coordinate
     * 
     * @param xPos the x location of the section is set to
     * @param yPos the y location of the section is set to
     */
    public void setCoords(double xPos, double yPos) {
        this.coords.setLocation(xPos, yPos);
    }

    public boolean isHit() {
        return this.isHit;
    }

    public void setHit(boolean isHit) {
        this.isHit = isHit;
    }

    public String toString() {
        return coords.toString();
    }
}
