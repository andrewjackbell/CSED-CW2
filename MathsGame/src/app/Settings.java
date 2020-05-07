package app;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.util.Hashtable;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * Settings window class
 * Creates object to display settings window for the game
 * 
 * @author Andrew
 */
public class Settings extends Window{
	
	private JButton backButton;
	private JButton colourButton;
	private JPanel centerPanel;
	private JPanel[] panels;
	private JSlider brightSlider;
	
	private Color[] colours= {Color.WHITE,Color.LIGHT_GRAY,Color.ORANGE,Color.GREEN,Color.PINK};
	private int currentColour=0;
	private int brightness;
	
	/**
	 * Creates the settings window object to be displayed. It instantiates the components and adds them to the main panel
	 * @param manager: the WindowManager of this window, used to call the method to switch from this screen
	 * @param frameSize: the size of the window
	 */
	public Settings(WindowManager manager,Dimension frameSize) {
		super(manager,frameSize);
		Dimension d = new Dimension((int)(frameSize.getWidth()/3),(int)(frameSize.getHeight()/3));
		//Panels
		centerPanel = new JPanel(new GridLayout(2,1,10,10));	
		panels = new JPanel[4];
		for (int i =0;i<4;i++) {
			panels[i]=new JPanel();
			panels[i].setPreferredSize(d);
		}
		panels[1].setLayout(new FlowLayout(FlowLayout.RIGHT)); 
		//Brightness slider
		brightSlider = new JSlider(JSlider.HORIZONTAL,-4,4,0);
		brightSlider.setMajorTickSpacing(1); 
		brightSlider.setPaintTicks(true);
		brightSlider.setPaintLabels(true);
		Hashtable<Integer, JLabel> labels = new Hashtable<Integer, JLabel>();
		labels.put(0, new JLabel("Brightness")); //Puts the label "brightness" in the middle of the slider
		brightSlider.setLabelTable(labels); 
		brightSlider.addChangeListener(new ChangeListener(){
			@Override
			public void stateChanged(ChangeEvent e) { //When the slider is moved
				if (!brightSlider.getValueIsAdjusting()) {//Waits for the slider stop moving
					brightness = brightSlider.getValue(); 
					//Sets the brightness of the screen to the new value
					Color current = colours[currentColour];
					if (brightness<0) {
						for (int i=0;i<Math.abs(brightness);i++) {
							current = current.darker();

						}
					}else if (brightness>0){
						for (int i=0;i<Math.abs(brightness);i++) {
							current = current.brighter();
						}
					}
					manager.setColour(current);//updates the brightness of all the windows
					
				}
			}
		});
		
		//Buttons	
		backButton = new GButton("Back",Color.CYAN,this);
		colourButton = new GButton("Change Colour",Color.CYAN,this);
		
		//Adding components to respective panels
		panels[1].add(backButton);
		centerPanel.add(colourButton);
		centerPanel.add(brightSlider);
		
		//Addings panels to main panel
		mainPanel.add(centerPanel,BorderLayout.CENTER);
		mainPanel.add(panels[1],BorderLayout.NORTH);
		mainPanel.add(panels[2],BorderLayout.SOUTH);
		mainPanel.add(panels[3],BorderLayout.WEST);
		mainPanel.add(panels[0],BorderLayout.EAST);

	}
	@Override
	public void mousePress(MouseEvent e) {
		if (e.getSource()==backButton) {
			super.manager.changeState("menu"); //Goes back to menu if back button is pressed
		}
		
		else if (e.getSource()==colourButton) {
			//Cycles through colours in the array
			if (currentColour==4) {
				currentColour=0; //Loops back around to the start of array
			}
			else {
				currentColour++;
			}
			super.manager.setColour(colours[currentColour]);//Updates colours
		}
	}
	
	
}
