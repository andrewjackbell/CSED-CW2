package app;

import java.awt.CardLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class WindowManager {
	private JFrame frame;
	private CardLayout layout = new CardLayout();
	private JPanel container;
	private MenuWindow menuWindow;
	private SettingsWindow settingsWindow;
	
	
	public WindowManager() {
		frame = new JFrame("efdjas");
		frame.setSize(900,900);
		container = new JPanel(layout);
		menuWindow = new MenuWindow();
		settingsWindow = new SettingsWindow();
		container.add(menuWindow.getMainPanel(),"1");
		container.add(settingsWindow.getMainPanel(),"2");
		frame.add(container);
		frame.setVisible(true);
		layout.show(container, "1");

	}
	
	public static void main(String[] args) {
		new WindowManager();
	}
	
}
