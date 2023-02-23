package me.ted.courses;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import me.ted.faculties.Department;
import me.ted.faculties.Faculty;

import java.util.ArrayList;
import java.util.Arrays;

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

        allCourses.addAll(Arrays.asList(

                // College of Computer Science and Engineering

                // Department of CS and AI
                new CourseBuilder("CCCS-100").credits(2).faculty(Faculty.COMPUTER_SCIENCE_AND_ENGINEERING).department(Department.COMPUTER_SCIENCE_AND_ARTIFICIAL_INTELLIGENCE).name("Introduction to Computer Science").build(),
                new CourseBuilder("CCCS-111").credits(3).faculty(Faculty.COMPUTER_SCIENCE_AND_ENGINEERING).department(Department.COMPUTER_SCIENCE_AND_ARTIFICIAL_INTELLIGENCE).name("Introduction to Programming").prerequisites("CCCS-100").build(),
                new CourseBuilder("CCCS-121").credits(3).faculty(Faculty.COMPUTER_SCIENCE_AND_ENGINEERING).department(Department.COMPUTER_SCIENCE_AND_ARTIFICIAL_INTELLIGENCE).name("Object Oriented Programming I").prerequisites("CCCS-111").build(),
                new CourseBuilder("CCCS-122").credits(3).faculty(Faculty.COMPUTER_SCIENCE_AND_ENGINEERING).department(Department.COMPUTER_SCIENCE_AND_ARTIFICIAL_INTELLIGENCE).name("Discrete Mathematics").prerequisites("SCMT-101").build(),

                // Department of Cyber-security
                new CourseBuilder("CCCY-112").credits(2).faculty(Faculty.COMPUTER_SCIENCE_AND_ENGINEERING).department(Department.CYBER_SECURITY).name("Computing Ethics").prerequisites("CCCS-100").build(),

                // Department of Information Technology
                new CourseBuilder("CCIT-113").credits(2).faculty(Faculty.COMPUTER_SCIENCE_AND_ENGINEERING).department(Department.INFORMATION_TECHNOLOGY).name("Technical Writing").prerequisites("ELPR-102").build(),

                // College of Science

                // Department of Mathematics
                new CourseBuilder("SCMT-101").credits(3).faculty(Faculty.SCIENCE).department(Department.MATHEMATICS).name("General Mathematics").build(),
                new CourseBuilder("SCMT-211").credits(3).faculty(Faculty.SCIENCE).department(Department.MATHEMATICS).name("Calculus II").prerequisites("SCMT-101").build(),
                new CourseBuilder("SCMT-221").credits(3).faculty(Faculty.SCIENCE).department(Department.MATHEMATICS).name("Linear Algebra").prerequisites("SCMT-101").build(),

                // Department of Physics
                new CourseBuilder("SCPH-101").credits(3).faculty(Faculty.SCIENCE).department(Department.PHYSICS).name("General Physics").build(),
                new CourseBuilder("SCPH-211").credits(4).faculty(Faculty.SCIENCE).department(Department.PHYSICS).name("General Physics II").prerequisites("SCPH-101").build(),

                // Department of Biology
                new CourseBuilder("SCBI-101").credits(3).faculty(Faculty.SCIENCE).department(Department.BIOLOGY).name("General Biology").build(),

                // Department of Chemistry
                new CourseBuilder("SCCH-101").credits(3).faculty(Faculty.SCIENCE).department(Department.CHEMISTRY).name("General Chemistry").build(),

                // Department of Statistics
                new CourseBuilder("SCST-210").credits(3).faculty(Faculty.SCIENCE).department(Department.STATISTICS).name("General Statistics").prerequisites("SCMT-101").build(),

                // College of Business

                // Department of Supply Chain Management
                new CourseBuilder("BCSC-100").credits(2).faculty(Faculty.BUSINESS).department(Department.SUPPLY_CHAIN_MANAGEMENT).name("Introduction to Logistics").build(),

                // College of Society

                // Department of what the fuck do you want
                new CourseBuilder("SEPS-100").credits(2).faculty(Faculty.SOCIETY).department(Department.OTHER).name("Academic University Skills").build(),


                // English Institute
                new CourseBuilder("ELPR-100").credits(3).faculty(Faculty.ENGLISH).department(Department.ENGLISH_LANGUAGE).name("English Language I").build(),
                new CourseBuilder("ELPR-101").credits(3).faculty(Faculty.ENGLISH).department(Department.ENGLISH_LANGUAGE).name("English Language II").build(),
                new CourseBuilder("ELPR-102").credits(3).faculty(Faculty.ENGLISH).department(Department.ENGLISH_LANGUAGE).name("English Language III").build(),

                // College of Languages
                new CourseBuilder("CLPR-100").credits(2).faculty(Faculty.LANGUAGES).department(Department.CHINESE_LANGUAGE).name("Chinese Language").build(),
                new CourseBuilder("CLAL-101").credits(3).faculty(Faculty.LANGUAGES).department(Department.ARABIC_LANGUAGE).name("Arabic Language I").build(),
                new CourseBuilder("CLAL-102").credits(3).faculty(Faculty.LANGUAGES).department(Department.ARABIC_LANGUAGE).name("Arabic Language II").build(),

                // College of Islamic Studies
                new CourseBuilder("ISLM-101").credits(3).faculty(Faculty.THE_HOLY_QURAN_AND_ISLAMIC_STUDIES).department(Department.ISLAMIC_STUDIES).name("Islamic Culture I").build(),
                new CourseBuilder("ISLM-102").credits(3).faculty(Faculty.THE_HOLY_QURAN_AND_ISLAMIC_STUDIES).department(Department.ISLAMIC_STUDIES).name("Islamic Culture II").build(),
                new CourseBuilder("ISLM-103").credits(3).faculty(Faculty.THE_HOLY_QURAN_AND_ISLAMIC_STUDIES).department(Department.ISLAMIC_STUDIES).name("Islamic Culture III").build()));

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