public class Course {

    private String courseName;
    private String courseFaculty;
    private String courseDepartment;
    private String courseLevel;
    private String courseShortcut;

    public Course(String courseName, String courseFaculty, String courseDepartment, String courseLevel, String courseShortcut) {
        this.courseName = courseName;
        this.courseFaculty = courseFaculty;
        this.courseDepartment = courseDepartment;
        this.courseLevel = courseLevel;
        this.courseShortcut = courseShortcut;
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

    public String getCourseShortcut() {
        return courseShortcut;
    }

    @Override
    public String toString() {
        return "Name: " + courseName + "\n"
                + "Faculty: " + courseFaculty + "\n"
                + "Department: " + courseDepartment + "\n"
                + "Level: " + courseLevel + "\n"
                + "Code: " + courseShortcut + "\n";
    }
}