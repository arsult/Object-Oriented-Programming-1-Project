package me.ted.person.adviser;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import me.ted.faculties.Faculty;

@Getter@Setter
@NoArgsConstructor@AllArgsConstructor
public class AcademicAdviser {

    private String name;
    private String ID;
    private String password;
    private String phoneNumber;
    private Faculty faculty;
    private String department;
    private String position;
    private String graduateMajor;


    public void saveData() {

    }

    public static AcademicAdviser find(String ID) {
        return null;
    }


}
