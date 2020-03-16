package app;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class ButtonListener implements MouseListener{
	Window window;
	public ButtonListener(Window window) {
		this.window=window;
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		window.mousePress(e);
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		window.mouseEnter(e);
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		window.mouseExit(e);
		
	}
	
}
