package graphics.screens.gameplay;

import graphics.Board;
import graphics.GamePanel;

import javax.swing.*;
import java.awt.*;

public class GameplayScreen extends JFrame {
	public GameplayScreen() {
		// Set up the main frame
		setTitle("Gameplay Screen");
		setSize(800, 600);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		// Create game panel
		Game gamePanel = new Game();
		add(gamePanel);
		
		// Create player and computer panels
		Player playerPanel = new Player(new Point(50, 50));
		Computer computerPanel = new Computer(new Point(0, 50));
		
		// Add player and computer panels to the game panel
		gamePanel.add(playerPanel);
		gamePanel.add(computerPanel);
		
		// Display the frame
		setVisible(true);
	}
	
	public static void main(String[] args) {
			SwingUtilities.invokeLater(() -> new GameplayScreen());
	}
}

class Game extends JPanel {
	public Game() {
		setLayout(new GridLayout(1, 2));
		setBounds(0, 0, 800, 600);
	}
}

class Player extends JPanel {
	private Board board;
	
	public Player(Point pos) {
		setLayout(null); // Set layout to null for absolute positioning
//		setBounds(position.x, position.y, GamePanel.boardWidth, GamePanel.boardHeight);
//		setBorder(BorderFactory.createTitledBorder("Player"));
		setBackground(Color.BLUE);
		// Create the board panel for the player
		board = new Board();
		board.setBounds(pos.x, pos.y, GamePanel.boardWidth, GamePanel.boardHeight); // Manually position the board
		add(board);
	}
}

class Computer extends JPanel {
	private Board board;
	
	public Computer(Point pos) {
		setLayout(null); // Set layout to null for absolute positioning
//		setBounds(position.x, position.y, GamePanel.boardWidth, GamePanel.boardHeight);
//		setBorder(BorderFactory.createTitledBorder("Computer"));
		setBackground(Color.GREEN);
		
		// Create the board panel for the computer
		board = new Board();
		board.setBounds(pos.x, pos.y, GamePanel.boardWidth, GamePanel.boardHeight); // Manually position the board
		add(board);
	}
}