/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package university;

import java.io.IOException;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.util.Set;
import java.util.HashSet;
import java.util.Objects;

/**
 *
 * @author Mavrov
 */
public class Room implements IPersistable, IKeyHolder {
    
    public Room() {
        buildingID = University.INVALID_ID;
        name = "";
        type = RoomType.LECTURE_HALL;
        capacity = 0;
        attributes = new HashSet<>();
        schedule = new Schedule();
    }
    
    public Room(
            int roomBuildingID, 
            String roomName, 
            RoomType roomType, 
            int roomCapacity,
            Set<String> roomAttributes) {
        buildingID = roomBuildingID;
        name = roomName;
        type = roomType;
        capacity = roomCapacity;
        attributes = roomAttributes;
        schedule = new Schedule();
    }
    
    public int getBuildingID() {
        return buildingID;
    }

    public void setBuildingID(int newBuildingID) {
        buildingID = newBuildingID;
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
    
    public boolean hasAttribute(String attribute) {
        return attributes.contains(attribute);
    }

    public boolean addAttribute(String attribute) {
        return attributes.add(attribute);
    }

    public boolean removeAttribute(String attribute) {
        return attributes.remove(attribute);
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
        return (buildingID == other.buildingID) && name.equalsIgnoreCase(other.name);
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 83 * hash + this.buildingID;
        hash = 83 * hash + Objects.hashCode(this.name);
        return hash;
    }

    @Override
    public String toString() {
        Building building = University.getInstance().getBuilding(buildingID);
        return building.getName() + ": " + name;
    }
    
    @Override
    public boolean save(BufferedWriter writer) throws IOException {
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
        
        return true;
    }
    
    @Override
    public boolean load(BufferedReader reader) throws IOException {
        buildingID = Integer.valueOf(reader.readLine());
        name = reader.readLine();
        type = RoomType.valueOf(reader.readLine());
        capacity = Integer.valueOf(reader.readLine());
        
        int attributeCount = Integer.valueOf(reader.readLine());
        while (attributeCount > 0) {
            attributes.add(reader.readLine());
            --attributeCount;
        }
        
        return true;
    }
    
    @Override
    public boolean hasBadKey() {
        return (buildingID == University.INVALID_ID) || name.isEmpty();
    }
    
    public boolean assignClass(UniversityClass universityClass, AssignPolicy policy) {
        if (universityClass == null) {
            return false;
        }
        
        // TODO: Place in schedule
        boolean isPlacedInSchedule = true;
        
        if (policy == AssignPolicy.BOTH_WAYS) {
            universityClass.setRoom(this);
            // TODO: Set time as well ?
        }
        
        boolean isClassAdded = classes.add(universityClass);
        
        return isPlacedInSchedule && isClassAdded;
    }
    
    public boolean unassignClass(UniversityClass universityClass, AssignPolicy policy) {
        if (universityClass == null) {
            return false;
        }
        
        // TODO: Remove from schedule
        boolean isRemovedFromSchedule = true;
        
        if (policy == AssignPolicy.BOTH_WAYS) {
            universityClass.setRoom(null);
            // TODO: Set time as well ?
        }
        
        boolean isClassRemoved = classes.remove(universityClass);
        
        return isRemovedFromSchedule && isClassRemoved;
    }
    
    public boolean unassignAllClasses() {
        boolean areClassesUnassigned = true;
        
        for (UniversityClass universityClass : classes) {
            boolean isClassUnassigned = unassignClass(universityClass, AssignPolicy.BOTH_WAYS);
            areClassesUnassigned = areClassesUnassigned && isClassUnassigned;
        }
        
        return areClassesUnassigned;
    }
       
    // Room info
    private int buildingID;
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
