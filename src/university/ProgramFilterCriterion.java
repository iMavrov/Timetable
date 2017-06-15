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

    public ProgramFilterCriterion(Program filterProgram) {
        program = filterProgram;
    }
    
    @Override
    public boolean passes(UniversityClass item) {
        return (item.getSubject().getProgram().equals(program));
    }
    
    private Program program;
}
