package controller.edit;

import java.io.*;

import model.*;
import view.text.*;

/**
 * Handles edit of MP3 file (uses Strategy pattern)
 * 
 * Modifications: PA3 (3/8/2013) - new for PA3
 * 
 * @author Thomas Wray & Joe Cumins
 * @version PA3 (3/8/2013)
 */
public class MP3FileEdit extends AudioEdit {

	/**
	 * Explicit value constructor
	 * 
	 * @param list
	 */
	public MP3FileEdit(AudioView view) {
		super(view);

	} // explicit value constructor

	/**
	 * Edit an MP3 file
	 */
	@Override
	public AudioFile editFile(AudioFile file) {
		int bitRate;

		File mp3File;

		MP3File newFile;
		MP3File original = (MP3File) file; // convert first

		String fileName;
		String label;
		String orgLabel;
		String pathName;

		bitRate = original.getBitRate();
		orgLabel = original.getLabel();
		pathName = original.getPathName();

		mp3File = new File(pathName);
		fileName = mp3File.getName();

		// invoke the parent to handle the common elements for audio files
		newFile = (MP3File) super.editFile(file);

		// now do the MP3-specific stuff
		view.display("Label"
				+ (orgLabel.length() > 0 ? " (" + orgLabel + "): " : ": "));
		label = view.getInput(false, "");
		view.displayLine("Filename (" + fileName + ")");

		view.displayLine("Bitrate (" + bitRate + ")");

		newFile.setPathName(pathName);
		newFile.setLabel(label.length() > 0 ? label : orgLabel);
		newFile.setBitRate(bitRate);

		return newFile;

	} // method editFile

} // class MP3Edit
