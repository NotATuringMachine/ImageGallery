package imageGallery;

import java.util.ArrayList;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.event.ComponentListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JFileChooser;
import javax.swing.ImageIcon;
import javax.imageio.ImageIO;

import java.io.File;
import java.io.IOException;



public class ImageGalleryApp extends JFrame implements ItemListener{

	private ArrayList<GalleryImage> images;
	private int current_image_index;
	private JLabel imageLabel;
	private JMenuBar menuBar;


	public ImageGalleryApp() {
		super("Image Gallery");
		setLayout(new FlowLayout());
		setSize(1000, 700);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		images = new ArrayList<>();
		imageLabel = new JLabel();
		current_image_index = -1;
		add(imageLabel);


		setupMenu();
		setVisible(true);
	}

	/**
	 * Sets up the menu bar for the GUI
	 */
	private void setupMenu(){
		menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		setupFileDropDownMenu();
		setUpProcessDropDownMenu();
	}

	/**
	 * Sets up the file drop down menu
	 */
	private void setupFileDropDownMenu(){
		JMenu fileMenu = new JMenu("File");
		menuBar.add(fileMenu);
		JMenuItem fileMenuOpen = new JMenuItem("Add Image");
		fileMenu.add(fileMenuOpen);
		fileMenuOpen.addActionListener(
			new ActionListener(){
				
				public void actionPerformed(ActionEvent e){
					JFileChooser fileChooser = new JFileChooser(".");
					int retval = fileChooser.showOpenDialog(ImageGalleryApp.this);
					if (retval == JFileChooser.APPROVE_OPTION){
						File f = fileChooser.getSelectedFile();

						BufferedImage bImage;
						try{
							bImage = ImageIO.read(f);
							GalleryImage image = new GalleryImage(bImage);
							images.add(image);
							current_image_index = images.size() - 1;
							setImage(image.getOriginalImage());
						}catch(IOException er){
							System.out.println("We have a problem");
						}
						
					}
				}
			});

		JMenuItem fileMenuQuit = new JMenuItem("Quit");
		fileMenu.add(fileMenuQuit);
		fileMenuQuit.addActionListener(
			new ActionListener() 
			{
				public void actionPerformed(ActionEvent e){
					System.exit(0);
				}
			}
		);

	}

	/**
	 * Sets up the process drop down menu
	 */
	private void setUpProcessDropDownMenu(){
		JMenu processMenu = new JMenu("Process");
		menuBar.add(processMenu);
		JMenuItem processMenuOriginal = new JMenuItem("Original Image");
		JMenuItem processMenuGreyScale = new JMenuItem("Greyscale Image");
		JMenuItem processMenuSepia = new JMenuItem("Sepia Image");
		processMenu.add(processMenuOriginal);
		processMenu.add(processMenuGreyScale);
		processMenu.add(processMenuSepia);

		processMenuOriginal.addActionListener(
			new ActionListener() {
				public void actionPerformed(ActionEvent e){
					setImage(images.get(current_image_index).getOriginalImage());
				}
			});

		processMenuGreyScale.addActionListener(
			new ActionListener() {
				public void actionPerformed(ActionEvent e){
					GalleryImage current_image = images.get(current_image_index);
					current_image.applyRGBtoGreyscale();
					setImage(current_image.getTransformedImage());
				}
			});
		
		processMenuSepia.addActionListener(
			new ActionListener() {
				public void actionPerformed(ActionEvent e){
					GalleryImage current_image = images.get(current_image_index);
					current_image.applyRGBtoSepia();
					setImage(current_image.getTransformedImage());
				}
			});

	}

	/** Sets an image into the current display
	 * @param image image to be displayed
	 */
	private void setImage(BufferedImage image){
		this.imageLabel.setIcon(new ImageIcon(image));
	}

	public void actionPerformed(ActionEvent e){

	}

	public void itemStateChanged(ItemEvent e){

	}

}