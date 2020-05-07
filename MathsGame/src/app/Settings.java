package app;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.util.Dictionary;
import java.util.Hashtable;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;

public class Settings extends Window{
	
	private JButton backButton;
	private JButton colourButton;
	private JPanel centerPanel;
	private JPanel[] panels;
	private JSlider brightSlider;
	
	private Color[] colours= {Color.WHITE,Color.LIGHT_GRAY,Color.ORANGE,Color.GREEN,Color.PINK};
	private int currentColour=0;
	
	public Settings(WindowManager manager,Dimension frameSize) {
		super(manager,frameSize);
		Dimension d = new Dimension((int)(frameSize.getWidth()/3),(int)(frameSize.getHeight()/3));
		panels = new JPanel[4];
		for (int i =0;i<4;i++) {
			panels[i]=new JPanel();
			panels[i].setPreferredSize(d);
		}
		Hashtable<Integer,JLabel > labelTable = new Hashtable<Integer, JLabel>();
		labelTable.put(50, new JLabel("Brightness"));
		brightSlider = new JSlider(JSlider.HORIZONTAL,0,100,50); brightSlider.setLabelTable(labelTable);
		centerPanel = new JPanel(new GridLayout(2,1,10,10));
		panels[1].setLayout(new FlowLayout(FlowLayout.RIGHT));
		backButton = new GButton("Back",Color.CYAN,this);
		colourButton = new GButton("Colour",Color.LIGHT_GRAY,this);
		panels[1].add(backButton);
		
		mainPanel.setBackground(Color.GREEN);
		centerPanel.add(colourButton);
		centerPanel.add(brightSlider);
		mainPanel.add(centerPanel,BorderLayout.CENTER);
		mainPanel.add(panels[1],BorderLayout.NORTH);
		mainPanel.add(panels[2],BorderLayout.SOUTH);
		mainPanel.add(panels[3],BorderLayout.WEST);
		mainPanel.add(panels[0],BorderLayout.EAST);

	}
	@Override
	public void mousePress(MouseEvent e) {
		if (e.getSource()==backButton) {
			super.manager.changeState("menu");
		}
		
		else if (e.getSource()==colourButton) {
			if (currentColour==4) {
				currentColour=0;
			}
			else {
				currentColour++;
			}
			super.manager.setColour(colours[currentColour]);
		}
	}
	
	
}
