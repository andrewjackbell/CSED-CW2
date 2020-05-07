package app;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
/**
 * Login window class
 * Creates an object which displays the login window for the game
 *
 */
public class Login extends Window{
	private JPanel centerPanel;
	private JPanel southPanel;
	private JPanel eastPanel;
	private JPanel westPanel;
	private JPanel[] blanks;
	
	private JTextField nameField;
	private JPasswordField passField;
	private JLabel alertText;
	
	private GButton signUpButton;
	private GButton loginButton;
	private JPanel topPanel;
	
	/**
	 * 
	 * Creates the login window object to be displayed. It instantiates the components and adds them to the main panel
	 * @param manager: the WindowManager of this window, used to call the method to switch from this screen
	 */
	public Login(WindowManager manager,Dimension frameSize) {
		
		super(manager,frameSize);
		Dimension x = new Dimension((int)(frameSize.getWidth()/2.5),0);
		
		topPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));  topPanel.setPreferredSize(new Dimension(0,(int)(frameSize.getHeight()/4)));
		centerPanel = new JPanel(); centerPanel.setLayout(new GridLayout(3,4,10,10)); 

		southPanel=new JPanel(); southPanel.setPreferredSize(new Dimension(0,(int)(frameSize.getHeight()/1.8)));
		eastPanel = new JPanel(); eastPanel.setPreferredSize(x);
		westPanel = new JPanel(); westPanel.setPreferredSize(x);
		
		blanks=new JPanel[4];
		for (int i=0;i<4;i++) {
			blanks[i]=new JPanel();
		}

		nameField= new JTextField("Username",16);
		nameField.setForeground(Color.GRAY);
		
		nameField.addFocusListener(new FocusListener() { //Creates new thread to check if focus is gained or lost on the username field
			public void focusGained(FocusEvent e) {
				//Removes the label from the username field when clicked on
				if (nameField.getForeground().equals(Color.GRAY))
			    nameField.setText("");
			    nameField.setForeground(Color.BLACK);
			}
	
	
			public void focusLost(FocusEvent e) {
				//Adds the label back to the username field if it is empty and clicked off of
				if (nameField.getText().equals("")) {
					resetUsername();
				}
			}
	
			});      
		
		passField = new JPasswordField("Password",16);
		passField.setForeground(Color.GRAY);
		passField.setEchoChar((char)0);
		passField.setPreferredSize(new Dimension(0,50));
		passField.addFocusListener(new FocusListener() {

			//Removes the label from the password field when clicked on
			public void focusGained(FocusEvent e) {
				if (passField.getForeground().equals(Color.GRAY)) {
					passField.setEchoChar('•');
				    passField.setText("");
				    passField.setForeground(Color.BLACK);
				}
			}
	
			//Removes the label from the password field when clicked on
			public void focusLost(FocusEvent e) {
				if (passField.getText().equals("")) {
					resetPassword();
				}
				
			}
	
			});
		
		
		signUpButton = new GButton("  Sign Up  ",Color.CYAN,this);
		loginButton = new GButton("  Login  ",Color.GREEN,this);
		alertText = new JLabel("Welcome to quick maffs. Please login or sign up");
		alertText.setFont(GFonts.mediumFont);

		
		for (int i =0;i<4;i++) {
			centerPanel.add(blanks[i]);
		}
		centerPanel.add(nameField);
		centerPanel.add(eastPanel);
		centerPanel.add(passField);
		centerPanel.add(eastPanel);
		southPanel.add(signUpButton);
		southPanel.add(loginButton);
		topPanel.add(alertText);
		
		mainPanel.add(westPanel,BorderLayout.WEST);
		mainPanel.add(eastPanel,BorderLayout.EAST);

		mainPanel.add(southPanel,BorderLayout.SOUTH);
		mainPanel.add(topPanel,BorderLayout.PAGE_START);
		mainPanel.add(centerPanel,BorderLayout.CENTER);

		
	}
	
	/**
	 * 
	 * Generates a hashed string from a given char array in hexadecimal
	 * 
	 * @param arr: char array to be hashed
	 * @return The string containing the hash 
	 */
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
	
	/**
	 * 
	 * Checks the values in the 
	 * 
	 */
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
	@Override
	public void mouseEnter(MouseEvent e) {
		if (e.getSource()==signUpButton) {
			signUpButton.changeBrightness(true);
		}
		if (e.getSource()==loginButton) {
			loginButton.changeBrightness(true);
		}
	}
	@Override
	public void mouseExit(MouseEvent e) {
		if (e.getSource()==signUpButton) {
			signUpButton.changeBrightness(false);
		}
		if (e.getSource()==loginButton) {
			loginButton.changeBrightness(false);
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
