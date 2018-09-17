package timetable;

/**
 *
 * @author Mavrov
 * @param <T>
 */
public interface CrossoverOperator<T extends Speciment> {

    public boolean cross(T parent0, T parent1, T child0, T child1);
    
}
