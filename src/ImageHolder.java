import java.awt.*;
import java.awt.image.BufferedImage;

public class ImageHolder {
    private BufferedImage original_image; //store the original image

    public ImageHolder(){

    }

    public ImageHolder(BufferedImage original_image){
        this.original_image = original_image;
    }

    /** Returns the original image
     * @return original image
     */
    public BufferedImage getOriginalImage(){
        return original_image;
    }

    /**
     * Applies a greyscale filter to the image
     * @return greyscale image
     */
    public BufferedImage applyGreyscaleFilter(){
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
    public BufferedImage applyNegativeFilter(){
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
     *  Applies a sepia filter to the original image
     * @return sepia image
     */
    public BufferedImage applySepiaFilter(){
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
    public BufferedImage applyCartoonFilter(){
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
        if (n <= 127){
            n = 0;
        } else {
            n = 255;
        }
        /*if (n < 85) {
            n = 0;
        } else if (n < 170) {
            n = 85;
        } else if (n < 255) {
            n = 170;
        } else {
            n = 255;
        }*/
        return n;
    }

    /**
     *  Increase the contrast of the image
     * @return contrast enhanced Image
     */
    public BufferedImage applyContrastEnhancement(){
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
    public BufferedImage thresholdImage(){
        BufferedImage transformed_image = new BufferedImage(
                original_image.getWidth(),
                original_image.getHeight(),
                original_image.getType());
        int red_val;
        int green_val;
        int blue_val;
        int greyscale_val;
        Color c;
        for (int x = 0; x < original_image.getWidth(); x++){
            for (int y = 0; y < original_image.getHeight(); y++){
                c = new Color(original_image.getRGB(x, y));
                red_val = c.getRed();
                green_val = c.getGreen();
                blue_val = c.getBlue();
                greyscale_val = (int)(red_val * 0.3 + green_val * 0.6 + blue_val * 0.1);
                if (greyscale_val >= 127){
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
}
