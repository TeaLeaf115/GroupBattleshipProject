package graphics.screens;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;

import gameLogic.Bots;
import graphics.GamePanel;
import graphics.GameStates;
import graphicsManager.AnimationHandler;

import javax.swing.*;

public class TitleScreen extends JPanel {
    private static final BufferedImage titleScreenImage = GamePanel.sm.getTitleScreen();
    public static BufferedImage logo;
    Font pixelFont;
    private final AnimationHandler[] buttonAnimations;
    private final AnimationHandler[] highlightedButtonAnimations;
    private final JButton[] buttons;
    private final ImageIcon[] buttonIcons;
    private boolean[] enterAnimations;
    private boolean[] exitAnimations;
    private final boolean[] buttonStates;
    
    private final int startWidth = (int)Math.ceil(32*5.5);
    private final int startHeight = (int)Math.ceil(16*5.5);
    private final int difficultyWidth = (int)Math.ceil(64*2.5);
    private final int difficultyHeight = (int)Math.ceil(16*2.5);
    
    private final JLabel hyperlinkLabel;
    
    public TitleScreen() {
        setBounds(0, 0, GamePanel.getScreenSize().width, GamePanel.getScreenSize().height);
        try {
            pixelFont = Font.createFont(Font.TRUETYPE_FONT, new File("res/fonts/5x4-ish Pixel Font.ttf")).deriveFont(24f);
        } catch (FontFormatException | IOException e) {
            throw new RuntimeException(e);
        }
        logo = GamePanel.sm.getLogo();
        setBackground(Color.red);
    
        buttonAnimations = new AnimationHandler[]{
                new AnimationHandler(GamePanel.sm.getStartButtonSprites(), 1),
                new AnimationHandler(GamePanel.sm.getEasyButtonSprites(), 1),
                new AnimationHandler(GamePanel.sm.getNormalButtonSprites(), 1),
                new AnimationHandler(GamePanel.sm.getHardButtonSprites(), 1),
                new AnimationHandler(GamePanel.sm.getImpossibleButtonSprites(), 1)
        };
    
        highlightedButtonAnimations = new AnimationHandler[]{
                new AnimationHandler(GamePanel.sm.getEasyButtonSpritesHighlighted(), 1),
                new AnimationHandler(GamePanel.sm.getNormalButtonSpritesHighlighted(), 1),
                new AnimationHandler(GamePanel.sm.getHardButtonSpritesHighlighted(), 1),
                new AnimationHandler(GamePanel.sm.getImpossibleButtonSpritesHighlighted(), 1)
        };
    
        buttonIcons = new ImageIcon[]{
                new ImageIcon(buttonAnimations[0].getFrame(0).getScaledInstance(startWidth, startHeight, Image.SCALE_SMOOTH)),
                new ImageIcon(buttonAnimations[1].getFrame(0).getScaledInstance(difficultyWidth, difficultyHeight, Image.SCALE_SMOOTH)),
                new ImageIcon(highlightedButtonAnimations[1].getFrame(0).getScaledInstance(difficultyWidth, difficultyHeight, Image.SCALE_SMOOTH)),
                new ImageIcon(buttonAnimations[3].getFrame(0).getScaledInstance(difficultyWidth, difficultyHeight, Image.SCALE_SMOOTH)),
                new ImageIcon(buttonAnimations[4].getFrame(0).getScaledInstance(difficultyWidth, difficultyHeight, Image.SCALE_SMOOTH))
        };
    
        buttons = new JButton[5];
    
//        add(Box.createVerticalGlue());
        for (int i = 0; i < buttons.length; i++) {
            buttons[i] = new JButton(buttonIcons[i]);
            buttons[i].setSize(buttonIcons[i].getIconWidth(), buttonIcons[i].getIconHeight());
            setUpButton(buttons[i]);

            final int buttonIndex = i;
            buttons[i].addActionListener(e -> {
                if (e.getSource() == buttons[buttonIndex]) {
                    handleButtonClick(buttonIndex);
                }
            });

            buttons[i].addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) {
                    handleMouseEnter(buttonIndex);
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    handleMouseExit(buttonIndex);
                }
            });
        }
        
        // Initialize animation flags
        enterAnimations = new boolean[buttons.length];
        exitAnimations = new boolean[buttons.length];
        buttonStates = new boolean[buttons.length];
    
        // Set layout to GridBagLayout with center alignment
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.CENTER;
    
        JPanel buttonsContainer = new JPanel();
        buttonsContainer.setLayout(new BoxLayout(buttonsContainer, BoxLayout.Y_AXIS));
    
        buttonsContainer.setOpaque(false);
    
        for (int i = 0; i < buttons.length; i++) {
            buttonsContainer.add(buttons[i]);
            buttonsContainer.add(Box.createRigidArea(new Dimension(0, i == 0 ? 30 : 5)));
        }
        System.out.println("hi" + GamePanel.currentScreenSize);
        gbc.insets = new Insets((int)Math.ceil(GamePanel.currentScreenSize.height / 2.796), 0, 0, 0);
        System.out.println(GamePanel.defaultScreenSize);
        buttonsContainer.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(buttonsContainer, gbc);
        gbc.gridy++; // Move to the next row
        gbc.insets = new Insets(0, 0, 10, 0); // Add space between buttons and hyperlinkLabel
    
        hyperlinkLabel = new JLabel("GPL-3.0 LICENSE");
        hyperlinkLabel.setFont(pixelFont);
        hyperlinkLabel.setForeground(new Color(0x000DB2));
        hyperlinkLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
    
        hyperlinkLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                openWebpage();
            }
        
            @Override
            public void mouseEntered(MouseEvent e) {
                hyperlinkLabel.setForeground(new Color(0xFF0000));
            }
        
            @Override
            public void mouseExited(MouseEvent e) {
                hyperlinkLabel.setForeground(new Color(0x000DB2));
            }
        });
    
        add(hyperlinkLabel, gbc);
    }
    
    private void handleButtonClick(int buttonIndex) {
        if (buttonIndex == 0) {
            GamePanel.gameState = GameStates.SHIP_PLACEMENT;
            GamePanel.screenChange = true;
            removeAll();
            
        } else {
            Bots.BotLevel botLevel = Bots.BotLevel.values()[buttonIndex - 1];
            System.out.println("Difficulty changed to " + botLevel);
            GamePanel.computerDifficulty = botLevel;
        }
    }
    
    private void handleMouseEnter(int buttonIndex) {
        enterAnimations[buttonIndex] = true;
        buttonStates[buttonIndex] = true;
    }
    
    private void handleMouseExit(int buttonIndex) {
        exitAnimations[buttonIndex] = true;
        buttonStates[buttonIndex] = false;
    }
    
    public static void setUpButton(JButton button) {
        button.setBorderPainted(false);
        button.setContentAreaFilled(false);
        button.setFocusPainted(false);
        button.setOpaque(false);
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setVerticalAlignment(SwingConstants.CENTER);
        button.setHorizontalAlignment(SwingConstants.CENTER);
        button.setVisible(true);
    }
    
    public void draw() {
        repaint();
    }
    
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        
        // Draw title screen background
        g2.drawImage(titleScreenImage, 0, 0, GamePanel.getScreenSize().width, GamePanel.getScreenSize().height, null);
        
        // Draw logo
        g2.drawImage(logo,
                ((int) GamePanel.currentScreenSize.getWidth() / 2) - (int) (logo.getWidth() * 3.5 / 2),
                (int) GamePanel.currentScreenSize.getHeight() / 16,
                (int) (logo.getWidth() * 3.5),
                (int) (logo.getHeight() * 3.5),
                null);
        
        // Set font for license and credits
        g2.setFont(pixelFont);
    
        // Draw text with black background
        drawTextWithBackground(g2, "GPL-3.0 LICENSE", 10, getHeight() - 10);
        drawTextWithBackground(g2, "BY: KEVIN, OWEN & ANSH", getWidth() - g2.getFontMetrics().stringWidth("BY: KEVIN, OWEN & ANSH") - 10, getHeight() - 10);
    
        // Calculate the position based on the panel's size
        int labelWidth = g2.getFontMetrics().stringWidth(hyperlinkLabel.getText()); // Adjust the width as needed
        int labelHeight = 20; // Adjust the height as needed
        int labelX = 10;
        int labelY = getHeight() - labelHeight - 6; // Place the label 10 pixels above the bottom edge
    
        // Set bounds for hyperlinkLabel to place it at the bottom left corner
        hyperlinkLabel.setBounds(labelX, labelY, labelWidth, labelHeight);
    }
    
    private void drawTextWithBackground(Graphics2D g2, String text, int x, int y) {
        // Draw black background
        g2.setColor(new Color(0x59717D));
        g2.fillRect(x-5, y - pixelFont.getSize()+2, g2.getFontMetrics().stringWidth(text) + 5, pixelFont.getSize() + 6);
        
        // Draw white text
        g2.setColor(Color.BLACK);
        g2.drawString(text, x, y);
    }
    
    private void openWebpage() {
        try {
            if (Desktop.isDesktopSupported()) {
                Desktop desktop = Desktop.getDesktop();
                if (desktop.isSupported(Desktop.Action.BROWSE)) {
                    desktop.browse(new URI("https://www.gnu.org/licenses/gpl-3.0.en.html"));
                } else {
                    System.err.println("Browsing not supported on this platform.");
                }
            } else {
                System.err.println("Desktop not supported on this platform.");
            }
        } catch (IOException | URISyntaxException ex) {
            ex.printStackTrace();
        }
    }
    
    public void update() {
        for (int i = 0; i < buttons.length; i++) {
            animateMouseHover(buttons[i], buttonIcons[i], buttonAnimations[i], buttonStates[i], enterAnimations[i], exitAnimations[i]);
        }
    }
    
    private void animateMouseHover(JButton button, ImageIcon icon, AnimationHandler AH, boolean mouseIn, boolean enterAnimation, boolean exitAnimation) {
        if (mouseIn && enterAnimation) {
            AH.update();
            if (button.equals(buttons[0])) {
                icon.setImage(
                        AH.getCurrentFrame().getScaledInstance(
                                button.getWidth(),
                                button.getHeight(),
                                Image.SCALE_SMOOTH));
            }
            else {
                // Use highlighted animations for difficulty buttons when the difficulty is selected
                Bots.BotLevel botLevel = Bots.BotLevel.values()[Arrays.asList(buttons).indexOf(button) - 1];
                if (GamePanel.computerDifficulty == botLevel) {
                    highlightedButtonAnimations[botLevel.ordinal()].updateReverse();
                    icon.setImage(
                            highlightedButtonAnimations[botLevel.ordinal()].getCurrentFrame().getScaledInstance(
                                    button.getWidth(),
                                    button.getHeight(),
                                    Image.SCALE_SMOOTH));
                }
                else {
                    icon.setImage(
                            AH.getCurrentFrame().getScaledInstance(
                                    button.getWidth(),
                                    button.getHeight(),
                                    Image.SCALE_SMOOTH));
                }
            }
            button.setIcon(icon);
            if (AH.getCurrentFrame(0) == AH.getMaxFrames() - 1) {
                enterAnimations = new boolean[buttons.length]; // Reset enterAnimations
            }
        }
        if (!mouseIn && (exitAnimation || AH.getCurrentFrame(1) != 0)) {
            AH.updateReverse();
            if (button.equals(buttons[0])) {
                icon.setImage(
                        AH.getCurrentFrame().getScaledInstance(
                                startWidth,
                                startHeight,
                                Image.SCALE_SMOOTH));
            }
            else {
                // Use highlighted animations for difficulty buttons when the difficulty is selected
                Bots.BotLevel botLevel = Bots.BotLevel.values()[Arrays.asList(buttons).indexOf(button) - 1];
                if (GamePanel.computerDifficulty == botLevel) {
                    highlightedButtonAnimations[botLevel.ordinal()].updateReverse();
                    icon.setImage(
                            highlightedButtonAnimations[botLevel.ordinal()].getCurrentFrame().getScaledInstance(
                                    difficultyWidth,
                                    difficultyHeight,
                                    Image.SCALE_SMOOTH));
                }
                else {
                    icon.setImage(
                            AH.getCurrentFrame().getScaledInstance(
                                    difficultyWidth,
                                    difficultyHeight,
                                    Image.SCALE_SMOOTH));
                }
            }
            button.setIcon(icon);
            if (AH.getCurrentFrame(0) == 0) {
                exitAnimations = new boolean[buttons.length]; // Reset exitAnimations
            }
        }
    }
}