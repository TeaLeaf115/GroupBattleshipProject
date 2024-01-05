package graphics.screens;

import gameLogic.GamePlayLogic;
import graphics.GamePanel;
import graphics.GameStates;
import graphicsManager.AnimationHandler;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

public class GameOverScreen extends JPanel{
	public static BufferedImage boardSS;
	private final BufferedImage compWin = GamePanel.sm.getCompWin();
	private int compWinWidth = (int)(compWin.getWidth() * 2.5);
	private int compWinHeight = (int)(compWin.getHeight() * 2.5);
	private final BufferedImage playWin = GamePanel.sm.getPlayWin();
	private final int playWinWidth = (int)(playWin.getWidth() * 2.5);
	private final int playWinHeight = (int)(playWin.getHeight() * 2.5);
	private final AnimationHandler tryAgainAnimation;
	private ImageIcon tryAgainIcon;
	private final JButton tryAgainButton;
	private final int tryAgainWidth = (int) Math.ceil(48 * 5.5);
	private final int tryAgainHeight = (int) Math.ceil(16 * 5.5);
	private boolean tryAgainEnter, tryAgainExit, mouseIn = false;
	
	public GameOverScreen() {
		setLayout(null);
		this.setSize(new Dimension(1274, 699));
		setBackground(Color.yellow);
//		setBounds(0, 0, GamePanel.getScreenSize().width, GamePanel.getScreenSize().height);
		tryAgainAnimation = new AnimationHandler(GamePanel.sm.getTryAgainButtonSprites(), 1);
		tryAgainIcon = new ImageIcon(tryAgainAnimation.getFrame(0).getScaledInstance(tryAgainWidth, tryAgainHeight, Image.SCALE_SMOOTH));
		tryAgainButton = new JButton(tryAgainIcon);
		tryAgainButton.setSize(tryAgainIcon.getIconWidth(), tryAgainIcon.getIconHeight());
		TitleScreen.setUpButton(tryAgainButton);
		
		tryAgainButton.addActionListener(e -> {
			if (e.getSource() == tryAgainButton) {
				GamePanel.gameState = GameStates.TITLE;
				GamePanel.screenChange = true;
				removeAll();
			}
		});
		tryAgainButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				tryAgainEnter = true;
				mouseIn = true;
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				tryAgainExit = true;
				mouseIn = false;
			}
		});
		add(tryAgainButton);
		tryAgainButton.setLocation((getWidth()/2 - tryAgainIcon.getIconWidth()/2), (getHeight()/2+15));
	}
	
	public void draw() {
		repaint();
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		g2.drawImage(boardSS, 0, 0, GamePanel.getScreenSize().width, GamePanel.getScreenSize().height, null);
		g2.fillRect((getWidth()/2 - compWinWidth/2-50), (int)(getHeight()/4-50), compWinWidth+100, compWinHeight+300);
		if (GamePlayLogic.compWon)
			g2.drawImage(compWin, (int)(getWidth()/2 - compWinWidth/2), (int)(getHeight()/4), compWinWidth, compWinHeight, null);
		else
			g2.drawImage(playWin, (int)(getWidth()/2 - playWinWidth/2), (int)(getHeight()/4), playWinWidth, playWinHeight, null);
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
