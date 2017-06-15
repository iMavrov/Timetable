package utilities;

import java.io.IOException;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.util.Map;
import java.util.HashMap;
import java.util.Objects;
import university.IPersistable;

/**
 * Unique, system-wide, typed identification of objects.
 * 
 * @author Mavrov
 */
public class SystemID implements IPersistable {
    
    public static SystemID getNewID(SystemIDType type) {
        if (type == SystemIDType.UNRESOLVED_ID) {
            return new SystemID();
        }
        
        if (nextIndex == null) {
            initializeNextIndexMap();
        }
        
        int newIndex = nextIndex.get(type);
        nextIndex.put(type, newIndex + 1);
        
        return new SystemID(type, newIndex);
    }
    
    public SystemID() {
        type = SystemIDType.UNRESOLVED_ID;
        index = 0;
    }
    
    public SystemIDType getType() {
        return type;
    }
    
    public int getIndex() {
        return index;
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
        
        final SystemID other = (SystemID)o;
        return type == other.type && index == other.index;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + Objects.hashCode(type);
        hash = 97 * hash + index;
        return hash;
    }
    
    // From IPersistable
    @Override
    public boolean load(BufferedReader reader) throws IOException {
        // TODO: Read in type and index
        return false;
    }

    @Override
    public boolean save(BufferedWriter writer) throws IOException {
        // TODO: Write type and index
        return false;
    }
    
    // TODO: Maybe store this map somewhere?
    // UPDATE: Nah - here is just fine.
    // TODO: Maybe serialize this map? 
    // UPDATE: Do not serialize - recreate while loading serialized objects in the same order
    private static Map<SystemIDType, Integer> nextIndex = null;
    
    private SystemIDType type;
    private int index;
    
    private static void initializeNextIndexMap() {
        nextIndex = new HashMap<>();
        for (SystemIDType type : SystemIDType.values()) {
            nextIndex.put(type, 0);
        }
    }
    
    private SystemID(SystemIDType systemIDType, int systemIDIndex) {
        type = systemIDType;
        index = systemIDIndex;
    }
}
