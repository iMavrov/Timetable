/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package university;

import utilities.AssignPolicy;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.util.Set;
import java.util.HashSet;
import utilities.IObservable;
import utilities.UpdateReason;

/**
 *
 * @author Mavrov
 */
public class Group implements IPersistable, IKeyHolder, IAttributeHolder, IClassObserver, IObservable {
    
    public Group() {
        program = null;
        
        year = 0;
        division = 0;
        group = 0;
        
        capacity = 0;
        
        schedule = new Schedule();
        classes = new HashSet<>();
    }
    
    public Group(Program groupProgram, int yearNumber, int divisionNumber, int groupNumber, int groupCapacity) {
        program = groupProgram;
        
        year = yearNumber;
        division = divisionNumber;
        group = groupNumber;
        
        capacity = groupCapacity;
        
        schedule = new Schedule();
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
    
    @Override
    public boolean hasBadKey() {
        // TODO:
        return true;
    }
    
    @Override
    public boolean hasAttribute(String attribute) {
        return attributes.contains(attribute);
    }

    @Override
    public boolean addAttribute(String attribute) {
        return attributes.add(attribute);
    }

    @Override
    public boolean removeAttribute(String attribute) {
        return attributes.remove(attribute);
    }
    
    @Override
    public boolean assign(UniversityClass universityClass, AssignPolicy policy) {
        if (universityClass == null) {
            return false;
        }
        
        /*
        if (universityClass.hasBadKey()) {
            return false;
        }
        */
        
        if (classes.contains(universityClass)) {
            return false;
        }

        // We are ok with the class so far. See if it is ok with us.
        boolean isGroupAssigned = true;
        if (policy == AssignPolicy.BOTH_WAYS) {
            isGroupAssigned = universityClass.assign(this, AssignPolicy.ONE_WAY);
        }
        
        boolean isClassAssigned = true;
        if (isGroupAssigned) {
            isClassAssigned = classes.add(universityClass);
            
            // TODO: Place in schedule
            boolean isPlacedInSchedule = true;
        }
        
        return isClassAssigned && isGroupAssigned;
    }
    
    @Override
    public boolean unassign(UniversityClass universityClass, AssignPolicy policy) {
        if (universityClass == null) {
            return false;
        }
        
        /*
        if (universityClass.hasBadKey()) {
            return false;
        }
        */
        
        if (!classes.contains(universityClass)) {
            return false;
        }
        
        // We are ok to remove the class so far. See if it is ok with us.    
        boolean isGroupUnassigned = true;
        if (policy == AssignPolicy.BOTH_WAYS) {
            isGroupUnassigned = universityClass.unassign(this, AssignPolicy.ONE_WAY);
        }
        
        boolean isClassUnassigned = true;
        if (isGroupUnassigned) {
            isClassUnassigned = classes.remove(universityClass);
            
            // TODO: Remove from schedule
            boolean isRemovedFromSchedule = true;
        }
        
        return isClassUnassigned && isGroupUnassigned;
    }
    
    @Override
    public boolean update(UniversityClass universityClass, UpdateReason reason) {
        // TODO:
        return true;
    }
    
    @Override
    public boolean unassignAllClasses() {
        boolean areClassesUnassigned = true;
        
        for (UniversityClass universityClass : classes) {
            boolean isClassUnassigned = unassign(universityClass, AssignPolicy.ONE_WAY);
            areClassesUnassigned = areClassesUnassigned && isClassUnassigned;
        }
        
        classes.clear();
        
        // TODO:
        //schedule.clear();
        
        return areClassesUnassigned;
    }
    
    // Parent program
    private Program program;
    private int year;
    private int division;
    private int group;
    
    private int capacity;
    
    // Extended room info
    Set<String> attributes;
    
    private Schedule schedule;
    
    private Set<UniversityClass> classes;
}
