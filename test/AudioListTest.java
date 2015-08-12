package test;

import static org.junit.Assert.*;

import org.junit.*;
import model.*;

/**
 * AudioListTest - test the AudioList program
 * 
 * Modifications: PA2 (2/14/2013) - added methods to test difference,
 * intersection, and union - added method to test unique property of add - added
 * method to test ordering - modified testRemove to add unique items
 * 
 * @author Thomas Wray & Joe Cumins
 * @version PA2 (2/14/2013), PA1 (1/24/2013)
 */
public class AudioListTest {
	/**
	 * Test method for {@link AudioList#AudioList()} - test the constructor
	 */
	@Test
	public void testAudioList() {

		AudioList list = new AudioList();
		assertTrue(list.size() == 0);

	} // method testAudioList

	/**
	 * Test method for {@link AudioList#add(AudioFile)} - test adding a single
	 * AudioFile
	 * 
	 * **TAW & JMC (PA3) - specified mp3 or wav file
	 */
	@Test
	public void testAddOne() {
		AudioList list = new AudioList();

		list.add(new MP3File("abc", "def", "ghi", 0, "location1", "", 192));
		assertTrue(list.size() == 1);
		assertNotNull(list.get(0));

	} // method testAddOne

	/**
	 * Test method for {@link AudioList#add(AudioFile)} - test adding a single
	 * AudioFile
	 * 
	 * **TAW & JMC (PA3) - specified mp3 or wav file
	 */
	@Test
	public void testAddNull() {
		AudioList list = new AudioList();

		list.add(new MP3File("abc", "def", "ghi", 0, "location1", "", 192));
		assertTrue(list.size() == 1);
		list.add(null);
		assertTrue(list.size() == 1);

	} // method testAddNull

	/**
	 * Test method for {@link AudioList#clear} - test clearing the AudioList
	 * 
	 * **TAW & JMC (PA2) - new for PA2 **TAW & JMC (PA3) - specified mp3 or wav
	 * file
	 */
	@Test
	public void testClear() {
		AudioList list = new AudioList();

		list.add(new MP3File("abc", "def", "ghi", 0, "location1", "", 192));
		assertTrue(list.size() == 1);
		list.clear();
		assertTrue(list.size() == 0);

	} // method testClear

	/**
	 * Test method for {@link AudioList#difference(AudioList)}
	 * 
	 * **TAW & JMC (PA2) - new for PA2 **TAW & JMC (PA3) - specified mp3 or wav
	 * file
	 */
	@Test
	public void testDifference() {
		AudioFile test1 = new MP3File("abc", "def", "ghi", 0, "location1", "",
				192);
		AudioFile test2 = new WavFile("lmn", "opq", "", 0, "location2", 1234);
		AudioFile test3 = new WavFile("xyz", "abc", "", 0, "location3", 5678);

		AudioList difference;
		AudioList list1 = new AudioList();
		AudioList list2 = new AudioList();

		list1.add(test1);
		list1.add(test2);

		list2.add(test1);
		list2.add(test3);

		difference = list1.difference(list2);

		assertTrue(difference.size() == 1);
		assertTrue(difference.get(0).equals(test2));

	} // method testDifference

	/**
	 * Test method for {@link AudioList#getShortTitle}
	 * 
	 * **TAW & JMC (PA2) - new for PA2 **TAW & JMC (PA3) - specified mp3 or wav
	 * file
	 */

	@Test
	public void testGetShortTitle() {
		AudioFile test1 = new WavFile("lmn", "opq", "rst", 6, "location2",
				12345);
		AudioFile test2 = new MP3File("abc", "def", "", 0, "location1", "", 192);

		AudioList list = new AudioList();
		list.add(test1);
		list.add(test2);

		assertTrue(list.getShortTitle(0).equals("abc, def"));
		assertTrue(list.getShortTitle(1).equals("lmn, opq (rst)"));
		assertTrue(list.getShortTitle(2).length() == 0);

	} // method testGetShortTitle

	/**
	 * Test method for {@link AudioList#intersection(AudioList)}
	 * 
	 * **TAW & JMC (PA2) - new for PA2 **TAW & JMC (PA3) - specified mp3 or wav
	 * file
	 */
	@Test
	public void testIntersection() {
		AudioFile test1 = new MP3File("abc", "def", "ghi", 0, "location1", "",
				192);
		AudioFile test2 = new WavFile("lmn", "opq", "", 0, "location2", 1234);
		AudioFile test3 = new WavFile("xyz", "abc", "", 0, "location3", 5678);

		AudioList intersection;
		AudioList list1 = new AudioList();
		AudioList list2 = new AudioList();

		list1.add(test1);
		list1.add(test2);

		list2.add(test1);
		list2.add(test3);

		intersection = list1.intersection(list2);

		assertTrue(intersection.size() == 1);
		assertTrue(intersection.get(0).equals(test1));

	} // method testDifference

	/**
	 * Test method for {@link AudioList#add(AudioFile)} - test to ensure
	 * AudioFiles are being added in order - order alpha by artist, album,
	 * track, title - empty artist/track at end of grouping
	 * 
	 * **TAW & JMC (PA2) - added for PA2 **TAW & JMC (PA3) - specified mp3 or
	 * wav file
	 */
	@Test
	public void testOrdering() {
		AudioFile test1 = new WavFile("abc", "def", "abc", 1, "location1",
				12345);
		AudioFile test2 = new WavFile("abc", "def", "abc", 2, "location2",
				12345);
		AudioFile test3 = new WavFile("abc", "def", "", 0, "location3", 12345);
		AudioFile test4 = new WavFile("lmn", "opq", "lmn", 2, "location4",
				12345);
		AudioFile test5 = new WavFile("lmn", "opq", "", 0, "location5", 12345);
		AudioFile test6 = new WavFile("xyz", "abc", "", 0, "location6", 12345);

		AudioList list = new AudioList();

		list.add(test3); // doesn't matter where this goes
		list.add(test1); // should go first
		list.add(test5); // should go last
		list.add(test2); // should go second
		list.add(test4); // should go fourth
		list.add(test6); // should go last

		System.out.println(list.size()); // *****************

		assertTrue(list.size() == 6);
		assertTrue(list.get(0).equals(test1));
		assertTrue(list.get(1).equals(test2));
		assertTrue(list.get(2).equals(test3));
		assertTrue(list.get(3).equals(test4));
		assertTrue(list.get(4).equals(test5));
		assertTrue(list.get(5).equals(test6));

	} // method testOrdering

	/**
	 * Test method for {@link AudioList#remove(int)} - test the remove
	 * functionality by adding 33 AudioFiles & removing 1
	 * 
	 * **TAW & JMC (PA2) - modified to add unique items - delete from middle
	 * **TAW & JMC (PA3) - specified mp3 or wav file
	 */
	@Test
	public void testRemove() {
		AudioList list = new AudioList();

		for (int i = 0; i < 33; i++)
			list.add(new WavFile(i + "", i + "", "", 0, i + "", 192));

		assertTrue(list.size() == 33);
		assertNotNull(list.get(32));

		list.remove(16);
		assertTrue(list.size() == 32);
		assertNull(list.get(32));

	} // method testRemove

	/**
	 * Test method for {@link AudioList#remove(int)} - test the remove
	 * functionality by adding 33 AudioFiles attempting to remove out of bounds
	 * position
	 * 
	 * **TAW & JMC (PA2) - new for PA2 **TAW & JMC (PA3) - specified mp3 or wav
	 * file
	 */
	@Test
	public void testRemoveFile() {
		AudioList list = new AudioList();

		for (int i = 0; i < 33; i++)
			list.add(new WavFile(i + "", i + "", "", 0, i + "", 192));

		assertTrue(list.size() == 33);
		assertNotNull(list.get(32));

		assertTrue(list.remove(list.get(16)));
		assertNull(list.get(32));

	} // method testRemove

	/**
	 * Test method for {@link AudioList#remove(int)} - test the remove
	 * functionality by adding 33 AudioFiles attempting to remove out of bounds
	 * position
	 * 
	 * **TAW & JMC (PA2) - new for PA2 **TAW & JMC (PA3) - specified mp3 or wav
	 * file
	 */
	@Test
	public void testRemoveFileNull() {
		AudioList list = new AudioList();

		for (int i = 0; i < 33; i++)
			list.add(new WavFile(i + "", i + "", "", 0, i + "", 192));

		assertTrue(list.size() == 33);
		assertNotNull(list.get(32));

		assertFalse(list.remove(null));
		assertNotNull(list.get(32));

	} // method testRemove

	/**
	 * Test method for {@link AudioList#remove(int)} - test the remove
	 * functionality by adding 33 AudioFiles attempting to remove out of bounds
	 * position
	 * 
	 * **TAW & JMC (PA2) - new for PA2 **TAW & JMC (PA3) - specified mp3 or wav
	 * file
	 */
	@Test
	public void testRemoveOutOfBounds() {
		AudioList list = new AudioList();

		for (int i = 0; i < 33; i++)
			list.add(new WavFile(i + "", i + "", "", 0, i + "", 192));

		assertTrue(list.size() == 33);
		assertNotNull(list.get(32));

		assertFalse(list.remove(list.size()));
		assertNotNull(list.get(32));

	} // method testRemove

	/**
	 * Test method for {@link AudioList#union(AudioList)}
	 * 
	 * **TAW & JMC (PA2) - new for PA2 **TAW & JMC (PA3) - specified mp3 or wav
	 * file
	 */
	@Test
	public void testUnion() {
		AudioFile test1 = new MP3File("abc", "def", "ghi", 0, "location1", "",
				192);
		AudioFile test2 = new WavFile("lmn", "opq", "", 0, "location2", 1234);
		AudioFile test3 = new WavFile("xyz", "abc", "", 0, "location3", 5678);

		AudioList list1 = new AudioList();
		AudioList list2 = new AudioList();
		AudioList union;

		list1.add(test1);
		list1.add(test2);

		list2.add(test1);
		list2.add(test3);

		union = list1.union(list2);

		assertTrue(union.size() == 3);
		assertTrue(union.get(0).equals(test1));
		assertTrue(union.get(1).equals(test2));
		assertTrue(union.get(2).equals(test3));

	} // method testDifference

	/**
	 * Test method for {@link AudioList#add(AudioFile) - test the uniqueness
	 * property of the AudioList - now conceptualized as a set
	 * 
	 * **TAW & JMC (PA2) - added for PA2 **TAW & JMC (PA3) - specified mp3 or
	 * wav file
	 */
	@Test
	public void testUnique() {
		AudioList list = new AudioList();

		AudioFile file1 = new MP3File("abc", "def", "ghi", 0, "location1", "",
				192);
		AudioFile file2 = new MP3File("abc", "def", "ghi", 0, "location1", "",
				192);

		list.add(file1);
		list.add(file2);

		assertTrue(list.size() == 1);

	} // method testUnique

}
