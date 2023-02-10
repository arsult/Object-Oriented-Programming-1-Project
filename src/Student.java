import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

/*
 * This class holds the general information needed for a student
 * This class acts as a holder for the student information such as
 * their full name, university id, phone number and their semester level.
 */

public class Student {

    private String name;
    private String universityID;
    private int level;

    /**
     * Constructor
     *
     * @param name,         will receive the full name of the student and save it in the data field
     * @param universityID, will receive the university id and save it in the data field
     * @param level,        will receive their current semester level and based on that we can determine their schedule block.
     */

    public Student(String name, String universityID, int level) {
        this.name = name;
        this.universityID = universityID;
        this.level = level;
    }

    /**
     * The default constructor for this class which holds nothing.
     */
    public Student() {
        this.name = null;
        this.universityID = null;
        this.level = 0;
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

    /**
     * A functionally method, that will create a new file based on the University ID of the student and save their
     * General information such as their full name, ID, current semester level and their schedule block
     * where the student is able to modify it later.
     */

    public void saveData() throws FileNotFoundException {
        PrintWriter printWriter = new PrintWriter(universityID + ".txt"); // Create a new file based on the ID of the student

        printWriter.println("# Student Information"); // Write the header for the student general information

        printWriter.println(name + ", " + universityID + ", " + level); // Write the general information of student separated by commas.
        printWriter.println();

        printWriter.println("# Schedule"); // Write a header for the student schedule block

        int block = (int) (Long.parseLong(universityID) % 2 + 1); // This line will determine which schedule block the student receives based on their university id
        // If the student ID is even, then the first schedule block in the level will be chosen, If the student ID is odd, then the second schedule block in the level will be chosen.

        // Read Schedule blocks.
        File scheduleBlockFile = new File("ScheduleBlocks.txt"); // Instance of the ScheduleBlocks.txt file
        Scanner scheduleBlockScanner = new Scanner(scheduleBlockFile); // Creating an object of Scanner, so we are able to read from the file.

        while (scheduleBlockScanner.hasNext()) { // If we did not reach the end of file
            String line = scheduleBlockScanner.nextLine(); // Obtain the current line of reading.

            if (line.startsWith("//") || line.isBlank()) { // Ignore comments made by the user
                continue;
            }

            // If the student level and block match the one on the ScheduleBlock file
            if (line.equalsIgnoreCase("#ScheduleBlock_" + level + "_" + block)) {
                line = scheduleBlockScanner.nextLine(); // Jump to the next line

                // Read all the days in the schedule block selected
                while (!line.startsWith("Thursday")) { // If we haven't reached the end of the schedule yet
                    line = scheduleBlockScanner.nextLine(); // Read the next line
                    printWriter.println(line); // and paste it in the student's file.
                }

                break; // Break out the outer while loop
            }
        }

        printWriter.close();
    }

    /**
     * Another functionally method that will read the information of the specified student from their files and then
     * save the data into the data fields in this class
     * therefore reading the existing data of the student from their file, so we can work with these data.
     *
     */

    public void readData() throws FileNotFoundException {

        File file = new File(universityID + ".txt"); // set up file for reading
        Scanner scanner = new Scanner(file);

        String line; // our pointer to where the file reads

        while (scanner.hasNext()) {
            line = scanner.nextLine(); // read the first line of the file.

            if (line.equalsIgnoreCase("# Student Information")) { // if the first line matches our information header
                line = scanner.nextLine(); // read beyond that by 1 line.

                // Start tokenizing the information.
                String[] tokens = line.split(", ");
                name = tokens[0]; // The first token will contain the student name
                universityID = tokens[1]; // The second token will contain the student university id
                level = Integer.parseInt(tokens[2]); // the third token will contain their current semester level.

                break; // break out of the loop since we have found our wanted information.
            }

        }

    }

    /**
     * Overriding the default toString to show the student data
     *
     * @return The student data in a formatted form.
     */
    @Override
    public String toString() {
        return "Student's Name: " + name + "\nStudent's ID: " + universityID + "\nStudent's Semester level: " + level;
    }

}
