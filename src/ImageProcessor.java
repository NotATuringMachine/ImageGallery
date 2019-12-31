/**
 * @author Nathan McCulloch
 */

import java.awt.*;
import java.awt.image.BufferedImage;
import java.nio.Buffer;

/** Class to apply image filters to an image*/
public class ImageProcessor {

    public ImageProcessor() {}


    /**
     * Applies a greyscale filter to the image
     * @return greyscale image
     */
    public BufferedImage applyGreyscaleFilter(BufferedImage original_image){
        BufferedImage transformed_image = new BufferedImage(
                original_image.getWidth(),
                original_image.getHeight(),
                original_image.getType());
        // RGB values of the current pixel
        int red_val;
        int green_val;
        int blue_val;
        int greyscale_val;
        Color c;
        for (int x = 0; x < transformed_image.getWidth(); x++){
            for (int y = 0; y < transformed_image.getHeight(); y++){
                // Fetch current pixel's RGB values and calculate greyscale value
                c = new Color(original_image.getRGB(x, y));
                red_val = c.getRed();
                blue_val = c.getBlue();
                green_val = c.getGreen();
                greyscale_val = (int) (0.3 * red_val + 0.6 * green_val + 0.1 * blue_val);
                c = new Color(greyscale_val, greyscale_val, greyscale_val);
                transformed_image.setRGB(x, y, c.getRGB());
            }
        }
        return transformed_image;
    }

    /**
     * Applies a Negative filter to the original image i.e inverts
     * the RGB values for each pixel
     * @return negative
     */
    public BufferedImage applyNegativeFilter(BufferedImage original_image){
        BufferedImage transformed_image = new BufferedImage(
                original_image.getWidth(),
                original_image.getHeight(),
                original_image.getType());
        int red_val;
        int blue_val;
        int green_val;
        Color c;
        for (int x = 0; x < original_image.getWidth(); x++){
            for (int y = 0; y < original_image.getHeight(); y++){
                c = new Color(original_image.getRGB(x, y));
                red_val = 255 - c.getRed();
                green_val = 255 - c.getGreen();
                blue_val = 255 - c.getBlue();
                c = new Color(red_val, green_val, blue_val);
                transformed_image.setRGB(x, y, c.getRGB());
            }
        }
        return transformed_image;
    }

    /**
     * Applies a sepia filter to the original image
     * @return sepia image
     */
    public BufferedImage applySepiaFilter(BufferedImage original_image){
        BufferedImage transformed_image = new BufferedImage(
                original_image.getWidth(),
                original_image.getHeight(),
                original_image.getType());

        int red_val;
        int green_val;
        int blue_val;
        int new_red_val;
        int new_blue_val;
        int new_green_val;
        Color c;
        for (int x = 0; x < original_image.getWidth(); x++){
            for (int y = 0; y < original_image.getHeight(); y++){
                c = new Color(original_image.getRGB(x, y));
                red_val = c.getRed();
                green_val = c.getGreen();
                blue_val = c.getBlue();

                //Calculate new RGB values and check bounds
                new_red_val = (int)(0.393 * red_val + 0.769 * green_val + 0.189 * blue_val);
                if (new_red_val > 255)
                    new_red_val = 255;
                new_green_val = (int)(0.349 * red_val + 0.686 * green_val + 0.168 * blue_val);
                if (new_green_val > 255)
                    new_green_val = 255;
                new_blue_val = (int)(0.272 * red_val + 0.534 * green_val + 0.131 * blue_val);
                if (new_blue_val > 255)
                    new_blue_val = 255;

                c = new Color(new_red_val, new_green_val, new_blue_val);
                transformed_image.setRGB(x, y, c.getRGB());
            }
        }
        return transformed_image;
    }

    /**
     * Applies a cartoon-esque filter to the image by quantizing the RGB values of each pixel
     * @return cartoon-esque image
     */
    public BufferedImage applyCartoonFilter(BufferedImage original_image){
        BufferedImage transformed_image = new BufferedImage(
                original_image.getWidth(),
                original_image.getHeight(),
                original_image.getType());
        int red_val;
        int green_val;
        int blue_val;
        Color c;
        for (int x = 0; x < original_image.getWidth(); x++){
            for (int y = 0; y < original_image.getHeight(); y++){
                c = new Color(original_image.getRGB(x, y));
                red_val = quantizeRGBValue(c.getRed());
                green_val = quantizeRGBValue(c.getGreen());
                blue_val = quantizeRGBValue(c.getBlue());
                c = new Color(red_val, green_val, blue_val);
                transformed_image.setRGB(x, y, c.getRGB());
            }
        }
        return transformed_image;
    }

    /**
     * Quantizes a RGB value as an integer into discrete values
     * @param n RGB value to be quantized
     * @return quantized RGB value
     */
    private int quantizeRGBValue (int n) {
        if (n < 85) {
            n = 0;
        } else if (n < 170) {
            n = 85;
        } else if (n < 255) {
            n = 170;
        } else {
            n = 255;
        }
        return n;
    }


    /**
     * Increase the contrast of the image
     * Contrast is increased by calculating the average value of each pixel and increasing its brightness if
     * above a certain threshold (127) and decreasing it if it is below
     * @return contrast enhanced Image
     */
    public BufferedImage applyContrastEnhancement(BufferedImage original_image){
        BufferedImage transformed_image = new BufferedImage(
                original_image.getWidth(),
                original_image.getHeight(),
                original_image.getType());
        int red_val;
        int green_val;
        int blue_val;
        int average_val;
        Color c;
        for (int x = 0; x < original_image.getWidth(); x++){
            for (int y = 0; y < original_image.getHeight(); y++){
                c = new Color(original_image.getRGB(x, y));
                red_val = c.getRed();
                green_val = c.getGreen();
                blue_val = c.getBlue();
                average_val = (red_val + green_val + blue_val)/3;

                //Check threshold value and increase/decrease pixel brightness accordingly
                if (average_val > 127){
                    red_val = red_val + 50;
                    if (red_val > 255)
                        red_val = 255;
                    green_val = green_val + 50;
                    if (green_val > 255)
                        green_val = 255;
                    blue_val = blue_val  + 50;
                    if (blue_val > 255)
                        blue_val = 255;
                } else {
                    red_val = red_val - 50;
                    if (red_val < 0)
                        red_val = 0;
                    green_val = green_val - 50;
                    if (green_val < 0)
                        green_val = 0;
                    blue_val = blue_val  - 50;
                    if (blue_val < 0)
                        blue_val = 0;
                }
                c = new Color(red_val, green_val, blue_val);
                transformed_image.setRGB(x, y, c.getRGB());
            }
        }
        return transformed_image;
    }


    /**
     * Converts the image into a black and white image
     * @return black and white image
     */
    public BufferedImage thresholdImage(BufferedImage original_image) {
        BufferedImage transformed_image = new BufferedImage(
                original_image.getWidth(),
                original_image.getHeight(),
                original_image.getType());
        int red_val;
        int green_val;
        int blue_val;
        int greyscale_val;
        Color c;
        for (int x = 0; x < original_image.getWidth(); x++) {
            for (int y = 0; y < original_image.getHeight(); y++) {
                c = new Color(original_image.getRGB(x, y));
                red_val = c.getRed();
                green_val = c.getGreen();
                blue_val = c.getBlue();
                greyscale_val = (int)(red_val * 0.3 + green_val * 0.6 + blue_val * 0.1);
                if (greyscale_val >= 127) {
                    greyscale_val = 255;
                } else {
                    greyscale_val = 0;
                }
                c = new Color(greyscale_val, greyscale_val, greyscale_val);
                transformed_image.setRGB(x, y, c.getRGB());
            }
        }
        return transformed_image;
    }


    /**
     * Applies a box blur to the original image. Uses a kernel size of 5
     * @return box blurred image
     */
    public BufferedImage applyBoxBlur(BufferedImage original_image) {
        BufferedImage transformed_image = new BufferedImage(
                original_image.getWidth(),
                original_image.getHeight(),
                original_image.getType());
        Color c;
        int kernel_size = 5;
        //Kernel rgb sums used to calculate average value of the kernel
        int kernel_sum_red;
        int kernel_sum_green;
        int kernel_sum_blue;
        for ( int x = 0; x < original_image.getWidth(); x++ ) {
            for ( int y = 0; y < original_image.getHeight(); y++ ) {
                kernel_sum_red = 0;
                kernel_sum_green = 0;
                kernel_sum_blue = 0;
                for (int kernel_x = x - (kernel_size - 1)/2; kernel_x <= x + (kernel_size - 1)/2; kernel_x++ ) {
                    for (int kernel_y = y - (kernel_size - 1)/2; kernel_y <= y + (kernel_size - 1)/2; kernel_y++ ) {

                        //Check kernel coordinate bounds
                        if (kernel_x >= 0 && kernel_x < original_image.getWidth() && kernel_y >= 0 && kernel_y < original_image.getHeight()) {
                            c = new Color ( original_image.getRGB(kernel_x, kernel_y) );
                            kernel_sum_red += c.getRed();
                            kernel_sum_green += c.getGreen();
                            kernel_sum_blue += c.getBlue();
                        }
                    }
                }

                c = new Color(kernel_sum_red/(kernel_size*kernel_size),
                        kernel_sum_green/(kernel_size*kernel_size),
                        kernel_sum_blue/(kernel_size*kernel_size));
                transformed_image.setRGB(x, y, c.getRGB());
            }
        }
        return transformed_image;
    }


    /**
     * Applies a gaussian blur to the original image.
     * @return gaussian blurred image
     */
    public BufferedImage applyGaussianBlur(BufferedImage original_image) {
        BufferedImage transformed_image = new BufferedImage(
                original_image.getWidth(),
                original_image.getHeight(),
                original_image.getType());
        Color c;
        int kernel_size = 5; //Kernel size must be odd
        int gaussian_factor = 256; // value to divide gaussian kernel by
        int[][] gaussian_kernel = { {1, 4, 6, 4, 1}, {4, 16, 24, 16, 4}, {6, 24, 36, 24, 6}, {4, 16, 24, 16, 4}, {1, 4, 6, 4, 1} };
        //Kernel rgb sums used to calculate new pixel values
        int kernel_sum_red;
        int kernel_sum_green;
        int kernel_sum_blue;

        //Kernel position on the image
        int kernel_x;
        int kernel_y;
        for (int x = 0; x < original_image.getWidth(); x++ ) {
            for (int y = 0; y < original_image.getHeight(); y++) {
                //Initialize sums
                kernel_sum_red = 0;
                kernel_sum_green = 0;
                kernel_sum_blue = 0;
                for (int i = 0; i < kernel_size; i++) {
                    for (int j = 0; j < kernel_size; j++) {
                        kernel_x = x - (kernel_size - 1)/2 + i;
                        kernel_y = y - (kernel_size - 1)/2 + j;
                        if (kernel_x >= 0 && kernel_x < original_image.getWidth() && kernel_y >= 0 && kernel_y < original_image.getHeight()) {
                            c = new Color ( original_image.getRGB(kernel_x, kernel_y) );
                            //Add weighted pixel values to running kernel sums
                            kernel_sum_red += gaussian_kernel[i][j] * c.getRed();
                            kernel_sum_green += gaussian_kernel[i][j] * c.getGreen();
                            kernel_sum_blue += gaussian_kernel[i][j] * c.getBlue();
                        }
                    }
                }
                c = new Color(kernel_sum_red/(gaussian_factor),
                        kernel_sum_green/(gaussian_factor),
                        kernel_sum_blue/(gaussian_factor));
                transformed_image.setRGB(x, y, c.getRGB());
            }
        }
        return transformed_image;
    }


    /**
     * Applies the Sobel operator to the original image for edge detection
     * @return edge-detected image
     */
    public BufferedImage applySobelOperator(BufferedImage original_image) {
        BufferedImage greyscale_image = this.applyGreyscaleFilter(original_image); // Convert image to greyscale
        BufferedImage transformed_image = new BufferedImage(
                original_image.getWidth(),
                original_image.getHeight(),
                original_image.getType());
        Color c;

        int[][] sobel_kernel_x = {{-1, 0, 1}, {-2, 0, 2}, {-1, 0, 1}};
        int[][] sobel_kernel_y = {{-1, -2, -1}, {0, 0, 0}, {1, 2, 1}};
        int kernel_size = 3;
        //Kernel sums
        int kernel_sum_x = 0;
        int kernel_sum_y = 0;
        //Current pixel in the kernel
        int current_pixel_in_kernel_x;
        int current_pixel_in_kernel_y;
        int final_pixel_value;

        for (int x = 0; x < greyscale_image.getWidth(); x++) {
            for (int y = 0; y < greyscale_image.getHeight(); y++) {
                kernel_sum_x = 0;
                kernel_sum_y = 0;

                for (int i = 0; i < kernel_size; i++) {
                    for (int j = 0; j < kernel_size; j++) {
                        //Change current pixel's coordinates in the kernel to image coordinates
                        current_pixel_in_kernel_x = x - (kernel_size - 1)/2 + i;
                        current_pixel_in_kernel_y = y - (kernel_size - 1)/2 + j;

                        //Check pixel coordinate is in image bounds
                        if (current_pixel_in_kernel_x >= 0 && current_pixel_in_kernel_x < greyscale_image.getWidth() &&
                                current_pixel_in_kernel_y >= 0 && current_pixel_in_kernel_y < greyscale_image.getHeight()) {

                            //Get pixel's greyscale value
                            c = new Color ( greyscale_image.getRGB(current_pixel_in_kernel_x, current_pixel_in_kernel_y) );
                            //Add weighted pixel values to running kernel sums
                            kernel_sum_x += sobel_kernel_x[i][j] * c.getRed();
                            kernel_sum_y += sobel_kernel_y[i][j] * c.getRed();
                        }
                    }
                }
                final_pixel_value = (int) (Math.sqrt(kernel_sum_x * kernel_sum_x + kernel_sum_y * kernel_sum_y));
                final_pixel_value = map(final_pixel_value); // Ensure the pixel value is in the range [0 -255]

                c = new Color(final_pixel_value, final_pixel_value, final_pixel_value);
                transformed_image.setRGB(x, y, c.getRGB());
            }
        }
        return transformed_image;
    }

    /**
     * Ensures an integer is in the range 0 - 255
     * @param n number to be mapped
     * @return number in [0, 255]
     */
    private int map(int n){
        if (n > 255) {
            n = 255;
        }else if (n < 0) {
            n = 0;
        }
        return n;
    }

    /**
     * Pixelates an image by setting all the pixels in the kernel to the same RGB value
     * @param original_image
     * @return
     */
    public BufferedImage pixelate(BufferedImage original_image) {
        BufferedImage transformed_image = new BufferedImage(
                original_image.getWidth(),
                original_image.getHeight(),
                original_image.getType());
        Color c;
        int kernel_size = 5; //Kernel size should always be an odd number
        int current_pixel_in_kernel_x;
        int current_pixel_in_kernel_y;

        for ( int x = (kernel_size - 1)/2 ; x < original_image.getWidth() - (kernel_size - 1)/2; x += kernel_size) {
            for ( int y = (kernel_size - 1)/2; y < original_image.getHeight() - (kernel_size - 1)/2; y += kernel_size) {

                c = new Color( original_image.getRGB(x, y));

                for (int i = 0; i < kernel_size; i++) {
                    for (int j = 0; j < kernel_size; j++) {
                        //Change current pixel's coordinates in the kernel to image coordinates
                        current_pixel_in_kernel_x = x - (kernel_size - 1) / 2 + i;
                        current_pixel_in_kernel_y = y - (kernel_size - 1) / 2 + j;

                        //Check pixel coordinate is in image bounds
                        if (current_pixel_in_kernel_x >= 0 && current_pixel_in_kernel_x < original_image.getWidth() &&
                                current_pixel_in_kernel_y >= 0 && current_pixel_in_kernel_y < original_image.getHeight()) {

                            transformed_image.setRGB(current_pixel_in_kernel_x, current_pixel_in_kernel_y, c.getRGB());
                        }
                    }
                }
            }
        }

        return transformed_image;
    }
}
