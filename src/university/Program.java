/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package university;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;

/**
 *
 * @author Mavrov
 */
public class Program implements IPersistable {
    
    public Program() {
        id = University.INVALID_ID;
    }
    
    public Program(
            int programID,
            EducationDegree programDegree,
            EducationModel programModel,
            String programName,
            int programYear,
            int programDuration,
            CourseStructure programStructure) {
        id = programID;
        degree = programDegree;
        model = programModel;
        name = programName;
        year = programYear;
        duration = programDuration;
        structure = programStructure;
    }
    
    public EducationDegree getDegree() {
        return degree;
    }

    public void setDegree(EducationDegree programDegree) {
        degree = programDegree;
    }

    public EducationModel getModel() {
        return model;
    }

    public void setModel(EducationModel programModel) {
        model = programModel;
    }

    public String getName() {
        return name;
    }

    public void setName(String programName) {
        name = programName;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int programYear) {
        year = programYear;
    }

    public int getSemesterCount() {
        return duration;
    }

    public void setSemesterCount(int programDuration) {
        duration = programDuration;
    }

    public CourseStructure getStructure() {
        return structure;
    }

    public void setStructure(CourseStructure programStructure) {
        structure = programStructure;
    }
    
    @Override
    public boolean equals(Object o) {
        if (o == null) {
            return false;
        }
        
        if (getClass() != o.getClass()) {
            return false;
        }
        
        final Program program = (Program)o;
        return id == program.id;
    }
    
    @Override
    public int hashCode() {
        return id;
    }
    
    @Override
    public boolean save(BufferedWriter writer) throws IOException {
        writer.write(String.valueOf(id));
        writer.newLine();
        
        writer.write(String.valueOf(degree));
        writer.newLine();
        writer.write(String.valueOf(model));
        writer.newLine();
        writer.write(name);
        writer.newLine();
        writer.write(String.valueOf(year));
        writer.newLine();
        writer.write(duration);
        writer.newLine();
        
        structure.save(writer);
        
        return true;
    }
    
    @Override
    public boolean load(BufferedReader reader) throws IOException {
        id = Integer.valueOf(reader.readLine());
        
        degree = EducationDegree.valueOf(reader.readLine());
        model = EducationModel.valueOf(reader.readLine());
        name = reader.readLine();
        year = Integer.valueOf(reader.readLine());
        duration = Integer.valueOf(reader.readLine());
        
        structure.load(reader);
        
        return true;
    }
    
    // Key
    private int id;
    
    private EducationDegree degree; // Bachelor, Master
    private EducationModel model;   // Regular, Offsite, Distance
    private String name;  // Computer Science
    private int year;  // 2014
    private int duration; // 8 semesters
    private CourseStructure structure; // Subjects in semesters
}
