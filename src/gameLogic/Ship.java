package gameLogic;

import graphics.GamePanel;
import graphicsManager.SpriteManager.Section;

import java.awt.*;
import java.util.ArrayList;

/**
 * The {@code Ship} class represents a ship in the game, with attributes such as
 * its type,
 * length, rotation, coordinates, and sections. It provides methods to
 * initialize a ship,
 * set its coordinates, and retrieve information about the ship.
 *
 * <p>
 * Ships can be of different types, including Destroyer, Cruiser, Submarine,
 * Battleship, and Carrier.
 * Each ship has a specific length and can be oriented in different rotations
 * (UP, DOWN, LEFT, RIGHT).
 * The ship is composed of ship sections, and each section has its own
 * coordinates.
 * </p>
 *
 * <p>
 * Example Usage:
 * 
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
    private Rectangle rect;

    /**
     * Constructs a ship with the specified type and rotation.
     *
     * @param shipType The type of ship (DESTROYER, CRUISER, SUBMARINE, BATTLESHIP,
     *                 CARRIER).
     * @param rotation The rotation of the ship (UP, DOWN, LEFT, RIGHT).
     */
    public Ship(ShipType shipType, Rotation rotation) {
        this.coords = new Point();
        this.shipType = shipType;
        this.rotation = rotation;

        // determines ship length from the type of ship
        this.shipLength = switch (this.shipType) {
            case DESTROYER -> 2;
            case CRUISER, SUBMARINE -> 3;
            case BATTLESHIP -> 4;
            case CARRIER -> 5;
        };

        // creates ship length number of ship sections
        Section[] sections = Section.values();
        this.shipSections = new ArrayList<>();

        for (int i = 0; i < shipLength - 1; i++) {
            ShipSection section = new ShipSection(this.shipType, this.rotation, sections[i]);
            this.shipSections.add(section);
        }

        ShipSection backSection = new ShipSection(this.shipType, this.rotation, Section.BACK);
        this.shipSections.add(backSection);

        this.rect = new Rectangle();

        // sets the coordinates for all the ship sections
        this.rotateShip(this.rotation);
    }

    /**
     * Constructs a ship with the specified type and default rotation (UP).
     *
     * @param shipType The type of ship (DESTROYER, CRUISER, SUBMARINE, BATTLESHIP,
     *                 CARRIER).
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
        this.rect.setLocation(
                (int) (xPos * GamePanel.scaledTileSize),
                (int) (yPos * GamePanel.scaledTileSize));

        for (ShipSection section : this.shipSections) {
            section.setCoords(xPos, yPos);

            switch (this.rotation) {
                case LEFT, RIGHT -> xPos++; // Horizontal rotation
                case DOWN, UP -> yPos++; // Vertical rotation

            }
        }
    }

    public void setCoords(Point point) {
        this.setCoords(point.getX(), point.getY());
    }

    public int getShipLength() {
        return this.shipLength;
    }

    /**
     * Retrieves the ship sections that constitute the ship.
     *
     * @return ArrayList of ShipSection objects representing each section of the
     *         ship.
     */
    public ArrayList<ShipSection> getShipSections() {
        return this.shipSections;
    }

    /**
     * Rotates the coords of the ship sections and rect
     * 
     * @param rotation the new rotation the ship is set to
     */
    public void rotateShip(Rotation rotation) {
        // does not continue if rotation is not changed

        Dimension rectDimension = new Dimension();
        this.rotation = rotation;

        // rotates rectangle
        if (this.rotation == Rotation.DOWN || this.rotation == Rotation.UP) {
            rectDimension.setSize(GamePanel.scaledTileSize, this.shipLength * GamePanel.scaledTileSize);

        } else {
            rectDimension.setSize(this.shipLength * GamePanel.scaledTileSize, GamePanel.scaledTileSize);
        }

        this.rect.setSize(rectDimension);

        // rotates ship sprites
        int xPos = this.coords.x;
        int yPos = this.coords.y;

        for (ShipSection section : this.shipSections) {
            section.setRotation(this.rotation);
            section.setCoords(xPos, yPos);

            switch (this.rotation) {
                case LEFT, RIGHT -> xPos++; // Horizontal rotation
                case DOWN, UP -> yPos++; // Vertical rotation
            }
        }
    }

    /**
     * Determines whether the rectangle of another ship intersects
     * 
     * @param other the other ship to be compared to
     * @return whether this ship intersects with the rect of the other
     */
    public boolean intersect(Ship other) {
        return this.rect.intersects(other.rect);
    }

    /**
     * Returns a string representation of the ship.
     *
     * @return A string representation of the ship.
     */
    public String toString() {
        return this.rotation.toString() + " : " + this.shipSections.toString();
    }
}