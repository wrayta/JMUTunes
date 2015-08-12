package controller.file;

import java.io.*;

import model.*;
import view.text.*;

/**
 * Handle loading of audio files from this disk. This abstract class will handle
 * all generic operations. Concrete classes for specific types of audio files
 * will handle the details (uses the Bridge pattern).
 * 
 * @author Thomas Wray & Joe Cumins
 * @version PA3 (3/16/2013)
 */
public abstract class AudioFileHandler {
	protected AudioList list;

	public static final int MP3 = 1;
	public static final int WAV = 2;

	/**
	 * Explicit value constructor
	 */
	public AudioFileHandler(AudioList list) {
		this.list = list;

	} // explicit value constructor

	/**
	 * Abstract method to create a String for file writing from an AudioFile
	 * object
	 * 
	 * @param file
	 * @return a formatted string to write to Songlist.txt
	 */
	public abstract String createAudioFile(AudioFile file);

	/**
	 * Abstract method to create an AudioFile object from the information read
	 * from Songlist.txt
	 * 
	 * @param line
	 * @return an AudioFile object
	 */
	public abstract AudioFile createAudioFile(String line);

	/**
	 * Load the information from the file into AudioList
	 * 
	 * @param file
	 */
	public abstract void load(File file);

	/**
	 * Is the incoming line malformed?
	 * 
	 * @param elements
	 * @return true if the line is malformed
	 */
	protected abstract boolean isMalformed(String[] elements);

} // class AudioFileLoad
