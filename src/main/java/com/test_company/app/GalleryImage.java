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
	 * Applies a Negative filter to the original image
	 */
	public void applyRGBtoNegative(){
		int red_val;
		int blue_val;
		int green_val;
		Color c;
		for (int x = 0; x < originalImage.getWidth(); x++){
			for (int y = 0; y < originalImage.getHeight(); y++){
				c = new Color(originalImage.getRGB(x, y));
				red_val = 255 - c.getRed();
				blue_val = 255 - c.getBlue();
				green_val = 255 - c.getGreen();
				c = new Color(red_val, green_val, blue_val);
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

	/**
	 * Applies a blox blur to the original image and stores it in transformed image
	 */
	public void applyBoxBlur(){
		int kernel_size = 5;
		for (int x = 0; x < originalImage.getWidth(); x++){
			for (int y = 0; y < originalImage.getHeight(); y++){
					int average_red = 0;
					int average_green = 0;
					int average_blue = 0;
					Color c;

					for (int i = x - (kernel_size - 1)/2 ; i < x + (kernel_size - 1)/2; i++){
						for (int j = y - (kernel_size - 1)/2 ; j < y + (kernel_size - 1)/2; j++){
							if (i >= 0 && i < originalImage.getWidth() 
								&& j >= 0 && j < originalImage.getHeight()){

								c = new Color(originalImage.getRGB(i, j));
								average_red += c.getRed();
								average_blue += c.getBlue();
								average_green += c.getGreen();
							}
						}
					}
					average_red = average_red / (kernel_size * kernel_size);
					average_blue = average_blue / (kernel_size * kernel_size);
					average_green = average_green / (kernel_size * kernel_size); 
					c = new Color(average_red, average_green, average_blue);
					transformedImage.setRGB(x, y, c.getRGB());
			}
		}
	}

	/**
	 * Applies a Gaussian Blur to the original image and stores it in transformed image
	 */
	public void applyGaussianBlur(){
		int[][] gaussianKernel = {{1, 4, 6, 4, 1}, {4, 16, 24, 16, 4}, 
		{6, 24, 36, 24, 6}, {4, 16, 24, 16, 4}, {1, 4, 6, 4, 1}};
		int kernel_size = 5;
		int kernel_index_x = 0;
		int kernel_index_y = 0;
	
		for (int x = 0; x < originalImage.getWidth(); x++){
			for (int y = 0; y < originalImage.getHeight(); y++){
					int average_red = 0;
					int average_green = 0;
					int average_blue = 0;
					Color c;
					kernel_index_x = 0;
					for (int i = x - (kernel_size - 1)/2 ; i < x + (kernel_size - 1)/2; i++){
						kernel_index_y = 0;
						for (int j = y - (kernel_size - 1)/2 ; j < y + (kernel_size - 1)/2; j++){

							if (i >= 0 && i < originalImage.getWidth() 
								&& j >= 0 && j < originalImage.getHeight()){

								c = new Color(originalImage.getRGB(i, j));
								average_red += c.getRed() * gaussianKernel[kernel_index_x][kernel_index_y];
								average_blue += c.getBlue() * gaussianKernel[kernel_index_x][kernel_index_y];
								average_green += c.getGreen() * gaussianKernel[kernel_index_x][kernel_index_y];
							}
							kernel_index_y++;
						}
						kernel_index_x++;

					}
					average_red = average_red / (256);
					average_blue = average_blue / (256);
					average_green = average_green / (256); 
					c = new Color(average_red, average_green, average_blue);
					transformedImage.setRGB(x, y, c.getRGB());
			}
		}

	}

}