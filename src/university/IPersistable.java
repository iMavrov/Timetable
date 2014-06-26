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
    public boolean load(BufferedReader reader) throws IOException;
    
    //
    public boolean save(BufferedWriter writer) throws IOException;
}
