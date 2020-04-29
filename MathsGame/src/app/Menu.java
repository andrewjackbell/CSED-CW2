package app;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import javax.swing.*;

/**
 * Menu window class
 * Creates an object which displays the menu for the game
 */
public class Menu extends Window{

	private JPanel rightPanel;
	private JPanel leftPanel;
	private JPanel topPanel;
	private JPanel bottomPanel;
	private JPanel infoPanel;
	private JPanel leftInfo;
	private JPanel rightInfo;
	
	private GButton[] diffButtons;
	private GButton playButton;
	private GButton settingsButton;
	private GButton logoutButton;
	private GButton refreshButton;
	
	
	private JLabel title;
	private JLabel playText;
	private JLabel infoTitle;
	private JLabel bestText;
	private JLabel avText;
	private JLabel lastText;

	private ImageIcon hoverGear;
	private ImageIcon defaultGear;
	private ImageIcon hoverArrow;
	private ImageIcon defaultArrow;
	private ImageIcon defaultCircleArrow;
	private ImageIcon hoverCircleArrow;
	
	private int[] bests;
	private int[] averages;
	private int[] last;
	private String user;
	private int difficulty;
	/**
	 * 
	 * Creates the menu window object to be displayed. It instantiates the components and adds them to the main panel
	 * @param manager: the WindowManager of this window, used to call the method to switch from this screen
	 */
	public Menu(WindowManager manager){
		super(manager);
		
		bests=new int[3];
		averages=new int[3];
		last = new int[3];
		
		//Setting up mainPanel
		mainPanel.setBackground(Color.BLUE);
		
		//Setting up panels (sections of the window)
		 rightPanel = new JPanel(); rightPanel.setPreferredSize(new Dimension(60,50));
		 leftPanel = new JPanel(); leftPanel.setPreferredSize(new Dimension(260,0)); 
		 leftPanel.setLayout(new BoxLayout(leftPanel,BoxLayout.Y_AXIS));
		 bottomPanel = new JPanel(); bottomPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
		 bottomPanel.setPreferredSize(new Dimension(0,100));
		 topPanel=new JPanel(new BorderLayout(100,0));
		 infoPanel = new JPanel(); infoPanel.setLayout(new BorderLayout());
	     infoPanel.setBackground(Color.LIGHT_GRAY); infoPanel.setPreferredSize(new Dimension(500,50));	     
	     leftInfo = new JPanel(); leftInfo.setLayout(new BoxLayout(leftInfo,BoxLayout.Y_AXIS));
		 rightInfo = new JPanel(new FlowLayout(FlowLayout.LEFT));
	    
	    
		//Text Labels
		title=new JLabel("MAIN MENU"); title.setFont(GFonts.titleFont); 
		infoTitle = new JLabel("STATS"); infoTitle.setFont(GFonts.largeFont);
		bestText= new JLabel("Select Difficulty"); bestText.setFont(GFonts.mediumFont);
		avText= new JLabel(""); avText.setFont(GFonts.mediumFont);
		lastText = new JLabel(""); lastText.setFont(GFonts.mediumFont);
		playText=new JLabel("PLAY"); playText.setFont(GFonts.largeFont); playText.setVisible(false);
		
		//Image Icons
		hoverGear = new ImageIcon("resources/hoverGear.png");
		defaultGear= new ImageIcon("resources/gear.png");
		hoverArrow = new ImageIcon("resources/hoverArrow.png");
		defaultArrow = new ImageIcon("resources/arrow.png");
		defaultCircleArrow = new ImageIcon("resources/circleArrow.png");
		hoverCircleArrow = new ImageIcon("resources/hoverCircleArrow.png");
				
		//Buttons
		logoutButton=new GButton("Logout",Color.cyan,this); logoutButton.setFont(GFonts.mediumFont); logoutButton.setPreferredSize(new Dimension(110, 12));
		settingsButton = new GButton(defaultGear,this);
		playButton = new GButton(defaultArrow,this); 
		playButton.setVisible(false);
		refreshButton = new GButton(defaultCircleArrow,this);

		
		diffButtons = new GButton[3];
		diffButtons[0]=new GButton("EASY",Color.GREEN,this); 
	    diffButtons[1]=new GButton("MEDIUM", Color.ORANGE,this); 
	    diffButtons[2]=new GButton("HARD",Color.RED,this);
	    for (int i=0;i<3;i++) {
			leftPanel.add(Box.createRigidArea(new Dimension(0,70)));
			leftPanel.add(diffButtons[i]);
	    }
	    
	    //Adding components to their respective panels
		topPanel.add(title,BorderLayout.CENTER);
		topPanel.add(logoutButton,BorderLayout.LINE_START);
		topPanel.add(settingsButton,BorderLayout.LINE_END);
		bottomPanel.add(playText);
		bottomPanel.add(playButton);
	    leftInfo.add(infoTitle); 
	    leftInfo.add(bestText);
	    leftInfo.add(avText);
	    leftInfo.add(lastText);
	    rightInfo.add(refreshButton);
	    infoPanel.add(leftInfo, BorderLayout.CENTER);
	    infoPanel.add(rightInfo, BorderLayout.EAST);
	    
	    //Adding sub-panels to main panel
	    mainPanel.add(rightPanel, BorderLayout.LINE_END);
	    mainPanel.add(bottomPanel,BorderLayout.PAGE_END);
	    mainPanel.add(infoPanel, BorderLayout.CENTER);
	    mainPanel.add(leftPanel, BorderLayout.WEST);
	    mainPanel.add(topPanel, BorderLayout.PAGE_START);	    
		
	}
	
	@Override
	public void mousePress(MouseEvent e) {
		//Iterates through each difficulty button to check if any have been pressed. If it has, it brightens this particular button and dims the other two.
		for (int i=0;i<3;i++) {
			if (diffButtons[i]==e.getSource()) {
				diffButtons[i].changeBrightness(true);
				for (int j =0; j<3;j++) {
					 if (j!=i) {
						 diffButtons[j].changeBrightness(false);
					 }
				}
				playButton.setVisible(true); playText.setVisible(true);//Shows play button and accompanying text
				difficulty=i; //Changes difficulty value to match the change in which difficulty button is selected
				refreshValues(); //Updates the values shown on the screen to match the difficulty selected
			}
		}
		
		//These buttons invoke the method in the manager to change to a different screen if the appropriate button is pressed
		if (settingsButton==e.getSource()) {
			super.manager.changeState("settings"); 
		}
		else if (logoutButton==e.getSource()) {
			super.manager.changeState("login");
		}
		else if (playButton==e.getSource()) {
			super.manager.setDifficulty(difficulty); //Passes required information to run the game to the manager to pass on to the game class
			super.manager.setUser(user);
			super.manager.changeState("game");
		}
		else if (refreshButton==e.getSource()) { //This button reads the user's file again to update the values in memory and change them on the screen if necessary
			readData();
			refreshValues();
		}
	}
	
	@Override 
	public void mouseEnter(MouseEvent e) {
		
		//Changes icons/brightness when hovered over them
		if (e.getSource()==playButton) {
			playButton.setIcon(hoverArrow);
		}
		else if (e.getSource()==settingsButton) {
			settingsButton.setIcon(hoverGear);
		}
		else if (e.getSource()==refreshButton) {
			refreshButton.setIcon(hoverCircleArrow);
		}
		else if (e.getSource()==logoutButton) {
			logoutButton.changeBrightness(true);
		}
	}
	@Override 
	public void mouseExit(MouseEvent e) {
		
		//Changes icons/brightness when no longer hovered over them
		if (e.getSource()==playButton) {
			playButton.setIcon(defaultArrow);
		}
		else if (e.getSource()==settingsButton) {
			settingsButton.setIcon(defaultGear);
		}
		else if (e.getSource()==refreshButton) {
			refreshButton.setIcon(defaultCircleArrow);
		}
		else if (e.getSource()==logoutButton) {
			logoutButton.changeBrightness(false);
		}
	}
	
	/**
	 * Updates the values shown on the screen based on the current difficulty selected and the values in the memory
	 */
	public void refreshValues() {
		avText.setText("Average: "+Integer.toString(averages[difficulty]));
		bestText.setText("Best: "+Integer.toString(bests[difficulty]));
		lastText.setText("Previous: "+Integer.toString(last[difficulty]));
	}
	
	/**
	 * 
	 * Reads the user file and updates the memory with new data to be displayed on the screen
	 * 
	 */
	public void readData() {
		
		String file = "resources/data/"+user+".txt";
		try {
			BufferedReader br = new BufferedReader(new FileReader(file));
			br.readLine();
			
			String[] scores;
			//Goes through each line of scores and calculates the greatest, average and most recent score and adds them to the appropriate arrays
			for (int i=0;i<3;i++) {
				int max= 0;
				int total=0;
				int current=0;
				scores =br.readLine().split(",");
				
				for (int j=1;j<scores.length;j++) {
					current = Integer.parseInt(scores[j]);
					total+=current;
					if (current>max) {
						max=current;
					}
				}
				
				this.averages[i]=total/(scores.length-1);
				this.bests[i]=max;
				this.last[i]=current;
			}
			
			br.close();
		}
		catch(ArithmeticException e) {
			
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	

	public void setUser(String user) {
		this.user=user;
	}
	/**
	 * Updates the memory value of the most recent score. This is called by the game class so that the menu doesn't have to read the memory each time a game is played.
	 * @param score
	 */
	public void setLastScore(int score) {
		this.last[difficulty]=score;
		refreshValues();
	}
	
	
	
		
}
