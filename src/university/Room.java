package university;

import java.io.IOException;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.util.Set;
import java.util.HashSet;
import java.util.Objects;
import utilities.AssignPolicy;
import utilities.IObservable;
import utilities.UpdateReason;

/**
 *
 * @author Mavrov
 */
public class Room implements IPersistable, IKeyHolder, IAttributeHolder, IClassObserver, IObservable {
    
    public Room() {
        building = null;
        name = "";
        type = RoomType.UNKNOWN;
        capacity = 0;
        attributes = new HashSet<>();
        schedule = new Schedule();
        classes = new HashSet<>();
    }
    
    public Room(
            Building roomBuilding, 
            String roomName, 
            RoomType roomType, 
            int roomCapacity,
            Set<String> roomAttributes) {
        building = roomBuilding;
        name = roomName;
        type = roomType;
        capacity = roomCapacity;
        attributes = roomAttributes;
        schedule = new Schedule();
        classes = new HashSet<>();
    }
    
    public Building getBuilding() {
        return building;
    }

    public void setBuilding(Building newBuilding) {
        building = newBuilding;
    }

    public String getName() {
        return name;
    }

    public void setName(String newName) {
        name = newName;
    }

    public RoomType getType() {
        return type;
    }

    public void setType(RoomType newType) {
        type = newType;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int newCapacity) {
        capacity = newCapacity;
    }
    
    @Override
    public boolean equals(Object o) {
        if (o == null) {
            return false;
        }
        
        if (getClass() != o.getClass()) {
            return false;
        }
        
        final Room other = (Room)o;
        return (building == other.building) && name.equals(other.name);
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 83 * hash + building.hashCode();
        hash = 83 * hash + Objects.hashCode(name);
        return hash;
    }

    @Override
    public String toString() {
        return building.getName() + ": " + name;
    }
    
    @Override
    public boolean save(BufferedWriter writer) throws IOException {
        /*
        writer.write(String.valueOf(buildingID));
        writer.newLine();
        writer.write(name);
        writer.newLine();
        writer.write(type.toString());
        writer.newLine();
        writer.write(String.valueOf(capacity));
        writer.newLine();
        
        writer.write(String.valueOf(attributes.size()));
        writer.newLine();
        for (String attribute: attributes) {
            writer.write(attribute);
            writer.newLine();
        }
        */
        return true;
    }
    
    @Override
    public boolean load(BufferedReader reader) throws IOException {
        /*
        buildingID = Integer.valueOf(reader.readLine());
        name = reader.readLine();
        type = RoomType.valueOf(reader.readLine());
        capacity = Integer.valueOf(reader.readLine());
        
        int attributeCount = Integer.valueOf(reader.readLine());
        while (attributeCount > 0) {
            attributes.add(reader.readLine());
            --attributeCount;
        }
        */
        return true;
    }
    
    @Override
    public boolean hasBadKey() {
        return (building == null) || name.isEmpty();
    }
    
    @Override
    public boolean hasAttribute(String attribute) {
        return attributes.contains(attribute);
    }

    @Override
    public boolean addAttribute(String attribute) {
        return attributes.add(attribute);
    }

    @Override
    public boolean removeAttribute(String attribute) {
        return attributes.remove(attribute);
    }
    
    @Override
    public boolean assign(UniversityClass universityClass, AssignPolicy policy) {
        if (universityClass == null) {
            return false;
        }
        
        /*
        if (universityClass.hasBadKey()) {
            return false;
        }
        */
        
        if (classes.contains(universityClass)) {
            return false;
        }

        // We are ok with the class so far. See if it is ok with us.
        boolean isRoomAssigned = true;
        if (policy == AssignPolicy.BOTH_WAYS) {
            isRoomAssigned = universityClass.assign(this, AssignPolicy.ONE_WAY);
        }
        
        boolean isClassAssigned = true;
        if (isRoomAssigned) {
            isClassAssigned = classes.add(universityClass);
            
            // TODO: Place in schedule
            boolean isPlacedInSchedule = true;
        }
        
        return isClassAssigned && isRoomAssigned;
    }
    
    @Override
    public boolean unassign(UniversityClass universityClass, AssignPolicy policy) {
        if (universityClass == null) {
            return false;
        }
        
        /*
        if (universityClass.hasBadKey()) {
            return false;
        }
        */
        
        if (!classes.contains(universityClass)) {
            return false;
        }
        
        // We are ok to remove the class so far. See if it is ok with us.    
        boolean isRoomUnassigned = true;
        if (policy == AssignPolicy.BOTH_WAYS) {
            isRoomUnassigned = universityClass.unassign(this, AssignPolicy.ONE_WAY);
        }
        
        boolean isClassUnassigned = true;
        if (isRoomUnassigned) {
            isClassUnassigned = classes.remove(universityClass);
            
            // TODO: Remove from schedule
            boolean isRemovedFromSchedule = true;
        }
        
        return isClassUnassigned && isRoomUnassigned;
    }
    
    @Override
    public boolean update(UniversityClass universityClass, UpdateReason reason) {
        // TODO:        
        return true;
    }
    
    @Override
    public boolean unassignAllClasses() {
        boolean areClassesUnassigned = true;
        
        for (UniversityClass universityClass : classes) {
            boolean isClassUnassigned = unassign(universityClass, AssignPolicy.ONE_WAY);
            areClassesUnassigned = areClassesUnassigned && isClassUnassigned;
        }
        
        classes.clear();
        
        // TODO:
        //schedule.clear();
        
        return areClassesUnassigned;
    }
       
    // Room info
    private Building building;
    private String name;
    
    private RoomType type;
    private int capacity;
    
    // Extended room info
    Set<String> attributes;
    
    // Room schedule
    Schedule schedule;
    
    // Classes assigned in this room
    Set<UniversityClass> classes;
}
