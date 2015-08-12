package view.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;

import model.AudioFile;
import model.MP3File;

public class SearchFilesWindow extends JDialog {
	public static final int ROWS = 6;
	public static final int COLUMNS = 6;

	private static AudioWindow window;

	private JList normalList;

	private TunesListener listener;

	private JScrollPane scrollPane;
	private JPanel centralizingBorderPanel;
	private JPanel internalGridPanel;
	private JPanel mainGridPanel;
	private JPanel headerPanel;
	private JPanel footerPanel;
	private JPanel buttonsInFooterPanel;
	private JPanel centralBorderPanel;
	private JLabel artistLabel;
	private JLabel titleLabel;
	private JLabel albumLabel;
	private JLabel directionsHeader;
	private JPanel artistFlowLabel;
	private JPanel titleFlowLabel;
	private JPanel albumFlowLabel;

	private JPanel artistEntryButtonFlowLayout;
	private JPanel titleEntryButtonFlowLayout;
	private JPanel albumEntryButtonFlowLayout;

	private JLabel nullLabel;
	private JPanel externalBorderPanel;
	private JTextField artist;
	private JTextField title;
	private JTextField album;
	private JButton artistNewEntryField;
	private JButton albumNewEntryField;
	private JButton titleNewEntryField;
	private JButton localSearchButton;
	private JButton cancelButton;
	private AudioFile originalData;
	private JLabel searchDirections;
	int selectedFileNum;
	private final int ADD_WINDOW_WIDTH = 863; // Window width
	private final int ADD_WINDOW_HEIGHT = 500; // Window height

	private String orgAlbum;
	private String orgArtist;
	private String orgTitle;
	private int orgTrack;
	private String orgPathName;
	private int orgBitRate;
	private String orgLabel;

	public SearchFilesWindow(AudioWindow audWindow) {

		window = audWindow;

		setTitle("Search");
		buildHeaderPanel();
		buildFooterPanel();
		buildMainGridPanel();
		buildSearchWindow();

		setModal(true);
		setLocationRelativeTo(null);
		setVisible(true);
	}

	private void buildSearchWindow() {
		// Set the size of the window.
		setSize(ADD_WINDOW_WIDTH, ADD_WINDOW_HEIGHT);

		// Specify an action for the close button.

		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		setLayout(new BorderLayout());

		add(headerPanel, BorderLayout.NORTH);
		add(centralBorderPanel, BorderLayout.CENTER);
		add(footerPanel, BorderLayout.SOUTH);

	}

	private void buildHeaderPanel() {
		headerPanel = new JPanel();
		searchDirections = new JLabel();
		directionsHeader = new JLabel();

		headerPanel.setLayout(new BorderLayout());

		directionsHeader.setText("DIRECTIONS FOR SEARCHING:");
		directionsHeader.setName("search directions header");
		directionsHeader.setFont(new Font("Serif", Font.BOLD, 24));
		directionsHeader.setHorizontalAlignment(SwingConstants.CENTER);

		searchDirections
				.setText("^ = starts with,   $ = ends with,   !() = does not contain,   "
						+ "NO SYMBOL = contains");
		searchDirections.setFont(new Font("Serif", Font.ITALIC, 24));
		searchDirections.setName("searchDirections");
		searchDirections.setHorizontalAlignment(SwingConstants.CENTER);

		headerPanel.add(directionsHeader, BorderLayout.NORTH);
		headerPanel.add(searchDirections, BorderLayout.SOUTH);
	}

	private void buildFooterPanel() {
		footerPanel = new JPanel();
		buttonsInFooterPanel = new JPanel(); // to get the buttons in a
												// flowlayout
		footerPanel.setLayout(new BorderLayout());
		buttonsInFooterPanel.setLayout(new FlowLayout());
		// Create the localSearchButton
		localSearchButton = new JButton();
		localSearchButton.setText("Find Song(s)");
		localSearchButton.setName("Find Song(s)");
		localSearchButton.addActionListener(new TunesListener(this, window));

		// Create the Cancel Button
		cancelButton = new JButton();
		cancelButton.setText("Cancel");
		cancelButton.setName("Cancel Search");
		cancelButton.addActionListener(new TunesListener(this, window));
		buttonsInFooterPanel.add(localSearchButton);
		buttonsInFooterPanel.add(cancelButton);

		footerPanel.add(buttonsInFooterPanel, BorderLayout.SOUTH);

	}

	private void buildMainGridPanel() {
		centralBorderPanel = new JPanel();
		centralBorderPanel.setLayout(new BorderLayout());

		mainGridPanel = new JPanel();

		mainGridPanel.setLayout(new BorderLayout());
		buildLeftGridPanel();
		mainGridPanel.add(externalBorderPanel, BorderLayout.NORTH);
		centralBorderPanel.add(mainGridPanel, BorderLayout.CENTER);
	}

	private void buildLeftGridPanel() {

		artistEntryButtonFlowLayout = new JPanel();
		titleEntryButtonFlowLayout = new JPanel();
		albumEntryButtonFlowLayout = new JPanel();

		artistNewEntryField = new JButton();
		albumNewEntryField = new JButton();
		titleNewEntryField = new JButton();

		externalBorderPanel = new JPanel();
		;
		internalGridPanel = new JPanel();
		nullLabel = new JLabel();

		titleFlowLabel = new JPanel();
		artistFlowLabel = new JPanel();
		albumFlowLabel = new JPanel();

		externalBorderPanel.setLayout(new GridLayout(2, 1));

		titleFlowLabel.setLayout(new FlowLayout());
		artistFlowLabel.setLayout(new FlowLayout());
		albumFlowLabel.setLayout(new FlowLayout());

		internalGridPanel.setLayout(new GridLayout(3, 3));

		artistLabel = new JLabel("Artist:");
		artistLabel.setFont(new Font("Serif", Font.ITALIC, 24));
		artistLabel.setHorizontalAlignment(SwingConstants.CENTER);

		artist = new JTextField(24);
		artist.setFont(new Font("Serif", Font.PLAIN, 20));
		artist.setHorizontalAlignment(SwingConstants.CENTER);

		titleLabel = new JLabel("Title:");
		titleLabel.setFont(new Font("Serif", Font.ITALIC, 24));
		titleLabel.setHorizontalAlignment(SwingConstants.CENTER);

		title = new JTextField(24);
		title.setHorizontalAlignment(SwingConstants.CENTER);
		title.setFont(new Font("Serif", Font.PLAIN, 20));

		albumLabel = new JLabel("Album:");
		albumLabel.setFont(new Font("Serif", Font.ITALIC, 24));
		albumLabel.setHorizontalAlignment(SwingConstants.CENTER);

		album = new JTextField(24);
		album.setFont(new Font("Serif", Font.PLAIN, 20));
		album.setHorizontalAlignment(SwingConstants.CENTER);

		artist.addActionListener(new TunesListener(this, window));
		title.addActionListener(new TunesListener(this, window));
		album.addActionListener(new TunesListener(this, window));

		artistNewEntryField.setText("Add Another Search Entry");
		artistNewEntryField.setName("Artist Entry Field Button");
		artistNewEntryField.addActionListener(new TunesListener(this, window));

		albumNewEntryField.setText("Add Another Search Entry");
		albumNewEntryField.setName("Album Entry Field Button");
		albumNewEntryField.addActionListener(new TunesListener(this, window));

		titleNewEntryField.setText("Add Another Search Entry");
		titleNewEntryField.setName("Title Entry Field Button");
		titleNewEntryField.addActionListener(new TunesListener(this, window));

		titleFlowLabel.add(title);
		artistFlowLabel.add(artist);
		albumFlowLabel.add(album);

		artistEntryButtonFlowLayout.add(artistNewEntryField);
		titleEntryButtonFlowLayout.add(titleNewEntryField);
		albumEntryButtonFlowLayout.add(albumNewEntryField);

		internalGridPanel.add(artistLabel);
		internalGridPanel.add(artistFlowLabel);
		internalGridPanel.add(artistEntryButtonFlowLayout);

		internalGridPanel.add(titleLabel);
		internalGridPanel.add(titleFlowLabel);
		internalGridPanel.add(titleEntryButtonFlowLayout);

		internalGridPanel.add(albumLabel);
		internalGridPanel.add(albumFlowLabel);
		internalGridPanel.add(albumEntryButtonFlowLayout);

		externalBorderPanel.add(nullLabel);
		externalBorderPanel.add(internalGridPanel);
	}

	private JTextField generateJTextField() {
		JTextField generatedJTextField = new JTextField();

		return generatedJTextField;
	}

	public JTextField getSearchArtistField() {
		return artist;
	}

	public JTextField getSearchAlbumField() {
		return album;
	}

	public JTextField getSearchTitleField() {
		return title;
	}

}
