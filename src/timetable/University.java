package timetable;

import java.util.List;

import university.Room;
import university.Group;
import university.Lecturer;

/**
 *
 * @author Mavrov
 * 
 * Stub for university persistent data
 */
public abstract class University {
    
    public static University getInstance() {
        return instance;
    }
    
    public abstract int getRoomCount();
    
    public abstract List<Room> getRooms();
    
    public abstract List<Lecturer> getLecturers();
    
    public abstract List<Group> getGroups();
    
    private static University instance = null;
}
