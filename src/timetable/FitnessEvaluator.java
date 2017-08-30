package timetable;

/**
 *
 * @author Mavrov
 * @param <T>
 */
public interface FitnessEvaluator<T extends Speciment> {
    
    public int evaluateFitness(T speciment);
    
}
