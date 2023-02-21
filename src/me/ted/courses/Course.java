package me.ted.courses;

import com.mongodb.client.MongoCollection;
import me.ted.database.Database;
import org.bson.Document;

import java.util.ArrayList;

/**
 * This class holds all the requirements for a course
 *
 */

public class Course {

    private String courseName;
    private String courseFaculty;
    private String courseDepartment;
    private String courseLevel;
    private String courseCode;
    private int courseCredits;

    public static ArrayList<Course> allCourses;

    public Course(String courseName, String courseFaculty, String courseDepartment, String courseLevel, String courseCode, int courseCredits) {
        this.courseName = courseName;
        this.courseFaculty = courseFaculty;
        this.courseDepartment = courseDepartment;
        this.courseLevel = courseLevel;
        this.courseCode = courseCode;
        this.courseCredits = courseCredits;
    }

    public Course() {
        this.courseName = "";
        this.courseFaculty = "";
        this.courseDepartment = "";
        this.courseLevel = "";
        this.courseCode = "";
        this.courseCredits = 0;
    }

    public String getCourseName() {
        return courseName;
    }

    public String getCourseFaculty() {
        return courseFaculty;
    }

    public String getCourseDepartment() {
        return courseDepartment;
    }

    public String getCourseLevel() {
        return courseLevel;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public int getCourseCredits() {
        return courseCredits;
    }

    /**
     * This method will read the all courses available in the database and add them to an arraylist
     * so we can have easy access to all the courses just through the arraylist without fetching the data
     * everytime we want to use it.
     */

    public static void setupCourses() {
        allCourses = new ArrayList<>();

        // Look for the collection that has the name Courses
        MongoCollection<Document> courseCollection = Database.getDatabase().getCollection("Courses");
        // And take only the first document and look at for the key that is named AllCourses
        String courses = courseCollection.find(new Document("_id", Database.coursesObjectId)).first().getString("AllCourses");

        // Split the values apart by a newline
        String[] token = courses.split("\n");

        for (String s : token) {
            // Split the courses data by a comma, so we can identify each component.
            String[] data = s.split(", ");

            String courseName = data[0]; // The element is always the general name of the course
            String courseFaculty = data[1]; // The second element is the college that is responsible for the course
            String courseDepartment = data[2]; // The third element is the department in the college that is responsible for the course
            String courseLevel = data[3]; // The fourth element is the level of the course
            String courseCode = data[4]; // The fifth element is the course-code which is a shortcut of the course name
            int courseCredits = Integer.parseInt(data[5]); // The sixth and last element is the credit hour of the course

            // Create a new course object containing all the data we obtained from the database and add them to the arraylist
            allCourses.add(new Course(courseName, courseFaculty, courseDepartment, courseLevel, courseCode, courseCredits));
        }

        // Prompt
        System.out.println("Courses have been setup successfully with size of (" + allCourses.size() + ")");
    }

    /**
     * This method takes a string (me.ted.courses.Course Code) and try to find the object of the course in the ArrayList mention above.
     *
     * @param courseCode, recieve the course code only
     * @return the object of the course from the ArrayList allCourses
     */
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