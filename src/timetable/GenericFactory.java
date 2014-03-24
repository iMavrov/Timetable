/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package timetable;

/**
 *
 * @author Mavrov
 */
public class GenericFactory<T> {
    
    public GenericFactory(Class<T> genericTypeClass) {
        this.genericTypeClass = genericTypeClass;
    }
    
    public T newInstance() throws InstantiationException, IllegalAccessException {
        return genericTypeClass.newInstance();
    }
    
    private Class<T> genericTypeClass;
}
