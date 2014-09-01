/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package university;

/**
 *
 * @author Mavrov
 */
public class Availability {
    
    public static final int HOURS_PER_DAY = 24;
    public static final int DAYS_PER_WEEK = 7;
    public static final int HOURS_PER_WEEK = HOURS_PER_DAY * DAYS_PER_WEEK;
    
    public Availability() {
        slots = new boolean[HOURS_PER_WEEK];
    }
    
    public boolean getAvailability(int day, int hour) {
        return slots[HOURS_PER_DAY * day + hour];
    }
    
    public void setAvailability(int day, int hour, boolean available) {
        slots[HOURS_PER_DAY * day + hour] = available;
    }
    
    private boolean[] slots;
}
