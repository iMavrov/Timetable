package utilities;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Mavrov
 */
public class Combinatorics {
    // Input unique positive monotonically increasing numbers, positive target sum.
    // Ouputs an array list of summand combinations (presented as histograms) 
    // that sum up to target sum or null if input criteria is not met.
    public static List<int[]> getPartitions(List<Integer> summands, int targetSum) {
        // Input check
        if (summands == null || summands.isEmpty() || targetSum <= 0) {
            return null;
        }
        
        // Enforce sorted positive monotonically increasing summands
        int lastNumber = 0;
        for (int number : summands) {
            if (number <= lastNumber) {
                return null;
            }
            lastNumber = number;
        }

        // Prepare result list
        List<int[]> partitions = new ArrayList<>();
        
        int summandCount = summands.size();
        for (int summandIndex = 0; summandIndex < summandCount; ++summandIndex) {
            int maxSummand = summands.get(summandIndex);
            
            int[] summandHistogram = new int[summandCount];
            ++summandHistogram[summandIndex];
            
            addSummand(summands, summandHistogram, maxSummand, targetSum - maxSummand, partitions);
        }
        
        return partitions;
    }
    
    // Adds a summand from the summands list that is smaller than or equal to the maximum summand.
    // Increments the corresponding slot of the summand histogram and reduces the partial sum.
    // If partial sum is below zero it means we have passed the target and have reached an invalid combination.
    // If partial sum is equal to zero it means we have reached a solution and the combination is added to the list of solutions.
    // Note: Since all summands are added in monotonically decreasing order every reached solution is a unique one.
    private static void addSummand(List<Integer> summands, int[] summandHistogram, int maxSummand, int partialsSum, List<int[]> partitions) {
        if (partialsSum < 0) {
            return;
        }
        
        if (partialsSum == 0) {
            partitions.add(summandHistogram);
            return;  
        }
        
        int summandCount = summands.size();
        for (int summandIndex = 0; summandIndex < summandCount; ++summandIndex) {
            int summand = summands.get(summandIndex);
            if (summand <= maxSummand) {
                int[] newSummandHistogram = new int[summandCount];
                System.arraycopy(summandHistogram, 0, newSummandHistogram, 0, summandCount);
                ++newSummandHistogram[summandIndex];
                addSummand(summands, newSummandHistogram, summand, partialsSum - summand, partitions);
            }
        }
    }
}
