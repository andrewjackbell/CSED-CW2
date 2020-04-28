package app;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Summary extends Window{
	
	private JPanel nPanel;
	private JPanel sPanel;
	private JPanel wPanel;
	private JPanel ePanel;
	private JPanel centerPanel;
	private GButton button;
	private JLabel titleText; 
	private JLabel scoreText;
	private JLabel correctText;
	private JLabel incorrectText;
	
	public Summary(WindowManager manager) {
		super(manager);
		Dimension d = new Dimension(300,300);
		nPanel = new JPanel(); nPanel.setPreferredSize(d);
		wPanel = new JPanel(); wPanel.setPreferredSize(d);
		ePanel = new JPanel(); ePanel.setPreferredSize(d);
		sPanel = new JPanel(); sPanel.setPreferredSize(d);
		centerPanel = new JPanel(); centerPanel.setLayout(new BoxLayout(centerPanel,BoxLayout.Y_AXIS));
		button = new GButton("Continue",Color.GREEN,this);
		scoreText = new JLabel("Score: ");
		correctText= new JLabel("Correct Answers: ");
		incorrectText= new JLabel("Incorrect Answers: ");
		titleText= new JLabel("Game Sumarry");
		centerPanel.add(scoreText);
		centerPanel.add(correctText);
		centerPanel.add(incorrectText);
		centerPanel.add(button);
		mainPanel.add(centerPanel,BorderLayout.CENTER);
		mainPanel.add(nPanel,BorderLayout.NORTH);
		mainPanel.add(wPanel,BorderLayout.WEST);
		mainPanel.add(sPanel,BorderLayout.SOUTH);
		mainPanel.add(ePanel,BorderLayout.EAST);
		
	}
	@Override
	public void mousePress(MouseEvent e) {
		if (e.getSource()==button) {
			manager.changeState("menu");
		}
	}
	
	
	
	public void setScoreValues(int correct, int incorrect) {
		int score= correct-incorrect;
		scoreText.setText("Score: " + score);
		correctText.setText("Correct Answers: "+ correct);
		incorrectText.setText("Incorrect Answers: "+ incorrect);
	}
	
	
}
