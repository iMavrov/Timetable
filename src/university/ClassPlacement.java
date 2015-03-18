/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package university;

import java.io.IOException;
import java.io.BufferedReader;
import java.io.BufferedWriter;

/**
 *
 * @author Mavrov
 */
public class ClassPlacement implements IPersistable {
    
    public ClassPlacement() {
        start = 0;
        roomID = University.INVALID_ID;
    }
    
    public int getStartWeekHour() {
        return start;
    }
    
    public int getRoomID() {
        return roomID;
    }

    @Override
    public boolean load(BufferedReader reader) throws IOException {
        start = Integer.valueOf(reader.readLine());
        roomID = Integer.valueOf(reader.readLine());
        
        return true;
    }

    @Override
    public boolean save(BufferedWriter writer) throws IOException {
        writer.write(String.valueOf(start));
        writer.newLine();
        
        writer.write(String.valueOf(roomID));
        writer.newLine();
        
        return true;
    }
    
    // When? Hour of the week [0 - 167]
    private int start;
    
    // Where? The room's ID in which the class is to take place.
    private int roomID;
}
