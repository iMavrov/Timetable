package utilities;

import java.io.IOException;
import java.io.BufferedReader;
import java.io.BufferedWriter;

/**
 *
 * @author Mavrov
 */
public class SystemObject {
    
    public SystemObject(SystemIDType systemObjectType) {
        id = SystemID.getNewID(systemObjectType);
    }
    
    public SystemID getID() {
        return id;
    }
    
    // From Object
    @Override
    public boolean equals(Object o) {
        if (o == null) {
            return false;
        }
        
        if (getClass() != o.getClass()) {
            return false;
        }
        
        final SystemObject other = (SystemObject)o;
        return id.equals(other.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
    
    protected SystemID id;
}
