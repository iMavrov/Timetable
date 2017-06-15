package university;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.Set;
import java.util.Objects;
import university.UniversityClass.AddLecturerEvent;
import university.UniversityClass.RemoveLecturerEvent;
import utilities.SystemID;
import utilities.SystemIDType;

/**
 * Represents a university lecturer.
 * 
 * @author Mavrov
 */
public class Lecturer extends ScheduleHolder {
    
    public Lecturer() {
        super(SystemIDType.LECTURER_ID);
        
        faculty = null;
        department = null;
        name = "";
    }
    
    public Lecturer(
            Faculty lecturerFaculty, 
            Department lecturerDepartment,
            String lecturerName,
            Set<String> lecturerAttributes) {
        super(SystemIDType.LECTURER_ID);
        
        faculty = lecturerFaculty;
        department = lecturerDepartment;
        name = lecturerName;
    }
    
    public Faculty getFaculty() {
        return faculty;
    }

    public void setFaculty(Faculty newLecturerFaculty) {
        faculty = newLecturerFaculty;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartmentID(Department lecturerDepartment) {
        department = lecturerDepartment;
    }

    public String getName() {
        return name;
    }

    public void setName(String lecturerName) {
        name = lecturerName;
    }
    
    @Override
    public String toString() {
        return name;
    }
    
    // From IPersistable
    @Override
    public boolean save(BufferedWriter writer) throws IOException {
        faculty.getID().save(writer);
        department.getID().save(writer);
        
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
        SystemID facultyID = new SystemID();
        facultyID.load(reader);
        faculty = University.getInstance().getFaculty(facultyID.getIndex());
        
        SystemID departmentID = new SystemID();
        departmentID.load(reader);
        department = University.getInstance().getDepartment(departmentID.getIndex());

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
        return (department == null) || name.isEmpty();
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
    
    // Lecturer position info
    private Faculty faculty;
    private Department department;
    
    // Lecturer info
    private String name;
    
    // Can be inferred from the null faculty and department
    //private boolean isStateLecturer;
}
