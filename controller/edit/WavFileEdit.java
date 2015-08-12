package controller.edit;

import java.io.*;

import model.*;
import view.text.*;

/**
 * Handles edit of Wav file (uses Strategy pattern)
 * 
 * Modifications: PA3 (3/8/2013) - new for PA3
 * 
 * @author Thomas Wray & Joe Cumins
 * @version PA3 (3/8/2013)
 */
public class WavFileEdit extends AudioEdit {

	/**
	 * Explicit value constructor
	 * 
	 * @param list
	 */
	public WavFileEdit(AudioView view) {
		super(view);

	} // explicit value constructor

	/**
	 * Edit a WAV file
	 * 
	 * @param the
	 *            WavFile to edit
	 */
	@Override
	public AudioFile editFile(AudioFile file) {
		long length;
		File wavFile;

		String fileName;
		String pathName;

		WavFile newFile;
		WavFile original = (WavFile) file; // convert first

		length = original.getLength();
		pathName = original.getPathName();

		wavFile = new File(pathName);
		fileName = wavFile.getName();

		// invoke the parent to handle the common elements for audio files
		newFile = (WavFile) super.editFile(file);

		// now do the Wav-specific stuff
		view.displayLine("Filename (" + fileName + ")");
		view.displayLine("Length (" + length + ")");

		newFile.setPathName(pathName);
		newFile.setLength(length);

		return newFile;

	} // method editFile

} // class WavEdit
