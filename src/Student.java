import java.util.ArrayList;

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

    @Override
    public String toString() {
        return "Student Name: " + name + "\n"
                + "Student ID: " + universityID + "\n"
                + "Student Schedule: " + schedule.toString() + "\n"
                + "Student Semester: " + level + "\n"
                + "Student Current Courses: " + currentCourses.toString() + "\n";
    }
}
