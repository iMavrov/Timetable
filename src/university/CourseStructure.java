package university;

import java.io.IOException;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.util.List;
import java.util.ArrayList;

/**
 *
 * @author Mavrov
 */
public class CourseStructure implements IPersistable {
    
    public CourseStructure() {
        subjects = null;
    }
    
    public CourseStructure(List<List<Subject>> structureSubjects) {
        subjects = structureSubjects;
    }

    public List<List<Subject>> getSubjects() {
        return subjects;
    }

    public void setSubjects(List<List<Subject>> structureSubjects) {
        subjects = structureSubjects;
    }
    
    @Override
    public boolean save(BufferedWriter writer) throws IOException {
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
    
    private List<List<Subject>> subjects;
}
