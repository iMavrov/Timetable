package university;

import java.io.IOException;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.util.Objects;
import utilities.SystemIDType;
import utilities.SystemObject;

/**
 *
 * @author Mavrov
 */
public class Department extends SystemObject implements IPersistable, IKeyHolder {
    
    public Department() {
        super(SystemIDType.DEPARTMENT_ID);
        
        name = "";
    }
    
    public Department(String departmentName) {
        super(SystemIDType.DEPARTMENT_ID);
        
        name = departmentName;
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

    // From IPersistable
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
