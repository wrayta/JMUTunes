package controller.file;

import java.io.*;

import model.*;
import view.text.*;

/**
 * Handle Wav specific file operations (uses the Bridge pattern)
 * 
 * @author Thomas Wray & Joe Cumins
 * @version PA3 (3/16/2013)
 */
public class WavFileHandler extends AudioFileHandler {
	/**
	 * Explicit value constructor
	 * 
	 * @param list
	 * @param view
	 */
	public WavFileHandler(AudioList list) {
		super(list);

	} // explicit value constructor

	/**
	 * Create a output line to write from a WavFile object
	 * 
	 * @param the
	 *            AudioFile to translate
	 * @return the String representation of the AudioFile
	 */
	@Override
	public String createAudioFile(AudioFile audioFile) {
		WavFile file = (WavFile) audioFile;

		String album = " ";
		String artist = "";
		String length = " ";
		String pathName = " ";
		String title = "";
		String track = " ";

		artist = file.getArtist();
		title = file.getTitle();

		if (file.getAlbum().trim().length() != 0)
			album = file.getAlbum();
		if (file.getPathName().trim().length() != 0)
			pathName = file.getPathName();
		if (file.getTrack() != 0)
			track = "" + file.getTrack();
		if (file.getLength() != 0L)
			length = "" + file.getLength();

		return WAV + "|" + artist + "|" + title + "|" + album + "|" + track
				+ "|" + pathName + "|" + length + "|";

	} // method createAudioFile

	/**
	 * Create an WavFile object from the text line. If the line is malformed
	 * return null
	 * 
	 * **TAW & JMC (PA3) - New for PA3 - converted from createAudioFile(String)
	 * 
	 * @param the
	 *            line from the file
	 * @return a WavFile if good, null otherwise
	 */
	@Override
	public AudioFile createAudioFile(String line) {
		WavFile audio = null;

		if (line != null) {
			String[] elements = line.split("\\|");

			// skip malformed records
			if (!isMalformed(elements)) {
				audio = new WavFile();

				audio.setArtist(elements[1].trim());
				audio.setTitle(elements[2].trim());
				audio.setAlbum(elements[3].trim());
				audio.setTrack(elements[4].trim());
				audio.setPathName(elements[5].trim());
				audio.setLength(elements[6].trim());

			} // end if

		} // end if

		return audio;

	} // method createFile

	/**
	 * Load a wav file into the list
	 * 
	 * **TAW & JMC (PA3) - new for PA3
	 * 
	 * @param file
	 */
	@Override
	public void load(File file) {
		// no tags in a wav file, so just plug the values
		WavFile wav = new WavFile();

		wav.setArtist(file.getName());
		wav.setTitle(file.getName());
		wav.setPathName(file.getAbsolutePath());
		wav.setLength(file.length());

		list.add(wav);

	} // method loadWav

	/**
	 * Check the line to see if it is malformed.
	 * 
	 * @param an
	 *            array of elements
	 * @return true if the line is malformed, false otherwise
	 */
	@Override
	protected boolean isMalformed(String[] elements) {
		boolean isMalformed = true;

		if (elements.length == 7)
			if (elements[1].trim().length() > 0)
				if (elements[2].trim().length() > 0)
					isMalformed = false;

		return isMalformed;

	} // method isMalformed

}
