package view.text;

import java.io.*;

/**
 * AudioView - handles the interaction between the control part of the program
 * and the user. All screen and keyboard I/O is here.
 * 
 * Modifications: **TAW & JMC (PA2) - fixed newline after retry in getInput() -
 * added checks for null & empty for getInput and pause methods - added boolean
 * parameter to getNumber() to distinguish between non-required values (track)
 * and required values (listings) for edit and delete. **TAW & JMC (PA3) - added
 * overloaded getNumber for long values - added isValidLong to support
 * overloaded getNumber method
 * 
 * @author Thomas Wray & Joe Cumins
 * @version PA2 (2/18/2013), PA1 (1/19/2013)
 * 
 */
public class AudioView {
	// declarations
	private BufferedReader reader; // the reader object

	/**
	 * Default value constructor
	 * 
	 * @throws IOException
	 */
	public AudioView() {
		reader = new BufferedReader(new InputStreamReader(System.in));

	} // default constructor

	/**************************** public Methods *************************/

	/**
	 * Print the text in the center of an 80-column screen
	 * 
	 * @param incoming
	 *            text
	 */
	public void centerText(String text) {
		String spaces = "";

		if (text != null && text.length() > 0) {
			// truncate if over 80 characters
			text = truncate(text);

			for (int i = 0; i < (80 - text.length()) / 2; i++)
				spaces += " ";

			displayLine(spaces + text);

		} // end if

	} // method centerText

	/**
	 * Clear a line
	 * 
	 * **TAW & JMC (PA3) - added for PA3
	 */
	public void clearLine() {
		for (int i = 0; i < 80; i++)
			display("\b"); // return cursor to beginning
		for (int i = 0; i < 80; i++)
			display(" "); // clear line
		for (int i = 0; i < 80; i++)
			display("\b"); // return to beginning again

	} // method clearLine

	/**
	 * Clear the screen.
	 */
	public void clearScreen() {
		for (int i = 0; i < 25; i++)
			displayLine();

	} // method clearScreen

	/**
	 * display - display the text to the screen without end-of-line. Truncates
	 * if necessary
	 * 
	 * @param the
	 *            text to display
	 */
	public void display(String text) {
		if (text != null)
			System.out.print(truncate(text));

	} // method display

	/**
	 * displayError - display an error message and request re-entry of input
	 * 
	 * @param the
	 *            error message
	 */
	public void displayError(String errorMessage) {
		displayLine();

		if (errorMessage != null)
			displayLine(truncate(errorMessage));

		display("Please re-enter -> ");

	} // method displayError

	/**
	 * displayLine - display a blank line to the screen
	 * 
	 * @param String
	 */
	public void displayLine() {
		System.out.println();

	} // method displayLine

	/**
	 * displayLine - display the content of the parameter to the screen with NL
	 * (overloaded version). Truncates to 80 characters if necessary.
	 * 
	 * @param the
	 *            String to display
	 */
	public void displayLine(String text) {
		if (text != null)
			System.out.println(truncate(text));

	} // method displayLine

	/**
	 * Get input from the user
	 * 
	 * Modification: **TAW & JMC - fixed newline after retry (pa2) - added check
	 * for null errorMessage
	 * 
	 * @param true if the entry is required, false otherwise
	 * @param the
	 *            error message to print
	 * 
	 * @return the String entered by the user
	 */
	public String getInput(boolean required, String errorMessage) {
		boolean isValid = true;
		String input = null;

		do {
			try {
				input = reader.readLine();

				// TAW & JMC - fixed to add NL after re-entry
				if (!isValid)
					displayLine();

				if (required) {
					if (input.length() == 0) {
						isValid = false;
						displayError(errorMessage);

					} // end if
					else
						isValid = true;

				} // end if

			} // end try

			catch (IOException e) { /* do nothing */
			}

		} while (!isValid);

		return input;

	} // method getInput

	/**
	 * Get a number from the user between the lower and upper bounds (inclusive)
	 * 
	 * **TAW & JMC (PA2) - added required flag to support delete/edit
	 * 
	 * @param lower
	 * @param upper
	 * @return a valid number
	 */
	public int getNumber(boolean required, int lower, int upper) {
		boolean isValid = true;

		int number = 0; // default value
		String str;

		do {
			str = getInput(false, "");

			if (isValidInt(required, str, lower, upper)) {
				// don't bother if not required and nothing here (for track)
				if (required || str.length() > 0)
					number = Integer.parseInt(str);

				isValid = true;

			} // end if

			else {
				displayError("Must be a number between " + lower + " and "
						+ upper);
				isValid = false;

			} // end else

		} while (!isValid);

		return number;

	} // method getNumber

	/**
	 * Get a number from the user between the lower and upper bounds (inclusive)
	 * - overloaded version for long values
	 * 
	 * **TAW & JMC (PA3) - added for PA3
	 * 
	 * @param lower
	 * @param upper
	 * @return a valid number
	 */
	public long getNumber(boolean required, long lower, long upper) {
		boolean isValid = true;

		long number = 0L; // default value
		String str;

		do {
			str = getInput(false, "");

			if (isValidLong(required, str, lower, upper)) {
				// don't bother if not required and nothing here (for track)
				if (required || str.length() > 0)
					number = Long.parseLong(str);

				isValid = true;

			} // end if

			else {
				displayError("Must be a number between " + lower + " and "
						+ upper);
				isValid = false;

			} // end else

		} while (!isValid);

		return number;

	} // method getNumber

	/**
	 * Optionally print a message and wait for the user to press enter
	 * 
	 * **TAW & JMC (PA2) - modified to account for null msg
	 * 
	 * @param the
	 *            message to print
	 */
	public void pause(String msg) {
		displayLine();
		displayLine();

		if (msg != null && msg.length() > 0)
			displayLine(msg);

		display("Press <ENTER> to continue . . .");

		getInput(false, ""); // don't need to capture this input

	} // method pause

	/**
	 * Show that the choice is not currently available
	 */
	public void showUnavailable(String title) {
		clearScreen();
		centerText(title);
		pause("This function is not currently available");

	} // method showUnavailable

	/************************* private methods ****************************/

	/**
	 * This method ensures that the input String will evaluate to an int between
	 * the lower & upper bounds (inclusive)
	 * 
	 * **TAW & JMC (PA2) - added required flag to support delete/edit
	 * 
	 * @param input
	 *            string
	 * @return true if the value is between 1 and 99 (inclusive)
	 */
	private boolean isValidInt(boolean required, String input, int lower,
			int upper) {
		boolean isValid = true;
		int test;

		// don't bother if not required and nothing here (for track)
		if (required || input.length() > 0)
			try {
				test = Integer.parseInt(input);

				if (test < lower || test > upper)
					throw new NumberFormatException();

			} // end try

			catch (NumberFormatException e) {
				isValid = false;

			} // end catch

		return isValid;

	} // method isValidInt

	/**
	 * This method ensures that the input String will evaluate to an int between
	 * the lower & upper bounds (inclusive)
	 * 
	 * **TAW & JMC (PA2) - added required flag to support delete/edit
	 * 
	 * @param input
	 *            string
	 * @return true if the value is between 1 and 99 (inclusive)
	 */
	private boolean isValidLong(boolean required, String input, long lower,
			long upper) {
		boolean isValid = true;
		long test;

		// don't bother if not required and nothing here (for track)
		if (required || input.length() > 0)
			try {
				test = Long.parseLong(input);

				if (test < lower || test > upper)
					throw new NumberFormatException();

			} // end try

			catch (NumberFormatException e) {
				isValid = false;

			} // end catch

		return isValid;

	} // method isValidInt

	/**
	 * Truncate a line if it is over 80 characters in length
	 * 
	 * @param the
	 *            string to truncate
	 * @return the truncated string
	 */
	private String truncate(String string) {
		if (string.length() > 80)
			string = string.substring(0, 80);

		return string;

	} // method truncate

} // class AudioView
