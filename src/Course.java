import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

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

    public static ArrayList<Course> setupCourses() throws FileNotFoundException {
        allCourses = new ArrayList<>();

        File file = new File("Courses.txt");

        if (!file.exists()) {
            System.out.println("The Courses.txt file does not exist!");
            return new ArrayList<>();
        }

        Scanner scanner = new Scanner(file);

        while (scanner.hasNext()) {
            String line = scanner.nextLine();

            // Ignore comments on file.
            if (line.startsWith("//") || line.isBlank()) {
                continue;
            }

            String[] tokens = line.split(", ");
            String courseName = tokens[0];
            String courseFaculty = tokens[1];
            String courseDepartment = tokens[2];
            String courseLevel = tokens[3];
            String courseCode = tokens[4];
            int courseCredits = Integer.parseInt(tokens[5]);

            allCourses.add(new Course(courseName, courseFaculty, courseDepartment, courseLevel, courseCode, courseCredits));
        }

        if (allCourses.isEmpty()) {
            System.out.println("Courses file is empty");
        }

        System.out.println("Courses have been setup successfully with size of (" + allCourses.size() + ")");

        scanner.close();
        return allCourses;
    }

    public static Course findCourse(String courseCode) {
        for (Course course : allCourses) {
            if (course.getCourseCode().equals(courseCode)){
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