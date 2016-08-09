package university;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.Set;
import java.util.Objects;
import university.UniversityClass.AddLecturerEvent;
import university.UniversityClass.RemoveLecturerEvent;

/**
 * Represents a university lecturer.
 * 
 * @author Mavrov
 */
public class Lecturer extends ScheduleHolder {
    
    public Lecturer() {
        super();
        
        facultyID = University.INVALID_ID;
        departmentID = University.INVALID_ID;
        name = "";
    }
    
    public Lecturer(
            int lecturerFacultyID, 
            int lecturerDepartmentID,
            String lecturerName,
            Set<String> lecturerAttributes) {
        super();
        
        facultyID = lecturerFacultyID;
        departmentID = lecturerDepartmentID;
        name = lecturerName;
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
    
    // From Object
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
    
    // From IPersistable
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
    
    // From IAttributeHolder
    @Override
    public boolean hasBadKey() {
        return (departmentID == University.INVALID_ID) || name.isEmpty();
    }
    
    // From ScheduleHolder
    @Override
    public void onAddedLecturer(AddLecturerEvent event) {
        Lecturer lecturer = event.getLecturer();
        if (equals(lecturer)) {
            UniversityClass universityClass = event.getUniversityClass();
            schedule.add(universityClass);
        }
    }
    
    @Override
    public void onRemovedLecturer(RemoveLecturerEvent event) {
        Lecturer lecturer = event.getLecturer();
        if (equals(lecturer)) {
            UniversityClass universityClass = event.getUniversityClass();
            schedule.remove(universityClass);
        }
    }
    
    public boolean addClass(UniversityClass universityClass) {
        if (universityClass == null) {
            return false;
        }
        
        if (universityClass.hasBadKey()) {
            return false;
        }
        
        if (schedule.contains(universityClass)) {
            return false;
        }
        
        return universityClass.addLecturer(this);
    }
    
    @Override
    public boolean removeClass(UniversityClass universityClass) {
        if (universityClass == null) {
            return false;
        }
        
        if (universityClass.hasBadKey()) {
            return false;
        }
        
        if (!schedule.contains(universityClass)) {
            return false;
        }
        
        return universityClass.removeLecturer(this);
    }
    
    // LecturerData position info
    private int facultyID;
    private int departmentID;
    
    // LecturerData info
    private String name;
    
    //private boolean isStateLecturer;
}
