package app;

import java.awt.BorderLayout;
import java.awt.event.MouseEvent;

import javax.swing.JPanel;

public abstract class Window {
	protected JPanel mainPanel;
	protected WindowManager manager;
	
	public Window(WindowManager manager) {
		this.manager=manager;
		mainPanel = new JPanel(new BorderLayout());
	}
	public JPanel getMainPanel() {
		return mainPanel;
	}
	
	public void mousePress(MouseEvent e) {
		
	}
	
	public void mouseEnter(MouseEvent e) {
		
	}
	
	public void mouseExit(MouseEvent e) {
		
	}
}
