package app;

import java.awt.BorderLayout;
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

import javax.swing.JLabel;
import javax.swing.JTextField;

public class Game extends Window {
	private int difficulty;
	private String user;
	private String[] operators = {"+","-","/","*"};
	private JLabel questionField;
	private JTextField answerField;
	private String currentAnswer;
	private JLabel cheatField;
	int score;
	boolean nextQuestion;
	
	public Game(WindowManager manager) {
		super(manager);
		this.mainPanel.setLayout(new FlowLayout());
		questionField = new JLabel("question here");
		cheatField = new JLabel();
		answerField = new JTextField(16);
		answerField.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				checkAnswer();
				
			}
		});
		mainPanel.add(cheatField,BorderLayout.CENTER);
		mainPanel.add(questionField,BorderLayout.CENTER);
		mainPanel.add(answerField,BorderLayout.CENTER);
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
			questionField.setText("correct answer");
			score++;
			synchronized(this) {
				nextQuestion=true;
			}
		}
	}
	private void generateQuestion() {
		int rand1 = (int) (Math.random()*100);
		int rand2 = (int) (Math.random()*100);
		int i = (int) (Math.random()*4);
		String operator = operators[i];
		String answer = String.valueOf(operate(rand1,rand2,operator));
		String question = rand1+operator+rand2+" = ?";
		questionField.setText(question);
		currentAnswer=answer;
		cheatField.setText("answer is "+ currentAnswer);
		
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
