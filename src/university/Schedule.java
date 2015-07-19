/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package university;

import java.io.IOException;
import java.io.BufferedReader;
import java.io.BufferedWriter;

/**
 *
 * @author Mavrov
 */
public class Schedule implements IPersistable {
    
    public static final int HOURS_PER_DAY = 24;
    public static final int DAYS_PER_WEEK = 7;
    public static final int HOURS_PER_WEEK = HOURS_PER_DAY * DAYS_PER_WEEK;
    
    public Schedule() {
        slots = new HourSlot[HOURS_PER_WEEK];
        for (int weekHour = 0; weekHour < HOURS_PER_WEEK; ++weekHour) {
            boolean isWeekend = 4 < (weekHour / HOURS_PER_DAY);
            slots[weekHour] = new HourSlot(isWeekend);
        }
    }
    
    public boolean getAvailability(int day, int hour) {
        return slots[HOURS_PER_DAY * day + hour].isAvailable;
    }
    
    public void setAvailability(int day, int hour, boolean isAvailable) {
        slots[HOURS_PER_DAY * day + hour].isAvailable = isAvailable;
    }
    
    public int getClass(int day, int hour) {
        return slots[HOURS_PER_DAY * day + hour].classID;
    }
    
    public void setClass(int day, int hour, int classID, int classDuration) {
        for (int hourIndex = 0; hourIndex < classDuration; ++hourIndex) {
            slots[HOURS_PER_DAY * day + hour + hourIndex].classID = classID;
        }
    }

    @Override
    public boolean load(BufferedReader reader) throws IOException {
        String slotsString = reader.readLine();
        String[] slotValues = slotsString.split(" ");
        
        for (int weekHour = 0; weekHour < HOURS_PER_WEEK; ++weekHour) {
            slots[weekHour].isAvailable = Boolean.valueOf(slotValues[2 * weekHour]);
            slots[weekHour].classID = Integer.valueOf(slotValues[2 * weekHour + 1]);
        }
        
        return true;
    }

    @Override
    public boolean save(BufferedWriter writer) throws IOException {
        for (int weekHour = 0; weekHour < HOURS_PER_WEEK; ++weekHour) {
            writer.write(String.valueOf(slots[weekHour].isAvailable) + " ");
            writer.write(String.valueOf(slots[weekHour].classID) + " ");
        }
        writer.newLine();
        
        return true;
    }
    
    private class HourSlot {
        
        private HourSlot(boolean isSlotAvailable) {
            isAvailable = isSlotAvailable;
            classID = University.INVALID_ID;
        }
        
        private boolean isAvailable;
        private int classID;
    }
    
    private HourSlot[] slots;
}
