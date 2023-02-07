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
     *
     * A functionally method, that will create a new file based on the University ID of the student and save their
     * General information such as their full name, ID, current semester level and their schedule block
     * where the student is able to modify it later.
     *
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
        Scanner scheduleBlockScanner = new Scanner(scheduleBlockFile); // Creating an object of Scanner so we are able to read from the file.

        while (scheduleBlockScanner.hasNext()) { // If we did not reach the end of file
            String line = scheduleBlockScanner.nextLine(); // Obtain the current line of reading.

            if (line.startsWith("//") || line.isBlank()) { // Ignore comments made by the user
                continue;
            }

            // If the student level and block match the one on the ScheduleBlock file
            if (line.equalsIgnoreCase("#ScheduleBlock_" + level + "_" + block)) {
                line = scheduleBlockScanner.nextLine(); // Jump to the next line

                // Read all the days in the schedule block selected
                while (!line.startsWith("Thursday")) {
                    line = scheduleBlockScanner.nextLine();
                    printWriter.println(line);
                }

                break;
            }
        }

        printWriter.close();
    }

    @Override
    public String toString() {
        return ""; // Will design later...
    }
}
