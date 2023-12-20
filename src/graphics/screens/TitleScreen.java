package graphics.screens;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import com.sun.tools.javac.Main;
import graphics.*;

import javax.swing.*;

public class TitleScreen extends JPanel {
    private final BufferedImage titleScreenImage;
    private final BufferedImage logo;
    private final AnimationHandler startButtonAnimation;
    

    public TitleScreen() {
        titleScreenImage = GamePanel.sm.getTitleScreen();
        logo = GamePanel.sm.getLogo();
        startButtonAnimation = new AnimationHandler(GamePanel.sm.getStartButtonSprites(), 60);
        setBounds(0, 0, titleScreenImage.getWidth(), titleScreenImage.getHeight());
    }

    public void draw(Graphics2D g2) {
        g2.drawImage(
                titleScreenImage,
                0,
                0,
                (int) (this.titleScreenImage.getWidth() * (GamePanel.getSpriteScaleMultiplier()*2.5)),
                (int) (this.titleScreenImage.getHeight() * (GamePanel.getSpriteScaleMultiplier()*2.5)),
                null);
    }
    
    public void update() {
//        System.out.println();
    }
}
