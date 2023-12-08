package gameLogic;

import java.util.*;

import java.awt.Point;

import graphicsManager.SpriteManager.Section;

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
        this.shipSections.add(new ShipSection(this.shipType, this.rotation, Section.FRONT));

        // inserts mid sections
        for (int i = 1; i < shipLength - 1; i++) {
            this.shipSections.add(new ShipSection(this.shipType, this.rotation, sections[i]));
        }

        this.shipSections.add(new ShipSection(this.shipType, this.rotation, Section.BACK));
    }
}
