package test;

import static org.junit.Assert.*;

import java.io.*;
import org.junit.*;

import view.file.*;

/**
 * Test the methods of the AudioFileIO class
 * 
 * Modifications: **TAW & JMC: PA2 (2/14/2013) - New for PA2
 * 
 * @author Thomas Wray & Joe Cumins
 * @version PA2 (2/15/2013)
 */
public class AudioFileIOTest {

	/**
	 * Remove the test file if it exists
	 * 
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		File file = new File("Test");

		if (file.exists())
			file.delete();

		file = null;

	} // method setup

	/**
	 * Test method for {@link AudioFileIO#FileIO(java.lang.String, int)} - test
	 * constructor to
	 */
	@Test
	public void testFileIO() {
		AudioFileIO file = new AudioFileIO("Test", AudioFileIO.READING);
		assertFalse(file.exists());
		file.close();

		file = new AudioFileIO("Test", AudioFileIO.WRITING);
		file.write("xyz");
		file.close();

		file = new AudioFileIO("Test", AudioFileIO.READING);
		assertTrue(file.exists());

	} // method testFile

	/**
	 * Test method for {@link AudioFileIO#close()}.
	 */
	@Test
	public void testClose() {
		AudioFileIO file = new AudioFileIO("Test", AudioFileIO.WRITING);
		file.write("xyz");

		assertTrue(file.length() == 0L);

		file.close();
		assertTrue(file.length() == 4L);

	} // method testClose

	/**
	 * Test method for {@link AudioFileIO#exists()}.
	 */
	@Test
	public void testExists() {
		AudioFileIO file = new AudioFileIO("Test", AudioFileIO.READING);
		assertFalse(file.exists());
		file.close();

		file = new AudioFileIO("Test", AudioFileIO.WRITING);
		assertTrue(file.exists());
		file.close();

	} // method testExists

	/**
	 * Test method for {@link AudioFileIO#getFileName()}.
	 */
	@Test
	public void testGetFileName() {
		AudioFileIO file = new AudioFileIO("Test", AudioFileIO.WRITING);
		assertTrue(file.getFileName().equals("Test"));

	} // method testGetFileName

	/**
	 * Test method for {@link AudioFileIO#getMode()}.
	 */
	@Test
	public void testGetMode() {
		AudioFileIO file1 = new AudioFileIO("Test", AudioFileIO.READING);
		AudioFileIO file2 = new AudioFileIO("Test", AudioFileIO.WRITING);

		assertTrue(file1.getMode() == AudioFileIO.READING);
		assertTrue(file2.getMode() == AudioFileIO.WRITING);

	} // method testGetMode

	/**
	 * Test method for {@link AudioFileIO#open(java.lang.String, int)}.
	 */
	@Test
	public void testOpen() {
		AudioFileIO file = new AudioFileIO();
		file.open("Test", AudioFileIO.WRITING);
		assertTrue(file.exists());

	} // method testOpen

	/**
	 * Test method for {@link AudioFileIO#readLine()}.
	 */
	@Test
	public void testReadLine() {
		AudioFileIO file = new AudioFileIO("Test", AudioFileIO.WRITING);
		String line;

		file.write("xyz");
		file.close();

		file = new AudioFileIO("Test", AudioFileIO.READING);
		line = file.readLine();

		assertTrue(line.equals("xyz"));

	} // method testReadLine

	/**
	 * Test method for {@link AudioFileIO#write(java.lang.String)}.
	 */
	@Test
	public void testWrite() {
		testReadLine(); // already did this so call it again to be safe

	} // method testWrite

} // class AudioFileIO
