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

/**
 *
 * @author Mavrov
 */
public class Lecturer implements IPersistable {
    
    public Lecturer() {
        id = University.INVALID_ID;
        attributes = new HashSet<>();
    }
    
    public Lecturer(
            int lecturerID,
            String lecturerFaculty, 
            String lecturerDepartment,
            String lecturerName,
            Set<String> lecturerAttributes) {
        id = lecturerID;
        faculty = lecturerFaculty;
        department = lecturerDepartment;
        name = lecturerName;
        attributes.addAll(lecturerAttributes);
    }

    public String getFaculty() {
        return faculty;
    }

    public void setFaculty(String lecturerFaculty) {
        faculty = lecturerFaculty;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String lecturerDepartment) {
        department = lecturerDepartment;
    }

    public String getName() {
        return name;
    }

    public void setName(String lecturerName) {
        name = lecturerName;
    }
    
    public boolean hasAttribute(String attribute) {
        return attributes.contains(attribute);
    }

    public boolean addAttribute(String attribute) {
        return attributes.add(attribute);
    }

    public boolean removeAttribute(String attribute) {
        return attributes.remove(attribute);
    }
    
    @Override
    public boolean save(BufferedWriter writer) throws IOException {
        writer.write(id);
        writer.newLine();
        
        writer.write(faculty);
        writer.newLine();
        writer.write(department);
        writer.newLine();
        writer.write(name);
        writer.newLine();

        return true;
    }
    
    @Override
    public boolean load(BufferedReader reader) throws IOException {
        id = Integer.valueOf(reader.readLine());
        
        faculty = reader.readLine();
        department = reader.readLine();
        name = reader.readLine();

        return true;
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
        return id == other.id;
    }

    @Override
    public int hashCode() {
        return id;
    }
    
    @Override
    public String toString() {
        return name;
    }
    
    // Key
    private int id;
    
    // Lecturer position info
    private String faculty;
    private String department;
    
    // Lecturer info
    private String name;
    
    // Lecturer class requirements
    private Set<String> attributes;
}
