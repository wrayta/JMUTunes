package controller.edit;

import model.*;
import view.text.*;

/**
 * Parent type for Strategy pattern. Handle edit for mp3/wav in child classes
 * (uses Command pattern).
 * 
 * Modifications: PA3 (3/8/2013) - new for PA3
 * 
 * @author Thomas Wray & Joe Cumins
 * @version PA3 (3/8/2013)
 */
public abstract class AudioEdit {
	protected AudioView view;

	/**
	 * Explicit value constructor
	 * 
	 * @param list
	 */
	public AudioEdit(AudioView view) {
		this.view = view;

	} // explicit value constructor

	/**
	 * Handle edit for the
	 * 
	 * @param file
	 */
	public AudioFile editFile(AudioFile original) {
		AudioFile newFile;
		String which = "";

		// create copy of the AudioFile
		if (original instanceof MP3File) {
			newFile = new MP3File(original);
			which = "MP3";

		} // end if

		else {
			newFile = new WavFile(original);
			which = "Wav";

		} // end else

		int orgTrack = original.getTrack();
		int track = 0;

		String album = "";
		String artist = "";
		String orgAlbum = original.getAlbum();
		String orgArtist = original.getArtist();
		String orgTitle = original.getTitle();
		String title = "";

		// print header
		view.clearScreen();
		view.centerText("Edit " + which + " File");
		view.displayLine();
		view.displayLine();

		view.display("Artist"
				+ (orgArtist.length() > 0 ? " (" + orgArtist + "): " : ": "));
		artist = view.getInput(false, "");

		// validation loop for artist if trying to clear the entry
		while (artist.length() > 0 && artist.trim().length() == 0) {
			view.displayError("Required entry.");
			artist = view.getInput(false, "");

		} // end while

		view.display("Title"
				+ (orgTitle.length() > 0 ? " (" + orgTitle + "): " : ": "));
		title = view.getInput(false, "");

		// validation loop for title if trying to clear the entry
		while (title.length() > 0 && title.trim().length() == 0) {
			view.displayError("Required entry.");
			title = view.getInput(false, "");

		} // end while

		view.display("Album"
				+ (orgAlbum.length() > 0 ? " (" + orgAlbum + "): " : ": "));
		album = view.getInput(false, "");

		view.display("Track" + (orgTrack != 0 ? " (" + orgTrack + "): " : ": "));
		track = view.getNumber(false, 1, 99);

		newFile.setArtist(artist.length() > 0 ? artist : orgArtist);
		newFile.setTitle(title.length() > 0 ? title : orgTitle);
		newFile.setAlbum(album.length() > 0 ? album : orgAlbum);
		newFile.setTrack(track != 0 ? track : orgTrack);

		return newFile;

	} // editFile (parent version)

} // abstract class AudioEdit
