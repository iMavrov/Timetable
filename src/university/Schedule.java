/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package university;

import java.util.Set;
import java.util.HashSet;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.BufferedWriter;

/**
 *
 * @author Mavrov
 */

/// Note: Schedule is interested in class time inly. Not room placement!!!
public class Schedule implements IPersistable {
    
    public static final int HOURS_PER_DAY = 24;
    public static final int DAYS_PER_WEEK = 7;
    public static final int HOURS_PER_WEEK = HOURS_PER_DAY * DAYS_PER_WEEK;
    
    public static enum WeekDay {
        MONDAY,
        TUESDAY,
        WEDNESDAY,
        THURSDAY,
        FRIDAY,
        SATURDAY,
        SUNDAY;
        
        public static WeekDay getWeekDay(int weekHour) {
            weekHour = Math.max(0, weekHour);
            weekHour = Math.min(weekHour, HOURS_PER_WEEK - 1);
            
            int weekDay = weekHour / HOURS_PER_DAY;
             
            return values()[weekDay];
        }
        
        public boolean isWeekend() {
            return (this == SATURDAY) || (this == SUNDAY);
        }
    }
    
    public Schedule() {
        slots = new UniversityClass[HOURS_PER_WEEK];
        
        for (WeekDay weekDay: WeekDay.values()) {
            int weekHour = 0;
            
            if (weekDay.isWeekend()) {
                for (int dayHour = 0; dayHour < HOURS_PER_DAY; ++dayHour) {
                    slots[weekHour + dayHour] = UNAVAILABLE_SLOT;
                }
            } else {
                for (int dayHour = 0; dayHour < HOURS_PER_DAY; ++dayHour) {
                    slots[weekHour + dayHour] = AVAILABLE_SLOT;
                }
            }
            
            weekHour += HOURS_PER_DAY;
        }
        
        classes = new HashSet<>();
    }

    public boolean isEmpty() {
        return classes.isEmpty();
    }

    public boolean contains(UniversityClass universityClass) {
        if (universityClass == null) {
            return false;
        }
        
        if (universityClass.hasBadKey()) {
            return false;
        }
        
        return classes.contains(universityClass);
    }
    
    boolean canFit(UniversityClass universityClass) {
        if (contains(universityClass)) {
            return false;
        }
        
        if (universityClass.isPlaced()) {
            int startHour = universityClass.getStartHour();
            int duration = universityClass.getDuration();

            for (int classHour = 0; classHour < duration; ++classHour) {
                if (slots[startHour + classHour] != AVAILABLE_SLOT) {
                    return false;
                }
            }
        }

        return true;
    }

    public boolean add(UniversityClass universityClass) {
        if (!canFit(universityClass)) {
            return false;
        }
        
        if (universityClass.isPlaced()) {
            int startHour = universityClass.getStartHour();
            int duration = universityClass.getDuration();
            
            for (int classHour = 0; classHour < duration; ++classHour) {
                slots[startHour + classHour] = universityClass;
            }  
        }
        
        return classes.add(universityClass);
    }

    public boolean remove(UniversityClass universityClass) {
        if (!contains(universityClass)) {
            return false;
        }
        
        for (int slotIndex = 0; slotIndex < HOURS_PER_WEEK; ++slotIndex) {
            if (slots[slotIndex] != AVAILABLE_SLOT &&
                slots[slotIndex] != UNAVAILABLE_SLOT &&
                slots[slotIndex].equals(universityClass)) {
                slots[slotIndex] = 
                    WeekDay.getWeekDay(slotIndex).isWeekend() ? 
                    UNAVAILABLE_SLOT : AVAILABLE_SLOT;
            }
        }
        
        return classes.remove(universityClass);
    }
    
    public boolean update(UniversityClass universityClass) {
        boolean isOldClassRemoved = remove(universityClass);
        
        boolean isNewClassAdded = add(universityClass);
        
        return isOldClassRemoved && isNewClassAdded;
    }

    public void clear() {
        for (int slotIndex = 0; slotIndex < HOURS_PER_WEEK; ++slotIndex) {
            if (slots[slotIndex] != AVAILABLE_SLOT &&
                slots[slotIndex] != UNAVAILABLE_SLOT) {
                slots[slotIndex] = 
                    WeekDay.getWeekDay(slotIndex).isWeekend() ? 
                    UNAVAILABLE_SLOT : AVAILABLE_SLOT;
            }
        }
        
        classes.clear();
    }

    @Override
    public boolean load(BufferedReader reader) throws IOException {
        return false;
    }

    @Override
    public boolean save(BufferedWriter writer) throws IOException {
        return false;
    }
    
    public Set<UniversityClass> getClasses() {
        return classes;
    }
    
    public Availability getAvailability(int weekHourSlotIndex) {
        return availability[weekHourSlotIndex];
    }

    private static final UniversityClass AVAILABLE_SLOT = null;
    private static final UniversityClass UNAVAILABLE_SLOT = new UniversityClass();
    
    private UniversityClass[] slots;
    private Set<UniversityClass> classes;
    
    private Availability[] availability;
}
