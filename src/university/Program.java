package university;

import java.io.IOException;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.util.List;
import java.util.Objects;
import java.util.ArrayList;

/**
 *
 * @author Mavrov
 */
public class Program implements IPersistable, IKeyHolder {
    
    public Program() {
        degree = EducationDegree.BACHELOR;
        model = EducationModel.REGULAR;
        name = "";
        year = 0;
        subjects = null;
    }
    
    public Program(
            EducationDegree programDegree,
            EducationModel programModel,
            String programName,
            int programYear,
            int programDuration,
            List<List<Subject>> subjectsBySemesters) {
        degree = programDegree;
        model = programModel;
        name = programName;
        year = programYear;
        subjects = subjectsBySemesters;
    }
    
    public EducationDegree getDegree() {
        return degree;
    }

    public void setDegree(EducationDegree programDegree) {
        degree = programDegree;
    }

    public EducationModel getModel() {
        return model;
    }

    public void setModel(EducationModel programModel) {
        model = programModel;
    }

    public String getName() {
        return name;
    }

    public void setName(String programName) {
        name = programName;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int programYear) {
        year = programYear;
    }

    public int getSemesterCount() {
        if (subjects == null) {
            return 0;
        }
        
        return subjects.size();
    }

    public int getSemesterSubjectCount(int semesterIndex) {
        if (subjects == null) {
            return 0;
        }
        
        if (semesterIndex < 0 || subjects.size() <= semesterIndex) {
            return 0;
        }
        
        List<Subject> semesterSubjects = subjects.get(semesterIndex);
        
        if (semesterSubjects == null) {
            return 0;
        }
        
        return subjects.get(semesterIndex).size();
    }

    public Subject getSubject(int semesterIndex, int semesterSubjectIndex) {
        if (subjects == null) {
            return null;
        }
        
        if (semesterIndex < 0 || subjects.size() <= semesterIndex) {
            return null;
        }
        
        List<Subject> semesterSubjects = subjects.get(semesterIndex);
        
        if (semesterSubjects == null) {
            return null;
        }
        
        if (semesterSubjectIndex < 0 || semesterSubjects.size() <= semesterSubjectIndex) {
            return null;
        }
        
        return semesterSubjects.get(semesterSubjectIndex);
    }
    
    @Override
    public boolean equals(Object o) {
        if (o == null) {
            return false;
        }
        
        if (getClass() != o.getClass()) {
            return false;
        }
        
        final Program other = (Program)o;
        return (model == other.model) && name.equalsIgnoreCase(other.name) && (year == other.year);
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 47 * hash + Objects.hashCode(this.model);
        hash = 47 * hash + Objects.hashCode(this.name);
        hash = 47 * hash + this.year;
        return hash;
    }

    @Override
    public String toString() {
        return name + " " + String.valueOf(year) + " (" + model + ")";
    }
    
    @Override
    public boolean save(BufferedWriter writer) throws IOException {
        writer.write(String.valueOf(degree));
        writer.newLine();
        writer.write(String.valueOf(model));
        writer.newLine();
        writer.write(name);
        writer.newLine();
        writer.write(String.valueOf(year));
        writer.newLine();
        
        writer.write(String.valueOf(subjects.size()));
        writer.newLine();
        
        for (List<Subject> semesterSubjects : subjects) {
            writer.write(String.valueOf(semesterSubjects.size()));
            writer.newLine();
            
            for (Subject subject : semesterSubjects) {
                subject.save(writer);
            }
        }
        
        return true;
    }
    
    @Override
    public boolean load(BufferedReader reader) throws IOException {
        degree = EducationDegree.valueOf(reader.readLine());
        model = EducationModel.valueOf(reader.readLine());
        name = reader.readLine();
        year = Integer.valueOf(reader.readLine());
        
        int semesterCount = Integer.valueOf(reader.readLine());
        while (semesterCount > 0) {
            List<Subject> semesterSubjects = new ArrayList<>();
            
            int subjectCount = Integer.valueOf(reader.readLine());
            while (subjectCount > 0) {
                Subject newSubject = new Subject();
                newSubject.load(reader);
                semesterSubjects.add(newSubject);
                
                --subjectCount;
            }
            
            subjects.add(semesterSubjects);
                    
            --semesterCount;
        }
        
        return true;
    }
    
    @Override
    public boolean hasBadKey() {
        return name.isEmpty() || (year == 0);
    }
    
    private EducationDegree degree;       // Bachelor, Master
    private EducationModel model;         // Regular, Offsite, Distance
    
    private String name;                  // Computer Science
    private int year;                     // 2014
    
    private List<List<Subject>> subjects; // Subjects by semesters
}
