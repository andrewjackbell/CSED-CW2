package app;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.MouseEvent;

import javax.swing.JPanel;
import javax.swing.JSlider;

public abstract class Window {
	protected JPanel mainPanel;
	protected WindowManager manager;
	private Dimension frameSize;
	
	public Window(WindowManager manager,Dimension frameSize) {
		this.manager=manager;
		this.frameSize=frameSize;
		mainPanel = new JPanel(new BorderLayout());
	}

	public void setColour(Color colour) {
		colourComponents(mainPanel,colour);
		
	}
	
	private void colourComponents(Component c,Color colour) {
		if (c instanceof JPanel|| c instanceof JSlider) {
			c.setBackground(colour);
		}
		
		if (c instanceof Container) {
			Component[] cs=((Container) c).getComponents();
			if (cs.length>0) {
				for (int j=0;j<cs.length;j++) {
					colourComponents(cs[j],colour);
				}
			}
			else {
				
				
			}
		}else {
			
		}
		
	}
	
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
