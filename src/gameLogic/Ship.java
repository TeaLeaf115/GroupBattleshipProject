package gameLogic;

import java.util.ArrayList;

public class Ship {
    private int xPos, yPos;
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
        this.shipType = shipType;
        this.rotation = rotation;

        // determines ship length from the type of ship
        switch (this.shipType) {
            case DESTROYER:
                this.shipLength = 2;
                break;

            case CRUISER:
                this.shipLength = 3;
                break;

            case SUBMARINE:
                this.shipLength = 3;
                break;

            case BATTLESHIP:
                this.shipLength = 4;
                break;

            case CARRIER:
                this.shipLength = 5;
                break;
        }

        // creates ship length number of ship sections
        for (int i = 0; i < shipLength; i++) {
            this.shipSections.add(new ShipSection(this.shipType, this.rotation, i));
        }
    }
}
