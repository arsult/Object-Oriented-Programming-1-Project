import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Student {

    private String name;
    private String universityID;
    private Schedule schedule;
    private int level;
    private ArrayList<Course> currentCourses; // Student's Courses in current semester (level)

    public Student(String name, String universityID, Schedule schedule, int level) {
        this.name = name;
        this.universityID = universityID;
        this.schedule = schedule;
        this.level = level;
        this.currentCourses = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public String getUniversityID() {
        return universityID;
    }

    public Schedule getSchedule() {
        return schedule;
    }

    public int getLevel() {
        return level;
    }

    public ArrayList<Course> getCurrentCourses() {
        return currentCourses;
    }


    public static ArrayList<Student> setupStudentsData() throws FileNotFoundException {
        ArrayList<Student> students = new ArrayList<>();
        File file = new File("Students.txt");

        if (!file.exists()) {
            System.out.println("The Students.txt file does not exist!");
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
            String studentName = tokens[0];
            String studentID = tokens[1];
            String studentScheduleBlock = tokens[2];
            int studentLevel = Integer.parseInt(tokens[3]);

            students.add(new Student(studentName, studentID, new Schedule().getBlock(studentScheduleBlock), studentLevel));

        }
        if (students.isEmpty()) {
            System.out.println("Student file is empty");
        }

        scanner.close();
        return students;
    }


    @Override
    public String toString() {
        return "Student Name: " + name + "\n"
                + "Student ID: " + universityID + "\n"
                + "Student Schedule: " + schedule.toString() + "\n"
                + "Student Semester: " + level + "\n"
                + "Student Current Courses: " + currentCourses.toString() + "\n";
    }
}
