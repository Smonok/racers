package com.foxminded.racers;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SortedRacers {

    private final Map<String, String> racersCarsTime = new HashMap<>();

    public List<String> createSortedRacers() throws IOException {
        List<String> sortedRacers = new ArrayList<>();

        createRacersCarsTime("src/main/resources/abbreviations.txt");
        racersCarsTime.entrySet().stream().sorted(Map.Entry.<String, String>comparingByValue()).forEach(racer -> {
            sortedRacers.add(racer.getKey() + racer.getValue());
        });

        return sortedRacers;
    }

    private void createRacersCarsTime(String abbreviationsFile) throws IOException {
        Map<String, String> racersTime = new RacersTime().createRacersTime("src/main/resources/start.log",
                "src/main/resources/end.log");
        List<String> abbreviations = new ArrayList<>(Files.readAllLines(Paths.get(abbreviationsFile)));

        abbreviations.forEach(abbreviation -> {
            String racer = abbreviation.substring(0, 3);
            String fullName = abbreviation.substring(4, getSecondUnderscoreIndex(abbreviation));
            String car = abbreviation.substring(getSecondUnderscoreIndex(abbreviation) + 1);

            racersTime.keySet().forEach(name -> {
                if (name.equals(racer)) {
                    racersCarsTime.put(String.format("%-" + 20 + "s | %-" + 25 + "s | ", fullName, car),
                            racersTime.get(name));
                }
            });
        });
    }

    private int getSecondUnderscoreIndex(String abbreviation) {
        return abbreviation.indexOf("_", abbreviation.indexOf("_") + 1);
    }
}
