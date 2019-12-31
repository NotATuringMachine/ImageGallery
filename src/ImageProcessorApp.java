import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Class for UI component of the application
 */
public class ImageProcessorApp extends JFrame {

    private ArrayList<ImageHolder> images; //Holds all the images currently in the application
    private int current_image_index;
    private BufferedImage current_displayed_image;
    private JLabel image_label; // JLabel used to display image
    private JMenuBar menu_bar;

    public ImageProcessorApp(){
        super("Image Processor");
        setLayout( new FlowLayout());
        setSize(1000, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        images = new ArrayList<>();
        image_label = new JLabel();
        current_image_index = -1;
        add(image_label);

        setupMenu();
        setVisible(true);
    }

    /**
     *  Used to setup application UI
     */
    private void setupMenu(){
        menu_bar = new JMenuBar();
        setJMenuBar(menu_bar);
        setupFileDropDownMenu();
        setupProcessDropDownMenu();
    }

    /**
     * Sets up the file drop down menu
     */
    private void setupFileDropDownMenu(){
        JMenu file_menu = new JMenu("File");
        menu_bar.add(file_menu);

        //Create and add image button to read image in.
        JMenuItem file_menu_open = new JMenuItem("Add Image");
        file_menu.add(file_menu_open);
        file_menu_open.addActionListener( //ActionListener to read in image file
                e -> {
                    JFileChooser file_chooser = new JFileChooser(".");
                    int ret_val = file_chooser.showOpenDialog(ImageProcessorApp.this);
                    if (ret_val == JFileChooser.APPROVE_OPTION) {
                        File f = file_chooser.getSelectedFile();
                        BufferedImage buff_image;
                        try {
                            buff_image = ImageIO.read(f);
                            ImageHolder image = new ImageHolder(buff_image);
                            images.add(image);
                            current_image_index = images.size() - 1;
                            setDisplayImage(image.getOriginalImage());
                        } catch ( IOException err){
                            err.printStackTrace();
                        }
                    }
                }
        );

        //Add save image button to file menu
        JMenuItem file_menu_save = new JMenuItem("Save Image");
        file_menu.add(file_menu_save);
        file_menu_save.addActionListener(
                e -> {
                    JFileChooser file_chooser = new JFileChooser(".");
                    int ret_val = file_chooser.showSaveDialog(ImageProcessorApp.this);
                    if (ret_val == JFileChooser.APPROVE_OPTION) {
                        try (FileWriter fw = new FileWriter(file_chooser.getSelectedFile())){
                            ImageIO.write(getDisplayedImage(), "jpg", (file_chooser.getSelectedFile()));
                        } catch (IOException err) {
                            err.printStackTrace();
                        }
                    }
                }
        );

        // Add quit button to file menu
        JMenuItem file_menu_quit = new JMenuItem("Quit");
        file_menu.add(file_menu_quit);
        file_menu_quit.addActionListener(
                e -> {
                    System.exit(0);
                }
        );
    }

    /**
     *  Sets up the process drop down menu
     */
    private void setupProcessDropDownMenu(){
        JMenu process_menu = new JMenu("Process Image");
        menu_bar.add(process_menu);
        JMenuItem process_menu_original = new JMenuItem("Original Image");
        JMenuItem process_menu_greyscale = new JMenuItem("Greyscale Filter");
        JMenuItem process_menu_negative = new JMenuItem("Negative Filter");
        JMenuItem process_menu_sepia = new JMenuItem("Sepia Filter");
        JMenuItem process_menu_cartoon = new JMenuItem("Cartoon Filter");
        JMenuItem process_menu_false = new JMenuItem("Increase Contrast");
        JMenuItem process_menu_threshold = new JMenuItem("Threshold Image");
        JMenuItem process_menu_boxBlur = new JMenuItem("Box Blur");
        JMenuItem process_menu_gaussianBlur = new JMenuItem("Gaussian Blur");
        JMenuItem process_menu_sobelEdge = new JMenuItem("Sobel Edge Detection");
        JMenuItem process_menu_blurredSobel = new JMenuItem("Sobel Edge Detection (Pre-blurred)");
        JMenuItem process_menu_pixelate = new JMenuItem("Pixelate");

        // Add JMenuItems to drop down menu
        process_menu.add(process_menu_original);
        process_menu.add(process_menu_greyscale);
        process_menu.add(process_menu_negative);
        process_menu.add(process_menu_sepia);
        process_menu.add(process_menu_cartoon);
        process_menu.add(process_menu_false);
        process_menu.add(process_menu_threshold);
        process_menu.add(process_menu_boxBlur);
        process_menu.add(process_menu_gaussianBlur);
        process_menu.add(process_menu_sobelEdge);
        process_menu.add(process_menu_blurredSobel);
        process_menu.add(process_menu_pixelate);

        //Add ActionListeners to each JMenuItem
        process_menu_original.addActionListener (
                e -> {
                    setDisplayImage(images.get(current_image_index).getOriginalImage());
                }
        );

        process_menu_greyscale.addActionListener(
                e -> {
                    setDisplayImage(images.get(current_image_index).applyGreyscaleFilter());
                }
        );

        process_menu_negative.addActionListener(
                e -> {
                    setDisplayImage(images.get(current_image_index).applyNegativeFilter());
                }
        );

        process_menu_sepia.addActionListener(
                e -> {
                    setDisplayImage(images.get(current_image_index).applySepiaFilter());
                }
        );

        process_menu_cartoon.addActionListener(
                e -> {
                    setDisplayImage(images.get(current_image_index).applyCartoonFilter());
                }
        );

        process_menu_false.addActionListener(
                e -> {
                    setDisplayImage(images.get(current_image_index).applyContrastEnhancement());
                }
        );

        process_menu_threshold.addActionListener(
                e -> {
                    setDisplayImage(images.get(current_image_index).thresholdImage());
                }
        );

        process_menu_boxBlur.addActionListener(
                e -> {
                    setDisplayImage(images.get(current_image_index).applyBoxBlur());
                }
        );

        process_menu_gaussianBlur.addActionListener(
                e -> {
                    setDisplayImage(images.get(current_image_index).applyGaussianBlur());
                }
        );

        process_menu_sobelEdge.addActionListener(
                e -> {
                    setDisplayImage(images.get(current_image_index).applySobelOperator());
                }
        );

        process_menu_blurredSobel.addActionListener(
                e -> {
                    setDisplayImage(images.get(current_image_index).applyPreblurredSobelOperator());
                }
        );

        process_menu_pixelate.addActionListener(
                e -> {
                    setDisplayImage(images.get(current_image_index).pixelate());
                }
        );
    }

    /** Sets an image into the current display
     * @param image image to be displayed
     */
    private void setDisplayImage(BufferedImage image){
        current_displayed_image = image;
        this.image_label.setIcon(new ImageIcon(image));
    }

    /**
     *  Retrieves the image currently being displayed
     * @return current displayed image
     */
    private BufferedImage getDisplayedImage(){
        return current_displayed_image;
    }
}
