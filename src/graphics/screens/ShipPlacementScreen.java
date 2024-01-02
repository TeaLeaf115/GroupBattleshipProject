package graphics.screens;

import graphics.DragAndDropHandler;
import graphics.GamePanel;

import javax.swing.*;

import gameLogic.GamePlayLogic;
import gameLogic.Player;
import gameLogic.Ship;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import static graphics.GamePanel.getSpriteScaleMultiplier;

public class ShipPlacementScreen extends JPanel {
    // screen
    private final BufferedImage shipPlacementScreen;
    private Dimension screenSize;
    private Point screenLocation, originPoint;

    // drag components
    private final ArrayList<DragAndDropHandler> dragComponents;
    public static GamePlayLogic gameLogic = GameplayScreen.gl;

    public ShipPlacementScreen() {
        this.setBackground(new Color(0x848482));
        this.setSize(new Dimension(1274, 699));

        // screen
        this.shipPlacementScreen = GamePanel.sm.getPlacementOverlay();
        this.screenSize = new Dimension(
                (int) Math.floor(shipPlacementScreen.getWidth() * getSpriteScaleMultiplier()),
                (int) Math.floor(shipPlacementScreen.getHeight() * getSpriteScaleMultiplier()));

        // location where the screen is drawn
        this.screenLocation = new Point(
                this.getWidth() / 2 - this.screenSize.width / 2,
                this.getHeight() / 2 - this.screenSize.height / 2);

        // location where the board starts
        this.originPoint = new Point(
                (int) Math.floor(this.screenLocation.x + this.screenSize.width * 96.0 / 336),
                (int) Math.floor(this.screenLocation.y + this.screenSize.height * 44.0 / 207));

        // drag components
        this.dragComponents = new ArrayList<>();
        Player player = ShipPlacementScreen.gameLogic.player;

        for (Ship ship : player.getShips()) {
            DragAndDropHandler dragComponent = new DragAndDropHandler(ship, this.originPoint);
            this.dragComponents.add(dragComponent);
            this.add(dragComponent.getShipLabel()); // add the ship JLabel directly
        }

    }

    public void draw() {
        this.repaint();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        g2.drawImage(
                this.shipPlacementScreen,
                this.screenLocation.x,
                this.screenLocation.y,
                this.screenSize.width,
                this.screenSize.height,
                null);

        g2.drawRect(
                this.originPoint.x,
                this.originPoint.y,
                10 * GamePanel.scaledTileSize,
                10 * GamePanel.scaledTileSize);

        for (DragAndDropHandler dadh : dragComponents) {
            // dadh.draw();
        }
    }

    public void update() {
        // this.screenLocation.setLocation(
        // this.getWidth() / 2 - this.screenSize.width / 2,
        // this.getHeight() / 2 - this.screenSize.height / 2);

        // Add update logic if needed
    }
}