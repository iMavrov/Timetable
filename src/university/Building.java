package university;

import java.io.IOException;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.util.Objects;
import utilities.SystemIDType;
import utilities.SystemObject;

/**
 * Represents a faculty building of the university's campus.
  * 
 * @author Mavrov
 */
public class Building extends SystemObject implements IPersistable, IKeyHolder {
    
    public Building() {
        super(SystemIDType.BUILDING_ID);
        
        name = "";
    }
    
    public Building(int buildingID, String buildingName) {
        super(SystemIDType.BUILDING_ID);
        
        name = buildingName;
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
        id.load(reader);
        name = reader.readLine();
        
        return true;
    }

    @Override
    public boolean save(BufferedWriter writer) throws IOException {
        id.save(writer);
        
        writer.write(name);
        writer.newLine();
        
        return true;
    }
    
    @Override
    public boolean hasBadKey() {
        return (name == null) || name.isEmpty();
    }
    
    private String name;
}
