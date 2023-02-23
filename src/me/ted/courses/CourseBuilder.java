package me.ted.courses;

import lombok.Getter;
import me.ted.faculties.Department;
import me.ted.faculties.Faculty;

import java.util.ArrayList;
import java.util.Arrays;

@Getter
public class CourseBuilder {

    private String name;
    private String code;
    private String faculty;
    private String department;
    private int credits;
    private ArrayList<Course> prerequisites;

    public CourseBuilder(String code) {
        this.code = code;
        this.prerequisites = new ArrayList<>();
    }

    public CourseBuilder name(String name) {
        this.name = name;
        return this;
    }

    public CourseBuilder faculty(Faculty faculty) {
        this.faculty = faculty.getFancyName();
        return this;
    }

    public CourseBuilder department(Department department) {
        this.department = department.getFancyName();
        return this;
    }

    public CourseBuilder credits(int credits) {
        this.credits = credits;
        return this;
    }

    public CourseBuilder prerequisites(String courseCode) {
        this.prerequisites.add(Course.findCourse(courseCode));
        return this;
    }

    public CourseBuilder prerequisites(String... code) {
        this.prerequisites.addAll(Arrays.stream(code).map(Course::findCourse).toList());
        return this;
    }

    public Course build() {
        return new Course(name, faculty, department, code.split("-")[1], code, credits);
    }

}
