package me.ted.person.student;

import com.mongodb.client.MongoCollection;
import me.ted.Main;
import me.ted.courses.Course;
import me.ted.database.Database;
import me.ted.schedules.Schedule;
import me.ted.utils.Days;
import org.bson.Document;

import java.util.ArrayList;

public class Student {

    private String name;
    private String universityID;
    private int level;
    private ArrayList<Schedule> schedule;

    public Student(String name, String universityID, int level, ArrayList<Schedule> schedule) {
        this.name = name;
        this.universityID = universityID;
        this.level = level;
        this.schedule = schedule;
    }

    public Student() {
        this.name = null;
        this.universityID = null;
        this.level = 0;
        this.schedule = null;
    }

    public void setName(String name) {
        this.name = name;
    }


    public void setLevel(int level) {
        this.level = level;
    }


    public void setUniversityID(String universityID) {
        this.universityID = universityID;
    }


    public String getName() {
        return name;
    }


    public int getLevel() {
        return level;
    }


    public String getUniversityID() {
        return universityID;
    }


    public ArrayList<Schedule> getSchedule() {
        return schedule;
    }

    public void setSchedule(ArrayList<Schedule> schedule) {
        this.schedule = schedule;
    }


    public int calculateCumulativeCreditHours() {
        int total = 0;
        ArrayList<Course> duplicates = new ArrayList<>();

        for (Schedule studentSchedule : schedule) {
            if (duplicates.contains(studentSchedule.getCourse())) {
                continue;
            }
            duplicates.add(studentSchedule.getCourse());
            total += studentSchedule.getCourse().getCourseCredits();
        }
        return total;
    }


    public void registerStudentData() {
        Schedule schedule = new Schedule(this);

        MongoCollection<Document> studentCollection = Database.studentDatabase.getDatabase().getCollection(universityID);
        MongoCollection<Document> scheduleBlockCollection = Database.utilsDatabase.getDatabase().getCollection("ScheduleBlocks");

        Document document = new Document("_id", universityID);

        document.append("Name", name);
        document.append("Level", level);

        int block = (Database.studentDatabase.countCollections()) % 3 + 1;

        String scheduleBlock = scheduleBlockCollection.find(new Document("_id", Database.scheduleBlocksObjectId)).first().getString("ScheduleBlock_" + level + "_" + block);

        document.append("Schedule", scheduleBlock);
        studentCollection.insertOne(document);

        this.schedule = schedule.readSchedule();
    }

    public void readStudentData() {
        MongoCollection<Document> studentCollection = Database.studentDatabase.getDatabase().getCollection(universityID);

        name = studentCollection.find(new Document("_id", universityID)).first().getString("Name");
        level = studentCollection.find(new Document("_id", universityID)).first().getInteger("Level");
        universityID = studentCollection.find(new Document("_id", universityID)).first().getString("_id");
    }


    public void viewStudentSchedule() {
        schedule = new Schedule(this).readSchedule();

        System.out.println("_____________________________________________________________________________");

        for (int i = 0; i < Days.values().length; i++) {
            String day = Days.values()[i].name();

            System.out.printf("%35s\n\n", day);
            System.out.printf("%s:%20s: %20s: %20s:\n", "Time", "Course", "Code", "Classroom");

            for (Schedule schedule : this.schedule) {
                if (schedule.getDay().equalsIgnoreCase(day)) {

                    String courseCode = schedule.getCourse().getCourseCode();
                    String courseName = schedule.getCourse().getCourseName();
                    String time = schedule.getTime();

                    if (courseName.length() > 20) {
                        courseName = courseName.substring(0, 20);
                    }

                    System.out.print(time);
                    int standardLengthOfTime = 13, indent = 2;

                    indent += (standardLengthOfTime - time.length());

                    for (int timeIndention = 0; timeIndention < indent; timeIndention++) {
                        System.out.print(" ");
                    }

                    System.out.print("   " + courseName);


                    int courseCodeIndention = 24 - courseName.length();

                    for (int nameIndention = 0; nameIndention < courseCodeIndention; nameIndention++) {
                        System.out.print(" ");
                    }

                    System.out.print(courseCode + "            " + schedule.getClassroom() + "\n");
                }
            }
            System.out.println("_____________________________________________________________________________");
        }
        System.out.println("Total Credit Hours: " + calculateCumulativeCreditHours());

    }

    @Override
    public String toString() {
        return "Student's Name: " + name + "\nStudent's ID: " + universityID + "\nStudent's Year: " + level + "\nCurrent Credit Hours: " + calculateCumulativeCreditHours();
    }

}
