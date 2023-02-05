import java.util.ArrayList;

public class Student {

    private String name;
    private String universityID;
    private int level;

    public Student(String name, String universityID, int level) {
        this.name = name;
        this.universityID = universityID;
        this.level = level;
    }

    public Student() {
        this.name = null;
        this.universityID = null;
        this.level = 0;
    }

    // Setters


    public void setName(String name) {
        this.name = name;
    }

    public void setLevel(int level) {
        this.level = level;
    }


    public void setUniversityID(String universityID) {
        this.universityID = universityID;
    }

    // Getters


    public String getName() {
        return name;
    }

    public int getLevel() {
        return level;
    }


    public String getUniversityID() {
        return universityID;
    }

    @Override
    public String toString() {
        return ""; // Will design later...
    }
}
