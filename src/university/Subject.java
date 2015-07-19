/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package university;

import java.io.IOException;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.util.Objects;

/**
 *
 * @author Mavrov
 */
public class Subject implements IPersistable, IKeyHolder {
    
    public Subject() {
        code = "";
        fullName = "";
        shortName = "";
        type = SubjectType.MANDATORY;
        departmentID = University.INVALID_ID;
        lectureHourCount = 0;
        seminarHourCount = 0;
        labHourCount = 0;
    }
    
    public Subject(
            String subjectCode,
            String subjectFullName,
            String subjectShortName,
            SubjectType subjectType,
            int subjectDepartmentID,
            int subjectLectureHours,
            int subjectSeminarHours,
            int subjectLabHours) {
        code = subjectCode;
        
        fullName = subjectFullName;
        shortName = subjectShortName;
    
        type = subjectType;
        departmentID = subjectDepartmentID;
        
        lectureHourCount = subjectLectureHours;
        seminarHourCount = subjectSeminarHours;
        labHourCount = subjectLabHours;
    }
    
    public Program getProgram() {
        return program;
    }
    
    public String getCode() {
        return code;
    }
    
    public void setCode(String subjectCode) {
        code = subjectCode;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String subjectFullName) {
        fullName = subjectFullName;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String subjectShortName) {
        shortName = subjectShortName;
    }

    public SubjectType getSubjectType() {
        return type;
    }

    public void setSubjectType(SubjectType subjectType) {
        type = subjectType;
    }

    public int getDepartment() {
        return departmentID;
    }

    public void setDepartment(int subjectDepartmentID) {
        departmentID = subjectDepartmentID;
    }

    public boolean hasClass(UniversityClassType classType) {
        boolean hasClass = false;
        
        switch (classType) {
            case LECTION: {
                hasClass = (0 < lectureHourCount);
                break;
            }
            case LABORATORY: {
                hasClass = (0 < labHourCount);
                break;
            }
            case SEMINAR: {
                hasClass = (0 < seminarHourCount);
                break;
            }
        }
        
        return hasClass;
    }
    
    public int getClassHourCount(UniversityClassType classType) {
        int hourCount = 0;
        
        switch (classType) {
            case LECTION: {
                hourCount = lectureHourCount;
                break;
            }
            case LABORATORY: {
                hourCount = lectureHourCount;
                break;
            }
            case SEMINAR: {
                hourCount = seminarHourCount;
                break;
            }
        }
        
        return hourCount;
    }

    public void setClassHourCount(UniversityClassType classType, int hourCount) {
        switch (classType) {
            case LECTION: {
                lectureHourCount = hourCount;
            }
            case LABORATORY: {
                labHourCount = hourCount;
            }
            case SEMINAR: {
                seminarHourCount = hourCount;
            }
        }
    }
    
    @Override
    public String toString() {
        return fullName;
    }
    
    @Override
    public boolean equals(Object o) {
        if (o == null) {
            return false;
        }
        
        if (getClass() != o.getClass()) {
            return false;
        }
        
        final Subject other = (Subject)o;
        return code.equals(other.code);
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 23 * hash + Objects.hashCode(this.code);
        return hash;
    }

    @Override
    public boolean load(BufferedReader reader) throws IOException {
        code = reader.readLine();
        
        fullName = reader.readLine();
        shortName = reader.readLine();
        
        type = SubjectType.valueOf(reader.readLine());
        departmentID = Integer.valueOf(reader.readLine());
        
        lectureHourCount = Integer.valueOf(reader.readLine());
        seminarHourCount = Integer.valueOf(reader.readLine());
        labHourCount = Integer.valueOf(reader.readLine());
        
        return true;
    }

    @Override
    public boolean save(BufferedWriter writer) throws IOException {
        writer.write(code);
        writer.newLine();
        
        writer.write(fullName);
        writer.newLine();
        writer.write(shortName);
        writer.newLine();
        
        writer.write(type.toString());
        writer.newLine();
        writer.write(String.valueOf(departmentID));
        writer.newLine();
        
        writer.write(String.valueOf(lectureHourCount));
        writer.newLine();
        writer.write(String.valueOf(seminarHourCount));
        writer.newLine();
        writer.write(String.valueOf(labHourCount));
        writer.newLine();
        
        return true;
    }
    
    @Override
    public boolean hasBadKey() {
        if (code == null) {
            return true;
        }
        
        if (code.isEmpty()) {
            return true;
        }
        
        return false;
    }
    
    
    
    // Parent program
    private Program program;
    private int semesterIndex;
    private int semesterSubjectIndex;
    
    // Subject data
    private String code;
    
    private String fullName;
    private String shortName;
    
    private SubjectType type;
    private int departmentID;
    
    private int lectureHourCount;
    private int seminarHourCount;
    private int labHourCount;
}
