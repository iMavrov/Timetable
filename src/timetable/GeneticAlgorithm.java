package timetable;

import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

/**
 *
 * @author Mavrov
 * @param <T>
 */
public class GeneticAlgorithm <T extends Speciment> {
    
    public GeneticAlgorithm (
        GenericFactory<T> inputSpecimentFactory, 
        SpecimentSelector<T> inputCrossSelector,
        SpecimentSelector<T> inputTransferSelector,
        SpecimentSelector<T> inputMutationSelector,
        CrossoverOperator<T> inputCrossOperator,
        MutationOperator<T> inputMutationOperator,
        FitnessEvaluator<T> inputFitnessEvaluator,
        Terminator inputTerminator
    ) {
        specimentFactory = inputSpecimentFactory;
        crossSelector = inputCrossSelector;
        transferSelector = inputTransferSelector;
        mutationSelector = inputMutationSelector;
        crossOperator = inputCrossOperator;
        mutationOperator = inputMutationOperator;
        fitnessEvaluator = inputFitnessEvaluator;
        terminator = inputTerminator;
                
        // Initialize population
        population = new ArrayList<>(Settings.POPULATION_SIZE);
        offspring = new ArrayList<>(Settings.POPULATION_SIZE);
        for (int i = 0; i < Settings.POPULATION_SIZE; ++i) {
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

    private List<T> population;
    private List<T> offspring;
    
    private GenericFactory<T> specimentFactory;
    private SpecimentSelector<T> crossSelector;
    private SpecimentSelector<T> transferSelector;
    private SpecimentSelector<T> mutationSelector;
    private CrossoverOperator<T> crossOperator;
    private MutationOperator<T> mutationOperator;
    private FitnessEvaluator<T> fitnessEvaluator;
    
    private Statistics statistics;
    private Terminator terminator;
    
    private void calculateNextGeneration() {
        //
        
        Collections.sort(population);
        
        // Prepare selection algorithms
        
        //crossSelector.updateSpeciments(population);
        //transferSelector.updateSpeciments(population);
        
        // Reproduce species
        
        int speciesToReproduce = (int)(Settings.REPRODUCTION_RATE * Settings.POPULATION_SIZE);
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
        
        // Transfer species to offspring unchanged
        
        int speciesToTransfer = Settings.POPULATION_SIZE - speciesToReproduce;
        while (speciesToTransfer > 0) {
            T selectedSpeciment = transferSelector.selectSpeciment();
            offspring.add(selectedSpeciment);
            --speciesToTransfer;
        }
        
        // Mutate offspring
        
        mutationSelector.updateSpeciments(offspring);
        
        int speciesToMutate = (int)(Settings.POPULATION_SIZE * Settings.MUTATION_RATE);
        while (speciesToMutate > 0) {
            T specimentToMutate = mutationSelector.selectSpeciment();
            mutationOperator.mutate(specimentToMutate);
            fitnessEvaluator.evaluateFitness(specimentToMutate);
            --speciesToMutate;
        }
        
        // Move to next iteration
        List<T> swapHelper = population;
        population = offspring;
        offspring = swapHelper;
        offspring.clear();
    }
}
