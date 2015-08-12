package controller.file;

import java.io.*;
import java.util.logging.*;

import org.jaudiotagger.audio.*;
import org.jaudiotagger.tag.*;

/**
 * Handle MP3 specific file operations (uses the Bridge pattern)
 * 
 * @author Thomas Wray & Joe Cumins
 * @version PA3 (3/16/2013)
 */
public class MP3FileHandler extends AudioFileHandler {

	/**
	 * Exlicit value constructor
	 * 
	 * @param list
	 * @param view
	 */
	public MP3FileHandler(model.AudioList list) {
		super(list);

		// disable JAudioTagger warnings
		Logger.getLogger("org.jaudiotagger").setLevel(Level.OFF);

	} // explicit value constructor

	/**
	 * Create a output line to write from an AudioFile object
	 * 
	 * @param the
	 *            AudioFile to translate
	 * @return the String representation of the AudioFile
	 */
	@Override
	public String createAudioFile(model.AudioFile audioFile) {
		model.MP3File file = (model.MP3File) audioFile;

		String album = " ";
		String artist = "";
		String bitRate = " ";
		String label = " ";
		String pathName = " ";
		String title = "";
		String track = " ";

		artist = file.getArtist();
		title = file.getTitle();

		if (file.getAlbum().trim().length() != 0)
			album = file.getAlbum();
		if (file.getLabel().trim().length() != 0)
			label = file.getLabel();
		if (file.getPathName().trim().length() != 0)
			pathName = file.getPathName();
		if (file.getBitRate() != 0)
			bitRate = "" + file.getBitRate();
		if (file.getTrack() != 0)
			track = "" + file.getTrack();

		return MP3 + "|" + artist + "|" + title + "|" + album + "|" + track
				+ "|" + pathName + "|" + label + "|" + bitRate + "|";

	} // method createAudioFile

	/**
	 * Create an MP3File object from the text line. If the line is malformed
	 * return null
	 * 
	 * **TAW & JMC (PA3) - New for PA3 - converted from createAudioFile(String)
	 * 
	 * @param the
	 *            line from the file
	 * @return an AudioFile if good, null otherwise
	 */
	@Override
	public model.AudioFile createAudioFile(String line) {
		model.MP3File audio = null;

		if (line != null) {
			String[] elements = line.split("\\|");

			// skip malformed records
			if (!isMalformed(elements)) {
				audio = new model.MP3File();

				audio.setArtist(elements[1].trim());
				audio.setTitle(elements[2].trim());
				audio.setAlbum(elements[3].trim());
				audio.setTrack(elements[4].trim());
				audio.setPathName(elements[5].trim());
				audio.setLabel(elements[6].trim());
				audio.setBitRate(elements[7].trim());

			} // end if
		}

		return audio;

	} // method createFile

	/**
	 * Concrete load method for mp3s
	 * 
	 * @param the
	 *            mp3 file object
	 */
	@Override
	public void load(File file) {
		if (file.canRead()) {

			model.MP3File mp3 = new model.MP3File();

			try {
				AudioFile mp3File = AudioFileIO.read(file);

				Tag tag = mp3File.getTag();
				AudioHeader header = mp3File.getAudioHeader();

				if (tag.getFirst(FieldKey.ARTIST).trim().length() > 0)
					mp3.setArtist(tag.getFirst(FieldKey.ARTIST));
				else
					mp3.setArtist(file.getName());

				if (tag.getFirst(FieldKey.TITLE).trim().length() > 0)
					mp3.setTitle(tag.getFirst(FieldKey.TITLE));
				else
					mp3.setTitle(file.getName());

				mp3.setAlbum(tag.getFirst(FieldKey.ALBUM));
				mp3.setTrack(tag.getFirst(FieldKey.TRACK));
				mp3.setLabel(tag.getFirst(FieldKey.RECORD_LABEL));
				mp3.setBitRate(Integer.parseInt(header.getBitRate()));

			} // end try

			catch (Exception e) {
			}

			// if no tags then set manually
			if (mp3.getArtist().trim().length() == 0)
				mp3.setArtist(file.getName());
			if (mp3.getTitle().trim().length() == 0)
				mp3.setTitle(file.getName());

			// set pathname regardless
			mp3.setPathName(file.getAbsolutePath());

			list.add(mp3);

			// view.clearLine();
			// view.display( mp3.toString() + " . . . " );

		} // method load

	} // method load

	/**
	 * Check the line to see if it is malformed.
	 * 
	 * @param an
	 *            array of elements
	 * @return true if the line is malformed, false otherwise
	 */
	@Override
	protected boolean isMalformed(String[] elements) {
		boolean isMalformed = true;

		if (elements.length == 8)
			if (elements[1].trim().length() > 0)
				if (elements[2].trim().length() > 0)
					isMalformed = false;

		return isMalformed;

	} // method isMalformed

} // class MP3FileLoad
