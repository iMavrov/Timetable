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
import java.util.HashSet;
import java.util.Map.Entry;
import utilities.Filter;

/**
 *
 * @author Mavrov
 */
public class Semester implements IPersistable {
    
    public Semester() {
        type = SemesterType.WINTER;
        calendarYear = 2015;
        
        roomAvailability = new HashMap<>();
        
        //lecturerAvailability = new HashMap<>();
        addedSemesterLecturers = 0;
        semesterLecturers = new HashMap<>();
        semesterLecturerAvailability = new HashMap<>();
        
        programs = new HashMap<>();
        groups = new HashSet<>();
        studentAvailability = new HashMap<>();
    
        addedSemesterSubjects = 0;
        semesterSubjects = new HashMap<>();
        semesterSubjectCapacity = new HashMap<>();

        classes = new HashSet<>();
        
        roomToClass = new HashMap<>();
        classToRoom = new HashMap<>();
    
        //lecturerToClass = new HashMap<>();
        classToLecturer = new HashMap<>();
        
        studentsToClass = new HashMap<>();
        classToStudents = new HashMap<>();
        
        timetable = new HashMap<>();
        
        lecturers = new HashSet<>();
    }
    
    public Semester(SemesterType semesterType, int semesterCalendarYear) {
        type = semesterType;
        calendarYear = semesterCalendarYear;
        
        roomAvailability = new HashMap<>();
        
        //lecturerAvailability = new HashMap<>();
        addedSemesterLecturers = 0;
        semesterLecturers = new HashMap<>();
        semesterLecturerAvailability = new HashMap<>();
        
        programs = new HashMap<>();
        groups = new HashSet<>();
        studentAvailability = new HashMap<>();
    
        addedSemesterSubjects = 0;
        semesterSubjects = new HashMap<>();
        semesterSubjectCapacity = new HashMap<>();

        classes = new HashSet<>();
        
        roomToClass = new HashMap<>();
        classToRoom = new HashMap<>();
    
        //lecturerToClass = new HashMap<>();
        classToLecturer = new HashMap<>();
        
        studentsToClass = new HashMap<>();
        classToStudents = new HashMap<>();
        
        timetable = new HashMap<>();
        
        lecturers = new HashSet<>();
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
    
    public boolean addRoom(Room newRoom) {
        if (newRoom == null) {
            return false;
        }
        
        return rooms.add(newRoom);
    }
    
    public boolean removeRoom(int roomID) {
        Schedule availability = roomAvailability.remove(roomID);
        return (availability != null);
    }
    
    public Set<Integer> getRooms() {
        return roomAvailability.keySet();
    }
    
    public Schedule getRoomAvailability(int roomID) {
        return roomAvailability.get(roomID);
    }
    
    public boolean setRoomAvailability(int roomID, Schedule newAvailability) {
        if (!roomAvailability.containsKey(roomID)) {
            return false;
        }
        
        roomAvailability.put(roomID, newAvailability);
        
        return true;
    }
    
    public boolean addLecturer(Lecturer lecturer) {
        if (lecturer == null) {
            return false;
        }
        
        return lecturers.add(lecturer);
    }
    
    public boolean removeLecturer(Lecturer lecturer) {
        if (lecturer == null) {
            return false;
        }
        
        boolean areAllClassesUnassigned = lecturer.unassignAllClasses();
        boolean isLecturerRemoved = lecturers.remove(lecturer);
        
        return areAllClassesUnassigned && isLecturerRemoved;
    }
    
    public Set<Lecturer> getLecturers() {
        return lecturers;
    }
    
    public boolean addSemesterLecturer(LecturerData newLecturer, Schedule newSemesterLecturerAvailability) {
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
        
        lecturerToClass.put(newSemesterLecturerID, new ArrayList<Integer>());

        return true;
    }
    
    public boolean updateSemesterLecturer(LecturerData updatedLecturer) {
        if (updatedLecturer == null) {
            return false;
        }
        
        if (updatedLecturer.hasBadKey()) {
            return false;
        }
        
        ArrayList<Integer> matches = new ArrayList<>();
        for (Entry<Integer, LecturerData> entry : semesterLecturers.entrySet()) {
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
        Schedule availability = semesterLecturerAvailability.remove(lecturerID);
        lecturerToClass.remove(lecturerID);
        
        return (availability != null);
    }
    
    public Set<Integer> getSemesterLecturers() {
        return semesterLecturers.keySet();
    }
    
    public Schedule getSemesterLecturerAvailability(int semesterLecturerID) {
        return semesterLecturerAvailability.get(semesterLecturerID);
    }
    
    public boolean setSemesterLecturerAvailability(int semesterLecturerID, Schedule newAvailability) {
        if (!semesterLecturerAvailability.containsKey(semesterLecturerID)) {
            return false;
        }

        lecturerAvailability.put(semesterLecturerID, newAvailability);

        return true;
    }
    
    public boolean addProgram(Program newProgram, StudentDistribution newProgramStudentDistribution) {
        if (newProgram == null || newProgramStudentDistribution == null) {
            return false;
        }
        
        programs.put(newProgram, newProgramStudentDistribution);
        
        // Generate student groups according to student distributions
        // Give the new groups default student availability
        // Generate university classes and connect them to the student groups.
        
        int yearCount = newProgramStudentDistribution.getYearCount();
        for (int yearIndex = 0; yearIndex < yearCount; ++yearIndex) {
            
            int yearNumber = newProgramStudentDistribution.getYearNumber(yearIndex);
            int semesterIndex = 
                    (type == SemesterType.WINTER) ? 
                    2 * (yearNumber - 1) : 
                    2 * (yearNumber - 1) + 1;
                    
            int divisionCount = newProgramStudentDistribution.getDivisionCount(yearIndex);
            for (int divisionIndex = 0; divisionIndex < divisionCount; ++divisionIndex) {
                
                int divisionNumber = newProgramStudentDistribution.getDivisionNumber(yearIndex, divisionIndex);
                int divisionCapacity = newProgramStudentDistribution.getDivisionCapacity(yearIndex, divisionIndex);
                
                List<Integer> newGroupIDs = new ArrayList<>();
                
                int groupCount = newProgramStudentDistribution.getGroupCount(yearIndex, divisionIndex);
                for (int groupIndex = 0; groupIndex < groupCount; ++groupIndex) {
                    
                    int groupNumber = newProgramStudentDistribution.getGroupNumber(yearIndex, divisionIndex, groupIndex);
                    int groupCapacity = newProgramStudentDistribution.getGroupCapacity(yearIndex, divisionIndex, groupIndex);
                    
                    Group group = new Group(newProgram, yearNumber, divisionNumber, groupNumber, groupCapacity);
                    groups.add(group);
                    
                    int groupID = groups.size() - 1;
                    studentAvailability.put(groupID, new Schedule());
                    
                    studentsToClass.put(groupID, new ArrayList<Integer>());
                    
                    newGroupIDs.add(groupID);
                    
                    // Add all the laboratory and seminar classes this group has
                    int semesterSubjectCount = newProgram.getSemesterSubjectCount(semesterIndex);
                    for (int subjectIndex = 0; subjectIndex < semesterSubjectCount; ++subjectIndex) {
                        
                        Subject subject = newProgram.getSubject(semesterIndex, subjectIndex);
                        String subjectName = subject.getShortName();
                        
                        boolean subjectHasSeminar = subject.hasClass(UniversityClassType.SEMINAR);
                        if (subjectHasSeminar) {
                            int seminarHourCount = subject.getClassHourCount(UniversityClassType.SEMINAR);

                            UniversityClass universityClass = new UniversityClass(
                                    subject, UniversityClassType.SEMINAR, subjectName, 
                                    seminarHourCount, groupCapacity);
                            classes.add(universityClass);
                            
                            int classID = classes.size() - 1;
                            
                            List<Integer> groupIDs = new ArrayList<>(1);
                            groupIDs.add(groupID);
                            
                            classToStudents.put(classID, groupIDs);
                            studentsToClass.get(groupID).add(classID);
                            
                            classToLecturer.put(classID, new ArrayList<Integer>());
                        }

                        boolean subjectHasLab = subject.hasClass(UniversityClassType.LABORATORY);
                        if (subjectHasLab) {
                            int labHourCount = subject.getClassHourCount(UniversityClassType.LABORATORY);

                            UniversityClass universityClass = new UniversityClass(
                                    subject, UniversityClassType.LABORATORY, subjectName, 
                                    labHourCount, groupCapacity);

                            classes.add(universityClass);
                            
                            int classID = classes.size() - 1;
                            
                            List<Integer> groupIDs = new ArrayList<>(1);
                            groupIDs.add(groupID);
                            
                            classToStudents.put(classID, groupIDs);
                            studentsToClass.get(groupID).add(classID);
                            
                            classToLecturer.put(classID, new ArrayList<Integer>());
                        }
                    }
                }
                
                // Add all the lections this division of groups has
                int semesterSubjectCount = newProgram.getSemesterSubjectCount(semesterIndex);
                for (int subjectIndex = 0; subjectIndex < semesterSubjectCount; ++subjectIndex) {
                    
                    Subject subject = newProgram.getSubject(semesterIndex, subjectIndex);
                    String subjectName = subject.getShortName();

                    boolean subjectHasLection = subject.hasClass(UniversityClassType.LECTION);
                    if (subjectHasLection) {
                        int lectionHourCount = subject.getClassHourCount(UniversityClassType.LECTION);

                        // For each division
                        UniversityClass universityClass = new UniversityClass(
                                subject, UniversityClassType.LECTION, subjectName, 
                                lectionHourCount, divisionCapacity);

                        classes.add(universityClass);
                        
                        int classID = classes.size() - 1;
                        
                        classToStudents.put(classID, newGroupIDs);
                        
                        for (Integer groupID : newGroupIDs) {
                            studentsToClass.get(groupID).add(classID);
                        }
                        
                        classToLecturer.put(classID, new ArrayList<Integer>());
                    }
                }
            }
        }
        
        return true;
    }
    
    public boolean removeProgram(Program program) {
        // Remove program
        programs.remove(program);
        
        // Remove groups
        List<Group> groupsToRemove = new ArrayList<>();
        
        for (Group group : groups) {
            if (group.getProgram() == program) {
                groupsToRemove.add(group); 
            }
        }
        
        for (Group group : groupsToRemove) {
            groups.remove(group);
            // TODO: studentAvailability.remove(groupIndex);
            // TODO: studentsToClass.remove(groupIndex);
        }
        
        // Remove classIDs
        List<UniversityClass> classesToRemove = new ArrayList<>();
        
        for (UniversityClass universityClass : classes) {
            if (universityClass.getSubject().getProgram() == program) {
                classesToRemove.add(universityClass);
            }
        }
        
        for (UniversityClass universityClass : classesToRemove) {
            classes.remove(universityClass);
            //TODO: classToStudents.remove(classIndex);
            //TODO: classToLecturer.remove(classIndex);
        }

        return true;
    }
    
    public Set<Program> getPrograms() {
        return programs.keySet();
    }
    
    public StudentDistribution getStudentDistribution(Program program) {
        return programs.get(program);
    }
    
    public boolean setStudentDistribution(Program program, StudentDistribution newDistribution) {
        if (!programs.containsKey(program)) {
            return false;
        }
        
        boolean programRemoved = removeProgram(program);
        
        boolean programAdded = addProgram(program, newDistribution);
        
        return (programRemoved && programAdded);
    }
    
    public boolean addSemesterSubject(Subject newSubject, int capacity) {
        if (newSubject == null) {
            return false;
        }
        
        if (newSubject.hasBadKey()) {
            return false;
        }
        
        if (semesterSubjects.containsValue(newSubject)) {
            return false;
        }
        
        ++addedSemesterSubjects;
        
        int newSemesterSubjectID = -addedSemesterSubjects;
        semesterSubjects.put(newSemesterSubjectID, newSubject);
        semesterSubjectCapacity.put(newSemesterSubjectID, capacity);

        return true;
    }
    
    public boolean updateSemesterSubject(Subject updatedSubject, int updatedCapacity) {
        if (updatedSubject == null) {
            return false;
        }
        
        if (updatedSubject.hasBadKey()) {
            return false;
        }
        
        ArrayList<Integer> matches = new ArrayList<>();
        for (Entry<Integer, Subject> entry : semesterSubjects.entrySet()) {
            if (entry.getValue().equals(updatedSubject)) {
                matches.add(entry.getKey());
            }
        }
        
        if (matches.size() != 1) {
            return false;
        }

        semesterSubjects.put(matches.get(0), updatedSubject);
        semesterSubjectCapacity.put(matches.get(0), updatedCapacity);
            
        return true;
    }
    
    public boolean removeSemesterSubject(int subjectID) {
        semesterSubjects.remove(subjectID);
        Integer capacity = semesterSubjectCapacity.remove(subjectID);
        
        return (capacity != null);
    }
    
    public Set<Integer> getSemesterSubjects() {
        return semesterSubjects.keySet();
    }
    
    public int getSemesterSubjectsCapacity(int semesterSubjectID) {
        return semesterSubjectCapacity.get(semesterSubjectID);
    }
    
    public boolean setSemesterSubjectCapacity(int semesterSubjectID, int newCapacity) {
        if (!semesterSubjectCapacity.containsKey(semesterSubjectID)) {
            return false;
        }
        
        semesterSubjectCapacity.put(semesterSubjectID, newCapacity);
        
        return true;
    }
    
    public boolean assignLecturerToClass(Lecturer lecturer, UniversityClass universityClass) {
        if (universityClass == null) {
            return false;
        }
        
        return universityClass.assignLecturer(lecturer, AssignPolicy.BOTH_WAYS);
    }
    
    public boolean unassignLecturerFromClass(Lecturer lecturer, UniversityClass universityClass) {
        if (universityClass == null) {
            return false;
        }
        
        return universityClass.unassignLecturer(lecturer, AssignPolicy.BOTH_WAYS);
    }
    
    public boolean removeClass(UniversityClass universityClass) {
        if (universityClass == null) {
            return false;
        }
        
        return universityClass.unassignAll();
        // TODO: universityClass = null; ???
    }
    
    public boolean mergeClasses(UniversityClass class1, UniversityClass class2) {
        if ((class1 == null) || (class2 == null)) {
            return false;
        }
        
        if (class1.getType() != class2.getType()) {
            return false;
        }
        
        if (class1.getDuration() != class2.getDuration()) {
            return false;
        }

        boolean isMergeOK = true;
        
        class1.setCapacity(class1.getCapacity() + class2.getCapacity());
        
        for (String attribute : class2.getAttributes()) {
            class1.addAttribute(attribute);
        }
        
//        List<Integer> class1GroupIDs = classToStudents.get(classID1);
//        List<Integer> class2GroupIDs = classToStudents.get(classID2);
//        
//        for (Integer class2GroupID : class2GroupIDs) {
//            class1GroupIDs.add(class2GroupID);
//            studentsToClass.get(class2GroupID).add(classID1);
//        }
        
        boolean areGroupsTransferred = true;
        for (Group group : class2.getGroups()) {
            boolean isGroupTransferred = class1.assignGroup(group, AssignPolicy.BOTH_WAYS);
            areGroupsTransferred = areGroupsTransferred && isGroupTransferred;
        }

        boolean isClass2Removed = removeClass(class2);

        return areGroupsTransferred && isClass2Removed;
    }
       
    public Set<Lecturer> filterLecturers(int departmentID) {
        Filter<Lecturer> lecturerFilter = new Filter<>();
        lecturerFilter.addCriterion(new DepartmentFilterCriterion(departmentID));
        
        return lecturerFilter.filterList(lecturers);
    }
    
    public Set<UniversityClass> filterClasses(Program program) {
        Filter<UniversityClass> classFilter = new Filter<>();
        classFilter.addCriterion(new ProgramFilterCriterion(program));
        
        return classFilter.filterList(classes);
    }
    
    @Override
    public boolean load(BufferedReader reader) throws IOException {
        type = SemesterType.valueOf(reader.readLine());
        calendarYear = Integer.valueOf(reader.readLine());
        
        int roomAvailabilityItemsCount = Integer.valueOf(reader.readLine());
        while (0 < roomAvailabilityItemsCount) {
            int id = Integer.valueOf(reader.readLine());
            
            Schedule availability = new Schedule();
            availability.load(reader);
            
            roomAvailability.put(id, availability);
            --roomAvailabilityItemsCount;
        }
        
        int lecturerAvailabilityItemsCount = Integer.valueOf(reader.readLine());
        while (0 < lecturerAvailabilityItemsCount) {
            int id = Integer.valueOf(reader.readLine());
            
            Schedule availability = new Schedule();
            availability.load(reader);
            
            lecturerAvailability.put(id, availability);
            --lecturerAvailabilityItemsCount;
        }
        
        addedSemesterLecturers = Integer.valueOf(reader.readLine());
        
        int semesterLecturerCount = Integer.valueOf(reader.readLine());
        while (0 < semesterLecturerCount) {
            int lecturerID = Integer.valueOf(reader.readLine());
            
            LecturerData lecturer = new LecturerData();
            lecturer.load(reader);
            
            semesterLecturers.put(lecturerID, lecturer);
            --semesterLecturerCount;
        }
        
        int semesterLecturerAvailabilityItemsCount = Integer.valueOf(reader.readLine());
        while (0 < semesterLecturerAvailabilityItemsCount) {
            int id = Integer.valueOf(reader.readLine());
            
            Schedule availability = new Schedule();
            availability.load(reader);            
            
            semesterLecturerAvailability.put(id, availability);
            --semesterLecturerAvailabilityItemsCount;
        }
        
        int studentDistributionItemsCount = Integer.valueOf(reader.readLine());
        while (0 < studentDistributionItemsCount) {
            int id = Integer.valueOf(reader.readLine());
            
            StudentDistribution distribution = new StudentDistribution();
            distribution.load(reader);
            
            //TODO: programs.put(id, distribution);
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
            
            Schedule availability = new Schedule();
            availability.load(reader);            
            
            studentAvailability.put(id, availability);
            --studentAvailabilityItemsCount;
        }
    
        addedSemesterSubjects = Integer.valueOf(reader.readLine());
        
        int semesterSubjectsCount = Integer.valueOf(reader.readLine());
        while (0 < semesterSubjectsCount) {
            int semesterSubjectID = Integer.valueOf(reader.readLine());
            
            Subject subject = new Subject();
            subject.load(reader);
            
            semesterSubjects.put(semesterSubjectID, subject);
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
        for (Entry<Integer, Schedule> entry : roomAvailability.entrySet()) {
            writer.write(String.valueOf(entry.getKey()));
            writer.newLine();
            
            entry.getValue().save(writer);
        }
        
        writer.write(String.valueOf(lecturerAvailability.size()));
        writer.newLine();
        for (Entry<Integer, Schedule> entry : lecturerAvailability.entrySet()) {
            writer.write(String.valueOf(entry.getKey()));
            writer.newLine();
            
            entry.getValue().save(writer);
        }
        
        writer.write(String.valueOf(addedSemesterLecturers));
        writer.newLine();
        
        writer.write(String.valueOf(semesterLecturers.size()));
        writer.newLine();
        for (Entry<Integer, LecturerData> entry : semesterLecturers.entrySet()) {
            writer.write(String.valueOf(entry.getKey()));
            writer.newLine();
            
            entry.getValue().save(writer); 
        }
        
        writer.write(String.valueOf(semesterLecturerAvailability.size()));
        writer.newLine();
        for (Entry<Integer, Schedule> entry : semesterLecturerAvailability.entrySet()) {
            writer.write(String.valueOf(entry.getKey()));
            writer.newLine();
            
            entry.getValue().save(writer);
        }
        
        writer.write(String.valueOf(programs.size()));
        writer.newLine();
        for (Entry<Program, StudentDistribution> entry : programs.entrySet()) {
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
        for (Entry<Integer, Schedule> entry : studentAvailability.entrySet()) {
            writer.write(String.valueOf(entry.getKey()));
            writer.newLine();
            
            entry.getValue().save(writer);
        }
        
        writer.write(String.valueOf(addedSemesterSubjects));
        writer.newLine();
        
        writer.write(String.valueOf(semesterSubjects.size()));
        writer.newLine();
        for (Entry<Integer, Subject> entry : semesterSubjects.entrySet()) {
            writer.write(String.valueOf(entry.getKey()));
            writer.newLine();
            
            entry.getValue().save(writer);
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
    
    /*** Room view ***/
    // Room ID to Schedule. // X
    private Map<Integer, Schedule> roomAvailability;
    
    // Room ID to a list of UniversityClass IDs // X
    private Map<Integer, List<Integer>> roomToClass;
    
    private Set<Room> rooms;
    
    /*** Lecturer view ***/
    // Lecturer ID to Schedule. // X
    private Map<Integer, Schedule> lecturerAvailability;
    
    // LecturerData ID to a list of UniversityClass IDs // X
    private Map<Integer, List<Integer>> lecturerToClass;
    
    // Count of the number of added semester lecturers. Helps for the unique ID system. // TODO
    private int addedSemesterLecturers;
    
    // Semester specific lecturers to LecturerID // TODO
    private Map<Integer, LecturerData> semesterLecturers;
    
    // Semester specific lecturer ID to Schedule. // TODO
    private Map<Integer, Schedule> semesterLecturerAvailability;
    
    private Set<Lecturer> lecturers;
    
    /*** Student view ***/
    // Program ID to StudentDistribution. // TODO
    private Map<Program, StudentDistribution> programs;
    
    // Group IDs to Schedule // X
    private Map<Integer, Schedule> studentAvailability;
    
    // Group IDs to UniversityClass ID // X
    private Map<Integer, List<Integer>> studentsToClass;
    
    // Student groups
    private Set<Group> groups;
    
    /*** Class view ***/
    // Count of the number of added semester subjects. Helps for the unique ID system.
    private int addedSemesterSubjects;
    // Semester specific subjects to SubjectID.
    private Map<Integer, Subject> semesterSubjects;
    // Semester specific subject ID to capacity.
    private Map<Integer, Integer> semesterSubjectCapacity;
    
    // UniversityClass ID to Room ID // X
    private Map<Integer, Integer> classToRoom;
    
    // UniversityClass ID to LecturerData ID // X
    private Map<Integer, List<Integer>> classToLecturer;
    
    // UniversityClass ID to Group IDs // X
    private Map<Integer, List<Integer>> classToStudents;
    
    // UniversityClass ID to Placement // X
    private Map<Integer, ClassPlacement> timetable;
    
    // Classes to place in the timetable
    private Set<UniversityClass> classes;
    
    // Add rooms for the semester
    
    // Specify room schedule and attributes
    
    // Add state lecturers for the semester
    
    // Specify lecturer schedule and attributes
    
    // Add semester lecturers for the semester (same place as other lecturers)
    
    // Specify semester lecturer schedule and attributes
    
    // Add programs for the semester
    
    // Specify student distributions for the programs to generate classes
    
    // Assign lecturers to classes and specify attributes for the class (by the lecturer)

    // Merge classes that would be taught together by the same teacher
    
    // Select a class and place it (room and time)
}
