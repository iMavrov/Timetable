package university;

/**
 *
 * @author Mavrov
 */

import java.util.List;

public class StudentStructure {

    private static class Year {
        List<Division> divisions;
        int year;
        int capacity;
    }
    
    private static class Division {
        List<Group> groups;
        int division;
        int capacity;
    }
    
    private static class Group {
        List<Subgroup> subgroups;
        int group;
        int capacity;
    }
    
    private static class Subgroup {
        int id;
        int subgroup;
        int capacity;
    }
    
    private List<Year> years;
    private int capacity;
}
