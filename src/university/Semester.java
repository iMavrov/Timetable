/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package university;

import java.util.Map;
import java.util.Set;
import java.util.List;

/**
 *
 * @author Mavrov
 */
public class Semester {
    private SemesterType type;
    private int calendarYear;
    
    // Room view
    private Map<Room, Availability> rooms;
    
    // Lecturer view
    private Map<Lecturer, Availability> lecturers;         // State lecturers
    private Map<Lecturer, Availability> semesterLecturers; // Semester specific lecturers
    
    // Program view
    private Map<Program, StudentStructure> subjects; // Regular subjects and student distribution per program
    
    // Semester specific subjects and their capacity
    private Map<Subject, Integer> semesterSubjects;
    
    // Result
    private Set<UniversityClass> classes;
    
    // Lecturer ID to a list of UniversityClass IDs
    private Map<Integer, List<Integer>> lecturerToClass;
    
    // UniversityClass ID to Lecturer ID
    private Map<Integer, List<Integer>> classToLecturer;
    
    // Subgroup IDs to UniversityClass ID
    private Map<Integer, List<Integer>> studentsToClass;
    
    // UniversityClass ID to Subgroup IDs
    private Map<Integer, List<Integer>> classToStudents;
}
