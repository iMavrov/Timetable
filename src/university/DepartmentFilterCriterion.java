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

    public DepartmentFilterCriterion(Department targetDepartment) {
        department = targetDepartment;
    }
    
    @Override
    public boolean passes(Lecturer lecturer) {
        return lecturer.getDepartment().equals(department);
    }
    
    private Department department;
}
