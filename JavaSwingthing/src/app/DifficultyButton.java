package app;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.border.Border;

public class DifficultyButton extends JButton{

	private static final long serialVersionUID = 1L;

	private Font font = new Font(Font.SERIF,Font.PLAIN,35);
	private Color color;
	private Color bright;
	private Color dim;
	
	private Border border = BorderFactory.createLineBorder(Color.BLACK);
	public DifficultyButton(String text, Color color){
		super(text);
		bright = color.brighter().brighter();
		dim= color.darker().darker();
		this.setBackground(dim);
		this.setBorder(border);
		this.setFont(font);
		this.setContentAreaFilled(true);
		
		
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
