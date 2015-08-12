package view.gui;

import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

import javax.swing.*;

import model.AudioFile;
import model.AudioList;
import controller.file.AudioFileControl;

/**
 * 
 * @author Thomas Wray
 * 
 */
public class TunesListener extends InputVerifier implements MouseListener,
		ActionListener {
	private model.AudioList normalList;
	private JList selectedAudioList;
	private Object[] selections;
	private AudioWindow window;
	private AddFilesWindow addWindow;
	private EditFilesWindow editWindow;
	private int selectedFileNum;
	private SearchFilesWindow searchWindow;
	private SearchAudioWindow searchAudioWindow;
	private SoundJLayer soundToPlay;

	private model.AudioList newResultSet;
	private model.AudioList resultSet = null;
	private model.AudioList newList;
	private AudioClip music;
	private Object current;
	private boolean isPlaying;

	public static final char STARTS_WITH = '^';
	public static final char ENDS_WITH = '$';
	public static final char CONTAINS = '@'; // used internally only
	public static final char NOT = '!';

	public static final int ARTIST = 1;
	public static final int TITLE = 2;
	public static final int ALBUM = 3;

	/**
	 * 
	 * @param incomingWindow
	 */
	public TunesListener(AudioWindow incomingWindow) {
		window = incomingWindow;
		normalList = incomingWindow.getList();
		selectedAudioList = incomingWindow.getSelectedAudioList();
	}

	/**
	 * 
	 * @param addFiles
	 * @param audWindow
	 */
	// constructor for adding files
	public TunesListener(AddFilesWindow addFiles, AudioWindow audWindow) {
		addWindow = addFiles;
		window = audWindow;

	}

	/**
	 * 
	 * @param editFiles
	 * @param audWindow
	 */
	// constructors for editing files
	public TunesListener(EditFilesWindow editFiles, AudioWindow audWindow) {
		editWindow = editFiles;
		window = audWindow;

	}

	/**
	 * 
	 * @param searchFiles
	 * @param audWindow
	 */
	// constructor for searching files
	public TunesListener(SearchFilesWindow searchFiles, AudioWindow audWindow) {
		searchWindow = searchFiles;
		window = audWindow;

	}

	/**
	 * 
	 * @param audWindow
	 * @param searchWin
	 */
	public TunesListener(AudioWindow audWindow, SearchAudioWindow searchWin) {
		window = audWindow;
		searchAudioWindow = searchWin;
		normalList = searchWin.getList();

	}

	/**
	 * 
	 * @param editFiles
	 * @param searchWin
	 * @param audioWin
	 */
	public TunesListener(EditFilesWindow editFiles,
			SearchAudioWindow searchWin, AudioWindow audioWin) {
		editWindow = editFiles;
		searchAudioWindow = searchWin;
		window = audioWin;
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {

	}

	@Override
	public void mouseExited(MouseEvent arg0) {

	}

	/**
	 * 
	 */
	@Override
	public void mousePressed(MouseEvent arg0) {
		try {
			if (arg0.getSource() == window.getNormalList()
					|| arg0.getSource() == searchAudioWindow.getNormalList()) {
				if (window.isShowing()) {
					// Get the selected values.
					selections = window.getNormalList().getSelectedValues();
					if (soundToPlay != null || music != null) {
						window.getDeleteButton().setEnabled(false);
						window.getEditButton().setEnabled(false);
						window.getSearchButton().setEnabled(false);

					}

					else if (window.getList().size() > 0) {
						window.getDeleteButton().setEnabled(true);
						window.getEditButton().setEnabled(true);
						window.getPlayButton().setEnabled(true);
					}

					else {
						window.getPlayButton().setEnabled(false);
						window.getDeleteButton().setEnabled(false);
						window.getEditButton().setEnabled(false);
					}

				}

				else if (searchAudioWindow.isShowing()) {
					selections = searchAudioWindow.getNormalList()
							.getSelectedValues();

				}
			}
		} catch (Exception e) {
		}

	}

	/**
	 * 
	 */
	@Override
	public void mouseReleased(MouseEvent arg0) {
		try {
			if (arg0.getSource() == window.getNormalList()
					|| arg0.getSource() == searchAudioWindow.getNormalList()) {
				if (window.isActive()) {
					window.getSelectedAudioList().setListData(selections);

					if (soundToPlay != null || music != null) {
						window.getDeleteButton().setEnabled(false);
						window.getEditButton().setEnabled(false);
						window.getSearchButton().setEnabled(false);

					}

					else if (window.getList().size() > 0) {
						window.getDeleteButton().setEnabled(true);
						window.getEditButton().setEnabled(true);
						window.getPlayButton().setEnabled(true);
					}

					else if (window.getList().size() <= 0) {
						window.getDeleteButton().setEnabled(false);
						window.getEditButton().setEnabled(false);
						window.getPlayButton().setEnabled(false);
					}

				}

				else if (searchAudioWindow.isActive()) {
					searchAudioWindow.getSelectedAudioList().setListData(
							selections);

					if (soundToPlay != null || music != null) {
						searchAudioWindow.getDeleteButton().setEnabled(false);
						searchAudioWindow.getEditButton().setEnabled(false);
						searchAudioWindow.getSearchButton().setEnabled(false);

					}

					else if (searchAudioWindow.getList().size() > 0) {
						searchAudioWindow.getDeleteButton().setEnabled(true);
						searchAudioWindow.getEditButton().setEnabled(true);
						searchAudioWindow.getPlayButton().setEnabled(true);
					}

					else {
						searchAudioWindow.getDeleteButton().setEnabled(false);
						searchAudioWindow.getEditButton().setEnabled(false);
						searchAudioWindow.getPlayButton().setEnabled(false);
					}

				}
			}
		} catch (Exception e) {
		}
	}

	/**
	 * 
	 */
	@Override
	public void actionPerformed(ActionEvent arg0) {

		try {
			if ((((JMenuItem) arg0.getSource()).getName().equals("Exit"))) {
				exitProgram();
			}

			else if ((((JMenuItem) arg0.getSource()).getName().equals("Add"))) {
				if (soundToPlay != null)
					soundToPlay.stop();

				else if (music != null)
					music.stop();

				if (window.isActive()) {
					if (window.getList().size() > 0) {
						window.getPlayButton().setText("   Play   ");
						soundToPlay = null;
						music = null;
						window.getPlayButton().setEnabled(true);
						window.getEditButton().setEnabled(true);
						window.getDeleteButton().setEnabled(true);
						window.getSearchButton().setEnabled(true);
						window.getNormalList().setSelectedIndex(1);

					}
					addWindow = new AddFilesWindow(window);

				}

				else if (searchAudioWindow.isActive()) {
					try {
						if (searchAudioWindow.getList().size() > 0) {
							searchAudioWindow.getPlayButton().setText(
									"   Play   ");
							soundToPlay = null;
							music = null;
							searchAudioWindow.getPlayButton().setEnabled(true);
							searchAudioWindow.getEditButton().setEnabled(true);
							searchAudioWindow.getDeleteButton()
									.setEnabled(true);
							JOptionPane.showMessageDialog(null,
									"Cannot add songs while in Search Mode");

						} else {
							JOptionPane.showMessageDialog(null,
									"Cannot add songs while in Search Mode");
						}
					} catch (NullPointerException e) {
						JOptionPane.showMessageDialog(null,
								"Cannot add songs while in Search Mode");
					}
				}

			}
		} catch (ClassCastException e) {

			if ((((JButton) arg0.getSource()).getName().equals("Go"))) {
				if (addWindow.getMp3CheckBox().isSelected()
						&& !addWindow.getWavCheckBox().isSelected()
						&& addWindow.getDirectoryField().getText().trim()
								.length() != 0) {
					String filter = "mp3";
					addSongs(filter);
				}

				else if (!addWindow.getMp3CheckBox().isSelected()
						&& addWindow.getWavCheckBox().isSelected()
						&& addWindow.getDirectoryField().getText().trim()
								.length() != 0) {

					String filter = "wav";
					addSongs(filter);
				}

				else if (addWindow.getMp3CheckBox().isSelected()
						&& addWindow.getWavCheckBox().isSelected()
						&& addWindow.getDirectoryField().getText().trim()
								.length() != 0) {
					String filter = "mp3wav";
					addSongs(filter);

				}

				else {
					addWindow.dispose();
				}
			}

			else if ((((JButton) arg0.getSource()).getName()
					.equals("Cancel Add"))) {
				addWindow.dispose();
			}

			else if ((((JButton) arg0.getSource()).getName()
					.equals("Cancel Edit"))) {
				editWindow.dispose();
			}

			else if ((((JButton) arg0.getSource()).getName()
					.equals("Cancel Search"))) {
				searchWindow.dispose();
			}

			else if ((((JButton) arg0.getSource()).getName().equals("Edit"))) {
				editDisplay(window, searchAudioWindow);
			}

			else if ((((JButton) arg0.getSource()).getName()
					.equals("EditSearch"))) {
				editDisplay(window, searchAudioWindow);
			}

			else if ((((JButton) arg0.getSource()).getName()
					.equals("Quit Search"))) {
				if (soundToPlay != null) {
					soundToPlay.stop();
				} else if (music != null) {
					music.stop();
				}
				newList = window.getList();
				searchAudioWindow.dispose();
				window = new AudioWindow(newList);
			}

			else if ((((JButton) arg0.getSource()).getName().equals("Search"))) {
				searchWindow = new SearchFilesWindow(window);
			}

			else if ((((JButton) arg0.getSource()).getName()
					.equals("Find Song(s)"))) {
				doSearch();
			}

			else if ((((JButton) arg0.getSource()).getName()
					.equals("ConfirmEditNormal"))) {
				edit(window, searchAudioWindow);
			}

			else if ((((JButton) arg0.getSource()).getName()
					.equals("ConfirmEditSearch"))) {
				edit(window, searchAudioWindow);
			}

			else if ((((JButton) arg0.getSource()).getName()
					.equals("Artist Entry Field Button"))) {
				searchArtist();
			}

			else if ((((JButton) arg0.getSource()).getName()
					.equals("Album Entry Field Button"))) {
				searchAlbum();
			}

			else if ((((JButton) arg0.getSource()).getName()
					.equals("Title Entry Field Button"))) {
				searchTitle();
			}

			else if ((((JButton) arg0.getSource()).getName().equals("Delete"))) {
				deleteSong(window, searchAudioWindow);
			}

			else if ((((JButton) arg0.getSource()).getName()
					.equals("DeleteSearch"))) {
				deleteSong(window, searchAudioWindow);
			}

			else if ((((JButton) arg0.getSource()).getName().equals("Play"))
					&& searchAudioWindow == null) {

				model.AudioFile fileToPlay = (AudioFile) window.getNormalList()
						.getSelectedValue();

				if (fileToPlay instanceof model.MP3File && music != null) {
					music.stop();
					window.getPlayButton().setText("   Play   ");
					music = null;
					window.getEditButton().setEnabled(true);
					window.getDeleteButton().setEnabled(true);
					window.getSearchButton().setEnabled(true);

				}

				else if (fileToPlay instanceof model.WavFile
						&& soundToPlay != null) {
					soundToPlay.stop();
					window.getPlayButton().setText("   Play   ");
					soundToPlay = null;
					window.getEditButton().setEnabled(true);
					window.getDeleteButton().setEnabled(true);
					window.getSearchButton().setEnabled(true);

				}

				else if (fileToPlay instanceof model.MP3File) {
					if (soundToPlay == null && music == null) {
						soundToPlay = new SoundJLayer(fileToPlay.getPathName());
						soundToPlay.play();
						window.getPlayButton().setText("   Stop   ");
						window.getEditButton().setEnabled(false);
						window.getDeleteButton().setEnabled(false);
						window.getSearchButton().setEnabled(false);

					} else if (soundToPlay != null && music == null) {
						soundToPlay.stop();
						window.getPlayButton().setText("   Play   ");
						soundToPlay = null;
						window.getEditButton().setEnabled(true);
						window.getDeleteButton().setEnabled(true);
						window.getSearchButton().setEnabled(true);

					}

				}

				else if (fileToPlay instanceof model.WavFile) {
					if (music == null && soundToPlay == null) {
						try {
							music = Applet.newAudioClip(new URL("file",
									"localhost", fileToPlay.getPathName()));
							music.play();
							window.getPlayButton().setText("   Stop   ");
							window.getEditButton().setEnabled(false);
							window.getDeleteButton().setEnabled(false);
							window.getSearchButton().setEnabled(false);
						} catch (MalformedURLException e1) {
						}
					}

					else if (music != null && soundToPlay == null) {
						music.stop();
						window.getPlayButton().setText("   Play   ");
						music = null;
						window.getEditButton().setEnabled(true);
						window.getDeleteButton().setEnabled(true);
						window.getSearchButton().setEnabled(true);
					}

				}

			}

			else if ((((JButton) arg0.getSource()).getName()
					.equals("PlaySearch")) && searchAudioWindow != null) {
				model.AudioFile fileToPlay = (AudioFile) searchAudioWindow
						.getNormalList().getSelectedValue();

				if (fileToPlay instanceof model.MP3File && music != null) {
					music.stop();
					searchAudioWindow.getPlayButton().setText("   Play   ");
					music = null;
					searchAudioWindow.getEditButton().setEnabled(true);
					searchAudioWindow.getDeleteButton().setEnabled(true);
				}

				else if (fileToPlay instanceof model.WavFile
						&& soundToPlay != null) {
					soundToPlay.stop();
					searchAudioWindow.getPlayButton().setText("   Play   ");
					soundToPlay = null;
					searchAudioWindow.getEditButton().setEnabled(true);
					searchAudioWindow.getDeleteButton().setEnabled(true);
				}

				else if (fileToPlay instanceof model.MP3File) {
					if (soundToPlay == null && music == null) {
						soundToPlay = new SoundJLayer(fileToPlay.getPathName());
						soundToPlay.play();
						searchAudioWindow.getPlayButton().setText("   Stop   ");
						searchAudioWindow.getEditButton().setEnabled(false);
						searchAudioWindow.getDeleteButton().setEnabled(false);

					} else if (soundToPlay != null && music == null) {
						soundToPlay.stop();
						searchAudioWindow.getPlayButton().setText("   Play   ");
						soundToPlay = null;
						searchAudioWindow.getEditButton().setEnabled(true);
						searchAudioWindow.getDeleteButton().setEnabled(true);

					}

				}

				else if (fileToPlay instanceof model.WavFile) {
					if (music == null && soundToPlay == null) {
						try {
							music = Applet.newAudioClip(new URL("file",
									"localhost", fileToPlay.getPathName()));
							music.play();
							searchAudioWindow.getPlayButton().setText(
									"   Stop   ");
							searchAudioWindow.getEditButton().setEnabled(false);
							searchAudioWindow.getDeleteButton().setEnabled(
									false);
						} catch (MalformedURLException e1) {
						}
					}

					else if (music != null && soundToPlay == null) {
						music.stop();
						searchAudioWindow.getPlayButton().setText("   Play   ");
						music = null;
						searchAudioWindow.getEditButton().setEnabled(true);
						searchAudioWindow.getDeleteButton().setEnabled(true);
					}

				}

			}
		}

	}

	/**
	 * 
	 * @param window
	 * @param searchAudioWindow
	 */
	private void deleteSong(AudioWindow window,
			SearchAudioWindow searchAudioWindow) {

		if (searchAudioWindow == null) {
			int choice = JOptionPane.showConfirmDialog(null, "Delete \""
					+ normalList.get(window.getNormalList().getSelectedIndex())
							.toString() + "\"?", "Confirm",
					JOptionPane.OK_CANCEL_OPTION);

			if (choice == JOptionPane.OK_OPTION) {
				int fileNumber = 0;

				model.AudioFile deleteFile;

				fileNumber = window.getNormalList().getSelectedIndex();

				deleteFile = normalList.get(fileNumber);

				window.getList().remove(deleteFile);

				selections[0] = "";

				window.getSelectedAudioList().setListData(selections);
				window.getEditButton().setEnabled(false);
				window.getDeleteButton().setEnabled(false);
				window.getPlayButton().setEnabled(false);

				if (window.getList().size() > 0)
					window.getSearchButton().setEnabled(true);

				else {
					window.getSearchButton().setEnabled(false);
				}

			}
		} else {
			int choice = JOptionPane.showConfirmDialog(
					null,
					"Delete \""
							+ normalList.get(
									searchAudioWindow.getNormalList()
											.getSelectedIndex()).toString()
							+ "\"?", "Confirm", JOptionPane.OK_CANCEL_OPTION);

			if (choice == JOptionPane.OK_OPTION) {
				int fileNumber = 0;

				model.AudioFile deleteFile;

				fileNumber = searchAudioWindow.getNormalList()
						.getSelectedIndex();

				deleteFile = normalList.get(fileNumber);

				normalList.remove(deleteFile);

				if (window.getList().contains(deleteFile))
					window.getList().remove(deleteFile);

				selections[0] = "";

				searchAudioWindow.getSelectedAudioList()
						.setListData(selections);
				searchAudioWindow.getEditButton().setEnabled(false);
				searchAudioWindow.getDeleteButton().setEnabled(false);
				searchAudioWindow.getPlayButton().setEnabled(false);
			}
		}
	}

	/**
	 * 
	 * @param type
	 * @param term
	 * @param window
	 * @return
	 */
	private AudioList getResultSet(int type, String term, AudioWindow window) {
		AudioList resultSet = new model.AudioList();

		for (int i = 0; i < window.getList().size(); i++) {
			AudioFile file = window.getList().get(i);
			String check = null;

			if (file != null)
				switch (type) {
				case ARTIST:
					check = file.getArtist();
					break;
				case TITLE:
					check = file.getTitle();
					break;
				case ALBUM:
					check = file.getAlbum();
					break;

				} // end switch

			check = check.trim(); // trim excess space

			if (includeItem(check, term))
				resultSet.add(file);

		} // end for

		return resultSet;

	} // method getResultSet

	/**
	 * 
	 * @param check
	 * @param term
	 * @return
	 */
	private boolean includeItem(String check, String term) {
		boolean contains = false;

		if (check != null)
			if (term != null & term.length() > 0) {
				switch (getOperation(term)) {
				case STARTS_WITH:
					contains = startsWith(check, term.substring(1));
					break;
				case ENDS_WITH:
					contains = endsWith(check, term.substring(1));
					break;
				case CONTAINS:
					contains = contains(check, term);
					break;
				case NOT:
					String newTerm = getNotExpression(term);
					if (newTerm != null)
						contains = !includeItem(check, newTerm);

				} // end switch

			} // end if

		return contains;

	} // method includeItem

	/**
	 * 
	 * @param term
	 * @return
	 */
	private char getOperation(String term) {
		char operation = (char) 0;
		String operations = "" + STARTS_WITH + ENDS_WITH + NOT;

		if (term != null && term.trim().length() > 0)
			if (operations.indexOf(term.charAt(0)) > -1)
				operation = term.charAt(0);
			else
				operation = CONTAINS;

		return operation;

	} // method getOperation

	/**
	 * 
	 * @param check
	 * @param term
	 * @return
	 */
	private boolean startsWith(String check, String term) {
		return check.toLowerCase().startsWith(term.toLowerCase());

	}

	/**
	 * 
	 * @param check
	 * @param term
	 * @return
	 */
	private boolean endsWith(String check, String term) {
		return check.toLowerCase().endsWith(term.toLowerCase());

	}

	/**
	 * 
	 * @param check
	 * @param term
	 * @return
	 */
	private boolean contains(String check, String term) {
		return check.toLowerCase().indexOf(term.toLowerCase()) > -1;

	} // method startsWith

	/**
	 * 
	 * @param expression
	 * @return
	 */
	private String getNotExpression(String expression) {
		String toCheck = null;

		// check to make sure this is well formed first
		if (isLegalNotExpression(expression))
			toCheck = expression.substring(2, expression.length() - 1);

		return toCheck;

	} // method invertCheck

	/**
	 * 
	 * @param check
	 * @return
	 */
	private boolean isLegalNotExpression(String check) {
		boolean isLegal = false;

		if (check != null && check.length() > 2)
			if (check.charAt(0) == '!')
				if (check.charAt(1) == '(')
					if (check.charAt(check.length() - 1) == ')')
						isLegal = true;

		return isLegal;

	} // method isLegalNotExpression

	/**
	 * 
	 */
	public boolean verify(JComponent input) {
		boolean verified = true;
		String message = null;

		JTextField field = (JTextField) input;

		if (field.getName().equals("Artist") || field.getName().equals("Title")) {
			if (field.getText().trim().length() == 0) {
				verified = false;
				message = "Required Entry";

			} // end if

		} // end if
		else if (field.getName().equals("Track")) {
			if (field.getText().trim().length() > 0) {
				try {
					int track = Integer.parseInt(field.getText().trim());

					if (track < 0 || track > 99)
						throw new NumberFormatException();

				} // end try
				catch (NumberFormatException e) {
					verified = false;
					message = "Must be a number between 1 and 99";

				} // end catch

			} // end if

		} // end else/if

		if (!verified) {
			JOptionPane.showMessageDialog(null, message);
			if (field.getName().equals("Artist"))
				field.setText(editWindow.getOrgArtist());
			else if (field.getName().equals("Title"))
				field.setText(editWindow.getOrgTitle());
			else if (field.getName().equals("Track"))
				field.setText(editWindow.getOrgTrack() + "");
		}

		return verified;

	} // method verify

	/**
	 * 
	 */
	private void exitProgram() {
		// Write the current songs to the songList.txt
		AudioFileControl fileController = new AudioFileControl(window.getList());
		fileController.writeFile();

		// Exit the program
		System.exit(0);
	}

	/**
	 * 
	 * @param filter
	 */
	private void addSongs(String filter) {

		String folderName = addWindow.getDirectoryField().getText();

		File newFile = new File(folderName);

		controller.file.AudioFileControl fileControl = new controller.file.AudioFileControl(
				window.getList());
		fileControl.loadFiles(newFile, filter);

		DefaultListModel newModel;

		newModel = window.getList().getModel();

		window.getNormalList().setModel(newModel);

		if (window.getList().size() > 0) {
			window.getSearchButton().setEnabled(true);
		}

		addWindow.dispose();
	}

	/**
	 * 
	 * @param window
	 * @param searchAudioWindow
	 */
	private void editDisplay(AudioWindow window,
			SearchAudioWindow searchAudioWindow) {

		if (searchAudioWindow == null) {
			int choice = JOptionPane.showConfirmDialog(null, "Edit \""
					+ normalList.get(window.getNormalList().getSelectedIndex())
					+ "\"?", "Confirm", JOptionPane.OK_CANCEL_OPTION);

			if (choice == JOptionPane.OK_OPTION) {
				model.AudioFile originalUneditedFile = normalList.get(window
						.getNormalList().getSelectedIndex());

				selectedFileNum = window.getNormalList().getSelectedIndex();

				editWindow = new EditFilesWindow(window, originalUneditedFile,
						selectedFileNum);

			}
		}

		else {
			int choice = JOptionPane.showConfirmDialog(
					null,
					"Edit \""
							+ normalList.get(searchAudioWindow.getNormalList()
									.getSelectedIndex()) + "\"?", "Confirm",
					JOptionPane.OK_CANCEL_OPTION);

			if (choice == JOptionPane.OK_OPTION) {
				model.AudioFile originalUneditedFile = normalList
						.get(searchAudioWindow.getNormalList()
								.getSelectedIndex());

				selectedFileNum = searchAudioWindow.getNormalList()
						.getSelectedIndex();

				editWindow = new EditFilesWindow(searchAudioWindow,
						originalUneditedFile, selectedFileNum, window);

			}
		}
	}

	/**
	 * 
	 */
	private void doSearch() {
		searchArtist();
		searchAlbum();
		searchTitle();

		searchWindow.dispose();

		window.dispose();

		searchAudioWindow = new SearchAudioWindow(window, resultSet);
	}

	/**
	 * 
	 */
	private void edit(AudioWindow window, SearchAudioWindow searchAudioWindow) {

		int track = 0;
		model.AudioFile newFile = null;

		if (searchAudioWindow == null) {
			model.AudioFile file = (AudioFile) window.getModel().get(
					editWindow.getSelectedFileNum());
			window.getList().remove(editWindow.getSelectedFileNum());

			if (file instanceof model.MP3File) {
				mp3Edit(track, newFile, searchAudioWindow);
			}

			else if (file instanceof model.WavFile) {
				wavEdit(track, newFile, searchAudioWindow);
			}

			editWindow.getArtistField().setText("");
			editWindow.getTitleField().setText("");
			editWindow.getAlbumField().setText("");
			editWindow.getTrackField().setText("");
			editWindow.getPathNameField().setText("");

			editWindow.dispose();

			String[] clearArray = { "" };

			window.getSelectedAudioList().setListData(clearArray);
			window.getEditButton().setEnabled(false);
			window.getDeleteButton().setEnabled(false);
			window.getPlayButton().setEnabled(false);
		}

		else {
			model.AudioFile file = (AudioFile) searchAudioWindow.getModel()
					.get(editWindow.getSelectedFileNum());

			if (window.getList().contains(file)) {
				window.getList().remove(file);

			}
			searchAudioWindow.getList().remove(editWindow.getSelectedFileNum());

			if (file instanceof model.MP3File) {
				mp3Edit(track, newFile, searchAudioWindow);
			}

			else if (file instanceof model.WavFile) {
				wavEdit(track, newFile, searchAudioWindow);
			}

			editWindow.getArtistField().setText("");
			editWindow.getTitleField().setText("");
			editWindow.getAlbumField().setText("");
			editWindow.getTrackField().setText("");
			editWindow.getPathNameField().setText("");

			editWindow.dispose();

			String[] clearArray = { "" };

			searchAudioWindow.getSelectedAudioList().setListData(clearArray);
			searchAudioWindow.getEditButton().setEnabled(false);
			searchAudioWindow.getDeleteButton().setEnabled(false);
			searchAudioWindow.getPlayButton().setEnabled(false);
		}
	}

	/**
	 * 
	 * @param track
	 * @param newFile
	 */
	private void mp3Edit(int track, model.AudioFile newFile,
			SearchAudioWindow searchAudioWindow) {
		String artist = editWindow.getArtistField().getText();
		String title = editWindow.getTitleField().getText();
		String album = editWindow.getAlbumField().getText();
		try {
			track = Integer.parseInt(editWindow.getTrackField().getText());
		} catch (NumberFormatException g) {
		}
		String pathName = editWindow.getPathNameField().getText();
		String label = editWindow.getLabelField().getText();
		int bitRate = Integer.parseInt(editWindow.getBitRateField().getText());

		newFile = new model.MP3File(artist, title, album, track, pathName,
				label, bitRate);

		if (searchAudioWindow == null)
			window.getList().add(newFile);

		else {
			searchAudioWindow.getList().add(newFile);
			window.getList().add(newFile);
		}

		editWindow.getLabelField().setText("");
		editWindow.getBitRateField().setText("");
	}

	/**
	 * 
	 * @param track
	 * @param newFile
	 */
	private void wavEdit(int track, model.AudioFile newFile,
			SearchAudioWindow searchAudioWindow) {
		String artist = editWindow.getArtistField().getText();
		String title = editWindow.getTitleField().getText();
		String album = editWindow.getAlbumField().getText();
		try {
			track = Integer.parseInt(editWindow.getTrackField().getText());
		} catch (NumberFormatException f) {

		}
		String pathName = editWindow.getPathNameField().getText();
		long length = Long.parseLong(editWindow.getLengthField().getText());

		newFile = new model.WavFile(artist, title, album, track, pathName,
				length);

		if (searchAudioWindow == null)
			window.getList().add(newFile);

		else {
			searchAudioWindow.getList().add(newFile);
			window.getList().add(newFile);
		}

		editWindow.getLengthField().setText("");
	}

	/**
	 * 
	 */
	private void searchArtist() {
		try {
			if (searchWindow.getSearchArtistField().getText().trim().length() > 0)
				if (resultSet == null)
					resultSet = getResultSet(ARTIST, searchWindow
							.getSearchArtistField().getText(), window);
				else {
					newResultSet = getResultSet(ARTIST, searchWindow
							.getSearchArtistField().getText(), window);
					resultSet = resultSet.intersection(newResultSet);

				} // end else
			searchWindow.getSearchArtistField().setText("");
		} catch (NullPointerException e) {
		}
	}

	/**
	 * 
	 */
	private void searchAlbum() {
		try {
			if (searchWindow.getSearchAlbumField().getText().trim().length() > 0)
				if (resultSet == null)
					resultSet = getResultSet(ALBUM, searchWindow
							.getSearchAlbumField().getText(), window);
				else {
					newResultSet = getResultSet(ALBUM, searchWindow
							.getSearchAlbumField().getText(), window);
					resultSet = resultSet.intersection(newResultSet);

				} // end else
			searchWindow.getSearchAlbumField().setText("");
		}

		catch (NullPointerException e) {
		}
	}

	/**
	 * 
	 */
	private void searchTitle() {
		try {
			if (searchWindow.getSearchTitleField().getText().trim().length() > 0)
				if (resultSet == null)
					resultSet = getResultSet(TITLE, searchWindow
							.getSearchTitleField().getText(), window);
				else {
					newResultSet = getResultSet(TITLE, searchWindow
							.getSearchTitleField().getText(), window);
					resultSet = resultSet.intersection(newResultSet);

				} // end else
			searchWindow.getSearchTitleField().setText("");
		}

		catch (NullPointerException e) {
		}
	}

}
