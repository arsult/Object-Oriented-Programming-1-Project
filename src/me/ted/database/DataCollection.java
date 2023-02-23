package me.ted.database;

public enum DataCollection {

    STUDENTS,
    ACADEMIC_ADVISERS,
    FACULTIES,
    SCHEDULES,
    UTILITY;

    public String getCollectionName() {
        String name = name().toLowerCase();
        String[] token = name.split("_");

        if (token.length > 1) {
            name = token[0].substring(0, 1).toUpperCase() + token[0].substring(1) + token[1].substring(0, 1).toUpperCase() + token[1].substring(1);
        } else {
            name = name.substring(0, 1).toUpperCase() + name.substring(1);
        }
        return name;
    }


}
