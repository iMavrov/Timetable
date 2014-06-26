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

/**
 *
 * @author Mavrov
 */
public class Room implements IPersistable {
    
    public Room() {
        id = University.INVALID_ID;
        attributes = new HashSet<>();
    }
    
    public Room(
            int roomID,
            String buildingName, 
            String roomName, 
            RoomType roomType, 
            int roomCapacity,
            Set<String> roomAttributes) {
        id = roomID;
        building = buildingName;
        name = roomName;
        type = roomType;
        capacity = roomCapacity;
        attributes = roomAttributes;
    }
    
    public String getBuilding() {
        return building;
    }

    public void setBuilding(String buildingName) {
        building = buildingName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public RoomType getType() {
        return type;
    }

    public void setType(RoomType type) {
        this.type = type;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
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
        return id == other.id;
    }

    @Override
    public int hashCode() {
        return id;
    }

    @Override
    public String toString() {
        return building + ": " + name;
    }
    
    @Override
    public boolean save(BufferedWriter writer) throws IOException {
        writer.write(String.valueOf(id));
        writer.newLine();
        
        writer.write(building);
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
        id = Integer.valueOf(reader.readLine());
        
        building = reader.readLine();
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
    
    // Key
    private int id;
    
    // Room info
    private String building;
    private String name;
    
    private RoomType type;
    private int capacity;
    
    // Extended room info
    Set<String> attributes;
}
