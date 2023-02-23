package me.ted.schedules;

import com.mongodb.client.MongoCollection;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import me.ted.Main;
import me.ted.courses.Course;
import me.ted.database.Database;
import me.ted.person.student.Student;
import me.ted.utils.Days;
import org.bson.Document;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Schedule {

    private Student student;
    private Course course;
    private String time;
    private String classroom;
    private String day;

    public Schedule(Student student) {
        this.student = student;
    }

    public ArrayList<Schedule> readSchedule() {
        ArrayList<Schedule> schedules = new ArrayList<>();

        MongoCollection<Document> collection = Database.studentDatabase.getDatabase().getCollection(student.getUniversityID());

        if (!Database.studentDatabase.exists(student.getUniversityID())) {
            System.out.println("This student does not exists...");
            return new ArrayList<>();
        }

        String studentSchedule = collection.find(new Document("_id", student.getUniversityID())).first().getString("Schedule");
        String[] lines = studentSchedule.split("\n");

        for (String line : lines) {

            String[] token = line.split(": ");
            String day = token[0];

            if (token.length == 1) {
                schedules.add(new Schedule(student, new Course(), "", "", day)); // Day off
                continue;
            }

            String[] daySchedule = token[1].split(", ");

            for (String courseData : daySchedule) {

                String courseCode = courseData.substring(0, courseData.indexOf("("));
                Course course = Course.findCourse(courseCode);
                String time_class = courseData.substring(courseData.indexOf("(") + 1, courseData.indexOf(")"));
                String time = time_class.split("_")[0];
                String classroom = time_class.split("_")[1];

                schedules.add(new Schedule(student, course, time, classroom, day));
            }

        }

        student.setSchedule(schedules);
        return schedules;
    }


    public void writeSchedule(String schedule) {
        MongoCollection<Document> studentCollection = Database.studentDatabase.getDatabase().getCollection(student.getUniversityID());

        Document header = new Document("_id", student.getUniversityID());
        Document key_value = new Document("Schedule", schedule);

        studentCollection.updateOne(header, new Document("$set", key_value));

    }


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

                    if (!time.isEmpty() && !classroom.isEmpty()) {
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
        ArrayList<Schedule> modifiedSchedule = new ArrayList<>(student.getSchedule());

        for (int i = 0; i < modifiedSchedule.size(); i++) {
            if (modifiedSchedule.get(i) != null && modifiedSchedule.get(i).getCourse() != null && modifiedSchedule.get(i).getCourse().getCourseCode().equalsIgnoreCase(courseCode)) {
                modifiedSchedule.remove(modifiedSchedule.get(i));
            }
        }
        return modifiedSchedule;
    }

    public boolean canAddCourse(int day) throws ParseException {

        boolean add = true;
        String[] daysToAdd = getDaysBasedOnIndex(day);

        Document timeTables = Database.utilsDatabase.getDatabase().getCollection("Timetable").find(new Document("_id", Database.timeTableObjectId)).first();
        String chosenTime = timeTables.getString(Integer.toString(day));

        for (Schedule schedule : student.getSchedule()) {
            for (String days : daysToAdd) {
                if (schedule.getDay().equalsIgnoreCase(days)) {
                    if (schedule.getTime() != null && isConflicting(chosenTime, schedule.getTime())) {
                        add = false;
                        break;
                    }
                }
            }
        }

        return add;

    }

    public static String[] getDaysBasedOnIndex(int day) {
        String[] daysToAdd = new String[3];

        if (day % 2 == 0) {
            daysToAdd[0] = Days.values()[0].toString(); // Sunday
            daysToAdd[1] = Days.values()[2].toString(); // Tuesday
            daysToAdd[2] = Days.values()[4].toString(); // Thursday
        } else {
            daysToAdd[0] = Days.values()[1].toString(); // Monday
            daysToAdd[1] = Days.values()[3].toString(); // Wednesday
        }
        return daysToAdd;
    }

    private boolean isConflicting(String time1, String time2) throws ParseException {

        if (time1.isEmpty() || time2.isEmpty()) {
            return false;
        }

        String startTimeHour1 = time1.split(" - ")[0].split(":")[0];
        String endTimeHour1 = time1.split(" - ")[1].split(":")[0];
        String startTimeMinutes1 = time1.split(" - ")[0].split(":")[1];
        String endTimeHourMinutes1 = time1.split(" - ")[1].split(":")[1];

        String startTimeHour2 = time2.split(" - ")[0].split(":")[0];
        String endTimeHour2 = time2.split(" - ")[1].split(":")[0];
        String startTimeMinutes2 = time2.split(" - ")[0].split(":")[1];
        String endTimeHourMinutes2 = time2.split(" - ")[1].split(":")[1];

        DateFormat dateFormat = new SimpleDateFormat("hh:mm");
        Date course1StartTime = dateFormat.parse(startTimeHour1 + ":" + startTimeMinutes1);
        Date course1EndTime = dateFormat.parse(endTimeHour1 + ":" + endTimeHourMinutes1);

        Date course2StartTime = dateFormat.parse(startTimeHour2 + ":" + startTimeMinutes2);
        Date course2EndTime = dateFormat.parse(endTimeHour2 + ":" + endTimeHourMinutes2);

        return isOverlapping(course1StartTime, course1EndTime, course2StartTime, course2EndTime);
    }


    private boolean isOverlapping(Date start1, Date end1, Date start2, Date end2) {
        boolean bStartsInA = start1.getTime() <= start2.getTime() && start2.getTime() <= end1.getTime();
        boolean bEndsInA = start1.getTime() <= end2.getTime() && end2.getTime() <= end1.getTime();
        boolean aInB = start2.getTime() < start1.getTime() && end1.getTime() < end2.getTime();

        return bStartsInA || bEndsInA || aInB;
    }

    @Override
    public String toString() {
        return course.getCourseCode() + " " + time + " " + classroom + " " + day + " \n";
    }


}
