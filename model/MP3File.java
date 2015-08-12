package model;

/**
 * A single MP3 file (extends AudioFile)
 * 
 * Modifications: **TAW & JMC - PA3 (3/8/2013) - new for PA3
 * 
 * @author Thomas Wray & Joe Cumins
 * @version PA3 (3/8/2013)
 */
public class MP3File extends AudioFile {
	private int bitRate;
	private String label;

	/**
	 * Default constructor
	 */
	public MP3File() {
		super();

		bitRate = 0;
		label = "";

	} // default constructor

	/**
	 * Explicit value constructor
	 * 
	 * @param artist
	 * @param title
	 * @param album
	 * @param track
	 * @param pathName
	 * @param label
	 * @param bitRate
	 */
	public MP3File(String artist, String title, String album, int track,
			String pathName, String label, int bitRate) {
		super(artist, title, album, track, pathName);

		this.label = label;
		this.bitRate = bitRate;

	} // explicit value constructor

	/**
	 * Return the bit rate
	 * 
	 * @return the bitRate
	 */
	public int getBitRate() {
		return bitRate;

	} // method getBitRate

	/**
	 * Copy constructor
	 * 
	 * @param the
	 *            MP3File to copy
	 */
	public MP3File(AudioFile copy) {
		super(copy);

		try {
			MP3File newCopy = (MP3File) copy;
			label = newCopy.getLabel();
			bitRate = newCopy.getBitRate();

		} // end try

		catch (ClassCastException e) { /* can't copy */
		}

	} // copy constructor

	/**
	 * Return the label
	 * 
	 * @return the label
	 */
	public String getLabel() {
		return label;

	} // method getLabel

	/**
	 * Set the bit rate
	 * 
	 * @param bitRate
	 */
	public void setBitRate(int bitRate) {
		this.bitRate = bitRate;

	} // method setBitRate

	/**
	 * Set the bit rate (overloaded)
	 * 
	 * @param bitString
	 */
	public void setBitRate(String bitString) {
		int bitRate = 0;

		if (bitString != null)
			if (bitString.trim().length() > 0)
				try {
					bitRate = Integer.parseInt(bitString);
					setBitRate(bitRate);

				} // end try
				catch (NumberFormatException e) { /* do nothing */
				}

	} // method setBitRate

	/**
	 * Set the value for the record label
	 * 
	 * @param label
	 */
	public void setLabel(String label) {
		if (label != null)
			this.label = label;

	} // method setLabel

} // class MP3File
