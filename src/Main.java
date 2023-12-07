import javax.swing.*;

// import graphicsManager.GamePanel;

public class Main {
    public static void main(String[] args) {
        JFrame gameWindow = new JFrame("Battleship");
        gameWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gameWindow.setResizable(false);

        // GamePanel gamePanel = new GamePanel();
        gameWindow.add(gamePanel);
        gameWindow.pack();

        gameWindow.setLocationRelativeTo(null);
        gameWindow.setVisible(true);

        // gamePanel.setupGame();
        // gamePanel.startGameThread();
    }
}