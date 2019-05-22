package imageGallery;

import java.awt.image.BufferedImage;
import java.awt.Image;
import java.awt.Color;

/**
 * Represents an Image in the gallery.
 */
public class GalleryImage {

	private String imageName;
	private BufferedImage originalImage;
	private BufferedImage transformedImage;

	public GalleryImage(){

	}

	public GalleryImage(BufferedImage image){
		this.originalImage = image;
		this.transformedImage = new BufferedImage(image.getWidth(), image.getHeight(), image.getType());
		applyRGBtoGreyscale();
	}

	/**
	 * Returns the original image
	 */
	public BufferedImage getOriginalImage(){
		return this.originalImage;
	}

	/**
	 * Returns the transformed image
	 */
	public BufferedImage getTransformedImage(){
		return this.transformedImage;
	}

	/**
	 * Applies the greyscale filter to the originalImage and stores it in transformedImage
	 */
	public void applyRGBtoGreyscale(){
		int red_val;
		int blue_val;
		int green_val;
		int greyscale_val;
		Color c;
		for (int x = 0; x < transformedImage.getWidth(); x++){
			for (int y = 0; y < transformedImage.getHeight(); y++){
				c = new Color(originalImage.getRGB(x, y));
				red_val = c.getRed();
				blue_val = c.getGreen();
				green_val = c.getBlue();
				greyscale_val = (int) (0.3 * red_val + 0.6 * green_val + 0.1 * blue_val);
				c = new Color(greyscale_val, greyscale_val, greyscale_val);
        		transformedImage.setRGB(x, y, c.getRGB());
			}
		}
	}

	/**
	 * Applies the Speia filter to the originalImage and stores it in transformedImage
	 */
	public void applyRGBtoSepia(){
		int red_val;
		int blue_val;
		int green_val;
		int new_red_val;
		int new_blue_val;
		int new_green_val;
		Color c;
		for (int x = 0; x < transformedImage.getWidth(); x++){
			for (int y = 0; y < transformedImage.getHeight(); y++){
				c = new Color(originalImage.getRGB(x, y));
				red_val = c.getRed();
				blue_val = c.getGreen();
				green_val = c.getBlue();

				//Calculate new RGB values and check bounds
				new_red_val = (int)(0.393*red_val + 0.769*green_val + 0.189*blue_val);
				if (new_red_val > 255){
					new_red_val = 255;
				}
				new_green_val = (int)(0.349*red_val + 0.686*green_val + 0.168*blue_val);
				if (new_green_val > 255){
					new_green_val = 255;
				}
				new_blue_val = (int)(0.272*red_val + 0.534*green_val + 0.131*blue_val);
				if (new_blue_val > 255){
					new_blue_val = 255;
				}
				c = new Color(new_red_val, new_green_val, new_blue_val);
        		transformedImage.setRGB(x, y, c.getRGB());
			}
		}
	}

}