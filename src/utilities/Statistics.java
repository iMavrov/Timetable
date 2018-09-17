package utilities;

import java.util.List;

/**
 *
 * @author Mavrov
 */
public class Statistics {
    public static <T extends Comparable<T>> T getMedian(List<T> list) {
        return getKMedian(list, list.size() / 2);
    }
    
    public static <T extends Comparable<T>> T getKMedian(List<T> list, int k) {
        if (list.isEmpty()) {
            return null;
        }
        
        int listSize = list.size();
        if (listSize == 1) {
            return list.get(0);
        }
        
        // Clamp k to [0, list size - 1]
        k = Math.min(Math.max(k, 0), listSize - 1);
        
        // Get a pivot approximation
        int pivotIndex = getPseudoPivotIndex(list);
        T pivot = list.get(pivotIndex);
        
        // Place pivot at the end of the list
        int rightIndex = listSize - 1;
        list.set(pivotIndex, list.get(rightIndex));
        list.set(rightIndex, pivot);
        pivotIndex = rightIndex;
        
        // Partition the list
        int leftIndex = 0;
        for (int i = 0; i < rightIndex; ++i) {
            if (list.get(i).compareTo(pivot) < 0) {
                T swap = list.get(leftIndex);
                list.set(leftIndex, list.get(i));
                list.set(i, swap);
                ++leftIndex;
            }
        }
        
        // Restore the pivot's position
        list.set(rightIndex, list.get(leftIndex));
        list.set(leftIndex, pivot);
        pivotIndex = leftIndex;
        
        if (k == pivotIndex) {
            return pivot;
        } else if (k < pivotIndex) {
            return getKMedian(list.subList(0, pivotIndex), k);
        } else {
            return getKMedian(list.subList(pivotIndex + 1, listSize), k - pivotIndex - 1);
        }
    }
    
    private static <T extends Comparable<T>> int getPseudoPivotIndex(List<T> list) {
        int leftIndex = 0;
        int rightIndex = list.size() - 1;
        int middleIndex = (leftIndex + rightIndex) / 2;
        
        T left = list.get(leftIndex);
        T right = list.get(rightIndex);
        T middle = list.get(middleIndex);
        
        if (left.compareTo(middle) < 0) {
            if (middle.compareTo(right) < 0) {
                return middleIndex;
            } else if (left.compareTo(right) < 0) {
                return rightIndex;
            } else {
                return leftIndex;
            }
        } else {
            if (middle.compareTo(right) < 0) {
                if (left.compareTo(right) < 0) {
                    return leftIndex;
                } else {
                    return rightIndex;
                }
            } else {
                return middleIndex;
            }
        }
    }
}
