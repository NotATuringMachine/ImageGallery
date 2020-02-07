/**
 * @author Nathan McCulloch
 *
 */

import javax.swing.*;

/* Entry point into application*/
public class RunApplication implements Runnable{

    public void run(){
        ImageProcessorApp app = new ImageProcessorApp();
    }

    public static void main(String args[]){
        // Start swing in different thread to the main thread
        SwingUtilities.invokeLater( new RunApplication());
    }
}
