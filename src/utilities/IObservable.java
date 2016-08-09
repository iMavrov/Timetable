/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package utilities;

/**
 *
 * @author Mavrov
 */
public interface IObservable<T> {

    void updateObservers(Observation observation);
    
    void addObserver(IObserver<T> observer);
    void removeObserver(IObserver<T> observer);
}
