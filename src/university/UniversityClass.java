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
public class UniversityClass implements IPersistable, IKeyHolder, IAttributeHolder {
    
    public abstract class IClassEvent {
        public abstract void notifyObserver(IClassObserver classObserver);
        
        public UniversityClass getUniversityClass() {
            return universityClass;
        }
        
        protected UniversityClass universityClass;
        
        protected IClassEvent(UniversityClass inputClass) {
            universityClass = inputClass;
        }
    }
    
    public class AddLecturerEvent extends IClassEvent {
        // From IClassEvent
        @Override
        public void notifyObserver(IClassObserver classObserver) {
            if (classObserver != null) {
                classObserver.onAddedLecturer(this);
            }
        }
        
        public Lecturer getLecturer() {
            return lecturer;
        }
        
        private Lecturer lecturer;
        
        private AddLecturerEvent(UniversityClass inputClass, Lecturer inputLecturer) {
            super(inputClass);
            
            lecturer = inputLecturer;
        }
    }
    
    public class RemoveLecturerEvent extends IClassEvent {
        @Override
        public void notifyObserver(IClassObserver classObserver) {
            if (classObserver != null) {
                classObserver.onRemovedLecturer(this);
            }
        }
        
        public Lecturer getLecturer() {
            return lecturer;
        }
        
        private Lecturer lecturer;
        
        private RemoveLecturerEvent(UniversityClass inputClass, Lecturer inputLecturer) {
            super(inputClass);
            
            lecturer = inputLecturer;
        }
    }
    
    public class AddGroupEvent extends IClassEvent {
        @Override
        public void notifyObserver(IClassObserver classObserver) {
            if (classObserver != null) {
                classObserver.onAddedGroup(this);
            }
        }
        
        public Group getGroup() {
            return group;
        }
        
        private Group group;
        
        private AddGroupEvent(UniversityClass inputClass, Group inputGroup) {
            super(inputClass);
            
            group = inputGroup;
        }
    }
    
    public class RemoveGroupEvent extends IClassEvent {
        @Override
        public void notifyObserver(IClassObserver classObserver) {
            if (classObserver != null) {
                classObserver.onRemovedGroup(this);
            }
        }
        
        public Group getGroup() {
            return group;
        }
        
        private Group group;
        
        private RemoveGroupEvent(UniversityClass inputClass, Group inputGroup) {
            super(inputClass);
            
            group = inputGroup;
        }
    }
    
    public class PlaceEvent extends IClassEvent {
        @Override
        public void notifyObserver(IClassObserver classObserver) {
            if (classObserver != null) {
                classObserver.onRoomPlacement(this);
            }
        }
        
        public boolean keepsRoom() {
            return keepRoom;
        }
        
        public boolean keepsStartHour() {
            return keepStartHour;
        }

        /// Keeps the room. Just update the schedule. Cannot keep the start hour as well.
        private boolean keepRoom;
        
        /// Keeps the start hour. Just update the room. Cannot keep the room as well.
        private boolean keepStartHour;
        
        private PlaceEvent(UniversityClass inputClass, boolean keepsRoom, boolean keepsStartHour) {
            super(inputClass);
            
            keepRoom = keepsRoom;
            keepStartHour = keepsStartHour;
        }
    }
    
    public class DisplaceEvent extends IClassEvent {
        @Override
        public void notifyObserver(IClassObserver classObserver) {
            if (classObserver != null) {
                classObserver.onRoomDisplacement(this);
            }
        }
        
        private DisplaceEvent(UniversityClass inputClass) {
            super(inputClass);
        }
    }
    
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
    
    public int getCapacity() {
        return capacity;
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
        return (room != null) && (0 <= startHour) && (startHour + duration < Schedule.HOURS_PER_WEEK);
    }
    
    public Set<Group> getGroups() {
        return groups;
    }
    
    public Set<Lecturer> getLecturers() {
        return lecturers;
    }
    
    public int getIndex() {
        return index;
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

    public boolean addLecturer(Lecturer lecturer) {
        if (lecturer == null) {
            return false;
        }
        
        if (lecturer.hasBadKey()) {
            return false;
        }
        
        // Note: Set would not allow duplicates and return false.
        boolean isLecturerAdded = lecturers.add(lecturer);
        
        if (isLecturerAdded) {
            AddLecturerEvent addLecturerEvent = new AddLecturerEvent(this, lecturer);
            notifyObservers(addLecturerEvent);
        }
        
        return isLecturerAdded;
    }
    
    public boolean removeLecturer(Lecturer lecturer) {
        if (lecturer == null) {
            return false;
        }
        
        if (lecturer.hasBadKey()) {
            return false;
        }
        
        if (!lecturers.contains(lecturer)) {
            return false;
        }
        
        RemoveLecturerEvent removeLecturerEvent = new RemoveLecturerEvent(this, lecturer);
        notifyObservers(removeLecturerEvent);

        boolean isLecturerRemoved = lecturers.remove(lecturer);
        
        return isLecturerRemoved;
    }

    public boolean addGroup(Group group) {
        if (group == null) {
            return false;
        }
        
        if (group.hasBadKey()) {
            return false;
        }
        
        // Note: Set would not allow duplicates and return false.
        boolean isGroupAdded = groups.add(group);
        
        if (isGroupAdded) {
            capacity += group.getCapacity();
            
            AddGroupEvent addGroupEvent = new AddGroupEvent(this, group);
            notifyObservers(addGroupEvent);
        }
        
        return isGroupAdded;
    }
    
    public boolean removeGroup(Group group) {
        if (group == null) {
            return false;
        }
        
        if (group.hasBadKey()) {
            return false;
        }
        
        if (!groups.contains(group)) {
            return false;
        }

        RemoveGroupEvent removeGroupEvent = new RemoveGroupEvent(this, group);
        notifyObservers(removeGroupEvent);

        boolean isGroupRemoved = groups.remove(group);
        
        if (isGroupRemoved) {
            capacity -= group.getCapacity();
        }
        
        return isGroupRemoved;
    }

    public boolean place(Room newRoom, int newStartHour) {
        if (newRoom == null) {
            return false;
        }
        
        if (newRoom.hasBadKey()) {
            return false;
        }
        
        if (newStartHour < 0 || Schedule.HOURS_PER_WEEK <= newStartHour + duration) {
            return false;
        }
        
        boolean keepsRoom = newRoom.equals(room);
        boolean keepsStartHour = (newStartHour == startHour);
        
        if (isPlaced() && keepsRoom && keepsStartHour) {
            return false;
        }
        
        if (isPlaced() && !keepsRoom) {
            displace();
        }
        
        room = newRoom;        
        startHour = newStartHour;
        
        PlaceEvent placeEvent = new PlaceEvent(this, keepsRoom, keepsStartHour);
        notifyObservers(placeEvent);
        
        return true;
    }
    
    public boolean displace() {
        if (isPlaced()) {
            DisplaceEvent displaceEvent = new DisplaceEvent(this);
            notifyObservers(displaceEvent);
            
            room = null;
            startHour = -1;

            return true;
        } else {
            return false;
        }
    }
    
    public boolean removeAll() {
        HashSet<Lecturer> lecturersCopy = new HashSet<>(lecturers);
        for (Lecturer lecturer : lecturersCopy) {
            removeLecturer(lecturer);
        }
        
        HashSet<Group> groupsCopy = new HashSet<>(groups);
        for (Group group : groupsCopy) {
            removeGroup(group);
        }
        
        if (isPlaced()) {
            displace();
        }
        
        return true;
    }
    
    public boolean merge(UniversityClass other) {
        if (other == null) {
            return false;
        }
        
        if (getType() != other.getType()) {
            return false;
        }
        
        if (getDuration() != other.getDuration()) {
            return false;
        }
        
        capacity += other.getCapacity();
        
        for (String attribute : other.getAttributes()) {
            addAttribute(attribute);
        }
        
        boolean areGroupsTransferred = true;
        for (Group group : other.getGroups()) {
            boolean isGroupTransferred = addGroup(group);
            areGroupsTransferred = areGroupsTransferred && isGroupTransferred;
        }

        return areGroupsTransferred;
    }
    
    protected void notifyObservers(IClassEvent event) {       
        for (Lecturer lecturer : lecturers) {
            event.notifyObserver(lecturer);
        }
        
        for (Group group : groups) {
            event.notifyObserver(group);
        }
        
        if (isPlaced()) {
            event.notifyObserver(room);
        }
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
    
    // Placement index in the timetable
    private int index;
}