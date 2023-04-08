package me.ted.courses;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;

/**
 * This class holds all the requirements for a course
 */

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Course {

    private String courseName;
    private String courseFaculty;
    private String courseDepartment;
    private String courseLevel;
    private String courseCode;
    private int courseCredits;

    public static ArrayList<Course> allCourses;

    public static void setupCourses() {
        allCourses = new ArrayList<>();

    }

    public static Course findCourse(String courseCode) {
        for (Course course : allCourses) {
            if (course.getCourseCode().equals(courseCode)) {
                return course;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return "Name: " + courseName + "\n"
                + "College: College of " + courseFaculty + "\n"
                + "Department: Department of " + courseDepartment + "\n"
                + "Level: " + courseLevel + "\n"
                + "Code: " + courseCode + "\n"
                + "Credits: " + courseCredits + "\n";
    }
}