/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package timetable;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Mavrov
 */
public class GeneticAlgorithm<T extends Speciment> {
    
    public GeneticAlgorithm (GenericFactory<T> specimentFactory, 
                             SpecimentSelector<T> crossSelector,
                             SpecimentSelector<T> transferSelector,
                             SpecimentSelector<T> mutationSelector,
                             CrossOperator<T> crossOperator,
                             MutationOperator<T> mutationOperator,
                             FitnessEvaluator<T> fitnessEvaluator,
                             Terminator terminator) {
        this.specimentFactory = specimentFactory;
        this.crossSelector = crossSelector;
        this.transferSelector = transferSelector;
        this.mutationSelector = mutationSelector;
        this.crossOperator = crossOperator;
        this.mutationOperator = mutationOperator;
        this.fitnessEvaluator = fitnessEvaluator;
        this.terminator = terminator;
                
        // Initialize population
        population = new ArrayList<>(POPULATION_SIZE);
        offspring = new ArrayList<>(POPULATION_SIZE);
        for (int i = 0; i < POPULATION_SIZE; ++i) {
            T newSpeciment;
            
            try {
                newSpeciment = specimentFactory.newInstance();
            } catch (IllegalAccessException | InstantiationException ex) {
                System.err.println(ex.getMessage());
                --i;
                continue;
            }
            
            fitnessEvaluator.evaluateFitness(newSpeciment);
            population.add(newSpeciment);
        }
        
        statistics = new Statistics();
    }
    
    public void runAlgorithm() {
        while(!terminator.terminateAlgorithm(statistics)) {
            calculateNextGeneration();
        }
        
        System.out.println(statistics);
    }
    
    private static final int POPULATION_SIZE = 128;
    private static final float REPRODUCTION_RATE = 0.5f;
    private static final float MUTATION_RATE = 0.05f;
    private static final int FITNESS_THRESHOLD = 1000;

    private List<T> population;
    private List<T> offspring;
    
    private GenericFactory<T> specimentFactory;
    private SpecimentSelector<T> crossSelector;
    private SpecimentSelector<T> transferSelector;
    private SpecimentSelector<T> mutationSelector;
    private CrossOperator<T> crossOperator;
    private MutationOperator<T> mutationOperator;
    private FitnessEvaluator<T> fitnessEvaluator;
    
    private Statistics statistics;
    private Terminator terminator;
    
    private void calculateNextGeneration() {
        //// Prepare selection algorithms
        
        crossSelector.updateSpeciments(population);
        transferSelector.updateSpeciments(population);
        
        //// Reproduce species
        
        int speciesToReproduce = (int)(REPRODUCTION_RATE * POPULATION_SIZE);
        speciesToReproduce &= 0xFFFFFFFE; // Make even number
        
        int couplesCount = speciesToReproduce / 2;
        while (couplesCount > 0) {
            List<T> parents = new ArrayList<>(2);
            parents.add(crossSelector.selectSpeciment());
            parents.add(crossSelector.selectSpeciment());
            
            List<T> children = crossOperator.cross(parents);
            for (T child : children) {
                fitnessEvaluator.evaluateFitness(child);
                offspring.add(child);
            }
            
            --couplesCount;
        }
        
        //// Transfer species to offspring unchanged
        
        int speciesToTransfer = POPULATION_SIZE - speciesToReproduce;
        while (speciesToTransfer > 0) {
            T selectedSpeciment = transferSelector.selectSpeciment();
            offspring.add(selectedSpeciment);
            --speciesToTransfer;
        }
        
        //// Mutate offspring
        
        mutationSelector.updateSpeciments(offspring);
        
        int speciesToMutate = (int)(POPULATION_SIZE * MUTATION_RATE);
        while (speciesToMutate > 0) {
            T specimentToMutate = mutationSelector.selectSpeciment();
            mutationOperator.mutate(specimentToMutate);
            fitnessEvaluator.evaluateFitness(specimentToMutate);
            --speciesToMutate;
        }
        
        //// Move to next iteration
        
        population.clear();
        population = offspring;
        offspring.clear();
    }
}
