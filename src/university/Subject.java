/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package university;

/**
 *
 * @author Mavrov
 */
public class Subject {
    
    public Subject() {
        subjectID = -1;
        fullName = "Предмет";
        shortName = "";
        yearIndex = -1;
        semesterIndex = -1;
        lectureHourCount = 0;
        seminarHourCount = 0;
        labHourCount = 0;
        subjectType = SubjectType.MANDATORY;
        department = new Department();
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public int getYearIndex() {
        return yearIndex;
    }

    public void setYearIndex(int yearIndex) {
        this.yearIndex = yearIndex;
    }

    public int getSemesterIndex() {
        return semesterIndex;
    }

    public void setSemesterIndex(int semesterIndex) {
        this.semesterIndex = semesterIndex;
    }

    public int getLectureHourCount() {
        return lectureHourCount;
    }

    public void setLectureHourCount(int lectureHourCount) {
        this.lectureHourCount = lectureHourCount;
    }

    public int getSeminarHourCount() {
        return seminarHourCount;
    }

    public void setSeminarHourCount(int seminarHourCount) {
        this.seminarHourCount = seminarHourCount;
    }

    public int getLabHourCount() {
        return labHourCount;
    }

    public void setLabHourCount(int labHourCount) {
        this.labHourCount = labHourCount;
    }

    public SubjectType getSubjectType() {
        return subjectType;
    }

    public void setSubjectType(SubjectType subjectType) {
        this.subjectType = subjectType;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }
    
    @Override
    public String toString() {
        return fullName;
    }
    
    private int subjectID;
    
    private String fullName;
    private String shortName;
    
    private int yearIndex;
    private int semesterIndex;
    
    private int lectureHourCount;
    private int seminarHourCount;
    private int labHourCount;
    
    private SubjectType subjectType;
    private Department department;
}
