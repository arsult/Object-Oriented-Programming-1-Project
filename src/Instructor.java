import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Instructor {

    private String name;
    private String universityID;
    private String department;
    private Schedule schedule;
    private ArrayList<Course> currentCourses; // Instructor's Courses in current semester (level)

    public Instructor(String name, String universityID, String department, Schedule schedule, ArrayList<Course> currentCourses) {
        this.name = name;
        this.universityID = universityID;
        this.schedule = schedule;
        this.department = department;
        this.currentCourses = currentCourses;
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

    public ArrayList<Course> getCurrentCourses() {
        return currentCourses;
    }

    public static ArrayList<Instructor> setupInstructorsData(ArrayList<Course> courses) throws FileNotFoundException {
        ArrayList<Instructor> instructors = new ArrayList<>();
        File file = new File("Instructors.txt");
        Scanner scanner = new Scanner(file);


        while (scanner.hasNext()) {
            String line = scanner.nextLine();

            // Ignore comments on file.
            if (line.startsWith("//") || line.isBlank()) {
                continue;
            }

            String[] tokens = line.split(", ");
            String instructorName = tokens[0];
            String instructorID = tokens[1];
            String instructorDepartment = tokens[2];
            String instructorScheduleBlock = tokens[3];
            String[] coursesTaught = tokens[4].split("~");

            ArrayList<Course> instructorCourses = new ArrayList<>();
            for (String taught : coursesTaught) {
                for (Course course : courses) {
                    if (course.getCourseCode().equals(taught)) {
                        instructorCourses.add(course);
                    }
                }
            }

            instructors.add(new Instructor(instructorName, instructorID, instructorDepartment, new Schedule().getBlock(instructorScheduleBlock), instructorCourses));

        }
        if (instructors.isEmpty()) {
            System.out.println("instructors file is empty");
        }
        scanner.close();

        return instructors;
    }

    @Override
    public String toString() {
        return "Instructor Name: " + name + "\n"
                + "Instructor ID: " + universityID + "\n"
                + "Instructor Schedule: " + schedule.toString() + "\n"
                + "Instructor Department: " + department + "\n"
                + "Instructor Courses: " + currentCourses.toString() + "\n";
    }
}