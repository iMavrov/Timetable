/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package timetable;

/**
 *
 * @author Mavrov
 */
public interface MutationOperator<T extends Speciment> {
    
    public void mutate(T speciment);
    
}
