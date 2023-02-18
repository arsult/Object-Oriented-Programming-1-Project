import com.mongodb.client.MongoCollection;
import org.bson.Document;

import java.util.ArrayList;

/*
 * This class holds the general information needed for a student
 * This class acts as a holder for the student information such as
 * their full name, university id, phone number and their semester level.
 */

public class Student {

    private String name;
    private String universityID;
    private int level;
    private ArrayList<Schedule> schedule;

    /**
     * Constructor
     *
     * @param name,         will receive the full name of the student and save it in the data field
     * @param universityID, will receive the university id and save it in the data field
     * @param level,        will receive their current semester level and based on that we can determine their schedule block.
     */

    public Student(String name, String universityID, int level, ArrayList<Schedule> schedule) {
        this.name = name;
        this.universityID = universityID;
        this.level = level;
        this.schedule = schedule;
    }

    /**
     * The default constructor for this class which holds nothing.
     */
    public Student() {
        this.name = null;
        this.universityID = null;
        this.level = 0;
        this.schedule = null;
    }

    /**
     * Default Setter
     *
     * @param name, will receive the name and replace the current name held in the data field by the received argument.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Default Setter
     *
     * @param level, will receive the current semester level and replace the current name held in the data field by the received argument.
     */
    public void setLevel(int level) {
        this.level = level;
    }

    /**
     * Default Setter
     *
     * @param universityID, will receive the university id and replace the current name held in the data field by the received argument.
     */
    public void setUniversityID(String universityID) {
        this.universityID = universityID;
    }

    /**
     * Default Getter
     *
     * @return the name in the data field name
     */
    public String getName() {
        return name;
    }

    /**
     * Default Getter
     *
     * @return the semester level in the data field level
     */
    public int getLevel() {
        return level;
    }

    /**
     * Default Getter
     *
     * @return the university ID in the data field universityID
     */
    public String getUniversityID() {
        return universityID;
    }


    public ArrayList<Schedule> getSchedule() {
        return schedule;
    }

    public void setSchedule(ArrayList<Schedule> schedule) {
        this.schedule = schedule;
    }

    /**
     * This method will calculate the cumulative credit hours for the student.
     * The arraylist of schedules in data field is constructed by that each day has a schedule for it,
     * with that in mind this means that multiple days could have the same course, and we certainly do not need to count for duplicate course hours
     * The duplicates arraylist stores the course after calculating it credit hours therefore we are eliminating the possibility of counting the credit hours more than once.
     *
     * @return The total credit hours that the student has in their current schedule
     */
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

    /**
     * functionally method, that will create a new file based on the University ID of the student and save their
     * General information such as their full name, ID, current semester level and their schedule block
     * where the student is able to modify it later.
     */
    public void registerStudentData() {
        Schedule schedule = new Schedule(this);

        MongoCollection<Document> studentCollection = Database.getDatabase().getCollection(universityID);
        MongoCollection<Document> scheduleBlockCollection = Database.getDatabase().getCollection("ScheduleBlocks");

        Document document = new Document("_id", universityID);

        document.append("Name", name);
        document.append("Level", level);

        int block = (Database.countCollections() - 3) % 3 + 1;

        String scheduleBlock = scheduleBlockCollection.find(new Document("_id", Database.scheduleBlocksObjectId)).first().getString("ScheduleBlock_" + level + "_" + block);

        document.append("Schedule", scheduleBlock);
        studentCollection.insertOne(document);

        this.schedule = schedule.readSchedule();
    }

    /**
     * Another functionally method that will read the information of the specified student from their database collection and then
     * save the data into the data fields in this class
     * therefore reading the existing data of the student from their collection, so we can work with these data.
     */

    public void readStudentData() {
        MongoCollection<Document> studentCollection = Database.getDatabase().getCollection(universityID);

        name = studentCollection.find(new Document("_id", universityID)).first().getString("Name");
        level = studentCollection.find(new Document("_id", universityID)).first().getInteger("Level");
        universityID = studentCollection.find(new Document("_id", universityID)).first().getString("_id");
    }


    public void viewStudentSchedule() {
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

                    if (courseName.length() > 20) { // Cut the course short to only 20 words.
                        courseName = courseName.substring(0, 20);
                    }

                    // Designing-output so everything aligns with each other.
                    System.out.print(time);
                    // An equation developed for the indention of schedule (design-output)
                    int standardLengthOfTime = 13, indent = 2;

                    indent += (standardLengthOfTime - time.length());

                    for (int timeIndention = 0; timeIndention < indent; timeIndention++) {
                        System.out.print(" ");
                    }

                    System.out.print("   " + courseName);

                    /*
                    Required for designing the correct output of the schedule, since the general name of the course varys in length
                    We need to find the amount of spaces required for the course-code to be in the correct column by using the provided equation below.
                    */

                    int courseCodeIndention = 24 - courseName.length(); // 20 words -> only 4 indention, 19 words -> 5 indentions, etc...

                    for (int nameIndention = 0; nameIndention < courseCodeIndention; nameIndention++) {
                        System.out.print(" ");
                    }

                    System.out.print(courseCode + "            " + schedule.getClassroom() + "\n"); //12 spaces
                }
            }
            System.out.println("_____________________________________________________________________________");
        }
        System.out.println("Total Credit Hours: " + calculateCumulativeCreditHours());

    }

    /**
     * Overriding the default toString to show the student data
     *
     * @return The student data in a formatted form.
     */
    @Override
    public String toString() {
        return "Student's Name: " + name + "\nStudent's ID: " + universityID + "\nStudent's Year: " + level + "\nCurrent Credit Hours: " + calculateCumulativeCreditHours();
    }

}
