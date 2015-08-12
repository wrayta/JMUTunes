package model;

import java.util.*;

import javax.swing.DefaultListModel;

/**
 * The collection of AudioFile object
 * 
 * Modifications: **TAW & JMC PA2 (2/10/2013) - Modified class to function as a
 * set - changed storage structure from array to ArrayList - modified add to
 * guarantee uniqueness - modified to place in alpha order - added private
 * methods: append, insert, isLast to assist in alpha adding - added contains to
 * assist in set functions - added clear method because I could - added
 * difference, intersection, and union methods because this is a set - Modified
 * add to prevent adding of malformed records (missing artist/ title)
 * 
 * @author Thomas Wray & Joe Cumins
 * @version PA2 (2/12/2013), PA1 (1/18/2013)
 */
public class AudioList {
	// ----------------------------------------------------------------------
	// Declarations
	// ----------------------------------------------------------------------
	private DefaultListModel list;

	/**
	 * Default constructor
	 */
	public AudioList() {
		list = new DefaultListModel();

	} // default constructor

	/************************** public methods ****************************/

	/**
	 * Add an audio file to the list
	 * 
	 * **TAW & JMC (PA2) - Modified to guarantee uniqueness & place in alpha
	 * order - Modified to prevent addition of malformed records (missing artist
	 * and/or title)
	 * 
	 * @param the
	 *            audio file the add
	 * @return the position added
	 */
	public void add(AudioFile newFile) {

		if (newFile != null)
			if (newFile.getArtist().length() > 0
					&& newFile.getTitle().length() > 0)
				if (!list.contains(newFile)) {
					if (list.size() == 0 || isLast(newFile))
						append(newFile);
					else
						insert(newFile);
				} // end if

	} // method add

	/**
	 * Clear the Audiolist
	 * 
	 * **TAW & JMC (PA2) - added for PA2
	 */
	public void clear() {
		list.clear();

	} // method clear

	/**
	 * Check to see if the object in the parameter is contained in the list
	 * 
	 * **TAW & JMC (PA2) - added for PA2
	 * 
	 * @param the
	 *            AudioFile to check
	 * @return true if the file is contained in the list, false otherwise
	 */
	public boolean contains(AudioFile file) {
		return list.contains(file);
	}

	/**
	 * Returns the difference between the 2 sets (the unique items in this set)
	 * 
	 * **TAW & JMC (PA2) - added for PA2
	 * 
	 * @param the
	 *            other AudioList
	 * @return the unique items in this AudioList
	 */
	public AudioList difference(AudioList other) {
		AudioList difference = new AudioList();

		for (int i = 0; i < list.size(); i++) {
			AudioFile file = (AudioFile) list.get(i);

			if (!other.contains(file))
				difference.add(file);
		}

		return difference;

	} // method difference

	/**
	 * Get a particular audio file
	 * 
	 * **TAW & JMC (PA2) - Modified for PA2 to use ArrayList
	 * 
	 * @param position
	 * @return the specified audio file
	 */
	public AudioFile get(int which) {
		AudioFile gotFile = null; // return null if position not available

		if (isInBounds(which))
			gotFile = (AudioFile) list.getElementAt(which);

		return gotFile;

	} // method get

	/**
	 * Get the short title for the selected audio file
	 * 
	 * **TAW & JMC (PA2) - Modified for PA2 to use ArrayList
	 * 
	 * @param which
	 * @return the short title
	 */
	public String getShortTitle(int which) {
		String shortTitle = "";

		if (isInBounds(which))
			shortTitle = list.get(which).toString();

		return shortTitle;

	} // method getShortTitle

	/**
	 * Returns the intersection of the two sets (AudioLists)
	 * 
	 * **TAW & JMC (PA2) - added for PA2
	 * 
	 * @param the
	 *            other AudioList
	 * @return the intersection of this and the other AudioList
	 */
	public AudioList intersection(AudioList other) {
		AudioList intersection = new AudioList();

		for (int i = 0; i < list.size(); i++) {
			AudioFile file = (AudioFile) list.get(i);

			if (other.contains(file)) {
				intersection.add(file);
			}
		}

		return intersection;

	} // method intersection

	/**
	 * Remove an audio file from the list - in the first version we delete only
	 * the last audio file
	 * 
	 * **TAW & JMC (PA2) - modified to remove specified audio file
	 * 
	 * @param position
	 * @return
	 */
	public boolean remove(int which) {
		boolean success = false; // assume this will not work

		if (isInBounds(which)) {
			list.remove(which);
			success = true;

		} // end if

		return success;

	} // method remove

	/**
	 * Remove an audio file from the list - overloaded version to delete the
	 * object specified
	 * 
	 * **TAW & JMC (PA2) - new for PA2
	 * 
	 * @param the
	 *            AudioFile to delete
	 * @return true if successful
	 */
	public boolean remove(AudioFile which) {
		return list.removeElement(which);

	} // method remove (overloaded)

	/**
	 * Returns the number of AudioFiles in the list
	 * 
	 * **TAW & JMC (PA2) - changed to use the ArrayList.size() method
	 * 
	 * @return the number of audio files in the list
	 */
	public int size() {
		return list.size();

	} // method getLast

	/**
	 * Return the union of this set and the other (AudioLists)
	 * 
	 * **TAW & JMC (PA2) - added for PA2
	 * 
	 * @param the
	 *            other AudioList
	 * @return the union of the 2 AudioLists
	 */
	public AudioList union(AudioList other) {
		AudioList union = new AudioList();

		for (int i = 0; i < list.size(); i++) {
			AudioFile file = (AudioFile) list.get(i);
			union.add(file);
		}

		for (int i = 0; i < other.size(); i++) {
			union.add(other.get(i));
		}

		return union;

	} // method union

	public DefaultListModel getList() {
		return list;
	}

	public DefaultListModel getModel() {
		return list;
	}

	/************************* private methods ****************************/

	/**
	 * Append the AudioFile to the end of the list
	 * 
	 * **TAW & JMC (PA2) - added for PA2
	 * 
	 * @param the
	 *            AudioFile to append
	 */
	private void append(AudioFile file) {
		if (file != null)
			list.addElement(file);

	} // method append

	/**
	 * Insert the AudioFile into its proper place in the list
	 * 
	 * **TAW & JMC (PA2) - added for PA2 **TAW & JMC (PA3) - modified to use the
	 * new compareTo method of AudioFile (this simplifies the method!!)
	 * 
	 * @param the
	 *            AudioFile to insert
	 */
	private void insert(AudioFile file) {
		if (file != null) {
			int counter = 0;

			while (file.compareTo((AudioFile) list.get(counter)) >= 0)
				counter++;

			list.add(counter, file);

		} // end if

	} // method insert

	/**
	 * Returns true if the parameter is in bounds (> 0 and <= last)
	 * 
	 * @param which
	 *            file?
	 * @return true if it is here
	 */
	private boolean isInBounds(int which) {
		return which > -1 && which < size();

	} // method isInBounds

	/**
	 * Checks to see if the AudioFile should go at the end of the list based on
	 * the sort string of the AudioFile
	 * 
	 * **TAW & JMC (PA2) - added for PA2
	 * 
	 * @param the
	 *            AudioFile to check
	 * @return true if this AudioFile should go at the end of the list
	 */
	private boolean isLast(AudioFile file) {
		boolean isLast = false;

		if (file != null) {
			String compFile = file.getSortString();
			String lastFile = ((AudioFile) list.get(list.size() - 1))
					.getSortString();

			if (compFile.compareTo(lastFile) >= 0)
				isLast = true;

		} // end if

		return isLast;

	} // method isLast

} // class AudioList
