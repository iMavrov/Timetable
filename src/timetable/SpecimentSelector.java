package timetable;

import java.util.List;

/**
 *
 * @author Mavrov
 */
public interface SpecimentSelector<T extends Speciment> {
    
    public void updateSpeciments(List<T> newSpeciments);
    
    public T selectSpeciment();
}
