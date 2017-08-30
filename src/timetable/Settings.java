package timetable;

/**
 *
 * @author Mavrov
 */

// TODO: Maybe load the settings from a properties file?
public class Settings {
    // Constants
    public static final int DAYS_PER_WEEK = 7;
    public static final int WORKDAYS_PER_WEEK = 5;
    
    public static final int HOURS_PER_DAY = 24;
    public static final int HOURS_PER_WEEK = DAYS_PER_WEEK * HOURS_PER_DAY;
    
    // Schedule settings    
    public static final int EARLIEST_CLASS_START = 7; // 7 - earliest class start
    public static final int LATEST_CLASS_END = 21;  // 21 - latest class end
    public static final int WORKHOURS_PER_DAY = LATEST_CLASS_END - EARLIEST_CLASS_START;
    
    // GA settings
    public static final int POPULATION_SIZE = 128;
    public static final float REPRODUCTION_RATE = 0.5f;
    public static final float MUTATION_RATE = 0.05f;
    public static final int FITNESS_THRESHOLD = 1000;
    
    public static final int OVERLAP_PENALTY = 10000;
    public static final int WEEKEND_PENALTY = 100;
    public static final int UNAVAILABLE_PENALTY = 10;
    public static final int QUESTIONABLE_PENALTY = 1;
    public static final int HOLE_PENALTY = 10;
    public static final int ROOM_SIZE_MISMATCH_PENALTY = 100;
    
    public static final int UNIVERSITY_DAY_PENALTY = 10;
    public static final int LONG_DAY_PENALTY = 10;
    public static final int SHORT_DAY_PENALTY = 10;
    
    // Random generator settings
    public static final boolean USE_DEBUG_SEED = true;
    public static final long DEBUG_SEED = 0xCAFEBABEL;
}
