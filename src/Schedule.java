import com.mongodb.client.MongoCollection;
import org.bson.Document;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

/*
 * This class is designed to read the schedule from the ScheduleBlocks file or the student file
 * also aside from reading it can also display the Schedule of the student or from the ScheduleBlock file
 * With this class we are able to manipulate data and add commands we wish to do on a student's schedule as such
 *  Removing a Course or View a Course details easily.
 *
 */

public class Schedule {

    private Student student;
    private Course course;
    private String time;
    private String classroom;
    private String day;

    /**
     * Constructor
     *
     * @param student,   Takes an object of type student, so we are able to work with them.
     * @param course,    Takes an object of type course, so we are able to identify the data of the course we are working with
     * @param time,      Takes an object of type string that tell us the start and end time of the course
     * @param classroom, Takes an object of type string that tell us the classroom of the course in a specified day
     * @param day,       Takes an object of type string that tell us the day that this course is available on
     */
    public Schedule(Student student, Course course, String time, String classroom, String day) {
        this.student = student;
        this.course = course;
        this.time = time;
        this.classroom = classroom;
        this.day = day;
    }

    /**
     * Another constructor, This constructor's purpose is to use the methods related only to the student class.
     *
     * @param student Takes an object of type student.
     */
    public Schedule(Student student) {
        this.student = student;
    }

    /**
     * A default constructor
     */
    public Schedule() {
        this.student = null;
        this.course = null;
        this.time = null;
        this.classroom = null;
        this.day = null;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
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

    /**
     * This method reads the schedule that is stored on the database and create an array of schedules to fill it with the student schedule.
     *
     * @return An ArrayList containing the schedules of the student throughout the week.
     */

    public ArrayList<Schedule> readSchedule() {
        // Create an ArrayList so we are able to return the read schedule.
        ArrayList<Schedule> schedules = new ArrayList<>();

        // Look in the database for a collection that matches the student ID
        MongoCollection<Document> collection = Database.getDatabase().getCollection(student.getUniversityID());

        // If there is no collection by that ID then we do can return an empty ArrayList.
        if (!Database.exists(student.getUniversityID())) {
            System.out.println("This student does not exists...");
            return new ArrayList<>();
        }

        // Get the first document in the collection that has the ID of the student and look for their schedule key.
        String studentSchedule = collection.find(new Document("_id", student.getUniversityID())).first().getString("Schedule");
        // Separate the schedule days by a new line.
        String[] lines = studentSchedule.split("\n");

        // Loop through each line (which is each day) so we are able to tokenize and get our data.
        for (String line : lines) {

            // Tokenize by ": " so we can get the day and the courses in each day
            String[] token = line.split(": ");
            String day = token[0]; // The day in the line.

            // If the token size is 1 then that day is a break (e.g Thursday:  tokenizing by : will only return Thursday)
            if (token.length == 1) {
                schedules.add(new Schedule(student, new Course(), "", "", day)); // Day off
                continue;
            }

            // Split the courses by the comma
            String[] daySchedule = token[1].split(", ");

            // Loop through all the courses in the day
            for (String courseData : daySchedule) {

                String courseCode = courseData.substring(0, courseData.indexOf("(")); // Take only the course code from the file
                Course course = Course.findCourse(courseCode); // Find the general name of the course from the course code we've just taken
                String time_class = courseData.substring(courseData.indexOf("(") + 1, courseData.indexOf(")")); // Get the time and classroom between the ( )
                String time = time_class.split("_")[0]; // The time is the first element that is resulted from the split of _
                String classroom = time_class.split("_")[1]; // The classroom is the second element that is resulted from the split of _

                schedules.add(new Schedule(student, course, time, classroom, day)); // Add this schedule to the ArrayList
            }

        }

        // Set the student schedule to the one we have just read.
        student.setSchedule(schedules);
        return schedules;
    }

    /**
     * This method's purpose is to write the schedule on the database.
     *
     * @param schedule, receives the schedule as string so we can store it in the database without any issues.
     */
    public void writeSchedule(String schedule) {
        // Find the collection that matches the student ID
        MongoCollection<Document> studentCollection = Database.getDatabase().getCollection(student.getUniversityID());

        // We will use this "header" to find the document in the collection that also matches the university id of the student
        Document header = new Document("_id", student.getUniversityID());
        // We will try to look for the key name "Schedule" and insert the value "schedule" in it.
        Document key_value = new Document("Schedule", schedule);

        // This database command updates the already existing schedule of the student in the database.
        studentCollection.updateOne(header, new Document("$set", key_value));

    }

    /**
     * This method is called after we add a new course or remove an existing course from the schedule.
     * This method is important to update the schedule of the student with the additional changes made
     *
     * @param newSchedule, the new schedule that we want to store.
     */
    public void updateSchedule(ArrayList<Schedule> newSchedule) {
        String scheduleHeader = "", courseData = "";

        for (int i = 0; i < Days.values().length; i++) {
            Days day = Days.values()[i];

            scheduleHeader += day.name() + ": ";

            for (Schedule schedule : newSchedule) {
                if (schedule.getDay().equalsIgnoreCase(day.name())) {

                    String time = schedule.getTime();
                    String classroom = schedule.getClassroom();
                    Course course = schedule.getCourse();

                    if (time.isEmpty() && classroom.isEmpty()) { // The day is break, no need to do anything.
                        continue; // skip the iteration.
                    } else {
                        courseData += course.getCourseCode() + "(" + time + "_" + classroom + "), ";
                    }
                }

            }

            if (courseData.length() > 0) {
                courseData = courseData.substring(0, courseData.length() - 2);
            }

            scheduleHeader += courseData + "\n";
            courseData = "";
        }

        writeSchedule(scheduleHeader);

    }

    public ArrayList<Schedule> removeCourse(String courseCode) {
        ArrayList<Schedule> modifiedSchedule = new ArrayList<>(student.getSchedule()); // Copy students schedule into a new arraylist.

        // Loop through all the elements in the array
        for (int i = 0; i < modifiedSchedule.size(); i++) {
            if (modifiedSchedule.get(i) != null && modifiedSchedule.get(i).getCourse() != null && modifiedSchedule.get(i).getCourse().getCourseCode().equalsIgnoreCase(courseCode)) {
                modifiedSchedule.remove(modifiedSchedule.get(i)); // Remove the course wanted.
            }
        }
        return modifiedSchedule;
    }

    /**
     * This method concentrate on adding a course to the student schedule and see whether the chosen time is conflicting with the students schedule or not.
     *
     * @return True if there is no conflict, false if there is a conflict.
     */

    public boolean canAddCourse(int day) throws ParseException {

        boolean add = true; // the flag value to determine whether we can add the course or not.
        String[] daysToAdd = getDaysBasedOnIndex(day); // Get the days that the user has chosen depending on the chosen index

        // Get all the times available from the database
        Document timeTables = Database.getDatabase().getCollection("Timetable").find(new Document("_id", Database.timeTableObjectId)).first();
        String chosenTime = timeTables.getString(Integer.toString(day));

        // We are going to loop through the days and see whether they can fit in their schedule or not.
        for (Schedule schedule : student.getSchedule()) {
            for (String days : daysToAdd) {
                if (schedule.getDay().equalsIgnoreCase(days)) { // Include only the days chosen by the user
                    // Check whether the time of course actually exists (sometimes have breaks) and by using the isConflict method
                    // We can determine whether we fit the course in the schedule or not.
                    if (schedule.getTime() != null && isConflicting(chosenTime, schedule.getTime())) {
                        add = false; // We cannot add the course in the schedule, set the flag value to false
                        break; // leave the loop since we are finished.
                    }
                }
            }
        }

        return add;

    }

    /**
     * This method relays on what have been saved on the database on the document called Timetables.
     * We have set it up as any even number is for Sunday, Tuesday and Thursday
     * Otherwise its for Monday and Wednesday.
     *
     * @param day The index from 1 to 12
     * @return An array of strings containing the days
     */

    public static String[] getDaysBasedOnIndex(int day) {
        // Let the maximum days that can be held is 3 (Sunday, Tuesday, Thursday)
        String[] daysToAdd = new String[3];

        if (day % 2 == 0) { // If the user choose an even number, then it is one of the selected days below
            daysToAdd[0] = Days.values()[0].toString(); // Sunday
            daysToAdd[1] = Days.values()[2].toString(); // Tuesday
            daysToAdd[2] = Days.values()[4].toString(); // Thursday
        } else {// Otherwise
            daysToAdd[0] = Days.values()[1].toString(); // Monday
            daysToAdd[1] = Days.values()[3].toString(); // Wednesday
        }
        return daysToAdd;
    }

    private boolean isConflicting(String time1, String time2) throws ParseException {

        if (time1.isEmpty() || time2.isEmpty()) { // If one of the two times are empty (Day Break) we need not check whether they are overlapping or not.
            return false;
        }

        String startTimeHour1 = time1.split(" - ")[0].split(":")[0]; // Get the starting hour of the first course
        String endTimeHour1 = time1.split(" - ")[1].split(":")[0]; // Get the ending hour of the first course
        String startTimeMinutes1 = time1.split(" - ")[0].split(":")[1]; // Get the starting minutes of the first course
        String endTimeHourMinutes1 = time1.split(" - ")[1].split(":")[1]; // Get the ending minutes of the first course

        String startTimeHour2 = time2.split(" - ")[0].split(":")[0]; // Get the starting hours of the second course.
        String endTimeHour2 = time2.split(" - ")[1].split(":")[0];  // Get the ending hours of the second course
        String startTimeMinutes2 = time2.split(" - ")[0].split(":")[1]; // Get the starting minutes of the second course.
        String endTimeHourMinutes2 = time2.split(" - ")[1].split(":")[1]; // Get the ending minutes of the second course.

        DateFormat dateFormat = new SimpleDateFormat("hh:mm"); // Create a DateFormat object so we can work around the time and compare between times of the courses.
        Date course1StartTime = dateFormat.parse(startTimeHour1 + ":" + startTimeMinutes1); // Parse the starting hour and minutes for the first course so that we can get working time (e.g 10:00)
        Date course1EndTime = dateFormat.parse(endTimeHour1 + ":" + endTimeHourMinutes1); // Parse the ending hour and minutes for the first course so we can get a working time (e.g 10:50)

        Date course2StartTime = dateFormat.parse(startTimeHour2 + ":" + startTimeMinutes2); // Parse the starting hour and minutes for the second course so we can a working time (e.g 12:00)
        Date course2EndTime = dateFormat.parse(endTimeHour2 + ":" + endTimeHourMinutes2); // Parse the ending hour and minutes for the second course so we can get a working time (e.g 12:50)

        return isOverlapping(course1StartTime, course1EndTime, course2StartTime, course2EndTime); // This method returns a boolean and see if the two times that we have created are overlapping with each others or not.
    }

    /**
     * The idea behind this condition can be represented by a graph to see whether the timing of the two courses are overlapping or not.
     * Assume that we have a straight line that represents the starting point of the first course and ending point of the first and second course (Start time, End time)
     * (8:00)                        (8:50)
     * * ---------------------------- *
     *                      * ------------------------------- *
     *                   (8:30)                               (9:50)
     *
     * from this graph we can conclude that the time of the ending of the first course is greater than the start of the second course that is (end1 >= start2)
     * and the ending of the second course is greater than the start of the first course that is (end2 >= start1)
     * by that we can see that the two timings are overlapping between 8:30 and 8:50
     *
     * @param start1 The starting time of the first course
     * @param end1   The ending time of the first course
     * @param start2 THe starting time of the second course
     * @param end2   The ending time of the second course
     * @return true if they are overlapping, otherwise false.
     */
    private boolean isOverlapping(Date start1, Date end1, Date start2, Date end2) {
        return end1.getTime() >= start2.getTime() && end2.getTime() >= start1.getTime();
    }

    @Override
    public String toString() {
        return course.getCourseCode() + " " + time + " " + classroom + " " + day + " \n";
    }


}
