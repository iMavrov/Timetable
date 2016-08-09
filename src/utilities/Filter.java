/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package utilities;

import java.util.List;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Multi-purpose collection filter.
 * 
 * Inspired by:
 * http://stackoverflow.com/questions/122105/what-is-the-best-way-to-filter-a-java-collection
 * 
 * @author Mavrov
 */
public class Filter<T extends Object, C extends Collection<T>> {
    
    public Filter() {
        criteria = new ArrayList<>();
    }
    
    public boolean addCriterion(FilterCriterion<T> newCriterion) {
        return criteria.add(newCriterion);
    }
    
    public C filterList(C inputList, C outputList) {
        outputList.clear();
        
        for (T item : inputList) {
            boolean doesItemPass = true;
            for (FilterCriterion<T> criterion : criteria) {
                if (!criterion.passes(item)) {
                    doesItemPass = false;
                    break;
                }
            }
            if (doesItemPass) {
                outputList.add(item);
            }
        }
        
        return outputList;
    }   
    
    private List<FilterCriterion<T>> criteria;
}
