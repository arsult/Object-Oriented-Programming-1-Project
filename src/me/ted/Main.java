package me.ted;

import me.ted.courses.Course;
import me.ted.database.DataCollection;
import me.ted.database.Database;
import me.ted.person.student.Student;
import me.ted.schedules.Schedule;
import me.ted.utils.Timetable;
import org.bson.Document;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws ParseException {
        Database.connectDatabase(); // Establish connection to the database
        Database.setupDatabase();

        Course.setupCourses(); // Set up all courses

        Scanner scan = new Scanner(System.in);
        Student student = new Student();

        System.out.println();
        System.out.println("\t\t\t Welcome to UJ Courses and Schedule Project");
        System.out.println();

        String id;

        do {
            System.out.print("Please enter a student ID number: ");
            id = scan.nextLine();

            if (!validateStudentID(id)) {
                System.out.println();
                System.out.println("Invalid student ID");
                System.out.println("A student ID must be only 7 digits.");
            }

        } while (!validateStudentID(id));

        student.setUniversityID(id);

        if (!Database.studentDatabase.exists(student.getUniversityID())) {

            System.out.println();
            System.out.println("This student does not exists in the database.");
            System.out.println("Do you wish to add this student into the database? (Yes / No)");

            String input = scan.nextLine();
            char answer = input.charAt(0);

            if (answer == 'Y' || answer == 'y') {

                System.out.println("Please enter the following student information: ");
                System.out.println();

                System.out.print("1. Enter the student full name: ");
                student.setName(scan.nextLine());

                System.out.print("2. Enter the student current year (1 for preparatory year, 2 for general year in CCSE): ");
                student.setLevel(scan.nextInt());

                student.registerStudentData();

                System.out.println();
                System.out.println("Student " + student.getName() + " with ID " + student.getUniversityID() + " has been successfully registered.");

            } else {

                System.out.println("Exiting Program");
                System.exit(0);
            }

        } else {

            Schedule schedule = new Schedule(student);

            student.readStudentData();
            schedule.readSchedule();

            System.out.println();
            System.out.println("This student exists in the database.");
            System.out.println("What do you wish to do with this student?");
            System.out.println();


            int selection;

            do {
                System.out.println("1. View Student Information");
                System.out.println("2. Modify Student Schedule");
                System.out.println();
                System.out.print("Enter your choice: ");
                selection = scan.nextInt();

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
                        System.out.println("\t\t\t\t\t\tShowing Schedule\n");

                        student.viewStudentSchedule();

                        System.out.println();
                        System.out.println("Which command do you want to perform on this schedule?");
                        System.out.println("Currently the available commands are listed below");
                        System.out.println();

                        System.out.println("1. Add a course");
                        System.out.println("2. Remove a course");
                        System.out.println("3. View course details");
                        System.out.println("4. Exit");
                        System.out.println();

                        System.out.print("Enter your choice: ");
                        selection = scan.nextInt();

                        if (selection == 4) {
                            System.exit(0);
                        }

                        Scanner inputScanner = new Scanner(System.in);

                        String courseCode;
                        Course course;

                        do {
                            System.out.print("Enter the course code (e.g CCCS-121): ");
                            courseCode = inputScanner.nextLine();
                            course = Course.findCourse(courseCode);

                            if (course == null) {
                                System.out.println();
                                System.out.println("This course does not exists");
                                System.out.println("Please enter a valid course code");
                            }

                        } while (course == null);

                        switch (selection) {

                            case 1:

                                boolean add = false;

                                for (Schedule studentSchedule : student.getSchedule()) {
                                    if (studentSchedule.getCourse().getCourseCode().equalsIgnoreCase(courseCode)) {
                                        add = true;
                                        break;
                                    }
                                }

                                if (add) {
                                    System.out.println("This course is already in the schedule.");
                                    System.exit(0);
                                }

                                System.out.println();
                                System.out.println("Attempting to add the " + course.getCourseName() + " Course");
                                System.out.println();
                                System.out.println("Choose one of the following timetables for this course.");
                                System.out.println();

                                Timetable timetable = new Timetable();

                                timetable.printTimetable();
                                Document timeTables = Database.utilsDatabase.getDatabase().getCollection("Timetable").find(new Document("_id", Database.timeTableObjectId)).first();

                                /*

                                for (int i = 1; i < timeTables.size(); i++) {
                                    String days;
                                    if (i % 2 == 0) {
                                        days = "(S T R)";
                                    } else {
                                        days = "(M W)";
                                    }
                                    System.out.println(i + ". " + timeTables.getString(Integer.toString(i)) + " " + days);
                                }


                                 */
                                System.out.println();
                                System.out.print("Enter your choice: ");
                                Scanner scanner = new Scanner(System.in);
                                int choice = scanner.nextInt();

                                boolean allow = schedule.canAddCourse(choice);

                                if (allow) {
                                    if (student.calculateCumulativeCreditHours() + course.getCourseCredits() < 18) {

                                        int courseToAddTime = Integer.parseInt(timeTables.getString(Integer.toString(choice)).split(" - ")[0].split(":")[0]);
                                        int[] position = new int[Schedule.getDaysBasedOnIndex(choice).length];

                                        for (Schedule currentSchedule : student.getSchedule()) {
                                            for (int i = 0; i < Schedule.getDaysBasedOnIndex(choice).length; i++) {

                                                String days = Schedule.getDaysBasedOnIndex(choice)[i];

                                                if (currentSchedule.getDay().equalsIgnoreCase(days) && !currentSchedule.getTime().isEmpty()) {
                                                    int currentCourseTime = Integer.parseInt(currentSchedule.getTime().split(" - ")[0].split(":")[0]);

                                                    if (courseToAddTime > currentCourseTime) {
                                                        position[i]++;
                                                    }
                                                }
                                            }
                                        }

                                        ArrayList<String> duplicated = new ArrayList<>();

                                        for (int i = 0; i < student.getSchedule().size(); i++) {
                                            Schedule currentSchedule = student.getSchedule().get(i);

                                            for (int j = 0; j < Schedule.getDaysBasedOnIndex(choice).length; j++) {
                                                String days = Schedule.getDaysBasedOnIndex(choice)[j];

                                                if (duplicated.contains(days)) {
                                                    continue;
                                                }

                                                if (currentSchedule.getDay().equalsIgnoreCase(days)) {
                                                    student.getSchedule().add(position[j] + i, new Schedule(null, course, timeTables.getString(Integer.toString(choice)), "TBA", days));
                                                    duplicated.add(days);
                                                }
                                            }
                                        }

                                        schedule.updateSchedule(student.getSchedule()); // Update the student schedule and store it in the database.
                                        System.out.println(course.getCourseName() + " (" + courseCode + ") has been added to the schedule successfully!");
                                    } else {
                                        System.out.println("The maximum amount of hours in a schedule cannot execs 18 hours.");
                                    }
                                } else {
                                    System.out.println("This course cannot be added due to the fact that it conflicts with some courses.");
                                }

                                break;

                            case 2:

                                boolean remove = false;

                                for (Schedule studentSchedule : student.getSchedule()) {
                                    if (studentSchedule.getCourse().getCourseCode().equalsIgnoreCase(courseCode)) {
                                        remove = true;
                                        break;
                                    }
                                }

                                if (!remove) {
                                    System.out.println("This course is not in the schedule.");
                                    System.exit(0);
                                }

                                System.out.println();
                                System.out.println("Attempting to remove the " + course.getCourseName() + " Course");
                                System.out.println();

                                int creditHours = student.calculateCumulativeCreditHours() - course.getCourseCredits();

                                if (creditHours < 9) {
                                    System.out.println("You cannot remove this course, because the remaining hours on the schedule will be fewer than 9.");
                                } else {
                                    schedule.updateSchedule(schedule.removeCourse(courseCode));
                                    System.out.println(course.getCourseName() + " (" + courseCode + ") has been removed from the schedule successfully!");
                                }

                                break;

                            case 3:

                                System.out.println();
                                System.out.println("\t\t\tViewing Course Details");
                                System.out.println();
                                System.out.println(course);

                                break;

                            default:
                                break;
                        }
                }
            } while (selection != 4);
        }

    }

    private static boolean validateStudentID(String ID) {
        if (ID.length() != 7) {
            return false;
        }
        for (int i = 0; i < ID.length(); i++) {
            if (!Character.isDigit(ID.charAt(i))) {
                return false;
            }
        }
        return true;
    }

}
