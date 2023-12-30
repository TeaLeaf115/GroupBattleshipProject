package graphics.screens;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;

import graphics.GamePanel;
import graphics.GameStates;
import graphicsManager.AnimationHandler;

import javax.swing.*;

public class TitleScreen extends JPanel {
    private final BufferedImage titleScreenImage;
    private final BufferedImage logo;
    private final AnimationHandler startButtonAnimation;
    private final JButton startButton;
    private final ImageIcon startButtonIcon;
    private boolean startButtonEnterAnimation = false;
    private boolean startButtonExitAnimation = false;
    private boolean mouseIn = false;
    
    public TitleScreen() {
        setBounds(0, 0, GamePanel.getScreenSize().width, GamePanel.getScreenSize().height);
        titleScreenImage = GamePanel.sm.getTitleScreen();
        logo = GamePanel.sm.getLogo();
        setBackground(Color.red);
        
        // Set layout to BoxLayout with Y_AXIS alignment
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        
        startButtonAnimation = new AnimationHandler(GamePanel.sm.getStartButtonSprites(), 1);
        startButtonIcon = new ImageIcon(startButtonAnimation.getFrame(0).getScaledInstance((int)(32*4.5), (int)(16*4.5), Image.SCALE_SMOOTH));
        startButton = new JButton(startButtonIcon);
        startButton.setSize((int)(32*2.5), (int)(16*2.5));
        startButton.setContentAreaFilled(false);  // Make the button background transparent
        startButton.setBorderPainted(false);  // Remove the border
        startButton.setFocusPainted(false);  // Remove the focus border
        startButton.setOpaque(false);  // Make the button itself transparent
        startButton.setVisible(true);
        startButton.setAlignmentX(Component.CENTER_ALIGNMENT);  // Center horizontally
        startButton.addActionListener(e -> {
            if (e.getSource() == startButton) {
                System.out.println("BUTTON CLICKED!!!!");
                GamePanel.gameState = GameStates.GAMEPLAY;
                GamePanel.screenChange = true;
            }
        });
        startButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                startButtonEnterAnimation = true;
                mouseIn = true;
            }
    
            @Override
            public void mouseExited(MouseEvent e) {
                startButtonExitAnimation = true;
                mouseIn = false;
            }
        });
        this.add(Box.createVerticalGlue());
        this.add(startButton);
        this.add(Box.createVerticalGlue());
    }
    
    public void draw() {
        repaint();
    }
    
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        // Draw the background image
        g2.drawImage(titleScreenImage, 0, 0, GamePanel.getScreenSize().width, GamePanel.getScreenSize().height, null);
        // Draw the logo image
        g2.drawImage(logo,
                ((int) GamePanel.windowSize.getWidth() / 2) - (int) (logo.getWidth() * 2.5 / 2),
                titleScreenImage.getHeight() / 2,
                (int)(logo.getWidth() * 2.5),
                (int)(logo.getHeight() * 2.5),
                null);
    }
    
    public void update() {
        // Update any logic related to the title screen
        if (mouseIn && startButtonEnterAnimation) {
            startButtonAnimation.update();
            startButtonIcon.setImage(
                    startButtonAnimation
                            .getCurrentFrame()
                            .getScaledInstance(
                                    (int)(32 * 4.5),
                                    (int) (16 * 4.5),
                                    Image.SCALE_SMOOTH));
            startButton.setIcon(startButtonIcon);
            if (startButtonAnimation.getCurrentFrame(0) == startButtonAnimation.getMaxFrames()-1)
                startButtonEnterAnimation = false;
        }
        if (!mouseIn && (startButtonExitAnimation || startButtonAnimation.getCurrentFrame(1) != 0)) {
            startButtonAnimation.updateReverse();
            startButtonIcon.setImage(
                    startButtonAnimation
                            .getCurrentFrame()
                            .getScaledInstance(
                                    (int)(32 * 4.5),
                                    (int) (16 * 4.5),
                                    Image.SCALE_SMOOTH));
            startButton.setIcon(startButtonIcon);
            if (startButtonAnimation.getCurrentFrame(0) == 0)
                startButtonExitAnimation = false;
        }
    }
}