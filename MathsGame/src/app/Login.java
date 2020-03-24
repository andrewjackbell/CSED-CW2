package app;

import java.awt.BorderLayout;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class Login extends Window{
	JTextField nameField;
	JPasswordField passField;
	JButton submitButton;
	
	public Login(WindowManager manager) {
		super(manager);
		nameField= new JTextField(16);
		passField = new JPasswordField(16);
		submitButton = new JButton("Submit");
		submitButton.addMouseListener(new ButtonListener(this));
		mainPanel.add(submitButton,BorderLayout.CENTER);
		mainPanel.add(nameField,BorderLayout.CENTER);
		mainPanel.add(passField,BorderLayout.CENTER);

	}
	
	public void authenticate() {
		try {
			if (nameField.getText().equals("andy")){
<<<<<<< Updated upstream
				
					manager.changeState("menu");
				
			}

		}catch(NullPointerException e) {}
=======
					/*
					char[] givenPass = passField.getPassword();
					File file = new File("resources/data/chicken.txt");
					PrintWriter pw = new PrintWriter(file);
					pw.println(hash(givenPass));
					pw.close();
					*/
					manager.changeState("menu");
			}

		}catch(NullPointerException e) {
			
		}// catch (IOException e) {
		//	e.printStackTrace();
		//}
>>>>>>> Stashed changes

	}
	@Override
	public void mousePress(MouseEvent e) {
		if (e.getSource()==submitButton) {
			authenticate();
		}
	}
		
}
