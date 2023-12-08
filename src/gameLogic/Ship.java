package gameLogic;

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

    private ShipSection[] shipSections;

    public Ship () {
        
    }
}
