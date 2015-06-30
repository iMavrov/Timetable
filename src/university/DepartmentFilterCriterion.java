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
public class DepartmentFilterCriterion implements FilterCriterion<Lecturer> {

    public DepartmentFilterCriterion(int departmentID) {
        this.departmentID = departmentID;
    }
    
    @Override
    public boolean passes(Lecturer lecturer) {
        return (lecturer.getDepartmentID() == departmentID);
    }
    
    private int departmentID;
}
