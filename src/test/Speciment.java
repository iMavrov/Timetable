package test;

import java.util.*;

/**
 *
 * @author Mavrov
 */
public class Speciment {
    
    public static final int MAX_FITNESS = 50;
    
    public static class Children {
        public Speciment child1;
        public Speciment child2;
        
        public Children(Speciment child1, Speciment child2) {
            this.child1 = child1;
            this.child2 = child2;
        }
    }
    
    public Speciment() {
        initializeDigits();
        initializeGenerator();
        
        Collections.shuffle(digits);
        
        numbers = new Integer[DIGIT_COUNT];
        numbers = digits.toArray(numbers);
        
        calculateFitness();
    }
    
    public int getFitness() {
        return fitness;
    }
    
    public Children crossGenomes(Speciment other) {
        Speciment a = new Speciment();
        Speciment b = new Speciment();
        
        for (int i = 0; i < DIGIT_COUNT / 2; ++i) {
            a.numbers[i] = numbers[i];
            b.numbers[i] = other.numbers[i];
        }
        for (int i = DIGIT_COUNT / 2; i < DIGIT_COUNT; ++i) {
            a.numbers[i] = other.numbers[i];
            b.numbers[i] = numbers[i];
        }
        
        a.fixGenome();
        b.fixGenome();
        
        return new Children(a, b);
    }
    
    public void mutateGenome() {
        int indexA = generator.nextInt(DIGIT_COUNT);
        int indexB = generator.nextInt(DIGIT_COUNT);
        
        int buffer = numbers[indexA];
        numbers[indexA] = numbers[indexB];
        numbers[indexB] = buffer;
        
        calculateFitness();
    }
    
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder(DIGIT_COUNT + 5);
        
        builder.append("[");
        for (int i = 0; i < DIGIT_COUNT; ++i) {
            builder.append(numbers[i]);
        }
        builder.append("]-");
        builder.append(fitness);
        
        return builder.toString();
    }
    
    private static final int DIGIT_COUNT = 10;
    
    private static List<Integer> digits = null;
    private static Random generator;
    
    private Integer[] numbers;
    private int fitness;
    
    private static void initializeDigits() {
        if (digits == null) {
            digits = new ArrayList<>(DIGIT_COUNT);
            for (int i = 0; i < DIGIT_COUNT; ++i) {
                digits.add(i);
            }
        }
    }
    
    private static void initializeGenerator() {
        if (generator == null) {
            generator = new Random();
        }
    }
        
    private void fixGenome() {
        boolean[] digitMap = new boolean[DIGIT_COUNT];
        for (int i = 0; i < DIGIT_COUNT; ++i) {
            digitMap[numbers[i]] = true;
        }
        
        int unusedDigit = 9;
        boolean[] collisionMap = new boolean[DIGIT_COUNT];
        for (int i = 0; i < DIGIT_COUNT; ++i) {
            if (collisionMap[numbers[i]]) {
                while(digitMap[unusedDigit]) {
                    --unusedDigit;
                }
                
                numbers[i] = unusedDigit;
                digitMap[unusedDigit] = true;
            }
            
            collisionMap[numbers[i]] = true;
        }
        
        calculateFitness();
    }
    
    private void calculateFitness() {
        int inversions = 0;
        
        for (int i = 0; i < DIGIT_COUNT; ++i) {
            inversions += Math.abs(numbers[i] - i);
        }
        
        fitness = MAX_FITNESS - inversions;
    }
}
