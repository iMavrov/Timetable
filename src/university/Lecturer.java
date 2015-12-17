package university;

import utilities.AssignPolicy;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.Set;
import java.util.HashSet;
import java.util.Objects;
import utilities.IObservable;
import utilities.UpdateReason;

/**
 * Represents a university lecturer.
 * 
 * @author Mavrov
 */
public class Lecturer implements IPersistable, IKeyHolder, IAttributeHolder, IClassObserver, IObservable {
    
    public Lecturer() {
        facultyID = University.INVALID_ID;
        departmentID = University.INVALID_ID;
        name = "";
        attributes = new HashSet<>();
        schedule = new Schedule();
        classes = new HashSet<>();
    }
    
    public Lecturer(
            int lecturerFacultyID, 
            int lecturerDepartmentID,
            String lecturerName,
            Set<String> lecturerAttributes) {
        facultyID = lecturerFacultyID;
        departmentID = lecturerDepartmentID;
        name = lecturerName;
        attributes = lecturerAttributes;
        schedule = new Schedule();
        classes = new HashSet<>();
    }
    
    public int getFacultyID() {
        return facultyID;
    }

    public void setFaculty(int newLecturerFacultyID) {
        facultyID = newLecturerFacultyID;
    }

    public int getDepartmentID() {
        return departmentID;
    }

    public void setDepartmentID(int lecturerDepartmentID) {
        departmentID = lecturerDepartmentID;
    }

    public String getName() {
        return name;
    }

    public void setName(String lecturerName) {
        name = lecturerName;
    }
    
    @Override
    public boolean equals(Object o) {
        if (o == null) {
            return false;
        }
        
        if (getClass() != o.getClass()) {
            return false;
        }
        
        final Lecturer other = (Lecturer)o;
        return (departmentID == other.departmentID) && name.equalsIgnoreCase(other.name);
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 41 * hash + this.departmentID;
        hash = 41 * hash + Objects.hashCode(this.name);
        return hash;
    }
    
    @Override
    public String toString() {
        return name;
    }
    
    @Override
    public boolean save(BufferedWriter writer) throws IOException {
        writer.write(String.valueOf(facultyID));
        writer.newLine();
        writer.write(String.valueOf(departmentID));
        writer.newLine();
        writer.write(name);
        writer.newLine();
        
        writer.write(String.valueOf(attributes.size()));
        writer.newLine();
        for (String attribute: attributes) {
            writer.write(attribute);
            writer.newLine();
        }

        return true;
    }
    
    @Override
    public boolean load(BufferedReader reader) throws IOException {
        facultyID = Integer.valueOf(reader.readLine());
        departmentID = Integer.valueOf(reader.readLine());
        name = reader.readLine();

        int attributeCount = Integer.valueOf(reader.readLine());
        while (attributeCount > 0) {
            attributes.add(reader.readLine());
            --attributeCount;
        }
        
        return true;
    }
    
    @Override
    public boolean hasBadKey() {
        return (departmentID == University.INVALID_ID) || name.isEmpty();
    }
    
    @Override
    public boolean hasAttribute(String attribute) {
        return attributes.contains(attribute);
    }

    @Override
    public boolean addAttribute(String attribute) {
        return attributes.add(attribute);
    }

    @Override
    public boolean removeAttribute(String attribute) {
        return attributes.remove(attribute);
    }
    
    @Override
    public boolean assign(UniversityClass universityClass, AssignPolicy policy) {
        if (universityClass == null) {
            return false;
        }
        
        /*
        if (universityClass.hasBadKey()) {
            return false;
        }
        */
        
        if (classes.contains(universityClass)) {
            return false;
        }

        // We are ok with the class so far. See if it is ok with us.
        boolean isLecturerAssigned = true;
        if (policy == AssignPolicy.BOTH_WAYS) {
            isLecturerAssigned = universityClass.assign(this, AssignPolicy.ONE_WAY);
        }
        
        boolean isClassAssigned = true;
        if (isLecturerAssigned) {
            isClassAssigned = classes.add(universityClass);
            
            // TODO: Place in schedule
            boolean isPlacedInSchedule = true;
        }
        
        return isClassAssigned && isLecturerAssigned;
    }
    
    @Override
    public boolean unassign(UniversityClass universityClass, AssignPolicy policy) {
        if (universityClass == null) {
            return false;
        }
        
        /*
        if (universityClass.hasBadKey()) {
            return false;
        }
        */
        
        if (!classes.contains(universityClass)) {
            return false;
        }
        
        // We are ok to remove the class so far. See if it is ok with us.    
        boolean isLecturerUnassigned = true;
        if (policy == AssignPolicy.BOTH_WAYS) {
            isLecturerUnassigned = universityClass.unassign(this, AssignPolicy.ONE_WAY);
        }
        
        boolean isClassUnassigned = true;
        if (isLecturerUnassigned) {
            isClassUnassigned = classes.remove(universityClass);
            
            // TODO: Remove from schedule
            boolean isRemovedFromSchedule = true;
        }
        
        return isClassUnassigned && isLecturerUnassigned;
    }
    
    @Override
    public boolean update(UniversityClass universityClass, UpdateReason reason) {
        // TODO:
        return true;
    }
    
    @Override
    public boolean unassignAllClasses() {
        boolean areClassesUnassigned = true;
        
        for (UniversityClass universityClass : classes) {
            boolean isClassUnassigned = unassign(universityClass, AssignPolicy.ONE_WAY);
            areClassesUnassigned = areClassesUnassigned && isClassUnassigned;
        }
        
        classes.clear();
        
        // TODO:
        //schedule.clear();
        
        return areClassesUnassigned;
    }
    
    // LecturerData position info
    private int facultyID;
    private int departmentID;
    
    // LecturerData info
    private String name;
    
    // LecturerData class requirements
    private Set<String> attributes;
    
    //private boolean isStateLecturer;
    
    private Schedule schedule;
    private Set<UniversityClass> classes;
}
