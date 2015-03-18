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
public class Group implements IPersistable {
    
    public Group() {
        programID = University.INVALID_ID;
        
        year = 0;
        division = 0;
        group = 0;
        
        capacity = 0;
    }
    
    public Group(int program, int yearNumber, int divisionNumber, int groupNumber, int groupCapacity) {
        programID = program;
        
        year = yearNumber;
        division = divisionNumber;
        group = groupNumber;
        
        capacity = groupCapacity;
    }
    
    public int getProgramID() {
        return programID;
    }
    
    public void setProgramID(int newProgramID) {
        programID = newProgramID;
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
        return Integer.toString(programID) + ", " +
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
        return (programID == other.programID) && (year == other.year) && (division == other.division) && (group == other.group);
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 13 * hash + this.programID;
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
    
    private int programID;
    
    private int year;
    private int division;
    private int group;
    
    private int capacity;
}
