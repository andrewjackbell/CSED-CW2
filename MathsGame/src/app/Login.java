package app;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class Login extends Window{
	private JTextField nameField;
	private JPasswordField passField;
	private JButton signUpButton;
	private JButton loginButton;
	private JLabel alertText;
	private JPanel centerPanel;
	private JPanel southPanel;
	private JPanel blankPanel;
	
	public Login(WindowManager manager) {
		super(manager);
		centerPanel = new JPanel(); centerPanel.setPreferredSize(new Dimension(100,100));
		southPanel=new JPanel();
		blankPanel = new JPanel(); blankPanel.setPreferredSize(new Dimension(100,100));
		
		nameField= new JTextField(16);
		passField = new JPasswordField(16);
		signUpButton = new GButton("Sign Up",Color.CYAN);
		signUpButton.addMouseListener(new ButtonListener(this));
		loginButton = new GButton("Login",Color.MAGENTA);
		loginButton.addMouseListener(new ButtonListener(this));
		alertText = new JLabel(" ");
		alertText.setPreferredSize(new Dimension(100,100));
		centerPanel.add(nameField);
		centerPanel.add(passField);
		southPanel.add(signUpButton);
		southPanel.add(loginButton);
		
		//mainPanel.add(blankPanel,BorderLayout.PAGE_END);
		mainPanel.add(centerPanel, BorderLayout.CENTER);
		mainPanel.add(southPanel,BorderLayout.SOUTH);
		mainPanel.add(alertText,BorderLayout.NORTH);
		

		
	}
	
	public String hash(char[] arr) {
		String original = Arrays.toString(arr);
		try {
			MessageDigest digest = MessageDigest.getInstance("SHA-256");
			byte[] encodedHash = digest.digest(original.getBytes(StandardCharsets.UTF_8));
			String out ="";
			for (int i=0;i<encodedHash.length;i++) {
	            out += String.format("%02X", encodedHash[i]);
	        }
			return out;
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			return null;
			
		}
		
	}
	
	
	public void authenticate() {
		String username = nameField.getText();
		char[] password = passField.getPassword();
		File file = new File("resources/data/"+username+".txt");
			try {
				BufferedReader br = new BufferedReader(new FileReader(file));
				
				if (br.readLine().equals(hash(password))) {
					br.close();
					nameField.setText(null);
					passField.setText(null);
					manager.setUser(username);
					manager.changeState("menu");
				} else {
					alertText.setText("Incorrect Password");
					br.close();
				}
				
			} catch (FileNotFoundException e) {
				alertText.setText("Username not found");
			} catch (IOException e) {
				e.printStackTrace();
			}
		

	}
	
	public void createUser() {
		String username = nameField.getText();
		char[] password = passField.getPassword();
		if (username.length() < 2) {
			alertText.setText("Username must be at least 2 characters");
		} else if (password.length < 6 || password.length > 16) {
			alertText.setText("Password must be between 6 and 16 characters");
		} else {		
			File file = new File("resources/data/"+username+".txt");
			try {
				if (file.createNewFile()) {
					PrintWriter pw = new PrintWriter(file);
					pw.println(hash(password));
					pw.close();
					alertText.setText("User Created");
				}else {
					alertText.setText("Username taken");
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	@Override
	public void mousePress(MouseEvent e) {
		if (e.getSource()==signUpButton) {
			createUser();
		}
		if (e.getSource()==loginButton) {
			authenticate();
		}
	}
		
}
