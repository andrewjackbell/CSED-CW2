package app;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.RandomAccessFile;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Game extends Window {
	private int difficulty;
	private String user;
	private String[] operators = {"+","-","/","*"};
	private JLabel questionField;
	private JTextField answerField;
	private String currentAnswer;
	private JPanel centerPanel;
	private JPanel leftPanel;
	private JPanel rightPanel;
	private JPanel southPanel;

	private JPanel northPanel;

	int score;
	boolean nextQuestion;
	
	public Game(WindowManager manager) {
		super(manager);
		//this.mainPanel.setLayout(n);
		Dimension d = new Dimension(200,500);
		Dimension d1 = new Dimension(200,300);
		southPanel= new JPanel(); southPanel.setPreferredSize(d);
		leftPanel = new JPanel(); leftPanel.setPreferredSize(d);
		rightPanel= new JPanel(); rightPanel.setPreferredSize(d);
		northPanel= new JPanel(); northPanel.setPreferredSize(d1);
		
		
		centerPanel = new JPanel(); centerPanel.setLayout(new BoxLayout(centerPanel,BoxLayout.Y_AXIS));
		questionField = new JLabel("question here");
		answerField = new JTextField(16);
		answerField.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				checkAnswer();
				
			}
		});
		
		centerPanel.add(questionField);
		centerPanel.add(answerField);
		
		mainPanel.add(centerPanel,BorderLayout.CENTER);
		mainPanel.add(leftPanel,BorderLayout.WEST);
		mainPanel.add(rightPanel,BorderLayout.EAST);
		mainPanel.add(southPanel,BorderLayout.SOUTH);
		mainPanel.add(northPanel,BorderLayout.NORTH);
	
	}
	
	public void playGame() {
			score=0;
			nextQuestion=true;
			Thread game = new Thread() {
		    public void run() {
				long time = System.currentTimeMillis();
				while (System.currentTimeMillis()<time+10000) {
					synchronized(this) {
						if (nextQuestion==true) {
							nextQuestion=false;
							generateQuestion();
						}
					}
				}
				writeScore(score);
				manager.setScore(score);
				manager.changeState("menu");
		    }  
		};
		game.start();
		

	}
	
	public void checkAnswer() {
		if (answerField.getText().equals(currentAnswer)) {
			answerField.setText("");
			score++;
			synchronized(this) {
				nextQuestion=true;
			}
		}
	}
	private void generateQuestion() {
		int multiplier;
		if (difficulty==0) {
			multiplier=12;
		}
		else if (difficulty==1) {
			multiplier=24;
		}
		else {
			multiplier=48;
		}
		
		int rand1 = (int) (Math.random()*multiplier);
		int rand2 = (int) (Math.random()*multiplier);
		int i = (int) (Math.random()*4);
		String operator = operators[i];
		String answer = String.valueOf(operate(rand1,rand2,operator));
		String question = rand1+operator+rand2+" = ?";
		questionField.setText(question);
		currentAnswer=answer;
		
	}
	private int operate(int rand1, int rand2, String operator) {
		if (operator.equals("+")) {
			return rand1+rand2;
		}
		else if (operator.equals("-")) {
			return rand1-rand2;
		}
		else if (operator.equals("*")) {
			return rand1*rand2;
		}
		else {
			return rand1/rand2;
		}
	}
	public void setUser(String user) {
		this.user=user;
	}
	public void setDifficulty(int difficulty) {
		this.difficulty=difficulty;
	}
	
	private void writeScore(int score) {
		try {
			File file = new File("resources/data/"+user+".txt");
			BufferedReader br = new BufferedReader(new FileReader(file));
			String[] lines=new String[4];
			for (int i =0;i<4;i++) {
				lines[i]=br.readLine();
			}
			br.close();
			lines[difficulty+1]=lines[difficulty+1]+score+",";
			PrintWriter pw = new PrintWriter(new FileWriter(file));
			for (int i =0;i<4;i++) {
				pw.println(lines[i]);
			}
			pw.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	

}
