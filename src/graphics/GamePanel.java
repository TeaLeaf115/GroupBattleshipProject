package graphics;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

import graphicsManager.SpriteManager;

public class GamePanel extends JPanel implements Runnable {
    // -----------------
    // SCREEN SETTINGS
    // -----------------

    // Each tile on the map has a default texture resolution of 16x16 pixels.
    private static final int defaultTileSize = 16;
    // How many times we scale the sprite to match modern screen graphics.
    private static final double spriteScaleMultiplier = 1.8;

    // The upscale multiplier of the sprite tiles.
    public static final int scaledTileSize = (int)Math.ceil(defaultTileSize * spriteScaleMultiplier);

    // The amount of columns and rows for the board.
    public static final int maxBoardCol = 10;
    public static final int maxBoardRow = 10;

    // The pixel amount for the board size.
    private final int boardWidth = scaledTileSize * maxBoardCol;
    private final int boardHeight = scaledTileSize * maxBoardRow;

    // The thread that the game will be run on.
    private Thread gameThread;

    // How many Frames Per Second (FPS) the game screen will be updates.
    private final int FPS = 60;

    // Sets the sprites for the GUI aspects of the game.
    private GUI gui = new GUI(this);

    public static SpriteManager sm = new SpriteManager();

    // The different game states for the game.
    public GameStates gameState;

    public GamePanel() {
        this.setPreferredSize(new Dimension((int) ((gui.screenCoverWidth() + 6*16) * spriteScaleMultiplier), (int) ((gui.screenCoverHeight() + 7*16) * spriteScaleMultiplier)));
        this.setBackground(new Color(0x808080));
        this.setDoubleBuffered(true);
    }

    public void setupGame() {
        gameState = GameStates.SHIP_PLACEMENT;
    }

    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() {
        // 1 Billion nsec or 1 sec divided by the amount of FPS, giving us how many FPS we will get in nanoseconds.
        double drawInterval = 1000000000/*nanoseconds*/ / FPS;
        // 'delta' id the time period between the last frame and the current frame.
        double delta = 0;
        // The time, in nanoseconds, as the "last frame" to start the game loop.
        long lastFrame = System.nanoTime();
        // The declaration of the 'currentFrame' variable, used to calculate the delta time in the game loop.
        long currentFrame;

        // While the 'gameThread' is running, do the loop.
        while (gameThread != null) {
            // Sets the current time, in nanoseconds, for the current frame to calculate 'delta'.
            currentFrame = System.nanoTime();
            // Adds the time difference between 'currentFrame' and 'lastFrame', then divides it by the drawing interval to 'delta'.
            delta += (currentFrame - lastFrame) / drawInterval;
            // Sets time of the last frame to what the time of the current frame was.
            lastFrame = currentFrame;

            // If 'delta' is greater than or equal to 1, then update the display and subtract one from 'delta'.
            if (delta >= 1) {
                update();
                repaint();
                // System.out.println(delta);
                delta--;
            }
        }
    }

    public void update() {
        if (gameState == GameStates.GAMEPLAY) {
//            playerBoard.addShip(new Ship(2, 2, ShipType.CARRIER, 0, Rotation.RIGHT));
//            playerBoard.addShip(new Ship(2, 5, ShipType.DESTROYER, 0, Rotation.DOWN));
//            compBoard.hit(2, 5);
//            playerBoard.miss(0, 0);
//            tileM.update();
        }
        if (gameState == GameStates.PAUSED) {
            System.out.println("Game is Paused.");
        }
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        gui.drawShipPlacementScreen(g2d);

        g2d.dispose();
    }

    public double getSpriteScaleMultiplier() {
        return spriteScaleMultiplier;
    }
}