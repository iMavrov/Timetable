/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package university;

import java.io.IOException;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.util.Set;
import java.util.Map;
import java.util.List;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Map.Entry;

/**
 *
 * @author Mavrov
 */
public class Semester implements IPersistable {
    
    public Semester() {
        type = SemesterType.WINTER;
        calendarYear = 2015;
        
        roomAvailability = new HashMap<>();
        
        lecturerAvailability = new HashMap<>();
        addedSemesterLecturers = 0;
        semesterLecturers = new HashMap<>();
        semesterLecturerAvailability = new HashMap<>();
        
        studentDistribution = new HashMap<>();
        groups = new ArrayList<>();
        studentAvailability = new HashMap<>();
    
        semesterSubjects = new ArrayList<>();
        semesterSubjectCapacity = new HashMap<>();

        classes = new ArrayList<>();
        
        roomToClass = new HashMap<>();
        classToRoom = new HashMap<>();
    
        lecturerToClass = new HashMap<>();
        classToLecturer = new HashMap<>();
        
        studentsToClass = new HashMap<>();
        classToStudents = new HashMap<>();
        
        timetable = new HashMap<>();
    }
    
    public Semester(SemesterType semesterType, int semesterCalendarYear) {
        type = semesterType;
        calendarYear = semesterCalendarYear;
        
        roomAvailability = new HashMap<>();
        
        lecturerAvailability = new HashMap<>();
        addedSemesterLecturers = 0;
        semesterLecturers = new HashMap<>();
        semesterLecturerAvailability = new HashMap<>();
        
        studentDistribution = new HashMap<>();
        groups = new ArrayList<>();
        studentAvailability = new HashMap<>();
    
        semesterSubjects = new ArrayList<>();
        semesterSubjectCapacity = new HashMap<>();

        classes = new ArrayList<>();
        
        roomToClass = new HashMap<>();
        classToRoom = new HashMap<>();
    
        lecturerToClass = new HashMap<>();
        classToLecturer = new HashMap<>();
        
        studentsToClass = new HashMap<>();
        classToStudents = new HashMap<>();
        
        timetable = new HashMap<>();
    }
    
    public void setType(SemesterType newType) {
        type = newType;
    }
    
    public SemesterType getType() {
        return type;
    }
    
    public void setCalendarYear(int newYear) {
        calendarYear = newYear;
    }
    
    public int getCalendarYear() {
        return calendarYear;
    }
    
    public boolean addRoom(int roomID, Availability newRoomAvailability) {
        if (roomAvailability.containsKey(roomID)) {
            return false;
        }
        
        roomAvailability.put(roomID, newRoomAvailability);
        
        return true;
    }
    
    public boolean removeRoom(int roomID) {
        Availability availability = roomAvailability.remove(roomID);
        return (availability != null);
    }
    
    public Set<Integer> getRooms() {
        return roomAvailability.keySet();
    }
    
    public Availability getRoomAvailability(int roomID) {
        return roomAvailability.get(roomID);
    }
    
    public boolean addLecturer(int lecturerID, Availability newLecturerAvailability) {
        if (lecturerAvailability.containsKey(lecturerID)) {
            return false;
        }
        
        lecturerAvailability.put(lecturerID, newLecturerAvailability);
        
        return true;
    }
    
    public boolean removeLecturer(int lecturerID) {
        Availability availability = lecturerAvailability.remove(lecturerID);
        return (availability != null);
    }
    
    public Set<Integer> getLecturers() {
        return lecturerAvailability.keySet();
    }
    
    public Availability getLecturerAvailability(int lecturerID) {
        return lecturerAvailability.get(lecturerID);
    }
    
    public boolean addSemesterLecturer(Lecturer newLecturer, Availability newSemesterLecturerAvailability) {
        if (newLecturer == null) {
            return false;
        }
        
        if (newLecturer.hasBadKey()) {
            return false;
        }
        
        if (semesterLecturers.containsValue(newLecturer)) {
            return false;
        }
        
        ++addedSemesterLecturers;
        
        int newSemesterLecturerID = -addedSemesterLecturers;
        semesterLecturers.put(newSemesterLecturerID, newLecturer);
        semesterLecturerAvailability.put(newSemesterLecturerID, newSemesterLecturerAvailability);

        return true;
    }
    
    public boolean updateSemesterLecturer(Lecturer updatedLecturer) {
        if (updatedLecturer == null) {
            return false;
        }
        
        if (updatedLecturer.hasBadKey()) {
            return false;
        }
        
        ArrayList<Integer> matches = new ArrayList<>();
        for (Entry<Integer, Lecturer> entry : semesterLecturers.entrySet()) {
            if (entry.getValue().equals(updatedLecturer)) {
                matches.add(entry.getKey());
            }
        }
        
        if (matches.size() != 1) {
            return false;
        }

        semesterLecturers.put(matches.get(0), updatedLecturer);
            
        return true;
    }
    
    public boolean removeSemesterLecturer(int lecturerID) {
        semesterLecturers.remove(lecturerID);
        Availability availability = semesterLecturerAvailability.remove(lecturerID);
        
        return (availability != null);
    }
    
    public Set<Integer> getSemesterLecturers() {
        return semesterLecturers.keySet();
    }
    
    public Availability getSemesterLecturerAvailability(int semesterLecturerID) {
        return semesterLecturerAvailability.get(semesterLecturerID);
    }
    
    public boolean addProgram(int programID, StudentDistribution programStudentDistribution) {
        if (studentDistribution.containsKey(programID)) {
            return false;
        }
        
        studentDistribution.put(programID, programStudentDistribution);
        
        // Generate student groups according to student distributions
        // Give the new groups default student availability
        // Generate university classes and connect them to the student groups.
        Program program = University.getInstance().getProgram(programID);
        
        int yearCount = programStudentDistribution.getYearCount();
        for (int yearIndex = 0; yearIndex < yearCount; ++yearIndex) {
            
            int yearNumber = programStudentDistribution.getYearNumber(yearIndex);
            int semesterIndex = 
                    (type == SemesterType.WINTER) ? 
                    2 * (yearNumber - 1) : 
                    2 * (yearNumber - 1) + 1;
                    
            int divisionCount = programStudentDistribution.getDivisionCount(yearIndex);
            for (int divisionIndex = 0; divisionIndex < divisionCount; ++divisionIndex) {
                
                int divisionNumber = programStudentDistribution.getDivisionNumber(yearIndex, divisionIndex);
                int divisionCapacity = programStudentDistribution.getDivisionCapacity(yearIndex, divisionIndex);
                
                List<Integer> newGroupIDs = new ArrayList<>();
                
                int groupCount = programStudentDistribution.getGroupCount(yearIndex, divisionIndex);
                for (int groupIndex = 0; groupIndex < groupCount; ++groupIndex) {
                    
                    int groupNumber = programStudentDistribution.getGroupNumber(yearIndex, divisionIndex, groupIndex);
                    int groupCapacity = programStudentDistribution.getGroupCapacity(yearIndex, divisionIndex, groupIndex);
                    
                    Group group = new Group(programID, yearNumber, divisionNumber, groupNumber, groupCapacity);
                    groups.add(group);
                    
                    int groupID = groups.size() - 1;
                    studentAvailability.put(groupID, new Availability());
                    
                    studentsToClass.put(groupID, new ArrayList<Integer>());
                    
                    newGroupIDs.add(groupID);
                    
                    // Add all the laboratory and seminar classes this group has
                    int semesterSubjectCount = program.getSemesterSubjectCount(semesterIndex);
                    for (int subjectIndex = 0; subjectIndex < semesterSubjectCount; ++subjectIndex) {
                        
                        Subject subject = program.getSubject(semesterIndex, subjectIndex);
                        String subjectName = subject.getShortName();
                        
                        boolean subjectHasSeminar = subject.hasClass(UniversityClassType.SEMINAR);
                        if (subjectHasSeminar) {
                            int seminarHourCount = subject.getClassHourCount(UniversityClassType.SEMINAR);

                            UniversityClass universityClass = new UniversityClass(
                                    programID, semesterIndex, subjectIndex, 
                                    UniversityClassType.SEMINAR, subjectName, 
                                    seminarHourCount, groupCapacity);
                            classes.add(universityClass);
                            
                            int classID = classes.size() - 1;
                            
                            List<Integer> groupIDs = new ArrayList<>(1);
                            groupIDs.add(groupID);
                            
                            classToStudents.put(classID, groupIDs);
                            studentsToClass.get(groupID).add(classID);
                        }

                        boolean subjectHasLab = subject.hasClass(UniversityClassType.LABORATORY);
                        if (subjectHasLab) {
                            int labHourCount = subject.getClassHourCount(UniversityClassType.LABORATORY);

                            UniversityClass universityClass = new UniversityClass(
                                    programID, semesterIndex, subjectIndex, 
                                    UniversityClassType.LABORATORY, subjectName, 
                                    labHourCount, groupCapacity);

                            classes.add(universityClass);
                            
                            int classID = classes.size() - 1;
                            
                            List<Integer> groupIDs = new ArrayList<>(1);
                            groupIDs.add(groupID);
                            
                            classToStudents.put(classID, groupIDs);
                            studentsToClass.get(groupID).add(classID);
                        }
                    }
                }
                
                // Add all the lections this division of groups has
                int semesterSubjectCount = program.getSemesterSubjectCount(semesterIndex);
                for (int subjectIndex = 0; subjectIndex < semesterSubjectCount; ++subjectIndex) {
                    
                    Subject subject = program.getSubject(semesterIndex, subjectIndex);
                    String subjectName = subject.getShortName();

                    boolean subjectHasLection = subject.hasClass(UniversityClassType.LECTION);
                    if (subjectHasLection) {
                        int lectionHourCount = subject.getClassHourCount(UniversityClassType.LECTION);

                        // For each division
                        UniversityClass universityClass = new UniversityClass(
                                programID, semesterIndex, subjectIndex, 
                                UniversityClassType.LECTION, subjectName, 
                                lectionHourCount, divisionCapacity);

                        classes.add(universityClass);
                        
                        int classID = classes.size() - 1;
                        
                        classToStudents.put(classID, newGroupIDs);
                        
                        for (Integer groupID : newGroupIDs) {
                            studentsToClass.get(groupID).add(classID);
                        }
                    }
                }
            }
        }
        
        return true;
    }
    
    public boolean removeProgram(int programID) {
        StudentDistribution distribution = studentDistribution.remove(programID);
        return (distribution != null);
    }
    
    public Set<Integer> getPrograms() {
        return studentDistribution.keySet();
    }
    
    public StudentDistribution getStudentDistribution(int programID) {
        return studentDistribution.get(programID);
    }
    
    @Override
    public boolean load(BufferedReader reader) throws IOException {
        type = SemesterType.valueOf(reader.readLine());
        calendarYear = Integer.valueOf(reader.readLine());
        
        int roomAvailabilityItemsCount = Integer.valueOf(reader.readLine());
        while (0 < roomAvailabilityItemsCount) {
            int id = Integer.valueOf(reader.readLine());
            
            Availability availability = new Availability();
            availability.load(reader);
            
            roomAvailability.put(id, availability);
            --roomAvailabilityItemsCount;
        }
        
        int lecturerAvailabilityItemsCount = Integer.valueOf(reader.readLine());
        while (0 < lecturerAvailabilityItemsCount) {
            int id = Integer.valueOf(reader.readLine());
            
            Availability availability = new Availability();
            availability.load(reader);
            
            lecturerAvailability.put(id, availability);
            --lecturerAvailabilityItemsCount;
        }
        
        addedSemesterLecturers = Integer.valueOf(reader.readLine());
        
        int semesterLecturerCount = Integer.valueOf(reader.readLine());
        while (0 < semesterLecturerCount) {
            int lecturerID = Integer.valueOf(reader.readLine());
            
            Lecturer lecturer = new Lecturer();
            lecturer.load(reader);
            
            semesterLecturers.put(lecturerID, lecturer);
            --semesterLecturerCount;
        }
        
        int semesterLecturerAvailabilityItemsCount = Integer.valueOf(reader.readLine());
        while (0 < semesterLecturerAvailabilityItemsCount) {
            int id = Integer.valueOf(reader.readLine());
            
            Availability availability = new Availability();
            availability.load(reader);            
            
            semesterLecturerAvailability.put(id, availability);
            --semesterLecturerAvailabilityItemsCount;
        }
        
        int studentDistributionItemsCount = Integer.valueOf(reader.readLine());
        while (0 < studentDistributionItemsCount) {
            int id = Integer.valueOf(reader.readLine());
            
            StudentDistribution distribution = new StudentDistribution();
            distribution.load(reader);
            
            studentDistribution.put(id, distribution);
            --studentDistributionItemsCount;
        }
        
        int groupsCount = Integer.valueOf(reader.readLine());
        while (0 < groupsCount) {
            Group group = new Group();
            group.load(reader);

            groups.add(group);
        }
        
        int studentAvailabilityItemsCount = Integer.valueOf(reader.readLine());
        while (0 < studentAvailabilityItemsCount) {
            int id = Integer.valueOf(reader.readLine());
            
            Availability availability = new Availability();
            availability.load(reader);            
            
            studentAvailability.put(id, availability);
            --studentAvailabilityItemsCount;
        }
    
        int semesterSubjectsCount = Integer.valueOf(reader.readLine());
        while (0 < semesterSubjectsCount) {
            Subject subject = new Subject();
            subject.load(reader);
            
            semesterSubjects.add(subject);
            --semesterSubjectsCount;
        }
        
        int semesterSubjectCapacityItemsCount = Integer.valueOf(reader.readLine());
        while (0 < semesterSubjectCapacityItemsCount) {
            int id = Integer.valueOf(reader.readLine());
            int capacity = Integer.valueOf(reader.readLine());
            
            semesterSubjectCapacity.put(id, capacity);
            --semesterSubjectCapacityItemsCount;
        }

        int classesCount = Integer.valueOf(reader.readLine());
        while (0 < classesCount) {
            UniversityClass universityClass = new UniversityClass();
            universityClass.load(reader);
            
            classes.add(universityClass);
            --classesCount;
        }
        
        int roomToClassItemsCount = Integer.valueOf(reader.readLine());
        while (0 < roomToClassItemsCount) {
            int roomID = Integer.valueOf(reader.readLine());
            
            int classIDItemsCount = Integer.valueOf(reader.readLine());
            List<Integer> classIDs = new ArrayList<>(classIDItemsCount);
            while (0 < classIDItemsCount) {
                int classID = Integer.valueOf(reader.readLine());
                classIDs.add(classID);
                --classIDItemsCount;
            }
            
            roomToClass.put(roomID, classIDs);
            --roomToClassItemsCount;
        }
        
        int classToRoomItemsCount = Integer.valueOf(reader.readLine());
        while (0 < classToRoomItemsCount) {
            int classID = Integer.valueOf(reader.readLine());
            int roomID = Integer.valueOf(reader.readLine());

            classToRoom.put(classID, roomID);
            --classToRoomItemsCount;
        }
    
        int lecturerToClassItemsCount = Integer.valueOf(reader.readLine());
        while (0 < lecturerToClassItemsCount) {
            int lecturerID = Integer.valueOf(reader.readLine());
            
            int classIDItemsCount = Integer.valueOf(reader.readLine());
            List<Integer> classIDs = new ArrayList<>(classIDItemsCount);
            while (0 < classIDItemsCount) {
                int classID = Integer.valueOf(reader.readLine());
                classIDs.add(classID);
                --classIDItemsCount;
            }
            
            lecturerToClass.put(lecturerID, classIDs);
            --lecturerToClassItemsCount;
        }
        
        int classToLecturerItemsCount = Integer.valueOf(reader.readLine());
        while (0 < classToLecturerItemsCount) {
            int classID = Integer.valueOf(reader.readLine());
            
            int lecturerIDItemsCount = Integer.valueOf(reader.readLine());
            List<Integer> lecturerIDs = new ArrayList<>(lecturerIDItemsCount);
            while (0 < lecturerIDItemsCount) {
                int lecturerID = Integer.valueOf(reader.readLine());
                lecturerIDs.add(lecturerID);
                --lecturerIDItemsCount;
            }
            
            classToLecturer.put(classID, lecturerIDs);
            --classToLecturerItemsCount;
        }
        
        int studentsToClassItemsCount = Integer.valueOf(reader.readLine());
        while (0 < studentsToClassItemsCount) {
            int groupID = Integer.valueOf(reader.readLine());
            
            int classIDItemsCount = Integer.valueOf(reader.readLine());
            List<Integer> classIDs = new ArrayList<>(classIDItemsCount);
            while (0 < classIDItemsCount) {
                int classID = Integer.valueOf(reader.readLine());
                classIDs.add(classID);
                --classIDItemsCount;
            }
            
            studentsToClass.put(groupID, classIDs);
            --studentsToClassItemsCount;
        }
        
        int classToStudentsItemsCount = Integer.valueOf(reader.readLine());
        while (0 < classToStudentsItemsCount) {
            int classID = Integer.valueOf(reader.readLine());
            
            int groupIDItemsCount = Integer.valueOf(reader.readLine());
            List<Integer> groupIDs = new ArrayList<>(groupIDItemsCount);
            while (0 < groupIDItemsCount) {
                int groupID = Integer.valueOf(reader.readLine());
                groupIDs.add(groupID);
                --groupIDItemsCount;
            }
            
            classToStudents.put(classID, groupIDs);
            --classToStudentsItemsCount;
        }
        
        int timetableItemsCount = Integer.valueOf(reader.readLine());
        while (0 < timetableItemsCount) {
            int classID = Integer.valueOf(reader.readLine());
            
            ClassPlacement placement = new ClassPlacement();
            placement.load(reader);
            
            timetable.put(classID, placement);
            --timetableItemsCount;
        }
        
        return true;
    }

    @Override
    public boolean save(BufferedWriter writer) throws IOException {
        writer.write(type.toString());
        writer.newLine();
        
        writer.write(String.valueOf(calendarYear));
        writer.newLine();
        
        writer.write(String.valueOf(roomAvailability.size()));
        writer.newLine();
        for (Entry<Integer, Availability> entry : roomAvailability.entrySet()) {
            writer.write(String.valueOf(entry.getKey()));
            writer.newLine();
            
            entry.getValue().save(writer);
        }
        
        writer.write(String.valueOf(lecturerAvailability.size()));
        writer.newLine();
        for (Entry<Integer, Availability> entry : lecturerAvailability.entrySet()) {
            writer.write(String.valueOf(entry.getKey()));
            writer.newLine();
            
            entry.getValue().save(writer);
        }
        
        writer.write(String.valueOf(addedSemesterLecturers));
        writer.newLine();
        
        writer.write(String.valueOf(semesterLecturers.size()));
        writer.newLine();
        for (Entry<Integer, Lecturer> entry : semesterLecturers.entrySet()) {
            writer.write(String.valueOf(entry.getKey()));
            writer.newLine();
            
            entry.getValue().save(writer); 
        }
        
        writer.write(String.valueOf(semesterLecturerAvailability.size()));
        writer.newLine();
        for (Entry<Integer, Availability> entry : semesterLecturerAvailability.entrySet()) {
            writer.write(String.valueOf(entry.getKey()));
            writer.newLine();
            
            entry.getValue().save(writer);
        }
        
        writer.write(String.valueOf(studentDistribution.size()));
        writer.newLine();
        for (Entry<Integer, StudentDistribution> entry : studentDistribution.entrySet()) {
            writer.write(String.valueOf(entry.getKey()));
            writer.newLine();
            
            entry.getValue().save(writer);
        }
        
        writer.write(String.valueOf(groups.size()));
        writer.newLine();
        for (Group group : groups) {
            group.save(writer);
        }
        
        writer.write(String.valueOf(studentAvailability.size()));
        writer.newLine();
        for (Entry<Integer, Availability> entry : studentAvailability.entrySet()) {
            writer.write(String.valueOf(entry.getKey()));
            writer.newLine();
            
            entry.getValue().save(writer);
        }
        
        writer.write(String.valueOf(semesterSubjects.size()));
        writer.newLine();
        for (Subject subject : semesterSubjects) {
            subject.save(writer);
        }
        
        writer.write(String.valueOf(semesterSubjectCapacity.size()));
        writer.newLine();
        for (Entry<Integer, Integer> entry : semesterSubjectCapacity.entrySet()) {
            writer.write(String.valueOf(entry.getKey()));
            writer.newLine();
            
            writer.write(String.valueOf(entry.getValue()));
            writer.newLine();
        }

        writer.write(String.valueOf(classes.size()));
        writer.newLine();
        for (UniversityClass universityClass : classes) {
            universityClass.save(writer);
        }
        
        writer.write(String.valueOf(roomToClass.size()));
        writer.newLine();
        for (Entry<Integer, List<Integer>> entry : roomToClass.entrySet()) {
            writer.write(String.valueOf(entry.getKey()));
            writer.newLine();
            
            writer.write(String.valueOf(entry.getValue().size()));
            writer.newLine();
            for (Integer value : entry.getValue()) {
                writer.write(String.valueOf(value));
                writer.newLine();
            }
        }
        
        writer.write(String.valueOf(classToRoom.size()));
        writer.newLine();
        for (Entry<Integer, Integer> entry : classToRoom.entrySet()) {
            writer.write(String.valueOf(entry.getKey()));
            writer.newLine();
            
            writer.write(String.valueOf(entry.getValue()));
            writer.newLine();
        }
    
        writer.write(String.valueOf(lecturerToClass.size()));
        writer.newLine();
        for (Entry<Integer, List<Integer>> entry : lecturerToClass.entrySet()) {
            writer.write(String.valueOf(entry.getKey()));
            writer.newLine();
            
            writer.write(String.valueOf(entry.getValue().size()));
            writer.newLine();
            for (Integer value : entry.getValue()) {
                writer.write(String.valueOf(value));
                writer.newLine();
            }
        }
        
        writer.write(String.valueOf(classToLecturer.size()));
        writer.newLine();
        for (Entry<Integer, List<Integer>> entry : classToLecturer.entrySet()) {
            writer.write(String.valueOf(entry.getKey()));
            writer.newLine();
            
            writer.write(String.valueOf(entry.getValue().size()));
            writer.newLine();
            for (Integer value : entry.getValue()) {
                writer.write(String.valueOf(value));
                writer.newLine();
            }
        }
        
        writer.write(String.valueOf(studentsToClass.size()));
        writer.newLine();
        for (Entry<Integer, List<Integer>> entry : studentsToClass.entrySet()) {
            writer.write(String.valueOf(entry.getKey()));
            writer.newLine();
            
            writer.write(String.valueOf(entry.getValue().size()));
            writer.newLine();
            for (Integer value : entry.getValue()) {
                writer.write(String.valueOf(value));
                writer.newLine();
            }
        }
        
        writer.write(String.valueOf(classToStudents.size()));
        writer.newLine();
        for (Entry<Integer, List<Integer>> entry : classToStudents.entrySet()) {
            writer.write(String.valueOf(entry.getKey()));
            writer.newLine();
            
            writer.write(String.valueOf(entry.getValue().size()));
            writer.newLine();
            for (Integer value : entry.getValue()) {
                writer.write(String.valueOf(value));
                writer.newLine();
            }
        }
        
        writer.write(String.valueOf(timetable.size()));
        writer.newLine();
        for (Entry<Integer, ClassPlacement> entry : timetable.entrySet()) {
            writer.write(String.valueOf(entry.getKey()));
            writer.newLine();
            
            entry.getValue().save(writer);
        }
        
        return true;
    }
    
    private SemesterType type;
    private int calendarYear;
    
    // Room view.
    // Room ID to Availability.
    private Map<Integer, Availability> roomAvailability;
    
    // Lecturer view. 
    // State Lecturer ID to Availability. 
    private Map<Integer, Availability> lecturerAvailability;
    
    // Count of the number of added semester lecturers. Helps for the unique ID system.
    private int addedSemesterLecturers;
    // Semester specific lecturers to LecturerID
    private Map<Integer, Lecturer> semesterLecturers;
    // Semester specific lecturer ID to Availability.
    private Map<Integer, Availability> semesterLecturerAvailability;
    
    // Student view.
    // Program ID to StudentDistribution.
    private Map<Integer, StudentDistribution> studentDistribution;
    // Student group availability.
    private List<Group> groups;
    // Group ID to Availability
    private Map<Integer, Availability> studentAvailability;
    
    // Semester specific subjects and their capacity
    private List<Subject> semesterSubjects;
    private Map<Integer, Integer> semesterSubjectCapacity;
    
    // Classes to place in the timetable
    private List<UniversityClass> classes;
    
    // 1. Use the student distribution to generate classes
    
    // 2. Assign lecturers to classes
    
    // 1. Merge the different classes that would be teached together by the same teacher.
    
    // 3. Select a class to place and place it.
    
    // Room ID to a list of UniversityClass IDs
    private Map<Integer, List<Integer>> roomToClass;
    
    // UniversityClass ID to Room ID
    private Map<Integer, Integer> classToRoom;
    
    // Lecturer ID to a list of UniversityClass IDs
    private Map<Integer, List<Integer>> lecturerToClass;
    
    // UniversityClass ID to Lecturer ID
    private Map<Integer, List<Integer>> classToLecturer;
    
    // Group IDs to UniversityClass ID
    private Map<Integer, List<Integer>> studentsToClass;
    
    // UniversityClass ID to Group IDs
    private Map<Integer, List<Integer>> classToStudents;
    
    // UniversityClass ID to Placement
    private Map<Integer, ClassPlacement> timetable;
}
