package view.gui;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class AddFilesWindow extends JDialog {
	private AudioWindow window;

	private JPanel addMainPanel;
	private JPanel nullPanel;
	private JPanel topPanel;
	private JLabel message;
	private JCheckBox mp3CheckBox;
	private JCheckBox wavCheckBox;
	private JButton goButton;

	private final int ADD_WINDOW_WIDTH = 700; // Window width
	private final int ADD_WINDOW_HEIGHT = 400; // Window height
	private JPanel checkBoxPanel;
	private JPanel startingFolderPanel;
	private JLabel directoryPrompt;
	private JTextField directoryField;
	private JPanel messagePanel;
	private JPanel mp3CheckBoxPanel;
	private JPanel wavCheckBoxPanel;

	private JButton cancelButton;
	private JPanel bottomPanel;
	private JPanel inBottomPanel;

	public AddFilesWindow(AudioWindow audWindow) {
		window = audWindow;
		setTitle("Load songs");
		buildAddWindow();
		buildCheckBoxPanel();
		buildMessagePanel();
		buildStartingFolderPanel();

		add(checkBoxPanel, BorderLayout.CENTER);
		add(topPanel, BorderLayout.NORTH);
		add(bottomPanel, BorderLayout.SOUTH);

		setLocationRelativeTo(null);
		setModal(true);
		setVisible(true);
	}

	private void buildAddWindow() {
		// Set the size of the window.
		setSize(ADD_WINDOW_WIDTH, ADD_WINDOW_HEIGHT);

		// Specify an action for the close button.

		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		// Add a BorderLayout manager.

		setLayout(new BorderLayout());

		// Add the addMainPanel to the addWindow.

	}

	private void buildCheckBoxPanel() {
		// Create the checkBoxPanel
		checkBoxPanel = new JPanel();

		// Create the nullPanel
		nullPanel = new JPanel();

		// Create the mp3CheckBoxPanel
		mp3CheckBoxPanel = new JPanel();

		// Create the wavCheckBoxPanel
		wavCheckBoxPanel = new JPanel();

		// Set the layout of the checkBoxPanel
		checkBoxPanel.setLayout(new GridLayout(5, 3));

		// Create the wav and mp3 checkboxes
		mp3CheckBox = new JCheckBox("MP3 Files", false);
		wavCheckBox = new JCheckBox("Wav Files", false);
		mp3CheckBox.setName("MP3");
		wavCheckBox.setName("Wav");

		// Add the checkBoxes to the respective panels
		mp3CheckBoxPanel.add(mp3CheckBox);
		wavCheckBoxPanel.add(wavCheckBox);

		// Add the checkBoxes to the checkBoxPanel
		checkBoxPanel.add(nullPanel);
		checkBoxPanel.add(nullPanel);
		checkBoxPanel.add(nullPanel);
		checkBoxPanel.add(nullPanel);
		checkBoxPanel.add(nullPanel);
		checkBoxPanel.add(nullPanel);
		checkBoxPanel.add(nullPanel);
		checkBoxPanel.add(mp3CheckBoxPanel);
		checkBoxPanel.add(nullPanel);
		checkBoxPanel.add(nullPanel);
		checkBoxPanel.add(wavCheckBoxPanel);
		checkBoxPanel.add(nullPanel);
		checkBoxPanel.add(nullPanel);
		checkBoxPanel.add(nullPanel);
		checkBoxPanel.add(nullPanel);

	}

	private void buildMessagePanel() {
		// Create the topPanel
		topPanel = new JPanel();

		// Set the layout of the topPanel
		topPanel.setLayout(new GridLayout(3, 3));

		messagePanel = new JPanel();

		messagePanel.setLayout(new FlowLayout());

		// Create the message
		message = new JLabel(
				"1. Check the corresponding boxes for the types of audio files you wish to add");

		messagePanel.add(message);

		// Add the message to the topPanel
		topPanel.add(nullPanel);
		topPanel.add(nullPanel);
		topPanel.add(nullPanel);
		topPanel.add(nullPanel);
		topPanel.add(messagePanel);
		topPanel.add(nullPanel);

	}

	private void buildStartingFolderPanel() {

		// Create the bottomPanel
		bottomPanel = new JPanel();

		// Create the inBottomPanel
		inBottomPanel = new JPanel();

		// Set the layout of the inBottomPanel
		inBottomPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 35));

		// Set the layout of the bottomPanel
		bottomPanel.setLayout(new BorderLayout());

		// Create the JLabel to prompt for directory
		directoryPrompt = new JLabel("2. Enter the name of the starting folder");

		// Create the JTextField for the user's input
		directoryField = new JTextField(10);

		// Add the label and textField to the startingFolderPanel
		inBottomPanel.add(directoryPrompt);
		inBottomPanel.add(directoryField);

		// Create the cancelButton and give it the name "Cancel"
		cancelButton = new JButton();
		cancelButton.setText("Cancel");
		cancelButton.setName("Cancel Add");
		cancelButton.addActionListener(new TunesListener(this, window));

		// Create the goButton and give it the name "Go"
		goButton = new JButton();
		goButton.setText("Go");
		goButton.setName("Go");
		goButton.addActionListener(new TunesListener(this, window));

		// Add the cancelButton to the inBottomPanel
		inBottomPanel.add(cancelButton);

		// Add the goButton to the inBottomPanel
		inBottomPanel.add(goButton);

		// Add the inBottomPanel to the north section of the bottomPanel
		bottomPanel.add(inBottomPanel, BorderLayout.NORTH);

	}

	public JCheckBox getMp3CheckBox() {
		return mp3CheckBox;
	}

	public JCheckBox getWavCheckBox() {
		return wavCheckBox;
	}

	public JTextField getDirectoryField() {
		return directoryField;
	}
}
