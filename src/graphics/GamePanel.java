package graphics;

import java.awt.*;

import javax.swing.*;

import gameLogic.Bots;
import graphicsManager.SpriteManager;
import graphics.screens.*;

public class GamePanel extends JFrame implements Runnable {
    // -----------------
    // DEFAULT SETTINGS
    // -----------------
    public static final Dimension defaultScreenSize = new Dimension(1274, 699);
    // Each tile on the map has a default texture resolution of 16x16 pixels.
    
    public static final Bots.BotLevel defaultComputerDifficulty = Bots.BotLevel.NORMAL;
    private static final int defaultTileSize = 16;
    // The amount of columns and rows for the board.
    public static final int maxBoardCol = 10;
    public static final int maxBoardRow = 10;
    
    private int defaultFPS = 60;
    
    // -----------------
    // SCREEN SETTINGS
    // -----------------
    
    // How many times we scale the sprite to match modern screen graphics.
    private static final double spriteScaleMultiplier = 1.8;

    // The upscale multiplier of the sprite tiles.
    public static final int scaledTileSize = (int) Math.ceil(defaultTileSize * spriteScaleMultiplier);

    // The pixel amount for the board size.
    public static final int boardWidth = scaledTileSize * maxBoardCol;
    public static final int boardHeight = scaledTileSize * maxBoardRow;

    public static Dimension currentScreenSize = defaultScreenSize;

    // The thread that the game will be run on.
    private Thread gameThread;

    // How many Frames Per Second (FPS) the game screen will be updates.
    private int FPS = defaultFPS;

    public static SpriteManager sm = new SpriteManager();

    // The different game states for the game.
    public static GameStates gameState;

    public static Bots.BotLevel computerDifficulty;

    // Screen Initialises
    public TitleScreen titleScreen;
    public ShipPlacementScreen shipPlacementScreen;
    public GameplayScreen gameplayScreen;
    public GameOverScreen gameOverScreen;
    public static boolean screenChange = false;
    private static GamePanel instance;

    public GamePanel() {
        this.setTitle("Battleship");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setLayout(new BorderLayout());

        this.setPreferredSize(defaultScreenSize);
        this.setIconImage(sm.windowIcon);
        
        // screens
        this.titleScreen = new TitleScreen();
        this.shipPlacementScreen = new ShipPlacementScreen();
        this.gameplayScreen = new GameplayScreen();
        this.gameOverScreen = new GameOverScreen();

        this.add(this.titleScreen, BorderLayout.CENTER);
        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);

        this.setupGame();
        this.startGameThread();
        instance = this;
    }

    public static GamePanel getInstance() {
        return instance;
    }

    public void setupGame() {
        gameState = GameStates.TITLE;
        computerDifficulty = defaultComputerDifficulty;
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
                delta--;
            }
        }
    }

    public void update() {
        SwingUtilities.invokeLater(() -> {
//            System.out.println(gameState);
//            System.out.println("x: " + getX() + " y: " + getY());
            if (screenChange) {
                switch (gameState) {
                    case TITLE -> remove(titleScreen);
                    case SHIP_PLACEMENT -> remove(shipPlacementScreen);
                    case GAMEPLAY -> remove(gameplayScreen);
                    case GAMEOVER -> remove(gameOverScreen);
                }
                switch (gameState) {
                    case TITLE -> {
//                        titleScreen = new TitleScreen();
                        add(titleScreen);
                    }
                    case SHIP_PLACEMENT -> {
//                        shipPlacementScreen = new ShipPlacementScreen();
                        add(shipPlacementScreen);
                    }
                    case GAMEPLAY -> {
//                        gameplayScreen = new GameplayScreen();
                        add(gameplayScreen);
                        GameplayScreen.gl.player.setShipLocations();
                    }
                    case GAMEOVER -> {
//                        gameOverScreen = new GameOverScreen();
                        add(gameOverScreen);
                    }
                }
                screenChange = false;

                revalidate(); // Add this line to update the UI hierarchy
                paint();
            }
            updateScreenSize(getSize());
            switch (gameState) {
                case TITLE -> titleScreen.update();
                case SHIP_PLACEMENT -> this.shipPlacementScreen.update();
                case GAMEPLAY -> gameplayScreen.update();
                case GAMEOVER -> gameOverScreen.update();
            }
        });
    }

    public void paint() {
        // System.out.println(screenChange);
        if (!screenChange) {
            switch (gameState) {
                case TITLE -> titleScreen.draw();
                case SHIP_PLACEMENT -> shipPlacementScreen.draw();
                case GAMEPLAY -> gameplayScreen.draw();
                case GAMEOVER -> gameOverScreen.draw();
            }
        }
    }

    public static double getSpriteScaleMultiplier() {
        return spriteScaleMultiplier;
    }

    public void updateScreenSize(Dimension d) {
        currentScreenSize = d;
    }

    public static Dimension getScreenSize() {
        return currentScreenSize;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(GamePanel::new);
    }
}