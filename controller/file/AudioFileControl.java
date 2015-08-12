package controller.file;

import java.io.*;

/**
 * Top-level file handling routine - uses AudioFileHandler & its children to
 * handle type specific issues
 * 
 * Modifications: **TAW & JMC - PA2 (2/20/2012) - New for PA2 **TAW & JMC - PA3
 * (3/8/2013) - added loadFiles method - added helper methods isMP3 & isWav -
 * modified readFile / writeFile methods to handle new file structure
 * 
 * @author Thomas Wray & Joe Cumins
 * @version PA3 (3/8/2013), PA2 (2/12/2013)
 */
public class AudioFileControl {
	// ----------------------------------------------------------------------
	// Declarations
	// ----------------------------------------------------------------------
	private model.AudioList list;

	public static final int MP3 = 1;
	public static final int WAV = 2;

	/**
	 * Default constructor
	 */
	public AudioFileControl(model.AudioList list) {
		this.list = list;

	} // default constructor

	/**
	 * Load files from the disk (recursive method)
	 * 
	 * **TAW & JMC (PA3) - new for PA3
	 */
	public void loadFiles(File startingFolder, String filter) {
		AudioFileHandler loader;

		// make sure the file/directory is accessible
		if (startingFolder.canRead())
			if (startingFolder.isDirectory()) {
				File[] files = startingFolder.listFiles();

				for (File file : files) {
					loader = null;

					// make sure the file/directory is accessible
					if (file.canRead()) {
						if (file.getAbsolutePath().toLowerCase()
								.endsWith(".mp3")) {
							if (filter.length() == 6 || filter.equals("mp3"))
								loader = new MP3FileHandler(list);

						} // end if

						else if (file.getAbsolutePath().toLowerCase()
								.endsWith(".wav")) {

							if (filter.length() == 6 || filter.equals("wav"))
								loader = new WavFileHandler(list);

						} // end else

						if (loader != null)
							loader.load(file);
						else if (file.isDirectory())
							loadFiles(file, filter);

					} // end if

				} // end for

			} // end if

	} // method loadFiles

	/**
	 * Read the file if it exists and populate the Library
	 * 
	 * **TAW & JMC (PA3) - modified to create MP3File/WavFile file objects -
	 * uses new createMP3File & createWavFile methods
	 */
	public void readFile() {
		view.file.AudioFileIO audioFile = new view.file.AudioFileIO();
		view.file.AudioFileIO errFile = new view.file.AudioFileIO();
		AudioFileHandler handler = null; // new for PA3

		// Declare String to hold a line from the file
		String audioLine = null;

		// open the audio file for reading and the error file for writing
		audioFile.open("Songlist.txt", view.file.AudioFileIO.READING);
		errFile.open("malformed.err", view.file.AudioFileIO.WRITING);

		// make sure the reader for the file exists
		if (audioFile.exists()) {
			// read first line from the library file
			audioLine = audioFile.readLine();

			while (audioLine != null) {
				model.AudioFile file = null;

				if (isMP3(audioLine))
					handler = new MP3FileHandler(list);
				else if (isWav(audioLine))
					handler = new WavFileHandler(list);
				else
					errFile.write(audioLine); // not 1 or 2

				if (handler != null) {
					file = handler.createAudioFile(audioLine);

					if (file != null)
						list.add(file);
					else
						errFile.write(audioLine);

				} // end if

				audioLine = audioFile.readLine();

			} // end while

			// close the reader & writer (for malformed.err)
			audioFile.close();
			errFile.close();

		} // end if

	} // method readFiles

	/**
	 * write the library to a data file
	 * 
	 * **TAW & JMC (PA3) - modified to write MP3File or WavFile
	 */
	public void writeFile() {
		AudioFileHandler handler = null;

		view.file.AudioFileIO audioFile = new view.file.AudioFileIO();

		// open the audio data file for writing
		audioFile.open("Songlist.txt", view.file.AudioFileIO.WRITING);

		// read through the library
		for (int i = 0; i < list.size(); i++) {
			// write the library file
			model.AudioFile file = list.get(i);

			if (file instanceof model.MP3File)
				handler = new MP3FileHandler(list);
			else
				handler = new WavFileHandler(list);

			audioFile.write(handler.createAudioFile(file));

		} // end for

		// close the writer
		audioFile.close();

	} // method writeFiles

	/************************** private methods ***************************/

	/**
	 * Does this line represent an MP3 file?
	 * 
	 * **TAW & JMC (PA3) - new for PA3
	 * 
	 * @param the
	 *            line to check
	 * @return true if this line is an MP3
	 */
	private boolean isMP3(String line) {
		boolean isMP3 = false;

		if (line != null)
			if (line.length() > 0)
				if (line.charAt(0) == '1')
					isMP3 = true;

		return isMP3;

	} // method isMP3

	/**
	 * Does this line represent an MP3 file?
	 * 
	 * **TAW & JMC (PA3) - new for PA3
	 * 
	 * @param the
	 *            line to check
	 * @return true if this line is an MP3
	 */
	private boolean isWav(String line) {
		boolean isWav = false;

		if (line != null)
			if (line.length() > 0)
				if (line.charAt(0) == '2')
					isWav = true;

		return isWav;

	} // method isMP3

} // class AudioFileControl
