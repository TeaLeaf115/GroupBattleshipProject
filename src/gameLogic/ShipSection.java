package gameLogic;

import java.awt.Point;

import gameLogic.Ship.Rotation;
import gameLogic.Ship.ShipType;
import graphicsManager.SpriteManager.Section;

/**
 * The {@code ShipSection} class represents a section of a ship on the game board.
 * It holds information about the section's coordinates, hit status, ship type, rotation,
 * and the specific section (front, mid, or back) to which it belongs.
 *
 * <p>
 * A ship section is a basic unit of a ship, and each ship consists of multiple sections.
 * This class is used to store information about individual ship sections, including
 * their current state (hit or not), position on the game board, the type of ship they
 * belong to, the ship's rotation, and the specific section they represent (front, mid, or back).
 * </p>
 *
 * <p>
 * Example Usage:
 * <pre>
 * {@code
 * // Create a ShipSection object for a destroyer's front section
 * ShipSection frontSection = new ShipSection(ShipType.DESTROYER, Rotation.UP, Section.FRONT);
 *
 * // Get the coordinates of the ship section
 * Point coords = frontSection.getCoords();
 *
 * // Check if the ship section has been hit
 * boolean isHit = frontSection.isHit();
 *
 * // Set the hit status of the ship section
 * frontSection.setHit(true);
 *
 * // Get the type of ship to which the section belongs
 * ShipType shipType = frontSection.getShipType();
 *
 * // Get the rotation of the ship section
 * Rotation rotation = frontSection.getRotation();
 *
 * // Get the specific section of the ship (front, mid, or back)
 * Section sectionType = frontSection.getSection();
 * }
 * </pre>
 * </p>
 *
 * @see ShipType
 * @see Rotation
 * @see Section
 */
public class ShipSection {

    /** The coordinates of the ship section on the game board. */
    private final Point coords;

    /** The hit status of the ship section. */
    private boolean isHit;

    /** The type of ship to which the section belongs. */
    private final ShipType shipType;

    /** The rotation of the ship section. */
    private final Rotation rotation;

    /** The specific section of the ship (front, mid, or back). */
    private final Section section;

    /**
     * Constructs a ShipSection object with the specified ship type, rotation, and section.
     *
     * @param shipType The type of ship to which the section belongs.
     * @param rotation The rotation of the ship section.
     * @param section  The specific section of the ship (front, mid, or back).
     */
    public ShipSection(ShipType shipType, Rotation rotation, Section section) {
        this.coords = new Point();
        this.isHit = false;
        this.shipType = shipType;
        this.rotation = rotation;
        this.section = section;
    }

    /**
     * Retrieves the coordinates of the ship section on the game board.
     *
     * @return Point representing the coordinates of the ship section.
     */
    public Point getCoords() {
        return this.coords;
    }

    /**
     * Sets the coordinates of the ship section on the game board.
     *
     * @param xPos The x-coordinate to set.
     * @param yPos The y-coordinate to set.
     */
    public void setCoords(double xPos, double yPos) {
        this.coords.setLocation(xPos, yPos);
    }

    /**
     * Checks if the ship section has been hit.
     *
     * @return {@code true} if the ship section has been hit, {@code false} otherwise.
     */
    public boolean isHit() {
        return this.isHit;
    }

    /**
     * Sets the hit status of the ship section.
     *
     * @param isHit The hit status to set.
     */
    public void setHit(boolean isHit) {
        this.isHit = isHit;
    }

    /**
     * Retrieves the type of ship to which the section belongs.
     *
     * @return ShipType representing the type of ship.
     */
    public ShipType getShipType() {
        return this.shipType;
    }

    /**
     * Retrieves the rotation of the ship section.
     *
     * @return Rotation representing the rotation of the ship section.
     */
    public Rotation getRotation() {
        return this.rotation;
    }

    /**
     * Retrieves the specific section of the ship (front, mid, or back).
     *
     * @return Section representing the specific section of the ship.
     */
    public Section getSection() {
        return this.section;
    }

    /**
     * Returns a string representation of the ShipSection object.
     *
     * @return A string representation of the ShipSection.
     */
    public String toString() {
        return super.toString(); // this.coords.toString();
    }
}