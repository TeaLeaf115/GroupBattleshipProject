package graphics.screens;

import graphics.DragAndDropHandler;
import graphics.GamePanel;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import static graphics.GamePanel.getSpriteScaleMultiplier;

public class ShipPlacementScreen extends JPanel {
    private final BufferedImage shipPlacementScreen;
    private final ArrayList<DragAndDropHandler> dragComponents;
    
    public ShipPlacementScreen() {
        this.setBackground(Color.green);
        this.shipPlacementScreen = GamePanel.sm.getPlacementOverlay();
        BufferedImage[] fullShipSprites = GamePanel.sm.getFullShipSprites();
        this.dragComponents = new ArrayList<>();
        
        for (BufferedImage shipImage : fullShipSprites) {
            DragAndDropHandler dragComponent = new DragAndDropHandler(shipImage);
            this.dragComponents.add(dragComponent);
            this.add(dragComponent.getShipLabel()); // Add the ship JLabel directly
        }
    }
    
    public void draw() {
        this.repaint();
    }
    
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        
        int screenWidth = (int) Math.ceil(shipPlacementScreen.getWidth() * getSpriteScaleMultiplier());
        int screenHeight = (int) Math.ceil(shipPlacementScreen.getHeight() * getSpriteScaleMultiplier());
        g2.drawImage(
                this.shipPlacementScreen,
                this.getWidth() / 2 - screenWidth / 2,
                this.getHeight() / 2 - screenHeight / 2,
                screenWidth,
                screenHeight,
                null);
        
        for (DragAndDropHandler dadh : dragComponents) {
            dadh.draw();
        }
    }
    
    public void update() {
        // Add update logic if needed
    }
}