package timetable;

import java.util.List;

/**
 *
 * @author Mavrov
 */
public interface CrossOperator<T extends Speciment> {

    public List<T> cross(List<T> parents);
    
}
