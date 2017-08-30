package test;

/*
import gui.CourseStructureFormFrame;
import javax.swing.SwingUtilities;
import gui.MainFrame;
*/

import java.util.ArrayList;
import java.util.List;
import timetable.*;
import university.Room;
import university.UniversityClass;

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
                //CourseStructureFormFrame.getInstance().setVisible(true);
                MainFrame.getInstance().setVisible(true);
            }
        });
        */
        
        /*
        AccessTxtParser parser = new AccessTxtParser();
        parser.parseAccessTxtFile("C:\\Users\\Mavrov\\Desktop\\Master Project\\FMI sources\\txt exports\\Chetenia.txt");
       
        while (parser.hasNextToken()) {
            System.out.println(parser.getNextToken());
        }
        */
        
        List<Integer> summands = new ArrayList<>();
        summands.add(2);
        summands.add(3);
        summands.add(5);
        
        List<int[]> partitions = Timetable.getPartitions(summands, Settings.LATEST_CLASS_END - Settings.EARLIEST_CLASS_START); 
        
        // Basic sanity check
        int providedTimeCapacity = 
            University.getInstance().getRoomCount() * 
            Settings.WORKHOURS_PER_DAY * Settings.WORKDAYS_PER_WEEK;
        
        int requiredTimeCapacity = 0;
        List<UniversityClass> classes = Semester.getInstance().getClasses();
        for (UniversityClass universityClass : classes) {
            requiredTimeCapacity += universityClass.getDuration();
        }
        
        int excessTimeCapacity = providedTimeCapacity - requiredTimeCapacity;
        System.out.println("Excess time budget: " + excessTimeCapacity);
        
        // Initialize once the random generator
        Generator.initialize();
        
        GenericFactory<Timetable> timetableFactory = new GenericFactory<>(Timetable.class);
        
        SpecimentSelector<Timetable> crossSelector = null;
        SpecimentSelector<Timetable> transferSelector = null;
        SpecimentSelector<Timetable> mutationSelector = null;
        CrossoverOperator<Timetable> crossOperator = null;
        MutationOperator<Timetable> mutationOperator = null;
        FitnessEvaluator<Timetable> fitnessEvaluator = null;
        Terminator terminator = null;
        
        GeneticAlgorithm<Timetable> a = new GeneticAlgorithm<>(
            timetableFactory, 
            crossSelector, transferSelector, mutationSelector,
            crossOperator, mutationOperator, fitnessEvaluator,
            terminator
        );
        
        a.runAlgorithm();
    }
}
