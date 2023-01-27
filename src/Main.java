import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    private static ArrayList<Course> courses;
    private static ArrayList<Student> students;
    private static ArrayList<Instructor> instructors;

    public static void main(String[] args) throws IOException {

        // Retrieve Students, Courses Data from files
        courses = Course.setupCourses();
        students = Student.setupStudentsData();
        instructors = Instructor.setupInstructorsData(courses);

        Scanner scanner = new Scanner(System.in);
        int choice;

        new Schedule().readSchedule();

        do {

            menu();
            choice = scanner.nextInt();

            switch (choice) {
                case 1: // Add a student
                    addStudent();
                    break;
                case 2: // Add a course

                    break;
                case 3: // Add a schedule block(?)

                    break;
                case 4: // View all students
                    viewStudents();
                    break;
                case 5: // View all courses
                    viewCourses();
                    break;
                case 6: // View all schedule blocks(?)

                    break;
                case 7: // Exit the program
                    System.out.println("Exiting program...");
                    System.exit(0);
                default: // Invalid input.
                    System.out.println();
                    System.out.println("Invalid input.");
                    System.out.println("Please select an option from the menu.");
                    System.out.println();
                    break;
            }

        } while (choice != 7);

    }

    //albara add menu method 
    public static void menu() {

        System.out.println("1. Add a Student");
        System.out.println("2. Add a Course");
        System.out.println("3. Add a Schedule Block"); // may be removed
        System.out.println("4. View all Students");
        System.out.println("5. View all Courses");
        System.out.println("6. View all Schedule Blocks"); // may be removed
        System.out.println("7. Exit");
        System.out.print("Enter your choice: ");
    }

    // Append to Students.txt file
    public static void addStudent() throws IOException {
        FileWriter fileWriter = new FileWriter("Students.txt", true);
        PrintWriter printWriter = new PrintWriter(fileWriter);

        Scanner scan = new Scanner(System.in);
        String name, ID, block, level;

        System.out.println("Enter the following Student Information Data: ");

        /*
        Apply User Input-Validation here such as.
        Student's ID must be 7 digits
        Students Schedule Block must exist (Show prompt)
        Students Level must be equivalent to the schedule block
         */
        System.out.print("Enter the Student's Name: ");
        name = scan.nextLine();
        System.out.print("Enter the Student's ID: ");
        ID = scan.nextLine();
        System.out.print("Enter the Student's Schedule Block: ");
        block = scan.nextLine();
        System.out.print("Enter the Student's Level: ");
        level = scan.nextLine();

        // Possible idea is to create a new file for each student.
        printWriter.println(name + ", " + ID + ", " + block + ", " + level);

        fileWriter.close();
        printWriter.close();
        //scan.close(); Do not close this Scanner, Closing this scanner will result in closing the Input Stream which will result in a NoSuchElementException Error

        System.out.println("Student " + name + " has been successfully registered.");

    }

    // Fetch courses from our ArrayList, No need to read the file
    public static void viewCourses() {
        System.out.println("Listing all the available courses");
        for (Course course : courses) {
            System.out.println(course.getCourseName() + " (" + course.getCourseCode() + ")");
        }
    }

    public static void viewStudents() {
        System.out.println("Listing all the available courses");

        for (Student student : students) {
            System.out.println(student);
            System.out.println("---------------");
        }

    }

}
