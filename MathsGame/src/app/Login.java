package app;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
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
	private JPanel centerPanel;
	private JPanel southPanel;
	private JPanel eastPanel;
	private JPanel westPanel;
	
	private JTextField nameField;
	private JPasswordField passField;
	private JButton signUpButton;
	private JButton loginButton;
	
	private JLabel alertText;
	Font medium = new Font(Font.SERIF,Font.PLAIN,26);
	

	
	
	public Login(WindowManager manager) {
		super(manager);
		centerPanel = new JPanel(); centerPanel.setLayout(new GridLayout(3,2,10,10));centerPanel.setSize(new Dimension(100,100));
		southPanel=new JPanel(); southPanel.setPreferredSize(new Dimension(100,500));
		eastPanel = new JPanel(); eastPanel.setPreferredSize(new Dimension(300,100));
		westPanel = new JPanel(); westPanel.setPreferredSize(new Dimension(300,100));
		JPanel blankPanel = new JPanel();
		JPanel blankPanel1 = new JPanel();
		JPanel blankPanel2 = new JPanel();
		JPanel blankPanel3 = new JPanel();

		nameField= new JTextField("Username",16);
		nameField.setPreferredSize(new Dimension(0,10));
		nameField.setForeground(Color.GRAY);
		nameField.addFocusListener(new FocusListener() {


			public void focusGained(FocusEvent e) {
				if (nameField.getForeground().equals(Color.GRAY))
			    nameField.setText("");
			    nameField.setForeground(Color.BLACK);
			}
	
	
			public void focusLost(FocusEvent e) {
				if (nameField.getText().equals("")) {
					resetUsername();
				}
			}
	
			});      
		
		passField = new JPasswordField("Password",16);
		passField.setForeground(Color.GRAY);
		passField.setEchoChar((char)0);
		passField.addFocusListener(new FocusListener() {


			public void focusGained(FocusEvent e) {
				if (passField.getForeground().equals(Color.GRAY)) {
					passField.setEchoChar('•');
				    passField.setText("");
				    passField.setForeground(Color.BLACK);
				}
			}
	
	
			public void focusLost(FocusEvent e) {
				if (passField.getText().equals("")) {
					resetPassword();
				}
				
			}
	
			});
		passField.setPreferredSize(new Dimension(0,50));
		signUpButton = new GButton("Sign Up",Color.CYAN);
		signUpButton.addMouseListener(new ButtonListener(this));
		loginButton = new GButton("Login",Color.MAGENTA);
		loginButton.addMouseListener(new ButtonListener(this));
		alertText = new JLabel("Welcome to quick maffs. Please login or sign up");
		alertText.setFont(medium);
		alertText.setPreferredSize(new Dimension(100,120));
		centerPanel.add(blankPanel);
		centerPanel.add(blankPanel1);
		centerPanel.add(blankPanel2);
		centerPanel.add(blankPanel3);
		centerPanel.add(nameField);
		centerPanel.add(eastPanel);
		centerPanel.add(passField);
		centerPanel.add(eastPanel);
		 
		southPanel.add(signUpButton);
		southPanel.add(loginButton);

		
		mainPanel.add(westPanel,BorderLayout.WEST);
		mainPanel.add(eastPanel,BorderLayout.EAST);

		mainPanel.add(southPanel,BorderLayout.SOUTH);
		mainPanel.add(alertText,BorderLayout.PAGE_START);
		mainPanel.add(centerPanel,BorderLayout.CENTER);

		
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
					resetUsername();
					resetPassword();
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
		
		
		
		if (username.length() < 2||username.equals("Username")) {
			alertText.setText("Username must be at least 2 characters");
		} else if (password.length < 6 || password.length > 16||String.valueOf(password).equals("Password")) {
			alertText.setText("Password must be between 6 and 16 characters");
		} else {		
			File file = new File("resources/data/"+username+".txt");
			try {
				if (file.createNewFile()) {
					PrintWriter pw = new PrintWriter(file);
					pw.println(hash(password));
					pw.println(",");pw.println(",");pw.println(",");
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
	
	
	private void resetPassword() {
		passField.setEchoChar((char)0);
		passField.setText("Password");
		passField.setForeground(Color.GRAY);
		
	}
	private void resetUsername() {
		nameField.setText("Username");
		nameField.setForeground(Color.GRAY);
	}
	
		
}
