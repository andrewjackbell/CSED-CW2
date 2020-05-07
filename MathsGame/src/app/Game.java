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
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Game extends Window {
	private int difficulty;
	private String user;
	private int[][][] ranges = {{{0,50},{0,35}},{{-50,100},{0,60},{0,12}},{{-50,100},{-12,20},{0,100}}};
	private char[][] operators= {{'+','-'},{'+','-','*'},{'-','*','/'}};
	private JLabel questionField;
	private JTextField answerField;
	private String currentAnswer;
	private JPanel centerPanel;
	private JPanel leftPanel;
	private JPanel rightPanel;
	private JPanel southPanel;
	private JPanel northPanel;
	private int correctAns;
	private int incorrectAns;
	private boolean nextQuestion;
	private JLabel timer;
	private JPanel topPanel;
	
	public Game(WindowManager manager,Dimension frameSize) {
		super(manager,frameSize);
		Dimension d = new Dimension((int)frameSize.getWidth()/3,(int)frameSize.getHeight()/2);
		Dimension d1 = new Dimension((int)frameSize.getWidth()/3,(int)frameSize.getHeight()/3);
		southPanel= new JPanel(); southPanel.setPreferredSize(d);
		leftPanel = new JPanel(); leftPanel.setPreferredSize(d);
		rightPanel= new JPanel(); rightPanel.setPreferredSize(d);
		centerPanel = new JPanel(); centerPanel.setLayout(new BoxLayout(centerPanel,BoxLayout.Y_AXIS));
		topPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT)); topPanel.setPreferredSize(d1);
		timer= new JLabel("60"); timer.setFont(GFonts.titleFont); 
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
		topPanel.add(timer);
		
		mainPanel.add(centerPanel,BorderLayout.CENTER);
		mainPanel.add(leftPanel,BorderLayout.WEST);
		mainPanel.add(rightPanel,BorderLayout.EAST);
		mainPanel.add(southPanel,BorderLayout.SOUTH);
		mainPanel.add(topPanel,BorderLayout.PAGE_START);
	
	}
	
	public void playGame(int difficulty, String user) {
			this.difficulty=difficulty;
			this.user=user;
			correctAns=0;
			incorrectAns=0;
			nextQuestion=true;
			
			Thread game = new Thread() {
		    public void run() {
				long time = System.currentTimeMillis();
				int seconds = 0;
				while (seconds<60) {
					if (System.currentTimeMillis()>time+seconds*1000+1) {
						seconds++;
						decrementTimer();
					}
					synchronized(this) {
						if (nextQuestion==true) {
							nextQuestion=false;
							answerField.setText("");
							generateQuestion();
						}
					}
				}
				int score = correctAns-incorrectAns;
				writeScore(score);
				manager.setScoreValues(correctAns,incorrectAns);
				manager.changeState("summary");
		    }  
		};
		game.start();
		

	}
	
	public void decrementTimer() {
		int time = Integer.valueOf(timer.getText());
		time--;
		timer.setText(String.valueOf(time));
	}
	
	public void checkAnswer() {
		if (answerField.getText().equals(currentAnswer)) {
			correctAns++;
			
		}else {
			incorrectAns++;
		}
		synchronized(this) {
			nextQuestion=true;
		}
	}
	
	private void generateQuestion() {
		int min=0;
		int max=0;
		int operand1=0;
		int operand2=0;
		int operatorint=0;
		int answer=0;
		operatorint = (int) (Math.random()*operators[difficulty].length);
		char operator = operators[difficulty][operatorint];
		min = ranges[difficulty][operatorint][0];
		max = ranges[difficulty][operatorint][1];
		while (answer==0) {
			operand1 = randInt(min,max);
			operand2 = randInt(min,max);
			answer = operate(operand1,operand2,operator);
		}
		System.out.println(answer);
		String question = operand1+String.valueOf(operator)+operand2+" = ?";
		currentAnswer=String.valueOf(answer);
		questionField.setText(question);
	}
	
	private int operate(int rand1, int rand2, char operator) {
		if (operator=='+') {
			return rand1+rand2;
		}
		else if (operator=='-') {
			return rand1-rand2;
		}
		else if (operator=='*') {
			return rand1*rand2;
		}
		else {
			if (rand2!=0) {
				if (rand1%rand2==0) {
					return rand1/rand2;
				}else {
					return 0;
				}
			
			}else {
				return 0;
			}
		}
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
	private int randInt(int min, int max) {
		return (int) (Math.random()*(max-min))+min;
	}
	

}
