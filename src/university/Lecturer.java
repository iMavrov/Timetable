/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package university;

import java.io.IOException;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.util.Set;
import java.util.HashSet;
import java.util.Objects;

/**
 *
 * @author Mavrov
 */
public class Lecturer implements IPersistable, IKeyHolder, IAttributeHolder {
    
    public Lecturer() {
        facultyID = University.INVALID_ID;
        departmentID = University.INVALID_ID;
        name = "";
        attributes = new HashSet<>();
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
    
    // Lecturer position info
    private int facultyID;
    private int departmentID;
    
    // Lecturer info
    private String name;
    
    // Lecturer class requirements
    private Set<String> attributes;
}
