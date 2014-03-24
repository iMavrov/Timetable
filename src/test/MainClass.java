package test;

import gui.CourseStructureFormFrame;
import javax.swing.SwingUtilities;
import gui.MainFrame;

/**
 *
 * @author Mavrov
 */
public class MainClass {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        /*
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                MainFrame.getInstance().setVisible(true);
            }
        });
        */
        
        CourseStructureFormFrame.getInstance().setVisible(true);
    }
}
