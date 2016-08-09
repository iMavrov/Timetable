package university;

/**
 *
 * @author Mavrov
 */
public interface IAttributeHolder {

    boolean addAttribute(String attribute);

    boolean removeAttribute(String attribute);
    
    boolean hasAttribute(String attribute);

}
