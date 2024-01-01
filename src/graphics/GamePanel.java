package graphics;

import java.awt.*;

import javax.swing.*;

import graphicsManager.SpriteManager;
import graphics.screens.*;

public class GamePanel extends JFrame implements Runnable {
    // -----------------
    // SCREEN SETTINGS
    // -----------------

    // Each tile on the map has a default texture resolution of 16x16 pixels.
    private static final int defaultTileSize = 16;
    // How many times we scale the sprite to match modern screen graphics.
    private static final double spriteScaleMultiplier = 1.8;

    // The upscale multiplier of the sprite tiles.
    public static final int scaledTileSize = (int) Math.ceil(defaultTileSize * spriteScaleMultiplier);

    // The amount of columns and rows for the board.
    public static final int maxBoardCol = 10;
    public static final int maxBoardRow = 10;

    // The pixel amount for the board size.
    public static final int boardWidth = scaledTileSize * maxBoardCol;
    public static final int boardHeight = scaledTileSize * maxBoardRow;

    public static Dimension windowSize = new Dimension(1274, 699);

    // The thread that the game will be run on.
    private Thread gameThread;

    // How many Frames Per Second (FPS) the game screen will be updates.
    private final int FPS = 60;

    public static SpriteManager sm = new SpriteManager();

    // The different game states for the game.
    public static GameStates gameState;

    // Screen Initialises
    public TitleScreen titleScreen;
    public ShipPlacementScreen shipPlacementScreen;
    public GameplayScreen gameplayScreen;
    public static boolean screenChange = false;

    public GamePanel() {
        this.setTitle("Battleship");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setLayout(new BorderLayout());

        this.setPreferredSize(new Dimension(1274, 699));
        this.setIconImage(sm.windowIcon);
        this.updateScreenSize(getSize());

        this.titleScreen = new TitleScreen();
        this.shipPlacementScreen = new ShipPlacementScreen();
        this.gameplayScreen = new GameplayScreen();

        this.add(this.gameplayScreen, BorderLayout.CENTER);
        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);

        this.setupGame();
        this.startGameThread();
    }

    public void setupGame() {
        gameState = GameStates.GAMEPLAY;
        System.out.println("Game successfully loaded and ready to play!");
    }

    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() {
        // 1 Billion nsec or 1 sec divided by the amount of FPS, giving us how many FPS
        // we will get in nanoseconds.
        double drawInterval = 1000000000/* nanoseconds */ / FPS;
        // 'delta' id the time period between the last frame and the current frame.
        double delta = 0;
        // The time, in nanoseconds, as the "last frame" to start the game loop.
        long lastFrame = System.nanoTime();
        // The declaration of the 'currentFrame' variable, used to calculate the delta
        // time in the game loop.
        long currentFrame;

        // While the 'gameThread' is running, do the loop.
        while (gameThread != null) {
            // Sets the current time, in nanoseconds, for the current frame to calculate
            // 'delta'.
            currentFrame = System.nanoTime();
            // Adds the time difference between 'currentFrame' and 'lastFrame', then divides
            // it by the drawing interval to 'delta'.
            delta += (currentFrame - lastFrame) / drawInterval;
            // Sets time of the last frame to what the time of the current frame was.
            lastFrame = currentFrame;

            // If 'delta' is greater than or equal to 1, then update the display and
            // subtract one from 'delta'.
            if (delta >= 1) {
                update();
                paint();
                // System.out.println(delta);
                delta--;
            }
        }
    }

    public void update() {
        SwingUtilities.invokeLater(() -> {
            if (screenChange) {
                removeAll();
                switch (gameState) {
                    case TITLE -> add(titleScreen);
                    case SHIP_PLACEMENT -> add(shipPlacementScreen);
                    case GAMEPLAY -> add(gameplayScreen);
                    case GAMEOVER -> System.out.println("Game-over Screen");
                    case PAUSED -> System.out.println("Pause Screen");
                    case SETTINGS -> System.out.println("Settings Screen");
                }
                screenChange = false;
            }
            updateScreenSize(getSize());
            // System.out.println(getScreenSize());
            switch (gameState) {
                case TITLE -> {
                    // removeAll();
                    // add(titleScreen);
                    titleScreen.update();
                }
                case SHIP_PLACEMENT -> {
//                    System.out.println("Ship Placement Screen");
                    this.shipPlacementScreen.update();
                }
                case GAMEPLAY -> {
//                System.out.println("Gameplay Screen");
                    gameplayScreen.update();

                }
                case GAMEOVER -> {
                    System.out.println("Game-over Screen");
                }
                case PAUSED -> {
                    System.out.println("Pause Screen");
                }
                case SETTINGS -> {
                    System.out.println("Settings Screen");
                }
            }
        });
    }

    public void paint() {
        // System.out.println(screenChange);
        if (!screenChange) {
            switch (gameState) {
                case TITLE -> {
                    titleScreen.draw();
                }
                case SHIP_PLACEMENT -> {
                    shipPlacementScreen.draw();
//                    System.out.println("Ship Placement Screen");
                }
                case GAMEPLAY -> {
//                System.out.println("Gameplay Screen");
                    gameplayScreen.draw();
//                    System.out.println("here Now");
                }
                case GAMEOVER -> {
                    System.out.println("Game-over Screen");
                }
                case PAUSED -> {
                    System.out.println("Pause Screen");
                }
                case SETTINGS -> {
                    System.out.println("Settings Screen");
                }
            }
        }
    }

    public static double getSpriteScaleMultiplier() {
        return spriteScaleMultiplier;
    }

    public void updateScreenSize(Dimension d) {
        windowSize = d;
    }

    public static Dimension getScreenSize() {
        return windowSize;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(GamePanel::new);
    }
}