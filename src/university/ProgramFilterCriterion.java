/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package university;

import utilities.FilterCriterion;

/**
 *
 * @author Mavrov
 */
public class ProgramFilterCriterion implements FilterCriterion<UniversityClass> {

    public ProgramFilterCriterion(int programID, int semesterIndex) {
        this.programID = programID;
        this.semesterIndex = semesterIndex;
    }
    
    @Override
    public boolean passes(UniversityClass item) {
        return (item.getProgramID() == programID) && (item.getSemesterIndex() == semesterIndex);
    }
    
    private int programID;
    private int semesterIndex;
}
