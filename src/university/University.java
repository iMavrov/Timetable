/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package university;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.util.Set;
import java.util.HashSet;

/**
 *
 * @author Mavrov
 */
public class University implements IPersistable {
    
    public static final int INVALID_ID = -1;
    
    public static University getInstance() {
        if (university == null) {
            initializeUniversity();
        }
        
        return university;
    }
    
    public boolean save() {
        boolean isSaveSuccessful = false;
        
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(DATABASE_FILEPATH))) {
            isSaveSuccessful = university.save(writer);
        } catch (FileNotFoundException e) {
            // TODO: Do something about this error!
        } catch (IOException e) {
            // TODO: Do something about this error!
        }
        
        return isSaveSuccessful;
    }
    
    @Override
    public boolean save(BufferedWriter writer) throws IOException {
        writer.write(String.valueOf(DATABASE_VERSION));
        writer.newLine();
        
        writer.write(String.valueOf(buildings.size()));
        writer.newLine();
        for (String building : buildings) {
            writer.write(building);
            writer.newLine();
        }
        
        writer.write(String.valueOf(rooms.size()));
        writer.newLine();
        for (Room room : rooms) {
            room.save(writer);
        }
        
        writer.write(String.valueOf(faculties.size()));
        writer.newLine();
        for (String building : faculties) {
            writer.write(building);
            writer.newLine();
        }
        
        writer.write(String.valueOf(departments.size()));
        writer.newLine();
        for (String building : departments) {
            writer.write(building);
            writer.newLine();
        }
        
        writer.write(String.valueOf(lecturers.size()));
        writer.newLine();
        for (Lecturer lecturer : lecturers) {
            lecturer.save(writer);
        }

        writer.write(String.valueOf(programs.size()));
        writer.newLine();
        for (Program program : programs) {
            program.save(writer);
        }
        
        return true;
    }
    
    @Override
    public boolean load(BufferedReader reader) throws IOException {
        float fileVersion = Float.valueOf(reader.readLine());
        // TODO: Check version match
        
        int buildingCount = Integer.valueOf(reader.readLine());
        while (buildingCount > 0) {
            buildings.add(reader.readLine());
            --buildingCount;
        }

        int roomCount = Integer.valueOf(reader.readLine());
        while (roomCount > 0) {
            Room newRoom = new Room();
            newRoom.load(reader);
            rooms.add(newRoom);
            --roomCount;
        }
        
        int facultyCount = Integer.valueOf(reader.readLine());
        while (facultyCount > 0) {
            faculties.add(reader.readLine());
            --facultyCount;
        }
        
        int departmentCount = Integer.valueOf(reader.readLine());
        while (departmentCount > 0) {
            departments.add(reader.readLine());
            --departmentCount;
        }
        
        int lecturerCount = Integer.valueOf(reader.readLine());
        while (lecturerCount > 0) {
            Lecturer newLecturer = new Lecturer();
            newLecturer.load(reader);
            lecturers.add(newLecturer);
            --lecturerCount;
        }
        
        int programCount = Integer.valueOf(reader.readLine());
        while (programCount > 0) {
            Program newProgram = new Program();
            newProgram.load(reader);
            programs.add(newProgram);
            --programCount;
        }

        return true;
    }
/*
    public List<Class> getSubjects() {
        return subjects;
    }
    
    public int getSubjectsCount() {
        return getInstance().subjects.size();
    }
    
    public List<Room> getRooms() {
        return rooms;
    }
    
    public List<Room> getRooms(Faculty facultyType, RoomType roomType) {
        List<Room> filteredRooms = new ArrayList<>();
        for (Room room : rooms) {
            if (room.getFaculty() == facultyType && room.getType() == roomType) {
                filteredRooms.add(room);
            }
        }
        
        return filteredRooms;
    }
    
    public int getRoomCount() {
        return getInstance().rooms.size();
    }
    
    public void addNewRoom(Room newRoom) throws IllegalArgumentException {
        if (rooms.contains(newRoom)) {
            throw new IllegalArgumentException("Зала/стая със същото име вече съществува в тази сграда!");
        }
        
        // TODO: use a variable to determine the next unique number
        newRoom.setRoomID(rooms.size());
        rooms.add(newRoom);
        
        save();
    }
    
    public void updateRoom(Room newRoom) throws IllegalArgumentException {
        int oldRoomIndex = -1;
        
        for (int roomIndex = 0; roomIndex < rooms.size(); ++roomIndex) {
            if (rooms.get(roomIndex).equals(newRoom) && 
                rooms.get(roomIndex).getRoomID() != newRoom.getRoomID()) {
                throw new IllegalArgumentException("Зала/стая със същото име вече съществува в тази сграда!");
            }
            
            if (rooms.get(roomIndex).getRoomID() == newRoom.getRoomID()) {
                oldRoomIndex = roomIndex;
            }
        }
        
        rooms.set(oldRoomIndex, newRoom);
        
        save();
    }
    
    public List<Department> getDepartments() {
        return departments;
    }
    
    public List<Specialty> getSpecialties() {
        return specialties;
    }
    
    public void addNewCourseStructure(CourseStructure newCourseStructure) throws IllegalArgumentException {
        
    }
    
    public void updateCourseStructure(CourseStructure newCourseStructure) throws IllegalArgumentException {
        
    }
    */
    
    @Override
    protected Object clone() {
        return university;
    }
    
    private static final String DATABASE_FILEPATH = "university.txt";
    private static final float DATABASE_VERSION = 1.0f;
    
    private static University university = null;
    
    // Room view
    private Set<String> buildings;
    private Set<Room> rooms;
    
    // Lecturer view
    private Set<String> faculties;
    private Set<String> departments;
    private Set<Lecturer> lecturers;
    
    // Program view
    private Set<Program> programs;
    
    // Student view ???
    
    private static void initializeUniversity() {
        university = new University();
        
        try (BufferedReader reader = new BufferedReader(new FileReader(DATABASE_FILEPATH))) {
            university.load(reader);
        } catch (FileNotFoundException e) {
            // TODO: Do something about this error!
        } catch (IOException e) {
            // TODO: Do something about this error!
        }
    }
    
    private University() {
        // Room view
        buildings = new HashSet<>();
        rooms = new HashSet<>();
    
        // Lecturer view
        faculties = new HashSet<>();
        departments = new HashSet<>();
        lecturers = new HashSet<>();
    
        // Student view
        programs = new HashSet<>();
    }
}
