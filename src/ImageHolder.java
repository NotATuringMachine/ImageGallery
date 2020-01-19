import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.nio.Buffer;

/**
 * Class to hold the original image loaded in by the user and apply filters to the image
 */
public class ImageHolder {
    private ImageProcessor image_processor;

    //Store processed images to avoid having to recalculate every time
    private BufferedImage original_image;
    private BufferedImage greyscale_image;
    private BufferedImage negative_image;
    private BufferedImage sepia_image;
    private BufferedImage cartoon_image;
    private BufferedImage contrast_enhanced_image;
    private BufferedImage threshold_image;
    private BufferedImage box_blur_image;
    private BufferedImage gaussian_blur_image;
    private BufferedImage prewitt_image;
    private BufferedImage sobel_image;
    private BufferedImage preblurred_sobel_image;
    private BufferedImage inverted_sobel_image;
    private BufferedImage pixelated_image;
    private BufferedImage sharpened_image;

    public ImageHolder(BufferedImage original_image){
        this.original_image = original_image;
        image_processor = new ImageProcessor();
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
        if (greyscale_image != null)
            return greyscale_image;

        greyscale_image = image_processor.applyGreyscaleFilter(original_image);
        return greyscale_image;
    }

    /**
     * Applies a Negative filter to the original image i.e inverts
     * the RGB values for each pixel
     * @return negative
     */
    public BufferedImage applyNegativeFilter(){
        if (negative_image != null)
            return negative_image;

        negative_image = image_processor.applyNegativeFilter(original_image);
        return negative_image;
    }

    /**
     * Applies a sepia filter to the original image
     * @return sepia image
     */
    public BufferedImage applySepiaFilter() {
        if (sepia_image != null)
            return sepia_image;

        sepia_image = image_processor.applySepiaFilter(original_image);
        return sepia_image;
    }

    /**
     * Applies a cartoon-esque filter to the image by quantizing the RGB values of each pixel
     * @return cartoon-esque image
     */
    public BufferedImage applyCartoonFilter() {
        if (cartoon_image != null)
            return cartoon_image;

        cartoon_image = image_processor.applyCartoonFilter(original_image);
        return cartoon_image;
    }

    /**
     * Increase the contrast of the image
     * Contrast is increased by calculating the average value of each pixel and increasing its brightness if
     * above a certain threshold (127) and decreasing it if it is below
     * @return contrast enhanced Image
     */
    public BufferedImage applyContrastEnhancement(){
        if (contrast_enhanced_image != null)
            return contrast_enhanced_image;

        contrast_enhanced_image = image_processor.applyContrastEnhancement(original_image);;
        return contrast_enhanced_image;
    }

    /**
     * Converts the image into a black and white image
     * @return black and white image
     */
    public BufferedImage thresholdImage() {
        if (threshold_image != null)
            return threshold_image;

        threshold_image = image_processor.thresholdImage(original_image);
        return threshold_image;
    }

    /**
     * Applies a box blur to the original image. Uses a kernel size of 5
     * @return box blurred image
     */
    public BufferedImage applyBoxBlur() {
        if (box_blur_image != null)
            return box_blur_image;

        box_blur_image = image_processor.applyBoxBlur(original_image);;
        return box_blur_image;
    }

    /**
     * Applies a gaussian blur to the original image.
     * @return gaussian blurred image
     */
    public BufferedImage applyGaussianBlur() {
        if (gaussian_blur_image != null)
            return gaussian_blur_image;

        gaussian_blur_image = image_processor.applyGaussianBlur(original_image);
        return gaussian_blur_image;
    }

    public BufferedImage applyPrewittOperator() {
        if (prewitt_image != null)
            return prewitt_image;

        prewitt_image = image_processor.applyPrewittOperator(original_image);
        return prewitt_image;
    }

    /**
     * Applies the Sobel operator to the original image for edge detection
     * @return edge-detected image
     */
    public BufferedImage applySobelOperator() {
        if (sobel_image != null)
            return sobel_image;

        sobel_image = image_processor.applySobelOperator(original_image);
        return sobel_image;
    }

    /**
     * Applies a gaussian blur filter to the original image before applying sobel to
     * reduce noise in the image
     * @return blurred-edge-detected image
     */
    public BufferedImage applyPreblurredSobelOperator() {
        if (preblurred_sobel_image != null)
            return preblurred_sobel_image;

        if (gaussian_blur_image == null)
            gaussian_blur_image = image_processor.applyGaussianBlur(original_image);

        preblurred_sobel_image = image_processor.applySobelOperator(gaussian_blur_image);

        return preblurred_sobel_image;
    }

    /**
     * Inverts the RGB values of an image after applying a sobel operator
     * @return inverted-sobel-image
     */
    public BufferedImage applyInvertedSobel(){
        if (inverted_sobel_image != null)
            return inverted_sobel_image;

        if (sobel_image == null)
            sobel_image = image_processor.applySobelOperator(original_image);

        inverted_sobel_image = image_processor.applyNegativeFilter(sobel_image);

        return inverted_sobel_image;
    }

    /**
     * Pixelates an image
     * @return pixelated image
     */
    public BufferedImage pixelate() {
        if (pixelated_image != null)
                return pixelated_image;

        pixelated_image = image_processor.pixelate(original_image);
        return pixelated_image;
    }

    /**
     * Sharpens the original image
     * @return sharpened image
     */
    public BufferedImage sharpen() {
        if (sharpened_image != null)
            return sharpened_image;

        sharpened_image = image_processor.sharpen(original_image);
        return sharpened_image;
    }

}
