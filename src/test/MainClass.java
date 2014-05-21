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
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                //CourseStructureFormFrame.getInstance().setVisible(true);
                MainFrame.getInstance().setVisible(true);
            }
        });
        
        /*
        AccessTxtParser parser = new AccessTxtParser();
        parser.parseAccessTxtFile("C:\\Users\\Mavrov\\Desktop\\Master Project\\FMI sources\\txt exports\\Chetenia.txt");
       
        while (parser.hasNextToken()) {
            System.out.println(parser.getNextToken());
        }
        */
    }
}
