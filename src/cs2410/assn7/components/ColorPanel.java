package cs2410.assn7.components;

import java.awt.Color;

import javax.sound.midi.MidiChannel;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Synthesizer;
import javax.swing.JPanel;


@SuppressWarnings("serial")
/**
 * 
 * @author Derek Garner
 * @brief Class for color panel objects which 
 * are used on simonish game board
 */
public class ColorPanel extends JPanel {
	/**
	 * Color of panel
	 */
	private Color color;
	
	/**
	 * midi object use for sound
	 */
	private MidiChannel midi;
	
	/**
	 * integer used to choose pictch of sound
	 */
	private int pitch;
	
	/**
	 *  bool determines if sound should be played
	 */
	private boolean sound;
	
	/**
	 * 
	 * @param color sets color of the panel
	 * @param newPitch sets note played when panel is pressed
	 * @param soundOn determines if sound should be played 
	 */
	public ColorPanel(Color color,int newPitch,boolean soundOn) {
		this.color = color.darker().darker();
		this.pitch=newPitch;
		this.sound=soundOn;
		this.setBackground(this.color);
		try {
			Synthesizer synth = MidiSystem.getSynthesizer();
			synth.open();
	        midi = synth.getChannels()[0];
		} catch (MidiUnavailableException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	/**
	 * operations for panel being pressed
	 * sound is only played if bool sound is true
	 */
	public void pressed() {
			if(sound){
			midi.programChange(12);
			midi.noteOn(pitch, 100);	
			}
		this.setBackground(color.brighter().brighter());
		this.update(this.getGraphics());
	}
	
	/**
	 * resets panel upon release
	 */
	public void released() {
		this.setBackground(color);
		this.update(this.getGraphics());
	}
	/**
	 * resets panel
	 */
	public void reset() {
		this.setBackground(this.color);
		this.update(this.getGraphics());
	}
	
	/**
	 * sets color of panel 
	 * @param colorSet to set color panel
	 */
	public void setColor(Color colorSet){
		this.color=colorSet;
	}
	
	/**
	 * 
	 * @return color of current panel
	 */
	public Color getColor(){
		return this.color;
	}
}
