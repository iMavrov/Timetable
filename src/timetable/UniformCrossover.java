package timetable;

import java.util.List;
import java.util.BitSet;
import java.util.ArrayList;
import utilities.RoomSlot;
import university.UniversityClass;

/**
 *
 * @author Mavrov
 */
public class UniformCrossover implements CrossoverOperator<Timetable> {

    @Override
    public boolean cross(Timetable parent0, Timetable parent1, Timetable child0, Timetable child1) {
        if (parent0 == null || parent1 == null || child0 == null || child1 == null) {
            return false;
        }
        
        List<UniversityClass> classes = Semester.getInstance().getClasses();
        int classCount = classes.size();
        
        BitSet conflictMap = new BitSet(classCount);
        List<RoomSlot> child0FreeSlots = new ArrayList<>(classCount / 2);
        List<RoomSlot> child1FreeSlots = new ArrayList<>(classCount / 2);
        
        for (int classIndex = 0; classIndex < classCount; ++classIndex) {
            UniversityClass universityClass = classes.get(classIndex);
            
            ClassPlacement parent0ClassPlacement = parent0.getClassPlacement(classIndex);
            ClassPlacement parent1ClassPlacement = parent1.getClassPlacement(classIndex);
            
            boolean keepClassPlacement = Generator.getBool();
            if (keepClassPlacement) {
                child0.setClassPlacement(classIndex, parent0ClassPlacement);
                child1.setClassPlacement(classIndex, parent1ClassPlacement);
            } else {
                conflictMap.set(classIndex);
                
                ClassPlacement child0OldPlacement = child0.getClassPlacement(classIndex);
                child0FreeSlots.add(new RoomSlot(child0OldPlacement.start, universityClass.getDuration(), child0OldPlacement.room));
                
                ClassPlacement child1OldPlacement = child1.getClassPlacement(classIndex);
                child1FreeSlots.add(new RoomSlot(child1OldPlacement.start, universityClass.getDuration(), child1OldPlacement.room));
                
                child0.setClassPlacement(classIndex, parent1ClassPlacement);
                child1.setClassPlacement(classIndex, parent0ClassPlacement);
            }
        }
        
        child0.fix(conflictMap, child0FreeSlots);
        child1.fix(conflictMap, child1FreeSlots);
        
        return true;
    }
}
