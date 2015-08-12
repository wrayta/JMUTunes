package view.file;

import java.io.*;

/**
 * Handle file operations - 2 modes: 1 = READING, 2 = WRITING. To open a file
 * for reading, you can either specify this in the constructor: AudioFileIO
 * myFile = new AudioFileIO( "Filename", FileIO.READING ) or create a generic
 * object: AudioFileIO myFile = new AudioFileIO() and then call the open method:
 * myFile.open( "Filename", FileIO.READING ).
 * 
 * Modifications: **TAW & JMC: PA2 (2/10/2013) - New for PA2
 * 
 * @author Thomas Wray & Joe Cumins
 * @version PA2 (2/10/2013)
 */
public class AudioFileIO {
	// ----------------------------------------------------------------------
	// Declarations
	// ----------------------------------------------------------------------
	private int mode;

	private BufferedReader reader;
	private BufferedWriter writer;

	private File file;

	public static final int READING = 1;
	public static final int WRITING = 2;

	/**
	 * Default constructor
	 */
	public AudioFileIO() {
		mode = 0;
		reader = null;
		writer = null;
		file = null;

	} // default constructor

	/**
	 * Explicit value constructor
	 * 
	 * @param fileName
	 * @param fileMode
	 */
	public AudioFileIO(String fileName, int fileMode) {
		// the open method already does this, so we'll not repeat the code here
		open(fileName, fileMode);

	} // explicit value constructor

	/**
	 * Close the file
	 * 
	 * @return true if file can be closed
	 */
	public boolean close() {
		boolean success = false; // assume failure

		try {
			if (mode == READING && reader != null)
				reader.close();
			else if (mode == WRITING && writer != null)
				writer.close();

			success = true;
		} catch (IOException e) { /* do nothing */
		} catch (NullPointerException e) { /* do nothing */
		}

		return success;

	} // method close

	/**
	 * Reports on whether the Reader/Writer exists
	 * 
	 * @return true if the object exists
	 */
	public boolean exists() {
		boolean exists = false; // assume it does not exist

		if (mode == READING)
			exists = (reader != null);
		else if (mode == WRITING)
			exists = (writer != null);

		return exists;

	} // method exists

	/**
	 * Get the name of the file being read or written to
	 * 
	 * @return the name of the file
	 */
	public String getFileName() {
		return file.getName();

	} // method getFileName

	/**
	 * Get the mode (READING or WRITING) for this object
	 * 
	 * @return the mode as an int (see the static public finals to resolve)
	 */
	public int getMode() {
		return mode;

	} // method getMode

	/**
	 * Return the length of the file
	 * 
	 * @return the length of the file
	 */
	public long length() {
		return file.length();

	} // method length

	/**
	 * Open a file for READING or WRITING
	 * 
	 * @param fileName
	 * @param fileMode
	 * @return success or failure
	 */
	public boolean open(String fileName, int fileMode) {
		boolean success = false; // assume failure
		file = new File(fileName);

		mode = fileMode;
		try {
			if (mode == READING) {
				if (file.exists()) {
					reader = new BufferedReader(new FileReader(file));
					success = true;

				} // end if

			} // end if

			else if (mode == WRITING) {
				writer = new BufferedWriter(new FileWriter(file));
				success = true;

			} // end else
		} catch (IOException e) { /* do nothing */
		}

		return success;

	} // method open

	/**
	 * Read a line from the file (mode == READING)
	 * 
	 * @return the line read from the file
	 */
	public String readLine() {
		String line = null;

		try {
			if (mode == READING)
				return line = reader.readLine();
		} catch (IOException e) { /* do nothing */
		}

		return line;

	} // method readLine

	/**
	 * Write a line to the file (mode == WRITING)
	 * 
	 * @param line
	 * @return success or failure
	 */
	public boolean write(String line) {
		boolean success = false;

		try {
			if (mode == WRITING) {
				if (line != null) {
					writer.write(line + "\n");
					success = true;

				} // end if

			} // end if
		} catch (IOException e) { /* do nothing */
		}

		return success;

	} // method write

} // class AudioFileIO
