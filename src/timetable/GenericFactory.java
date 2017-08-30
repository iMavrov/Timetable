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
    
    public T newInstance() throws InstantiationException, IllegalAccessException {
        return genericTypeClass.newInstance();
    }
    
    private final Class<T> genericTypeClass;
}
