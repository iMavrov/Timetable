/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package university;

import utilities.IObserver;

/**
 *
 * @author Mavrov
 */
public interface IClassObserver extends IObserver<UniversityClass> {
    
    boolean unassignAllClasses();
    
}
