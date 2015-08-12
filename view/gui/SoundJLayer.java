package view.gui;

import java.io.*;

import javazoom.jl.decoder.*;
import javazoom.jl.player.*;
import javazoom.jl.player.advanced.*;

/**
 * Play an MP3 audio file - assumes the file is in the current directory
 * 
 * Shamelessly adapted from:
 * http://thiscouldbebetter.wordpress.com/2011/06/14/playing
 * -an-mp3-from-java-using-jlayer/
 * 
 * @author Thomas Wray & Joe Cumins (adapted by)
 * @version 1.0
 */
public class SoundJLayer extends PlaybackListener implements Runnable {
	private AdvancedPlayer player;
	private SoundListener listener;
	private String filePath;
	private Thread playerThread;

	/**
	 * Explicit value constructor
	 * 
	 * @param the
	 *            file name sent by the user
	 */
	public SoundJLayer(String fileName) {
		filePath = fileName;
		listener = new SoundListener();

	} // explicit value constructor

	/**
	 * Create the long pathname from the file name (assuming the current
	 * directory) and play the MP3 audio file
	 */
	public void play() {
		try {
			String urlAsString = "file:///" + "/" + this.filePath;

			player = new AdvancedPlayer(
					new java.net.URL(urlAsString).openStream(), FactoryRegistry
							.systemRegistry().createAudioDevice());

			player.setPlayBackListener(listener);

			playerThread = new Thread(this, "AudioPlayerThread");

			playerThread.start();

		} // end try

		catch (Exception ex) {
			ex.printStackTrace();

		} // end catch

	} // method play

	// PlaybackListener members

	/**
	 * run - required by the Runnable interface
	 */
	public void run() {
		try {
			player.play();

		} // end try

		catch (JavaLayerException ex) {
			ex.printStackTrace();

		} // end catch

	} // method run

	/**
     * 
     */
	public void stop() {
		playerThread.stop();
	}

} // class SoundJLayer