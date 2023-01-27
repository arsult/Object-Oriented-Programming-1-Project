public class Course {

    private String courseName;
    private String courseFaculty;
    private String courseDepartment;
    private String courseLevel;
    private String courseCode;
    private int courseCredits;

    public Course(String courseName, String courseFaculty, String courseDepartment, String courseLevel, String courseCode, int courseCredits) {
        this.courseName = courseName;
        this.courseFaculty = courseFaculty;
        this.courseDepartment = courseDepartment;
        this.courseLevel = courseLevel;
        this.courseCode = courseCode;
        this.courseCredits = courseCredits;
    }

    public String getCourseName() {
        return courseName;
    }

    public String getCourseFaculty() {
        return courseFaculty;
    }

    public String getCourseDepartment() {
        return courseDepartment;
    }

    public String getCourseLevel() {
        return courseLevel;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public int getCourseCredits() {
        return courseCredits;
    }

    @Override
    public String toString() {
        return "Name: " + courseName + "\n"
                + "Faculty: " + courseFaculty + "\n"
                + "Department: " + courseDepartment + "\n"
                + "Level: " + courseLevel + "\n"
                + "Code: " + courseCode + "\n"
                + "Credits: " + courseCredits + "\n";
    }
}