package graphics.screens;

import graphics.GamePanel;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ShipPlacementScreen extends JPanel {
    private GamePanel gp;

    private BufferedImage shipPlacementScreen;
    private final BufferedImage[] fullShipSprites;

    public ShipPlacementScreen(GamePanel gp) {
        this.gp = gp;

        this.shipPlacementScreen = GamePanel.sm.getPlacementOverlay();
        this.fullShipSprites = GamePanel.sm.getFullShipSprites();

    }

    public void draw() {
        repaint();
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

        g2.drawImage(
                fullShipSprites[0],
                189,
                525,
                (int) (fullShipSprites[0].getWidth() * GamePanel.getSpriteScaleMultiplier()),
                (int) (fullShipSprites[0].getHeight() * GamePanel.getSpriteScaleMultiplier()),
                null);
    }

    public void update() {

    }
}
