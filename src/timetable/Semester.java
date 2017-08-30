package timetable;

import java.util.List;
import university.UniversityClass;

/**
 *
 * @author Mavrov
 * 
 * Stub for semester data
 */
public abstract class Semester {
    
    public static Semester getInstance() {
        return instance;
    }
    
    public abstract int getClassesCount();
    
    public abstract List<UniversityClass> getClasses();
    
    private static Semester instance = null;
}
