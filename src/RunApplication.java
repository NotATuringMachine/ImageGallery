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
        SwingUtilities.invokeLater( new RunApplication());
    }
}
