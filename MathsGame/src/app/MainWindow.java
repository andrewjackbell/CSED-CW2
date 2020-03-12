package app;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.*;

public class MainWindow{
	
	Font  titleFont  = new Font("Rockwell", Font.PLAIN, 40);      
	Font medium = new Font(Font.SERIF,Font.PLAIN,26);
	Font large = new Font(Font.SERIF,Font.PLAIN,35);
	
	
	private JFrame frame;
	private DifficultyButton[] diffButtons;
	private JButton playButton;
	private JLabel playText;
	private String difficulty;
	private JLabel bestText;
	private JLabel avText;
	

	public JFrame getFrame() {
		return frame;
	}

	
	public MainWindow(int width,int height){
		
		//Setting up frame (window)
		frame = new JFrame("Game");
		frame.setSize(width,height);
		frame.setBackground(Color.BLUE);
		
		//Setting up panels (sections of the window)
		JPanel leftPanel = new JPanel(); leftPanel.setPreferredSize(new Dimension(260,0)); 
		leftPanel.setLayout(new BoxLayout(leftPanel,BoxLayout.Y_AXIS));
		JPanel bottomPanel = new JPanel(); bottomPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
		bottomPanel.setPreferredSize(new Dimension(0,100));
		JPanel topPanel=new JPanel(new BorderLayout(100,0));
		JPanel infoPanel = new JPanel(); infoPanel.setLayout(new BoxLayout(infoPanel,BoxLayout.Y_AXIS));
	    infoPanel.setBackground(Color.LIGHT_GRAY); infoPanel.setPreferredSize(new Dimension(500,50));	     
	    JPanel rightPanel = new JPanel(); rightPanel.setPreferredSize(new Dimension(60,50));
		
		//Text Labels
		JLabel title=new JLabel("MAIN MENU"); title.setFont(titleFont); 
		JLabel infoTitle = new JLabel("STATS"); infoTitle.setFont(large);
		bestText= new JLabel("Best: "); bestText.setFont(medium);
		avText= new JLabel("Average: "); avText.setFont(medium);
		playText=new JLabel("PLAY"); playText.setFont(large); playText.setVisible(false);
		
		//Buttons
		JButton logoutButton=new JButton("Logout");  logoutButton.setFont(medium);logoutButton.setPreferredSize(new Dimension(110, 12));
		JButton settingsButton = new JButton(); settingsButton.setOpaque(false); settingsButton.setContentAreaFilled(false);
		settingsButton.setBorder(null);settingsButton.setIcon(new ImageIcon("resources/gear.png")); //Adding icon the settings button
		playButton = new JButton(); playButton.setOpaque(false); playButton.setContentAreaFilled(false);
		playButton.setBorder(null); playButton.setIcon(new ImageIcon("resources/play.png")); //Adding icon to play button
		playButton.setVisible(false);
		
		diffButtons = new DifficultyButton[3];
		diffButtons[0]=new DifficultyButton("EASY",Color.GREEN); 
	    diffButtons[1]=new DifficultyButton("MEDIUM", Color.ORANGE); 
	    diffButtons[2]=new DifficultyButton("HARD",Color.RED);
	    for (int i=0;i<3;i++) {
	    	diffButtons[i].addMouseListener(new ButtonListener());
			leftPanel.add(Box.createRigidArea(new Dimension(0,70)));
			leftPanel.add(diffButtons[i]);
	    }
	    
	    //Adding components to their respective panels
		topPanel.add(title,BorderLayout.CENTER);
		topPanel.add(logoutButton,BorderLayout.LINE_START);
		topPanel.add(settingsButton,BorderLayout.LINE_END);
		bottomPanel.add(playText);
		bottomPanel.add(playButton);
	    infoPanel.add(infoTitle); 
	    infoPanel.add(bestText);
	    infoPanel.add(avText);
	    
	    //Adding panels to frame
	    frame.add(rightPanel, BorderLayout.LINE_END);
	    frame.add(bottomPanel,BorderLayout.PAGE_END);
	    frame.add(infoPanel, BorderLayout.CENTER);
	    frame.add(leftPanel, BorderLayout.WEST);
	    frame.add(topPanel, BorderLayout.PAGE_START);
	    

		
	}
	

	
	private void buttonPressed(MouseEvent e) {
		//Loops through array of difficulty buttons, hightlights the one pressed and dims the rest
		for (int i=0;i<3;i++) {
			if (diffButtons[i]==e.getSource()) {
				diffButtons[i].changeBrightness(true);
				for (int j =0; j<3;j++) {
					 if (j!=i) {
						 diffButtons[j].changeBrightness(false);
					 }
				}
				playButton.setVisible(true); //Shows play button and accompanying text
				playText.setVisible(true);
				difficulty=diffButtons[i].getText().toLowerCase(); //Sets difficulty string
			}
		}
	}
	
	public void updateInfo() {
		
		//Reads file depending on difficulty and changes stats on the stats pane
		String file = "resources/data/"+difficulty+".txt";
		try {
			BufferedReader br = new BufferedReader(new FileReader(file));
			String best = br.readLine();
			String average = br.readLine();
			bestText.setText("Best: "+best);
			avText.setText("Average: "+average);
			br.close();
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
		
	}
	
	
	
	public class ButtonListener implements MouseListener{

		@Override
		public void mouseClicked(MouseEvent e) {
			buttonPressed(e);
			updateInfo();
			
		}

		@Override
		public void mousePressed(MouseEvent e) {
			
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			
			
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO make the play, settings buttons do things when hovered over
			
		}

		@Override
		public void mouseExited(MouseEvent e) {
			//"" "" ""
			
		}
		
	}
	
}
