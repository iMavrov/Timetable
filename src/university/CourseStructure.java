/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package university;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Mavrov
 */
public class CourseStructure {
    
    public CourseStructure(Specialty courseSpecialty, int courseYear) {
        specialty = courseSpecialty;
        year = courseYear;
        subjects = new ArrayList<>(courseSpecialty.getSemesterCount());
    }
    
    public void setSemesterSubjects(int semesterIndex, List<Subject> semesterSubjects) {
        subjects.add(semesterIndex, semesterSubjects);
    }
    
    @Override
    public String toString() {
        return specialty.getName() + " " + String.valueOf(year);
    }
    
    // Unique ID
    private int courseStructureID;
    
    // Members
    private Specialty specialty;
    private int year;
    private List< List<Subject> > subjects;
}
