package graphics.screens;

import graphics.Dnd2;
import graphics.GamePanel;

import javax.swing.*;

import gameLogic.Ship.ShipType;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class ShipPlacementScreen extends JPanel {
    private GamePanel gp;

    private BufferedImage shipPlacementScreen;
    private final BufferedImage[] fullShipSprites;

    private ArrayList<Dnd2> dragComponents;

    public ShipPlacementScreen(GamePanel gp) {
        this.gp = gp;

        this.shipPlacementScreen = GamePanel.sm.getPlacementOverlay();
        this.fullShipSprites = GamePanel.sm.getFullShipSprites();

        this.dragComponents = new ArrayList<>();
        for (ShipType shipType : ShipType.values()) {
            BufferedImage shipImage = switch (shipType) {
                case DESTROYER -> this.fullShipSprites[0];
                case CRUISER -> this.fullShipSprites[1];
                case SUBMARINE -> this.fullShipSprites[2];
                case BATTLESHIP -> this.fullShipSprites[3];
                case CARRIER -> this.fullShipSprites[4];
            };

            Dnd2 dragComponent = new Dnd2(shipImage);
            this.dragComponents.add(dragComponent);
            this.add(dragComponent);
        }
    }

    public void draw() {
        this.repaint();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        int screenWidth = (int) (this.shipPlacementScreen.getWidth() * GamePanel.getSpriteScaleMultiplier());
        int screenHeight = (int) (this.shipPlacementScreen.getHeight() * GamePanel.getSpriteScaleMultiplier());
        g2.drawImage(
                this.shipPlacementScreen,
                this.getWidth() / 2 - screenWidth / 2,
                this.getHeight() / 2 - screenHeight / 2,
                screenWidth,
                screenHeight,
                null);

    }

    public void update() {

    }
}
