import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Schedule {

    private String fileName;
    private int studentLevel;
    private ScheduleDays workingDays;

    public Schedule(String fileName, int studentLevel, ScheduleDays workingDays) {
        this.fileName = fileName;
        this.studentLevel = studentLevel;
        this.workingDays = workingDays;
    }

    public Schedule() {

    }

    public String getFileName() {
        return fileName;
    }

    public void addSchedule() {

    }

    public ScheduleDays getWorkingDays() {
        return workingDays;
    }

    public void viewStudentSchedule(String selectedDay) throws FileNotFoundException {
        File file = new File("ScheduleReader.txt");
        Scanner scan = new Scanner(file);
        String line;

        // Starting reading from the file
        while (scan.hasNext()) {
            line = scan.nextLine();

            // Ignore any comments written in the file starting by // or a blank line or any student's information, since we are only looking for their schedule.
            if (line.startsWith("//") || line.isBlank() || line.startsWith("#Information")) {
                continue;
            }

            // We've found the students schedule, proceed to split it apart
            if (line.startsWith("#Schedule")) {
                // Testing
                System.out.print("Enter a day to view the student's schedule: ");

                // Get all the working days from our enum class.
                for (ScheduleDays allDays : ScheduleDays.values()) {
                    String day = allDays.toString();

                    // If the requested day is available, then proceed
                    if (day.equalsIgnoreCase(selectedDay)) {

                        // Read-jumping from the file till we find our requested day from the user.
                        for (int i = 0; i < allDays.ordinal() + 1; i++) {
                            line = scan.nextLine();
                        }

                        // Designing-output.
                        System.out.printf("%24s Showing Schedule for %s:\n", " ", day);
                        System.out.printf("%s:%20s: %20s: %20s:\n", "Time", "Course", "Code", "Classroom");

                        String[] courseWithTime = line.split(": ")[1].split(", ");

                        for (String tokenOfCourses : courseWithTime) {

                            String courseCode = tokenOfCourses.substring(0, tokenOfCourses.indexOf("(")); // Take only the course code from the file
                            String course = Course.findCourse(courseCode).getCourseName(); // Find the general name of the course from the course code we've just taken

                            if (course.length() > 20) { // Cut the course short to only 20 words.
                                course = course.substring(0, 20);
                            }

                            String time = tokenOfCourses.substring(tokenOfCourses.indexOf("(") + 1, tokenOfCourses.indexOf(")")); // Get the time between the ( )

                            // Designing-output.
                            System.out.print(time + "    " + course + "");//4 spaces

                            /*
                            Required for designing the correct output of the schedule, since the general name of the course varys in length
                            We need to find the amount of spaces required for the course-code to be in the correct column by using the provided equation below.
                             */

                            int courseCodeIndention = 24 - course.length(); // 20 words -> only 4 indention, 19 words -> 5 indentions, etc...
                            for (int indention = 0; indention < courseCodeIndention; indention++){
                                System.out.print(" ");
                            }

                            System.out.print(courseCode + "            " + "404" + "\n"); //12 spaces
                        }
                    }
                }

                break;

            }

        }
    }

    public Schedule getBlock(String str) {
        return new Schedule();
    }

}

enum ScheduleDays {

    SUNDAY,
    MONDAY,
    TUESDAY,
    WEDNESDAY,
    THURSDAY;

    @Override
    public String toString() {
        return name().substring(0, 1).toUpperCase() + name().substring(1).toLowerCase();
    }
}