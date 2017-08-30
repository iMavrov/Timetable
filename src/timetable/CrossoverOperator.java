package timetable;

import java.util.List;

/**
 *
 * @author Mavrov
 */
public interface CrossoverOperator<T extends Speciment> {

    public List<T> cross(List<T> parents);
    
}
