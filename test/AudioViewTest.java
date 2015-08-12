package test;

import static org.junit.Assert.*;

import org.junit.*;
import java.io.*;
import view.text.*;

/**
 * Test the methods of the AudioView class
 * 
 * Modifications: **TAW & JMC (PA2) - modified testGetInputEmptyRequired to
 * reflect fix in AudioView - filled in testUnavailable to ensure full coverage
 * 
 * @author Thomas Wray & Joe Cumins
 * @version PA2 (2/15/2013), PA1 (1/25/2013)
 */
public class AudioViewTest {

	private ByteArrayOutputStream out = new ByteArrayOutputStream();
	private ByteArrayOutputStream err = new ByteArrayOutputStream();

	/**
	 * Setup the environment - redirect the stdin & stderr streams so that we
	 * can capture it for testing
	 * 
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		System.setOut(new PrintStream(out));
		System.setErr(new PrintStream(err));

	} // method setup (before tests)

	/**
	 * Tear down the environment - reset the stdin & stderr streams
	 * 
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
		System.setOut(null);
		System.setErr(null);

	} // method tearDown (after tests)

	/**
	 * Test method for {@link AudioView#centerText(java.lang.String)} - Test
	 * empty input
	 */
	@Test
	public void testCenterTextEmpty() {
		AudioView view = new AudioView();
		view.centerText("");

		assertTrue(out.toString().length() == 0);

	} // method testCenterTextEmpty

	/**
	 * Test method for {@link AudioView#centerText(java.lang.String)} - Test
	 * good input
	 */
	@Test
	public void testCenterTextGood() {
		AudioView view = new AudioView();
		view.centerText("Hello");

		// one greater due to \n
		assertTrue(out.toString().length() == 43);

	} // method testCenterTextGood

	/**
	 * Test method for {@link AudioView#centerText(java.lang.String)} - Test
	 * long input (over 80 characters)
	 */
	@Test
	public void testCenterTextLong() {
		AudioView view = new AudioView();
		view.centerText(getLongText());

		// one greater due to \nl
		assertTrue(out.toString().length() == 81);

	} // method testCenterTextLong

	/**
	 * Test method for {@link AudioView#centerText(java.lang.String)} - Test
	 * null input
	 */
	@Test
	public void testCenterTextNull() {
		AudioView view = new AudioView();
		view.centerText(null);

		assertTrue(out.toString().length() == 0);

	} // method testCenterTextNull

	/**
	 * Test method for {@link AudioView#clearScreen()} - test clear screen
	 */
	@Test
	public void testClearScreen() {
		AudioView view = new AudioView();
		view.clearScreen();

		assertTrue(out.toString().length() == 25);
		for (int i = 0; i < 25; i++)
			assertTrue(out.toString().substring(i, i + 1).equals("\n"));

	} // method testClearScreen

	/**
	 * Test method for {@link AudioView#display(java.lang.String)} - test
	 * display with an empty message
	 */
	@Test
	public void testDisplayEmpty() {
		AudioView view = new AudioView();
		view.display("");

		assertTrue(out.toString().length() == 0);

	} // method testDisplayEmpty

	/**
	 * Test method for {@link AudioView#display(java.lang.String)} - test the
	 * display method with a string < 80 characters
	 */
	@Test
	public void testDisplayGood() {
		AudioView view = new AudioView();
		view.display("Hello");

		assertTrue(out.toString().equals("Hello")
				&& out.toString().length() == 5);

	} // method testDisplayGood

	/**
	 * Test method for {@link AudioView#display(java.lang.String)} - test the
	 * display method with a string > 80 characters
	 */
	@Test
	public void testDisplayLong() {
		AudioView view = new AudioView();
		view.display(getLongText());

		assertTrue(out.toString().length() == 80);

	} // method testDisplayLong

	/**
	 * Test method for {@link AudioView#display(java.lang.String)} - test
	 * display with null input
	 */
	@Test
	public void testDisplayNull() {
		AudioView view = new AudioView();
		view.display(null);

		assertTrue(out.toString().length() == 0);

	} // testDisplayNull

	/**
	 * Test method for {@link AudioView#displayError(java.lang.String)} - test
	 * displayError with an empty message
	 */
	@Test
	public void testDisplayErrorEmpty() {
		AudioView view = new AudioView();
		view.displayError("");

		assertTrue(out.toString().equals("\n\nPlease re-enter -> "));

	} // method testDisplayErrorEmpty

	/**
	 * Test method for {@link AudioView#displayError(java.lang.String)} - test
	 * displayError with a message < 80 characters
	 */
	@Test
	public void testDisplayErrorGood() {
		AudioView view = new AudioView();
		view.displayError("Hello");

		assertTrue(out.toString().equals("\nHello\nPlease re-enter -> "));

	} // method testDisplayErrorGood

	/**
	 * Test method for {@link AudioView#displayError(java.lang.String)} - test
	 * displayError with a message that is > 80 characters
	 */
	@Test
	public void testDisplayErrorLong() {
		AudioView view = new AudioView();
		view.displayError(getLongText());

		assertTrue(out.toString().length() == 101);

	} // method testDisplayErrorLong

	/**
	 * Test method for {@link AudioView#displayError(java.lang.String)} - test
	 * displayError with a null message
	 */
	@Test
	public void testDisplayErrorNull() {
		AudioView view = new AudioView();
		view.displayError(null);

		assertTrue(out.toString().equals("\nPlease re-enter -> "));

	} // method testDisplayErrorNull

	/**
	 * Test method for {@link AudioView#displayLine()} - test displayLine with
	 * no parameter
	 */
	@Test
	public void testDisplayLine() {
		AudioView view = new AudioView();
		view.displayLine();

		assertTrue(out.toString().length() == 1 && out.toString().equals("\n"));

	} // method testDisplayLine

	/**
	 * Test method for {@link AudioView#displayLine(java.lang.String)} - test
	 * displayLine with an empty string
	 */
	@Test
	public void testDisplayLineStringEmpty() {
		AudioView view = new AudioView();
		view.displayLine("");

		assertTrue(out.toString().equals("\n"));

	} // method testDisplayLineEmpty

	/**
	 * Test method for {@link AudioView#displayLine(java.lang.String)} - test
	 * displayLine with a string that is < 80 characters
	 */
	@Test
	public void testDisplayLineStringGood() {
		AudioView view = new AudioView();
		view.displayLine("Hello");

		assertTrue(out.toString().equals("Hello\n"));

	} // method testDisplayLineGood

	/**
	 * Test method for {@link AudioView#displayLine(java.lang.String)} - test
	 * displayLine with a string that is > 80 characters
	 */
	@Test
	public void testDisplayLineLong() {
		AudioView view = new AudioView();
		view.displayLine(getLongText());

		assertTrue(out.toString().length() == 81);

	} // testDisplayLineLong

	/**
	 * Test method for {@link AudioView#displayLine(java.lang.String)} - test
	 * displayLine with null input
	 */
	@Test
	public void testDisplayLineNull() {
		AudioView view = new AudioView();
		view.displayLine(null);

		assertTrue(out.toString().length() == 0);

	} // testDisplayLineNull

	/**
	 * Test method for {@link AudioView#getInput(boolean, java.lang.String)} -
	 * test getInput with the required flag not set & empty input
	 */
	@Test
	public void testGetInputEmptyOptional() {
		String input = "\n";
		System.setIn(new ByteArrayInputStream(input.getBytes()));

		AudioView view = new AudioView();
		String output = null;

		output = view.getInput(false, "");

		assertTrue(out.toString().length() == 0);
		assertTrue(output.length() == 0);

	} // testGetInputEmptyOptional

	/**
	 * Test method for {@link AudioView#getInput(boolean, java.lang.String)} -
	 * test getInput with the required flag set & message & empty input
	 * 
	 * Modification: **TAW & JMC (pa02) - added NL to end of test string to
	 * reflect change in AudioView.getInput()
	 */
	@Test
	public void testGetInputEmptyRequired() {
		AudioView view;

		String input = "\nOk\n";
		String output = null;

		// redirect input to method
		System.setIn(new ByteArrayInputStream(input.getBytes()));

		view = new AudioView();

		output = view.getInput(true, "Oops");

		assertTrue(out.toString().equals("\nOops\nPlease re-enter -> \n"));
		assertTrue(output.equals("Ok"));

	} // method testGetInputEmptyRequired

	/**
	 * Test method for {@link AudioView#getNumber(int, int)} - test getNumber
	 * with empty input
	 */
	@Test
	public void testGetNumberEmptyNotRequired() {
		AudioView view;

		String input = "" + " \n12\n";

		int output = 0;

		// redirect input to method
		System.setIn(new ByteArrayInputStream(input.getBytes()));

		view = new AudioView();

		output = view.getNumber(false, 1, 99);

		assertTrue(out.toString().equals(
				"\nMust be a number between 1 and 99\nPlease re-enter -> "));
		assertTrue(output == 12);

	} // method testGetNumberEmpty

	/**
	 * Test method for {@link AudioView#getNumber(int, int)} - test getNumber
	 * with valid input
	 */
	@Test
	public void testGetNumberGood() {
		AudioView view;

		String input = "12\n";
		int output = 0;

		// redirect input to method
		System.setIn(new ByteArrayInputStream(input.getBytes()));

		view = new AudioView();

		output = view.getNumber(false, 1, 99);

		// assertTrue( out.toString().equals(
		// "\nMust be a number between 1 and 99\nPlease re-enter -> " ) );
		assertTrue(output == 12);

	} // method testGetNumberGood

	/**
	 * Test method for {@link AudioView#getNumber(int, int)} - test getNumber
	 * with a number out of bounds
	 */
	@Test
	public void testGetNumberOutOfBounds() {
		AudioView view;

		String input = "101\n12\n";

		int output = 0;

		// redirect input to method
		System.setIn(new ByteArrayInputStream(input.getBytes()));

		view = new AudioView();

		output = view.getNumber(false, 1, 99);

		assertTrue(out.toString().equals(
				"\nMust be a number between 1 and 99\nPlease re-enter -> "));
		assertTrue(output == 12);

	} // method testGetNumberOutOfBounds

	/**
	 * Test method for {@link AudioView#getNumber(int, int)} - test getNumber
	 * with empty input
	 */
	@Test
	public void testGetNumberEmptyRequired() {
		AudioView view;

		String input = "" + " \n12\n";

		int output = 0;

		// redirect input to method
		System.setIn(new ByteArrayInputStream(input.getBytes()));

		view = new AudioView();

		output = view.getNumber(true, 1, 99);

		assertTrue(out.toString().equals(
				"\nMust be a number between 1 and 99\nPlease re-enter -> "));
		assertTrue(output == 12);

	} // method testGetNumberEmpty

	/**
	 * Test method for {@link AudioView#getNumber(int, int)} - test the
	 * getNumber method when a string is entered
	 */
	@Test
	public void testGetNumberString() {
		AudioView view;

		String input = "Hello\n12\n";

		int output = 0;

		// redirect input to method
		System.setIn(new ByteArrayInputStream(input.getBytes()));

		view = new AudioView();

		output = view.getNumber(true, 1, 99);

		assertTrue(out.toString().equals(
				"\nMust be a number between 1 and 99\nPlease re-enter -> "));
		assertTrue(output == 12);

	} // method testGetNumberString

	/**
	 * Test method for {@link AudioView#pause(java.lang.String)} - test the
	 * pause function
	 */
	@Test
	public void testPause() {
		AudioView view;

		String input = "\n";

		// redirect input to method
		System.setIn(new ByteArrayInputStream(input.getBytes()));

		view = new AudioView();
		view.pause("Hello");

		assertTrue(out.toString().equals(
				"\n\nHello\nPress <ENTER> to continue . . ."));

	} // method testPause

	/**
	 * Test method for {@link AudioView#pause(java.lang.String)} - test the
	 * pause function
	 * 
	 * **TAW & JMC (PA2) - new for PA2
	 */
	@Test
	public void testPauseEmpty() {
		AudioView view;

		String input = "\n";

		// redirect input to method
		System.setIn(new ByteArrayInputStream(input.getBytes()));

		view = new AudioView();
		view.pause("");

		assertTrue(out.toString().equals("\n\nPress <ENTER> to continue . . ."));

	} // method testPause

	/**
	 * Test method for {@link AudioView#pause(java.lang.String)} - test the
	 * pause function
	 * 
	 * **TAW & JMC (PA2) - new for PA2
	 */
	@Test
	public void testPauseNull() {
		AudioView view;

		String input = "\n";

		// redirect input to method
		System.setIn(new ByteArrayInputStream(input.getBytes()));

		view = new AudioView();
		view.pause(null);

		assertTrue(out.toString().equals("\n\nPress <ENTER> to continue . . ."));

	} // method testPause

	/**
	 * Test method for {@link AudioView#showUnavailable(String)} - test the
	 * showUnavailable method
	 * 
	 * **TAW & JMC (PA2) - completed to make ensure full coverage, even though
	 * this is unnecessary.
	 */
	@Test
	public void testShowUnavailable() throws Exception {
		AudioView view = new AudioView();
		view.showUnavailable("Hello");

		for (int i = 0; i < 25; i++)
			assertTrue(out.toString().substring(i, i + 1).equals("\n"));

		assertTrue(out.toString().substring(25, 68).trim().equals("Hello"));
		assertTrue(out
				.toString()
				.substring(68)
				.trim()
				.equals("This function is not currently available\n"
						+ "Press <ENTER> to continue . . ."));

	} // method testShowUnavailable

	/************************ private methods *****************************/

	/**
	 * Return a long text for testing
	 * 
	 * @return a long text
	 */
	private String getLongText() {
		return "abcdefghijklmnopqrstuvwxyz" + "abcdefghijklmnopqrstuvwxyz"
				+ "abcdefghijklmnopqrstuvwxyz" + "abcdefghijklmnopqrstuvwxyz";

	} // method getLongText

} // class AudioViewTest
