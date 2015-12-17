/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package university;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;

/**
 *
 * @author Mavrov
 */
public interface IPersistable {
    
    //
    boolean load(BufferedReader reader) throws IOException;
    
    //
    boolean save(BufferedWriter writer) throws IOException;
}
