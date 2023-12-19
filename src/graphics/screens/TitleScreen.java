package graphics.screens;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import graphics.GamePanel;

public class TitleScreen {
    private GamePanel gp;

    private BufferedImage titleScreenImage;

    public TitleScreen(GamePanel gp) {
        this.gp = gp;

        try {
            titleScreenImage = ImageIO.read(new File("res/images/ShipPlacementScreen.png"));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void drawSPS(Graphics2D g2) {
        g2.drawImage(
                titleScreenImage,
                0,
                0,
                (int) (this.titleScreenImage.getWidth() * this.gp.getSpriteScaleMultiplier()),
                (int) (this.titleScreenImage.getHeight() * this.gp.getSpriteScaleMultiplier()),
                null);
    }
}
