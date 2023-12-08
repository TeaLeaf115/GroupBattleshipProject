package gameLogic;

import graphicsManager.SpriteManager.Section;
import java.util.*;
import java.awt.Point;

public class Ship {
    private Point coords;
    private int shipLength;

    // rotation of the ship sprite direction
    private Rotation rotation;

    public enum Rotation {
        UP, DOWN, LEFT, RIGHT
    }

    // type of ship
    private ShipType shipType;

    public enum ShipType {
        DESTROYER, CRUISER, SUBMARINE, BATTLESHIP, CARRIER
    }

    // ship sections
    private ArrayList<ShipSection> shipSections;

    public Ship(ShipType shipType, Rotation rotation) {
        this.coords = new Point();

        this.shipType = shipType;
        this.rotation = rotation;

        // determines ship length from the type of ship
        switch (this.shipType) {
            case DESTROYER -> this.shipLength = 2;

            case CRUISER, SUBMARINE -> this.shipLength = 3;

            case BATTLESHIP -> this.shipLength = 4;

            case CARRIER -> this.shipLength = 5;
        }

        // creates ship length number of ship sections
        Section[] sections = Section.values();
        this.shipSections = new ArrayList<>();

        for (int i = 0; i < shipLength - 1; i++) {
            ShipSection section = new ShipSection(this.shipType, this.rotation, sections[i]);
            section.setCoords(
                    (int) this.coords.getX(),
                    (int) this.coords.getY());

            this.shipSections.add(section);
        }

        ShipSection backSection = new ShipSection(this.shipType, this.rotation, Section.BACK);
        backSection.setCoords(
                (int) this.coords.getX(),
                (int) this.coords.getY());

        this.shipSections.add(backSection);
    }

    public Ship(ShipType shipType) {
        this(shipType, Rotation.UP);
    }

    /**
     * Sets the topleft location of the ship to specified coordinate
     * 
     * @param xPos the x location of the ship is set to
     * @param yPos the y location of the ship is set to
     */
    public void setCoords(double xPos, double yPos) {
        this.coords.setLocation(xPos, yPos);
    }

    public String toString() {
        return shipSections.toString();
    }
}
