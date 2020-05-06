package app;

import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import javax.swing.JFrame;
import javax.swing.JPanel;


/**
 * 
 * This class creates an object containing all the windows needed for the game and switches between them in cardLayout when the user navigates through them. 
 * Contains methods necessary to pass information between windows. 
 *
 */
public class WindowManager {
	private JFrame frame;
	private JPanel container;
	private CardLayout layout = new CardLayout();
	
	//Windows
	private Menu menuWindow;
	private Login loginWindow;
	private Settings settingsWindow;
	private Game gameWindow;
	private Summary summaryWindow;
	
	//Temporary variables for passing values between windows
	private String user;
	private int difficulty;
	private int score;
	private int correctAns;
	private int incorrectAns;
	
	/**
	 * Creates a window frame, along with all the necessary windows for the game and adds them to the container
	 * Container is added to the window frame and then made visible
	 */
	public WindowManager() {
		frame = new JFrame("Quick Maffs");
		frame.setResizable(false);
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		frame.setSize(screenSize);
		container = new JPanel(layout);
		
		loginWindow = new Login(this,screenSize);
		menuWindow = new Menu(this,screenSize);
		settingsWindow = new Settings(this,screenSize);
		gameWindow = new Game(this,screenSize);
		summaryWindow = new Summary(this,screenSize);
		
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
	
	/**
	 *
	 *Changes the screen to the name of the screen given, and passes necessary information and invokes methods in the window objects
	 * 
	 *@param state: the name of the state to change to
	 */
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
