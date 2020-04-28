package app;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JPanel;

public class Settings extends Window{
	
	JButton backButton;
	
	public Settings(WindowManager manager) {
		super(manager);
		mainPanel.setBackground(Color.GREEN);
		backButton = new GButton("Back",Color.CYAN,this);
		mainPanel.add(backButton);
	}
	@Override
	public void mousePress(MouseEvent e) {
		if (e.getSource()==backButton) {
			super.manager.changeState("menu");
		}
	}
	
}
