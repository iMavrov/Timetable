package university;

/**
 *
 * @author Mavrov
 */

import java.io.IOException;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.util.List;
import java.util.ArrayList;


public class StudentDistribution implements IPersistable {
    
    public StudentDistribution() {
        years = new ArrayList<>();
        capacity = 0;
    }
    
    public int getCapacity() {
        return capacity;
    }
    
    public int getYearCount() {
        return years.size();
    }
    
    public int getYearNumber(int yearIndex) {
        Year year = getYear(yearIndex);
        
        if (year == null) {
            return 0;
        }
        
        return year.year;
    }
    
    public int getYearCapacity(int yearIndex) {
        Year year = getYear(yearIndex);
        
        if (year == null) {
            return 0;
        }
        
        return year.capacity;
    }
    
    public int getDivisionCount(int yearIndex) {
        Year year = getYear(yearIndex);
        
        if (year == null) {
            return 0;
        }
        
        return year.divisions.size();
    }
    
    public int getDivisionNumber(int yearIndex, int divisionIndex) {
        Division division = getDivision(yearIndex, divisionIndex);
        
        if (division == null) {
            return 0;
        }
        
        return division.division;
    }
    
    public int getDivisionCapacity(int yearIndex, int divisionIndex) {
        Division division = getDivision(yearIndex, divisionIndex);
        
        if (division == null) {
            return 0;
        }
        
        return division.capacity;
    }
    
    public int getGroupCount(int yearIndex, int divisionIndex) {
        Division division = getDivision(yearIndex, divisionIndex);
        
        if (division == null) {
            return 0;
        }
        
        return division.groups.size();
    }
    
    public int getGroupNumber(int yearIndex, int divisionIndex, int groupIndex) {
        Group group = getGroup(yearIndex, divisionIndex, groupIndex);
        
        if (group == null) {
            return 0;
        }
        
        return group.group;
    }
    
    public int getGroupCapacity(int yearIndex, int divisionIndex, int groupIndex) {
        Group group = getGroup(yearIndex, divisionIndex, groupIndex);
        
        if (group == null) {
            return 0;
        }
        
        return group.capacity;
    }
    
    @Override
    public boolean load(BufferedReader reader) throws IOException {
        int yearCount = Integer.valueOf(reader.readLine());
        while (0 < yearCount) {
            Year newYear = new Year();
            
            int divisionCount = Integer.valueOf(reader.readLine());
            while (0 < divisionCount) {
                Division newDivision = new Division();
                
                int groupCount = Integer.valueOf(reader.readLine());
                while (0 < groupCount) {
                    Group newGroup = new Group();
                    newGroup.group = Integer.valueOf(reader.readLine());
                    newGroup.capacity = Integer.valueOf(reader.readLine());
                    
                    newDivision.groups.add(newGroup);
                    
                    --groupCount;
                }
                
                newDivision.division = Integer.valueOf(reader.readLine());
                newDivision.capacity = Integer.valueOf(reader.readLine());
                
                newYear.divisions.add(newDivision);
                
                --divisionCount;
            }
            
            newYear.year = Integer.valueOf(reader.readLine());
            newYear.capacity = Integer.valueOf(reader.readLine());
            
            years.add(newYear);
            
            --yearCount;
        }
        
        capacity = Integer.valueOf(reader.readLine());
        
        return true;
    }

    @Override
    public boolean save(BufferedWriter writer) throws IOException {
        writer.write(String.valueOf(years.size()));
        writer.newLine();
        
        for (Year year : years) {
            writer.write(String.valueOf(year.divisions.size()));
            writer.newLine();
            
            for (Division division : year.divisions) {
                writer.write(String.valueOf(division.groups.size()));
                writer.newLine();
                
                for (Group group : division.groups) {
                    writer.write(String.valueOf(group.group));
                    writer.newLine();
                    
                    writer.write(String.valueOf(group.capacity));
                    writer.newLine();
                }
                
                writer.write(String.valueOf(division.division));
                writer.newLine();
                
                writer.write(String.valueOf(division.capacity));
                writer.newLine();
            }
            
            writer.write(String.valueOf(year.year));
            writer.newLine();
            
            writer.write(String.valueOf(year.capacity));
            writer.newLine();
        }
        
        writer.write(String.valueOf(capacity));
        writer.newLine();
        
        return true;
    }
    
    private class Year {
        
        Year() {
            divisions = new ArrayList<>();
            year = 0;
            capacity = 0;
        }
        
        List<Division> divisions;
        int year;
        int capacity;
    }
    
    private class Division {
        
        public Division() {
            groups = new ArrayList<>();
            division = 0;
            capacity = 0;
        }
        
        List<Group> groups;
        int division;
        int capacity;
    }
    
    private class Group {
        
        public Group() {
            group = 0;
            capacity = 0;
        }
        
        int group;
        int capacity;
    }
    
    private List<Year> years;
    private int capacity;
    
    private Year getYear(int yearIndex) {
        if (yearIndex < 0 || years.size() <= yearIndex) {
            return null;
        }
        
        return years.get(yearIndex);
    }
    
    private Division getDivision(int yearIndex, int divisionIndex) {
        Year year = getYear(yearIndex);
        
        if (year == null) {
            return null;
        }
        
        if (divisionIndex < 0 || year.divisions.size() <= divisionIndex) {
            return null;
        }
        
        return year.divisions.get(divisionIndex);
    }
    
    private Group getGroup(int yearIndex, int divisionIndex, int groupIndex) {
        Division division = getDivision(yearIndex, divisionIndex);
        
        if (division == null) {
            return null;
        }
        
        if (groupIndex < 0 || division.groups.size() <= groupIndex) {
            return null;
        }
        
        return division.groups.get(groupIndex);
    }
}
