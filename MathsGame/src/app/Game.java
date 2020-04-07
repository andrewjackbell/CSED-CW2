package app;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileWriter;
import java.io.PrintWriter;

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
	
	
	
	public void playGame(int difficulty, String user) {
			
			score=0;
			//long time = System.currentTimeMillis();
	
			generateQuestion();
			
		
	}
	
	public void checkAnswer() {
		if (answerField.getText().equals(currentAnswer)) {
			questionField.setText("correct answer");
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
		String question = rand1+operator+rand2+"=?";
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
	
	

}
