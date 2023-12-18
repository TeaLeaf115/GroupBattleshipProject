import javax.swing.*;

import graphics.GamePanel;

import java.awt.*;

public class Main {

    public static void main(String[] args) {
        JFrame gameWindow = new JFrame("Battleship");
        gameWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gameWindow.setResizable(false);
        gameWindow.setLayout(new BorderLayout());
        gameWindow.setSize(800, 600);
        GamePanel gamePanel = new GamePanel();
        gameWindow.add(gamePanel);
        gameWindow.pack();

        gameWindow.setLocationRelativeTo(null);
        gameWindow.setVisible(true);

        gamePanel.setupGame();
        gamePanel.startGameThread();
    }
}