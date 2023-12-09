import javax.swing.*;

import gameLogic.MouseHandler;
import graphics.GamePanel;

public class Main {
    private static MouseHandler mouseHandler = new MouseHandler();

    public static void main(String[] args) {
        JFrame gameWindow = new JFrame("Battleship");
        gameWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gameWindow.setResizable(false);

        // Creates mouseHandler to track mouse movement and input
        gameWindow.addMouseListener(mouseHandler);

        GamePanel gamePanel = new GamePanel();
        gameWindow.add(gamePanel);
        gameWindow.pack();

        gameWindow.setLocationRelativeTo(null);
        gameWindow.setVisible(true);

        gamePanel.setupGame();
        gamePanel.startGameThread();
    }
}