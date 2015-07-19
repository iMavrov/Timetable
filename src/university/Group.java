/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package university;

import java.io.IOException;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.util.Set;
import java.util.HashSet;

/**
 *
 * @author Mavrov
 */
public class Group implements IPersistable {
    
    public Group() {
        program = null;
        
        year = 0;
        division = 0;
        group = 0;
        
        capacity = 0;
        
        classes = new HashSet<>();
    }
    
    public Group(Program groupProgram, int yearNumber, int divisionNumber, int groupNumber, int groupCapacity) {
        program = groupProgram;
        
        year = yearNumber;
        division = divisionNumber;
        group = groupNumber;
        
        capacity = groupCapacity;
        
        classes = new HashSet<>();
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
    
    public boolean assignClass(UniversityClass universityClass, AssignPolicy policy) {
        if (universityClass == null) {
            return false;
        }
        
        // TODO: Place class in schedule
        boolean isPlacedInSchedule = true;
        
        boolean isGroupAssigned = true;
        if (policy == AssignPolicy.BOTH_WAYS) {
            isGroupAssigned = universityClass.assignGroup(this, AssignPolicy.ONE_WAY);
        }
        
        boolean isClassAdded = classes.add(universityClass);
        
        return isPlacedInSchedule && isGroupAssigned && isClassAdded;
    }
    
    public boolean unassignClass(UniversityClass universityClass, AssignPolicy policy) {
        if (universityClass == null) {
            return false;
        }
        
        // TODO: Remove class from schedule
        boolean isRemovedFromSchedule = true;
        
        boolean isGroupUnassigned = true;
        if (policy == AssignPolicy.BOTH_WAYS) {
            isGroupUnassigned = universityClass.unassignGroup(this, AssignPolicy.ONE_WAY);
        }
        
        boolean isClassRemoved = classes.remove(universityClass);
        
        return isRemovedFromSchedule && isGroupUnassigned && isClassRemoved;
    }
    
    public boolean unassignAllClasses() {
        boolean areClassesUnassigned = true;
        
        for (UniversityClass universityClass : classes) {
            boolean isClassUnassigned = unassignClass(universityClass, AssignPolicy.BOTH_WAYS);
            areClassesUnassigned = areClassesUnassigned && isClassUnassigned;
        }
        
        return areClassesUnassigned;
    }
    
    private Program program;
    
    private int year;
    private int division;
    private int group;
    
    private int capacity;
    
    private Schedule schedule;
    private Set<UniversityClass> classes;
}
