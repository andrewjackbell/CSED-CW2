package app;

import java.awt.CardLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class WindowManager {
	private JFrame frame;
	private CardLayout layout = new CardLayout();
	private JPanel container;
	private Menu menuWindow;
	private Login loginWindow;
	private Settings settingsWindow;
	private Game gameWindow;
	private Summary summaryWindow;
	
	private String user;
	private int difficulty;
	private int score;
	private int correctAns;
	private int incorrectAns;
	
	
	public WindowManager() {
		frame = new JFrame("Quick Maffs");
		frame.setSize(900,900);
		container = new JPanel(layout);
		loginWindow = new Login(this);
		menuWindow = new Menu(this);
		settingsWindow = new Settings(this);
		gameWindow = new Game(this);
		summaryWindow = new Summary(this);
		
		container.add(loginWindow.getMainPanel(),"login");
		container.add(menuWindow.getMainPanel(),"menu");
		container.add(settingsWindow.getMainPanel(),"settings");
		container.add(gameWindow.getMainPanel(),"game");
		container.add(summaryWindow.getMainPanel(),"summary");
		frame.add(container);
		frame.setVisible(true);
		layout.show(container, "login");

	}
	
	public static void main(String[] args) {
		new WindowManager();
	}
	
	public void changeState(String state) {
		if (state.equals("menu")) {
			menuWindow.setUser(user);
			menuWindow.setLastScore(score);
			menuWindow.readData();
		}
		if (state.equals("summary")){
			summaryWindow.setScoreValues(correctAns,incorrectAns);
		}
		layout.show(container, state);
		
		if (state.equals("game")) {
			gameWindow.playGame(difficulty,user);
			
		}
	}
	public void setUser(String user) {
		this.user=user;
	}
	public void setDifficulty(int difficulty) {
		this.difficulty=difficulty;
	}

	public void setScoreValues(int correct,int incorrect) {
		this.score=correct-incorrect;
		this.correctAns=correct;
		this.incorrectAns=incorrect;
		
	}

	
	

}
