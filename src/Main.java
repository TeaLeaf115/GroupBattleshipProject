import java.awt.*;

import javax.swing.JFrame;

import graphics.GamePanel;

public class Main {
    public static JFrame gameWindow;
    
    public static void main(String[] args) {
        gameWindow = new JFrame("Battleship");
        gameWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gameWindow.setResizable(true);
        gameWindow.setLayout(new BorderLayout());
        GamePanel gamePanel = new GamePanel();
        gamePanel.setupGame();
        gameWindow.setPreferredSize(gamePanel.getPreferredSize());
        gameWindow.getContentPane().add(gamePanel);
        gameWindow.pack();

        gameWindow.setLocationRelativeTo(null);
        gameWindow.setVisible(true);
        
        gamePanel.startGameThread();
    }
}