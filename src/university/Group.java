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
public class Group extends ScheduleHolder {
    
    public Group() {
        super();
        
        program = null;
        
        year = 0;
        division = 0;
        group = 0;
        
        capacity = 0;
    }
    
    public Group(Program groupProgram, int yearNumber, int divisionNumber, int groupNumber, int groupCapacity) {
        super();
        
        program = groupProgram;
        
        year = yearNumber;
        division = divisionNumber;
        group = groupNumber;
        
        capacity = groupCapacity;
    }
    
    public Program getProgram() {
        return program;
    }
    
    public void setProgram(Program newProgram) {
        program = newProgram;
    }
    
    public int getYear() {
        return year;
    }
    
    public void setYear(int newYear) {
        year = newYear;
    }
    
    public int getDivision() {
        return division;
    }
    
    public void setDivision(int newDivision) {
        division = newDivision;
    }
    
    public int getGroup() {
        return group;
    }
    
    public void setGroup(int newGroup) {
        group = newGroup;
    }
    
    public int getCapacity() {
        return capacity;
    }
    
    public void setCapacity(int newCapacity) {
        capacity = newCapacity;
    }
    
    @Override
    public String toString() {
        return //TODO: Integer.toString(program.getID()) + ", " +
               Integer.toString(year) + ", " +
               Integer.toString(division) + ", " +
               Integer.toString(group);
    }
    
    @Override
    public boolean equals(Object o) {
        if (o == null) {
            return false;
        }
        
        if (getClass() != o.getClass()) {
            return false;
        }
        
        final Group other = (Group)o;
        return (program == other.program) && (year == other.year) && (division == other.division) && (group == other.group);
    }

    @Override
    public int hashCode() {
        int hash = 7;
        // TODO: hash = 13 * hash + this.programID;
        hash = 13 * hash + this.year;
        hash = 13 * hash + this.division;
        hash = 13 * hash + this.group;
        return hash;
    }
    
    @Override
    public boolean load(BufferedReader reader) throws IOException {
        return true;
    }

    @Override
    public boolean save(BufferedWriter writer) throws IOException {
        return true;
    }
    
    @Override
    public boolean hasBadKey() {
        // TODO:
        return true;
    }
    
    // From ScheduleHolder
    @Override
    public void onAddedGroup(UniversityClass.AddGroupEvent event) {
        Group studentGroup = event.getGroup();
        if (equals(studentGroup)) {
            UniversityClass universityClass = event.getUniversityClass();
            schedule.add(universityClass);
        }
    }
    
    @Override
    public void onRemovedGroup(UniversityClass.RemoveGroupEvent event) {
        Group studentGroup = event.getGroup();
        if (equals(studentGroup)) {
            UniversityClass universityClass = event.getUniversityClass();
            schedule.remove(universityClass);
        }
    }
    
    public boolean addClass(UniversityClass universityClass) {
        if (universityClass == null) {
            return false;
        }
        
        if (universityClass.hasBadKey()) {
            return false;
        }
        
        if (schedule.contains(universityClass)) {
            return false;
        }
        
        return universityClass.addGroup(this);
    }
    
    @Override
    public boolean removeClass(UniversityClass universityClass) {
        if (universityClass == null) {
            return false;
        }
        
        if (universityClass.hasBadKey()) {
            return false;
        }
        
        if (!schedule.contains(universityClass)) {
            return false;
        }
        
        return universityClass.removeGroup(this);
    }
    
    // Parent program
    private Program program;
    private int year;
    private int division;
    private int group;
    
    private int capacity;
}
