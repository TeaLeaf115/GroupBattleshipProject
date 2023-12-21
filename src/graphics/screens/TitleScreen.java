package graphics.screens;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

import graphics.*;
import graphicsManager.AnimationHandler;

import javax.swing.*;

public class TitleScreen extends JPanel {
    private final BufferedImage titleScreenImage;
    private final BufferedImage logo;
    private final AnimationHandler startButtonAnimation;
    private JButton startButton;
    private JButton toggleButton;
    private boolean isToggleOn;
    

    public TitleScreen() {
        titleScreenImage = GamePanel.sm.getTitleScreen();
        logo = GamePanel.sm.getLogo();
        startButtonAnimation = new AnimationHandler(GamePanel.sm.getStartButtonSprites(), 60);
        startButton = new JButton(new ImageIcon(startButtonAnimation.getFrame(0)));
        startButton.setBounds(0, 0, 250, 250);
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == startButton) {
                    System.out.println("Button WORKS!!!!!!");
                    // Add logic to transition to the next screen or start the game
                    // For example: GamePanel.gameState = GameStates.SHIP_PLACEMENT;
                }
            }
        });
        this.add(startButton);
    }
    
    public void draw(Graphics2D g2) {
        // Draw the background image
        g2.drawImage(titleScreenImage, 0, 0, 1138, 540, null);
        
        // Draw the logo image
        g2.drawImage(logo, GamePanel.getScreenSize().width/6, titleScreenImage.getHeight()/2, (int)(logo.getWidth()*2.5), (int)(logo.getHeight()*2.5), null);
    }
    
    public void update() {
//        System.out.println();
    }
}
