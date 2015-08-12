package test;

import static org.junit.Assert.*;

import java.io.*;
import org.junit.*;

import controller.file.*;
import model.*;
import view.text.*;
import view.file.*;

/**
 * Test the methods of the AudioFileControl class
 * 
 * Modifications: **TAW & JMC: PA2 (2/14/2013) - New for PA2 **TAW & JMC: PA3
 * (3/20/2013) - Modified to handle MP3Files and WavFiles
 * 
 * @author Thomas Wray & Joe Cumins
 * @version PA2 (2/15/2013)
 */
public class AudioFileControlTest {
	private AudioList list = new AudioList();
	private AudioView view = new AudioView();

	/**
	 * Test method for {@link AudioFileControl#readFile()}.
	 * 
	 * **TAW & JMC (PA3): Modified to account for MP3s & Wavs
	 */
	@Test
	public void testReadFile() {

		AudioFileControl fileController;
		AudioFileIO file;
		AudioFileIO malformed;

		// create the Songlist.txt file with good and malformed records
		String line1 = "1|abc|def| | |location1|capital|192|";
		String line2 = "1| |def|ghi|1|location2|origin|192|";
		String line3 = "2|ghi|jkl|mno| |location3|20345|";
		String line4 = "2|abc|def| |location4|12345|";

		file = new AudioFileIO("Songlist.txt", AudioFileIO.WRITING);

		file.write(line1);
		file.write(line2);
		file.write(line3);
		file.write(line4);

		file.close();

		// read the file & populate the list
		fileController = new AudioFileControl(list);
		fileController.readFile();

		malformed = new AudioFileIO("malformed.err", AudioFileIO.READING);

		// check what got written to the AudioList
		assertTrue(list.size() == 2);
		assertTrue(list.get(0).equals(
				new MP3File("abc", "def", "", 0, "location1", "capital", 192)));
		assertTrue(list.get(1).equals(
				new WavFile("ghi", "jkl", "mno", 0, "location3", 20345)));

		// check what got written to the malformed.err file
		assertTrue(malformed.exists());
		assertTrue(malformed.readLine().equals(line2));
		assertTrue(malformed.readLine().equals(line4));

	} // method testReadFile

	/**
	 * Test method for {@link AudioFileControl#writeFile()}.
	 */
	@Test
	public void testWriteFile() {
		testReadFile(); // setup the AudioList

		File file;
		AudioFileControl control = new AudioFileControl(list);
		AudioFileIO test;

		// write the AudioList back to the file
		control.writeFile();
		file = new File("Songlist.txt");
		test = new AudioFileIO("Songlist.txt", AudioFileIO.READING);

		// check that the file exists and is populated correctly
		assertTrue(file.exists());
		assertTrue(test.readLine().equals(
				"1|abc|def| | |location1|capital|192|"));
		assertTrue(test.readLine().equals("2|ghi|jkl|mno| |location3|20345|"));
		assertTrue(test.readLine() == null);

	} // method testWriteFile

} // class AudioFileControlTest
