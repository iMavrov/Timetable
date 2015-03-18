/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package university;

/**
 *
 * @author Mavrov
 */
public interface IAttributeHolder {
    
    public boolean hasAttribute(String attribute);

    public boolean addAttribute(String attribute);

    public boolean removeAttribute(String attribute);
    
}
