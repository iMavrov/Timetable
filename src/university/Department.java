package university;

import java.io.IOException;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.util.Objects;

/**
 *
 * @author Mavrov
 */
public class Department implements IPersistable, IKeyHolder {
    
    public Department() {
        name = "";
    }
    
    public Department(String departmentName) {
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
    
    @Override
    public boolean equals(Object o) {
        if (o == null) {
            return false;
        }
        
        if (getClass() != o.getClass()) {
            return false;
        }
        
        final Department other = (Department)o;
        return name.equalsIgnoreCase(other.name);
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 43 * hash + Objects.hashCode(this.name);
        return hash;
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
