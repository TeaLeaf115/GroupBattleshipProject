package graphics;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

import javax.swing.*;

import gameLogic.Player;
import gameLogic.Ship;
import gameLogic.Ship.Rotation;

public class DragAndDropHandler {
    private BufferedImage img;
    private final BufferedImage[] rotatedImages;
    private final JLabel shipLabel;

    private Ship ship;
    private Player player;

    private Point gridOriginPoint, labelPoint, initialLabelPoint;
    private double rotationAngle;

    public DragAndDropHandler(Ship ship, Player player, Point gridOriginPoint) {
        BufferedImage[] fullShipSprites = GamePanel.sm.getFullShipSprites();
        BufferedImage shipImage = switch (ship.getShipType()) {
            case DESTROYER -> fullShipSprites[0];
            case CRUISER -> fullShipSprites[1];
            case SUBMARINE -> fullShipSprites[2];
            case BATTLESHIP -> fullShipSprites[3];
            case CARRIER -> fullShipSprites[4];
        };

        // Scale the original image
        int scaledWidth = (int) Math.ceil(shipImage.getWidth() * GamePanel.getSpriteScaleMultiplier());
        int scaledHeight = (int) Math.ceil(shipImage.getHeight() * GamePanel.getSpriteScaleMultiplier());
        Image scaledImage = shipImage.getScaledInstance(scaledWidth, scaledHeight, Image.SCALE_SMOOTH);

        // Create a new BufferedImage from the scaled image
        this.img = new BufferedImage(scaledWidth, scaledHeight, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = this.img.createGraphics();
        g2d.drawImage(scaledImage, 0, 0, null);
        g2d.dispose();

        // create rotated images
        this.rotatedImages = new BufferedImage[4];
        for (int i = 0; i < 4; i++) {
            double angle = i * Math.PI / 2;
            this.rotatedImages[i] = rotateImage(angle);
        }

        // create ship label
        this.shipLabel = new JLabel(new ImageIcon(new BufferedImage(
                GamePanel.scaledTileSize * ship.getShipLength(),
                GamePanel.scaledTileSize * ship.getShipLength(),
                BufferedImage.TYPE_INT_ARGB)));

        this.shipLabel.setBorder(BorderFactory.createLineBorder(Color.WHITE));
        this.shipLabel.addMouseListener(new Click());
        this.shipLabel.addMouseMotionListener(new Drag());
        this.rotateShipImage(this.rotationAngle);

        this.ship = ship;
        this.player = player;

        this.gridOriginPoint = gridOriginPoint;
        this.labelPoint = new Point();
        this.initialLabelPoint = null;

        this.rotationAngle = 0;
    }

    public BufferedImage getImg() {
        return this.img;
    }

    public JLabel getShipLabel() {
        return this.shipLabel;
    }

    /**
     * Rotates image by a radian angle
     *
     * @param angle the radian the icon image is rotated
     */
    private BufferedImage rotateImage(double angle) {
        // Get the width and height of the original image
        int w = this.img.getWidth();
        int h = this.img.getHeight();

        // Calculate sine and cosine of the rotation angle
        double sin = Math.abs(Math.sin(angle));
        double cos = Math.abs(Math.cos(angle));

        // Calculate the dimensions of the new bounding box after rotation
        int newW = (int) Math.floor(w * cos + h * sin);
        int newH = (int) Math.floor(h * cos + w * sin);

        // Create a new BufferedImage for the rotated image
        BufferedImage rotatedImage = new BufferedImage(newW, newH, this.img.getType());

        // Get the graphics context of the rotated image
        Graphics2D g2 = rotatedImage.createGraphics();

        // Create an AffineTransform for translation and rotation
        AffineTransform at = new AffineTransform();

        // Translate to the center of the new bounding box
        at.translate((newW - w) / 2, (newH - h) / 2);

        // Coordinates for rotation center
        int x = w / 2;
        int y = h / 2;

        // Rotate the image around the specified angle
        at.rotate(angle, x, y);
        g2.setTransform(at);

        // Draw the original image onto the rotated image
        g2.drawImage(this.img, 0, 0, null);

        // Dispose of the graphics context
        g2.dispose();

        return rotatedImage;
    }

    /**
     * Retrieves the ship icon at the angle and sets the shipLabel to the icon
     * 
     * @param angle the angle of the new ship icon
     */
    public void rotateShipImage(double angle) {
        int halfPiCoefficient = (int) (rotationAngle / (Math.PI / 2) % 4);

        this.img = this.rotatedImages[halfPiCoefficient];
        //this.shipLabel.setIcon(new ImageIcon(this.img));
        //shipLabel.setLocation(initialLabelPoint);
    }

    /**
     * Checks if the point is within the game board
     * 
     * @param point    the point to be checked
     * @param shipRect the shipRect which is used to determine the size of the game
     *                 board borders
     * @return whether the point is within the game board
     */
    public boolean checkWithinBoard(Point point, Rectangle shipRect) {
        return (point.x < this.gridOriginPoint.x
                || point.y < this.gridOriginPoint.y
                || point.x + shipRect.width > this.gridOriginPoint.x + GamePanel.boardWidth
                || point.y + shipRect.height > this.gridOriginPoint.y + GamePanel.boardHeight);
    }

    /**
     * Check if the point is within the ship rect
     * 
     * @param point the point to be checked
     * @param shipRect the shipRect which is used to determine collision
     * @return whether the point is within the ship rect
     */
    public boolean checkWithinShipRect(Point point, Rectangle shipRect) {
        Point labelCoords = this.shipLabel.getLocation();
        return (point.x > labelCoords.x
                && point.y > labelCoords.y
                && point.x < labelCoords.x + shipRect.width
                && point.y < labelCoords.y + shipRect.height);
    }

    /**
     * Checks if the ship collides with any other placed ship
     * 
     * @param ship the ship to be checked
     * @return whether the ship collides with other ships
     */
    public boolean checkShipCollision(Ship ship) {
        for (Ship otherShip : player.getShips()) {
            if (otherShip != ship
                    && otherShip.isPlaced()
                    && ship.intersect(otherShip)) {
                return true;
            }
        }

        return false;
    }

    public void draw() {
        // this.shipLabel.repaint();
    }

    private class Click extends MouseAdapter {
        @Override
        public void mousePressed(MouseEvent e) {
            if (initialLabelPoint == null) {
                initialLabelPoint = shipLabel.getLocation();
            }

            labelPoint.setLocation(
                    e.getXOnScreen() - shipLabel.getX(),
                    e.getYOnScreen() - shipLabel.getY());
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            // do not continue if left mouse is not pressed
            if (!SwingUtilities.isLeftMouseButton(e)) {
                return;
            }

            shipLabel.setCursor(Cursor.getDefaultCursor());

            Point labelCoords = shipLabel.getLocation();
            Point mappedCoords = new Point(
                    (labelCoords.x - gridOriginPoint.x) / GamePanel.scaledTileSize,
                    (labelCoords.y - gridOriginPoint.y) / GamePanel.scaledTileSize);

            Point newLabelCoords = new Point(
                    gridOriginPoint.x + mappedCoords.x * GamePanel.scaledTileSize,
                    gridOriginPoint.y + mappedCoords.y * GamePanel.scaledTileSize);

            // snaps ship to location on board
            ship.setCoords(mappedCoords);
            ship.setPlaced(true);
            shipLabel.setLocation(newLabelCoords);

            // checks if the ship is out of bounds
            // if so, return it to its starting position
            if (checkWithinBoard(newLabelCoords, ship.getRect())) {
                ship.setPlaced(false);
                shipLabel.setLocation(initialLabelPoint);
            }

            // checks if the ship intersects other ship
            // if so, return it to its starting position
            if (checkShipCollision(ship)) {
                ship.setPlaced(false);
                shipLabel.setLocation(initialLabelPoint);
            }

        }

        @Override
        public void mouseClicked(MouseEvent e) {
            // do not continue if right mouse is not pressed
            if (!SwingUtilities.isRightMouseButton(e)) {
                return;
            }
            
            Point labelCoords = shipLabel.getLocation();
            Point mouseCoords = new Point(
                labelCoords.x + e.getX(),
                labelCoords.y + e.getY());

            // do not continue if the right mouse is not pressed over the ship image
            if (!checkWithinShipRect(mouseCoords, ship.getRect())) {
                return;
            }

            // rotates image
            rotationAngle += Math.PI / 2;
            if (rotationAngle == 2 * Math.PI)
                rotationAngle = 0;

            rotateShipImage(rotationAngle);

            // rotates ship
            Rotation rotation = switch ((int) (rotationAngle / (Math.PI / 2) % 4)) {
                case 0 -> Ship.Rotation.RIGHT;
                case 1 -> Ship.Rotation.UP;
                case 2 -> Ship.Rotation.LEFT;
                default -> Ship.Rotation.DOWN;
            };

            ship.rotateShip(rotation);

            // checks if the ship intersects other ship
            // if so, return it to its starting position
            if (checkShipCollision(ship)) {
                ship.setPlaced(false);
                shipLabel.setLocation(initialLabelPoint);
            }
        }
    }

    private class Drag extends MouseMotionAdapter {
        public void mouseDragged(MouseEvent e) {
            if (SwingUtilities.isLeftMouseButton(e)) {
                int x = e.getXOnScreen() - labelPoint.x;
                int y = e.getYOnScreen() - labelPoint.y;
                shipLabel.setLocation(x, y);
            }
        }
    }
}