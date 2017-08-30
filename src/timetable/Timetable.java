package timetable;

import java.util.Set;
import java.util.List;
import java.util.BitSet;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;
import java.util.TreeSet;

import utilities.Combinatorics;
import university.Room;
import university.UniversityClass;

/**
 *
 * @author Mavrov
 */
public class Timetable extends Speciment {
    
    // Note: Not thread safe!
    public static void initialize() {
        if (areStaticStructuresInitialized) {
            return;
        }
        
        // Group all semester classes' indices by duration.
        durationClassIndexMap = new HashMap<>();
        
        List<UniversityClass> universityClasses = Semester.getInstance().getClasses();
        for (UniversityClass universityClass : universityClasses) {
            int classIndex = universityClass.getIndex();
            int classDuration = universityClass.getDuration();
            List<Integer> sameDurationClasses = durationClassIndexMap.get(classDuration);
            if (sameDurationClasses != null) {
                sameDurationClasses.add(classIndex);
            } else {
                sameDurationClasses = new LinkedList<>();
                sameDurationClasses.add(classIndex);
                durationClassIndexMap.put(classDuration, sameDurationClasses);
            }
        }
        
        classDurations = new ArrayList<>(durationClassIndexMap.keySet());
        Collections.sort(classDurations);
        
        // Find all possible class duration combinations that perfectly fit in a day.
        perfectCombinations = Combinatorics.getPartitions(
            classDurations,
            Settings.WORKHOURS_PER_DAY
        );
        
        areStaticStructuresInitialized = true;
    }
    
    public Timetable() {
        super();
        
        initialize();
        
        // Copy the map as we are about to use it destructively.
        Map<Integer, List<Integer>> durationClassIndexMapCopy = new HashMap<>(durationClassIndexMap);
        
        // Copy the class duration combinations as well for the same reason.
        List<int[]> perfectCombinationsCopy = new ArrayList<>(perfectCombinations);
        
        int roomCount = University.getInstance().getRooms().size();
        
        int classStart = Settings.EARLIEST_CLASS_START;
        int classWeekday = 0;
        int classRoomIndex = 0;
        
        List<UniversityClass> universityClasses = Semester.getInstance().getClasses();
        placement = new ClassPlacement[universityClasses.size()];

        while (!perfectCombinationsCopy.isEmpty()) {
            // Pick a combination
            int combinationIndex = Generator.getInt(perfectCombinationsCopy.size());
            int[] durationHistogram = perfectCombinationsCopy.get(combinationIndex);
            
            // Verify we have enough classes to use this combination
            boolean isCombinationPossible = true;
            for (int durationIndex = 0; durationIndex < durationHistogram.length; ++durationIndex) {
                int duration = classDurations.get(durationIndex);
                int classCount = durationHistogram[durationIndex];
                if (durationClassIndexMapCopy.get(duration).size() < classCount) {
                    isCombinationPossible = false;
                    break;
                }
            }
            
            // We no longer have enough classes of the required durations for this combination.
            if (!isCombinationPossible) {
                // Note: We are doing remove on an ArrayList but it is done rarely.
                perfectCombinationsCopy.remove(combinationIndex);
                continue;
            }
            
            // Select class indices of specific durations according to the selected combination
            // to schedule tightly a whole day.
            List<Integer> selectedClassIndices = new ArrayList<>();
            for (int durationIndex = 0; durationIndex < durationHistogram.length; ++durationIndex) {
                int duration = classDurations.get(durationIndex);
                int classCount = durationHistogram[durationIndex];
                List<Integer> classIndices = durationClassIndexMapCopy.get(duration);
                while (0 < classCount) {
                    int randomIndex = Generator.getInt(classIndices.size());
                    int classIndex = classIndices.remove(randomIndex);
                    selectedClassIndices.add(classIndex);
                    --classCount;
                }
            }
            
            // Right now the selected class indices are sorted by duration.
            Collections.shuffle(selectedClassIndices);
            
            // Place the selected classes
            for (int selectedClassIndex : selectedClassIndices) {
                placement[selectedClassIndex] = new ClassPlacement(classRoomIndex, classStart);
                classStart += universityClasses.get(selectedClassIndex).getDuration();
            }
            
            // We know we've just filled in an entire day perfectly.
            // Start with the next work day.
            ++classWeekday;
            if (classWeekday == Settings.WORKDAYS_PER_WEEK) {
                // The week schedule for this room is all filled up.
                // Move to the next room.
                ++classRoomIndex;
                classWeekday = 0;
            }
            classStart = Settings.HOURS_PER_DAY * classWeekday + Settings.EARLIEST_CLASS_START;

            if (roomCount <= classRoomIndex) {
                throw new AssertionError("Not enough rooms to schedule all classes!");
            }
        }
        
        // All perfect combinations are depleted. We switch to a greedy solution.
        // Start placing classes longest first into the remaing rooms and days
        // while keeping track of the free slots left. Always try to fill a free slot first.
        // This set helps find appropriate slots in log time.
        TreeSet<FreeSlot> freeSlots = new TreeSet<>();
        
        // Add all remaining rooms and days as free whole day slots.
        // We have been putting perfect work day fitting combinations so only whole days are left.
        while (classRoomIndex < roomCount) {
            while (classWeekday < Settings.WORKDAYS_PER_WEEK) {
                freeSlots.add(
                    new FreeSlot(
                        classRoomIndex,
                        Settings.HOURS_PER_DAY * classWeekday + Settings.EARLIEST_CLASS_START,
                        Settings.WORKHOURS_PER_DAY
                    )
                );
                ++classWeekday;
            }
            ++classRoomIndex;
            classWeekday = 0;
        }
        
        ListIterator<Integer> reverseIterator = classDurations.listIterator(classDurations.size());
        while (reverseIterator.hasPrevious()) {
            Integer classDuration = reverseIterator.previous();
            List<Integer> sameDurationClassIndices = durationClassIndexMapCopy.get(classDuration);
            for (Integer classIndex : sameDurationClassIndices) {
                FreeSlot querySlot = new FreeSlot(0, Settings.EARLIEST_CLASS_START, classDuration);
                FreeSlot freeSlot = freeSlots.ceiling(querySlot);
                if (freeSlot != null) {
                    placement[classIndex] = new ClassPlacement(freeSlot.room, freeSlot.start);

                    int newFreeSlotLength = freeSlot.length - classDuration;
                    if (0 < newFreeSlotLength) {
                        FreeSlot newFreeSlot = new FreeSlot(
                            freeSlot.room, freeSlot.start + classDuration, 
                            newFreeSlotLength
                        );
                        freeSlots.add(newFreeSlot);
                    }
                } else {
                    throw new AssertionError("Not enough free slots to schedule all classes!");
                }
            }
        }
    }
    
    public ClassPlacement getClassPlacement(int classIndex) {
        return placement[classIndex];
    }
    
    public void fix(BitSet conflictMap) {        
        List<Room> rooms = University.getInstance().getRooms();
        
        for (Room room : rooms) {
            
            BitSet availability = new BitSet(Settings.HOURS_PER_WEEK);
            
            Set<UniversityClass> classes = room.getSchedule().getClasses();
            List<ClassEvent> classEvents = new ArrayList<>(2 * classes.size());
            
            for (UniversityClass universityClass : classes) {
                int classIndex = universityClass.getIndex();
                ClassPlacement classPlacement = placement[classIndex];
                
                availability.set(classPlacement.start, classPlacement.start + universityClass.getDuration());
                
                ClassEvent classStartEvent = new ClassEvent(
                    classIndex, 
                    ClassEventType.CLASS_START, 
                    classPlacement.start
                );
                classEvents.add(classStartEvent);
                
                ClassEvent classEndEvent = new ClassEvent(
                    classIndex, 
                    ClassEventType.CLASS_END, 
                    classPlacement.start + universityClass.getDuration()
                );
                classEvents.add(classEndEvent);
            }
            
            Collections.sort(classEvents);
            
            List<List<Integer>> classClusters = new ArrayList<>();

            int runningClasses = 0;
            List<Integer> classCluster = null;
            for (ClassEvent classEvent : classEvents) {
                if (classEvent.type == ClassEventType.CLASS_START) {
                    ++runningClasses;
                    if (classCluster == null) {
                        classCluster = new ArrayList<>();
                    }
                    classCluster.add(classEvent.placementIndex);
                } else {
                    --runningClasses;
                    if (runningClasses == 0) {
                        classClusters.add(classCluster);
                        classCluster = null;
                    }
                }
            }
        }
    }

    // Note: We are using this particular arrangement because we want 
    // the natural ordering of the events to end classes 
    // before starting new once (in the same hour slot).
    private enum ClassEventType {
        CLASS_END,
        CLASS_START,
    }
    
    private class ClassEvent implements Comparable<ClassEvent> {
        @Override
        public int compareTo(ClassEvent other) {
            int hourDifference = hour - other.hour;
            if (hourDifference == 0) {
                int typeDifference = type.compareTo(other.type);
                if (typeDifference == 0) {
                    // Note: We do not really care which class comes first
                    // as long as it is consistent.
                    int placementIndexDifference = placementIndex - other.placementIndex;
                    return placementIndexDifference;
                } else {
                    return typeDifference;
                }
            } else {
                return hourDifference;
            }
        }
        
        private ClassEvent(int inputPlacementIndex, ClassEventType inputType, int inputHour) {
            placementIndex = inputPlacementIndex;
            type = inputType;
            hour = inputHour;
        }
        
        private int placementIndex;
        private ClassEventType type;
        private int hour;
    }
    
    private class ClassCluster {
        
        private int startHour;
        private int endHour;
        private int requiredHours;
        private List<Integer> classIndex;
    }
    
    // Maps duration to a list of class indices with the same duration.
    private static Map<Integer, List<Integer>> durationClassIndexMap = null;
    
    // Lists all possible class duration combinations that perfectly fit in a day.
    private static List<int[]> perfectCombinations = null;
    
    // A sorted list of unique class durations.
    private static List<Integer> classDurations = null;
    
    // A flag telling us if we should populate the static data structures.
    private static boolean areStaticStructuresInitialized = false;
    
    private class FreeSlot implements Comparable<FreeSlot> {
        private int room;
        private int start;
        private int length;
        
        private FreeSlot(int inputRoom, int inputStart, int inputLength) {
            room = inputRoom;
            start = inputStart;
            length = inputLength;
        }
        
        @Override
        public boolean equals(Object o) {
            if (o == null) {
                return false;
            }
            
            if (o.getClass() != getClass()) {
                return false;
            }
            
            final FreeSlot other = (FreeSlot)o;
            return room == other.room && start == other.start && length == other.length;
        }

        @Override
        public int compareTo(FreeSlot other) {
            int lengthDifference = length - other.length;
            if (lengthDifference == 0) {
                int roomDifference = room - other.room;
                if (roomDifference == 0) {
                    int startDifference = start - other.start;
                    return startDifference;
                } else {
                    return roomDifference;
                }
            } else {
                return lengthDifference;
            }
        }
        
        @Override
        public int hashCode() {
            return Settings.HOURS_PER_WEEK * room + Settings.HOURS_PER_DAY * start + length;
        }
    }
    
    private ClassPlacement[] placement;
}
