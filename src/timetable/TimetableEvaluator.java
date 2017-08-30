package timetable;

import java.util.BitSet;
import java.util.Set;
import java.util.List;
import university.Availability;

import university.Room;
import university.Group;
import university.Lecturer;
import university.UniversityClass;

/**
 *
 * @author Mavrov
 */
public class TimetableEvaluator implements FitnessEvaluator<Timetable> {

    @Override
    public int evaluateFitness(Timetable timetable) {
        int fitness = 0;
        
        // This evaluation approach is not suitable for performance applications.
        // We need to decode and keep the availability of all lecturers, students and rooms
        // in order to evaluate the timetable's fitness.
        // This is not only a memory issue. This is also a bad multithreading obstacle.

        /*
        // Evalute the timetable going through the classes' placements
        List<UniversityClass> classes = Semester.getInstance().getClasses();
        int classesCount = classes.size();
        
        for (int classIndex = 0; classIndex < classesCount; ++classIndex) {
            UniversityClass universityClass = classes.get(classIndex);
            ClassPlacement classPlacement = timetable.getClassPlacement(classIndex);
            
            int classStart = classPlacement.start;
            int classEnd = classStart + universityClass.getDuration();
            
            for (Lecturer lecturer : universityClass.getLecturers()) {
                
            }
        }
        */
        
        // Instead we could evalute the timetable from the view point of every scheduled entity.
        // That way we do not store the availability for all of them plus it is easily multithreadable.
        // Every scheduled entity can be considered as an agent evaluating its state.
        
        // Evaluate from lecturers' point of view
        List<Lecturer> lecturers = University.getInstance().getLecturers();
        for (Lecturer lecturer : lecturers) {
            BitSet occupied = new BitSet(Settings.HOURS_PER_WEEK);

            Set<UniversityClass> classes = lecturer.getSchedule().getClasses();
            for (UniversityClass universityClass : classes) {
                ClassPlacement classPlacement = timetable.getClassPlacement(universityClass.getIndex());
                int classStart = classPlacement.start;
                int classEnd = classStart + universityClass.getDuration();
                
                BitSet classTimeInterval = occupied.get(classStart, classEnd);
                // TODO: Remove the "if" entirely
                if (!classTimeInterval.isEmpty()) {
                    // Less overlap causes better fitness
                    fitness -= Settings.OVERLAP_PENALTY * classTimeInterval.cardinality();
                }

                occupied.set(classStart, classEnd);
                
                for (int hourIndex = 0; hourIndex < universityClass.getDuration(); ++hourIndex) {
                    Availability availability = lecturer.getSchedule().getAvailability(classStart + hourIndex);
                    switch (availability) {
                        case UNAVAILABLE: {
                            fitness -= Settings.UNAVAILABLE_PENALTY;
                            break;
                        }
                        case QUESTIONABLE: {
                            fitness -= Settings.QUESTIONABLE_PENALTY;
                            break;
                        }
                        case AVAILABLE: {
                            // No penalty
                            break;
                        }
                        default: {
                            // No penalty
                        }
                    }
                }
            }
            
            // Fewer university days
            int universityDays = 0;
            for (int dayIndex = 0; dayIndex < Settings.DAYS_PER_WEEK; ++dayIndex) {
                BitSet dayOccupation = occupied.get(dayIndex * Settings.HOURS_PER_DAY, (dayIndex + 1) * Settings.HOURS_PER_DAY);
                if (!dayOccupation.isEmpty()) {
                    ++universityDays;
                    
                    // Fewer holes during the university day
                    int firstClassStart = dayOccupation.nextSetBit(0);
                    int lastClassEnd = dayOccupation.previousSetBit(Settings.HOURS_PER_DAY - 1);
                    
                    BitSet classHourRange = dayOccupation.get(firstClassStart, lastClassEnd + 1);
                    int hourHoleCount = classHourRange.size() - classHourRange.cardinality();
                    
                    // One hole is OK - like a lunch break
                    if (1 < hourHoleCount) {
                        fitness -= (hourHoleCount - 1) * Settings.HOLE_PENALTY;
                    }
                    
                    if (dayOccupation.cardinality() < 4) {
                        fitness -= Settings.SHORT_DAY_PENALTY;
                    }
                    
                    // Too long day
                    if (8 < dayOccupation.cardinality()) {
                        fitness -= Settings.LONG_DAY_PENALTY;
                    }
                    
                }
            }
            
            fitness -= Settings.UNIVERSITY_DAY_PENALTY * universityDays;
        }
        
        // Evaluate from students' point of view
        List<Group> groups = University.getInstance().getGroups();
        for (Group group : groups) {
            BitSet occupied = new BitSet(Settings.HOURS_PER_WEEK);
            
            Set<UniversityClass> classes = group.getSchedule().getClasses();
            for (UniversityClass universityClass : classes) {
                ClassPlacement classPlacement = timetable.getClassPlacement(universityClass.getIndex());
                int classStart = classPlacement.start;
                int classEnd = classStart + universityClass.getDuration();
                        
                BitSet classTimeInterval = occupied.get(classStart, classEnd);
                if (!classTimeInterval.isEmpty()) {
                    fitness -= Settings.OVERLAP_PENALTY;
                }

                occupied.set(classStart, classEnd);
            }
            
            // TODO: Fewer university days
            // TODO: Not very long or very short university days
            // TODO: Have classes start immediately one after another
            // TODO: Allow a single hour slot break for the day
            // TODO: Leave weekend free (Bachelors only)
            // TODO: Have most of the lab and seminars in one day (Masters only)
        }
        
        // Evaluate from rooms' point of view
        List<Room> rooms = University.getInstance().getRooms();
        for (Room room : rooms) {
            BitSet occupied = new BitSet(Settings.HOURS_PER_WEEK);
            
            Set<UniversityClass> classes = room.getSchedule().getClasses();
            for (UniversityClass universityClass : classes) {
                ClassPlacement classPlacement = timetable.getClassPlacement(universityClass.getIndex());
                int classStart = classPlacement.start;
                int classEnd = classStart + universityClass.getDuration();
                        
                BitSet classTimeInterval = occupied.get(classStart, classEnd);
                if (!classTimeInterval.isEmpty()) {
                    fitness -= Settings.OVERLAP_PENALTY;
                }

                occupied.set(classStart, classEnd);
                
                // TODO: This is too strict. Just make sure that the room is not too big or too small
                int capacityMismatch = room.getCapacity() - universityClass.getCapacity();
                fitness -= Settings.ROOM_SIZE_MISMATCH_PENALTY * capacityMismatch;
                
                
            }
            
            // TODO: Make sure room working day hours are fully packed and defragmented
            // TODO: 
        }
        
        return fitness;
    }
}
