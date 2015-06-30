package utilities;

/**
 *
 * @author Mavrov
 */
public interface FilterCriterion<T extends Object> {
    
    boolean passes(T item);
    
}
