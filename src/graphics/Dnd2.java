package graphics;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

// Dnd2 class extends JPanel for drag-and-drop functionality
public class Dnd2 extends JPanel {

    // Variables to handle image, icon, and ship JLabel
    private BufferedImage img; // BufferedImage to store the image
    private ImageIcon icon; // ImageIcon to display the image
    private JLabel ship; // JLabel to hold the icon and allow dragging

    // Variables for initial mouse click position and rotation angle
    private int initialX, initialY; // Store initial mouse click position relative to ship
    private double rotationAngle = 0; // Store rotation angle in radians

    // Constructor for Dnd2 class
    public Dnd2() {
        // Load the image from file
        try {
            this.img = ImageIO.read(new File("res/Destroyer.png"));
            
        } catch (IOException e) {
            // Handle IOException (image loading failure)
            e.printStackTrace();
        }

        // Create an icon and a ship JLabel to display the image
        this.icon = new ImageIcon(img);
        this.ship = new JLabel(icon);
        this.ship.setBorder(BorderFactory.createLineBorder(Color.BLACK)); // Add a border for better visibility

        // Add mouse listeners for dragging and clicking
        this.ship.addMouseListener(new Click());
        this.ship.addMouseMotionListener(new Drag());

        // Add the ship JLabel to the panel
        this.add(this.ship);
    }

    // Method to rotate the image
    private BufferedImage rotateImage(double angle) {
        // Calculate new dimensions after rotation
        int w = img.getWidth();
        int h = img.getHeight();
        double sin = Math.abs(Math.sin(angle));
        double cos = Math.abs(Math.cos(angle));
        int newW = (int) Math.floor(w * cos + h * sin);
        int newH = (int) Math.floor(h * cos + w * sin);

        // Create a new BufferedImage for rotated image
        BufferedImage rotatedImage = new BufferedImage(newW, newH, img.getType());
        Graphics2D g2 = rotatedImage.createGraphics();

        // Apply transformation for rotation
        AffineTransform at = new AffineTransform();
        at.translate((newW - w) / 2, (newH - h) / 2);
        int x = w / 2;
        int y = h / 2;
        
        at.rotate(angle, x, y);
        g2.setTransform(at);

        // Draw the rotated image onto the BufferedImage
        g2.drawImage(img, 0, 0, null);
        g2.dispose();

        return rotatedImage;
    }

    // Method to paint the component
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Get the rotated image based on the current angle
        BufferedImage rotatedImg = rotateImage(rotationAngle);

        // Set the rotated image as the icon for the ship JLabel
        this.ship.setIcon(new ImageIcon(rotatedImg));

        // Draw the rotated image at the JLabel position
        g.drawImage(rotatedImg, this.ship.getX(), this.ship.getY(), null);
    }

    // Inner class for handling mouse click events
    private class Click extends MouseAdapter {
        @Override
        public void mousePressed(MouseEvent e) {
            // Store initial mouse click position relative to ship
            initialX = e.getXOnScreen() - ship.getX();
            initialY = e.getYOnScreen() - ship.getY();
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            // Set the cursor back to default after mouse release
            ship.setCursor(Cursor.getDefaultCursor());
        }

        @Override
        public void mouseClicked(MouseEvent e) {
            // Check if the right mouse button is clicked
            if (SwingUtilities.isRightMouseButton(e)) {
                // Rotate the image 90 degrees clockwise on right-click
                rotationAngle += Math.PI / 2;
                repaint(); // Update the panel to show the rotated image
            }
        }
    }

    // Inner class for handling mouse drag events
    private class Drag extends MouseMotionAdapter {
        public void mouseDragged(MouseEvent e) {
            // Check if the left mouse button is held down
            if (SwingUtilities.isLeftMouseButton(e)) {
                // Update the ship location based on mouse drag
                int x = e.getXOnScreen() - initialX;
                int y = e.getYOnScreen() - initialY;
                ship.setLocation(x, y);
            }
        }
    }
}
