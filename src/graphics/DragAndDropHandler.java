package graphics;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

import javax.swing.*;

public class DragAndDropHandler {
    private final BufferedImage img;
    private final JLabel ship;
    private int initialX, initialY;
    private double rotationAngle;
    
    public DragAndDropHandler(BufferedImage pImg) {
        // Scale the original image
        int scaledWidth = (int) Math.ceil(pImg.getWidth() * GamePanel.getSpriteScaleMultiplier());
        int scaledHeight = (int) Math.ceil(pImg.getHeight() * GamePanel.getSpriteScaleMultiplier());
        Image scaledImage = pImg.getScaledInstance(scaledWidth, scaledHeight, Image.SCALE_SMOOTH);
        
        // Create a new BufferedImage from the scaled image
        this.img = new BufferedImage(scaledWidth, scaledHeight, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = this.img.createGraphics();
        g2d.drawImage(scaledImage, 0, 0, null);
        g2d.dispose();
        
        ImageIcon icon = new ImageIcon(this.img);
        this.ship = new JLabel(icon);
        this.ship.setBorder(BorderFactory.createLineBorder(Color.RED));
        this.ship.addMouseListener(new Click());
        this.ship.addMouseMotionListener(new Drag());

        this.initialX = 0;
        this.initialY = 0;
        this.rotationAngle = 0;
    }
    
    public JLabel getShipLabel() {
        return this.ship;
    }
    
    /**
     * Rotates icon image by a radian angle
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
        this.ship.setIcon(rotatedIcon);
    }
    
    public void draw() {
        this.ship.repaint();
    }
    
    private class Click extends MouseAdapter {
        @Override
        public void mousePressed(MouseEvent e) {
            System.out.println(ship.getLocation());

            initialX = e.getXOnScreen() - ship.getX();
            initialY = e.getYOnScreen() - ship.getY();
        }
        
        @Override
        public void mouseReleased(MouseEvent e) {
            ship.setCursor(Cursor.getDefaultCursor());
        }
        
        @Override
        public void mouseClicked(MouseEvent e) {
            if (SwingUtilities.isRightMouseButton(e)) {
                rotationAngle += Math.PI / 2;
                System.out.println(rotationAngle);
                rotateImage(rotationAngle);
            }
        }
    
        // Add this method to rotate the image
        // private void rotateImage(double angle) {
        //     ship.setBounds(ship.getX(), ship.getY(), ship.getHeight(), ship.getWidth());
            
        //     BufferedImage rotatedImage = new BufferedImage(img.getHeight(), img.getWidth(), BufferedImage.TYPE_INT_ARGB);
        //     Graphics2D g2d = rotatedImage.createGraphics();
            
        //     g2d.rotate(angle, img.getWidth() / 2,  ship.getHeight() / 2);
        //     g2d.drawImage(img, 0, 0, null);
        //     g2d.dispose();
        
        //     ImageIcon rotatedIcon = new ImageIcon(img);
            
            
        //     ship.setIcon(rotatedIcon);
        // }
    }
    
    private class Drag extends MouseMotionAdapter {
        public void mouseDragged(MouseEvent e) {
            if (SwingUtilities.isLeftMouseButton(e)) {
                int x = e.getXOnScreen() - initialX;
                int y = e.getYOnScreen() - initialY;
                ship.setLocation(x, y);
            }
        }
    }
}