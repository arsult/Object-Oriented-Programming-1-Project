import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    public static ArrayList<Course> courses;

    public static void main(String[] args) throws IOException {

        // Setup courses
        courses = Course.setupCourses();

        Scanner scan = new Scanner(System.in);
        Student student = new Student();

        System.out.println();
        System.out.println("\t\t\t Welcome to UJ Courses and Schedule Project");
        System.out.println();
        System.out.print("Please enter a student ID number: ");

        String id = scan.nextLine();
        File file = new File(id + ".txt");

        student.setUniversityID(id);

        if (!file.exists()) {

            System.out.println();
            System.out.println("This student does not exists in the database.");
            System.out.println("Do you wish to add this student into the database? (Yes / No)");

            String input = scan.nextLine();
            char answer = input.charAt(0);

            if (answer == 'Y' || answer == 'y') { // Proceed to add student

                System.out.println("Please enter the following student information: ");
                System.out.println();

                System.out.print("1. Enter the student full name: ");
                student.setName(scan.nextLine());

                System.out.print("2. Enter the student semester level (1 to 4): ");
                student.setLevel(scan.nextInt());

                student.saveData();

                System.out.println();
                System.out.println("Student " + student.getName() + " with ID " + student.getUniversityID() + " has been successfully registered.");

            } else { //No, or invalid input

                System.out.println("Exiting Program");
                System.exit(0);
            }

        } else { // Student does exist

            System.out.println();
            System.out.println("This student exists in the database.");
            System.out.println("What do you wish to do with this student?");
            System.out.println();

            System.out.println("1. View Student Information");
            System.out.println("2. Modify Student Schedule");
            System.out.println();

            System.out.print("Enter your choice: ");
            int selection = scan.nextInt();

            switch (selection) {

                case 1:

                    System.out.println();
                    System.out.println("\t\t\tShowing Student's Information");

                    System.out.println(student);
                 //   System.out.println(Schedule.readSchedule(student));
                    Schedule.viewStudentSchedule(student, "Thursday");

                    break;

                case 2:

                    System.out.println();
                    System.out.println("\t\t\tShowing Schedule\n\n");

                    System.out.println("Which command do you want to perform on this schedule?");
                    System.out.println("Currently the available commands are listed below");
                    System.out.println();

                    System.out.println("1. View course details");
                    System.out.println("2. Remove a course");
                    System.out.println("3. Go back");
                    System.out.println();

                    System.out.print("Enter your choice: ");
                    selection = scan.nextInt();

                    switch (selection) {
                        case 1:

                            break;
                        case 2:

                            break;

                        default:
                            System.out.println("Going back...");
                            break;
                    }
            }
        }
    }
}
