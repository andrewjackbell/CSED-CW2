package app;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.border.Border;

public class GButton extends JButton{

	private static final long serialVersionUID = 1L;

	private Font font = new Font(Font.SERIF,Font.PLAIN,35);
	private Color color;
	private Color bright;
	private Color dim;
	private MouseListener listener;
	private Window window;
	
	private Border border = BorderFactory.createLineBorder(Color.BLACK);
	public GButton(ImageIcon icon, Window window){
		super();
		this.setOpaque(false); 
		this.setContentAreaFilled(false);
		this.setBorder(null); 
		this.setIcon(icon);
		this.window=window;
		listener=new ButtonListener();
		this.addMouseListener(listener);
	
	}
	public GButton(String text, Color color, Window window){
		super(text);
		bright = color.brighter().brighter();
		dim= color.darker().darker();
		this.setBackground(dim);
		this.setBorder(border);
		this.setFont(font);
		this.window=window;
		listener=new ButtonListener();
		this.addMouseListener(listener);
		
	}
	public void changeBrightness(boolean brighter) {
		if (brighter) {
			color=bright;
		}
		else {
			color=dim;
		}
		this.setBackground(color);
	}
	
	
	public class ButtonListener implements MouseListener{
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
}