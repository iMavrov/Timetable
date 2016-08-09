package utilities;

/**
 * Unique, system-wide, typed identification of objects.
 * 
 * @author Mavrov
 */
public class SystemID {
    
    public SystemID(SystemIDType systemIDType) {
        type = systemIDType;
        id = System.currentTimeMillis();
    }
    
    private SystemIDType type;
    private long id;
}
