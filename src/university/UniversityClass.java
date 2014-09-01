/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package university;

import java.util.Set;
import java.util.List;

/**
 *
 * @author Mavrov
 */
public class UniversityClass {
    private int id;
    
    private String name;
    private int duration;
    private UniversityClassType type;
    
    // Room info
    private int capacity; // sum of all subgroups' capacities this class is read to
    
    // Requirements info
    private Set<String> attributes;
    
    // Subject connection
    private int subjectID;
}