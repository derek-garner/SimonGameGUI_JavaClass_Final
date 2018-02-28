package cs2410.assn7.components;

import java.awt.GridLayout;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;


@SuppressWarnings("serial")
/**
 * 
 * @author Derek Garner
 * @brief Score panel class is used to initialize 
 * the score panel at the top of game board
 */
public class ScorePanel extends JPanel{
	/**
	 * Current user score
	 */
	private int score = 0;
	
	/**
	 * highest score on highScores list
	 */
	private int highScore = 0;
	
	/**
	 * check if a new highscore is available
	 */
	private boolean newHighScore = false;
	
	/**
	 * label to display highScore
	 */
	private JLabel highScoreLabel = new JLabel();
	
	/**
	 * label for dispaly user score
	 */
	private JLabel scoreLabel = new JLabel();
	
	/**
	 * start button
	 */
	private JButton startBtn = new JButton("Start");
	
	
	/**
	 * Main scoreboard constructor
	 * @param highScore reads in top score to be displayed 
	 */
	public ScorePanel(int highScore) {
		
		this.highScore=highScore;
		this.setLayout(new GridLayout(1,3));
		highScoreLabel.setHorizontalAlignment(JLabel.CENTER);
		this.add(highScoreLabel);
		startBtn.setHorizontalAlignment(JLabel.CENTER);
		this.add(startBtn);
		scoreLabel.setHorizontalAlignment(JLabel.CENTER);
		this.add(scoreLabel);
		this.updateScoreView();
	}
	
	/**
	 * reset user score to 0
	 */
	public void resetScore() {
		score=0;
		updateScoreView();
		newHighScore = false;
	}
	
	/** 
	 * increase user score by one
	 */
	public void incrScore() {
		score++;
		if (score > highScore) {
			newHighScore = true;
			highScore = score;
		}
		updateScoreView();
	}
	
	/**
	 * 
	 * @return score
	 */
	public int getScore() {
		return score;
	}
	
	/**
	 * 
	 * @return newHighScore
	 */
	public boolean isNewHighScore() {
		return newHighScore;
	}
	
	/**
	 * Add new start button listener
	 * @param list
	 */
	public void addStartListener(ActionListener list) {
		startBtn.addActionListener(list);
	}
	
	
	/**
	 * disable start button
	 */
	public void disableStart() {
		startBtn.setEnabled(false);
	}
	
	/**
	 * enable start button
	 */
	public void enableStart() {
		startBtn.setEnabled(true);
	}
	
	/**
	 * update scores
	 */
	private void updateScoreView() {
		highScoreLabel.setText("High Score\n " + highScore);
		scoreLabel.setText("Score\n " + score);
		this.update(this.getGraphics());
	}
	
}
