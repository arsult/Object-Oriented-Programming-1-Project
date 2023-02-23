package me.ted.faculties;

public enum Faculty {

    PREP_YEAR,
    BUSINESS,
    SCIENCE,
    ENGINEERING,
    MEDICINE,
    APPLIED_MEDICINE,
    COMPUTER_SCIENCE_AND_ENGINEERING,
    ENGLISH,
    LANGUAGES,
    THE_HOLY_QURAN_AND_ISLAMIC_STUDIES,
    SOCIETY;

    public String getFancyName() {
        String name = name().toLowerCase();
        String[] token = name.split("_");
        String fancyName = "";

        if (token.length > 1) {

            for (int i = 0; i < token.length; i++) {
                fancyName += token[i].substring(0, 1).toUpperCase() + token[i].substring(1) + " ";
            }
        } else {
            fancyName = name.substring(0, 1).toUpperCase() + name.substring(1);
        }
        return fancyName;
    }

}
