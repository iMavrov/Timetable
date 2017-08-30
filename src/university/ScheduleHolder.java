package university;

import java.util.Set;
import java.util.HashSet;
import utilities.SystemIDType;
import utilities.SystemObject;
import university.UniversityClass.AddGroupEvent;
import university.UniversityClass.AddLecturerEvent;
import university.UniversityClass.PlaceEvent;
import university.UniversityClass.DisplaceEvent;
import university.UniversityClass.RemoveGroupEvent;
import university.UniversityClass.RemoveLecturerEvent;


/**
 *
 * @author Mavrov
 */

public abstract class ScheduleHolder extends SystemObject implements IPersistable, IKeyHolder, IAttributeHolder, IClassObserver {
    
    public ScheduleHolder(SystemIDType systemObjectType) {
        super(systemObjectType);
        
        attributes = new HashSet<>();
        schedule = new Schedule();
        //classes = new HashSet<>();
    }
    
    public Schedule getSchedule() {
        return schedule;
    }
    
    // From IAttributeHolder
    @Override
    public boolean addAttribute(String attribute) {
        return attributes.add(attribute);
    }

    @Override
    public boolean removeAttribute(String attribute) {
        return attributes.remove(attribute);
    }
    
    @Override
    public boolean hasAttribute(String attribute) {
        return attributes.contains(attribute);
    }
    
    // From IClassObserver
    @Override
    public void onAddedLecturer(AddLecturerEvent event) {
    }
    
    @Override
    public void onRemovedLecturer(RemoveLecturerEvent event) {
    }
    
    @Override
    public void onAddedGroup(AddGroupEvent event) {
    }
    
    @Override
    public void onRemovedGroup(RemoveGroupEvent event) {
    }
    
    @Override
    public void onRoomPlacement(PlaceEvent event) {
        if (!event.keepsStartHour()) {
            UniversityClass universityClass = event.getUniversityClass();
            schedule.update(universityClass);
        }
    }
    
    @Override
    public void onRoomDisplacement(DisplaceEvent event) {
        UniversityClass universityClass = event.getUniversityClass();
        schedule.update(universityClass);
    }
    
    public abstract boolean removeClass(UniversityClass universityClass);
    
    public boolean removeClasses() {
        boolean areAllClassesRemoved = true;
        
        Set<UniversityClass> classes = schedule.getClasses();
        for (UniversityClass universityClass : classes) {
            boolean isClassRemoved = removeClass(universityClass);
            areAllClassesRemoved = areAllClassesRemoved && isClassRemoved;
        }
        
        return areAllClassesRemoved;
    }
    
    protected Set<String> attributes;
    
    protected Schedule schedule;
    
    // TODO: Place UniversityClass in the Schedule
    //protected Set<UniversityClass> classes;
}
