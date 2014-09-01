/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package timetable;

import java.util.Random;

import university.University;

/**
 *
 * @author Mavrov
 */
public class ClassPlacement {
    
    public ClassPlacement() {
        Random generator = Generator.getGenerator();
        day = (byte)generator.nextInt(Settings.STUDY_DAYS_PER_WEEK);
        hour = (byte)(Settings.EARLIEST_CLASS_START + generator.nextInt(Settings.CLASS_START_INTERVAL + 1));
        roomID = (byte)generator.nextInt(University.getInstance().getRoomCount());
    }
    
    private byte day;
    private byte hour;
    private short roomID;
    private boolean lock; // TODO: Maybe change to enum for state
}
