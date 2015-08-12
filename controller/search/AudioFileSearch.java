package controller.search;

import model.*;
import view.text.*;

/**
 * This class handles the search functionality for JMUTunes
 * 
 * Modifications: **TAW & JMC: PA3 (3/14/2013) - new for PA3
 * 
 * @author Thomas Wray & Joe Cumins
 * @version PA3 (3/14/2013)
 */
public class AudioFileSearch {
	private AudioList list;

	public static final char STARTS_WITH = '^';
	public static final char ENDS_WITH = '$';
	public static final char CONTAINS = '@'; // used internally only
	public static final char NOT = '!';

	public static final int ARTIST = 1;
	public static final int TITLE = 2;
	public static final int ALBUM = 3;

	/**
	 * Explicit value constructor
	 * 
	 * @param list
	 * @param view
	 */
	public AudioFileSearch(AudioList list) {
		this.list = list;

	} // explicit value constructor

	/**
	 * Search the AudioList for AudioFiles matching the user-specified criteria
	 * 
	 * @return an AudioList containing the result of the search
	 */
	public AudioList search() {
		AudioList newResultSet;
		AudioList resultSet = null;

		String[] fields = { "Artist: ", "Title: ", "Album: " };
		String searchTerm = "";

		// do 3x (1 each for artist, title, album)
		for (int field = 1; field < 4; field++) {
			// repeat for each element until the user presses <ENTER> at
			// an empty line
			do {
				// view.display( fields[ field - 1 ] );
				// searchTerm = view.getInput( false, "" );
				searchTerm = searchTerm.trim(); // remove excess space

				if (searchTerm.length() > 0)
					if (resultSet == null)
						resultSet = getResultSet(field, searchTerm);
					else {
						newResultSet = getResultSet(field, searchTerm);
						resultSet = resultSet.intersection(newResultSet);

					} // end else

			} while (searchTerm.trim().length() > 0);

		} // end for

		return resultSet;

	} // method search()

	/**
	 * Return true if the String check contains term
	 * 
	 * @param the
	 *            String to check
	 * @param the
	 *            substring we're looking for
	 * 
	 * @return true if the String check contains term
	 */
	private boolean contains(String check, String term) {
		return check.toLowerCase().indexOf(term.toLowerCase()) > -1;

	} // method startsWith

	/**
	 * Return true if the String check ends with term
	 * 
	 * @param the
	 *            String to check
	 * @param the
	 *            substring we're looking for
	 * 
	 * @return true if the String check ends with term
	 */
	private boolean endsWith(String check, String term) {
		return check.toLowerCase().endsWith(term.toLowerCase());

	} // method endsWith

	/**
	 * Get the expression inside of a "NOT" expression - tbe meaning of this
	 * will be inverted
	 * 
	 * @param check
	 * @return
	 */
	private String getNotExpression(String expression) {
		String toCheck = null;

		// check to make sure this is well formed first
		if (isLegalNotExpression(expression))
			toCheck = expression.substring(2, expression.length() - 1);

		return toCheck;

	} // method invertCheck

	/**
	 * Return the operation to be performed
	 * 
	 * @param the
	 *            expression to check
	 * @return the operation to be performed (as a char)
	 */
	private char getOperation(String term) {
		char operation = (char) 0;
		String operations = "" + STARTS_WITH + ENDS_WITH + NOT;

		if (term != null && term.trim().length() > 0)
			if (operations.indexOf(term.charAt(0)) > -1)
				operation = term.charAt(0);
			else
				operation = CONTAINS;

		return operation;

	} // method getOperation

	/**
	 * Return an AudioList containing the selected AudioFiles
	 * 
	 * @param type
	 * @param term
	 * 
	 * @return an AudioFile (set) containing the selected AudioFiles
	 */
	private AudioList getResultSet(int type, String term) {
		AudioList resultSet = new AudioList();

		for (int i = 0; i < list.size(); i++) {
			AudioFile file = list.get(i);
			String check = null;

			if (file != null)
				switch (type) {
				case ARTIST:
					check = file.getArtist();
					break;
				case TITLE:
					check = file.getTitle();
					break;
				case ALBUM:
					check = file.getAlbum();
					break;

				} // end switch

			check = check.trim(); // trim excess space

			if (includeItem(check, term))
				resultSet.add(file);

		} // end for

		return resultSet;

	} // method getResultSet

	/**
	 * Determine if an item should be included in the search list
	 * 
	 * @param the
	 *            word or expression to search
	 * @param the
	 *            String we are looking for
	 * 
	 * @return true if it is found
	 */
	private boolean includeItem(String check, String term) {
		boolean contains = false;

		if (check != null)
			if (term != null & term.length() > 0) {
				switch (getOperation(term)) {
				case STARTS_WITH:
					contains = startsWith(check, term.substring(1));
					break;
				case ENDS_WITH:
					contains = endsWith(check, term.substring(1));
					break;
				case CONTAINS:
					contains = contains(check, term);
					break;
				case NOT:
					String newTerm = getNotExpression(term);
					if (newTerm != null)
						contains = !includeItem(check, newTerm);

				} // end switch

			} // end if

		return contains;

	} // method includeItem

	/**
	 * Is this a legal "Not" expression?
	 * 
	 * @param term
	 * @return true if this is a legal expression
	 */
	private boolean isLegalNotExpression(String check) {
		boolean isLegal = false;

		if (check != null && check.length() > 2)
			if (check.charAt(0) == '!')
				if (check.charAt(1) == '(')
					if (check.charAt(check.length() - 1) == ')')
						isLegal = true;

		return isLegal;

	} // method isLegalNotExpression

	/**
	 * Return true if the String check starts with term
	 * 
	 * @param the
	 *            String to check
	 * @param the
	 *            substring we're looking for
	 * 
	 * @return true if the String check starts with term
	 */
	private boolean startsWith(String check, String term) {
		return check.toLowerCase().startsWith(term.toLowerCase());

	} // method startsWith

} // class AudioFileSearch
