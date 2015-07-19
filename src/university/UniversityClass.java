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
public class UniversityClass implements IPersistable, IAttributeHolder {
    
    public UniversityClass() {
        subject = null;
        type = UniversityClassType.UNKNOWN;
        name = "";
        duration = 0;
        capacity = 0;
        attributes = new HashSet<>();
        room = null;
        startHour = -1;
        groups = new HashSet<>();
        lecturers = new HashSet<>();
    }
    
    public UniversityClass(
            Subject classSubject, UniversityClassType classType,
            String subjectName, int classDuration, int classCapacity) {
        subject = classSubject;
        type = classType;
        name = subjectName;
        duration = classDuration;
        capacity = classCapacity;
        attributes = new HashSet<>();
        room = null;
        startHour = -1;
        groups = new HashSet<>();
        lecturers = new HashSet<>();
        
        switch(type) {
            case LECTION: {
                name = name + ": Lection";
                break;
            }
            case LABORATORY: {
                name = name + ": Lab";
                break;
            }
            case SEMINAR: {
                name = name + ": Seminar";
                break;
            }
        }
    }
    
    public Subject getSubject() {
        return subject;
    }
    
    public UniversityClassType getType() {
        return type;
    }
    
    public String getName() {
        return name;
    }
    
    public int getDuration() {
        return duration;
    }
    
    public int getCapacity() {
        return capacity;
    }
    
    public void setCapacity(int newCapacity) {
        capacity = newCapacity;
    }
    
    public Set<String> getAttributes() {
        return attributes;
    }
    
    public Room getRoom() {
        return room;
    }
    
    public void setRoom(Room classRoom) {
        room = classRoom;
    }
    
    public int getStartHour() {
        return startHour;
    }
    
    public void setStartHour(int newStartHour) {
        startHour = newStartHour;
    }
    
    public boolean isPlaced() {
        return (room != null) && (0 <= startHour) && (startHour < Schedule.HOURS_PER_WEEK);
    }
    
    public Set<Group> getGroups() {
        return groups;
    }
    
    public Set<Lecturer> getLecturers() {
        return lecturers;
    }
    
    @Override
    public boolean load(BufferedReader reader) throws IOException {
        // TODO: Serialize subject id

        type = UniversityClassType.valueOf(reader.readLine());
        
        name = reader.readLine();
    
        duration = Integer.valueOf(reader.readLine());

        capacity = Integer.valueOf(reader.readLine());
    
        int attributeCount = Integer.valueOf(reader.readLine());
        while (0 < attributeCount) {
            String newAttribute = reader.readLine();
            
            attributes.add(newAttribute);
            
            --attributeCount;
        }
        
        return true;
    }

    @Override
    public boolean save(BufferedWriter writer) throws IOException {
        // TODO: Serialize subject id

        writer.write(type.toString());
        writer.newLine();
        
        writer.write(name);
        writer.newLine();
        
        writer.write(String.valueOf(duration));
        writer.newLine();
        
        writer.write(String.valueOf(capacity));
        writer.newLine();
        
        writer.write(String.valueOf(attributes.size()));
        writer.newLine();
        
        for (String attribute : attributes) {
            writer.write(attribute);
            writer.newLine();
        }
        
        return true;
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
    
    public boolean assignLecturer(Lecturer lecturer, AssignPolicy policy) {
        if (lecturer == null) {
            return false;
        }
        
        boolean isClassAssigned = true;
        if (policy == AssignPolicy.BOTH_WAYS) {
            isClassAssigned = lecturer.assignClass(this, AssignPolicy.ONE_WAY);
        }
        
        boolean isLecturerAdded = lecturers.add(lecturer);

        return isClassAssigned && isLecturerAdded;
    }
    
    public boolean unassignLecturer(Lecturer lecturer, AssignPolicy policy) {
        if (lecturer == null) {
            return false;
        }
        
        boolean isClassUnassigned = true;
        if (policy == AssignPolicy.BOTH_WAYS) {
            isClassUnassigned = lecturer.unassignClass(this, AssignPolicy.ONE_WAY);
        }
        
        boolean isLecturerRemoved = lecturers.remove(lecturer);
        
        return isClassUnassigned && isLecturerRemoved;
    }
    
    public boolean assignGroup(Group group, AssignPolicy policy) {
        if (group == null) {
            return false;
        }
        
        boolean isClassAssigned = true;
        if (policy == AssignPolicy.BOTH_WAYS) {
            isClassAssigned = group.assignClass(this, AssignPolicy.ONE_WAY);
        }
        
        boolean isGroupAdded = groups.add(group);

        return isClassAssigned && isGroupAdded;
    }
    
    public boolean unassignGroup(Group group, AssignPolicy policy) {
        if (group == null) {
            return false;
        }
        
        boolean isClassUnassigned = true;
        if (policy == AssignPolicy.BOTH_WAYS) {
            isClassUnassigned = group.unassignClass(this, AssignPolicy.ONE_WAY);
        }
        
        boolean isGroupRemoved = groups.remove(group);
        
        return isClassUnassigned && isGroupRemoved;
    }
    
    public boolean assignRoom(Room newRoom, AssignPolicy policy) {
        if (newRoom == null) {
            return false;
        }
        
        // Unassign from the old room
        boolean isUnassignedFromOldRoom = true;
        if (room != null) {
            isUnassignedFromOldRoom = room.unassignClass(this, AssignPolicy.ONE_WAY);
        }
        
        // Assign to the new room
        boolean isClassAssigned = true;
        if (policy == AssignPolicy.BOTH_WAYS) {
            isClassAssigned = newRoom.assignClass(this, AssignPolicy.ONE_WAY);
        }
        
        room = newRoom;

        return isUnassignedFromOldRoom && isClassAssigned;
    }
    
    public boolean unassignRoom(AssignPolicy policy) {      
        boolean isClassUnassigned = true;
        if ((room != null) && (policy == AssignPolicy.BOTH_WAYS)) {
            isClassUnassigned = room.unassignClass(this, AssignPolicy.ONE_WAY);
        }
        
        room = null;
        
        return isClassUnassigned;
    }
    
    public boolean unassignAllLecturers() {
        boolean areLecturersUnassigned = true;
        
        for (Lecturer lecturer : lecturers) {
            boolean isLecturerUnassigned = unassignLecturer(lecturer, AssignPolicy.BOTH_WAYS);
            areLecturersUnassigned = areLecturersUnassigned && isLecturerUnassigned;
        }
        
        return areLecturersUnassigned;
    }
    
    public boolean unassignAllGroups() {
        boolean areGroupsUnassigned = true;
        
        for (Group group : groups) {
            boolean isGroupUnassigned = unassignGroup(group, AssignPolicy.BOTH_WAYS);
            areGroupsUnassigned = areGroupsUnassigned && isGroupUnassigned;
        }
        
        return areGroupsUnassigned;
    }
    
    public boolean unassignAll() {
        boolean areAllLecturersUnassigned = unassignAllLecturers();
        boolean areAllGroupsUnassigned = unassignAllGroups();
        boolean isRoomUnassigned = unassignRoom(AssignPolicy.BOTH_WAYS);
        
        return areAllLecturersUnassigned && areAllGroupsUnassigned && isRoomUnassigned;
    }
    
    // Parent subject
    private Subject subject;
    
    // Which type of the subject's classes is this one
    private UniversityClassType type;
    
    // The generated name for the class
    private String name;
    
    // The class'es duration
    private int duration;
    
    // Sum of all group capacities this class is read to
    private int capacity;
    
    // Requirements info
    private Set<String> attributes;
    
    // Where is the class tought
    private Room room;
    
    // When is the class tought (hour of the week)
    private int startHour;
    
    // To whom the class is taught
    private Set<Group> groups;
    
    // Who teaches the class
    private Set<Lecturer> lecturers;
}