import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Schedule {

    private Course course;
    private String time;
    private String classroom;
    private String day;

    public Schedule(Course course, String time, String classroom, String day) {
        this.course = course;
        this.time = time;
        this.classroom = classroom;
        this.day = day;
    }

    public Schedule() {

    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getClassroom() {
        return classroom;
    }

    public void setClassroom(String classroom) {
        this.classroom = classroom;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public static ArrayList<Schedule> readSchedule(Student student) throws FileNotFoundException {
        ArrayList<Schedule> schedules = new ArrayList<>();

        File file = new File(student.getUniversityID() + ".txt");
        Scanner scan = new Scanner(file);
        String line;

        while (scan.hasNext()) {
            line = scan.nextLine();

            // Ignore any comments written in the file starting by // or a blank line or any student's information, since we are only looking for their schedule.
            if (line.startsWith("//") || line.isBlank() || line.startsWith("# Student Information")) {
                continue;
            }

            // We've found the students schedule, proceed to split it apart
            if (line.startsWith("# Schedule")) {

                while (!line.startsWith("Thursday")) {
                    line = scan.nextLine();

                    String day = line.split(": ")[0];

                    String[] splitter = line.split(": ");
                    String courses;

                    if (splitter.length > 1) { // Day is not off...
                        courses = splitter[1];
                    } else {
                        schedules.add(new Schedule(null, "", "", day)); // Day off
                        continue;
                    }

                    String[] courseData = courses.split(", ");

                    for (String tokenOfCourses : courseData) {

                        String courseCode = tokenOfCourses.substring(0, tokenOfCourses.indexOf("(")); // Take only the course code from the file
                        Course course = Course.findCourse(courseCode); // Find the general name of the course from the course code we've just taken
                        String time = tokenOfCourses.substring(tokenOfCourses.indexOf("(") + 1, tokenOfCourses.indexOf(")")); // Get the time between the ( )

                        schedules.add(new Schedule(course, time, "404", day));
                    }

                }

            }
        }


        return schedules;
    }

    public static void viewStudentSchedule(Student student, String day) throws FileNotFoundException {
        ArrayList<Schedule> studentSchedule = readSchedule(student);

        System.out.printf("%24s Showing Schedule for %s:\n\n", " ", day);
        System.out.printf("%s:%20s: %20s: %20s:\n", "Time", "Course", "Code", "Classroom");

        for (Schedule schedule : studentSchedule) {
            if (schedule.getDay().equalsIgnoreCase(day)) {

                String courseCode = schedule.getCourse().getCourseCode();
                String courseName = schedule.getCourse().getCourseName();
                String time = schedule.getTime();

                if (courseName.length() > 20) { // Cut the course short to only 20 words.
                    courseName = courseName.substring(0, 20);
                }

                // Designing-output.
                System.out.print(time + "    " + courseName + "");//4 spaces
                /*
                 Required for designing the correct output of the schedule, since the general name of the course varys in length
                 We need to find the amount of spaces required for the course-code to be in the correct column by using the provided equation below.
                 */
                int courseCodeIndention = 24 - courseName.length(); // 20 words -> only 4 indention, 19 words -> 5 indentions, etc...

                for (int indention = 0; indention < courseCodeIndention; indention++) {
                    System.out.print(" ");
                }

                System.out.print(courseCode + "            " + schedule.getClassroom() + "\n"); //12 spaces
            }
        }

    }


    @Override
    public String toString() {
        return (course != null ? course.getCourseCode() : "") + " " + time + " " + classroom + " " + day.toString() + " \n";
    }
}
