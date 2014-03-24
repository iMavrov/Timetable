/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package timetable;

/**
 *
 * @author Mavrov
 */
public class Statistics {
    
    public Statistics() {

    }

    public int getBestFitness() {
        return maximumFitness[populationIndex % HISTORY_LENGTH];
    }

    @Override
    public String toString() {
        return "Statistics";
    }

    private static final int HISTORY_LENGTH = 1024;

    private int populationIndex;
    private int[] minimumFitness;
    private int[] averageFitness;
    private int[] maximumFitness;
    Speciment bestSpeciment;
}
