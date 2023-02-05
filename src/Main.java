import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    public static ArrayList<Course> courses;

    public static void main(String[] args) throws IOException {

        // Setup courses
        courses = Course.setupCourses();

        Scanner scan = new Scanner(System.in);

        System.out.println();
        System.out.println("\t\t\t Welcome to UJ Courses and Schedule Project");
        System.out.println();
        System.out.print("Please enter a student ID number: ");

        String id = scan.nextLine();
        File file = new File(id + ".txt");

        if (!file.exists()) {

            System.out.println("This student does not exists in the database.");
            System.out.println("Do you wish to add this student into the database? (Yes / No)");

            String input = scan.nextLine();
            char answer = input.charAt(0);

            if (answer == 'Y' || answer == 'y') { // Proceed to add student

                addStudent(id);

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
                    System.out.println("\t\t\tShowing Student's Information\n\n");

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

    // Adopt the idea of creating a new file for each student.
    public static void addStudent(String studentID) throws IOException {
        PrintWriter printWriter = new PrintWriter(studentID + ".txt");


        Scanner scan = new Scanner(System.in);
        String name, level;

        System.out.println("Please enter the following student information: ");

        /*
        Apply User Input-Validation here such as.
        Student's ID must be 7 digits
        Students Schedule Block must exist (Show prompt)
        Students Level must be equivalent to the schedule block
         */
        System.out.print("1. Enter the student full name: ");
        name = scan.nextLine();

        System.out.print("2. Enter the student semester level (1 to 4): ");
        level = scan.nextLine();

        // Possible idea is to create a new file for each student.
        printWriter.println("# Student Information");

        printWriter.println(name + ", " + studentID + ", " + level);
        printWriter.println();

        printWriter.println("# Schedule");

        String block = Long.toString(Long.parseLong(studentID) % 2 + 1);

        File scheduleBlockFile = new File("ScheduleBlocks.txt");
        Scanner scheduleBlockScanner = new Scanner(scheduleBlockFile);

        while (scheduleBlockScanner.hasNext()) {
            String line = scheduleBlockScanner.nextLine();

            if (line.startsWith("//") || line.isBlank()) {
                continue;
            }

            if (line.equalsIgnoreCase("#ScheduleBlock_" + level + "_" + block)) {
                line = scheduleBlockScanner.nextLine();

                while (!line.startsWith("Thursday")) {
                    line = scheduleBlockScanner.nextLine();
                    printWriter.println(line);
                }

                break;
            }
        }

        printWriter.close();
        //scan.close(); Do not close this Scanner, Closing this scanner will result in closing the Input Stream which will result in a NoSuchElementException Error

        System.out.println("Student " + name + " with ID " + studentID + " has been successfully registered.");

    }

}
