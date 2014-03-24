package timetable;

import java.util.List;
import java.util.ArrayList;
import university.University;

/**
 *
 * @author Mavrov
 */
public class Timetable extends Speciment {
    
    public Timetable() {
        super();
        
        subjectCount = University.getInstance().getSubjectsCount();
        
        placement = new ArrayList<>(subjectCount);
        for (int i = 0; i < subjectCount; ++i) {
            placement.add(new ClassPlacement());
        }
    }
    
    private static int subjectCount;
    
    private List<ClassPlacement> placement;
}
