package university;

import java.io.IOException;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import utilities.SystemIDType;
import utilities.SystemObject;

/**
 *
 * @author Mavrov
 */
public class Faculty extends SystemObject implements IPersistable, IKeyHolder {
    
    public Faculty() {
        super(SystemIDType.FACULTY_ID);
        
        name = "";
    }
    
    public Faculty(String facultyName) {
        super(SystemIDType.FACULTY_ID);
        
        name = facultyName;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String newName) {
        name = newName;
    }
    
    @Override
    public String toString() {
        return name;
    }
    
    @Override
    public boolean load(BufferedReader reader) throws IOException {
        name = reader.readLine();
        
        return true;
    }

    @Override
    public boolean save(BufferedWriter writer) throws IOException {
        writer.write(name);
        writer.newLine();
        
        return true;
    }
    
    @Override
    public boolean hasBadKey() {
        return name.isEmpty();
    }
    
    private String name;
}
