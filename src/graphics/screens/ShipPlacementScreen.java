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

        try {
            shipPlacementScreen = ImageIO.read(new File("res/images/PlacementOverlay.png"));

        } catch (IOException e) {
            e.printStackTrace();
        }

        fullShipSprites = GamePanel.sm.getFullShipSprites();

    }

    public void draw(Graphics2D g2) {
        g2.drawImage(
                shipPlacementScreen,
                0,
                0,
                (int) (shipPlacementScreen.getWidth() * GamePanel.getSpriteScaleMultiplier()),
                (int) (shipPlacementScreen.getHeight() * GamePanel.getSpriteScaleMultiplier()),
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
