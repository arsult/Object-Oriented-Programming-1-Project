package me.ted.faculties;

public enum Department {

    COMPUTER_SCIENCE_AND_ARTIFICIAL_INTELLIGENCE(Faculty.COMPUTER_SCIENCE_AND_ENGINEERING),
    SOFTWARE_ENGINEERING(Faculty.COMPUTER_SCIENCE_AND_ENGINEERING),
    COMPUTER_ENGINEERING_AND_NETWORKS(Faculty.COMPUTER_SCIENCE_AND_ENGINEERING),
    DATA_SCIENCE(Faculty.COMPUTER_SCIENCE_AND_ENGINEERING),
    CYBER_SECURITY(Faculty.COMPUTER_SCIENCE_AND_ENGINEERING),
    INFORMATION_TECHNOLOGY(Faculty.COMPUTER_SCIENCE_AND_ENGINEERING),

    MATHEMATICS(Faculty.SCIENCE),
    CHEMISTRY(Faculty.SCIENCE),
    PHYSICS(Faculty.SCIENCE),
    BIOLOGY(Faculty.SCIENCE),
    STATISTICS(Faculty.SCIENCE),
    BIOCHEMISTRY(Faculty.SCIENCE),

    ACCOUNTING(Faculty.BUSINESS),
    MARKETING(Faculty.BUSINESS),
    FINANCE(Faculty.BUSINESS),
    INFORMATION_SYSTEMS_MANAGEMENT(Faculty.BUSINESS),
    BUSINESS_MANAGEMENT(Faculty.BUSINESS),
    HUMAN_RESOURCES_MANAGEMENT(Faculty.BUSINESS),
    SUPPLY_CHAIN_MANAGEMENT(Faculty.BUSINESS),

    MECHANICAL_AND_MATERIALS_ENGINEERING(Faculty.ENGINEERING),
    ELECTRIC_AND_ELECTRONIC_ENGINEERING(Faculty.ENGINEERING),
    CHEMICAL_ENGINEERING(Faculty.ENGINEERING),
    //...

    MEDICAL_SURGERY(Faculty.MEDICINE),

    ENGLISH_LANGUAGE(Faculty.ENGLISH),
    CHINESE_LANGUAGE(Faculty.LANGUAGES),
    ARABIC_LANGUAGE(Faculty.LANGUAGES),

    ISLAMIC_STUDIES(Faculty.THE_HOLY_QURAN_AND_ISLAMIC_STUDIES),

    OTHER(Faculty.PREP_YEAR)
    ;


    private final Faculty faculty;

    Department(Faculty belongsTo) {
        this.faculty = belongsTo;
    }

    public Faculty getFaculty() {
        return faculty;
    }

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
