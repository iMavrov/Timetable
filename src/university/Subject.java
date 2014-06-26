/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package university;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;

/**
 *
 * @author Mavrov
 */
public class Subject implements IPersistable {
    
    public Subject() {
        
    }
    
    public Subject(
            String subjectCode,
            String subjectFullName,
            String subjectShortName,
            SubjectType subjectType,
            String subjectDepartment,
            int subjectLectureHours,
            int subjectSeminarHours,
            int subjectLabHours) {
        code = subjectCode;
        
        fullName = subjectFullName;
        shortName = subjectShortName;
    
        type = subjectType;
        department = subjectDepartment;
        
        lectureHourCount = subjectLectureHours;
        seminarHourCount = subjectSeminarHours;
        labHourCount = subjectLabHours;
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

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String subjectDepartment) {
        department = subjectDepartment;
    }

    public int getLectureHourCount() {
        return lectureHourCount;
    }

    public void setLectureHourCount(int subjectLectureHourCount) {
        lectureHourCount = subjectLectureHourCount;
    }

    public int getSeminarHourCount() {
        return seminarHourCount;
    }

    public void setSeminarHourCount(int subjectSeminarHourCount) {
        seminarHourCount = subjectSeminarHourCount;
    }

    public int getLabHourCount() {
        return labHourCount;
    }

    public void setLabHourCount(int subjectLabHourCount) {
        labHourCount = subjectLabHourCount;
    }
    
    @Override
    public String toString() {
        return fullName;
    }
    
    @Override
    public boolean load(BufferedReader reader) throws IOException {
        code = reader.readLine();
        
        fullName = reader.readLine();
        shortName = reader.readLine();
        
        type = SubjectType.valueOf(reader.readLine());
        department = reader.readLine();
        
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
        writer.write(department);
        writer.newLine();
        
        writer.write(String.valueOf(lectureHourCount));
        writer.newLine();
        writer.write(String.valueOf(seminarHourCount));
        writer.newLine();
        writer.write(String.valueOf(labHourCount));
        writer.newLine();
        
        return true;
    }
    
    private String code;
    
    private String fullName;
    private String shortName;
    
    private SubjectType type;
    private String department;
    
    private int lectureHourCount;
    private int seminarHourCount;
    private int labHourCount;
}
