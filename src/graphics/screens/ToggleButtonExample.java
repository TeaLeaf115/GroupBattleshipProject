package graphics.screens;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ToggleButtonExample extends JFrame {
	private OuterPanelExample outerPanelExample;
	
	public ToggleButtonExample() {
		// Initialize the JFrame
		super("Toggle Button Example");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(300, 200);
		
		// Create an instance of the OuterPanelExample class
		outerPanelExample = new OuterPanelExample();
		
		// Add the outer panel to the JFrame
		getContentPane().add(outerPanelExample);
		
		// Set the JFrame to be visible
		setVisible(true);
	}
	
	public static void main(String[] args) {
		// Create an instance of the ToggleButtonExample class
		javax.swing.SwingUtilities.invokeLater(ToggleButtonExample::new);
	}
}

class OuterPanelExample extends JPanel {
	private InnerPanelExample innerPanelExample;
	
	public OuterPanelExample() {
		// Create an instance of the InnerPanelExample class
		innerPanelExample = new InnerPanelExample();
		System.out.println(true);
		// Add the inner panel to the outer panel
		add(innerPanelExample);
	}
}

class InnerPanelExample extends JPanel {
	private JButton toggleButton;
	private boolean isToggleOn;
	
	public InnerPanelExample() {
		// Initialize the JButton
		toggleButton = new JButton("Toggle");
		isToggleOn = true; // Initial value of the boolean
		
		// Add ActionListener to the button
		toggleButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// Toggle the boolean value
				isToggleOn = !isToggleOn;
				System.out.println("Toggle button clicked. Value is now: " + isToggleOn);
			}
		});
		
		// Add the button to the inner panel
		add(toggleButton);
		System.out.println(true);
	}
}