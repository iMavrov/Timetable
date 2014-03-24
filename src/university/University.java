/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package university;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Mavrov
 */
public class University {
    
    public static University getInstance() {
        if (university == null) {
            initializeUniversity();
        }
        
        return university;
    }

    public List<Class> getSubjects() {
        return subjects;
    }
    
    public int getSubjectsCount() {
        return getInstance().subjects.size();
    }
    
    public List<Room> getRooms() {
        return rooms;
    }
    
    public List<Room> getRooms(FacultyType facultyType, RoomType roomType) {
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
    
    @Override
    protected Object clone() {
        return university;
    }
    
    private static final String dataFilename = "university.txt";
    
    private static University university = null;
    
    private List<Class> subjects;
    private List<Lecturer> lecturers;
    private List<Room> rooms;
    private List<Subgroup> subgroups;
    private List<Department> departments;
    private List<Specialty> specialties;
    
    private static void initializeUniversity() {
        university = new University();
    }
    
    private University() {
        // Init data using dataFilename
        rooms = new ArrayList<>();
        departments = new ArrayList<>();
        specialties = new ArrayList<>();
        specialties.add(new Specialty("Компютърни науки", DegreeType.BACHELOR, EducationType.REGULAR, 8));
        
        load();
    }
    
    private boolean save() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(dataFilename))) {
            writer.write(String.valueOf(rooms.size()));
            writer.newLine();
            for (Room room : rooms) {
                room.save(writer);
            }
            
            writer.write(String.valueOf(departments.size()));
            writer.newLine();
            for (Department department : departments) {
                writer.write(department.toString());
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println(e.getMessage());
            System.err.println(e.getStackTrace());
            return false;
        }
        
        return true;
    }
    
    private boolean load() {
        try (BufferedReader reader = new BufferedReader(new FileReader(dataFilename))) {
            String elementCountString = reader.readLine();
            int roomCount = Integer.valueOf(elementCountString);
            for(int roomIndex = 0; roomIndex < roomCount; ++roomIndex) {
                Room newRoom = new Room();
                boolean isRoomLoaded = newRoom.load(reader);
                if (isRoomLoaded) {
                    rooms.add(newRoom);
                }
            }
            
            elementCountString = reader.readLine();
            int departmentCount = Integer.valueOf(elementCountString);
            for(int departmentIndex = 0; departmentIndex < departmentCount; ++departmentIndex) {
                Department newDepartment = new Department(reader.readLine());
                departments.add(newDepartment);
            }
        } catch (IOException e) {
            System.err.println(e.getMessage());
            System.err.println(e.getStackTrace());
            return false;
        }
        
        return true;
    }
}
