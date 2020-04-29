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
	/**
	 * 
	 * @return the main panel of the window to be used by the window manager
	 */
	public JPanel getMainPanel() {
		return mainPanel;
	}
	
	/**
	 * Contains the action to be performed when a button is pressed
	 * 
	 * @param e: mouseEvent given by the action
	 * 
	 */
	public void mousePress(MouseEvent e) {	
	}
	/**
	 * Contains the action to be performed when the mouse cursor enters the field of a button
	 * 
	 * @param e: mouseEvent given by the action
	 */
	public void mouseEnter(MouseEvent e) {	
	}
	/**
	 * Contains the action to be performed when the mouse cursor exits the field of a button
	 * 
	 * @param e: mouseEvent given by the action
	 * 
	 */
	public void mouseExit(MouseEvent e) {	
	}
}
