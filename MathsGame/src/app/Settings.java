package app;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JPanel;

public class Settings extends Window{
	
	JButton backButton;
	
	public Settings(WindowManager manager,Dimension frameSize) {
		super(manager,frameSize);
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
