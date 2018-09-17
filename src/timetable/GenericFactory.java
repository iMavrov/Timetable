package timetable;

/**
 *
 * @author Mavrov
 * @param <T>
 */
public class GenericFactory<T> {
    
    public GenericFactory(Class<T> genericTypeClass) {
        this.genericTypeClass = genericTypeClass;
    }
    
    public T newInstance() {
        T result;
        
        try {
            result = genericTypeClass.newInstance();
        } catch (IllegalAccessException | InstantiationException ex) {
            result = null;
        }
        
        return result;
    }
    
    private final Class<T> genericTypeClass;
}
