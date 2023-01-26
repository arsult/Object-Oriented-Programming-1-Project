import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {

        ArrayList<Course> courses = setupCourses();
        ArrayList<Student> students = setupStudentsData();

    }

    public static ArrayList<Course> setupCourses() throws FileNotFoundException {
        ArrayList<Course> courses = new ArrayList<>();
        File file = new File("Courses.txt");
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

            courses.add(new Course(courseName, courseFaculty, courseDepartment, courseLevel, courseCode));
        }

        scanner.close();
        return courses;
    }

    public static ArrayList<Student> setupStudentsData() throws FileNotFoundException {
        ArrayList<Student> students = new ArrayList<>();
        File file = new File("Students.txt");
        Scanner scanner = new Scanner(file);

        while (scanner.hasNext()) {
            String line = scanner.nextLine();

            // Ignore comments on file.
            if (line.startsWith("//") || line.isBlank()) {
                continue;
            }

            String[] tokens = line.split(", ");
            String studentName = tokens[0];
            String studentID = tokens[1];
            String studentScheduleBlock = tokens[2];
            int studentLevel = Integer.parseInt(tokens[3]);

            students.add(new Student(studentName, studentID, new Schedule().getBlock(studentScheduleBlock), studentLevel));

        }
        System.out.println(students.get(0));
        scanner.close();

        return students;
    }

}