package app;

import java.awt.BorderLayout;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

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
		try {
			if (nameField.getText().equals("andy")){
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

	}
	@Override
	public void mousePress(MouseEvent e) {
		if (e.getSource()==submitButton) {
			authenticate();
		}
	}
		
}
