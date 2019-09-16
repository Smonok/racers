package com.foxminded.racers;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Sorter {
    private Map<String, String> racersCarsTime = new HashMap<>();

    public List<String> createSortedRacers(String startFile, String endFile, String abbreviationsFile)
            throws IOException, MissingLineException {
        List<String> sortedRacers = new ArrayList<>();
        Map<String, String> racersTime = new TimeCalculator().createRacersTime(startFile, endFile);

        fillRacersCarsTime(racersTime, abbreviationsFile);
        racersCarsTime.entrySet().stream().sorted(Map.Entry.comparingByValue())
                .forEach(racer -> sortedRacers.add(racer.getKey() + racer.getValue()));

        return sortedRacers;
    }

    private void fillRacersCarsTime(Map<String, String> racersTime, String abbreviationsFile)
            throws IOException, MissingLineException {
        List<String> abbreviations = new ArrayList<>(Files.readAllLines(Paths.get(abbreviationsFile)));

        if (abbreviations.size() != racersTime.size()) {
            throw new MissingLineException("Missed line in the file");
        }

        abbreviations.forEach(abbreviation -> {
            String parsedRacerAbbreviation = abbreviation.substring(0, 3);
            String parsedFullName = abbreviation.substring(4, findSecondOccurrenceIndex(abbreviation, "_"));
            String parsedCarBrand = abbreviation.substring(findSecondOccurrenceIndex(abbreviation, "_") + 1);
            String nameAndCar = String.format("%-" + 20 + "s | %-" + 25 + "s | ", parsedFullName, parsedCarBrand);

            racersTime.keySet().stream().filter(name -> name.equals(parsedRacerAbbreviation))
                    .forEach(name -> racersCarsTime.put(nameAndCar, racersTime.get(name)));

        });
    }

    private int findSecondOccurrenceIndex(String abbreviation, String symbol) {
        return abbreviation.indexOf(symbol, abbreviation.indexOf(symbol) + 1);
    }
}
