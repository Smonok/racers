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

    public List<String> createSortedRacers(String startFile, String endFile, String abbreviationsFile)
            throws IOException, MissingAbbreviationException {
        List<String> sortedRacers = new ArrayList<>();
        Map<String, String> racersTime = new TimeCalculator().createRacersTime(startFile, endFile);

        createRacersCarsTime(racersTime, abbreviationsFile);
        racersCarsTime.entrySet().stream().sorted(Map.Entry.comparingByValue())
                .forEach(racer -> sortedRacers.add(racer.getKey() + racer.getValue()));

        return sortedRacers;
    }

    private void createRacersCarsTime(Map<String, String> racersTime, String abbreviationsFile)
            throws IOException, MissingAbbreviationException {
        List<String> abbreviations = new ArrayList<>(Files.readAllLines(Paths.get(abbreviationsFile)));

        if (abbreviations.size() != racersTime.size()) {
            throw new MissingAbbreviationException();
        }

        abbreviations.forEach(abbreviation -> {
            String racer = parseRacerAbbreviation(abbreviation);
            String fullName = parseFullName(abbreviation);
            String car = parseCarBrand(abbreviation);

            racersTime.keySet().stream().filter(name -> name.equals(racer)).forEach(name -> {
                racersCarsTime.put(String.format("%-" + 20 + "s | %-" + 25 + "s | ", fullName, car),
                        racersTime.get(name));
            });
        });
    }

    private String parseRacerAbbreviation(String abbreviation) {
        return abbreviation.substring(0, 3);
    }

    private String parseFullName(String abbreviation) {
        return abbreviation.substring(4, getSecondUnderscoreIndex(abbreviation));
    }

    private String parseCarBrand(String abbreviation) {
        return abbreviation.substring(getSecondUnderscoreIndex(abbreviation) + 1);
    }

    private int getSecondUnderscoreIndex(String abbreviation) {
        return abbreviation.indexOf("_", abbreviation.indexOf("_") + 1);
    }
}
