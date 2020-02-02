import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
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
    private int window_width = 1000;
    private int window_height = 700;

    public ImageProcessorApp(){
        super("Image Processor");
        setLayout( new FlowLayout());
        setSize(window_width, window_height);
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

                            //Check if image is loaded into application properly
                            //else remove object from list and throw new IOException
                            if (image.getOriginalImage() == null) {
                                images.remove(image);
                                current_image_index = images.size() - 1;
                                throw new IOException();
                            }
                            setDisplayImage(image.getOriginalImage());
                        } catch ( IOException err ){
                            showDisplayWindow("There was a problem reading your image. Please" +
                                    " try again or use a different image.");
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
                        try (FileWriter fw = new FileWriter(file_chooser.getSelectedFile())) {
                            ImageIO.write(this.getDisplayedImage(), "jpg", (file_chooser.getSelectedFile()));
                        } catch (IOException err) {
                            showDisplayWindow("There was a problem saving the image");
                        } catch (NullPointerException err) {
                            showDisplayWindow("There is no image in the display");
                        } catch (IllegalArgumentException err) {
                            showDisplayWindow("There was a problem saving the image");
                        }
                    }
                }
        );

        // Add quit button to file menu
        JMenuItem file_menu_quit = new JMenuItem("Quit");
        file_menu.add(file_menu_quit);
        file_menu_quit.addActionListener(
                e -> System.exit(0)
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
        JMenuItem process_menu_threshold = new JMenuItem("Binary Image");
        JMenuItem process_menu_boxBlur = new JMenuItem("Box Blur");
        JMenuItem process_menu_gaussianBlur = new JMenuItem("Gaussian Blur");
        JMenuItem process_menu_prewitt = new JMenuItem("Prewitt Edge Detection");
        JMenuItem process_menu_sobelEdge = new JMenuItem("Sobel Edge Detection");
        JMenuItem process_menu_blurredSobel = new JMenuItem("Sobel Edge Detection (Pre-blurred)");
        JMenuItem process_menu_inverted_sobel = new JMenuItem("Sobel Edge Detection (Post-Inverted)");
        JMenuItem process_menu_pixelate = new JMenuItem("Pixelate");
        JMenuItem process_menu_sharpen = new JMenuItem("Sharpen");

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
        process_menu.add(process_menu_prewitt);
        process_menu.add(process_menu_sobelEdge);
        process_menu.add(process_menu_blurredSobel);
        process_menu.add(process_menu_inverted_sobel);
        process_menu.add(process_menu_pixelate);
        process_menu.add(process_menu_sharpen);

        //Add ActionListeners to each JMenuItem
        process_menu_original.addActionListener (
                e -> {
                    if (images.size() > 0) {
                        setDisplayImage(images.get(current_image_index).getOriginalImage());
                    } else {
                        showDisplayWindow("There is no image loaded.");
                    }

                }
        );

        process_menu_greyscale.addActionListener(
                e -> {
                    if (images.size() > 0) {
                        setDisplayImage(images.get(current_image_index).applyGreyscaleFilter());
                    } else {
                        showDisplayWindow("There is no image loaded.");
                    }
                }
        );

        process_menu_negative.addActionListener(
                e -> {
                    if (images.size() > 0) {
                        setDisplayImage(images.get(current_image_index).applyNegativeFilter());
                    } else {
                        showDisplayWindow("There is no image loaded.");
                    }

                }
        );

        process_menu_sepia.addActionListener(
                e -> {
                    if (images.size() > 0) {
                        setDisplayImage(images.get(current_image_index).applySepiaFilter());
                    } else {
                        showDisplayWindow("There is no image loaded.");
                    }
                }
        );

        process_menu_cartoon.addActionListener(
                e -> {
                    if (images.size() > 0) {
                        setDisplayImage(images.get(current_image_index).applyCartoonFilter());
                    } else {
                        showDisplayWindow("There is no image loaded.");
                    }
                }
        );

        process_menu_false.addActionListener(
                e -> {
                    if (images.size() > 0) {
                        setDisplayImage(images.get(current_image_index).applyContrastEnhancement());
                    } else {
                        showDisplayWindow("There is no image loaded.");
                    }
                }
        );

        process_menu_threshold.addActionListener(
                e -> {
                    if (images.size() > 0) {
                        setDisplayImage(images.get(current_image_index).thresholdImage());
                    } else {
                        showDisplayWindow("There is no image loaded.");
                    }
                }
        );

        process_menu_boxBlur.addActionListener(
                e -> {
                    if (images.size() > 0) {
                        setDisplayImage(images.get(current_image_index).applyBoxBlur());
                    } else {
                        showDisplayWindow("There is no image loaded.");
                    }
                }
        );

        process_menu_gaussianBlur.addActionListener(
                e -> {
                    if (images.size() > 0) {
                        setDisplayImage(images.get(current_image_index).applyGaussianBlur());
                    } else {
                        showDisplayWindow("There is no image loaded.");
                    }
                }
        );

        process_menu_prewitt.addActionListener(
                e -> {
                    if (images.size() > 0) {
                        setDisplayImage(images.get(current_image_index).applyPrewittOperator());
                    } else {
                        showDisplayWindow("There is no image loaded.");
                    }
                }
        );

        process_menu_sobelEdge.addActionListener(
                e -> {
                    if (images.size() > 0) {
                        setDisplayImage(images.get(current_image_index).applySobelOperator());
                    } else {
                        showDisplayWindow("There is no image loaded.");
                    }
                }
        );

        process_menu_blurredSobel.addActionListener(
                e -> {
                    if (images.size() > 0) {
                        setDisplayImage(images.get(current_image_index).applyPreblurredSobelOperator());
                    } else {
                        showDisplayWindow("There is no image loaded.");
                    }
                }
        );

        process_menu_inverted_sobel.addActionListener(
                e -> {
                    if (images.size() > 0) {
                        setDisplayImage(images.get(current_image_index).applyInvertedSobel());
                    } else {
                        showDisplayWindow("There is no image loaded.");
                    }
                }
        );

        process_menu_pixelate.addActionListener(
                e -> {
                    if (images.size() > 0) {
                        setDisplayImage(images.get(current_image_index).pixelate());
                    } else {
                        showDisplayWindow("There is no image loaded.");
                    }
                }
        );

        process_menu_sharpen.addActionListener(
                e -> {
                    if (images.size() > 0) {
                        setDisplayImage(images.get(current_image_index).sharpen());
                    } else {
                        showDisplayWindow("There is no image loaded.");
                    }
                }
        );
    }

    /**
     * Displays a window with the specified message
     * @param message message to display
     */
    private void showDisplayWindow(String message){
        JOptionPane.showMessageDialog(this, message);
    }

    /** Sets an image into the current display
     * @param image image to be displayed
     */
    private void setDisplayImage(BufferedImage image){
        if (image.getWidth() > window_width || image.getHeight() > window_height) {
            image = scaleImage(image, (int) (0.9 * window_width) , (int) (0.9 * window_height));
        }
        current_displayed_image = image;
        this.image_label.setIcon(new ImageIcon(image));
    }

    /**
     *  Retrieves the image currently being displayed
     * @return current displayed image
     */
    private BufferedImage getDisplayedImage(){
        if (current_displayed_image == null) {
            throw new NullPointerException();
        } else {
            return current_displayed_image;
        }
    }


    /**
     * Scales down an image
     * @param original_image image to be scaled
     * @param new_image_width width of the new image
     * @param new_image_height height of the new image
     * @return scaled image
     */
    public BufferedImage scaleImage(BufferedImage original_image, int new_image_width, int new_image_height){
        //Get original image dimensions as a double for division
        double original_image_width = original_image.getWidth();
        double original_image_height = original_image.getHeight();

        if (new_image_width > original_image_width || new_image_height > original_image_height) {
            //This should not happen but just in case
            throw new IllegalArgumentException("New image width or height greater than the original");
        }
        //Calculate the scale down factor
        double scale_factor_x = new_image_width/original_image_width;
        double scale_factor_y = new_image_height/original_image_height;

        BufferedImage transformed_image = new BufferedImage(new_image_width, new_image_height, original_image.getType());
        AffineTransform at = new AffineTransform();
        at.scale(scale_factor_x, scale_factor_y);

        try{
            AffineTransformOp scaleOp = new AffineTransformOp(at, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
            transformed_image = scaleOp.filter(original_image, transformed_image);
        } catch ( Exception e) {
            //This should not happen but just in case
            JOptionPane.showMessageDialog(this, "There was a problem displaying the image correctly:");
        }


        return transformed_image;
    }
}
