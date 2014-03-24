/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package university;

import static university.EducationType.PART_TIME;
import static university.EducationType.REGULAR;

/**
 *
 * @author Mavrov
 */
public class Specialty {
    
    public Specialty(String specialtyName, DegreeType specialtyDegree, EducationType specialtyEducation, int count) {
        name = specialtyName;
        degree = specialtyDegree;
        education = specialtyEducation;
        semesterCount = count;
    }
    
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder(name);
        switch (degree) {
            case BACHELOR: {
                builder.append(" - бакалавър");
                break;
            }
            case MASTER: {
                builder.append(" - магистър");
            }
        }
        switch (education) {
            case REGULAR: {
                builder.append(" - редовно");
                break;
            }
            case PART_TIME: {
                builder.append(" - задочно");
            }
        }
        return builder.toString();
    }
    
    public int getSemesterCount() {
        return semesterCount;
    }
    
    public String getName() {
        return name;
    }
    
    // Unique ID
    private int specialtyID;
    
    // Members
    private String name;
    private DegreeType degree;
    private EducationType education;
    private int semesterCount;
}
