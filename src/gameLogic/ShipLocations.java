package gameLogic;

import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * The {@code ShipLocations} class manages the locations of ships on the game board,
 * keeping track of unguessed sections, hit sections, and missed shots. It provides
 * methods to add ships, shoot at specific locations, and clear the stored information.
 *
 * <p>
 * Ships on the game board are represented by their sections, and the locations of these
 * sections are stored in the unguessedSections map. When a shot is fired, the class
 * determines whether it hits a ship or misses, updating the hitSections and misses accordingly.
 * </p>
 *
 * <p>
 * Example Usage:
 * <pre>
 * {@code
 * // Create a ShipLocations object
 * ShipLocations shipLocations = new ShipLocations();
 *
 * // Add ships to unguessed sections
 * shipLocations.addUnguessedShips(myShips);
 *
 * // Shoot at a specific location
 * Point targetCoords = new Point(3, 5);
 * ShotStatus result = shipLocations.shootLocation(targetCoords);
 *
 * // Retrieve information about hit sections, misses, etc.
 * HashMap<Point, ShipSection> hitSections = shipLocations.getHitSections();
 * ArrayList<Point> misses = shipLocations.getMisses();
 * }
 * </pre>
 * </p>
 *
 * @see ShipSection
 * @see Ship
 */
public class ShipLocations {

    /**
     * Enumeration representing the possible shot statuses.
     */
    public enum ShotStatus {
        /** The shot hit an unguessed ship section. */
        HIT,
        /** The shot missed all ship sections. */
        MISS,
        /** The shot was fired at a previously guessed location. */
        GUESSED
    }

    // Ship locations that are unguessed
    private HashMap<Point, ShipSection> unguessedSections;

    // Ship locations that have been hit
    private HashMap<Point, ShipSection> hitSections;

    // Coordinates where shots have been misses
    private ArrayList<Point> misses;

    /**
     * Constructs a ShipLocations object with empty unguessedSections, hitSections, and misses.
     */
    public ShipLocations() {
        this.unguessedSections = new HashMap<>();
        this.hitSections = new HashMap<>();
        this.misses = new ArrayList<>();
    }

    /**
     * Retrieves the unguessedSections map containing the locations of ship sections that
     * have not been guessed.
     *
     * @return HashMap representing unguessed ship sections.
     */
    public HashMap<Point, ShipSection> getUnguessedSections() {
        return this.unguessedSections;
    }

    /**
     * Adds all sections of a ship into unguessedSections.
     *
     * @param ship The ship whose sections will be added to unguessedSections.
     */
    public void addUnguessedShip(Ship ship) {
        ship.getShipSections()
                .forEach((section) -> unguessedSections.put(section.getCoords(), section));
    }

    /**
     * Adds all sections from an ArrayList of ships into unguessedSections.
     *
     * @param ships ArrayList of ships whose sections will be added to unguessedSections.
     */
    public void addUnguessedShips(ArrayList<Ship> ships) {
        for (Ship ship : ships) {
            for (ShipSection section : ship.getShipSections()) {
                this.unguessedSections.put(section.getCoords(), section);
            }
        }
    }

    /**
     * Retrieves the hitSections map containing the locations of ship sections that have been hit.
     *
     * @return HashMap representing hit ship sections.
     */
    public HashMap<Point, ShipSection> getHitSections() {
        return this.hitSections;
    }

    /**
     * Retrieves the misses ArrayList containing the coordinates where shots have missed.
     *
     * @return ArrayList representing missed shot coordinates.
     */
    public ArrayList<Point> getMisses() {
        return this.misses;
    }

    /**
     * Attempts to shoot at a specified location and updates the corresponding maps accordingly.
     *
     * @param coords The coordinates where the shot is fired.
     * @return ShotStatus representing the result of the shot (HIT, MISS, or GUESSED).
     */
    public ShotStatus shootLocation(Point coords) {
        if (this.unguessedSections.containsKey(coords)) {
            // Shot hits an unguessed location of a ship

            // Removes ship from unguessedSections and places it in hitSections
            ShipSection hitSection = this.unguessedSections.remove(coords);
            hitSection.setHit(true);

            hitSections.put(coords, hitSection);

            return ShotStatus.HIT;

        } else if (this.hitSections.containsKey(coords) || this.misses.contains(coords)) {
            // Shot fired at an already guessed location
            return ShotStatus.GUESSED;
        }

        // Shot is a miss
        this.misses.add(coords);
        return ShotStatus.MISS;
    }

    /**
     * Clears all unguessedSections, hitSections, and misses.
     */
    public void clear() {
        this.unguessedSections.clear();
        this.hitSections.clear();
        this.misses.clear();
    }

    /**
     * Returns a string representation of the unguessedSections map.
     *
     * @return A string representation of unguessedSections.
     */
    public String toString() {
        return this.unguessedSections.toString();
    }
}