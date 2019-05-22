package imageGallery;

import javax.swing.SwingUtilities;

public class RunApplication implements Runnable{
	public void run() {
		ImageGalleryApp app = new ImageGalleryApp();
	}

	public static void main (String[] args){
		SwingUtilities.invokeLater(new RunApplication());
	}
}