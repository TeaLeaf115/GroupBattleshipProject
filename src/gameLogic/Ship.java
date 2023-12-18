package gameLogic;

import graphicsManager.SpriteManager.Section;

import java.util.*;
import java.awt.Point;

/**
 * The {@code Ship} class represents a ship in the game, with attributes such as its type,
 * length, rotation, coordinates, and sections. It provides methods to initialize a ship,
 * set its coordinates, and retrieve information about the ship.
 *
 * <p>
 * Ships can be of different types, including Destroyer, Cruiser, Submarine, Battleship, and Carrier.
 * Each ship has a specific length and can be oriented in different rotations (UP, DOWN, LEFT, RIGHT).
 * The ship is composed of ship sections, and each section has its own coordinates.
 * </p>
 *
 * <p>
 * Example Usage:
 * <pre>
 * {@code
 * // Create a new Destroyer ship with default rotation (UP)
 * Ship destroyer = new Ship(Ship.ShipType.DESTROYER);
 *
 * // Set the coordinates of the ship at a specific location
 * destroyer.setCoords(3, 5);
 *
 * // Retrieve the ship sections of the destroyer
 * ArrayList<ShipSection> sections = destroyer.getShipSections();
 * }
 * </pre>
 * </p>
 *
 * @see ShipType
 * @see Rotation
 * @see ShipSection
 */
public class Ship {

    private Point coords;
    private int shipLength;

    // Rotation of the ship sprite direction
    private Rotation rotation;

    /**
     * Enumeration representing the possible rotations of a ship.
     */
    public enum Rotation {
        UP, DOWN, LEFT, RIGHT
    }

    // Type of ship
    private ShipType shipType;

    /**
     * Enumeration representing the possible types of ships.
     */
    public enum ShipType {
        DESTROYER, CRUISER, SUBMARINE, BATTLESHIP, CARRIER
    }

    // Ship sections
    private ArrayList<ShipSection> shipSections;

    /**
     * Constructs a ship with the specified type and rotation.
     *
     * @param shipType  The type of ship (DESTROYER, CRUISER, SUBMARINE, BATTLESHIP, CARRIER).
     * @param rotation  The rotation of the ship (UP, DOWN, LEFT, RIGHT).
     */
    public Ship(ShipType shipType, Rotation rotation) {
        this.coords = new Point();
        this.shipType = shipType;
        this.rotation = rotation;

        // Determines ship length from the type of ship
        switch (this.shipType) {
            case DESTROYER -> this.shipLength = 2;
            case CRUISER, SUBMARINE -> this.shipLength = 3;
            case BATTLESHIP -> this.shipLength = 4;
            case CARRIER -> this.shipLength = 5;
        }

        // Creates ship length number of ship sections
        Section[] sections = Section.values();
        this.shipSections = new ArrayList<>();

        for (int i = 0; i < shipLength - 1; i++) {
            ShipSection section = new ShipSection(this.shipType, this.rotation, sections[i]);
            this.shipSections.add(section);
        }

        ShipSection backSection = new ShipSection(this.shipType, this.rotation, Section.BACK);
        this.shipSections.add(backSection);

        // Sets the coordinates for all the ship sections
        this.setCoords(this.coords.getX(), this.coords.getY());
    }

    /**
     * Constructs a ship with the specified type and default rotation (UP).
     *
     * @param shipType  The type of ship (DESTROYER, CRUISER, SUBMARINE, BATTLESHIP, CARRIER).
     */
    public Ship(ShipType shipType) {
        this(shipType, Rotation.UP);
    }

    /**
     * Retrieves the top-left coordinates of the ship.
     *
     * @return The Point representing the coordinates of the ship.
     */
    public Point getCoords() {
        return coords;
    }

    /**
     * Sets the top-left location of the ship to the specified coordinate.
     *
     * @param xPos The x-location to set for the ship.
     * @param yPos The y-location to set for the ship.
     */
    public void setCoords(double xPos, double yPos) {
        // Automatically moves point to integer coordinates
        this.coords.setLocation(xPos, yPos);

        for (ShipSection section : this.shipSections) {
            section.setCoords(xPos, yPos);

            if (this.rotation == Rotation.DOWN || this.rotation == Rotation.UP) {
                // Vertical rotation
                yPos++;
            } else if (this.rotation == Rotation.LEFT || this.rotation == Rotation.RIGHT) {
                // Horizontal rotation
                xPos++;
            }
        }
    }

    /**
     * Retrieves the ship sections that constitute the ship.
     *
     * @return ArrayList of ShipSection objects representing each section of the ship.
     */
    public ArrayList<ShipSection> getShipSections() {
        return this.shipSections;
    }

    /**
     * Returns a string representation of the ship.
     *
     * @return A string representation of the ship.
     */
    public String toString() {
        return super.toString(); // Override this method to provide a meaningful representation
        // e.g., return shipSections.toString();
    }
}