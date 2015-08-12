package view.gui;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.WindowListener;
import java.io.File;

import javax.swing.*;

import model.AudioFile;
import model.AudioList;
import model.MP3File;
import controller.edit.AudioEdit;
import controller.edit.MP3FileEdit;
import controller.edit.WavFileEdit;
import controller.file.AudioFileControl;
import controller.search.AudioFileSearch;

public class SearchAudioWindow extends JDialog {
	private int page;

	private AudioList current; // the current list (actual or search)
	private AudioList list; // the actual list

	private JMenuBar menuBar; // the menu bar
	private JMenu fileMenu; // the file menu
	private JMenu addMenu; // the add menu
	private JMenuItem exitItem; // to exit
	private JMenuItem addSubMenu; // the add sub menu

	private DefaultListModel model; // to be added to the JList

	private TunesListener listener; // a WindowListener object

	private JList normalList;
	private JList selectedAudioList;
	private JScrollPane scrollPane;
	private JScrollPane selectionScrollPane;
	private JPanel mainPanel;
	private JPanel selectedAudioPanel;
	private JPanel selectionAndOptionPanel;
	private JPanel gridPanel;

	private final int WINDOW_WIDTH = 800; // Window width
	private final int WINDOW_HEIGHT = 600; // Window height

	private JMenuItem addItem;

	private AbstractButton ImportMenu;

	private JMenu importMenu;

	private Container centerPanel;

	private JPanel centerPanelConstrain;

	private JButton editButton;
	private JButton searchButton;
	private JButton deleteButton;

	private Container editAndDeletePanel;

	private JPanel bottomPanel;

	private Component nullPanel;

	private AbstractButton quitSearchButton;

	private JButton playButton;

	public SearchAudioWindow(AudioWindow window, model.AudioList resultSet) {
		page = 0;

		list = resultSet;
		current = list;
		setModal(true);

		setTitle("JMUTunes Audio Player CS239 (Spring 2013) Search Results");

		// Set the size of the window.
		setSize(WINDOW_WIDTH, WINDOW_HEIGHT);

		// Specify an action for the close button.

		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		// Add a BorderLayout manager.

		setLayout(new BorderLayout());

		// Bring in the existing list of audioFiles

		// Instantiate the listener
		listener = new TunesListener(window, this);

		// Build the panels.
		buildMainPanel(list);
		// buildGridPanel();
		buildSelectedAudioPanel();
		buildSelectionAndOptionPanel();

		// Add the panels to the content pane.

		add(centerPanelConstrain, BorderLayout.CENTER);
		add(selectionAndOptionPanel, BorderLayout.SOUTH);

		// Build the menu bar
		buildMenuBar();

		// Pack and display the window.
		// pack();
		setLocationRelativeTo(null);
		setVisible(true);
	}

	private void buildMainPanel(model.AudioList list) {
		// create the defaultListModel
		if (list != null)
			model = getList().getModel();
		else
			model = null;

		// Create a panel to hold the list
		mainPanel = new JPanel();

		// Set the panel to a flowlayout
		mainPanel.setLayout(new FlowLayout());

		// Create the list of audiofiles
		if (list != null)
			normalList = new JList();
		else {
			Object[] nullArray = new Object[1];
			nullArray[0] = "                                                                              No Search Results";

			normalList = new JList(nullArray);
		}

		// Set the normalList to the model
		if (list != null)
			normalList.setModel(model);

		// Set the selection mode to single interval selection
		normalList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		// Set the number of visible rows to 16.
		normalList.setVisibleRowCount(16);
		normalList.setFixedCellWidth(600);

		// Add a border around the normal list
		normalList.setBorder(BorderFactory.createLineBorder(Color.GREEN, 5));

		normalList.addMouseListener(listener);

		// Add the list to a scroll pane.
		scrollPane = new JScrollPane(normalList);

		// Set the scrollbar policy
		scrollPane
				.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollPane
				.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

		// Add the scroll pane to the panel.
		mainPanel.add(scrollPane);

		mainPanel.add(Box.createVerticalGlue()); // first attempt; does NOT
		// take up available vertical space, instead it appears to create a
		// space
		// that is shared equally among the (now) four components of this space.
		centerPanelConstrain = new JPanel(new GridBagLayout());
		centerPanelConstrain.add(mainPanel);

	}

	private void buildSelectedAudioPanel() {
		if (list != null) {
			// Create a panel to hold the user's selection.
			selectedAudioPanel = new JPanel();

			// Create the selectedAudioList.
			selectedAudioList = new JList();

			// Set the number of visible rows to 1.
			selectedAudioList.setVisibleRowCount(1);

			selectedAudioList.addMouseListener(listener);

			// Add the list to a scroll pane.
			selectionScrollPane = new JScrollPane(selectedAudioList);

			selectionScrollPane
					.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
			selectionScrollPane
					.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);

			// Add the scroll pane to the panel.
			selectedAudioPanel.add(selectionScrollPane);
		}

	}

	private void buildSelectionAndOptionPanel() {
		// Create a panel to hold both the user's options and the selected song.
		selectionAndOptionPanel = new JPanel();

		editAndDeletePanel = new JPanel();

		editAndDeletePanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 10));

		nullPanel = new JPanel();

		bottomPanel = new JPanel();

		bottomPanel.setLayout(new BorderLayout());

		// Set the panel to a borderLayout
		selectionAndOptionPanel.setLayout(new BorderLayout(200, 200));

		if (list != null)
			bottomPanel.add(selectedAudioPanel, BorderLayout.SOUTH);

		// Build the edit and delete buttons
		buildPlayButton();
		buildEditButton();
		buildDeleteButton();
		buildSearchButton();

		// Add the editAndDeletePanel to the selectionAndOptionPanel
		bottomPanel.add(editAndDeletePanel, BorderLayout.NORTH);
		bottomPanel.add(nullPanel, BorderLayout.CENTER);
		// Add the bottomPanel to the selectionAndOptionPanel
		selectionAndOptionPanel.add(bottomPanel);

	}

	private void buildMenuBar() {
		// create the menu bar
		menuBar = new JMenuBar();

		// create file and add menus
		buildFileMenu();
		buildImportMenu();

		// add the file and import menus to the menu bar
		menuBar.add(fileMenu);
		menuBar.add(importMenu);

		// Set the window's menu bar
		setJMenuBar(menuBar);

	}

	private void buildFileMenu() {
		// Create an Exit menu item
		exitItem = new JMenuItem("Exit");
		exitItem.setMnemonic(KeyEvent.VK_X);
		exitItem.setName("Exit");
		exitItem.addActionListener(listener);

		// Create a JMenu object for the File menu
		fileMenu = new JMenu("File");
		fileMenu.setMnemonic(KeyEvent.VK_F);

		// Add the Exit menu item to the File menu.
		fileMenu.add(exitItem);

	}

	private void buildImportMenu() {

		// Create a JMenu object for the Import menu
		importMenu = new JMenu("Import");
		importMenu.setMnemonic(KeyEvent.VK_P);

		// Create a submenu for adding songs
		addSubMenu = new JMenuItem("Add");
		addSubMenu.setName("Add");
		addSubMenu.addActionListener(listener);

		// Add the mp3 and wav checkbox menu items to the addSubMenu

		// Add the addSubMenu item to the Import menu.
		importMenu.add(addSubMenu);
	}

	private void buildPlayButton() {
		playButton = new JButton();

		playButton.setText("   Play   ");

		playButton.setName("PlaySearch");

		playButton.setEnabled(false);

		playButton.addActionListener(listener);

		editAndDeletePanel.add(playButton);
	}

	private void buildEditButton() {
		editButton = new JButton();

		editButton.setText("   Edit   ");

		editButton.setName("EditSearch");

		editButton.setEnabled(false);

		editButton.addActionListener(listener);

		editAndDeletePanel.add(editButton);

	}

	private void buildDeleteButton() {
		deleteButton = new JButton();

		deleteButton.setText("Delete");

		deleteButton.setName("DeleteSearch");

		deleteButton.setEnabled(false);

		deleteButton.addActionListener(listener);

		editAndDeletePanel.add(deleteButton);
	}

	private void buildSearchButton() {
		quitSearchButton = new JButton();

		quitSearchButton.setText("Quit Search");

		quitSearchButton.setName("Quit Search");

		quitSearchButton.addActionListener(listener);

		editAndDeletePanel.add(quitSearchButton);
	}

	public JList getNormalList() {
		return normalList;
	}

	public JList getSelectedAudioList() {
		return selectedAudioList;
	}

	public JMenuItem getExitItem() {
		return exitItem;
	}

	public DefaultListModel getModel() {
		return model;
	}

	public model.AudioList getList() {
		return list;
	}

	public JButton getDeleteButton() {
		return deleteButton;
	}

	public JButton getEditButton() {
		return editButton;
	}

	public JButton getSearchButton() {
		return searchButton;

	}

	public JButton getPlayButton() {
		return playButton;
	}

}
