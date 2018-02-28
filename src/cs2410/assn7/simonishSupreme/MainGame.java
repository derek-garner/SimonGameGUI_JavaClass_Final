package cs2410.assn7.simonishSupreme;

import java.awt.BorderLayout;
import javax.swing.SwingWorker;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JColorChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import cs2410.assn7.components.ColorPanel;
import cs2410.assn7.components.HistoryAndHighScore;
import cs2410.assn7.components.MenuBar;
import cs2410.assn7.components.ScorePanel;
/**
 * @author Derek Garner
 * @brief Simonish game improved with features which 
 * allow for gameplay changes such as more tiles and reverse order.
 * Also added is things such as scoreBoard, sounds,images
 */
public class MainGame implements MouseListener, ActionListener {
	/**
	 * max game board size used to initialize all panels 
	 * regardless of current size
	 */
	private int maxGameBoardSize=16;
	
	/**
	 * current game board size
	 */
	private int gameBoardSize=2;
	
	/**
	 * stores number of plays after reading from file
	 */
	private int numberOfPlays;
	
	/**
	 * stores current highScore
	 */
	private int highScore;
	
	/**
	 * allows change of speed
	 */
	private int sleepLength=300;
	
	/**
	 * enables or disables sound
	 */
	private boolean sound=true;
	
	/**
	 * enables or disables images
	 */
	private boolean images=false;
	
	/**
	 * speed setting slow
	 */
	private boolean slow=true;
	
	/**
	 * speed setting medium
	 */
	private boolean medium=false;
	
	/**
	 * speed setting fast
	 */
	private boolean fast=false;
	
	/**
	 * speed setting adaptive
	 */
	private boolean adaptive=false;
	
	/**
	 * mode setting for scramble
	 */
	private boolean scramble=false;
	
	/**
	 * mode setting for reverse
	 */
	private boolean reverse=false;
	
	/**
	 * double used for average score
	 */
	private double average;
	
	protected static final Component colorPanel = null;
	
	/**
	 * Main frame
	 */
	private JFrame frame = new JFrame("Simonish");
	
	/**
	 * list of color for initalize colors
	 */
	private Color[] colors = {Color.RED, Color.BLUE, Color.GREEN, Color.YELLOW,Color.PINK,
			Color.CYAN,Color.gray,Color.magenta,Color.orange,new Color(145,50,25),new Color(20,150,25),new Color(10,50,180),new Color(180,180,150)
			,new Color(0,0,180),new Color(180,0,0),new Color(145,50,25),new Color(180,180,0),new Color(145,50,25),new Color(110,0,60),new Color(145,50,25)
			,new Color(145,50,25)};
	
	/**
	 * Color panel objects for game buttons
	 */
	private ColorPanel[] panels = new ColorPanel[maxGameBoardSize];
	
	/**
	 * Score panel object for top panel
	 */
	private ScorePanel scorePanel;
	
	/**
	 * stores computer sequence of colorpanels
	 */
	private ArrayList<ColorPanel> compSeq = new ArrayList<ColorPanel>();
	
	/**
	 * random variable use for computer selection of panels
	 */
	private Random rand = new Random();
	
	/**
	 * iterator to move through color panel sequences
	 */
	private Iterator<ColorPanel> iter;
	
	/**
	 * bool determine if its players turn
	 */
	boolean playerTurn = false;
	
	/**
	 * Action listener for colorListener
	 */
	private ActionListener colorListener;
	
	/**
	 * menu bar which holds all user settings
	 */
	private MenuBar menuBar=new MenuBar(gameBoardSize*gameBoardSize,
			sound,images,slow,medium,fast,adaptive,scramble,reverse);
	
	/**
	 * listener for pressing color pieces
	 */
	private MouseListener colorPieces=this ;
	
	/**
	 * listener to allow user to change size of board
	 */
	private ActionListener listenSize;
	
	/**
	 * main content pane
	 */
	private JPanel pane = (JPanel)frame.getContentPane();
	
	/**
	 * panel containing all color panels
	 */
	private JPanel gameArea = new JPanel();
	
	/**
	 * initalize history and highscore object for later reading
	 */
	private HistoryAndHighScore records= new HistoryAndHighScore();
	
	/**
	 * array of high scores
	 */
	private int highScores[]=new int[10];
	
	/**
	 * array of names of highscore holders
	 */
	private String[] namesHighScores=new String[10];
	
	/**
	 * listener for sound and images
	 */
	private ActionListener enableOrDisable;
	
	/**
	 * picture for unpressed panel 
	 */
    private JLabel[] unPressedImg=new JLabel[maxGameBoardSize];
    

	/**
	 * picture for pressed panel 
	 */
    private JLabel PressedImg=new JLabel();
    
    /**
     * note image
     */
    private ImageIcon note1;
    
    /**
     * Exclamation mark image
     */
    private ImageIcon exclaim;
    
    /**
     * listener to allow reverse mode
     */
	private ActionListener reverseListener;
	
	/**
	 * listener to allow scramble mode
	 */
	private ActionListener scrambleListener;
	
	/**
	 * listener to allow crazy mode
	 */
	private ActionListener crazyListener;
	
	/**
	 * listener to allow speed changes
	 */
	private ActionListener speedListener;
	
	/**
	 * lister to open stats options
	 */
	private ActionListener statsListener;

	/**
	 * listener for classic mode
	 */
	private ActionListener classicListener;
	
	/**
	 * Main game constructor which initializes the game board,
	 * set item listeners,sets default settings, and gathers
	 * initial values from files for history and highscores
	 */
	public MainGame() {
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		/**
		 * Initialize image objects for use if desired
		 */
		exclaim=new ImageIcon("data/190564-200.png");
		note1=new ImageIcon("data/ftmusic_sixteenthnote_Clipart_Free.png");
		PressedImg=new JLabel(new ImageIcon(exclaim.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH)));
		for(int i=0;i<maxGameBoardSize;i++){
			unPressedImg[i]=new JLabel(new ImageIcon(note1.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH)));
		}

		/**
		 * Gather data from files and store them in class variables 
		 * such as history and highScores
		 */
		for(int i=0;i<10;i++){highScores[i]=records.getHighScore(i);}
		for(int i=0;i<10;i++){namesHighScores[i]=records.getName(i);}
		highScore= highScores[0];
		numberOfPlays=records.getNumberOfPlays();
		average=records.getAverage();
		
		/**
		 * Initialize gameArea
		 */
		frame.setJMenuBar(menuBar);
		pane.setLayout(new BorderLayout());
		gameArea.setLayout(new GridLayout(gameBoardSize, gameBoardSize));
		gameArea.setPreferredSize(new Dimension(400, 400));
		for (int i = 0; i < panels.length; i++) {
			panels[i] = new ColorPanel(colors[i],43+(i*2),sound);
			panels[i].addMouseListener(this);
		}
		for (int i = 0; i < gameBoardSize*gameBoardSize; i++) {
			gameArea.add(panels[i]);
		}
		scorePanel=new ScorePanel(highScore);
		scorePanel.addStartListener(this);
		pane.add(gameArea);		
		pane.add(scorePanel, BorderLayout.NORTH);

		
		/**
		 * Use function to set all listeners and set frame visable
		 */
		initAllListeners();
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}
	
	/**
	 * reset() function used to reset in case of 
	 * game start and game over
	 */
	private void reset() {
		scorePanel.disableStart();
		
		for (int i=0;i<gameBoardSize*gameBoardSize;i++) {
			panels[i].reset();
		}
		if(images){addImages();}
		scorePanel.resetScore();
		compSeq.clear();
	}
	
	/**
	 * compTurn handles computer turn
	 */
	private void compTurn() {		
		playerTurn = false;
		/**
		 * SwingWorker used to handle errors cause 
		 * by user input during computer turn
		 */
		new SwingWorker<Void,Void>(){
			private ArrayList reverseSeq;
			@Override
			protected Void doInBackground() throws Exception {
				try {
					Thread.sleep(500);
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
				
				compSeq.add(panels[rand.nextInt(gameBoardSize*gameBoardSize)]);
				
				iter = compSeq.iterator();
				
				while(iter.hasNext()) {
					ColorPanel tmp = iter.next();
					tmp.pressed();
					
					

					try {
						Thread.sleep(sleepLength);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					
					tmp.released();
					
					try {
						Thread.sleep(sleepLength);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				
				/**
				 * Check for reverse sequence to set
				 * new iterator in reverse
				 */
				if(!reverse){iter = compSeq.iterator();}
				/**
				 * reverse function used to create reverse
				 * iterator
				 */
				else{reverseSeq= reverse(compSeq);
					iter=reverseSeq.iterator();
				}
				if(scramble){scrambleIt();}
				if(images){addImages();}
				
				return null;
			}
			@Override
			protected void done(){
				playerTurn = true;
			}
			}.execute();
		
		//pause briefly between rounds
	}
	
	/**
	 * 
	 * @param orig arraylist to be returned in reverse
	 * @return orig in reverse order
	 */
	public ArrayList reverse( ArrayList orig )
	{
		ArrayList reversed = new ArrayList() ;
		for ( int i = orig.size() - 1 ; i >= 0 ; i-- )
		{
			Object obj = orig.get( i ) ;
			reversed.add( obj ) ;
		}
		return reversed ;
	}
	
	/**
	 * 
	 * @param panel checks if panel is correct selection in sequnce
	 * @return true if correct selection
	 */
	private boolean isCorrectClick(ColorPanel panel) {
		
		return iter.next() == panel;	
	}
	
	/**
	 * If round is won increase score and adjust speed if 
	 * adaptive speed is selected
	 */
	private void roundWon() {
		if(adaptive){
			if(sleepLength>100)sleepLength=sleepLength-50;
		}
		scorePanel.incrScore();
	}

	/**
	 * Operations for incorrect selection in which game ends
	 */
	private void gameOver() {
		String initials=null;
		/**
		 * Readd images to fix cases where images stayed 
		 * after incorrect press
		 */
		if(images){addImages();}
		
		playerTurn = false;
		numberOfPlays++;
		average=average+scorePanel.getScore();
		average=(double)average/numberOfPlays;
		
		/**
		 * End score output block
		 */
		String msg = "Game Over\n" + "Your Score was " + scorePanel.getScore()+"\n";
		
		/**
		 * Check for new highscore and rewrite files if necessary 
		 */
		if (scorePanel.getScore()>highScores[9]) {
			msg = msg + "\nCongratulations! You set a new high score!\n";
			JOptionPane enterName=new JOptionPane();
		
			initials= enterName.showInputDialog(frame,"Enter three initials","-");
			int index=0;
			while (scorePanel.getScore()<=highScores[index]){
				index++;
				System.out.println(index);
			}
			if(initials!="-"||initials!=null){
			for(int j=9;j>index;j--){
				highScores[j]=highScores[j-1];
				namesHighScores[j]=namesHighScores[j-1];
			}
			highScores[index]=scorePanel.getScore();
			namesHighScores[index]=initials;
			records.writeScores(highScores,namesHighScores);
			records.writeHistory(numberOfPlays,average);
			}
		}
		
		for(int i=0;i<10;i++){
			msg=msg+namesHighScores[i];
			msg=msg+" ";
			msg=msg+highScores[i]+"\n";
		}
		JOptionPane.showMessageDialog(frame, msg);
		
		this.reset();
		enableMenu();
		scorePanel.enableStart();
	}

	/**
	 * Main method utilizes swingUtilities
	 * @param args
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable(){

			@Override
			public void run() {
				new MainGame();				
			}});
	}

	/**
	 * Do nothing
	 */
	@Override
	public void mouseClicked(MouseEvent e) {}

	/**
	 * Handle panel clicks including checks for
	 * correct selection
	 */
	@Override
	public void mousePressed(MouseEvent e) {
		if (!playerTurn) return;
		
		ColorPanel tmp = (ColorPanel)e.getSource();
		tmp.pressed();
		if (!isCorrectClick(tmp)) {
			this.gameOver();
		}
		
		/**
		 * additional commands to handle images
		 */
		if(images){
			tmp.removeAll();
			tmp.add(PressedImg);
			pane.add(gameArea);
			frame.pack();
		}	
	}

	/**
	 * Handle mouse release by redimming panels
	 * and checking if end of sequence to change
	 * turn
	 */
	@Override
	public void mouseReleased(MouseEvent e) {
		if (!playerTurn) return;
		if(images){
		ColorPanel tmp = (ColorPanel)e.getSource();
		int index=0;
		for(int i=0;i<(maxGameBoardSize);i++){
			if(tmp==panels[i]){
				index=i;
				break;
			}
		}
		tmp.removeAll();
		tmp.add(unPressedImg[index]);
		pane.add(gameArea);
		pane.repaint();
		frame.pack();
		}
		
		((ColorPanel)e.getSource()).released();
		if (!iter.hasNext()) {
			this.roundWon();
			this.compTurn();
		}	
	}

	/**
	 * Do Nothing
	 */
	@Override
	public void mouseEntered(MouseEvent e) {}

	/**
	 * Do Nothing
	 */
	@Override
	public void mouseExited(MouseEvent e) {}

	/**
	 * Handle start button operations
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		
		this.reset();	
		this.compTurn();	
		disableMenu();
		if(images){addImages();}
	}	
	
	/**
	 * Initializes size listener which 
	 * allows user to change the size 
	 * of the game board
	 */
	public void initSize(){
		listenSize=new ActionListener(){
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(e.getSource()==menuBar.s2x2){gameBoardSize=2;}
				if(e.getSource()==menuBar.s3x3){gameBoardSize=3;}
				if(e.getSource()==menuBar.s4x4){gameBoardSize=4;}	
				pane.remove(gameArea);
				pane.setLayout(new BorderLayout());
				gameArea.removeAll();
				
				gameArea.setLayout(new GridLayout(gameBoardSize, gameBoardSize));
				gameArea.setPreferredSize(new Dimension(400, 400));
				
				menuBar=new MenuBar(gameBoardSize*gameBoardSize,
						sound,images,slow,medium,fast,adaptive,scramble,reverse);
				frame.setJMenuBar(menuBar);
				
				for (int i = 0; i < gameBoardSize*gameBoardSize; i++) {
					gameArea.add(panels[i]);
				}
				
				initAllListeners();
				
				scorePanel.addStartListener(this);
				pane.add(gameArea);		
				pane.add(scorePanel, BorderLayout.NORTH);
				
				frame.pack();

				
				}
		};
	}
	
	/**
	 * Initializes color listener allowing user
	 * to change colors of individual panels
	 */
	public void initColorListener(){
		colorListener=new ActionListener(){
			
			@Override
			public void actionPerformed(ActionEvent e) {
				int currentPanel=0;
								
				for(int i=0;i<gameBoardSize*gameBoardSize;i++){
					if(e.getSource()==menuBar.panelsColor[i]){
					 currentPanel=i;
					}
				}
				Color currentPanelColor=new Color(currentPanel);
				currentPanelColor=panels[currentPanel].getColor();
				colors[currentPanel]=(JColorChooser.showDialog(frame,"Choose a Color",currentPanelColor));
			
				gameArea.removeAll();

				for (int i = 0; i < gameBoardSize*gameBoardSize; i++) {
					panels[i] = new ColorPanel(colors[i],43+(i*2),sound);
					panels[i].addMouseListener(colorPieces);
					gameArea.add(panels[i]);
				}
				if(images){addImages();}
				pane.add(gameArea);
				frame.pack();
				}	
		};
	}

	/**
	 * Initializes speed listener allowing user
	 * to change the speed of game using menu
	 */
	public void initSpeedListener(){
		speedListener=new ActionListener(){
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(e.getSource()==menuBar.slow){
					slow=true;
					sleepLength=1000;
				}
				if(e.getSource()==menuBar.medium){
					medium=true;
					sleepLength=500;
				}
				if(e.getSource()==menuBar.fast){
					fast=true;
					sleepLength=100;
				}
				if(e.getSource()==menuBar.adaptive){
					adaptive=true;
					sleepLength=500;
				}
				}	
		};
	}
	
	/**
	 * Initializes sound and image listeners 
	 * to allow user to enable or disable 
	 * sound and images
	 */
	public void initEnableOrDisable(){
		enableOrDisable=new ActionListener(){
			
			@Override
			public void actionPerformed(ActionEvent e) {
			if(e.getSource()==menuBar.enableSound){
				sound=true;	
				gameArea.removeAll();

				for (int i = 0; i < gameBoardSize*gameBoardSize; i++) {
					panels[i] = new ColorPanel(colors[i],43+(i*2),sound);
					panels[i].addMouseListener(colorPieces);
					gameArea.add(panels[i]);
				}
				pane.add(gameArea);
				frame.pack();
			}
			if(e.getSource()==menuBar.disableSound){
				sound=false;
				gameArea.removeAll();

				for (int i = 0; i < gameBoardSize*gameBoardSize; i++) {
					panels[i] = new ColorPanel(colors[i],43+(i*2),sound);
					panels[i].addMouseListener(colorPieces);
					gameArea.add(panels[i]);
				}
				pane.add(gameArea);
				frame.pack();
			}
			if(e.getSource()==menuBar.enableImages){
				images=true;
								
				addImages();		
			}	
			
			if(e.getSource()==menuBar.disableImages){
				images=false;
							
				removeImages();
			}	
			}	
		};
	}
	
	/**
	 * Initializes listener for reverse sequence 
	 * setting by changing bools reverse and scramble
	 */
	public void initReverse(){
		reverseListener=new ActionListener(){		
			@Override
			public void actionPerformed(ActionEvent e) {
			reverse=true;
			scramble=false;
			}	
		};
	}
	
	/**
	 * Initialize listener for scramble setting
	 * by simply changing bools reverse and scramble
	 */
	public void initScramble(){
		 scrambleListener = new ActionListener(){		
			@Override
			public void actionPerformed(ActionEvent e) {
			reverse=false;
			scramble=true;
			
			}	
		};
	}
	/**
	 * initializes listener for crazy setting
	 */
	public void initCrazy(){
		crazyListener=new ActionListener(){
			
			@Override
			public void actionPerformed(ActionEvent e) {
			reverse=true;
			scramble=true;
					
			}	
		};
	}
	/**
	 * Initialize classic listener
	 */
	public void initClassic(){
		classicListener=new ActionListener(){
			
			@Override
			public void actionPerformed(ActionEvent e) {
			reverse=false;
			scramble=false;
					
			}	
		};
	}
	
	/**
	 * Initializes listener for all stats objects
	 * including history, highscores and reset
	 */
	public void initStatsListener(){
		statsListener=new ActionListener(){
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if(e.getSource()==menuBar.history){
				JOptionPane.showMessageDialog(null,"Number of plays:"+numberOfPlays
					+"\n Average Score: "+average);
				}
				if(e.getSource()==menuBar.highScores){
					String msg="HighScores\n";
					for(int i=0;i<10;i++){
						
						msg=msg+namesHighScores[i];
						msg=msg+" ";
						msg=msg+highScores[i]+"\n";
					}
					JOptionPane.showMessageDialog(frame, msg);
				}
				if(e.getSource()==menuBar.reset){
					JOptionPane.showMessageDialog(null,"History and scores reset");
					average=0;
					numberOfPlays=0;
					for(int i=0;i<10;i++){highScores[i]=0;}
					for(int i=0;i<10;i++){namesHighScores[i]=("-");}
					records.writeScores(highScores,namesHighScores);
					records.writeHistory(numberOfPlays,average);
				}
			}	
		};
	}
	/**
	 * Method used to scramble board after computer turn
	 * used when bool scramble is true
	 */
	private void scrambleIt(){
		
		pane.remove(gameArea);
		pane.setLayout(new BorderLayout());
		gameArea.removeAll();
		
		gameArea.setLayout(new GridLayout(gameBoardSize, gameBoardSize));
		gameArea.setPreferredSize(new Dimension(400, 400));

		Collections.shuffle(Arrays.asList(panels).subList(0, gameBoardSize*gameBoardSize));
		
		for (int i = 0; i < gameBoardSize*gameBoardSize; i++) {
			gameArea.add(panels[i]);
		}
		
		pane.add(gameArea);		
		pane.add(scorePanel, BorderLayout.NORTH);	
		if(images){addImages();}
		pane.repaint();
		frame.pack();
	}
	
	/**
	 * Adds images to game board
	 * used when images bool is true
	 */
	private void addImages(){
		for (int i = 0; i < maxGameBoardSize; i++) {
			gameArea.add(panels[i]);
			panels[i].add(unPressedImg[i]);
		}				
		gameArea.removeAll();

		for (int i = 0; i < gameBoardSize*gameBoardSize; i++) {
			gameArea.add(panels[i]);
			panels[i].add(unPressedImg[i]);
		}
		pane.add(gameArea);
		pane.repaint();
		frame.pack();	
		
	}
	
	/**
	 * Removes images from game board
	 * upon user selection to disable images
	 */
	private void removeImages(){
		pane.remove(gameArea);
		pane.setLayout(new BorderLayout());
		gameArea.removeAll();
		pane.revalidate();
		gameArea.setLayout(new GridLayout(gameBoardSize, gameBoardSize));
		gameArea.setPreferredSize(new Dimension(400, 400));

		for (int i = 0; i < maxGameBoardSize; i++) {
			panels[i].removeAll();	
		}
		for(int i=0;i<gameBoardSize*gameBoardSize;i++){
			gameArea.add(panels[i]);
		}
		
		scorePanel=new ScorePanel(highScore);
		scorePanel.addStartListener(this);
		pane.add(gameArea);		
		
		pane.add(scorePanel, BorderLayout.NORTH);
		pane.revalidate();
		pane.repaint();
		frame.pack();
	}
	
	/**
	 * disables menu item while game running
	 */
	private void disableMenu(){
		menuBar.s2x2.setEnabled(false);
		menuBar.s3x3.setEnabled(false);
		menuBar.s4x4.setEnabled(false);
		menuBar.scramble.setEnabled(false);
		menuBar.reverse.setEnabled(false);
		menuBar.crazy.setEnabled(false);
		menuBar.disableSound.setEnabled(false);
		menuBar.enableSound.setEnabled(false);
		menuBar.enableImages.setEnabled(false);
		menuBar.disableImages.setEnabled(false);
		for(int i=0;i<gameBoardSize*gameBoardSize;i++){
			menuBar.panelsColor[i].setEnabled(false);
		}

	}
	
	/**
	 * enable menu items upon game end
	 */
	private void enableMenu(){
		menuBar.s2x2.setEnabled(true);
		menuBar.s3x3.setEnabled(true);
		menuBar.s4x4.setEnabled(true);
		menuBar.scramble.setEnabled(true);
		menuBar.reverse.setEnabled(true);
		menuBar.crazy.setEnabled(true);
		menuBar.disableSound.setEnabled(true);
		menuBar.enableSound.setEnabled(true);
		menuBar.enableImages.setEnabled(true);
		menuBar.disableImages.setEnabled(true);
		for(int i=0;i<gameBoardSize*gameBoardSize;i++){
			menuBar.panelsColor[i].setEnabled(true);
		}
	}
	
	/**
	 * initialize all listeners in method
	 */
	private void initAllListeners(){
		initSize();
		menuBar.s2x2.addActionListener(listenSize);
		menuBar.s3x3.addActionListener(listenSize);
		menuBar.s4x4.addActionListener(listenSize);	
		initEnableOrDisable();
		menuBar.disableSound.addActionListener(enableOrDisable);
		menuBar.enableSound.addActionListener(enableOrDisable);
		menuBar.enableImages.addActionListener(enableOrDisable);
		menuBar.disableImages.addActionListener(enableOrDisable);
		initColorListener();
		for(int i=0;i<gameBoardSize*gameBoardSize;i++){
			menuBar.panelsColor[i].addActionListener(colorListener);
		}
		initReverse();
		menuBar.reverse.addActionListener(reverseListener);
		initScramble();
		menuBar.scramble.addActionListener(scrambleListener);
		initCrazy();
		menuBar.crazy.addActionListener(crazyListener);
		initClassic();
		
		menuBar.classic.addActionListener(classicListener);
		initSpeedListener();
		menuBar.slow.addActionListener(speedListener);
		menuBar.medium.addActionListener(speedListener);
		menuBar.fast.addActionListener(speedListener);
		menuBar.adaptive.addActionListener(speedListener);
		initStatsListener();
		menuBar.history.addActionListener(statsListener);
		menuBar.reset.addActionListener(statsListener);
		menuBar.highScores.addActionListener(statsListener);
		
	}

}

