import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws IOException {
        Course.setupCourses(); // Set up all courses

        Scanner scan = new Scanner(System.in);
        Student student = new Student();

        System.out.println();
        System.out.println("\t\t\t Welcome to UJ Courses and Schedule Project");
        System.out.println();
        System.out.print("Please enter a student ID number: ");

        String id;

        do {
            id = scan.nextLine();

            if (!validateStudentID(id)) {
                System.out.println();
                System.out.println("Invalid student ID");
                System.out.println("A student ID must be only 7 digits.");
                System.out.print("Please enter a student ID number: ");
            }

        } while (!validateStudentID(id));

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

            // Read student's data
            student.readData();

            System.out.println();
            System.out.println("This student exists in the database.");
            System.out.println("What do you wish to do with this student?");
            System.out.println();

            System.out.println("1. View Student Information");
//            System.out.println("2. Modify Student Information");
            System.out.println("2. Modify Student Schedule");
            System.out.println();

            System.out.print("Enter your choice: ");
            int selection = scan.nextInt();

            switch (selection) {

                case 1:

                    System.out.println();
                    System.out.println("\t\t\tShowing Student's Information");
                    System.out.println();

                    System.out.println(student);
                    System.out.println();


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

                    Scanner inputScanner = new Scanner(System.in);

                    switch (selection) {
                        case 1:
                            String courseCode;
                            Course course;
                            do {
                                System.out.print("Enter the course code (E.g CCCS-100): ");
                                courseCode = inputScanner.nextLine();
                                course = Course.findCourse(courseCode);

                                if (course == null) {
                                    System.out.println();
                                    System.out.println("This course does not exists");
                                    System.out.println("Please enter a valid course code");
                                }

                            } while (course == null);

                            System.out.println();
                            System.out.println("\t\t\tViewing Course Details");
                            System.out.println();

                            System.out.println(course);
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

    private static boolean validateStudentID(String ID) {
        if (ID.length() != 7) {
            return false;
        }
        for (int i = 0; i < ID.length(); i++) {
            if (!Character.isDigit(ID.charAt(i))){
                return false;
            }
        }
        return true;
    }

}
