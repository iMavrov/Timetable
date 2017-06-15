package university;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.ArrayList;
import utilities.Filter;
import utilities.FilterCriterion;
import utilities.SystemID;
import utilities.SystemIDType;

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
    
    
    public boolean addBuilding(Building newBuilding) {
        if (newBuilding == null) {
            return false;
        }
        
        if (newBuilding.hasBadKey()) {
            return false;
        }
        
        buildings.
        
        final String newBuildingName = newBuilding.getName();
        Filter buildingNameFilter = new Filter(new FilterCriterion<Building>() {
            @Override
            public boolean passes(Building building) {
                return building.getName().equals(newBuildingName);
            }
        });
        
        List<Building> duplicateBuildingNameList = new ArrayList<>();
        buildingNameFilter.filterList(buildings, duplicateBuildingNameList);
        
        if (duplicateBuildingNameList.isEmpty()) {
            return buildings.add(newBuilding);
        } else {
            return false;
        }
    }
    
    public boolean updateBuilding(Building updatedBuilding) {
        if (updatedBuilding == null) {
            return false;
        }
        
        if (updatedBuilding.hasBadKey()) {
            return false;
        }
        
        int firstIndex = buildings.indexOf(updatedBuilding);
        int lastIndex = buildings.lastIndexOf(updatedBuilding);
        
        if ((firstIndex == -1) || (lastIndex == -1) || (firstIndex != lastIndex)) {
            return false;
        }
        
        buildings.set(firstIndex, updatedBuilding);
        return true;
    }
    
    public Building getBuilding(SystemID buildingID) {
        if (buildingID.getType() != SystemIDType.BUILDING_ID) {
            return null;
        }
        
        int index = buildingID.getIndex();
        if (index < 0 || buildings.size() <= index) {
            return null; 
       }
        
        Building result = buildings.get(index);
        
        boolean doubleCheck = result.getID().equals(buildingID);
        
        return result;
    }
    
    public boolean addRoom(Room newRoom) {
        if (newRoom == null) {
            return false;
        }
        
        if (newRoom.hasBadKey()) {
            return false;
        }
        
        if (rooms.contains(newRoom)) {
            return false;
        }
        
        return rooms.add(newRoom);
    }
    
    public boolean updateRoom(Room updatedRoom) {
        if (updatedRoom == null) {
            return false;
        }
        
        if (updatedRoom.hasBadKey()) {
            return false;
        }
        
        int firstIndex = buildings.indexOf(updatedRoom);
        int lastIndex = buildings.lastIndexOf(updatedRoom);
        
        if ((firstIndex == -1) || (lastIndex == -1) || (firstIndex != lastIndex)) {
            return false;
        }
                
        rooms.set(firstIndex, updatedRoom);
        return true;
    }
    
    public Room getRoom(int roomID) {
        if (roomID < 0 || rooms.size() <= roomID) {
            return null;
        }
        
        return rooms.get(roomID);
    }
    
    public boolean addFaculty(Faculty newFaculty) {
        if (newFaculty == null) {
            return false;
        }
        
        if (newFaculty.hasBadKey()) {
            return false;
        }
        
        if (faculties.contains(newFaculty)) {
            return false;
        }
        
        return faculties.add(newFaculty);
    }
    
    public boolean updateFaculty(Faculty updatedFaculty) {
        if (updatedFaculty == null) {
            return false;
        }
        
        if (updatedFaculty.hasBadKey()) {
            return false;
        }
        
        int firstIndex = faculties.indexOf(updatedFaculty);
        int lastIndex = faculties.lastIndexOf(updatedFaculty);
        
        if ((firstIndex == -1) || (lastIndex == -1) || (firstIndex != lastIndex)) {
            return false;
        }
                
        faculties.set(firstIndex, updatedFaculty);
        return true;
    }
    
    public Faculty getFaculty(int facultyID) {
        if (facultyID < 0 || faculties.size() <= facultyID) {
            return null;
        }
        
        return faculties.get(facultyID);
    }
    
    public boolean addDepartment(Department newDepartment) {
        if (newDepartment == null) {
            return false;
        }
        
        if (newDepartment.hasBadKey()) {
            return false;
        }
        
        if (departments.contains(newDepartment)) {
            return false;
        }
        
        return departments.add(newDepartment);
    }
    
    public boolean updateDepartment(Department updatedDepartment) {
        if (updatedDepartment == null) {
            return false;
        }
        
        if (updatedDepartment.hasBadKey()) {
            return false;
        }
        
        int firstIndex = departments.indexOf(updatedDepartment);
        int lastIndex = departments.lastIndexOf(updatedDepartment);
        
        if ((firstIndex == -1) || (lastIndex == -1) || (firstIndex != lastIndex)) {
            return false;
        }
                
        departments.set(firstIndex, updatedDepartment);
        return true;
    }
    
    public Department getDepartment(int departmentID) {
        if (departmentID < 0 || departments.size() <= departmentID) {
            return null;
        }
        
        return departments.get(departmentID);
    }
    
    public boolean addLecturer(Lecturer newLecturer) {
        if (newLecturer == null) {
            return false;
        }
        
        if (newLecturer.hasBadKey()) {
            return false;
        }
        
        if (lecturers.contains(newLecturer)) {
            return false;
        }
        
        return lecturers.add(newLecturer);
    }
    
    public boolean updateLecturer(Lecturer updatedLecturer) {
        if (updatedLecturer == null) {
            return false;
        }
        
        if (updatedLecturer.hasBadKey()) {
            return false;
        }
        
        ArrayList<Integer> matches = new ArrayList<>();
        for (int lecturerIndex = 0; lecturerIndex < lecturers.size(); ++lecturerIndex) {
            if (lecturers.get(lecturerIndex).equals(updatedLecturer)) {
                matches.add(lecturerIndex);
            }
        }
        
        if (matches.size() != 1) {
            return false;
        }
                
        lecturers.set(matches.get(0), updatedLecturer);
        
        return true;
    }
    
    public Lecturer getLecturer(int lecturerID) {
        if (lecturerID < 0 || lecturers.size() <= lecturerID) {
            return null;
        }
        
        return lecturers.get(lecturerID);
    }
    
    public boolean addProgram(Program newProgram) {
        if (newProgram == null) {
            return false;
        }
        
        if (newProgram.hasBadKey()) {
            return false;
        }
        
        if (programs.contains(newProgram)) {
            return false;
        }
        
        return programs.add(newProgram);
    }
    
    public boolean updatePrograms(Program updatedProgram) {
        if (updatedProgram == null) {
            return false;
        }
        
        if (updatedProgram.hasBadKey()) {
            return false;
        }
        
        int firstIndex = programs.indexOf(updatedProgram);
        int lastIndex = programs.lastIndexOf(updatedProgram);
        
        if ((firstIndex == -1) || (lastIndex == -1) || (firstIndex != lastIndex)) {
            return false;
        }
                
        programs.set(firstIndex, updatedProgram);
        return true;
    }
    
    public Program getProgram(int programID) {
        if (programID < 0 || programs.size() <= programID) {
            return null;
        }
        
        return programs.get(programID);
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
        for (Building building : buildings) {
            building.save(writer);
        }
        
        writer.write(String.valueOf(rooms.size()));
        writer.newLine();
        for (Room room : rooms) {
            room.save(writer);
        }
        
        writer.write(String.valueOf(faculties.size()));
        writer.newLine();
        for (Faculty faculty : faculties) {
            faculty.save(writer);
        }
        
        writer.write(String.valueOf(departments.size()));
        writer.newLine();
        for (Department department : departments) {
            department.save(writer);
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
        if (fileVersion != DATABASE_VERSION) {
            return false;
        }
        
        int buildingCount = Integer.valueOf(reader.readLine());
        while (0 < buildingCount) {
            Building newBuilding = new Building();
            newBuilding.load(reader);
            
            buildings.add(newBuilding);
            --buildingCount;
        }

        int roomCount = Integer.valueOf(reader.readLine());
        while (0 < roomCount) {
            Room newRoom = new Room();
            newRoom.load(reader);
            
            rooms.add(newRoom);
            --roomCount;
        }
        
        int facultyCount = Integer.valueOf(reader.readLine());
        while (0 < facultyCount) {
            Faculty newFaculty = new Faculty();
            newFaculty.load(reader);
            
            faculties.add(newFaculty);
            --facultyCount;
        }
        
        int departmentCount = Integer.valueOf(reader.readLine());
        while (0 < departmentCount) {
            Department newDepartment = new Department();
            newDepartment.load(reader);
            
            departments.add(newDepartment);
            --departmentCount;
        }
        
        int lecturerCount = Integer.valueOf(reader.readLine());
        while (0 < lecturerCount) {
            Lecturer newLecturer = new Lecturer();
            newLecturer.load(reader);
            
            lecturers.add(newLecturer);
            --lecturerCount;
        }
        
        int programCount = Integer.valueOf(reader.readLine());
        while (0 < programCount) {
            Program newProgram = new Program();
            newProgram.load(reader);
            
            programs.add(newProgram);
            --programCount;
        }

        return true;
    }
    
    protected University() {
        // Room view
        buildings = new ArrayList<>();
        rooms = new ArrayList<>();
    
        // Lecturer view
        faculties = new ArrayList<>();
        departments = new ArrayList<>();
        lecturers = new ArrayList<>();
    
        // Student view
        programs = new ArrayList<>();
    }
    
    @Override
    protected Object clone() {
        return university;
    }
    
    private static final String DATABASE_FILEPATH = "university.txt";
    private static final float DATABASE_VERSION = 1.0f;
    
    private static University university = null;
    
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
    
    // Room view
    private List<Building> buildings;
    private List<Room> rooms;
    
    // Lecturer view
    private List<Faculty> faculties;
    private List<Department> departments;
    private List<Lecturer> lecturers;
    
    // Program view
    private List<Program> programs;
}
