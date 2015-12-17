/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package utilities;

/**
 *
 * @author Mavrov
 */
public interface IObserver<T extends Object> {
    
    boolean assign(T observerable, AssignPolicy policy);
    
    boolean unassign(T observerable, AssignPolicy policy);
    
    boolean update(T observerable, UpdateReason reason);
    
}
