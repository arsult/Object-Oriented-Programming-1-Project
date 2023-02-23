package me.ted.faculties;

import java.util.HashMap;
import java.util.Set;

public class FacultyBuilder {

    private Faculty faculty;
    private HashMap<String, Set<String>> department_majors;

    public FacultyBuilder(Faculty faculty) {
        this.faculty = faculty;
    }
    

}
