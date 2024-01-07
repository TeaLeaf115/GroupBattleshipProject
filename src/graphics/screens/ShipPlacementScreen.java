package graphics.screens;

import graphics.DragAndDropHandler;
import graphics.GamePanel;
import graphics.GameStates;
import graphicsManager.AnimationHandler;

import javax.swing.*;

import gameLogic.GamePlayLogic;
import gameLogic.Player;
import gameLogic.Ship;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import static graphics.GamePanel.getSpriteScaleMultiplier;

public class ShipPlacementScreen extends JPanel {
    // screen
    private AnimationHandler waterAnimation = new AnimationHandler(GamePanel.sm.getWaterTileset(), 42);
    private final BufferedImage shipPlacementScreen;
    private final BufferedImage title = TitleScreen.logo;
    private AnimationHandler continueButtonAnimation;
    private ImageIcon continueButtonIcon;
    private JButton continueButton;
    private final int continueWidth = (int) Math.ceil(48 * 4);
    private final int continueHeight = (int) Math.ceil(16 * 4);
    private boolean continueEnterAnimation, continueExitAnimation, mouseIn = false;

    // private
    private Dimension screenSize;
    private Point screenLocation, originPoint;

    // drag components
    private final ArrayList<DragAndDropHandler> dragComponents;
    public static GamePlayLogic gameLogic = GameplayScreen.gl;

    public ShipPlacementScreen() {
        this.setBackground(new Color(0x848482));
        this.setSize(new Dimension(1274, 699));
        setLayout(null);

        Player player = ShipPlacementScreen.gameLogic.player;

        // screen
        this.shipPlacementScreen = GamePanel.sm.getPlacementOverlay();

        continueButtonAnimation = new AnimationHandler(GamePanel.sm.getContinueButtonSprites(), 1);
        continueButtonIcon = new ImageIcon(continueButtonAnimation.getFrame(0).getScaledInstance(continueWidth,
                continueHeight, Image.SCALE_SMOOTH));
        continueButton = new JButton(continueButtonIcon);
        continueButton.setSize(continueButtonIcon.getIconWidth(), continueButtonIcon.getIconHeight());
        TitleScreen.setUpButton(continueButton);
        continueButton.addActionListener(e -> {
            if (e.getSource() == continueButton && player.isShipsPlaced()) {
                GamePanel.gameState = GameStates.GAMEPLAY;
                GamePanel.screenChange = true;
                removeAll();
            }
        });

        continueButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                continueEnterAnimation = true;
                mouseIn = true;
            }

            @Override
            public void mouseExited(MouseEvent e) {
                continueExitAnimation = true;
                mouseIn = false;
            }
        });

        this.screenSize = new Dimension(
                (int) Math.floor(shipPlacementScreen.getWidth() * getSpriteScaleMultiplier()),
                (int) Math.floor(shipPlacementScreen.getHeight() * getSpriteScaleMultiplier()));

        // location where the screen is drawn
        this.screenLocation = new Point(
                this.getWidth() / 2 - this.screenSize.width / 2,
                this.getHeight() / 2 - this.screenSize.height / 2);

        // location where the board starts
        this.originPoint = new Point(
                (int) Math.floor(this.screenLocation.x + this.screenSize.width * 2 / 7),
                (int) Math.floor(this.screenLocation.y + this.screenSize.height * 3 / 14));

        // drag components
        this.dragComponents = new ArrayList<>();

        for (Ship ship : player.getShips()) {
            DragAndDropHandler dragComponent = new DragAndDropHandler(ship, player, this.originPoint, this.screenSize);
            this.dragComponents.add(dragComponent);
            this.add(dragComponent.getShipLabel()); // add the ship JLabel directly
        }

        this.add(continueButton);
        int continueButtonX = getWidth() - continueButton.getWidth() - 50; // Adjust the margin as needed
        int continueButtonY = getHeight() - continueButton.getHeight() * 2; // Adjust the margin as needed
        continueButton.setLocation(continueButtonX, continueButtonY);
    }

    public void draw() {
        this.repaint();
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        // draw placement screen
        g2.drawImage(
                this.shipPlacementScreen,
                this.screenLocation.x,
                this.screenLocation.y,
                this.screenSize.width,
                this.screenSize.height,
                null);

        g2.drawImage(title,
                getWidth() / 2 - title.getWidth(),
                (int) GamePanel.currentScreenSize.getHeight() / 32,
                title.getWidth() * 2,
                title.getHeight() * 2,
                null);

        // draw water
        BufferedImage waterSprite = this.waterAnimation.getCurrentFrame();
        for (int x = 0; x < GamePanel.maxBoardCol; x++) {
            for (int y = 0; y < GamePanel.maxBoardRow; y++) {
                g2.drawImage(
                        waterSprite,
                        originPoint.x + x * GamePanel.scaledTileSize,
                        originPoint.y + y * GamePanel.scaledTileSize,
                        (int) (waterSprite.getWidth() * GamePanel.getSpriteScaleMultiplier()),
                        (int) (waterSprite.getHeight() * GamePanel.getSpriteScaleMultiplier()),
                        null);
            }
        }

        // draw ships
        for (DragAndDropHandler dragComponent : this.dragComponents) {
            BufferedImage shipImage = dragComponent.getImg();
            Point labelCoords = dragComponent.getShipLabel().getLocation();

            g2.drawImage(
                    shipImage,
                    labelCoords.x,
                    labelCoords.y,
                    shipImage.getWidth(),
                    shipImage.getHeight(),
                    null);
        }

    }

    public void update() {
        this.waterAnimation.update();

        if (mouseIn && continueEnterAnimation) {
            continueButtonAnimation.update();
            continueButtonIcon.setImage(
                    continueButtonAnimation.getCurrentFrame().getScaledInstance(
                            continueButton.getWidth(),
                            continueButton.getHeight(),
                            Image.SCALE_SMOOTH));

            continueButton.setIcon(continueButtonIcon);
            if (continueButtonAnimation.getCurrentFrame(0) == continueButtonAnimation.getMaxFrames() - 1) {
                continueEnterAnimation = false;
            }
        }

        if (!mouseIn && (continueExitAnimation || continueButtonAnimation.getCurrentFrame(1) != 0)) {
            continueButtonAnimation.updateReverse();
            continueButtonIcon.setImage(
                    continueButtonAnimation.getCurrentFrame().getScaledInstance(
                            continueWidth,
                            continueHeight,
                            Image.SCALE_SMOOTH));

            continueButton.setIcon(continueButtonIcon);
            if (continueButtonAnimation.getCurrentFrame(0) == 0) {
                continueExitAnimation = false;
            }
        }
    }
}