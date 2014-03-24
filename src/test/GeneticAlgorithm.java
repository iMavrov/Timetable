package test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 *
 * @author Mavrov
 */
public class GeneticAlgorithm {
    
    public GeneticAlgorithm() {
        initializeGenerator();
        
        // Initialize population
        Speciment firstSpeciment = new Speciment();
        population = new ArrayList<>(POPULATION_SIZE);
        population.add(firstSpeciment);
        bestSpeciment = firstSpeciment;

        for (int i = 1; i < POPULATION_SIZE; ++i) {
            Speciment newSpeciment = new Speciment();
            population.add(newSpeciment);
            
            if (newSpeciment.getFitness() > bestSpeciment.getFitness()) {
                bestSpeciment = newSpeciment;
            }
        }
        
        offspring = new ArrayList<>(POPULATION_SIZE);
        cumulativeFitness = new int[POPULATION_SIZE + 1];
        generationCount = 0;
    }
    
    public void calculateNextGeneration() {
        
        System.out.println(population.size());
        
        //// Prepare
        
        calculateCumulativeFitness();
        
        //// Reproduce species
        
        int speciesToReproduce = (int)(REPRODUCTION_RATE * POPULATION_SIZE);
        speciesToReproduce &= 0xFFFFFFFE; // Make even number
        
        int couplesCount = speciesToReproduce / 2;
        while (couplesCount > 0) {
            Speciment parent1 = chooseSpeciment();
            Speciment parent2 = chooseSpeciment();
            
            Speciment.Children children = parent1.crossGenomes(parent2);
            
            offspring.add(children.child1);
            offspring.add(children.child2);
            
            --couplesCount;
        }
        
        //// Transfer species to offspring unchanged
        
        int speciesToTransfer = POPULATION_SIZE - speciesToReproduce;
        while (speciesToTransfer > 0) {
            offspring.add(chooseSpeciment());
            --speciesToTransfer;
        }
        
        //// Mutate offspring
        
        int speciesToMutate = (int)(POPULATION_SIZE * MUTATION_RATE);
        while (speciesToMutate > 0) {
            int mutationIndex = generator.nextInt(POPULATION_SIZE);
            offspring.get(mutationIndex).mutateGenome();
            --speciesToMutate;
        }
        
        //// Move to next iteration
        
        population.clear();
        for (Speciment speciment : offspring) {
            if (speciment.getFitness() > bestSpeciment.getFitness()) {
                bestSpeciment = speciment;
            }
            
            population.add(speciment);
        }
        
        offspring.clear();
        
        ++generationCount;
    }
    
    public void runAlgorithm() {
        while(bestSpeciment.getFitness() < FITNESS_THRESHOLD) {
            calculateNextGeneration();
        }
        
        System.out.println(bestSpeciment);
        System.out.println("Reached at: " + generationCount);
    }
    
    private static final int POPULATION_SIZE = 128;
    private static final float REPRODUCTION_RATE = 0.5f;
    private static final float MUTATION_RATE = 0.05f;
    private static final int FITNESS_THRESHOLD = Speciment.MAX_FITNESS;
    
    private static Random generator;
    
    private List<Speciment> population;
    private List<Speciment> offspring;
    private Speciment bestSpeciment;
    private int[] cumulativeFitness;
    private int generationCount;
    
    private static void initializeGenerator() {
        if (generator == null) {
            generator = new Random();
        }
    }
    
    private void calculateCumulativeFitness() {
        cumulativeFitness[0] = 0;
        for (int i = 0; i < POPULATION_SIZE; ++i) {
            cumulativeFitness[i + 1] = cumulativeFitness[i] + population.get(i).getFitness();
        }
    }
    
    private Speciment chooseSpeciment() {
        int rouletteValue = generator.nextInt(cumulativeFitness[POPULATION_SIZE]);
            
        int l = 0;
        int r = POPULATION_SIZE;
        int m = (l + r) / 2;

        while(l != r) {
            if (rouletteValue < cumulativeFitness[m]) {
                r = m;
                m = (l + r) / 2;
                continue;
            } else if (cumulativeFitness[m + 1] <= rouletteValue) {
                l = m + 1;
                m = (l + r) / 2;
                continue;
            } else {
                break;
            }
        }

        return population.get(m);
    }
}
