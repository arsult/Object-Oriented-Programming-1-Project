package me.ted.utils;

public enum Days {

    Sunday,
    Monday,
    Tuesday,
    Wednesday,
    Thursday;

    public char getFirstLetter() {
        if (this == Thursday) {
            return 'R';
        }

        return Character.toUpperCase(name().charAt(0));
    }

}