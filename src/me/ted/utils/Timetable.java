package me.ted.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Timetable {

    private final HashMap<List<Days>, List<String>> mapOfTimes;

    private static final int startingHour = 8, endingHour = 20;

    public Timetable() {
        mapOfTimes = new HashMap<>();
        fillTime();
    }

    private void fillTime() {
        // Will expand later, leave this as a prototype...

        List<String> times = new ArrayList<>();

        for (int i = startingHour; i <= endingHour; i++) {
            times.add(i + ":00 - " + i + ":50");
        }

        /**
         * 1. 8:00 - 8:50 [Sunday ...]          2. 8:00 - 8:50     [Monday, Wednesday] ...
         *
         *
         *
         */

        mapOfTimes.put(List.of(Days.Sunday, Days.Tuesday, Days.Thursday), times);
        mapOfTimes.put(List.of(Days.Monday, Days.Wednesday), times);
    }

    public void printTimetable() {
        int index = 1;
        int longestText = 0;
        for (Map.Entry<List<Days>, List<String>> entrySet : mapOfTimes.entrySet()) {
            for (String times : entrySet.getValue()) {
                String output = index + ". " + times + " " + entrySet.getKey().toString();
                System.out.print(output);
                longestText = Math.max(longestText, output.length());
                for (int i = 0; i < Math.abs(longestText-(output.length()+10)); i++){
                    System.out.print(" ");

                }
                if (index++ % 2 == 0) {
                    System.out.println();
                }
            }
        }

    }

    public HashMap<List<Days>, String> getTimeBasedOnIndex(int index) {

        return null;
    }

    public HashMap<List<Days>, List<String>> getMapOfTimes() {
        return mapOfTimes;
    }
}
