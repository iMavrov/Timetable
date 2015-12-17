package university;

import java.io.IOException;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.util.Objects;

/**
 * Represents a faculty building of the university's campus.
  * 
 * @author Mavrov
 */
public class Building implements IPersistable, IKeyHolder {
    
    public Building() {
        id = University.INVALID_ID;
        name = "";
    }
    
    public Building(int buildingID, String buildingName) {
        id = buildingID;
        name = buildingName;
    }
    
    public int getID() {
        return id;
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
    public boolean equals(Object o) {
        if (o == null) {
            return false;
        }
        
        if (getClass() != o.getClass()) {
            return false;
        }
        
        final Building other = (Building)o;
        return name.equalsIgnoreCase(other.name);
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 23 * hash + Objects.hashCode(name);
        return hash;
    }
    
    @Override
    public boolean load(BufferedReader reader) throws IOException {
        id = Integer.valueOf(reader.readLine());
        name = reader.readLine();
        
        return true;
    }

    @Override
    public boolean save(BufferedWriter writer) throws IOException {
        writer.write(String.valueOf(id));
        writer.newLine();
        
        writer.write(name);
        writer.newLine();
        
        return true;
    }
    
    @Override
    public boolean hasBadKey() {
        return (name == null) || name.isEmpty();
    }
    
    private int id;
    private String name;
}
