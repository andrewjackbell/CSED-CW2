package app;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;

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
	
	private Border border = BorderFactory.createLineBorder(Color.BLACK);
	public GButton(ImageIcon icon){
		super();
		this.setOpaque(false); 
		this.setContentAreaFilled(false);
		this.setBorder(null); 
		this.setIcon(icon);
	
	}
	public GButton(String text, Color color){
		super(text);
		bright = color.brighter().brighter();
		dim= color.darker().darker();
		this.setBackground(dim);
		this.setBorder(border);
		this.setFont(font);
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
	
}