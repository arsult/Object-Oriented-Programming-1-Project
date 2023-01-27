import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    private static ArrayList<Course> courses;
    private static ArrayList<Student> students;
    private static ArrayList<Instructor> instructors;

    public static void main(String[] args) throws FileNotFoundException {

        // Retrieve Students, Courses Data from files
        courses = Course.setupCourses();
        students = Student.setupStudentsData();
        instructors = Instructor.setupInstructorsData(courses);

        /*
        Create a drop menu to
        1. Add a student -> Name, ID, ScheduleBlock
        2. Add a course
        3. Add a schedule block
        4. View all students
        5. View all courses
        6. View all schedule blocks
        7. Exit
         */

        menu();

    }


    //albara add menu method 
    public static void menu(){
        System.out.println("""
                1. Add a student
                2. Add a course
                3. Add a schedule block
                4. View all students
                5. View all courses
                6. View all schedule blocks
                7. Exit
                Enter your choice:\s""");
    }

}
