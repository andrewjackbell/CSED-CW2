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
	private String user;
	
	
	public WindowManager() {
		frame = new JFrame("Quick Maffs");
		frame.setSize(900,900);
		container = new JPanel(layout);
		loginWindow = new Login(this);
		menuWindow = new Menu(this);
		settingsWindow = new Settings(this);
		
		container.add(loginWindow.getMainPanel(),"login");
		container.add(menuWindow.getMainPanel(),"menu");
		container.add(settingsWindow.getMainPanel(),"settings");
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
		}
		layout.show(container, state);
	}
	public void setUser(String username) {
		user=username;
	}

	
	

}
