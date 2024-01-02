package graphics;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

import javax.swing.*;

import gameLogic.Ship;
import gameLogic.Ship.Rotation;

public class DragAndDropHandler {
    private final BufferedImage img;
    private final JLabel shipLabel;
    private Ship ship;

    private Point gridOriginPoint, labelPoint, initalLabelPoint;
    private double rotationAngle;

    public DragAndDropHandler(Ship ship, Point gridOriginPoint) {
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

        ImageIcon icon = new ImageIcon(this.img);
        this.shipLabel = new JLabel(icon);
        this.shipLabel.setBorder(BorderFactory.createLineBorder(Color.RED));
        this.shipLabel.addMouseListener(new Click());
        this.shipLabel.addMouseMotionListener(new Drag());

        this.ship = ship;

        this.gridOriginPoint = gridOriginPoint;
        this.labelPoint = new Point();
        this.initalLabelPoint = null;

        this.rotationAngle = 0;
    }

    public JLabel getShipLabel() {
        return this.shipLabel;
    }

    /**
     * Rotates icon image by a radian angle
     * 
     * @param angle the radian the icon image is rotated
     */
    private void rotateImage(double angle) {
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

        // Set the rotated image as the icon for the JLabel
        ImageIcon rotatedIcon = new ImageIcon(rotatedImage);
        this.shipLabel.setIcon(rotatedIcon);
    }

    public void draw() {
        this.shipLabel.repaint();
    }

    private class Click extends MouseAdapter {
        @Override
        public void mousePressed(MouseEvent e) {
            if (initalLabelPoint == null) {
                initalLabelPoint = shipLabel.getLocation();
            }

            labelPoint.setLocation(
                    e.getXOnScreen() - shipLabel.getX(),
                    e.getYOnScreen() - shipLabel.getY());
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            if (SwingUtilities.isLeftMouseButton(e)) {
                shipLabel.setCursor(Cursor.getDefaultCursor());

                Point labelCoords = shipLabel.getLocation();
                Point mappedCoords = new Point(
                        (labelCoords.x - gridOriginPoint.x) / GamePanel.scaledTileSize,
                        (labelCoords.y - gridOriginPoint.y) / GamePanel.scaledTileSize);

                Point newLabelCoords = new Point(
                        gridOriginPoint.x + mappedCoords.x * GamePanel.scaledTileSize,
                        gridOriginPoint.y + mappedCoords.y * GamePanel.scaledTileSize);

                // checks if the ship is out of bounds
                // if so, return it to its starting position
                // otherwise snap the ship to the board
                ship.setCoords(mappedCoords);
                shipLabel.setLocation(newLabelCoords);

                Rectangle shipRect = ship.getRect();

                if (newLabelCoords.x < gridOriginPoint.x
                        || newLabelCoords.y < gridOriginPoint.y
                        || newLabelCoords.x + shipRect.width > gridOriginPoint.x + GamePanel.boardWidth
                        || newLabelCoords.y + shipRect.height > gridOriginPoint.y + GamePanel.boardHeight) {
                    shipLabel.setLocation(initalLabelPoint);

                }

                
            }
        }

        @Override
        public void mouseClicked(MouseEvent e) {
            if (SwingUtilities.isRightMouseButton(e)) {
                rotationAngle += Math.PI / 2;

                rotateImage(rotationAngle);
                Rotation rotation = switch ((int) (rotationAngle / (Math.PI / 2) % 4)) {
                    case 0 -> Ship.Rotation.RIGHT;
                    case 1 -> Ship.Rotation.UP;
                    case 2 -> Ship.Rotation.LEFT;
                    default -> Ship.Rotation.DOWN;
                };

                ship.rotateShip(rotation);
            }
        }

        // Add this method to rotate the image
        // private void rotateImage(double angle) {
        // ship.setBounds(ship.getX(), ship.getY(), ship.getHeight(), ship.getWidth());

        // BufferedImage rotatedImage = new BufferedImage(img.getHeight(),
        // img.getWidth(), BufferedImage.TYPE_INT_ARGB);
        // Graphics2D g2d = rotatedImage.createGraphics();

        // g2d.rotate(angle, img.getWidth() / 2, ship.getHeight() / 2);
        // g2d.drawImage(img, 0, 0, null);
        // g2d.dispose();

        // ImageIcon rotatedIcon = new ImageIcon(img);

        // ship.setIcon(rotatedIcon);
        // }
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