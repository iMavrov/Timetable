/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package university;

/**
 *
 * @author Mavrov
 */
public enum FacultyType {
    FMI,
    FC,
    FPh,
    B2;
    
    public static String[] getFacultyTypeNames() {
        if (facultyNames == null) {
            facultyNames = new String[] {
                "ФМИ",
                "ХФ",
                "ФзФ",
                "Блок 2"            
            };
        }
        
        return facultyNames;
    }
    
    public static FacultyType getFacultyType(int index) {
        if (facultyTypes == null) {
            facultyTypes = FacultyType.values();
        }
        
        return facultyTypes[index];
    }
    
    private static String[] facultyNames = null;
    private static FacultyType[] facultyTypes = null;
}
