package view.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import model.AudioFile;
import model.MP3File;
import model.WavFile;

public class EditFilesWindow extends JDialog {

	public static final int ROWS = 6;
	public static final int COLUMNS = 6;

	private AudioWindow window;

	private SearchAudioWindow searchWindow;

	private JList wavList;
	private JList mp3List;
	public JLabel joseph;
	private JPanel topBlankPanel;
	private JLabel blackLabel;
	private JPanel nullPanel;

	private JPanel songInfoPanel;
	private JPanel EditOrCancelPanel;
	private ImageIcon background;

	private JPanel BottomButtonsPanel;

	private JLabel artistLabel;
	private JLabel titleLabel;
	private JLabel albumLabel;
	private JLabel trackLabel;
	private JLabel labelLabel;
	private JLabel pathNameLabel;
	private JLabel bitRateLabel;

	private JPanel artistJTextFieldFlow;
	private JPanel titleJTextFieldFlow;
	private JPanel albumJTextFieldFlow;
	private JPanel trackJTextFieldFlow;
	private JPanel labelJTextFieldFlow;
	private JPanel pathNameJTextFieldFlow;
	private JPanel bitRateJTextFieldFlow;

	private JTextField artistField;
	private JTextField titleField;
	private JTextField albumField;
	private JTextField trackField;
	private JTextField pathNameField;
	private JTextField labelField;
	private JTextField bitRateField;

	private JButton editButton;
	private JButton cancelButton;

	private final int ADD_WINDOW_WIDTH = 500; // Window width
	private final int ADD_WINDOW_HEIGHT = 600; // Window height

	private AudioFile originalData;

	private String orgAlbum;
	private String orgArtist;
	private String orgTitle;
	private int orgTrack;
	private String orgPathName;
	private JTextField lengthField;
	private int selectedFileNum;
	private int orgBitRate;
	private String orgLabel;
	private JPanel lengthJTextFieldFlow;
	private JLabel lengthLabel;
	private long orgLength;

	public EditFilesWindow(AudioWindow audWindow, AudioFile originalFileInfo,
			int oldFileNum) {
		window = audWindow;
		selectedFileNum = oldFileNum;
		originalData = originalFileInfo;

		setTitle("Edit songs");

		// build window
		buildEditWindow();

		// buildPanels
		buildEditOrCancelPanelForNormal();

		if (originalFileInfo instanceof model.MP3File)
			buildMP3InfoPanel();

		else if (originalFileInfo instanceof model.WavFile)
			buildWavInfoPanel();

		buildTopBlankPanel();

		// add panels to window
		add(topBlankPanel, BorderLayout.NORTH);
		add(songInfoPanel, BorderLayout.CENTER);
		add(BottomButtonsPanel, BorderLayout.SOUTH);

		setModal(true);
		setLocationRelativeTo(null);
		setVisible(true);
	}

	public EditFilesWindow(SearchAudioWindow searchWin,
			AudioFile originalFileInfo, int oldFileNum, AudioWindow audWin) {
		searchWindow = searchWin;
		selectedFileNum = oldFileNum;
		originalData = originalFileInfo;
		window = audWin;

		setTitle("Edit songs");

		// build window
		buildEditWindow();

		// buildPanels
		buildEditOrCancelPanelForSearch();

		if (originalFileInfo instanceof model.MP3File)
			buildMP3InfoPanel();

		else if (originalFileInfo instanceof model.WavFile)
			buildWavInfoPanel();

		buildTopBlankPanel();

		// add panels to window
		add(topBlankPanel, BorderLayout.NORTH);
		add(songInfoPanel, BorderLayout.CENTER);
		add(BottomButtonsPanel, BorderLayout.SOUTH);

		setModal(true);
		setLocationRelativeTo(null);
		setVisible(true);
	}

	private void buildEditWindow() {
		// Set the size of the window.
		setSize(ADD_WINDOW_WIDTH, ADD_WINDOW_HEIGHT);

		// Specify an action for the close button.

		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		// Add a BorderLayout manager.

		setLayout(new BorderLayout());

	}

	private void buildTopBlankPanel() {
		topBlankPanel = new JPanel();
		nullPanel = new JPanel();

		joseph = new JLabel();

		nullPanel.add(joseph);

		topBlankPanel.setLayout(new GridLayout(ROWS, COLUMNS));

		for (int index = 0; index <= ROWS * COLUMNS; index++) {
			topBlankPanel.add(nullPanel);
		}

	}

	private void buildMP3InfoPanel() {

		artistJTextFieldFlow = new JPanel();
		titleJTextFieldFlow = new JPanel();
		albumJTextFieldFlow = new JPanel();
		trackJTextFieldFlow = new JPanel();
		labelJTextFieldFlow = new JPanel();
		pathNameJTextFieldFlow = new JPanel();
		bitRateJTextFieldFlow = new JPanel();

		artistJTextFieldFlow.setLayout(new FlowLayout());
		titleJTextFieldFlow.setLayout(new FlowLayout());
		albumJTextFieldFlow.setLayout(new FlowLayout());
		trackJTextFieldFlow.setLayout(new FlowLayout());
		labelJTextFieldFlow.setLayout(new FlowLayout());
		pathNameJTextFieldFlow.setLayout(new FlowLayout());
		bitRateJTextFieldFlow.setLayout(new FlowLayout());

		songInfoPanel = new JPanel();

		songInfoPanel.setLayout(new GridLayout(8, 2));
		songInfoPanel.setBorder(BorderFactory.createLineBorder(Color.GREEN, 3));

		artistLabel = new JLabel("Artist:");
		artistLabel.setHorizontalAlignment(SwingConstants.CENTER);
		titleLabel = new JLabel("Title:");
		titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
		albumLabel = new JLabel("Album:");
		albumLabel.setHorizontalAlignment(SwingConstants.CENTER);
		trackLabel = new JLabel("Track:");
		trackLabel.setHorizontalAlignment(SwingConstants.CENTER);
		labelLabel = new JLabel("Label:");
		labelLabel.setHorizontalAlignment(SwingConstants.CENTER);
		pathNameLabel = new JLabel("Path Name:");
		pathNameLabel.setHorizontalAlignment(SwingConstants.CENTER);
		bitRateLabel = new JLabel("Bit Rate:");
		bitRateLabel.setHorizontalAlignment(SwingConstants.CENTER);

		artistField = new JTextField(10);
		titleField = new JTextField(10);
		albumField = new JTextField(10);
		trackField = new JTextField(10);
		labelField = new JTextField(10);
		pathNameField = new JTextField(10);
		bitRateField = new JTextField(10);

		artistJTextFieldFlow.add(artistField);
		titleJTextFieldFlow.add(titleField);
		albumJTextFieldFlow.add(albumField);
		trackJTextFieldFlow.add(trackField);
		labelJTextFieldFlow.add(labelField);
		pathNameJTextFieldFlow.add(pathNameField);
		bitRateJTextFieldFlow.add(bitRateField);

		orgAlbum = originalData.getAlbum();
		orgArtist = originalData.getArtist();
		orgTitle = originalData.getTitle();
		orgTrack = originalData.getTrack();
		orgPathName = originalData.getPathName();
		orgBitRate = ((MP3File) originalData).getBitRate();
		orgLabel = ((MP3File) originalData).getLabel();

		artistField.setText(orgArtist);
		artistField.setName("Artist");
		artistField.setInputVerifier(new TunesListener(this, window));
		titleField.setText(orgTitle);
		titleField.setName("Title");
		titleField.setInputVerifier(new TunesListener(this, window));
		albumField.setText(orgAlbum);
		trackField.setText(orgTrack + "");
		trackField.setName("Track");
		trackField.setInputVerifier(new TunesListener(this, window));
		pathNameField.setText(orgPathName);
		pathNameField.setEditable(false);
		bitRateField.setText(orgBitRate + "");
		bitRateField.setEditable(false);
		labelField.setText(orgLabel);

		songInfoPanel.add(artistLabel);
		songInfoPanel.add(artistJTextFieldFlow);

		songInfoPanel.add(titleLabel);
		songInfoPanel.add(titleJTextFieldFlow);

		songInfoPanel.add(albumLabel);
		songInfoPanel.add(albumJTextFieldFlow);

		songInfoPanel.add(trackLabel);
		songInfoPanel.add(trackJTextFieldFlow);

		songInfoPanel.add(labelLabel);
		songInfoPanel.add(labelJTextFieldFlow);

		songInfoPanel.add(pathNameLabel);
		songInfoPanel.add(pathNameJTextFieldFlow);

		songInfoPanel.add(bitRateLabel);
		songInfoPanel.add(bitRateJTextFieldFlow);

	}

	private void buildWavInfoPanel() {

		artistJTextFieldFlow = new JPanel();
		titleJTextFieldFlow = new JPanel();
		albumJTextFieldFlow = new JPanel();
		trackJTextFieldFlow = new JPanel();
		pathNameJTextFieldFlow = new JPanel();
		lengthJTextFieldFlow = new JPanel();

		artistJTextFieldFlow.setLayout(new FlowLayout());
		titleJTextFieldFlow.setLayout(new FlowLayout());
		albumJTextFieldFlow.setLayout(new FlowLayout());
		trackJTextFieldFlow.setLayout(new FlowLayout());
		pathNameJTextFieldFlow.setLayout(new FlowLayout());
		lengthJTextFieldFlow.setLayout(new FlowLayout());

		songInfoPanel = new JPanel();

		songInfoPanel.setLayout(new GridLayout(8, 2));
		songInfoPanel.setBorder(BorderFactory.createLineBorder(Color.GREEN, 3));

		artistLabel = new JLabel("Artist:");
		artistLabel.setHorizontalAlignment(SwingConstants.CENTER);
		titleLabel = new JLabel("Title:");
		titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
		albumLabel = new JLabel("Album:");
		albumLabel.setHorizontalAlignment(SwingConstants.CENTER);
		trackLabel = new JLabel("Track:");
		trackLabel.setHorizontalAlignment(SwingConstants.CENTER);
		pathNameLabel = new JLabel("Path Name:");
		pathNameLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lengthLabel = new JLabel("Length:");
		lengthLabel.setHorizontalAlignment(SwingConstants.CENTER);

		artistField = new JTextField(10);
		titleField = new JTextField(10);
		albumField = new JTextField(10);
		trackField = new JTextField(10);
		pathNameField = new JTextField(10);
		lengthField = new JTextField(10);

		artistJTextFieldFlow.add(artistField);
		titleJTextFieldFlow.add(titleField);
		albumJTextFieldFlow.add(albumField);
		trackJTextFieldFlow.add(trackField);
		pathNameJTextFieldFlow.add(pathNameField);
		lengthJTextFieldFlow.add(lengthField);

		orgAlbum = originalData.getAlbum();
		orgArtist = originalData.getArtist();
		orgTitle = originalData.getTitle();
		orgTrack = originalData.getTrack();
		orgPathName = originalData.getPathName();
		orgLength = ((WavFile) originalData).getLength();

		artistField.setText(orgArtist);
		artistField.setName("Artist");
		artistField.setInputVerifier(new TunesListener(this, window));
		titleField.setText(orgTitle);
		titleField.setName("Title");
		titleField.setInputVerifier(new TunesListener(this, window));
		albumField.setText(orgAlbum);
		trackField.setText(orgTrack + "");
		trackField.setName("Track");
		trackField.setInputVerifier(new TunesListener(this, window));
		pathNameField.setText(orgPathName);
		pathNameField.setEditable(false);
		lengthField.setText(orgLength + "");
		lengthField.setEditable(false);

		songInfoPanel.add(artistLabel);
		songInfoPanel.add(artistJTextFieldFlow);

		songInfoPanel.add(titleLabel);
		songInfoPanel.add(titleJTextFieldFlow);

		songInfoPanel.add(albumLabel);
		songInfoPanel.add(albumJTextFieldFlow);

		songInfoPanel.add(trackLabel);
		songInfoPanel.add(trackJTextFieldFlow);

		songInfoPanel.add(pathNameLabel);
		songInfoPanel.add(pathNameJTextFieldFlow);

		songInfoPanel.add(lengthLabel);
		songInfoPanel.add(lengthJTextFieldFlow);

	}

	private void buildEditOrCancelPanelForNormal() {
		// Create the finishPanel
		EditOrCancelPanel = new JPanel();
		BottomButtonsPanel = new JPanel();
		nullPanel = new JPanel();
		// Set the layout of the finishPanel
		EditOrCancelPanel.setLayout(new FlowLayout());
		BottomButtonsPanel.setLayout(new GridLayout(3, 3));

		// Create the finishedButton and give it the name "Finished Editing"
		editButton = new JButton();
		editButton.setText("Confirm");
		editButton.setName("ConfirmEditNormal");
		editButton.addActionListener(new TunesListener(this, window));

		cancelButton = new JButton();
		cancelButton.setText("Cancel");
		cancelButton.setName("Cancel Edit");
		cancelButton.addActionListener(new TunesListener(this, window));

		// Add the EditOrCancelButton to the EditOrCancelPanel
		EditOrCancelPanel.add(editButton);
		EditOrCancelPanel.add(cancelButton);

		// Add the EditOrCancelPanel to the BottomButtonsPanel
		BottomButtonsPanel.add(nullPanel);
		BottomButtonsPanel.add(nullPanel);
		BottomButtonsPanel.add(nullPanel);
		BottomButtonsPanel.add(nullPanel);
		BottomButtonsPanel.add(EditOrCancelPanel);
		BottomButtonsPanel.add(nullPanel);
		BottomButtonsPanel.add(nullPanel);
		BottomButtonsPanel.add(nullPanel);
		BottomButtonsPanel.add(nullPanel);

	}

	private void buildEditOrCancelPanelForSearch() {
		// Create the finishPanel
		EditOrCancelPanel = new JPanel();
		BottomButtonsPanel = new JPanel();
		nullPanel = new JPanel();
		// Set the layout of the finishPanel
		EditOrCancelPanel.setLayout(new FlowLayout());
		BottomButtonsPanel.setLayout(new GridLayout(3, 3));

		// Create the finishedButton and give it the name "Finished Editing"
		editButton = new JButton();
		editButton.setText("Confirm");
		editButton.setName("ConfirmEditSearch");
		editButton.addActionListener(new TunesListener(this, searchWindow,
				window));

		cancelButton = new JButton();
		cancelButton.setText("Cancel");
		cancelButton.setName("Cancel Edit");
		cancelButton.addActionListener(new TunesListener(this, searchWindow,
				window));

		// Add the EditOrCancelButton to the EditOrCancelPanel
		EditOrCancelPanel.add(editButton);
		EditOrCancelPanel.add(cancelButton);

		// Add the EditOrCancelPanel to the BottomButtonsPanel
		BottomButtonsPanel.add(nullPanel);
		BottomButtonsPanel.add(nullPanel);
		BottomButtonsPanel.add(nullPanel);
		BottomButtonsPanel.add(nullPanel);
		BottomButtonsPanel.add(EditOrCancelPanel);
		BottomButtonsPanel.add(nullPanel);
		BottomButtonsPanel.add(nullPanel);
		BottomButtonsPanel.add(nullPanel);
		BottomButtonsPanel.add(nullPanel);

	}

	public String[] getAudioInfo() {
		String[] audioInfo = new String[7];

		audioInfo[0] = artistField.getText();
		audioInfo[1] = titleField.getText();
		audioInfo[2] = albumField.getText();
		audioInfo[3] = trackField.getText();

		audioInfo[4] = labelField.getText();
		audioInfo[5] = pathNameField.getText();
		audioInfo[6] = bitRateField.getText();

		return audioInfo;

	} // method getAudioInfo

	// String version of JTextField
	public JTextField getArtistField() {
		return artistField;
	}

	// String version of JTextField
	public JTextField getTitleField() {
		return titleField;
	}

	// String version of JTextField
	public JTextField getAlbumField() {
		return albumField;
	}

	// String version of JTextField
	public JTextField getTrackField() {
		return trackField;
	}

	// String version of JTextField
	public JTextField getBitRateField() {
		return bitRateField;
	}

	// String version of JTextField
	public JTextField getLabelField() {
		return labelField;
	}

	// String version of JTextField
	public JTextField getPathNameField() {
		return pathNameField;
	}

	public JTextField getLengthField() {
		return lengthField;
	}

	public int getSelectedFileNum() {
		return selectedFileNum;
	}

	public JList getWavList() {
		return wavList;
	}

	public JList getMp3List() {
		return mp3List;
	}

	public String getOrgArtist() {
		return orgArtist;
	}

	public String getOrgTitle() {
		return orgTitle;
	}

	public String getOrgAlbum() {
		return orgAlbum;
	}

	public int getOrgTrack() {
		return orgTrack;
	}

}
