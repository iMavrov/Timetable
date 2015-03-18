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
public class UniversityClass implements IPersistable, IAttributeHolder {
    
    public UniversityClass() {
        programID = University.INVALID_ID;
        semesterIndex = University.INVALID_ID;
        semesterSubjectIndex = University.INVALID_ID;
        type = UniversityClassType.LECTION;
        name = "";
        duration = 0;
        capacity = 0;
        attributes = new HashSet<>();
    }
    
    public UniversityClass(
            int classProgramID, int classSemesterIndex, 
            int classSemesterSubjectIndex, UniversityClassType classType,
            String subjectName, int classDuration, int classCapacity) {
        programID = classProgramID;
        semesterIndex = classSemesterIndex;
        semesterSubjectIndex = classSemesterSubjectIndex;
        type = classType;
        name = subjectName;
        duration = classDuration;
        capacity = classCapacity;
        attributes = new HashSet<>();
        
        switch(type) {
            case LECTION: {
                name = name + ": Lection";
                break;
            }
            case LABORATORY: {
                name = name + ": Lab";
                break;
            }
            case SEMINAR: {
                name = name + ": Seminar";
                break;
            }
        }
    }
    
    public Subject getSubject() {
        Program program = University.getInstance().getProgram(programID);
        
        if (program == null) {
            return null;
        }
        
        return program.getSubject(semesterIndex, semesterSubjectIndex);
    }
    
    public UniversityClassType getType() {
        return type;
    }
    
    public String getName() {
        return name;
    }
    
    public int getDuration() {
        return duration;
    }
    
    public int getCapacity() {
        return capacity;
    }
    
    @Override
    public boolean load(BufferedReader reader) throws IOException {
        programID = Integer.valueOf(reader.readLine());
        semesterIndex = Integer.valueOf(reader.readLine());
        semesterSubjectIndex = Integer.valueOf(reader.readLine());

        type = UniversityClassType.valueOf(reader.readLine());
        
        name = reader.readLine();
    
        duration = Integer.valueOf(reader.readLine());

        capacity = Integer.valueOf(reader.readLine());
    
        int attributeCount = Integer.valueOf(reader.readLine());
        while (0 < attributeCount) {
            String newAttribute = reader.readLine();
            
            attributes.add(newAttribute);
            
            --attributeCount;
        }
        
        return true;
    }

    @Override
    public boolean save(BufferedWriter writer) throws IOException {
        writer.write(String.valueOf(programID));
        writer.newLine();
        
        writer.write(String.valueOf(semesterIndex));
        writer.newLine();
        
        writer.write(String.valueOf(semesterSubjectIndex));
        writer.newLine();
                
        writer.write(type.toString());
        writer.newLine();
        
        writer.write(name);
        writer.newLine();
        
        writer.write(String.valueOf(duration));
        writer.newLine();
        
        writer.write(String.valueOf(capacity));
        writer.newLine();
        
        writer.write(String.valueOf(attributes.size()));
        writer.newLine();
        
        for (String attribute : attributes) {
            writer.write(attribute);
            writer.newLine();
        }
        
        return true;
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
    
    // Parent subject
    private int programID;
    private int semesterIndex;
    private int semesterSubjectIndex;
    
    // Which type of the subject's classes is this one
    private UniversityClassType type;
    
    // The generated name for the class
    private String name;
    
    // The class'es duration
    private int duration;
    
    // Sum of all group capacities this class is read to
    private int capacity;
    
    // Requirements info
    private Set<String> attributes;
}