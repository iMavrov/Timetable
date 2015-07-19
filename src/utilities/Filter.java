/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package utilities;

import java.util.Set;
import java.util.List;
import java.util.HashSet;
import java.util.ArrayList;

/**
 *
 * @author Mavrov
 */
public class Filter<T extends Object> {
    
    public Filter() {
        criteria = new ArrayList<>();
    }
    
    public boolean addCriterion(FilterCriterion<T> newCriterion) {
        return criteria.add(newCriterion);
    }
    
    public Set<T> filterList(Set<T> itemList) {
        Set<T> filteredItemList = new HashSet<>();
        
        for (T item : itemList) {
            boolean doesItemPass = true;
            for (FilterCriterion<T> criterion : criteria) {
                if (!criterion.passes(item)) {
                    doesItemPass = false;
                    break;
                }
            }
            if (doesItemPass) {
                filteredItemList.add(item);
            }
        }
        
        return filteredItemList;
    }   
    
    private List<FilterCriterion<T>> criteria;
}
