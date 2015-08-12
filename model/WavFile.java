package model;

/**
 * A single WAV file (extends AudioFile)
 * 
 * Modifications: PA3 (3/8/2013) - new for PA3
 * 
 * @author Thomas Wray & Joe Cumins
 * @version PA3 (3/8/2013)
 */
public class WavFile extends AudioFile {
	private long length;

	/**
	 * Default constructor
	 */
	public WavFile() {
		super();

		length = 0L;

	} // default constructor

	/**
	 * Explicit value constructor
	 * 
	 * @param artist
	 * @param title
	 * @param album
	 * @param track
	 * @param pathName
	 * @param length
	 */
	public WavFile(String artist, String title, String album, int track,
			String pathName, long length) {
		super(artist, title, album, track, pathName);

		this.length = length;

	} // explicit value constructor

	/**
	 * Copy constructor
	 * 
	 * @param the
	 *            WavFile to copy
	 */
	public WavFile(AudioFile copy) {
		super(copy);

		try {
			WavFile newCopy = (WavFile) copy;
			length = newCopy.getLength();

		} // end try

		catch (ClassCastException e) { /* can't copy */
		}

	} // copy constructor

	/**
	 * Return the file length
	 * 
	 * @return the file length
	 */
	public long getLength() {
		return length;

	} // method getLength

	/**
	 * Set the file length
	 * 
	 * @param length
	 */
	public void setLength(long length) {
		this.length = length;

	} // method setLength

	public void setLength(String lenString) {
		long length = 0;

		if (lenString != null)
			if (lenString.trim().length() > 0)
				try {
					length = Long.parseLong(lenString);
					setLength(length);

				} // end try
				catch (NumberFormatException e) { /* do nothing */
				}

	}

} // class WavFile
