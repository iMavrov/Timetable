/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package university;

/**
 *
 * @author Mavrov
 */
public enum SemesterType {
    WINTER,
    SUMMER;
    
    public SemesterType getSemesterType(int semesterIndex) {
        if (semesterIndex % 2 == 0) {
            return WINTER;
        } else {
            return SUMMER;
        }
    }
}
