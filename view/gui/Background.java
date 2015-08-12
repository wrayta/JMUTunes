package view.gui;

import javax.swing.*;
//Java Extension Package   
import java.awt.*;

public class Background extends JPanel {

	// Initializing the class Image
	Image background;

	// Setting up GUI
	public Background(EditFilesWindow editWindow) {

		// Constructing the class "Toolkit" which will be used to manipulate our
		// images.
		Toolkit kit = Toolkit.getDefaultToolkit();

		// Getting the "background.jpg" image we have in the folder
		background = kit.getImage("neonPanther.jpg");
	}

	// Manipulate Images with JAVA2D API. . creating a paintComponent method.
	public void paintComponent(Graphics comp) {

		// Constructing the class Graphics2D. Create 2D by casting the "comp" to
		// Graphics2D
		Graphics2D comp2D = (Graphics2D) comp;

		// creating a graphics2d using the images in the folder and place it in
		// a specific coordinates.
		comp2D.drawImage(background, 0, 0, this);
	}
}