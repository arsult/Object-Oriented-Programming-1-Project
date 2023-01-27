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

        Scanner scanner = new Scanner(System.in);
        int choice;

        do {

            menu();
            choice = scanner.nextInt();

            switch(choice) {
                case 1: // Add a student

                    break;
                case 2: // Add a course

                    break;
                case 3: // Add a schedule block(?)

                    break;
                case 4: // View all students

                    break;
                case 5: // View all courses

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

        } while(choice != 7);

    }


    //albara add menu method 
    public static void menu(){

        System.out.println("1. Add a Student");
        System.out.println("2. Add a Course");
        System.out.println("3. Add a Schedule Block"); // may be removed
        System.out.println("4. View all Students");
        System.out.println("5. View all Courses");
        System.out.println("6. View all Schedule Blocks"); // may be removed
        System.out.println("7. Exit");
        System.out.print("Enter your choice: ");
    }

}
