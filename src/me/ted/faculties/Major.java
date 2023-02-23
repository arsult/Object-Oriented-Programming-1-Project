package me.ted.faculties;

public enum Major {

    SURGERY(Department.MEDICAL_SURGERY),

    ARTIFICIAL_INTELLIGENCE(Department.COMPUTER_SCIENCE_AND_ARTIFICIAL_INTELLIGENCE),
    CYBER_SECURITY(Department.CYBER_SECURITY),
    DATA_SCIENCE(Department.DATA_SCIENCE),
    COMPUTER_SCIENCE(Department.COMPUTER_SCIENCE_AND_ARTIFICIAL_INTELLIGENCE),
    COMPUTER_ENGINEERING_AND_NETWORKS(Department.COMPUTER_ENGINEERING_AND_NETWORKS),
    SOFTWARE_ENGINEERING(Department.SOFTWARE_ENGINEERING),

    BIOCHEMISTRY(Department.BIOCHEMISTRY),
    MATHEMATICS(Department.MATHEMATICS),
    CHEMISTRY(Department.CHEMISTRY),
    INDUSTRY_CHEMISTRY(Department.CHEMISTRY),
    PHYSICS(Department.PHYSICS),
    BIOLOGY(Department.BIOLOGY),

    //...
    ;

    private final Department department;

    Major(Department belongsTo) {
        this.department = belongsTo;
    }

    public Department getDepartment() {
        return department;
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
