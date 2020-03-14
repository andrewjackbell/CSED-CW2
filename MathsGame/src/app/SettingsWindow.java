package app;

import java.awt.Color;

import javax.swing.JButton;
import javax.swing.JPanel;

public class SettingsWindow {
	
	JPanel mainPanel;
	JButton backButton;
	
	public JPanel getMainPanel() {
		return mainPanel;
	}
	
	public SettingsWindow() {
		mainPanel = new JPanel();
		mainPanel.setBackground(Color.GREEN);
		backButton = new JButton("Back");
		mainPanel.add(backButton);
	}
}
