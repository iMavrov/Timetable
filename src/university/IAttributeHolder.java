/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package university;

import java.util.Set;

/**
 *
 * @author Mavrov
 */
public interface IAttributeHolder {
    
    boolean hasAttribute(String attribute);

    boolean addAttribute(String attribute);

    boolean removeAttribute(String attribute);
}
