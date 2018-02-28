package cs2410.assn7.components;

import java.io.File;
import java.io.PrintWriter;
import java.util.Scanner;

/**
 * 
 * @author Derek Garner
 * @brief Class used to handle files for
 * storing highScores, number of plays and average
 * score
 *
 */
public class HistoryAndHighScore {

	/**
	 * High scores array after read for file
	 */
	private int highScores[]=new int[10];
	
	/**
	 * string array used to store list of names
	 */
	private String[] names=new String[10];
	
	/**
	 * used to store the total number of plays
	 */
	private int numberOfPlays;
	
	/**
	 * double used to score the average score
	 */
	private double average;
	
	/**
	 * scanner use to read files
	 */
	private Scanner scanner;
	
	/**
	 * printWriter to write to scores file
	 */
	private  PrintWriter scores;
	
	/**
	 * printWriter to write to history file
	 */
	private PrintWriter history;
	
	/**
	 * main constructor which reads both files upon opening
	 */
	public HistoryAndHighScore(){
		openHistory();
		readHistory();
		openScores();
		readScores();
	
		
		
		
	}
	/**
	 * open history file which stores number
	 * of plays and average score
	 */
	public void openHistory(){
		try{
			scanner= new Scanner(new File("data/History"));
		}
		catch(Exception e){
			System.out.println("File not found");
		}
		
	}
	
	/**
	 * Read history file
	 */
	public void readHistory(){
		while(scanner.hasNext()){
			String a=scanner.next();
			numberOfPlays=Integer.parseInt(a);
			System.out.printf("%d", numberOfPlays);
			String b=scanner.next();
			average=Float.parseFloat(b);
			System.out.printf("%f", average);
		}
		scanner.close();
		
	}
	
	/**
	 * open scores file which saves
	 * high score board data
	 */
	public void openScores(){
		try{
			scanner= new Scanner(new File("data/Scores"));
		}
		catch(Exception e){
			System.out.println("File not found");
		}
	}
	
	
	/**
	 * read scores file
	 */
	public void readScores(){
			scanner.nextLine();
			for(int i=0;i<10;i++){
			String a=scanner.next();
			names[i]=a;
			System.out.printf("%s",names[i]);
			String b=scanner.next();
			highScores[i]=Integer.parseInt(b);
			System.out.printf("%d", highScores[i]);
			}
			scanner.close();
		
	}
	
	public int getNumberOfPlays(){
		return numberOfPlays;
	}
	
	public String getName(int position){
		return names[position];
	}
	
	public int getHighScore(int position){
		return highScores[position];
		
	}
	public double getAverage(){
		return average;
	}
	
	/**
	 * Write new scores file utilizing two arrays 
	 * @param highScoresNew list of scores
	 * @param namesNew list of names
	 */
	public void writeScores(int[] highScoresNew,String namesNew[]){
		try{
			scores=new PrintWriter("data/Scores");
		}
		catch(Exception e){
			System.out.println("File not found");
		}
		
		scores.printf("%s\n","Name Score" );
		for(int i=0;i<10;i++){
			
			scores.printf("%s %d\n",namesNew[i],highScoresNew[i] );

		}
		scores.close();
	}
	
	/**
	 * Write new history file using two arrays
	 * @param historyNew number of plays
	 * @param average average 
	 */
	public void writeHistory(int historyNew,double average){
		try{
			history=new PrintWriter("data/History");
		}
		catch(Exception e){
			System.out.println("File not found");
		}
		
		
		history.printf("%d\n",historyNew);
		history.printf("%f",average);
		history.close();
	}
	
	
}
