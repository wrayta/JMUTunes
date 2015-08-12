package view.gui;

import javazoom.jl.player.advanced.*;

/**
 * The Listener for JLayer audio rendering Note that this is a class that is
 * extended rather than an interface that is implemented. It functions as an
 * Adapter rather than a Listener.
 * 
 * Shamelessly adapted from:
 * http://thiscouldbebetter.wordpress.com/2011/06/14/playing
 * -an-mp3-from-java-using-jlayer/
 * 
 * @author Thomas Wray & Joe Cumins (adapted by)
 * @version 1.0
 * 
 */
public class SoundListener extends PlaybackListener {

	/**
	 * This event is invoked with the audio begins to playback
	 * 
	 * @param the
	 *            PlaybackEvent object
	 */
	public void playbackStarted(PlaybackEvent playbackEvent) {

	} // method playbackStarted

	/**
	 * This event is invoked with the audio concludes playback
	 * 
	 * @param the
	 *            PlaybackEvent object
	 */
	public void playbackFinished(PlaybackEvent playbackEvent) {

	} // method playbackFinished

} // class SoundListener
