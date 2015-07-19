package university;

import java.util.Set;
import java.util.HashSet;


/**
 * Represents a university lecturer.
 * 
 * @author Mavrov
 */
public class Lecturer {
    
    public Lecturer(LecturerData lecturerData) {
        data = lecturerData;
        schedule = new Schedule();
        classes = new HashSet<>();
    }
    
    public int getDepartmentID() {
        return data.getDepartmentID();
    }
    
    public boolean assignClass(UniversityClass universityClass, AssignPolicy policy) {
        if (universityClass == null) {
            return false;
        }
        
        // TODO: Place in schedule
        boolean isPlacedInSchedule = true;
        
        boolean isLecturerAssigned = true;
        if (policy == AssignPolicy.BOTH_WAYS) {
            isLecturerAssigned = universityClass.assignLecturer(this, AssignPolicy.ONE_WAY);
        }
        
        boolean isClassAdded = classes.add(universityClass);
        
        return isPlacedInSchedule && isLecturerAssigned && isClassAdded;
    }
    
    public boolean unassignClass(UniversityClass universityClass, AssignPolicy policy) {
        if (universityClass == null) {
            return false;
        }
        
        // TODO: Remove from schedule
        boolean isRemovedFromSchedule = true;
        
        boolean isLecturerUnassigned = true;
        if (policy == AssignPolicy.BOTH_WAYS) {
            isLecturerUnassigned = universityClass.unassignLecturer(this, AssignPolicy.ONE_WAY);
        }
        
        boolean isClassRemoved = classes.remove(universityClass);
        
        return isRemovedFromSchedule && isLecturerUnassigned && isClassRemoved;
    }
    
    public boolean unassignAllClasses() {
        boolean areClassesUnassigned = true;
        
        for (UniversityClass universityClass : classes) {
            boolean isClassUnassigned = unassignClass(universityClass, AssignPolicy.BOTH_WAYS);
            areClassesUnassigned = areClassesUnassigned && isClassUnassigned;
        }
        
        return areClassesUnassigned;
    }
    
    private LecturerData data;
    
    private Schedule schedule;
    private Set<UniversityClass> classes;
}
