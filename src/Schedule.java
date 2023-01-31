import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
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

    public String readSchedule() throws FileNotFoundException {
        File file = new File("ScheduleBlock_1.txt");
        Scanner scanner = new Scanner(file);
        int daysCounter = 0;
        while (scanner.hasNext()) {
            String line = scanner.nextLine();

            // Ignore comments on file.
            if (line.startsWith("//") || line.isBlank()) {
                continue;
            }

            ScheduleDays[] allDays = ScheduleDays.values();

            for (ScheduleDays currentDay : allDays) {
                String day = currentDay.toString();

                if (line.startsWith(day +": ")) {
                    System.out.println("Showing courses for day: " + day);
                    String[] courseWithTime = line.split(": ")[1].split(", ");

                    for (String lie : courseWithTime) {
                        String course = lie.substring(0, lie.indexOf("("));
                        String time = lie.substring(lie.indexOf("(") + 1, lie.indexOf(")")); // Get the time between the ( )
                        System.out.println(course + " Time: " + "from " + time.replace("-", " to "));
                    }

                }
            }
        }
        return "";
    }

    public Schedule getBlock(String str) {
        return new Schedule();
    }

}

// Rough idea
enum ScheduleDays {

    SUNDAY(new ArrayList<>()),
    MONDAY(new ArrayList<>()),
    TUESDAY(new ArrayList<>()),
    WEDNESDAY(new ArrayList<>()),
    THURSDAY(new ArrayList<>());

    private ArrayList<Course> coursesPerDay;

    ScheduleDays(ArrayList<Course> coursesPerDay) {
        this.coursesPerDay = coursesPerDay;
    }

    public ArrayList<Course> getCoursesPerDay() {
        return coursesPerDay;
    }

    @Override
    public String toString() {
        return name().substring(0, 1).toUpperCase() + name().substring(1).toLowerCase();
    }
}