package graphics.screens;

import graphics.GamePanel;
import graphicsManager.AnimationHandler;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class GameOverScreen extends JPanel{
	public static BufferedImage boardSS;
	private BufferedImage compWin;
	private BufferedImage playWin;
	private AnimationHandler tryAgainAnimation;
	private ImageIcon tryAgainIcon;
	private JButton tryAgainButton;
	private final int tryAgainWidth = (int) Math.ceil(48 * 4);
	private final int tryAgainHeight = (int) Math.ceil(16 * 4);
	private boolean tryAgainEnter, tryAgainExit, mouseIn = false;
	
	public GameOverScreen() {
		tryAgainAnimation = new AnimationHandler(GamePanel.sm.getTryAgainButtonSprites(), 1);
		tryAgainIcon = new ImageIcon(tryAgainAnimation.getCurrentFrame());
		tryAgainButton = new JButton(tryAgainIcon);
		compWin = GamePanel.sm.getCompWin();
		playWin = GamePanel.sm.getPlayWin();
	}
	
	public void draw() {
		repaint();
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		g2.drawImage(boardSS, 0, 0, GamePanel.getScreenSize().width, GamePanel.getScreenSize().height, null);
		
	}
	
	public void update() {
		if (mouseIn && tryAgainEnter) {
			tryAgainAnimation.update();
			tryAgainIcon.setImage(
					tryAgainAnimation.getCurrentFrame().getScaledInstance(
							tryAgainButton.getWidth(),
							tryAgainButton.getHeight(),
							Image.SCALE_SMOOTH));
			
			tryAgainButton.setIcon(tryAgainIcon);
			if (tryAgainAnimation.getCurrentFrame(0) == tryAgainAnimation.getMaxFrames() - 1) {
				tryAgainEnter = false;
			}
		}
		
		if (!mouseIn && (tryAgainExit || tryAgainAnimation.getCurrentFrame(1) != 0)) {
			tryAgainAnimation.updateReverse();
			tryAgainIcon.setImage(
					tryAgainAnimation.getCurrentFrame().getScaledInstance(
							tryAgainWidth,
							tryAgainHeight,
							Image.SCALE_SMOOTH));
			
			tryAgainButton.setIcon(tryAgainIcon);
			if (tryAgainAnimation.getCurrentFrame(0) == 0) {
				tryAgainExit = false;
			}
		}
	}
}
