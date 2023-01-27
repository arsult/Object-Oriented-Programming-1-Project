import java.util.ArrayList;

public class Schedule {

    private String fileName;
    private int studentLevel;
    private ArrayList<Course> courses;

    public Schedule(String fileName, int studentLevel, ArrayList<Course> courses) {
        this.fileName = fileName;
        this.studentLevel = studentLevel;
        this.courses = courses;
    }

    public Schedule() {

    }

    public String getFileName() {
        return fileName;
    }

    public void addSchedule() {

    }

    public ArrayList<Course> getCourses() {
        return courses;
    }

    public void addCourse(Course course) {
        this.courses.add(course);
    }

    // معادلة الانجليزي
    public void removeCourse(Course course) {
        this.courses.remove(course);
    }

    public String readSchedule() {
        return "";
    }

    public Schedule getBlock(String str) {
        return new Schedule();
    }

}