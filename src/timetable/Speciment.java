/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package timetable;

/**
 *
 * @author Mavrov
 */
public abstract class Speciment implements Comparable<Speciment> {
    
    public static final int INVALID_FITNESS = -1;
    
    public Speciment() {
        fitness = INVALID_FITNESS;
    }
    
    public int getFitness() {
        return fitness;
    }
    
    public void setFitness(int fitness) {
        this.fitness = fitness;
    }
    
    @Override
    public int compareTo(Speciment other) {
        return other.getFitness() - getFitness();
    }
    
    protected int fitness;
}
