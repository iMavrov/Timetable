/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package timetable;

/**
 *
 * @author Mavrov
 */
public interface FitnessEvaluator<T extends Speciment> {
    
    public void evaluateFitness(T speciment);
    
}
