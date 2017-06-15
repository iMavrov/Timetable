package university;

import java.io.IOException;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.util.Set;
import java.util.HashSet;
import java.util.Objects;
import utilities.SystemIDType;

/**
 *
 * @author Mavrov
 */
public class Room extends ScheduleHolder {
    
    public Room() {
        super(SystemIDType.ROOM_ID);
        
        building = null;
        name = "";
        type = RoomType.UNKNOWN;
        capacity = 0;
        attributes = new HashSet<>();
    }
    
    public Room(
            Building roomBuilding, 
            String roomName, 
            RoomType roomType, 
            int roomCapacity,
            Set<String> roomAttributes) {
        super(SystemIDType.ROOM_ID);
        
        building = roomBuilding;
        name = roomName;
        type = roomType;
        capacity = roomCapacity;
        attributes = roomAttributes;
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
        return false;
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
        return false;
    }
    
    @Override
    public boolean hasBadKey() {
        return (building == null) || name.isEmpty();
    }
    
    @Override
    public void onRoomPlacement(UniversityClass.PlaceEvent event) {
        UniversityClass universityClass = event.getUniversityClass();
        if (event.keepsRoom()) {
            schedule.update(universityClass);
        } else {
            schedule.add(universityClass);
        }
    }
    
    @Override
    public void onRoomDisplacement(UniversityClass.DisplaceEvent event) {
        UniversityClass universityClass = event.getUniversityClass();
        schedule.remove(universityClass);
    }
    
    public boolean addClass(UniversityClass universityClass, int startHour) {
        if (universityClass == null) {
            return false;
        }
        
        if (universityClass.hasBadKey()) {
            return false;
        }
        
        if (schedule.contains(universityClass)) {
            return false;
        }
        
        return universityClass.place(this, startHour);
    }
    
    @Override
    public boolean removeClass(UniversityClass universityClass) {
        if (universityClass == null) {
            return false;
        }
        
        if (universityClass.hasBadKey()) {
            return false;
        }
        
        if (!schedule.contains(universityClass)) {
            return false;
        }
        
        return universityClass.displace();
    }
       
    // Room info
    private Building building;
    private String name;
    
    private RoomType type;
    private int capacity;
}
