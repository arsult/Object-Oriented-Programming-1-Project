import java.util.ArrayList;

public class Instructor {


    private String name;
    private String universityID;
    private String department;
    private Schedule schedule;
    private ArrayList<Course> currentCourses; // Instructor's Courses in current semester (level)

    public Instructor(String name, String universityID, String department, Schedule schedule) {
        this.name = name;
        this.universityID = universityID;
        this.schedule = schedule;
        this.department = department;
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

    public ArrayList<Course> getCurrentCourses() {
        return currentCourses;
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