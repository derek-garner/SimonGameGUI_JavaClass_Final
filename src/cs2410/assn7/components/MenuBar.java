package cs2410.assn7.components;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ButtonGroup;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JRadioButtonMenuItem;

@SuppressWarnings("serial")

/**
 * 
 * @author Derek Garner
 * @brief Menu bar object which hold all of 
 * the user setting and allows user to view high
 * scores and history
 *
 */
public class MenuBar extends JMenuBar  {

	/**
	 * menu containing color, size and mode changes
	 */
	private JMenu settings=new JMenu("Settings");
	
	/**
	 * menu containing stats options
	 */
	private JMenu stats=new JMenu("Stats");
	
	/**
	 * menu contain about and rules options
	 */
	private JMenu help=new JMenu("Help");
	
	/**
	 * menu to select panel to change color
	 */
	private JMenu color=new JMenu("Color");
	
	/**
	 * menu containing modes
	 */
	private JMenu mode=new JMenu("Mode");
	
	/**
	 * menu for enabling or disabling sound
	 */
	private JMenu sound= new JMenu("Sound");
	
	/**
	 * menu for changing size of board
	 */
	private JMenu size=new JMenu("Size");
	
	/**
	 * menu for speed settings
	 */
	private JMenu speed=new JMenu("Speed");
	
	/**
	 * menu for enabling images
	 */
	private JMenu images=new JMenu("Images");
	
	/**
	 * menu item to view highScores list
	 */
	public JMenuItem highScores=new JMenuItem("High Scores");
	
	/**
	 * menu displaying number of plays and average score
	 */
	public JMenuItem history=new JMenuItem("History");
	
	/**
	 * Displays information about the game
	 */
	private JMenuItem about=new JMenuItem("About");
	
	/**
	 * displays rules for game
	 */
	private JMenuItem rules=new JMenuItem("Rules");
	
	/**
	 * resets scores and history
	 */
	public JMenuItem reset=new JMenuItem("RESET");
	
	/**
	 * group containing size options
	 */
	public ButtonGroup sizeGroup=new ButtonGroup();
	
	/**
	 * list of panels currently available for change
	 */
	public JMenuItem[] panelsColor;

	/**
	 * group of sound options
	 */
	public ButtonGroup soundGroup=new ButtonGroup();

	/**
	 * group speed options
	 */
	public ButtonGroup speedGroup=new ButtonGroup();
	
	/**
	 * group of image options
	 */
	public ButtonGroup imageGroup=new ButtonGroup();
	
	
	public ButtonGroup modeGroup=new ButtonGroup();
	/**
	 * menu for advanced modes
	 */
	public JMenu advanced=new JMenu("Advanced");
	
	/**
	 * button for scramble mode
	 */
	public JRadioButtonMenuItem scramble=new JRadioButtonMenuItem("Scramble");
	
	/**
	 * button for reverse mode
	 */
	public JRadioButtonMenuItem reverse=new JRadioButtonMenuItem("Reverse");
	
	/**
	 * button crazy mode
	 */
	public JRadioButtonMenuItem crazy=new JRadioButtonMenuItem("Crazy");

	/**
	 * classic mode
	 */
	public JRadioButtonMenuItem classic=new JRadioButtonMenuItem("Classic");
	/**
	 * listener to display information about game
	 */
	private ActionListener aboutListener;
	
	/**
	 * listener to display rules
	 */
	private ActionListener rulesListener;
	
	/**
	 * slow option
	 */
	public JRadioButtonMenuItem slow=new JRadioButtonMenuItem("Slow");
	
	/**
	 * medium speed option
	 */
	public JRadioButtonMenuItem medium=new JRadioButtonMenuItem("Medium");
	
	/**
	 * fast speed option
	 */
	public JRadioButtonMenuItem fast=new JRadioButtonMenuItem("Fast");
	
	/**
	 * adaptive speed option
	 */
	public JRadioButtonMenuItem adaptive=new JRadioButtonMenuItem("Adaptive");
	
	/**
	 * sound enable
	 */
	public JRadioButtonMenuItem enableSound= new JRadioButtonMenuItem("Enable");
	
	/**
	 * sound disable
	 */
	public JRadioButtonMenuItem disableSound= new JRadioButtonMenuItem("Disable");
	
	/**
	 * images enable
	 */
	public JRadioButtonMenuItem enableImages=new JRadioButtonMenuItem("Enable");
	
	/**
	 * images disable
	 */
	public JRadioButtonMenuItem disableImages= new JRadioButtonMenuItem("Disable");
	
	/**
	 * set size to 2x2
	 */
	public JRadioButtonMenuItem s2x2=new JRadioButtonMenuItem("2X2");
	
	/**
	 * set size to 3x3
	 */
	public JRadioButtonMenuItem s3x3=new JRadioButtonMenuItem("3X3");
	
	/**
	 * set size to 4x4
	 */
	public JRadioButtonMenuItem s4x4=new JRadioButtonMenuItem("4X4");
	
	
	
	/**
	 * Main constructor used for the main menu bar
	 * @param numberOfPanels sets number of panels
	 * @param soundEnabled sets bool for sounds
	 * @param imagesEnabled sets bool for images
	 * @param isSlow sets bool for speed slow
	 * @param isMedium sets bool for speed medium
	 * @param isFast sets bool for speed fast
	 * @param isAdaptive sets bool for speed adaptive
	 * @param isScrambled sets bool for scramble mode
	 * @param isReversed sets bool for reverse mode
	 */
	public MenuBar(int numberOfPanels,boolean soundEnabled,boolean imagesEnabled ,boolean isSlow,
			boolean isMedium,boolean isFast,boolean isAdaptive,
			boolean isScrambled,boolean isReversed){	
		
		panelsColor=new JMenuItem[numberOfPanels];
		if(isSlow){slow.setSelected(true);}
		if(isMedium){medium.setSelected(true);}
		if(isFast){fast.setSelected(true);}
		if(isAdaptive){adaptive.setSelected(true);}
		if(numberOfPanels==4){s2x2.setSelected(true);}
		if(numberOfPanels==9){s3x3.setSelected(true);}
		if(numberOfPanels==16){s4x4.setSelected(true);}
		if(soundEnabled){enableSound.setSelected(true);}
		if(!soundEnabled){disableSound.setSelected(true);}
		if(imagesEnabled){enableImages.setSelected(true);}
		if(!imagesEnabled){disableImages.setSelected(true);}
		if(isScrambled&&!isReversed){scramble.setSelected(true);}
		if(!isScrambled&&isReversed){reverse.setSelected(true);}
		if(isScrambled&&isReversed){crazy.setSelected(true);}

		settings.setHorizontalAlignment(JMenu.CENTER);
		settings.add(color);
		for(int i=0;i<numberOfPanels;i++){
			panelsColor[i]=new JMenuItem ("Panel "+(i+1));
			color.add(panelsColor[i]);			
		}
		settings.add(mode);

		mode.add(size);
		
				
		sizeGroup.add(s2x2);
		sizeGroup.add(s3x3);
		sizeGroup.add(s4x4);
		size.add(s2x2);
		size.add(s3x3);
		size.add(s4x4);
				
		mode.add(sound);
	
		sound.add(enableSound);
		sound.add(disableSound);	
		soundGroup.add(enableSound);
		soundGroup.add(disableSound);

		mode.add(images);
		imageGroup.add(enableImages);
		imageGroup.add(disableImages);
		images.add(enableImages);
		images.add(disableImages);

		
		mode.add(speed);
		speed.add(slow);
		speed.add(medium);
		speed.add(fast);
		speed.add(adaptive);
		speedGroup.add(slow);
		speedGroup.add(medium);
		speedGroup.add(fast);
		speedGroup.add(adaptive);
		
		mode.add(advanced);
		modeGroup.add(scramble);
		modeGroup.add(reverse);

		modeGroup.add(crazy);

		modeGroup.add(classic);

		advanced.add(scramble);
		advanced.add(reverse);
		advanced.add(crazy);
		advanced.add(classic);
		
		stats.add(reset);
		stats.setHorizontalAlignment(JMenu.CENTER);
		stats.add(highScores);
		stats.add(history);
				
		initAboutListener();
		initRulesListener();
		help.setHorizontalAlignment(JMenu.CENTER);
		help.add(about);
		help.add(rules);
		
		about.addActionListener(aboutListener);
		rules.addActionListener(rulesListener);
		
		this.add(settings);
		this.add(stats);
		this.add(help);			
	}
	
	/**
	 * Initialize about button to display information about game
	 */
	private void initAboutListener(){
		aboutListener=new ActionListener(){
			
			@Override
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(null,"Created by Derek Garner CS2410","About",JOptionPane.PLAIN_MESSAGE);
				}		
		};
	}
		
	/**
	 * initialize rule button listener to display rules about the game
	 */
	private void initRulesListener(){
		rulesListener=new ActionListener(){
			
			@Override
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(null,"Simon is a game "
						+ "where the user must repeat the follwing pattern of increasing "
						+ "size."
						+ "\nReverse setting and scramble setting change gameplay "
						+ "and crazy setting combines the two.","Rules",JOptionPane.PLAIN_MESSAGE);
				}				
		};
	}
}
