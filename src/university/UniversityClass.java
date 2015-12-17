package university;

import java.io.IOException;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.util.Set;
import java.util.HashSet;
import utilities.IObservable;
import utilities.AssignPolicy;
import utilities.UpdateReason;

/**
 *
 * @author Mavrov
 */
public class UniversityClass implements IPersistable, IKeyHolder, IAttributeHolder, ILecturerObserver, IGroupObserver, IRoomObserver, IObservable {
    
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
            String subjectName, int classDuration) {
        subject = classSubject;
        type = classType;
        name = subjectName;
        duration = classDuration;
        capacity = 0;
        attributes = new HashSet<>();
        room = null;
        startHour = -1;
        groups = new HashSet<>();
        lecturers = new HashSet<>();
        
        switch(type) {
            case LECTION: {
                name = name + LECTION_SUFFIX;
                break;
            }
            case LABORATORY: {
                name = name + LAB_SUFFIX;
                break;
            }
            case SEMINAR: {
                name = name + SEMINAR_SUFFIX;
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
    
    public void setDuration(int newDuration) {
        duration = newDuration;
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
    
    public int getStartHour() {
        return startHour;
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
    public boolean hasBadKey() {
        // TODO:
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
    
    @Override
    public boolean assign(Lecturer lecturer, AssignPolicy policy) {
        if (lecturer == null) {
            return false;
        }
        
        if (lecturer.hasBadKey()) {
            return false;
        }
        
        if (lecturers.contains(lecturer)) {
            return false;
        }
        
        // We are ok with the lecturer so far. See if he is ok with us.
        boolean isClassAssigned = true;
        if (policy == AssignPolicy.BOTH_WAYS) {
            isClassAssigned = lecturer.assign(this, AssignPolicy.ONE_WAY);
        }
        
        boolean isLecturerAssigned = true;
        if (isClassAssigned) {
            isLecturerAssigned = lecturers.add(lecturer);
        }
        
        return isLecturerAssigned && isClassAssigned;
    }
    
    @Override
    public boolean unassign(Lecturer lecturer, AssignPolicy policy) {
        if (lecturer == null) {
            return false;
        }
        
        if (lecturer.hasBadKey()) {
            return false;
        }
        
        if (!lecturers.contains(lecturer)) {
            return false;
        }
        
        // We are ok to remove the lecturer so far. See if he is ok with us.
        boolean isClassUnassigned = true;
        if (policy == AssignPolicy.BOTH_WAYS) {
            isClassUnassigned = lecturer.unassign(this, AssignPolicy.ONE_WAY);
        }
        
        boolean isLecturerUnassigned = true;
        if (isClassUnassigned) {
            isLecturerUnassigned = lecturers.remove(lecturer);
        }
        
        return isLecturerUnassigned && isClassUnassigned;
    }
    
    @Override
    public boolean update(Lecturer lecturer, UpdateReason reason) {
        // TODO:
        return true;
    }
    
    @Override
    public boolean unassignAllLecturers() {
        boolean areLecturersUnassigned = true;
        
        for (Lecturer lecturer : lecturers) {
            boolean isLecturerUnassigned = unassign(lecturer, AssignPolicy.ONE_WAY);
            areLecturersUnassigned = areLecturersUnassigned && isLecturerUnassigned;
        }
        
        lecturers.clear();
        
        return areLecturersUnassigned;
    }
    
    //@Override
    public boolean assign(Group group, AssignPolicy policy) {
        if (group == null) {
            return false;
        }
        
        if (group.hasBadKey()) {
            return false;
        }
        
        if (groups.contains(group)) {
            return false;
        }
        
        // We are ok with the group so far. See if it is ok with us.
        boolean isClassAssigned = true;
        if (policy == AssignPolicy.BOTH_WAYS) {
            isClassAssigned = group.assign(this, AssignPolicy.ONE_WAY);
        }
        
        boolean isGroupAssigned = true;
        if (isClassAssigned) {
            isGroupAssigned = groups.add(group);
            if (isGroupAssigned) {
                int newCapacity = getCapacity() + group.getCapacity();
                setCapacity(newCapacity);
            }
        }
        
        return isGroupAssigned && isClassAssigned;
    }
    
    //@Override
    public boolean unassign(Group group, AssignPolicy policy) {
        if (group == null) {
            return false;
        }
        
        if (group.hasBadKey()) {
            return false;
        }
        
        if (!groups.contains(group)) {
            return false;
        }
        
        // We are ok to remove the group so far. See if it is ok with us.
        boolean isClassUnassigned = true;
        if (policy == AssignPolicy.BOTH_WAYS) {
            isClassUnassigned = group.unassign(this, AssignPolicy.ONE_WAY);
        }
        
        boolean isGroupUnassigned = true;
        if (isClassUnassigned) {
            isGroupUnassigned = groups.remove(group);
            if (isGroupUnassigned) {
                int newCapacity = getCapacity() - group.getCapacity();
                setCapacity(newCapacity);
            }
        }
        
        return isGroupUnassigned && isClassUnassigned;
    }
    
    //@Override
    public boolean update(Group group, UpdateReason reason) {
        // TODO:
        return true;
    }
    
    @Override
    public boolean unassignAllGroups() {
        boolean areGroupsUnassigned = true;
        
        for (Group group : groups) {
            boolean isGroupUnassigned = unassign(group, AssignPolicy.ONE_WAY);
            areGroupsUnassigned = areGroupsUnassigned && isGroupUnassigned;
        }
        
        groups.clear();
        setCapacity(0);
        
        return areGroupsUnassigned;
    }
    
    //@Override
    public boolean assign(Room newRoom, AssignPolicy policy) {
        if (newRoom == null) {
            return false;
        }
        
        if (newRoom.hasBadKey()) {
            return false;
        }
        
        if (room != null && room.equals(newRoom)) {
            return false;
        }
        
        // We are ok with the room so far. See if it is ok with us.
        boolean isClassAssigned = true;
        if (policy == AssignPolicy.BOTH_WAYS) {
            isClassAssigned = newRoom.assign(this, AssignPolicy.ONE_WAY);
        }
        
        boolean isRoomAssigned = true;
        if (isClassAssigned) {
            boolean isOldRoomUnassigned = unassign(room, AssignPolicy.BOTH_WAYS);
            
            boolean isNewRoomSet = setRoom(newRoom);
            // TODO: Set new time placement and notify ???
            
            isRoomAssigned = isOldRoomUnassigned && isNewRoomSet;
        }
        
        return isRoomAssigned && isClassAssigned;
    }
    
    //@Override
    // TODO: We do not need this argument
    public boolean unassign(Room oldRoom, AssignPolicy policy) {
        if (room == null) {
            return false;
        }
        
        if (room.hasBadKey()) {
            return false;
        }
        
        if (!room.equals(oldRoom)) {
            return false;
        }
        
        // We are ok to remove the room so far. See if it is ok with us.
        boolean isClassUnassigned = true;
        if (policy == AssignPolicy.BOTH_WAYS) {
            isClassUnassigned = room.unassign(this, AssignPolicy.ONE_WAY);
        }
        
        boolean isRoomUnassigned = true;
        if (isClassUnassigned) {
            isRoomUnassigned = setRoom(null);
            // TODO: Reset time placement and notify ???
        }
        
        return isRoomUnassigned && isClassUnassigned;
    }
    
    //@Override
    public boolean update(Room room, UpdateReason reason) {
        // TODO:
        return true;
    }
    
    @Override
    public boolean unassignAllRooms() {
        boolean isRoomUnassigned = unassign(room, AssignPolicy.BOTH_WAYS);
        return isRoomUnassigned;
    }
    
    public boolean unassignAll() {
        boolean areAllLecturersUnassigned = unassignAllLecturers();
        boolean areAllGroupsUnassigned = unassignAllGroups();
        boolean areAllRoomsUnassigned = unassignAllRooms();
        
        return areAllLecturersUnassigned && areAllGroupsUnassigned && areAllRoomsUnassigned;
    }
    
    private static final String LECTION_SUFFIX = ": Лекция";
    private static final String SEMINAR_SUFFIX = ": Упражнение";
    private static final String LAB_SUFFIX = ": Практикум";
    
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
    
    private boolean setRoom(Room newRoom) {
        room = newRoom;
        
        boolean areEveryoneOK = true;
        for (Lecturer lecturer : lecturers) {
            boolean isLecturerOK = lecturer.update(this, UpdateReason.CHANGE_ROOM);
            areEveryoneOK = areEveryoneOK && isLecturerOK;
        }

        for (Group group : groups) {
            boolean isGroupOK = group.update(this, UpdateReason.CHANGE_ROOM);
            areEveryoneOK = areEveryoneOK && isGroupOK;
        }
        
        return areEveryoneOK;
    }
}