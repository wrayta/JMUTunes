package test;

import static org.junit.Assert.*;

import org.junit.*;

import model.*;

/**
 * Test class for AudioFile
 * 
 * Modifications: PA2 (2/14/2013) - added methods to test getSortString() -
 * added methods to test setTrack(String)
 * 
 * @author Thomas Wray & Joe Cumins
 * @version PA2 (2/14/2013), PA1 (1/24/2013)
 * 
 */
public class AudioFileTest {
	/**
	 * Test method for {@link AudioFile#AudioFile()} - test the default
	 * constructor
	 */
	@Test
	public void testMP3File() {
		MP3File file = new MP3File();
		assertTrue(file.getAlbum().length() == 0);
		assertTrue(file.getArtist().length() == 0);
		assertTrue(file.getTitle().length() == 0);
		assertTrue(file.getTrack() == 0);
		assertTrue(file.getPathName().length() == 0);
		assertTrue(file.getLabel().length() == 0);
		assertTrue(file.getBitRate() == 0L);

	} // method testMP3File

	/**
	 * Test method for {@link AudioFile#AudioFile()} - test the default
	 * constructor
	 */
	@Test
	public void testWavFile() {
		WavFile file = new WavFile();
		assertTrue(file.getAlbum().length() == 0);
		assertTrue(file.getArtist().length() == 0);
		assertTrue(file.getTitle().length() == 0);
		assertTrue(file.getTrack() == 0);
		assertTrue(file.getPathName().length() == 0);
		assertTrue(file.getLength() == 0L);

	} // method testWavFile

	/**
	 * Test method for
	 * {@link AudioFile#AudioFile(java.lang.String, java.lang.String, java.lang.String, int)}
	 * - Test explicit value constructor with null & invalid data
	 */
	@Test
	public void testAudioFileExplicitBad() {
		WavFile file = new WavFile(null, null, null, 100, null, 0);
		assertNotNull(file.getAlbum());
		assertTrue(file.getAlbum().length() == 0);
		assertNotNull(file.getArtist());
		assertTrue(file.getArtist().equals("No Artist"));
		assertNotNull(file.getTitle());
		assertTrue(file.getTitle().equals("No Title"));
		assertTrue(file.getTrack() == 0);
		assertTrue(file.getPathName().equals("No Pathname"));
		assertTrue(file.getLength() == 0L);
	} // method testAudioFileExplicitBad

	/**
	 * Test method for {@link AudioFile#getSortString()} - test sort string for
	 * normal AudioFile with album
	 */
	@Test
	public void testGetSortStringNormalWithAlbum() {
		char delim = (char) 0;
		MP3File file = new MP3File("abc", "def", "efg", 9, "hij", "lmn", 1);
		String expected = "abc" + delim + "efg" + delim + "09" + delim + "def";
		assertTrue(file.getSortString().equals(expected));

	} // method testGetSortStringNormalWithAlbum

	/**
	 * Test method for {@link AudioFile#getSortString()} - test sort string for
	 * normal AudioFile without album
	 */
	@Test
	public void testGetSortStringNormalWithoutAlbum() {
		char delim = (char) 0;
		WavFile file = new WavFile("abc", "def", null, 0, "lmn", 1);
		String expected = "abc" + delim + "~~~~~~~~~~~~" + delim + "~~" + delim
				+ "def";
		System.out.println(file.getSortString());
		assertTrue(file.getSortString().equals(expected));

	} // method testGetSortStringNormalWithoutAlbum()

	/**
	 * Test method for {@link AudioFile#getSortString()} - test sort string for
	 * abnormal AudioFile with album
	 */
	@Test
	public void testGetSortStringAbnormalWithAlbum() {
		char delim = (char) 0;
		WavFile file = new WavFile(null, null, "abc", 0, "xyz", 3);
		String expected = "no artist" + delim + "abc" + delim + "~~" + delim
				+ "no title";
		System.out.println(file.getSortString());
		assertTrue(file.getSortString().equals(expected));

	} // method testGetSortStringAbnormalWithAlbum

	/**
	 * Test method for
	 * {@link AudioFile#AudioFile(java.lang.String, java.lang.String, java.lang.String, int)}
	 * . -- test the explicit vvalue constructor
	 */
	@Test
	public void testMP3FileExplicitGood() {
		MP3File file = new MP3File("abc", "def", "efg", 9, "hij", "lmn", 1);
		assertTrue(file.getAlbum().equals("efg"));
		assertTrue(file.getArtist().equals("abc"));
		assertTrue(file.getTitle().equals("def"));
		assertTrue(file.getPathName().equals("hij"));
		assertTrue(file.getBitRate() == 1);
		assertTrue(file.getLabel().equals("lmn"));
		assertTrue(file.getTrack() == 9);

	} // method testMP3FileExplicitGood

	/**
	 * Test method for {@link AudioFile#setAlbum()} - test bad album
	 */
	@Test
	public void testSetAlbumBad() {
		WavFile file = new WavFile();
		file.setAlbum(null);

		assertNotNull(file.getAlbum());
		assertTrue(file.getAlbum().length() == 0);

	} // method testSetAlbumBad

	/**
	 * Test method for {@link AudioFile#setAlbum()} - test good album
	 */
	@Test
	public void testSetAlbumGood() {
		MP3File file = new MP3File();
		file.setAlbum("Album");

		assertNotNull(file.getAlbum());
		assertTrue(file.getAlbum().equals("Album"));

	} // method testSetAlbumGood

	/**
	 * Test method for {@link AudioFile#setArtist()} - test bad artist
	 */
	@Test
	public void testSetArtistBad() {
		WavFile file = new WavFile();
		file.setArtist(null);

		assertNotNull(file.getArtist());
		assertTrue(file.getArtist().equals("No Artist"));

	} // method testSetTitleBad

	/**
	 * Test method for {@link AudioFile#setArtist()} - gest good artist
	 */
	@Test
	public void testSetArtistGood() {
		MP3File file = new MP3File();
		file.setArtist("Artist");

		assertNotNull(file.getArtist());
		assertTrue(file.getArtist().equals("Artist"));

	} // method testSetArtistGood

	/**
	 * Test method for {@link AudioFile#setTitle()} - test bad title
	 */
	@Test
	public void testSetTitleBad() {
		MP3File file = new MP3File();
		file.setTitle(null);

		assertNotNull(file.getTitle());
		assertTrue(file.getTitle().equals("No Title"));

	} // method testSetTitleBad

	/**
	 * Test method for {@link AudioFile#setTitle()} - set good title
	 */
	@Test
	public void testSetTitleGood() {
		WavFile file = new WavFile();
		file.setArtist("Title");

		assertNotNull(file.getTitle());
		assertTrue(file.getArtist().equals("Title"));

	} // method testSetTitleGood

	/**
	 * Test method for {@link AudioFile#setTrack()} - set track too high
	 */
	@Test
	public void testSetTrackAfterLast() {
		WavFile file = new WavFile();
		file.setTrack(50);
		file.setTrack(100);
		assertTrue(file.getTrack() == 50);

	} // method testSetTrackAfterLast

	/**
	 * Test method for {@link AudioFile#SetTrack()} - test track too low
	 */
	@Test
	public void testSetTrackBeforeFirst() {
		MP3File file = new MP3File();
		file.setTrack(50);
		file.setTrack(0);
		assertTrue(file.getTrack() == 50);

	} // method testSetTrackBeforeFirst

	/**
	 * Test method for {@link AudioFile#SetTrack()} - test set first track
	 */
	@Test
	public void testSetTrackFirst() {
		WavFile file = new WavFile();
		file.setTrack(20);
		file.setTrack(1);
		assertTrue(file.getTrack() == 1);

	} // method testSetTrackFirst

	/**
	 * Test method for {@link AudioFile#SetTrack()} - test set last track
	 */
	@Test
	public void testSetTrackLast() {
		MP3File file = new MP3File();
		file.setTrack(20);
		file.setTrack(99);
		assertTrue(file.getTrack() == 99);

	} // method testSetTrackLast

	/**
	 * Test method for {@link AudioFile#SetTrack()} - test set middle track
	 */
	@Test
	public void testSetTrackMiddle() {
		WavFile file = new WavFile();
		file.setTrack(20);
		file.setTrack(50);
		assertTrue(file.getTrack() == 50);

	} // method testSetTrackMiddle

	/**
	 * Test method for {@link AudioFile#SetTrack(Java.lang.String)} - test set
	 * empty track
	 */
	@Test
	public void testSetTrackWithStringEmpty() {
		MP3File file = new MP3File();
		file.setTrack("100");
		file.setTrack("");
		assertTrue(file.getTrack() == 0);

	} // method testSetTrackWithStringEmpty

	/**
	 * Test method for {@link AudioFile#SetTrack(Java.lang.String)} - test set
	 * valid track
	 */
	@Test
	public void testSetTrackWithStringGood() {
		WavFile file = new WavFile();
		file.setTrack("50");
		file.setTrack("");
		assertTrue(file.getTrack() == 50);

	} // method testSetTrackWithStringGood

	/**
	 * Test method for {@link AudioFile#SetTrack(Java.lang.String)} - test set
	 * null track
	 */
	@Test
	public void testSetTrackWithStringNull() {
		MP3File file = new MP3File();
		file.setTrack("50");
		file.setTrack(null);
		assertTrue(file.getTrack() == 50);

	} // method testSetTrackWithStringNull

	/**
	 * Test method for {@link java.lang.Object#toString()} - test toString with
	 * album
	 */
	@Test
	public void testToStringWithAlbum() {
		WavFile file = new WavFile("Abc", "Def", "Ghi", 90, "Jkl", 20);
		assertTrue(file.toString().equals("Abc, Def (Ghi)"));

	} // method testToStringWithAlbum

	/**
	 * Test method for {@link java.lang.Object#toString()} - test toString
	 * without album
	 */
	@Test
	public void testToStringWithoutAlbum() {
		MP3File file = new MP3File("Abc", "Def", "", 0, "Ghi", "Jkl", 20);
		assertTrue(file.toString().equals("Abc, Def"));

	} // method testToStringWithoutAlbum

	/**
	 * Test method for
	 * {@link AudioFile#AudioFile(java.lang.String, java.lang.String, java.lang.String, int)}
	 * . -- test the explicit vvalue constructor
	 */
	@Test
	public void testWavFileExplicitGood() {
		WavFile file = new WavFile("abc", "def", "efg", 9, "hij", 1);
		assertTrue(file.getAlbum().equals("efg"));
		assertTrue(file.getArtist().equals("abc"));
		assertTrue(file.getTitle().equals("def"));
		assertTrue(file.getPathName().equals("hij"));
		assertTrue(file.getLength() == 1L);
		assertTrue(file.getTrack() == 9);

	} // method testWavFileExplicitGood

} // class AudioFileTest
