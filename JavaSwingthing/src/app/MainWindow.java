package app;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.*;




public class MainWindow{
	private JFrame frame;
	private JPanel leftPanel;
	private JPanel topPanel;
	private JPanel bottomPanel;
	private DifficultyButton[] diffButtons;
	private JButton playButton;
	private JPanel infoPanel;
	private JPanel rightPanel;
	private String difficulty;
	private JLabel bestText;
	private JLabel avText;
	private JLabel worstText;
	
	public String getDifficulty() {
		return difficulty;
	}
	public JFrame getFrame() {
		return frame;
	}

	public JPanel getLeftPanel() {
		return leftPanel;
	}

	public JPanel getTopPanel() {
		return topPanel;
	}

	public JButton[] getDiffButtons() {
		return diffButtons;
	}

	public JPanel getInfoPanel() {
		return infoPanel;
	}

	

	Font  titleFont  = new Font("Rockwell", Font.PLAIN,  40);      
	Font medium = new Font(Font.SERIF,Font.PLAIN,26);
	Font large = new Font(Font.SERIF,Font.PLAIN,35);
	public MainWindow(int width,int height){
		frame = new JFrame("Game");
		frame.setSize(width,height);
		frame.setBackground(Color.BLUE);
		leftPanel = new JPanel();
		leftPanel.setPreferredSize(new Dimension(260,0));
		rightPanel = new JPanel();
		bottomPanel = new JPanel();
		bottomPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
		leftPanel.setLayout(new BoxLayout(leftPanel,BoxLayout.Y_AXIS));
		topPanel=new JPanel(new BorderLayout(100,0));
		JButton backButton=new JButton("Back");  backButton.setFont(medium);backButton.setPreferredSize(new Dimension(110, 12));
		topPanel.add(backButton,BorderLayout.LINE_START);
	    JLabel title=new JLabel("MAIN MENU"); title.setFont(titleFont); 
	    topPanel.add(title,BorderLayout.CENTER);
	    
		JButton settingsButton = new JButton(); settingsButton.setOpaque(false); settingsButton.setContentAreaFilled(false);
		settingsButton.setBorder(null);settingsButton.setIcon(new ImageIcon("resources/gear.png"));
		playButton = new JButton(); playButton.setOpaque(false); playButton.setContentAreaFilled(false);
		playButton.setBorder(null);playButton.setIcon(new ImageIcon("resources/play.png"));
		
		topPanel.add(settingsButton,BorderLayout.LINE_END);
	    diffButtons = new DifficultyButton[3];
	    diffButtons[0]=createDifficultyButton("EASY",Color.GREEN); 
	    diffButtons[1]=createDifficultyButton("MEDIUM", Color.ORANGE); 
	    diffButtons[2]=createDifficultyButton("HARD",Color.RED);
	    for (int i=0;i<3;i++) {
	    	diffButtons[i].addActionListener(new ButtonListener());
	    }
	    
	    bottomPanel.add(playButton);
	    
	    
	    
	    infoPanel = new JPanel();
	    infoPanel.setBackground(Color.LIGHT_GRAY);
	    infoPanel.setLayout(new BoxLayout(infoPanel,BoxLayout.Y_AXIS));
	    
	    JLabel infoTitle = new JLabel("STATS"); infoTitle.setFont(large);
	    infoPanel.add(infoTitle);
	    bestText= new JLabel("Best: "); bestText.setFont(medium);
	    infoPanel.add(bestText);
	    avText= new JLabel("Average: "); avText.setFont(medium);
	    infoPanel.add(avText);
	    worstText= new JLabel("Worst: "); worstText.setFont(medium);
	    infoPanel.add(worstText);
	    
	    bottomPanel.setPreferredSize(new Dimension(0,100));
	    infoPanel.setPreferredSize(new Dimension(500,50));
	    rightPanel.setPreferredSize(new Dimension(60,50));
	    frame.add(rightPanel, BorderLayout.LINE_END);
	    frame.add(bottomPanel,BorderLayout.PAGE_END);
	    frame.add(infoPanel, BorderLayout.CENTER);
	    frame.add(leftPanel, BorderLayout.WEST);
	    frame.add(topPanel, BorderLayout.PAGE_START);
	    

		
	}
	
	private DifficultyButton createDifficultyButton(String name, Color color) {
		DifficultyButton button = new DifficultyButton(name,color);
		leftPanel.add(Box.createRigidArea(new Dimension(0,70)));
		leftPanel.add(button);
		return button;
	}
	
	private void buttonPressed(ActionEvent e) {
		for (int i=0;i<3;i++) {
			if (diffButtons[i]==e.getSource()) {
				diffButtons[i].changeBrightness(true);
				difficulty=diffButtons[i].getText().toLowerCase();
			}else {
				diffButtons[i].changeBrightness(false);
			}
		}
	}
	
	public void updateInfo() {
		String file = "resources/data/"+difficulty+".txt";
		try {
			BufferedReader br = new BufferedReader(new FileReader(file));
			String best = br.readLine();
			String average = br.readLine();
			String worst = br.readLine();
			bestText.setText("Best: "+best);
			avText.setText("Average: "+average);
			worstText.setText("Worst: "+worst);

			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
		
	}
	
	
		
	public class ButtonListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			buttonPressed(e);
			updateInfo();
		}
	}
	
}